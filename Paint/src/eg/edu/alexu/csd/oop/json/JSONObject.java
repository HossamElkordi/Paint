package eg.edu.alexu.csd.oop.json;

import java.util.HashMap;

	public class JSONObject {
		
	private HashMap<String, Object> map;
	
	public JSONObject() {
		map = new HashMap<String, Object>();
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}
	
	public HashMap<String, Object> getMap() {
		return map;
	}

}
