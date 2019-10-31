package eg.edu.alexu.csd.oop.json;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JSONWriter {

	private String jsonString = "";
	private JSONObject obj;
	private static final ArrayList<String> types = new ArrayList<String>();

	public JSONWriter(JSONObject ojb) {
		this.obj = ojb;
		this.toSting();
		types.add("int"); types.add("Integer"); types.add("Double"); types.add("Long"); types.add("String");
		types.add("Character"); types.add("char"); types.add("Boolean"); types.add("Map"); types.add("Point");
		types.add("Color"); types.add("JSONArray");
	}

	public String getJsonString() {
		return jsonString;
	}

	@SuppressWarnings("unchecked")
	private void toSting() {
		HashMap<String, Object> map = this.obj.getMap();

		jsonString = "{";

		Set<?> set = map.entrySet();
		Iterator<?> i = set.iterator();

		while(i.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>)i.next();
			switch(me.getValue().getClass().getSimpleName()) {
				case "int":
				case "Long":
				case "Double":
				case "Integer":
					jsonString += "\n\t" + toJsonNUM(me.getKey(), me.getValue()) + ",";
					break;
				case "Boolean":
					jsonString += "\n\t" + toJsonBoolean(me.getKey(), me.getValue()) + ",";
					break;
				case "String":
					jsonString += "\n\t" + toJsonString(me.getKey(), me.getValue()) + ",";
					break;
				case "char":
				case "Character":
					jsonString += "\n\t" + toJsonCharacter(me.getKey(), me.getValue()) + ",";
					break;
				case "JSONArray":
					jsonString += "\n\t" + toJsonArray(me.getKey(), (JSONArray) me.getValue()) + ",";
					break;
				case "HashMap":
					jsonString += "\n\t" + me.getKey() + " : " + toJsonMap((HashMap<String, Object>)me.getValue()) + ",\n";
					break;
				default :
					jsonString += "\n\t" + toJsonObject(me.getValue()) + ",";
			}
		}

		jsonString = jsonString.substring(0, jsonString.length() - 1);

		jsonString += "\n}";
	}

	private String toJsonNUM(String key, Object value) {
		if(key.length() == 0) {
			return key;
		}
		return "" + key + " : " + value;
	}

	private String toJsonBoolean(String key, Object value) {
		if(key.length() == 0) {
			return key;
		}
		return "" + key + " : " + value;
	}

	private String toJsonString(String key, Object value) {
		if(key.length() == 0) {
			return "\"" + key + "\"";
		}
		return "" + key + " : \"" + value + "\"";
	}

	private String toJsonCharacter(String key, Object value) {
		if(key.length() == 0) {
			return "\'" + key + "\'";
		}
		return "" + key + " : \'" + value + "\'";
	}

	private String toJsonColor(Color color) {
		String s = "\""+ color.getClass().getName() +"\" : " + "{";

		s += "\n\t\t\t" + toJsonNUM("Red", color.getRed()) + ",";
		s += "\n\t\t\t" + toJsonNUM("Green", color.getGreen()) + ",";
		s += "\n\t\t\t" + toJsonNUM("Blue", color.getBlue()) + ",";
		s += "\n\t\t\t" + toJsonNUM("Alpha", color.getAlpha()) + ",";

		s = s.substring(0, s.length() - 1);
		s += "\n\t\t}";
		return s;
	}

	@SuppressWarnings("unchecked")
	private String toJsonMap(HashMap<String, Object> m) {
		String s = "\"" + m.getClass().getName() +"\" : " + "{";;

		Set<?> set = m.entrySet();
		Iterator<?> i = set.iterator();
		while(i.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>)i.next();
			switch(me.getValue().getClass().getSimpleName()) {
				case "int":
				case "Long":
				case "Double":
				case "Integer":
					s += "\n\t\t\t" + toJsonNUM(me.getKey(), me.getValue()) + ",";
					break;
				case "Boolean":
					s += "\n\t\t\t" + toJsonBoolean(me.getKey(), me.getValue()) + ",";
					break;
				case "String":
					s += "\n\t\t\t" + toJsonString(me.getKey(), me.getValue()) + ",";
					break;
				case "char":
				case "Character":
					s += "\n\t\t\t" + toJsonCharacter(me.getKey(), me.getValue()) + ",";
					break;
				default :
					s += "\n\t\t\t" + toJsonObject(me.getValue()) + ",";
			}
		}

		if(s.charAt(s.length() - 1) != '{') {
			s = s.substring(0, s.length() - 1);
		}
		s += "\n\t\t}";
		return s;
	}

	private String toJsonArray(String key,JSONArray value) {
		String s = "" + key + " : [";
		ArrayList<Object> list = value.getList();
		for(int i = 0; i < list.size(); i++) {
			switch(list.get(i).getClass().getSimpleName()) {
				case "int":
				case "Long":
				case "Double":
				case "Integer":
					s += "\n\t\t\t" + toJsonNUM("", list.get(i)) + ",";
					break;
				case "Boolean":
					s += "\n\t\t\t" + toJsonBoolean("", list.get(i)) + ",";
					break;
				case "String":
					s += "\n\t\t\t" + toJsonString("", list.get(i)) + ",";
					break;
				case "char":
				case "Character":
					s += "\n\t\t\t" + toJsonCharacter("", list.get(i)) + ",";
					break;
				default :
					s += "\n\t\t" + toJsonObject(list.get(i)) + ",";
			}
		}
		if(s.charAt(s.length() - 1) != '[') {
			s = s.substring(0, s.length() - 1);
		}
		s += "\n\t]";
		return s;
	}

	@SuppressWarnings("unchecked")
	private String toJsonObject(Object value) {
		String s = "\"" + value.getClass().getName() + "\" : " + "{\n";

		Class<?> cls = value.getClass();
		Field[] fields = cls.getDeclaredFields();
		if(fields.length == 0) {
			fields = cls.getSuperclass().getDeclaredFields();
		}

		try {
			for(Field f : fields) {
				f.setAccessible(true);
				switch(f.getType().getSimpleName()) {
					case "int":
					case "Long":
					case "Double":
					case "Integer":
						s += "\t\t" + f.getName() + " : " + toJsonNUM(f.getName(), f.get(value)) + ",\n";
						break;
					case "Boolean":
						s += "\t\t" + f.getName() + " : " + toJsonBoolean(f.getName(), f.get(value)) + ",\n";
						break;
					case "String":
						s += "\t\t" + f.getName() + " : " + toJsonString(f.getName(), f.get(value)) + ",\n";
						break;
					case "char":
					case "Character":
						s += "\t\t" + f.getName() + " : " + toJsonCharacter(f.getName(), f.get(value)) + ",\n";
						break;
					case "Color":
						s += "\t\t" + f.getName() + " : " + toJsonColor((Color) f.get(value)) + ",\n";
						break;
					case "Map":
						s += "\t\t" + f.getName() + " : " + toJsonMap((HashMap<String, Object>) f.get(value)) + ",\n";
						break;
					default:
						if(types.contains(f.getType().getSimpleName())) {
							s += "\t\t" + f.getName() + " : " + toJsonObject(f.get(value)) + ",\n";
						}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		s = s.substring(0, s.length() - 2);

		s += "\n\t}";
		return s;
	}

}