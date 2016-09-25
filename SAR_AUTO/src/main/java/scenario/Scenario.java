package scenario;

public interface Scenario {
	/** 
	 * シナリオで実行する処理 
	 */
	public void start();
	
	/** 
	 * 例外発生時のみ実行する処理 
	 */
	public void error();

	/** 
	 * シナリオ実行時に必ず行う処理<br>
	 * シナリオ実行中に例外が発生した場合でも必ず実行されます
	 */
	public void always();
	
	/**
	 * シナリオ名を返却する
	 * @return シナリオ名
	 */
	public String getScenarioName();
}
