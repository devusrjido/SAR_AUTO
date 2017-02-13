package sar.page.common;

/*** 要素セレクタ定義  */
public class CSSSelector {
	private CSSSelector(){}
	
	//------------------------------------------------------------------------------------------
	// ログイン画面
	//------------------------------------------------------------------------------------------
	/** ユーザ名 */
	public static final String TXT_LOGIN = "#userid";
	/** パスワード */
	public static final String PASS_PASSWORD = "input[type='password']";
	/** ログイン */
	public static final String BTN_LOGIN = ".tp-login-form button[type=submit]";
	
	//------------------------------------------------------------------------------------------
	// ヘッダー部画面リンク
	//------------------------------------------------------------------------------------------
	/** 日報(TOP)画面リンク */
	public static final String A_NIPPOU = "#name1";
	/** 週報画面リンク */
	public static final String A_SHUHOU = "#name2";
	/** 週報(計画)画面リンク */
	public static final String A_SHUHOU_KEIKAKU = "#name2_0";
	/** 週報(実績)画面リンク */
	public static final String A_SHUHOU_JISSEKI = "#name2_1";
	/** 月報(TOP)画面リンク */
	public static final String A_GEPPOU = "#name3";
	
	//------------------------------------------------------------------------------------------
	// 日報 
	//------------------------------------------------------------------------------------------
	/** 日付 */
	public static final String TXT_KINMU_DATE = "#kinmu_date";
	/** 出勤時間(時) */
	public static final String SELECT_SHUKKIN_TIME_HOUR = "#shukkin_time_hour";
	/** 出勤時間(分) */
	public static final String SELECT_SHUKKIN_TIME_MIN = "#shukkin_time_min";
	/** 退勤時間(時) */
	public static final String SELECT_TAIKIN_TIME_HOUR = "#taikin_time_hour";
	/** 退勤時間(分) */
	public static final String SELECT_TAIKIN_TIME_MIN = "#taikin_time_min";
	/** 勤務種別  */
	public static final String SELECT_KINMU_SHUBETSU = "#kinmu_shubetsu_view";
	
	//------------------------------------------------------------------------------------------
	// 週報計画
	//------------------------------------------------------------------------------------------
	/** 週報計画(一覧) 開始日 */
	public static final String[] SHUUHOU_KEIKAKU_LIST_START_DATE = {".dctp_ichiran_table .ichiran_tr_data", " td:nth-child(0)"};
	/** 週報計画(一覧) 月報S(ステータス) */
	public static final String[] SHUUHOU_KEIKAKU_LIST_STATUS = {".dctp_ichiran_table .ichiran_tr_data", " td:nth-child(5)"};
	
	//------------------------------------------------------------------------------------------
	// 週報実績 
	//------------------------------------------------------------------------------------------
	/** 作業開始日 */
	public static final String TXT_KETSUGOU_KINMU_DATE = "#ketsugou_kinmu_date";
	/** 評価 */
	public static final String SELECT_JISSEKI_HYOUKA = "#jisseki_hyouka";
	/** 評価理由 */
	public static final String TXT_HYUOUKA_RIYUU = "#hyouka_riyuu";
	
	//------------------------------------------------------------------------------------------
	// 作業一覧計画/実績 (日報,週報計画 共通)
	//------------------------------------------------------------------------------------------
	/** 追加 */
	public static final String BTN_ADD = "#addLineBtn";
	/** 部門名 */
	public static final String[] SELECT_BUMON_NAME = {"#sagyou_" , "_view_bumon_name_view"};
	/** PJ名 */
	public static final String[] SELECT_PROJECT_NAME = {"#sagyou_" , "_view_project_name_view"};
	/** 工程 */
	public static final String[] SELECT_PJ_KOUTEI = {"#sagyou_" , "_view_pj_koutei_view"};
	/** 作業時間(時) */
	public static final String[] SELECT_SAGYOU_TIME_HOUR = {"#sagyou_" , "_view_sagyou_time_hour_view"};
	/** 作業時間(分) */
	public static final String[] SELECT_SAGYOU_TIME_MIN = {"#sagyou_" , "_view_sagyou_time_min_view"};
	/** タスク */
	public static final String[] SELECT_TASK_ID = {"#sagyou_" , "_view_task_id_view"};
	/** 備考 */
	public static final String[] TXT_BIKOU = {"#sagyou_" , "_view_text_bikou"};
	
	//------------------------------------------------------------------------------------------
	// 週報計画/実績 共通) keikakum,jisseki
	//------------------------------------------------------------------------------------------
	/** 日付リンク */
	public static final String[] A_DATE_LINK = {"nippou_", "_view_kinmu_date", "_VIEW_LABEL"};

	//------------------------------------------------------------------------------------------
	// 作業一覧(計画),月報 共通
	//------------------------------------------------------------------------------------------
	/** 未作成ステータスリンク */
	public static final String[] A_STATUS_LINK = {"_status", "_VIEW_LABEL"};
	
	public static final String[] ITIRAN_TABLE_TD = {".dctp_ichiran_table .ichiran_tr_data", " .ichiran_01_mid_data"};
	
	//------------------------------------------------------------------------------------------
	// 月報
	//------------------------------------------------------------------------------------------
	/** アクション */
	public static final String SELECT_ACTIONS = "#actions";
	/** 月報(一覧) 年月 */
	public static final String[] GEPPOU_LIST_YYYYMM = {".dctp_ichiran_table .ichiran_tr_data", " td:nth-child(0)"};
	/** 月報(一覧) 月報S(ステータス) */
	public static final String[] GEPPOU_LIST_STATUS = {".dctp_ichiran_table .ichiran_tr_data", " td:nth-child(2)"};
	/** 実行*/
	public static final String BTN_ACTION = ".btn_action";
	
	//------------------------------------------------------------------------------------------
	// ボタン(共通)
	//------------------------------------------------------------------------------------------
	/** 新規作成 */
	public static final String BTN_CREATE_NEW = ".btn_size_07";
	/** 保存 */
	public static final String BTN_SAVE = ".dctp_ichiran_01_top_mid_header .btn_size_05";
	/** 登録 */
	public static final String BTN_REGISTER = ".dctp_ichiran_01_top_mid_header .btn_size_05:nth-child(2)";
	/** 申請 */
	public static final String BTN_APPLY = ".dctp_ichiran_01_top_mid_header .btn_size_05";
	/** 申請(週報実績) */
	public static final String BTN_APPLY_JISSEKI = ".dctp_ichiran_01_top_mid_header .btn_size_05:nth-child(2)";
	/** 勤務計画保存  */
	public static final String BTN_KINMU_KEIKAKU_SAVE = "#nippou_keikaku_view_dctp_recordset .dctp_ichiran_01_top_mid_header .btn_size_05";
	/** 月締 */
	public static final String BTN_TSUKIJIME = ".dctp_ichiran_01_top_mid_header .btn_size_05:nth-child(2)";
	
	/** ログアウト */
	public static final String BTN_LOGOUT = "#dctp_logout";
}
