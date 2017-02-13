package scenario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/** シナリオ実行クラス */
public class ScenarioOperator {
	private boolean _ignoreError = false;
	private List<Scenario> _scenarios = new LinkedList<Scenario>();
	
	public ScenarioOperator(){
		_scenarios = new LinkedList<Scenario>();
	}
	
	/** 
	 * @param ignoreError : シナリオの途中でエラーが発生しても以降のシナリオを実行するか
	 */
	public ScenarioOperator(boolean ignoreError){
		_ignoreError = ignoreError;
	}
	
	/** 
	 * 登録されたシナリオを実行する 
	 */
	public void operation() {
		boolean onError = false;
		
		if (_scenarios.size() == 0) {
			System.out.println("シナリオが登録されていません。");
			return;
		}
		
		for (Scenario scenario : _scenarios) {
			String scenarioName = scenario.getScenarioName();
			System.out.println(createLogHeader(scenarioName) + "のシナリオを開始します。");
			
			try {
				System.out.println("実行処理を開始します。");
				scenario.start();
				System.out.println("実行処理が完了しました。");

			} catch (Exception e) {
				System.out.println("シナリオ実行中にエラーが発生しました。");
				e.printStackTrace();
				
				System.out.println("エラー発生時の処理を開始します。");
				scenario.error();
				System.out.println("エラー発生時の処理が完了しました。");
				
				onError = true;
			} finally {
				System.out.println("必須処理を開始します。");
				scenario.always();
				System.out.println("必須処理が完了しました。");
				
				if(onError) {
					System.out.println(createLogHeader(scenarioName) + "のシナリオが異常終了しました。");
					
					if (_ignoreError) {
						System.out.println("エラーを無視して、次のシナリオを実行します。");
					} else {
						System.out.println("エラーが発生した為、以降のシナリオは実行しません。");
						break;
					}
				} else {
					System.out.println(createLogHeader(scenarioName) + "のシナリオが正常終了しました。");
				}
			}
		}
		
		System.out.println("全てのシナリオの実行が完了しました");
	}
	
	/** 
	 * 実行するシナリオを登録する
	 *　@param scenario : 実行するシナリオ
	 */
	public void addScenario(Scenario scenario) {
		if(scenario == null) throw new IllegalArgumentException("scenario is null");
		_scenarios.add(scenario);
	}
	
	/**
	 * 実行するシナリオを登録する
	 *　@param scenarios[] : 実行するシナリオ一覧
	 */
	public void addScenario(Scenario[] scenarios) {
		if(scenarios == null) throw new IllegalArgumentException("scenarios is null");
		for (Scenario scenario : _scenarios) {
			addScenario(scenario);
		}
	}
	
	/** 
	 * @param ignoreError : シナリオの途中でエラーが発生しても以降のシナリオを実行するか 
	 */
	public void setIgnoreError(boolean ignoreError){
		_ignoreError = ignoreError;
	}
	
	/**
	 * 標準出力先へ出力するログのヘッダー部を生成
	 *　@param scenarioName : シナリオ名
	 */
	private static String createLogHeader(String scenarioName) {
		LocalDateTime sysDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String dateTime = sysDateTime.format(formatter);
		
		return new StringBuffer("[")
				.append(dateTime)
				.append(" ")
				.append(scenarioName)
				.append("]")
				.toString();
	}
}
