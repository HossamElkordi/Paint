package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class jsonFileReader {
    jasonMapToShapeArray toarr=new jasonMapToShapeArray();
    jsonFileToMap tomap;
    public ArrayList<Shape> output(String path) throws IOException {
        String k = null;
        FileReader fr =
                new FileReader(path);

        int i;
        while ((i=fr.read()) != -1) {
            k = k + ((char) i);
        }
        tomap=new jsonFileToMap(k);
        return toarr.output(tomap.getOutput());
        
    }
}
