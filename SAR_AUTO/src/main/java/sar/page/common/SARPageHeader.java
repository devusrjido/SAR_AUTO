package sar.page.common;

import sar.page.geppou.GeppouListPage;
import sar.page.nippou.NippouListPage;
import sar.page.notloginpage.SARLoginPage;
import sar.page.shuuhou.keikaku.ShuuhouKeikakuListPage;

/** SARページの共通ヘッダー */
public interface SARPageHeader {
	/**
	 * 日報(一覧)画面ヘ遷移します。（週報のタブを選択します。）
	 * @return　日報(一覧)ページオブジェクト
	 */
	public NippouListPage navigateToNippouListPage();
	
	/**
	 * 週報計画(一覧)画面ヘ遷移します。（週報のタブを選択します。）
	 * @return　週報計画(一覧)ページオブジェクト
	 */
	public ShuuhouKeikakuListPage navigateToShuuhouKeikakuListPage();

	/**
	 * 月報(一覧)画面ヘ遷移します。（月報のタブを選択します。）
	 * @return　月報(一覧)ページオブジェクト 
	 */
	public GeppouListPage navigateToGeppouListPage();
	
	/**
	 * SARからログアウトします。
	 * @return　SARログインページオブジェクト 
	 */
	public SARLoginPage clickLogoutButton();
}
