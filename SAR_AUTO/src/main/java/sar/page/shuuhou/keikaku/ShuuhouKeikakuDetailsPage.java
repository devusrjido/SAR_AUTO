package sar.page.shuuhou.keikaku;

import java.util.List;

import org.openqa.selenium.WebDriver;

import main.ProcKind;
import sar.page.common.CSSSelector;
import sar.page.form.SagyouListForm;
import sar.page.shuuhou.AbstractShuuhouPage;

/** 週報計画(詳細)ページクラス  */
public class ShuuhouKeikakuDetailsPage extends AbstractShuuhouPage {
	
	public ShuuhouKeikakuDetailsPage(WebDriver driver){
		super(driver);
	}
	
	/**
	 * "日付"リンクを押下します。(日付指定) 
	 * @param date : 対象日
	 */
	public void clickDailyDateLink(String date) {
	    findElement(_dateElementIdList.get(date)).click();
	}
	
	/**
	 * 作業一覧(計画)の日付リストを取得します。
	 * @return 日付リスト(0:月曜日 ～ 6:日曜日)
	 */
	public List<String> getDateList() {
		return getDateList(ProcKind.SHUUHOU_KEIKAKU_REGISTER);
	}
	
	/**
	 * "勤務計画保存"ボタン押下します。 
	 */
	public void clickKinmuKeikakuSaveButton() {
		waitLoad(2000);
		click(CSSSelector.BTN_KINMU_KEIKAKU_SAVE);
	    alertAccept();
		waitForSARPageLoaded();
	}

	/**
	 * "申請"ボタン押下します。 
	 */
	public ShuuhouKeikakuListPage clickRequestButton() {
		waitLoad(2000);
		click(CSSSelector.BTN_APPLY);
	    alertAccept();
	    
	    waitForSARPageLoaded();
	    return new ShuuhouKeikakuListPage(_driver);
	}
	
	/**
	 * 作業一覧(計画)フォームを取得します。
	 * @return 作業一覧(計画)フォーム
	 */
	public SagyouListForm getSagyouListKeikakuForm() {
		return new SagyouListForm(_driver, ProcKind.SHUUHOU_KEIKAKU_REGISTER);
	}
}