package sar.scenario;

import org.openqa.selenium.WebDriver;

import main.ScenarioParamKey;
import sar.page.nippou.NippouListPage;
import sar.page.notloginpage.SARLoginPage;
import scenario.Scenario;
import scenario.ScenarioPameter;
import scenario.data.ScenarioDataHolder;

/** 
 * SARログインシナリオクラス  
 * SARへログインを行う 
 */
public class SARLoginScenario implements Scenario {
	private ScenarioPameter _params;
	
	public SARLoginScenario(ScenarioPameter params) {
		_params = params;
	}
	
	public void start() {
		// シナリオパラメータを取得
		WebDriver driver = (WebDriver)_params.getObject(ScenarioParamKey.WEBDRIVER);
		String userName = _params.getString(ScenarioParamKey.USERNAME);
		String password = _params.getString(ScenarioParamKey.PASSWORD);
		
		// SARログインページへ遷移
		SARLoginPage loginPage = new SARLoginPage(driver);
		loginPage.open();
		
		/* 下記項目を入力 */
		// ユーザ名
		loginPage.typeUserName(userName);
		// パスワード
		loginPage.typePassword(password);
		
		// ログインボタンを押下
		NippouListPage currentPage = loginPage.submitLoginButton();
		
		// 遷移失敗
		if(currentPage == null) {
			return;
		}
		
		// 現在のページオブジェクトを更新
		ScenarioDataHolder dataHolder = ScenarioDataHolder.getInstance();
		dataHolder.setValue(ScenarioParamKey.CURRENT_PAGE, currentPage);
		
		System.out.println("ログインが成功しました。");
	}

	public void error() {}
	
	public void always() {}
	
	public String getScenarioName(){
		return "SARログイン";
	}
}