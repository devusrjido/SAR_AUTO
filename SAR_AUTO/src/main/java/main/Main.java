package main;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import file.FileID;
import file.FileRecordFactory;
import library.WebDriverUtil;
import sar.bean.Nippou;
import sar.bean.SagyouJisseki;
import sar.bean.SagyouKeikaku;
import sar.bean.ShuuhouJisseki;
import sar.scenario.GeppouRegisterScenario;
import sar.scenario.NippouRegisterScenario;
import sar.scenario.SARLoginScenario;
import sar.scenario.ShuuhouJissekiRegisterScenario;
import sar.scenario.ShuuhouKeikakuRegisterScenario;
import scenario.ScenarioOperator;
import scenario.ScenarioPameter;

public class Main {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		JarOption option = new JarOption(args);
		
		// helpオプションが指定されていた場合、returnする。
		if (option.containsHelp()) { 
			option.showHelp();
			return;
		}
		
		// 必須オプションのチェック
		if (!checkRequiredOptions(option)) {
			return;
		}
		
		// オプションを取得
		List<String> procKindList = ProcKind.splitProcKindToList(option.getProcKind());
		String userName = option.getSarUserName();
		String password = option.getSarPassword();
		String renkeiFileDir = option.getRenkeiFileDirPath();
		String blowserName = option.getBlowserName();
		
		ScenarioOperator operator = new ScenarioOperator();
 		WebDriver driver = null;
		
		try {
			driver = WebDriverUtil.getWebDriver(blowserName, option.getBlowserBinary());
			FileRecordFactory factory = new FileRecordFactory(renkeiFileDir);
			
			// SARログインのシナリオを登録
			// ログイン処理は必須のため。
			operator.addScenario(new SARLoginScenario(new ScenarioPameter()
				.setValue(ScenarioParamKey.WEBDRIVER, driver)
				.setValue(ScenarioParamKey.USERNAME, userName)
				.setValue(ScenarioParamKey.PASSWORD, password) 
			));
			
			// 指定された処理区分別にシナリオを登録する
			for (String procKind : procKindList) {
				 switch (procKind){
					// 週報計画(登録)
					case ProcKind.SHUUHOU_KEIKAKU_REGISTER:
						List<SagyouKeikaku> sagyouKeikakuRecordList = 
							factory.createRecordForList(FileID.SAGYOU_LIST_KEIKAKU_REGISTER, SagyouKeikaku.class);
						if (sagyouKeikakuRecordList.size() == 0) return;
						
						operator.addScenario(new ShuuhouKeikakuRegisterScenario(new ScenarioPameter()
								.setValue(ScenarioParamKey.SAGYOU_KEIKAKU_REGISTER_RECORD, sagyouKeikakuRecordList)
						));
						break;
					
					// 週報実績(登録)
					case ProcKind.SHUUHOU_JISSEKI_REGISTER:
						ShuuhouJisseki shuuhouJissekiRegisterRecord = 
							factory.createRecord(FileID.SHUHOU_JISSEKI_REGISTER, ShuuhouJisseki.class);
						if (shuuhouJissekiRegisterRecord == null) return;
						
						operator.addScenario(new ShuuhouJissekiRegisterScenario(new ScenarioPameter()
								.setValue(ScenarioParamKey.SHUUHOU_JISSEKI_REGISTER_RECORD, shuuhouJissekiRegisterRecord)
						));
						break;
					
					// 日報(登録)
					case ProcKind.NIPPOU_REGISTER:
						List<Nippou> nippouRecordList = factory.createRecordForList(FileID.NIPPOU_REGISTER, Nippou.class);
						List<SagyouJisseki> sagyouJissekiRecordList = factory.createRecordForList(FileID.SAGYOU_LIST_JISSEKI_REGISTER, SagyouJisseki.class);
						
						if (nippouRecordList.size() == 0 || 
							sagyouJissekiRecordList.size() == 0) return;
						
						operator.addScenario(new NippouRegisterScenario(new ScenarioPameter()
								.setValue(ScenarioParamKey.NIPPOU_REGISTER_RECORD, nippouRecordList)
								.setValue(ScenarioParamKey.SAGYOU_JISSEKI_REGISTER_RECORD, sagyouJissekiRecordList)
						));
						break;
					
					// 月報(登録)
					case ProcKind.GEPPOU_REGISTER:
						operator.addScenario(new GeppouRegisterScenario());
						break;
						
					default:
						// 事前に処理区分をチェックしているため、この分岐には入らない
				}
			}
						
			// 登録された順にシナリオを実行
			operator.operation();
			
		} catch (WebDriverException e) {
			System.out.println("処理中にエラーが発生したので、終了しました。");
			e.printStackTrace();
			return;
		} catch (Exception e) {
			System.out.println("処理中にエラーが発生したので、終了しました。");
			e.printStackTrace();
			return;
		} finally {
			if (driver != null) {
					driver = null;
			}
			
			if (operator != null) {
				operator = null;
			}
		}

		return;
	}

	/**
	 * 必須オプションのチェックを行う
	 * @param option : 引数
	 * @return 正常(true), 異常(false)
	 */
	public static boolean checkRequiredOptions(JarOption option) {
		// 引数が未指定
		if (option.size() == 0) {
			System.out.println("引数が指定されていません。");
			return false;
		}
				
		// 処理区分チェック
		if (ProcKind.invalidProcKind(option.getProcKind())) {
			return false;
		}
		
		// ユーザ名が未指定
		if (StringUtils.isEmpty(option.getSarUserName())) {
			System.out.println("ユーザ名が指定されていません。");
			return false;
		}
		
		// パスワードが未指定
		if (StringUtils.isEmpty(option.getSarPassword())) {
			System.out.println("パスワードが指定されていません。");
			return false;
		}
		
		// 連携フォルダのパスが未指定
		if (StringUtils.isEmpty(option.getRenkeiFileDirPath())) {
			System.out.println("連携フォルダが指定されていません。");
			return false;
		}
		
		// ブラウザ種類が未指定
		String blowserName = option.getBlowserName();
		if (StringUtils.isEmpty(blowserName)) {
			System.out.println("ブラウザ種類が指定されていません。");
			System.out.println("InternetExplorer, GoogleChrome, Firefox のいずれかを指定してください。");
			return false;
		}
		
		if(WebDriverUtil.invalidBlowserName(blowserName)) {
			System.out.println("ブラウザ種類が不正です。"); 
			System.out.println("InternetExplorer, GoogleChrome, Firefox のいずれかを指定してください。");
			return false;
		}

		return true;
	}
}