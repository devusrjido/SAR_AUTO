package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.google.common.base.CaseFormat;

import library.ResourceLoader;

/** レコードファイル読込クラス */
public class FileRecordFactory {	
	/** ファイルパス定義 */
	private static final String FILE_NAME_PROPERTIES = "file_name.properties";

	/** ファイルインターフェース定義 */
	private static final String INTERFACE_PROPERTIES = "file_interface.properties";
	
	/** レコードの区切り文字 */
	private static final String RECORD_DELIMTIER = ":";
	private static final String INTERFACE_DELIMTIER = ",";
	
	private Properties fileNameProperties;
	private Properties interfaceProperties;
	private String renkeiFileDir;
	
	/**
	 * @param renkeiFileDir : 連携ファイルの格納先フォルダ
	 */
	public FileRecordFactory(String renkeiFileDir) {
    	this.renkeiFileDir = renkeiFileDir;
    	fileNameProperties = ResourceLoader.getProperties(FILE_NAME_PROPERTIES);
    	interfaceProperties = ResourceLoader.getProperties(INTERFACE_PROPERTIES);
	}
	
	/** 
	 * レコードファイルを読込み、そのリストを返却する
	 * @param fileId : ファイルID
	 * @param cls    : 生成するBeanのClassオブジェクト
	 * @return レコード一覧
	 */
	public <T> List<T> createRecordForList(String fileId, Class<T> cls) {
		List<T> recordList = new ArrayList<T>();
		T record = null;
	    BufferedReader br = null;
	    int lineNumber = 1;
	    
	    try{
		    String[] fileIf = interfaceProperties.getProperty(fileId).split(INTERFACE_DELIMTIER);
		    String filePath = renkeiFileDir + fileNameProperties.getProperty(fileId);
	    	br = new BufferedReader(new FileReader(filePath));
	    	
	    	System.out.println("# 読込ファイル : [" + filePath + "] ------------------------------------------------------");
	    	String buffer =  null;
	    	while ((buffer = br.readLine()) != null){
	    		// 空行は無視
	    		if ("".equals(buffer.trim())) continue;
	    		
	    		// レコードを配列に分解
	    		String[] tmpArr = splitString(buffer, RECORD_DELIMTIER);
	    		
	    		record = createRecord(fileIf, tmpArr, cls);
	    		recordList.add(record);
	    		lineNumber++;
	    		
	    		System.out.println(record.toString());
			}
	    	
    		System.out.println("--------------------------------------------------------------------------------------");
	    	
    		br.close();
	    } catch (FileNotFoundException fnfe) {
    		fnfe.printStackTrace();
    		return new ArrayList<T>();
	    } catch(IOException ioe) {
			System.out.println("ファイル読込中にエラーが発生しました。 行番号：" + lineNumber);
			ioe.printStackTrace();
			return new ArrayList<T>();
	    } catch(ArrayIndexOutOfBoundsException aie) {
			System.out.println("ファイルフォーマットエラー。カラム数が不正です。 行番号：" + lineNumber);
			aie.printStackTrace();
			return new ArrayList<T>(); 
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			return new ArrayList<T>(); 
		} catch(Exception ex) {
			System.out.println("リフレクションでの処理中にエラーが発生しました。");
			ex.printStackTrace();
			return new ArrayList<T>(); 
		} finally {
			if (br != null) {
				br = null;
			}
		}
	    
		return recordList;
	}
	
	/** 
	 * レコードファイルを読込み、そのレコードオブジェクトを返却する
	 * @param fileId : ファイルID
	 * @param cls    : 生成するBeanのClassオブジェクト
	 * @return レコードオブジェクト
	 */
	public <T> T createRecord(String fileId, Class<T> cls) {
		T record = null;
	    BufferedReader br = null;
	    
	    try{
		    String[] ifs = interfaceProperties.getProperty(fileId).split(INTERFACE_DELIMTIER);
		    String filePath = renkeiFileDir + fileNameProperties.getProperty(fileId);
	    	br = new BufferedReader(new FileReader(filePath));
		    
	    	System.out.println("# 読込ファイル : [" + filePath + "] ------------------------------------------------------");
	    	String buffer = br.readLine();
	    	
    		// 空行は無視
    		if ("".equals(buffer)) {
    			return null;
    		}
	    		
	    	// レコードを配列に分解
    		String[] tmpArr = splitString(buffer, RECORD_DELIMTIER); 		
	    	record = createRecord(ifs, tmpArr, cls);
	    	
	    	System.out.println(record.toString());
    		System.out.println("--------------------------------------------------------------------------------------");
	    	
    		br.close();
	    } catch (FileNotFoundException fnfe) {
    		fnfe.printStackTrace();
    		return null;
	    } catch(IOException ioe) {
			System.out.println("ファイル読込中にエラーが発生しました。");
			ioe.printStackTrace();
			return null;
	    } catch(ArrayIndexOutOfBoundsException aie) {
			System.out.println("ファイルフォーマットエラー。カラム数が不正です。");
			aie.printStackTrace();
			return null;
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			return null; 
		} catch(Exception ex) {
			System.out.println("リフレクション処理中にエラーが発生しました。");
			ex.printStackTrace();
			return null; 
		} finally {
			if (br != null) {
				br = null;
			}
		}
	    
		return record;
	}
	
	/**
	 * 文字列を区切り文字で区切った配列を返却する<br>
	 * (文字列の末尾が区切り文字の場合、配列の末尾に空文字が設定されます)
	 * @param buffer
	 * @param delimiter
	 * @return 文字列配列
	 */
	private String[] splitString(String buffer, String delimiter) {
		List<String> list = new LinkedList<String>();
		
		for(String b : buffer.split(delimiter)) {
			list.add(b);
		}
		
		// 末尾が区切り文字の場合、空文字を追加
		if(buffer.lastIndexOf(delimiter) == buffer.length() -1) {
			list.add("");
		}
		
		return list.toArray(new String[]{});
	}
	
	
	/**
	 * インターフェース定義を基にレコードのBeanオブジェクトを生成します。<br>
	 * (制約として、Beanの属性は全てjava.lang.String型にする必要があります。)
	 * @param fileIf : ファイルインターフェース配列
	 * @param arr    : レコード
	 * @param cls    : 生成するBeanのClassオブジェクト
	 * @return Beanオブジェクト
	 */
	private <T> T createRecord(String[] fileIf, String[] arr, Class<T> cls) 
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		T record = cls.newInstance();
		
		for(int i = 0; i < fileIf.length; i++){
			Method method = record.getClass().getDeclaredMethod(makeSetterMethodName(fileIf[i]), String.class);
			method.invoke(record, arr[i]);
		}
		
		return record;
	}
	
	/**
	 * 属性名からsetterメソッド名を生成する<br>
	 * (属性名はアッパーケース、ローキャメルケース、小文字アンダースコア区切り 形式のいずれかを指定してください)
	 * <pre>
	 * 例) FileRecord, fileRecord, file_record
	 *     上記はいずれも setFileRecord の形式に変換されます。
	 * </pre>    
	 * @param attributeName : 属性名
	 * @return setterメソッド名
	 */
	private static String makeSetterMethodName(String attributeName) {
		if(attributeName.indexOf("_") > 0) {
			return "set" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, attributeName);
		}
		
		return "set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, attributeName);
	}
}
