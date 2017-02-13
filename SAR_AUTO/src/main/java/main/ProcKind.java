package main;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/** 処理区分定義 **/
public class ProcKind {
	private ProcKind(){}
	
	/** 処理区分 週報計画(登録) */
	public static final String SHUUHOU_KEIKAKU_REGISTER = "SKR";
	/** 処理区分 週報実績(登録) */
	public static final String SHUUHOU_JISSEKI_REGISTER = "SJR";
	/** 処理区分 日報(登録) */
	public static final String NIPPOU_REGISTER = "NR";
	/** 処理区分 月締＆月報エクスポート */
	public static final String GEPPOU_REGISTER = "GR";
	
	/** 処理区分リスト 
	 *  定義追加時は最適なルーティング順で定義して下さい。 */
	public static final String[] PROC_KIND_LIST = {
									NIPPOU_REGISTER,
									SHUUHOU_JISSEKI_REGISTER,
									SHUUHOU_KEIKAKU_REGISTER,
									GEPPOU_REGISTER};
	
	/**
	 * 指定された処理区分の中で存在しない処理区分が含まれていないかチェックします。
	 * @param procKind : 引数の処理区分
	 * @return 含む(true), 含まない(false)
	 */
	public static boolean invalidProcKind(String procKind) {
		if (StringUtils.isEmpty(procKind)) {
			System.out.println("処理区分が指定されていません。");
			return false;
		}
		
		String[] iProcKindList = procKind.split(",");
		if (iProcKindList.length == 0) {
			System.out.println("処理区分が指定されていません。");
			return false;
		}
		
		for (String iPk : iProcKindList) {
			for (String pk : PROC_KIND_LIST){
				if(iPk.equals(pk)) {
					return true;
				}
			}
		}
		
		System.out.println("処理区分の値が不正です。");
		return false;
	}
	
	/**
	 * 処理区分をListに変換し、返却します.<br>
	 * ※処理順については登録順番を考慮し、下記順番に並べ替えます.<br>
	 *　   日報(登録) => 週報実績(登録) => 週報計画(登録) => 月報(登録)
	 * 
	 * @param procKinds 処理区分(カンマ区切り)
	 * @return 整列された処理区分リスト
	 */
	public static List<String> splitProcKindToList(String procKinds) {
		String procKindList[] = procKinds.split(",");
		List<String> optProcKindList = new LinkedList<String>();
		
		for(String pk : PROC_KIND_LIST) {
			for(String iPk : procKindList) {
				if(pk.equals(iPk)) {
					optProcKindList.add(pk);
				}
			}
		}
		
		return optProcKindList;
	}
}
