package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class MainTest {

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		Engine e = new Engine();
		for(Class<? extends Shape> cls : e.getSupportedShapes()) {
			if (cls.getSimpleName().equals("Circle")) {
				try {
					Shape s = (Shape)cls.newInstance();
					obj.put("Shape", s);
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
		JSONWriter wr = new JSONWriter(obj);
		
		new JSONParser(wr, "C:\\Users\\Geek\\Desktop\\test.json");
	}

}
