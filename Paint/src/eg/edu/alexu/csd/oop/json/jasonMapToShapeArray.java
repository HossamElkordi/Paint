package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Circle;
import eg.edu.alexu.csd.oop.draw.LineSegment;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class jasonMapToShapeArray {
    ArrayList<Shape> output=new ArrayList<>();
    Map<String,Object> input=new HashMap<>();
    ArrayList<Class>supported=new ArrayList<>();

    public ArrayList<Shape> output( Map<String,Object> input) {
        Map<String,Object> temp=new HashMap<>();
        String type;
        Class chosentype = null;
        input= (Map<String, Object>) input.get("value");
        int i=0;
        while(input.containsKey(Integer.toString(i))){
            temp= (Map<String, Object>) input.get(Integer.toString(i));
            type= (String) temp.get("type");
            for(int j=0;j<supported.size();j++){
                if(supported.get(j).getName()==type){
                    chosentype=supported.get(j);
                }
            }
            Shape ins=null;
            try {if(chosentype!=null){
                ins= (Shape) chosentype.getDeclaredConstructor().newInstance();}
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if(ins!=null){
                ins.setProperties((Map<String, Double>) temp.get("properties"));
                ins.setFillColor(new Color(
                        ((Map<String, Double>)temp.get("fillcolor")).get("Red").intValue(),
                        ((Map<String, Double>)temp.get("fillcolor")).get("Green").intValue(),
                        ((Map<String, Double>)temp.get("fillcolor")).get("Blue").intValue(),
                        ((Map<String, Double>)temp.get("fillcolor")).get("Alpha").intValue())
                );
                ins.setColor(new Color(
                        ((Map<String, Double>)temp.get("strokecolor")).get("Red").intValue(),
                        ((Map<String, Double>)temp.get("strokecolor")).get("Green").intValue(),
                        ((Map<String, Double>)temp.get("strokecolor")).get("Blue").intValue(),
                        ((Map<String, Double>)temp.get("strokecolor")).get("Alpha").intValue())
                );

            }
            output.add(ins);

        }
        return output;
    }


}

