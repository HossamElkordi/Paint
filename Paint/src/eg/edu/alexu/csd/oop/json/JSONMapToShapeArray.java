package eg.edu.alexu.csd.oop.json;

import eg.edu.alexu.csd.oop.draw.Circle;
import eg.edu.alexu.csd.oop.draw.LineSegment;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONMapToShapeArray {
    ArrayList<Shape> output=new ArrayList<>();
    Map<String,Object> input=new HashMap<>();
    List<Class<? extends Shape>> supported=new ArrayList<>();

    public ArrayList<Shape> output( Map<String,Object> input) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String,Object> temp=new HashMap<>();
        String Type;
        Class chosentype = null;
        input= (Map<String, Object>) input.get("value");
        int i=0;
        while(input.containsKey(Integer.toString(i))){
            temp= (Map<String, Object>) input.get(Integer.toString(i));
            Type= (String) temp.get("Type");
            for(int j=0;j<supported.size();j++){
                if(supported.get(j).getName().equals(Type)){
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
            	if(temp != null) {
                    Field[] f;
                    String x;
                    Map<String,Object> map;
                    if(chosentype.getDeclaredFields().length!=0){
                        f=chosentype.getDeclaredFields();
                    }
                    else{
                        f=chosentype.getSuperclass().getDeclaredFields();
                    }
                    for(int j=0;j<f.length;j++) {
                        x = f[j].getName();
                        map = (Map<String, Object>) temp.get(x);
                        //if(map.containsKey("Type"))
                        Class c = Class.forName((String) map.get("Type"));//point,map,color,or else
                        Object obj;
                        if(((String) map.get("Type")).equals("java.util.Map")){obj=new Object();}
                        else if(((String) map.get("Type")).equals("java.awt.Color")){obj=new Color(0);}
                        else {obj = c.getDeclaredConstructor().newInstance();}
                        if (((String) map.get("Type")).equals("java.awt.Color")) {
                            if (map.containsKey("Red") && map.containsKey("Blue") && map.containsKey("Green") && map.containsKey("Alpha")) {
                                obj = new Color(
                                        ((Map<String, Double>) temp.get(x)).get("Red").intValue(),
                                        ((Map<String, Double>) temp.get(x)).get("Green").intValue(),
                                        ((Map<String, Double>) temp.get(x)).get("Blue").intValue(),
                                        ((Map<String, Double>) temp.get(x)).get("Alpha").intValue()
                                );

                            }
                            else{
                                Color q=null;
                                obj=q;
                            }
                            f[j].setAccessible(true);
                            f[j].set(ins, obj);
                        } else if (((String) map.get("Type")).equals("java.util.Map")) {
                            map.remove("Type");
                            obj = map;
                            f[j].setAccessible(true);
                            f[j].set(ins, obj);
                        } else {
                            Field[] subf = c.getDeclaredFields();
                            for (int k = 0; k < subf.length; k++) {
                                if (map.containsKey(getNeededSubstring(subf[k].toString()))) {
                                    if (getType(subf[k].toString()).equals("int")) {
                                        int out = Integer.parseInt((String) map.get(getNeededSubstring(subf[k].toString())));
                                        subf[k].setAccessible(true);
                                        subf[k].set(obj, out);

                                    } else if (getType(subf[k].toString()).equals("double")) {
                                        double out = Double.parseDouble((String) map.get(getNeededSubstring(subf[k].toString())));
                                        subf[k].setAccessible(true);
                                        subf[k].set(obj, out);

                                    }

                                }
                                if (map.size()<2){c.cast(obj);obj=null;}
                            }
                            f[j].setAccessible(true);
                            f[j].set(ins, obj);

                        }
                        /////
                    }
            	}


            	    /*
            		ins.setProperties((Map<String, Double>) temp.get("properties"));
                    ins.setFillColor(new Color(
                            ((Map<String, Double>)temp.get("fillColor")).get("Red").intValue(),
                            ((Map<String, Double>)temp.get("fillColor")).get("Green").intValue(),
                            ((Map<String, Double>)temp.get("fillColor")).get("Blue").intValue(),
                            ((Map<String, Double>)temp.get("fillColor")).get("Alpha").intValue())
                    );
                    ins.setColor(new Color(
                            ((Map<String, Double>)temp.get("strokeColor")).get("Red").intValue(),
                            ((Map<String, Double>)temp.get("strokeColor")).get("Green").intValue(),
                            ((Map<String, Double>)temp.get("strokeColor")).get("Blue").intValue(),
                            ((Map<String, Double>)temp.get("strokeColor")).get("Alpha").intValue())
                    );*/

            }
                


            output.add(ins);
            i++;
        }
        return output;
    }

    private String getNeededSubstring(String in){
        if(in.length()==0){return in;}

        int i=in.length()-1;
        while(in.charAt(i)!='.'){
            i--;

        }
        return in.substring(i);

    }
    public String getType(String in){
        if(in.length()==0){return in;}
        int i=0,j=0,spaceCount=0;
        while(spaceCount!=2){
           if(spaceCount==0){
               i++;
               j++;
           }
           else {j++;}
           if(in.charAt(j)==' '){spaceCount++;}

        }
        return in.substring(i+1,j);
    }


}

