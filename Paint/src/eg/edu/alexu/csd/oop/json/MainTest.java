package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Circle;
import eg.edu.alexu.csd.oop.draw.Shape;

public class MainTest {

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		Circle c = new Circle();
		obj.put("sdf", c);
		
		JSONWriter wr = new JSONWriter(obj);
		new JSONParser(wr, "C:\\Users\\Geek\\Desktop\\test.json");
	}

}
