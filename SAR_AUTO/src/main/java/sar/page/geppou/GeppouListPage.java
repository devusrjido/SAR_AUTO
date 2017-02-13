package sar.page.geppou;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import main.ProcKind;
import sar.page.common.AbstractSARPage;

/** 月報(一覧)ページクラス  */
public class GeppouListPage extends AbstractSARPage {
	
	/** 年月 のインデックス  */
	public static final int ITIRAN_TABLE_COLUMN_YYYYMM = 0;
	/** 月報S(ステータス)のインデックス  */
	public static final int ITIRAN_TABLE_COLUMN_STATUS = 2;
	
	public GeppouListPage(WebDriver driver){
		super(driver);
	}

	/** 
	 * 最古の未作成(作成中)リンクを押下する。
	 * @return　月報(詳細)ページオブジェクト
	 */
	public GeppouDetailsPage clickUncreatedDateLink() {
		getUncreatedDateElement(ProcKind.GEPPOU_REGISTER).click();
		waitForSARPageLoaded();
		return new GeppouDetailsPage(_driver);
	}
	
	/**
	 * 最古の未作成リンクを押下する。 
	 * @return　月報(詳細)ページオブジェクト
	 */
	public GeppouDetailsPage clickDateLink(String yyyymm) {
		waitForSARPageLoaded();
		WebElement elem = getDateLinkElement(yyyymm, 
				ITIRAN_TABLE_COLUMN_YYYYMM, 
				ITIRAN_TABLE_COLUMN_STATUS);
		
		elem.click();
		waitForSARPageLoaded();
		return new GeppouDetailsPage(_driver);
	}
}