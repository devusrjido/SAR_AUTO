package main;

/** 処理区分定義 **/
public class ShoriKbn {
	private ShoriKbn(){}
	
	/** 処理区分 週報計画(登録) */
	public static final String SHUUHOU_KEIKAKU_REGISTER = "SKR";
	/** 処理区分 週報実績(登録) */
	public static final String SHUUHOU_JISSEKI_REGISTER = "SJR";
	/** 処理区分 日報(登録) */
	public static final String NIPPOU_REGISTER = "NR";
	/** 処理区分 月締＆月報エクスポート */
	public static final String GEPPOU_REGISTER = "GR";
	/** 処理区分リスト */
	public static final String[] SHORI_KBN_LIST_DEFINE = {
									SHUUHOU_KEIKAKU_REGISTER,
									SHUUHOU_JISSEKI_REGISTER,
									NIPPOU_REGISTER,
									GEPPOU_REGISTER};
	
}
