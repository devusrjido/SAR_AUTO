package scenario.data;

import java.util.HashMap;
import java.util.Map;

/** シナリオ間でのデータ保持クラス */
public class ScenarioDataHolder {
	public static Map<String,Object> _dataHolder;
	
	private ScenarioDataHolder() {
		if (_dataHolder == null) {
			_dataHolder = new HashMap<String,Object>();
		}
	}
	
	/**
	 * シナリオ間でのデータ保持クラスを返却する
	 * @return データ保持クラス
	 */
	public static ScenarioDataHolder getInstance() {
		return new ScenarioDataHolder();
	}
	
	public Object getObject(String key) {
		return _dataHolder.get(key);
	}

	public String getString(String key) {
		checkValueType(_dataHolder.get(key),String.class,"variable type mismatches java.lang.String" + " key:" + key);
		return (String)_dataHolder.get(key);
	}
	
	public int getInt(String key) {
		checkValueType(_dataHolder.get(key),Integer.class,"variable type mismatches java.lang.Integer" + " key:" + key);
		return (Integer)_dataHolder.get(key);
	}
	
	public short getShort(String key) {
		checkValueType(_dataHolder.get(key),Short.class,"variable type mismatches java.lang.Short" + " key:" + key);
		return (Short)_dataHolder.get(key);
	}
	
	public boolean getBoolean(String key) {
		checkValueType(_dataHolder.get(key),Boolean.class,"variable type mismatches java.lang.Boolean" + " key:" + key);
		return (Boolean)_dataHolder.get(key);
	}
	
	public ScenarioDataHolder setValue(String key, Object value) {
		_dataHolder.put(key, value);
		return this;
	}
	
	public String toString() {
		return _dataHolder.toString();
	}
	
	public <T> void checkValueType(Object obj, Class<T> cl, String message) {
		if(!(obj.getClass() == cl)) {
			throw  new IllegalStateException(message);
		}
	}
}
