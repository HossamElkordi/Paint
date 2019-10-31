package eg.edu.alexu.csd.oop.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class jsonFileToMap {
    Map<String,Object> output;
    String input;
    String typeString;


    //Stack temp=new Stack<Character>();


    public jsonFileToMap(String input) {
        output=new HashMap();
        this.input=input;
        this.stringIterator();

    }


    public void stringIterator(){
        int i=1;
        String nameString=new String();
        Map<String,Object> tempMap=new HashMap<>();
        while(i<input.length()){

                if(input.charAt(i)=='['){
                    tempMap=layer0(input.substring(i+1,nextcloser(input,i)-1));
                    output.put("type","array");
                    output.put("value",tempMap);
                    output.put("name",nameString);
                    i=nextcloser(input,i)-1;
                }
                else if(Character.isLetter(input.charAt(i))){
                nameString=new String();
                while(Character.isLetter(input.charAt(i))||Character.isDigit(input.charAt(i))){
                    nameString=nameString+input.charAt(i);
                    i++;
                }

            }

                else{i++;}

        }
    }


    public Map<String, Object> getOutput() {
        return output;
    }


    private int nextcloser(String s,int start){
        int skipNumbers=0;
        if(s.charAt(start)=='['){start++;
            while(start<s.length()){
                if(skipNumbers==0&&s.charAt(start)==']'){return start;}
                else if(s.charAt(start)=='['){skipNumbers++;}
                else if(s.charAt(start)==']'){skipNumbers--;}
                start++;
            }
        }


        if(s.charAt(start)=='{'){start++;
            while(start<s.length()){
                if(skipNumbers==0&&s.charAt(start)=='}'){return start;}
                else if(s.charAt(start)=='{'){skipNumbers++;}
                else if(s.charAt(start)=='}'){skipNumbers--;}
                start++;
            }
        }
        return 0;
    }
    public Map<String,Object> layer0(String input){
        int i=1;int counter=0;
        Map<String,Object> output=new HashMap<>();
        while(i<input.length()) {
            if(input.charAt(i)=='"'){
                i++;
                typeString=new String();
                while(input.charAt(i)!='"'){
                    typeString+=input.charAt(i);i++;
                }
                i++;
            }
            else if(input.charAt(i)=='{'){
                output.put(Integer.toString(counter),layer1(input.substring(i+1,nextcloser(input,i)-1),typeString));
                counter++;
                i=nextcloser(input,i)-1;

            }
            else{i++;}

        }

        return output;

    }

    public Map<String,Object> layer1(String input,String type){
        Map<String,Object> output=new HashMap<>();
        String nameString=new String(),typeString=new String();;
        output.put("type",type);
        int i=0;
        while(i<input.length()) {
            if(Character.isLetter(input.charAt(i))){
                nameString=new String();
                while(Character.isLetter(input.charAt(i))||Character.isDigit(input.charAt(i))){
                    nameString=nameString+input.charAt(i);
                    i++;
                }

            }
            else if(input.charAt(i)=='"'){i++;typeString=new String();
                while(input.charAt(i)!='"'){
                    typeString+=input.charAt(i);i++;
                }
                i++;
            }


            if(input.charAt(i)=='{'){
                output.put(nameString,layer2(input.substring(i+1,nextcloser(input,i)-1),typeString));
                i=nextcloser(input,i)-1;

            }
            else{i++;}

        }

        return output;

    }
    public Map<String,Object> layer2(String input,String type){
        Map<String,Object> output=new HashMap<>();
        String nameString=new String(),valueString=new String();
        output.put("type",type);
        int i=0;
        while(i<input.length()) {
            if(Character.isLetter(input.charAt(i))){
                nameString=new String();
                while(Character.isLetter(input.charAt(i))||Character.isDigit(input.charAt(i))){
                    nameString=nameString+input.charAt(i);
                    i++;
                }

            }
            else if(Character.isDigit(input.charAt(i))){
                valueString=new String();
                while(Character.isDigit(input.charAt(i))||input.charAt(i)=='.'){
                    valueString=valueString+input.charAt(i);
                    i++;
                }
               output.put(nameString,Double.parseDouble(valueString));
            }

            else{i++;}

        }

        return output;

    }

}
