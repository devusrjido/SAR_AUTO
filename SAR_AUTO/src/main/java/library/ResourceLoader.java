package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

/** リソース管理クラス  **/
public class ResourceLoader {
	/** デフォルトファイルエンコード */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/** アプリケーションルート */
	private static String _appRoot;
	/** exeファイルフォルダ*/
	private static String _exeDir;
	/** 連携ファイルフォルダ*/
	private static String _renkeiFileDir;	
	
	private static ClassLoader _loader;
	
	static {
		_loader = ClassLoader.getSystemClassLoader();
		URL url = _loader.getResource("");

    	_appRoot = new File(url.getPath()).getParentFile().getPath() + File.separator + "package" + File.separator;
    	System.out.println("appRoot:" + _appRoot);
    	
    	_exeDir = _appRoot + "library" + File.separator + "exe"  + File.separator;
    	System.out.println("_exeDir:" + _exeDir);
	}
	
	/**
	 * リソースフォルダ内のexeファイル格納先フォルダパスを返却します。
	 * @return exeファイル格納先フォルダパス
	 */
    public static String getExeDirPath() {
    	return _exeDir;
    }

	/**
	 * リソースフォルダ内のpropertiesファイルを読込み、そのオブジェクトを返却します。
	 * @param プロパティファイル名
	 * @return プロパティオブジェクト
	 */
    public static Properties getProperties(String resourceName) {
    	return _getProperties(resourceName, DEFAULT_ENCODING);
    }

	/**
	 * リソースフォルダ内のpropertiesファイルを読込み、そのオブジェクトを返却します。
	 * @param プロパティファイル名
	 * @param 文字エンコード
	 * @return プロパティオブジェクト
	 */
    public static Properties getProperties(String resourceName, String encoding) {
    	return _getProperties(resourceName, encoding);
    }
    
    private static Properties _getProperties(String resourceName, String encoding) {
    	InputStream is = _loader.getResourceAsStream("resources/" + resourceName);
        BufferedReader reader = null;
    	Properties prop = new Properties();
    	
		try {
			reader = new BufferedReader(new InputStreamReader(is, encoding));
			prop.load(reader);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
    	return prop;
    }
}
