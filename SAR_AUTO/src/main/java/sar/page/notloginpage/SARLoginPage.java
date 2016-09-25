package sar.page.notloginpage;

import org.openqa.selenium.WebDriver;

import sar.page.common.AbstractSARPage;
import sar.page.common.CSSSelector;
import sar.page.nippou.NippouListPage;

/** SARログインページクラス  */
public class SARLoginPage extends AbstractSARPage {
	/** SARログインURL */
	private static final String SAR_LOGIN_URL = "https://trustpro-apps.tdc.co.jp/trustpro/index.action";
	
	public SARLoginPage(WebDriver driver){
		super(driver);
	}
	
	/** 
	 * ログイン画面へ遷移します。 
	 */
	public void open() {
		waitForPageLoaded();
		navigate(SAR_LOGIN_URL);
	}
	
	/** 
	 * "ユーザ名"を入力します。
	 *　@param value : 入力情報 
	 */
	public void typeUserName(String value) {
		typeValue(CSSSelector.TXT_LOGIN, value);
	}
	
	/**
	 * "パスワード"を入力します。
	 *　@param value : 入力情報 
	 */
	public void typePassword(String value) {
		typeValue(CSSSelector.PASS_PASSWORD, value);
	}
	
	/**
	 * "ログイン"ボタン押下します。 
	 * @return　日報(一覧)ページオブジェクト
	 */
	public NippouListPage submitLoginButton() {
		String title = getTitle();
		submit(CSSSelector.BTN_LOGIN);
		waitForSARPageLoaded();
		
		if (!title.equals(getTitle())) {
			return new NippouListPage(_driver);
		}
		
		return null;
	}
}