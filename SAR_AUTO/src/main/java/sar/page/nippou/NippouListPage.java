package sar.page.nippou;

import org.openqa.selenium.WebDriver;

import sar.page.common.AbstractSARPage;
import sar.page.common.CSSSelector;

/** 日報(一覧)ページクラス  */
public class NippouListPage extends AbstractSARPage {
	public NippouListPage(WebDriver driver){
		super(driver);
	}
	
	/** 
	 * "新規作成"ボタン押下します。
	 * @return 日報(詳細)ページオブジェクト
	 */
	public NippouDetailsPage clickCreateNewButton() {
		click(CSSSelector.BTN_CREATE_NEW, 2000);
		
		waitForSARPageLoaded();
		return new NippouDetailsPage(_driver);
	}
}