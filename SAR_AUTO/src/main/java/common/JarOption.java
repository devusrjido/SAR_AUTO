package common;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/** jar起動時のオプション **/
public class JarOption {
	// jar実行時のoptionキー定義
	/** SAR ユーザ名 */
	public static final String SAR_USER_NAME = "sar-user-name";
	/** SAR パスワード */
	public static final String SAR_PASSOWORD = "sar-password";
	/** 実行するブラウザ名 */
	public static final String BLOWSER_NAME = "blowser-name";
	/** ブラウザのバイナリファイル */
	public static final String BLOWSER_BINARY = "blowser-binary";
	/** 連携フォルダ格納先フォルダ */
	public static final String RENKEI_FILE_DIR = "renkei-file-dir";
	/** 処理区分 */
	public static final String SHORI_KBN = "shori-kbn";
	/** ヘルプ */
	public static final String HELP = "help";	
	
	private Map<String,String> _option;
	private String _sep;
	
	/**
	 * @param args : jar起動時のコマンドライン引数の配列
	 */
	public JarOption(String[] args) {
		_option = new LinkedHashMap<String,String>();
		_sep = System.getProperty("line.separator");
		parseMap(args);
	}
	
	/**
	 * 指定されたキーに該当するjarオプションの文字列を返却します。
	 * @param key : jarオプションキー
	 * @return オプション文字列
	 */
	public String getValue(String key) {
		return _option.get(key);
	}
	
    private void parseMap(String[] args) {
    	for (String arg : args) {
    		if(!checkArg(arg)) {
    			throw new IllegalArgumentException("jarオプションのフォーマットエラーです。" + Arrays.toString(args));
    		}
    		
    		int posi = arg.indexOf("=");
    		String key = arg.substring(2);
    		String value = "";
    		if(posi != -1) {
    			key = arg.substring(2,posi);
    			value = arg.substring(posi + 1);;
    		}
    		
    		_option.put(key, value);
    	}
    }
    
    /**
     * jar起動時のコマンドライン引数をチェックし、フォーマットが正しい場合、trueを返却します。
     * @param arg : jar起動時のコマンドライン引数
     * @return フォーマットが正しい(true),誤り(false)
     */
    private boolean checkArg(String arg) {
		if (arg == null || arg.trim().equals("")) return false;
		// 「--」,「option」で最低3文字は必須
		if (arg.length() < 3) return false;
		// 3文字目が =
		if (arg.charAt(2) == '=') return false;
		// 先頭2文字が -- 以外
		if (!arg.substring(0,2).equals("--")) return false;
		
		return true;
    }
    
	/**
	 * 標準出力先へヘルプ情報を出力します。
	 */
    public void showHelp() {
    	String helpStr = "";
    	helpStr = "--shori-kbn         処理区分を指定します。" + _sep +
  			      "                    NR：日報(登録), SKR：週報計画(登録), SJR：週報実績(登録), GR：月締＆Excelエクスポート" + _sep +
    			  "--sar-user-name     SARのログインユーザ名を指定します。" + _sep +
    			  "--sar-password      SARのパスワードを指定します。" + _sep +
    			  "--blowser-name      実行環境のブラウザ名を指定します。" + _sep +
    			  "                    InternetExplorer, GoogleChrome, Firefox のいずれかを指定してください。" + _sep +
    			  "--blowser-binary    ブラウザのバイナリファイルを指定します。" + _sep +
    			  "--renkei-file-dir   連携ファイルの格納先フォルダを指定します。";
    	
    	System.out.println(helpStr);
    }

	/**
	 * 指定されたキーのデータが存在する場合、trueを返却します。
	 * @param key : jarオプションキー
	 * @return キーのデータが存在(true), 存在しない(false)
	 */
    public boolean contains(String key) {
    	return _option.containsKey(key);
    }
    
    /**
     * オプション内にある要素数を返却します。
     * @return 要素数
     */
    public int size() {   
    	return _option.size();
    }
    
    public String toString() {
    	return _option.toString();
    }
}
