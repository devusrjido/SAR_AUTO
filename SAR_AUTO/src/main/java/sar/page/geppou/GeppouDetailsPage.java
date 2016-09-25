package sar.page.geppou;

import org.openqa.selenium.WebDriver;

import sar.page.common.AbstractSARPage;
import sar.page.common.CSSSelector;

/** 月報(詳細)ページクラス  */
public class GeppouDetailsPage extends AbstractSARPage {
	public static final String SELECT_ACTION_EXCEL = "Download:Form";
	
	public GeppouDetailsPage(WebDriver driver){
		super(driver);
	}
	
	/** 
	 * "保存"ボタンを押下します。
	 */
	public void clickSaveButton() {
		waitLoad(2000);
		click(CSSSelector.BTN_SAVE);
		alertAccept();
		waitForSARPageLoaded();
	}
	
	/**
	 * "月締"ボタンを押下します。
	 * @return　月報(詳細)ページオブジェクト
	 */
	public GeppouListPage clickTsukijimeButton() {
		waitLoad(2000);
		click(CSSSelector.BTN_TSUKIJIME);
		alertAccept();
		
		waitForSARPageLoaded();
		return new GeppouListPage(_driver);
	}
	
	/** 
	 * 画面アクションの選択します。 
	 */
	public void selectAction(String action) {
		selectByValue(CSSSelector.SELECT_ACTIONS, action);
	}
	
	/** 
	 * "実行"ボタンを押下します。
	 */
	public void clickActionButton() {
		click(CSSSelector.BTN_ACTION);
		waitForSARPageLoaded();
	}
}