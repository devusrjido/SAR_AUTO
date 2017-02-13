package sar.page.shuuhou.keikaku;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import main.ProcKind;
import sar.page.shuuhou.AbstractShuuhouPage;

/** 週報計画(一覧)ページクラス  */
public class ShuuhouKeikakuListPage extends AbstractShuuhouPage {
	
	/** 開始日のインデックス  */
	public static final int ITIRAN_TABLE_COLUMN_START_DATE = 0;
	/** 計画S(ステータス)のインデックス  */
	public static final int ITIRAN_TABLE_COLUMN_STATUS = 5;
	
	public ShuuhouKeikakuListPage(WebDriver driver){
		super(driver);
	}

	/** 
	 * 最古の未作成(作成中)リンクを押下します。
	 * @return　週報計画(詳細)ページオブジェクト
	 */
	public ShuuhouKeikakuDetailsPage clickUncreatedDateLink() {
		getUncreatedDateElement(ProcKind.SHUUHOU_KEIKAKU_REGISTER).click();
		
		waitForSARPageLoaded();
		return new ShuuhouKeikakuDetailsPage(_driver);
	}
	
	/**
	 * "最古の未作成リンク"を押下します。
	 * @return　週報計画(詳細)ページオブジェクト
	 */
	public ShuuhouKeikakuDetailsPage clickDateLink(String date) {
		WebElement elem = getDateLinkElement(date, 
				ITIRAN_TABLE_COLUMN_START_DATE, 
				ITIRAN_TABLE_COLUMN_STATUS);
		
		elem.click();
		waitForSARPageLoaded();
		return new ShuuhouKeikakuDetailsPage(_driver);
	}
}