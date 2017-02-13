package sar.page.common;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import library.WebDriverUtil;
import sar.page.geppou.GeppouListPage;
import sar.page.nippou.NippouListPage;
import sar.page.notloginpage.SARLoginPage;
import sar.page.shuuhou.keikaku.ShuuhouKeikakuListPage;
import main.ProcKind;

/** SARページ親クラス */
public abstract class AbstractSARPage extends WebDriverUtil implements SARPageHeader {
	/** ページ読込待機時間(日報)　*/
	public static final long WAIT_TIME_NIPPOU = 11000L;
	/** ページ読込待機時間(週報)　*/
	public static final long WAIT_TIME_SHUHOU = 6000L;
	/** ページ読込待機時間(月報)　*/
	public static final long WAIT_TIME_GEPPOU = 8000L;
	
	protected Map<String,String> _dateElementIdList = new LinkedHashMap<String,String>();
	
	public AbstractSARPage(WebDriver driver){
		super(driver);
	}
		
	/** 
	 * 日報(一覧)画面ヘ遷移します。（週報のタブを選択します。）
	 * @return　日報(一覧)ページオブジェクト
	 */
	public NippouListPage navigateToNippouListPage(){
		click(CSSSelector.A_NIPPOU);
		System.out.println("日報(一覧)画面へ遷移します。");
		
		waitForSARPageLoaded();
		return new NippouListPage(_driver);
	}
	
	/**
	 * 週報計画(一覧)画面ヘ遷移します。（週報のタブを選択します。）
	 * @return　週報計画(一覧)ページオブジェクト
	 */
	public ShuuhouKeikakuListPage navigateToShuuhouKeikakuListPage(){
		click(CSSSelector.A_SHUHOU);
		System.out.println("週報計画(一覧)画面へ遷移します。");
		
		waitForSARPageLoaded();
		return new ShuuhouKeikakuListPage(_driver);
	}

	/**
	 * 月報(一覧)画面ヘ遷移します。（月報のタブを選択します。）
	 * @return　月報(一覧)ページオブジェクト 
	 */
	public GeppouListPage navigateToGeppouListPage(){
		click(CSSSelector.A_GEPPOU);
		System.out.println("月報(一覧)画面へ遷移します。");
		
		waitForSARPageLoaded();
		return new GeppouListPage(_driver);
	}
	
	/**
	 * SARからログアウトします。
	 * @return　SARログインページオブジェクト 
	 */
	public SARLoginPage clickLogoutButton() {
		click(CSSSelector.BTN_LOGOUT);
		
		waitForSARPageLoaded();
		return new SARLoginPage(_driver);
	}
	
	/**
	 * 週報(計画),月報の未作成(作成中)最古のリンクのエレメントを取得します。
	 * @param shoriKbn : 処理区分
	 * @return 取得成功(リンクID) 取得失敗(空文字)
	 */
	protected WebElement getUncreatedDateElement(String shoriKbn) {
		String s1 = getShoriKbnString(shoriKbn);
		String s2 = CSSSelector.A_STATUS_LINK[0];
		String s3 = CSSSelector.A_STATUS_LINK[1];
		WebElement tmpElem = null;
		WebElement retElem = null;
		String id = "";
		int count = 0;
		
		while (true) {
			id = "#" +s1 + s2 + count + s3;	
			tmpElem = findElement(id);
			
			// 未作成・作成中であれば設定
			if (tmpElem.getText().equals("未作成") || tmpElem.getText().equals("作成中")) {
				retElem =  tmpElem;
			// 未作成・作成中以外の場合、1つ前が最古の未作成日付のものと判断します。
			} else {
				break;
			}
			count++;
		}
		return retElem;
	}
	
	/**
	 * 作業一覧の日付一覧を取得します。
	 * @param shoriKbn : 処理区分
	 * @return 作業一覧の日付一覧
	 */
	public List<String> getDateList(String shoriKbn) {
		String s1 = CSSSelector.A_DATE_LINK[0] + getShoriKbnString(shoriKbn);
		String s2 = CSSSelector.A_DATE_LINK[1];
		String s3 = CSSSelector.A_DATE_LINK[2];
		
		List<String> dateList = new LinkedList<String>();
		String selector = "";
		String date = "";
		for (int i = 0; i < 7; i++) {
			selector = "#" + s1 + s2 + i + s3;
			date = findElement(selector).getText();
			_dateElementIdList.put(date, selector);
			dateList.add(date);
		}
		
		return dateList;
	}
	
	/**
	 * 一覧テーブルから作成日付に対応します。未作成(作成中)のリンクエレメントを取得します。
	 * @param date              : 作成日付
	 * @param dateColumnIndex   : 日付項目のインデックス
	 * @param statusColumnIndex : ステータス項目のインデックス
	 * @return 未作成(作成中)のリンクエレメント
	 */
	public WebElement getDateLinkElement(String date, int dateColumnIndex, int statusColumnIndex) {
		List<WebElement> tdElems = null;
		WebElement statusElem = null;
		String selector = "";
		int i = 1;
		
		while(true) {
			selector = CSSSelector.ITIRAN_TABLE_TD[0] + i + CSSSelector.ITIRAN_TABLE_TD[1];
			tdElems = findElements(selector);
			
			WebElement startDateElem = tdElems.get(dateColumnIndex);
			if (date.equals(startDateElem.getText())) {
				statusElem = tdElems.get(statusColumnIndex);
				break;
			}
			i++;
		}
		
		return statusElem;
	}
	
	/**
	 * 要素セレクタを生成します。
	 * @param selector : 要素セレクタ
	 * @param shoriKbn : 処理区分
	 * @param index    : インデックス
	 * @return 処理区分
	 */
	protected String buildSelector(String[] selector, String shoriKbn, int index) {
		String s = "";
		if(shoriKbn.equals(ProcKind.SHUUHOU_KEIKAKU_REGISTER)) {
			s = "keikaku";
		} else if(shoriKbn.equals(ProcKind.NIPPOU_REGISTER)) {
			s = "jisseki";
		}
		return selector[0] + s + selector[1] + index; 
	}
	
	/**
	 * 処理区分文字列を取得します。
	 * @param shoriKbn : 処理区分
	 * @return 処理区分文字列
	 */
	protected String getShoriKbnString(String shoriKbn) {
		if (shoriKbn.equals(ProcKind.SHUUHOU_KEIKAKU_REGISTER)) {
			return "keikaku";
		} else if(shoriKbn.equals(ProcKind.SHUUHOU_JISSEKI_REGISTER)) {
			return "jisseki";
		} else if(shoriKbn.equals(ProcKind.NIPPOU_REGISTER)) {
			return "nippou";
		} else if(shoriKbn.equals(ProcKind.GEPPOU_REGISTER)) {
			 return "geppou";
		}
		
		return ""; 
	}
}