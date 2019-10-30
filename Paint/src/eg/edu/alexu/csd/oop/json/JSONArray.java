package eg.edu.alexu.csd.oop.json;

import java.util.ArrayList;

public class JSONArray{
	
	private ArrayList<Object> list;
	
	public JSONArray() {
		list = new ArrayList<Object>();
	}

	public void add(Object e) {
		list.add(e);
	}

	public ArrayList<Object> getList() {
		return list;
	}
		
}
