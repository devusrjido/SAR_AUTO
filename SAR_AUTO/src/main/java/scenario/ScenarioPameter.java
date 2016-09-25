package scenario;

import java.util.HashMap;
import java.util.Map;

/** シナリオ実行時に渡すパラメータクラス */
public class ScenarioPameter {
	Map<String,Object> _params;
	
	public ScenarioPameter(){
		_params = new HashMap<String,Object>();
	}
	
	public Object getObject(String key) {
		return _params.get(key);
	}

	public String getString(String key) {
		checkValueType(_params.get(key),String.class,"variable type mismatches java.lang.String" + " key:" + key);
		return (String)_params.get(key);
	}
	
	public int getInt(String key) {
		checkValueType(_params.get(key),Integer.class,"variable type mismatches java.lang.Integer" + " key:" + key);
		return (Integer)_params.get(key);
	}
	
	public short getShort(String key) {
		checkValueType(_params.get(key),Short.class,"variable type mismatches java.lang.Short" + " key:" + key);
		return (Short)_params.get(key);
	}
	
	public boolean getBoolean(String key) {
		checkValueType(_params.get(key),Boolean.class,"variable type mismatches java.lang.Boolean" + " key:" + key);
		return (Boolean)_params.get(key);
	}
	
	public ScenarioPameter setValue(String key, Object value) {
		_params.put(key, value);
		return this;
	}
	
	public String toString() {
		return _params.toString();
	}
	
	public <T> void checkValueType(Object obj, Class<T> cl, String message) {
		if(!(obj.getClass() == cl)) {
			throw  new IllegalStateException(message);
		}
	}
}
