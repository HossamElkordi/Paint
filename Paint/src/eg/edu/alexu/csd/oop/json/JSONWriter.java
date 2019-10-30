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

	private void toSting() {
		HashMap<String, Object> map = this.obj.getMap();
		
		jsonString = "{";
		
		Set<?> set = map.entrySet();
		Iterator<?> i = set.iterator();
		
		while(i.hasNext()) {
			@SuppressWarnings("unchecked")
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
				default :
					jsonString += "\n\t" + toJsonObject(me.getValue()) + ",";
			}
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "}";
	}
	
	private String toJsonNUM(String key, Object value) {
		return "\"" + key + "\" : " + value;
	}
	
	private String toJsonBoolean(String key, Object value) {
		return "\"" + key + "\" : " + value;
	}
	
	private String toJsonString(String key, Object value) {
		return "\"" + key + "\" : \"" + value + "\"";
	}
	
	private String toJsonCharacter(String key, Object value) {
		return "\"" + key + "\" : \'" + value + "\'";
	}
	
	private String toJsonColor(Color color) {
		String s = "\""+ color.getClass().getName() +"\" : " + "{";
		
		s += "\n\t\t" + toJsonNUM("Red", color.getRed()) + ",";
		s += "\n\t\t" + toJsonNUM("Green", color.getGreen()) + ",";
		s += "\n\t\t" + toJsonNUM("Blue", color.getBlue()) + ",";
		s += "\n\t\t" + toJsonNUM("Alpha", color.getAlpha()) + ",";
		
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
					s += "\n\t\t" + toJsonNUM(me.getKey(), me.getValue()) + ",";
					break;
				case "Boolean":
					s += "\n\t\t" + toJsonBoolean(me.getKey(), me.getValue()) + ",";
					break;
				case "String":
					s += "\n\t\t" + toJsonString(me.getKey(), me.getValue()) + ",";
					break;
				case "char":
				case "Character":
					s += "\n\t\t" + toJsonCharacter(me.getKey(), me.getValue()) + ",";
					break;
				default :
					s += "\n\t\t" + toJsonObject(me.getValue()) + ",";
		}
			
			
		}
		
		s = s.substring(0, s.length() - 1);
		s += "\n\t\t}";
		return s;
	}
	
	@SuppressWarnings("unchecked")
	private String toJsonObject(Object value) {
		String s = "\"" + value.getClass().getName() + "\" : " + "{\n\t";
		
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
						s += "\t" + f.getName() + " : " + toJsonNUM(f.getName(), f.get(value)) + ",\n";
						break;
					case "Boolean":
						s += "\t" + f.getName() + " : " + toJsonBoolean(f.getName(), f.get(value)) + ",\n";
						break;
					case "String":
						s += "\t" + f.getName() + " : " + toJsonString(f.getName(), f.get(value)) + ",\n";
						break;
					case "char":	
					case "Character":
						s += "\t" + f.getName() + " : " + toJsonCharacter(f.getName(), f.get(value)) + ",\n";
						break;
					case "Color":
						s += "\t" + f.getName() + " : " + toJsonColor((Color) f.get(value)) + ",\n";
						break;	
					case "Map":
						s += "\t" + f.getName() + " : " + toJsonMap((HashMap<String, Object>) f.get(value)) + ",\n";
						break;
					default:
						if(types.contains(f.getType().getSimpleName())) {
							s += "\t" + f.getName() + " : " + toJsonObject(f.get(value)) + ",\n";
						}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		s = s.substring(0, s.length() - 1);
		
		s += "\n\t}\n";
		return s;
	}

}
