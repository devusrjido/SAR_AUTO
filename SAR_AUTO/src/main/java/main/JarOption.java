package main;

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
	public static final String PROC_KIND = "proc_king";
	/** ヘルプ */
	public static final String HELP = "help";	
	
	/** オプション情報を格納 **/
	private Map<String,String> _option;
	
	/** 改行文字 **/
	private String _lineSep;
	
	/**
	 * @param args : jar起動時のコマンドライン引数の配列
	 */
	public JarOption(String[] args) {
		_option = new LinkedHashMap<String,String>();
		_lineSep = System.getProperty("line.separator");
		parseMap(args);
	}
	
	public String getSarUserName() {
		return _option.get(SAR_USER_NAME);
	}
	
	public String getSarPassword() {
		return _option.get(SAR_PASSOWORD);
	}
	
	public String getBlowserName() {
		return _option.get(BLOWSER_NAME);
	}
	
	public String getBlowserBinary() {
		return _option.get(BLOWSER_BINARY);
	}
	
	public String getRenkeiFileDirPath() {
		return _option.get(RENKEI_FILE_DIR);
	}
	
	public String getProcKind() {
		return _option.get(PROC_KIND);
	}
	
    public boolean containsHelp() {
    	return _option.containsKey(HELP);
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
	 * 標準出力へヘルプ情報を出力します。
	 */
    public void showHelp() {
    	String helpStr = "";
    	helpStr = "--shori-kbn         処理区分を指定します。" + _lineSep +
  			      "                    NR：日報(登録), SKR：週報計画(登録), SJR：週報実績(登録), GR：月締＆Excelエクスポート" + _lineSep +
    			  "--sar-user-name     SARのログインユーザ名を指定します。" + _lineSep +
    			  "--sar-password      SARのパスワードを指定します。" + _lineSep +
    			  "--blowser-name      実行環境のブラウザ名を指定します。" + _lineSep +
    			  "                    InternetExplorer, GoogleChrome, Firefox のいずれかを指定してください。" + _lineSep +
    			  "--blowser-binary    ブラウザのバイナリファイルを指定します。" + _lineSep +
    			  "--renkei-file-dir   連携ファイルの格納先フォルダを指定します。";
    	
    	System.out.println(helpStr);
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
