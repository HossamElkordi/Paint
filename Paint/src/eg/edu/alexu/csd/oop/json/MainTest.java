package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Circle;
import eg.edu.alexu.csd.oop.draw.Rectangle;

public class MainTest {

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		Circle c = new Circle();
		Rectangle r = new Rectangle();
		JSONArray arr = new JSONArray();
		arr.add(c);
		arr.add(r);
		obj.put("Shapes", arr);
//		obj.put("circle",c);
//		obj.put("rectangle",r);
		
		JSONWriter wr = new JSONWriter(obj);
		System.out.println(wr.getJsonString());
//		new JSONParser(wr, "C:\\Users\\Geek\\Desktop\\test.json");
	}

}
