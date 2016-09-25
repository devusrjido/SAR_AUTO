package main;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import common.JarOption;
import common.WebDriverUtil;
import file.FileID;
import file.FileRecordFactory;
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
		
		if (option.contains(JarOption.HELP)) { 
			option.showHelp();
			return;
		}
		
		// 引数チェック
		if (!checkRequiredOptions(option)) {
			return;
		}
		
		String[] shoriKbnList = option.getValue(JarOption.SHORI_KBN).split(",");
		String userName = option.getValue(JarOption.SAR_USER_NAME);
		String password = option.getValue(JarOption.SAR_PASSOWORD);
		String renkeiFileDir = option.getValue(JarOption.RENKEI_FILE_DIR);
		String blowserName = option.getValue(JarOption.BLOWSER_NAME).toLowerCase();
		
		ScenarioOperator operator = new ScenarioOperator();
 		WebDriver driver = null;
		
		try {
			if (blowserName.equals(WebDriverUtil.BLOWSER_IE)) {
				driver = WebDriverUtil.getInternetExplorerDriver();
			} else if (blowserName.equals(WebDriverUtil.BLOWSER_CHROME)) { 
				driver = WebDriverUtil.getChromeDriver();
			} else if (blowserName.equals(WebDriverUtil.BLOWSER_FIREFOX)) {
				String binary = option.getValue(JarOption.BLOWSER_BINARY);
		    	if (binary != null && binary.equals("")) {
			    	driver = WebDriverUtil.getFirefoxDriver(binary);
		    	} else {
		    		driver = WebDriverUtil.getFirefoxDriver();
		    	}
			}
			
			FileRecordFactory factory = new FileRecordFactory(renkeiFileDir);
			
			List<SagyouKeikaku> sagyouKeikakuRecordList = null;
			ShuuhouJisseki shuuhouJissekiRecord = null;
			List<Nippou> nippouRecordList = null;
			List<SagyouJisseki> sagyouJissekiRecordList = null;
			
			// 処理区分別に必要なファイルを読込み
			for (String shoriKbn : shoriKbnList) {
				if (shoriKbn.equals(ShoriKbn.SHUUHOU_KEIKAKU_REGISTER)) {
					sagyouKeikakuRecordList = factory.createRecordForList(FileID.SAGYOU_LIST_KEIKAKU_REGISTER, SagyouKeikaku.class);
					if (sagyouKeikakuRecordList == null) return;
					
				} else if (shoriKbn.equals(ShoriKbn.SHUUHOU_JISSEKI_REGISTER)) {
					shuuhouJissekiRecord = factory.createRecord(FileID.SHUHOU_JISSEKI_REGISTER, ShuuhouJisseki.class);
					if (shuuhouJissekiRecord == null) return;
					
				} else if (shoriKbn.equals(ShoriKbn.NIPPOU_REGISTER)) {
					nippouRecordList = factory.createRecordForList(FileID.NIPPOU_REGISTER, Nippou.class);
					sagyouJissekiRecordList = factory.createRecordForList(FileID.SAGYOU_LIST_JISSEKI_REGISTER, SagyouJisseki.class);
					if (nippouRecordList == null || sagyouJissekiRecordList == null) return;
				}
			}
			
			// SARログインのシナリオを登録
			operator.addScenario(new SARLoginScenario(new ScenarioPameter()
				.setValue(ScenarioParamKey.WEBDRIVER, driver)
				.setValue(ScenarioParamKey.USERNAME, userName)
				.setValue(ScenarioParamKey.PASSWORD, password) 
			));
			
			// 渡された処理区分別にシナリオを登録
			for (String shoriKbn : shoriKbnList) {
				// 週報計画(登録)のシナリオを追加
				if (shoriKbn.equals(ShoriKbn.SHUUHOU_KEIKAKU_REGISTER)) {
					operator.addScenario(new ShuuhouKeikakuRegisterScenario(new ScenarioPameter()
						.setValue(ScenarioParamKey.SAGYOU_KEIKAKU_REGISTER_RECORD, sagyouKeikakuRecordList)
					));
				
				// 週報実績(登録)のシナリオを追加
				} else if (shoriKbn.equals(ShoriKbn.SHUUHOU_JISSEKI_REGISTER)) {
					operator.addScenario(new ShuuhouJissekiRegisterScenario(new ScenarioPameter()
						.setValue(ScenarioParamKey.SHUUHOU_JISSEKI_REGISTER_RECORD, shuuhouJissekiRecord)
					));
					
				//　日報(登録)のシナリオを追加
				} else if (shoriKbn.equals(ShoriKbn.NIPPOU_REGISTER)) {
					operator.addScenario(new NippouRegisterScenario(new ScenarioPameter()
						.setValue(ScenarioParamKey.NIPPOU_REGISTER_RECORD, nippouRecordList)
						.setValue(ScenarioParamKey.SAGYOU_JISSEKI_REGISTER_RECORD, sagyouJissekiRecordList)
					));
					
				//　月報(登録)のシナリオを追加
				} else if (shoriKbn.equals(ShoriKbn.GEPPOU_REGISTER)) {
					operator.addScenario(new GeppouRegisterScenario());
				}
			}
			
			// シナリオ実行
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
				driver.quit();
				driver = null;
			}
			
			if (operator != null) {
				operator = null;
			}
		}

		return;
	}

	/**
	 * 必須引数のチェックを行う
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
		String shoriKbn = option.getValue(JarOption.SHORI_KBN);
		if (!checkShoriKbn(shoriKbn)) {
			return false;
		}
		
		// ユーザ名が未指定
		String userName = option.getValue(JarOption.SAR_USER_NAME);
		if (StringUtils.isEmpty(userName)) {
			System.out.println("ユーザ名が指定されていません。");
			return false;
		}
		
		// パスワードが未指定
		String password = option.getValue(JarOption.SAR_PASSOWORD);
		if (StringUtils.isEmpty(password)) {
			System.out.println("パスワードが指定されていません。");
			return false;
		}
		
		// 連携フォルダのパスが未指定
		String renkeiFileDir = option.getValue(JarOption.RENKEI_FILE_DIR);
		if (StringUtils.isEmpty(renkeiFileDir)) {
			System.out.println("連携フォルダが指定されていません。");
			return false;
		}
		
		// ブラウザ種類が未指定
		String blowserName = option.getValue(JarOption.BLOWSER_NAME);
		if (StringUtils.isEmpty(blowserName)) {
			System.out.println("ブラウザ種類が指定されていません。");
			System.out.println("InternetExplorer, GoogleChrome, Firefox のいずれかを指定してください。");
			return false;
		} else {
			blowserName = blowserName.toLowerCase();
			if (!blowserName.equals(WebDriverUtil.BLOWSER_IE) &&
				!blowserName.equals(WebDriverUtil.BLOWSER_CHROME) &&
				!blowserName.equals(WebDriverUtil.BLOWSER_FIREFOX)) {
				
				System.out.println("ブラウザ種類が不正です。"); 
				System.out.println("InternetExplorer, GoogleChrome, Firefox のいずれかを指定してください。");
				return false;
			}
		}
	
		return true;
	}
	
	/**
	 * 指定された処理区分の中で存在しない処理区分が含まれていないかチェックします。
	 * @param shoriKbn : 引数の処理区分
	 * @return 含む(true), 含まない(false)
	 */
	public static boolean checkShoriKbn(String shoriKbn) {
		if (StringUtils.isEmpty(shoriKbn)) {
			System.out.println("処理区分が指定されていません。");
			return false;
		}
		
		String[] shoriKbnList = shoriKbn.split(",");
		if (shoriKbnList.length == 0) {
			System.out.println("処理区分が指定されていません。");
			return false;
		}
		
		boolean matched = false;
		for (String kbn : shoriKbnList) {
			for (String shoriKbnDefine : ShoriKbn.SHORI_KBN_LIST_DEFINE){
				if(kbn.equals(shoriKbnDefine)) {
					matched = true;
					break;
				}
			}
			if (!matched) {
				System.out.println("処理区分の値が不正です。");
				return false;
			}
			matched = false;
		}
		
		return true;
	}
}