package sar.page.shuuhou.jisseki;

import org.openqa.selenium.WebDriver;

import sar.page.common.CSSSelector;
import sar.page.shuuhou.AbstractShuuhouPage;

/** 週報実績(一覧)ページクラス  */
public class ShuuhouJissekiListPage extends AbstractShuuhouPage {
	public ShuuhouJissekiListPage(WebDriver driver){
		super(driver);
	}

	/** 
	 * "新規作成"ボタン押下します。
	 * @return　週報実績(詳細)ページオブジェクト
	 */
	public ShuuhouJissekiDetailsPage clickCreateNewButton() {
		waitLoad(2000);
		click(CSSSelector.BTN_CREATE_NEW);
		waitForSARPageLoaded();
		
		return new ShuuhouJissekiDetailsPage(_driver);
	}
}