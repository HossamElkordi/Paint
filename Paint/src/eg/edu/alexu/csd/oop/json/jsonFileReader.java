package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class jsonFileReader {
    jasonMapToShapeArray toarr=new jasonMapToShapeArray();
    jsonFileToMap tomap;
    public ArrayList<Shape> output(String path, List<Class<? extends Shape>> cls) throws IOException {
        String k = "";
        FileReader fr =
                new FileReader(path);

        int i;
        while ((i=fr.read()) != -1) {
            k = k + ((char) i);
        }
        tomap=new jsonFileToMap(k);
        toarr.supported=cls;
        return toarr.output(tomap.getOutput());
        
    }
}
