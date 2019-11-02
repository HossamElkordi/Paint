package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class MainTest {

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		Engine e = new Engine();
		for(Class<? extends Shape> cls : e.getSupportedShapes()) {
			if (cls.getSimpleName().equals("Rectangle")) {
				try {
					Shape s = (Shape)cls.newInstance();
					arr.add(s);
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
		obj.put("array", arr);
		JSONWriter wr = new JSONWriter(obj);
		System.out.println(wr.getJsonString());
//		new JSONParser(wr, "C:\\Users\\Geek\\Desktop\\test.json");
	}

}
