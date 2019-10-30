package eg.edu.alexu.csd.oop.json;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONParser {
	
	private JSONWriter writer;

	public JSONParser(JSONWriter writer, String path) {
		this.writer = writer;
		writeFile(path);
	}
	
	private void writeFile(String path) {
		File file = new File(path);
		try {
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			pw.println(writer.getJsonString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
}
