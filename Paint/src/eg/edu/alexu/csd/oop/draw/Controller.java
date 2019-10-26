package eg.edu.alexu.csd.oop.draw;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
 
public class Controller {
   
    private Engine engine;
    private Shape shape;
    private FreeDrawing f;
    private Point firstPoint;
    private Shape selectedShape;
    private int selectedX1,selectedX2,selectedY1,selectedY2,selectedX3,selectedY3,firstX,firstY,dummyX,dummyY,deltaX,deltaY,state=0;
    boolean resizeFlag=false,swichflag=false;
   
    public Controller() {
        engine = new Engine();
    }
 
    public void setFirts(Point p){
        firstX= (int) p.getX();firstY= (int) p.getY();
    }

 
    public void shapeCreator(char shapeChar, int x1, int y1, Color fillColor, Color strokeColor) {
        switch(shapeChar) {
            case 'l':
                shape = new LineSegment(x1, y1, fillColor, strokeColor);
                break;
            case 'r':
                shape = new Rectangle(x1, y1, fillColor, strokeColor);
                break;
            case 's':
                shape = new Square(x1, y1, fillColor, strokeColor);
                break;
            case 'e':
                shape = new Ellipse(x1, y1, fillColor, strokeColor);
                break;
            case 'c':
                shape = new Circle(x1, y1, fillColor, strokeColor);
                break;
            case 't':
                shape = new Triangle(x1, y1, fillColor, strokeColor);
                break;
            case 'f':
                f = new FreeDrawing(fillColor);
                f.addPoint(x1, y1);
                break;
        }
    }
 
    public void shapeDrawer(Graphics canvas, int x2, int y2) {
        if(f != null) {
            f.addPoint(x2, y2);
            f.draw(canvas);
            return;
        }
        if(shape != null) {
            shape.getProperties().put("x2", (double) x2);
            shape.getProperties().put("y2", (double) y2);
            if(shape.getClass().getSimpleName().equals("Triangle")) {
                shape.getProperties().put("y3", (double) y2);
            }
            shape.draw(canvas);
        }
 
    }
 
    public void shapeFinisher(char shapeChar, int x2, int y2) {
        if(f != null) {
            f.addPoint(x2, y2);
            engine.addShape(f);
            f = null;
            return;
        }
        shape.getProperties().put("x2", (double) x2);
        shape.getProperties().put("y2", (double) y2);
        int x1 = (int) Math.floor(shape.getProperties().get("x1"));
        int y1 = (int) Math.floor(shape.getProperties().get("y1"));
        switch(shapeChar) {
            case 'l':
                lineProp(x1, y1, x2, y2);
                break;
            case 'r':
                rectProp(x1, y1, x2, y2);
                break;
            case 's':
                sqrProp(x1, y1, x2, y2);
                break;
            case 'e':
                ellProp(x1, y1, x2, y2);
                break;
            case 'c':
                cirProp(x1, y1, x2, y2);
                break;
            case 't':
                trProp(x1, y1, x2, y2);
                break;
        }
        engine.addShape(shape);
    }
   
    private void lineProp(int x1, int y1, int x2, int y2) {
        shape.getProperties().put("Length", Math.hypot(x2 - x1, y2 - y1));
        shape.setPosition(new Point((x1+x2)/2, (y1+y2)/2));
    }
   
    private void rectProp(int x1, int y1, int x2, int y2) {
        shape.getProperties().put("Height", (double) (y2-y1));
        shape.getProperties().put("Width", (double) (y2-y1));
        shape.getProperties().put("Area", (double) (Math.abs(x2-x1) * Math.abs(y2-y1)));
        shape.getProperties().put("Perimeter", 2.0 * (Math.abs(x2-x1) + Math.abs(y2-y1)));
        shape.setPosition(new Point((x1+x2)/2, (y1+y2)/2));
    }
 
 
   
    private void sqrProp(int x1, int y1, int x2, int y2) {
        double type;

        if(x1>x2){
            x2=x1+Math.abs(x1-x2);
        }
        if(y1>y2){
            y2=y1+Math.abs(y1-y2);
        }
        type=0;

        shape.getProperties().put("type",type);
        shape.getProperties().put("Length", 2.0 * Math.max(Math.abs(x2-x1), Math.abs(y2-y1)));
        shape.getProperties().put("x2", shape.getProperties().get("x1")+shape.getProperties().get("Length")/2);
        shape.getProperties().put("y2", shape.getProperties().get("y1")+shape.getProperties().get("Length")/2);
        shape.getProperties().put("Area", Math.pow(2.0 * Math.abs(x2-x1), 2));
        shape.getProperties().put("Perimeter", 4.0 * 2.0 * Math.abs(x2-x1));
        shape.setPosition(new Point(x1, y1));
    }
   
    private void ellProp(int x1, int y1, int x2, int y2) {
        shape.getProperties().put("Major Axis", Math.max((double) Math.abs(x2-x1), (double) Math.abs(y2-y1)));
        shape.getProperties().put("Minor Axis", Math.min((double) Math.abs(x2-x1), (double) Math.abs(y2-y1)));
        shape.setPosition(new Point((x1+x2)/2, (y1+y2)/2));
 
    }
   
    private void cirProp(int x1, int y1, int x2, int y2) {
        double type;
        if(x1>x2){
            x2=x1+Math.abs(x1-x2);
        }
        if(y1>y2){
            y2=y1+Math.abs(y1-y2);
        }
        type=0;
        shape.getProperties().put("x1", (double) x1);
        shape.getProperties().put("x2", (double) x2);
        shape.getProperties().put("y1", (double) y1);
        shape.getProperties().put("y2", (double) y2);
        shape.getProperties().put("type",type);
        shape.getProperties().put("Radius", (double) Math.max(Math.abs(x2-x1), Math.abs(y2-y1)));
        shape.getProperties().put("Area", Math.PI * Math.pow(shape.getProperties().get("Radius"), 2));
        shape.getProperties().put("Perimeter", 2 * Math.PI * shape.getProperties().get("Radius"));
        shape.setPosition(new Point(x1, y1));
    }
   
    private void trProp(int x1, int y1, int x2, int y2) {
        shape.getProperties().put("x3", (double) x1);
        shape.getProperties().put("y3", (double) y2);
        shape.getProperties().put("Perimeter", Math.abs(x2-x1) + Math.abs(y2-y1) + Math.hypot(x2-x1, y2-y1));
        shape.setPosition(new Point(x1, y1));
    }
 
    public boolean isinsideresizerect(Point e){
        if (selectedShape!=null&&selectedShape.getClass()==Rectangle.class){
            if(e.getX()>selectedX2&&e.getX()<selectedX2+12&&e.getY()>selectedY2&&e.getY()<selectedY2+12){return true;}
        }
        if(selectedShape!=null&&selectedShape.getClass()==Square.class){
            if(e.getX()>selectedX2&&e.getX()<selectedX2+12&&e.getY()>selectedY2&&e.getY()<selectedY2+12){return true;}
        }
        if(selectedShape!=null&&selectedShape.getClass()==Ellipse.class){
            if(e.getX()>selectedX2&&e.getX()<selectedX2+12&&e.getY()>selectedY2&&e.getY()<selectedY2+12){return true;}
 
        }
        if(selectedShape!=null&&selectedShape.getClass()==LineSegment.class){
            if(e.getX()>selectedX2&&e.getX()<selectedX2+12&&e.getY()>selectedY2&&e.getY()<selectedY2+12){state =0;return true;}
            if(e.getX()>selectedX1&&e.getX()<selectedX1+12&&e.getY()>selectedY1&&e.getY()<selectedY1+12){state=1;return true;}
        }
        if(selectedShape!=null&&selectedShape.getClass()==Triangle.class){
            if(e.getX()>selectedX2&&e.getX()<selectedX2+12&&e.getY()>selectedY2&&e.getY()<selectedY2+12){state =0;return true;}
            if(e.getX()>selectedX1&&e.getX()<selectedX1+12&&e.getY()>selectedY1&&e.getY()<selectedY1+12){state=1;return true;}
            if(e.getX()>selectedX3&&e.getX()<selectedX3+12&&e.getY()>selectedY3&&e.getY()<selectedY3+12){state=2;return true;}
        }
        if(selectedShape!=null&&selectedShape.getClass()==Circle.class){
            if(e.getX()>selectedX2&&e.getX()<selectedX2+12&&e.getY()>selectedY2&&e.getY()<selectedY2+12){return true;}
        }
        return false;
    }
 
    public void resizeFinalizer(){
        if(selectedShape!=null&&selectedShape.getClass()==Square.class){
            shape=selectedShape;sqrProp(selectedShape.getProperties().get("x1").intValue(),selectedShape.getProperties().get("y1").intValue(),selectedShape.getProperties().get("x2").intValue(),selectedShape.getProperties().get("y2").intValue());
 
        }
        if(selectedShape!=null&&selectedShape.getClass()==Circle.class){
            shape=selectedShape;cirProp(selectedShape.getProperties().get("x1").intValue(),selectedShape.getProperties().get("y1").intValue(),selectedShape.getProperties().get("x2").intValue(),selectedShape.getProperties().get("y2").intValue());

        }

    }
 
    public void ShapeResize(Point e){
        if(selectedShape!=null){
            if(selectedShape.getClass()==Rectangle.class){
                if(!resizeFlag){dummyX=selectedShape.getProperties().get("x2").intValue();dummyY=selectedShape.getProperties().get("y2").intValue();resizeFlag=true;}
                deltaX= (int) (e.getX()-firstX);
                deltaY= (int) (e.getY()-firstY);
                selectedShape.getProperties().put("x2", (double) (dummyX+deltaX));
                selectedShape.getProperties().put("y2", (double) (dummyY+deltaY));
            }
            if(selectedShape.getClass()==Square.class||selectedShape.getClass()==Circle.class){
                if(!resizeFlag){dummyX=selectedShape.getProperties().get("x2").intValue();dummyY=selectedShape.getProperties().get("y2").intValue();resizeFlag=true;}
                deltaX= (int) (e.getX()-firstX);
                deltaY= (int) (e.getY()-firstY);
                if((dummyX+deltaX>selectedShape.getProperties().get("x1")&&dummyY+deltaY>selectedShape.getProperties().get("y1"))||
                        ((Math.abs(dummyX+deltaX-selectedShape.getProperties().get("x1"))<5)&&(Math.abs(dummyY+deltaY-selectedShape.getProperties().get("y1"))<5))){
                    selectedShape.getProperties().put("x2", (double) (dummyX+deltaX));
                    selectedShape.getProperties().put("y2", (double) (dummyY+deltaY));
                }
                resizeFinalizer();

            }
            if(selectedShape.getClass()==Ellipse.class){
                if(!resizeFlag){dummyX=selectedShape.getProperties().get("x2").intValue();dummyY=selectedShape.getProperties().get("y2").intValue();resizeFlag=true;}
                deltaX= (int) (e.getX()-firstX);
                deltaY= (int) (e.getY()-firstY);
                selectedShape.getProperties().put("x2", (double) (dummyX+deltaX));
                selectedShape.getProperties().put("y2", (double) (dummyY+deltaY));
            }
            if(selectedShape.getClass()==LineSegment.class) {
                if (state==0) {
                    if (!resizeFlag) {
                        dummyX = selectedShape.getProperties().get("x2").intValue();
                        dummyY = selectedShape.getProperties().get("y2").intValue();
                        resizeFlag = true;
                    }
                    deltaX = (int) (e.getX() - firstX);
                    deltaY = (int) (e.getY() - firstY);
                    selectedShape.getProperties().put("x2", (double) (dummyX + deltaX));
                    selectedShape.getProperties().put("y2", (double) (dummyY + deltaY));
                } else if (state==1) {
                    if (!resizeFlag) {
                        dummyX = selectedShape.getProperties().get("x1").intValue();
                        dummyY = selectedShape.getProperties().get("y1").intValue();
                        resizeFlag = true;
                    }
                    deltaX = (int) (e.getX() - firstX);
                    deltaY = (int) (e.getY() - firstY);
                    selectedShape.getProperties().put("x1", (double) (dummyX + deltaX));
                    selectedShape.getProperties().put("y1", (double) (dummyY + deltaY));
                }
 
            }
            if(selectedShape.getClass()==Triangle.class){
                if (state==0) {
                    if (!resizeFlag) {
                        dummyX = selectedShape.getProperties().get("x2").intValue();
                        dummyY = selectedShape.getProperties().get("y2").intValue();
                        resizeFlag = true;
                    }
                    deltaX = (int) (e.getX() - firstX);
                    deltaY = (int) (e.getY() - firstY);
                    selectedShape.getProperties().put("x2", (double) (dummyX + deltaX));
                    selectedShape.getProperties().put("y2", (double) (dummyY + deltaY));
                } else if (state==1) {
                    if (!resizeFlag) {
                        dummyX = selectedShape.getProperties().get("x1").intValue();
                        dummyY = selectedShape.getProperties().get("y1").intValue();
                        resizeFlag = true;
                    }
                    deltaX = (int) (e.getX() - firstX);
                    deltaY = (int) (e.getY() - firstY);
                    selectedShape.getProperties().put("x1", (double) (dummyX + deltaX));
                    selectedShape.getProperties().put("y1", (double) (dummyY + deltaY));
                } else if (state==2) {
                    if (!resizeFlag) {
                        dummyX = selectedShape.getProperties().get("x3").intValue();
                        dummyY = selectedShape.getProperties().get("y3").intValue();
                        resizeFlag = true;
                    }
                    deltaX = (int) (e.getX() - firstX);
                    deltaY = (int) (e.getY() - firstY);
                    selectedShape.getProperties().put("x3", (double) (dummyX + deltaX));
                    selectedShape.getProperties().put("y3", (double) (dummyY + deltaY));
                }
            }
            /*if(selectedShape.getClass()==Circle.class){
                if(!resizeFlag){dummyX=selectedShape.getProperties().get("x2").intValue();dummyY=selectedShape.getProperties().get("y2").intValue();resizeFlag=true;}
                deltaX= (int) (e.getX()-firstX);
                deltaY= (int) (e.getY()-firstY);

                if(swichflag||selectedShape.getProperties().get("x1")>selectedShape.getProperties().get("x2")&&selectedShape.getProperties().get("y1")>selectedShape.getProperties().get("y2")) {
                    selectedShape.getProperties().put("x2", (double) (dummyX - deltaX));
                    selectedShape.getProperties().put("y2", (double) (dummyY - deltaY));
                    swichflag=true;
                }
                else{selectedShape.getProperties().put("x2", (double) (dummyX + deltaX));
                    selectedShape.getProperties().put("y2", (double) (dummyY + deltaY));}
                selectedShape.getProperties().put("Radius",Math.abs((selectedShape.getProperties().get("x1")-selectedShape.getProperties().get("x2"))));
            }*/
 
        }
 
    }
 
    public void shapeLimitAdder(Graphics g){
        if(selectedShape!=null) {g.setColor(Color.gray);
 
 
                if (selectedShape.getClass() == Rectangle.class) {
                    selectedX1= (int) (selectedShape.getProperties().get("x1") - 4) ;
                    selectedY1=(int) (selectedShape.getProperties().get("y1") - 4);
 
                    g.fillRect((int) (selectedShape.getProperties().get("x1") - 4 ), (int) (selectedShape.getProperties().get("y1") - 4), 8, 8);
                    g.fillRect(- 4 + selectedShape.getProperties().get("x2").intValue(), (int) (selectedShape.getProperties().get("y1") - 4), 8, 8);
                    g.fillRect((int) (selectedShape.getProperties().get("x1") - 4),  - 4 + selectedShape.getProperties().get("y2").intValue(), 8, 8);
                    g.setColor(Color.red);
                    if(selectedShape.getProperties().get("Width")>0&&selectedShape.getProperties().get("Height")>0) {
                        g.fillRect(selectedShape.getProperties().get("x2").intValue(),  selectedShape.getProperties().get("y2").intValue(), 12, 12);
                        selectedX2=selectedShape.getProperties().get("x2").intValue();
                        selectedY2=selectedShape.getProperties().get("y2").intValue();
                    }
                    else{
                        g.fillRect((int) (selectedShape.getProperties().get("x2")-12), (int) (selectedShape.getProperties().get("y2")-12), 12, 12);
                        selectedX2=(int) (selectedShape.getProperties().get("x2")-12);
                        selectedY2=(int) (selectedShape.getProperties().get("y2")-12);
                    }
                }
                if (selectedShape.getClass() == Square.class) {int x2=0,y2=0,x1=0,y1=0;
                    switch (selectedShape.getProperties().get("type").intValue()){
                        case 0:x2=(int)(selectedShape.getProperties().get("x1")+selectedShape.getProperties().get("Length")/2);y2=(int)(selectedShape.getProperties().get("y1")+selectedShape.getProperties().get("Length")/2);
                        x1=(int)(selectedShape.getProperties().get("x1") - selectedShape.getProperties().get("Length").intValue()/2);y1= (int) (selectedShape.getProperties().get("y1") - selectedShape.getProperties().get("Length").intValue()/2);break;
                        case 1:x2=(int)(selectedShape.getProperties().get("x1")+selectedShape.getProperties().get("Length")/2);y2=(int)(selectedShape.getProperties().get("y1")-selectedShape.getProperties().get("Length")/2);
                            x1=(int)(selectedShape.getProperties().get("x1") - selectedShape.getProperties().get("Length").intValue()/2);y1= (int) (selectedShape.getProperties().get("y1") + selectedShape.getProperties().get("Length").intValue()/2);break;
                        case 2:x2=(int)(selectedShape.getProperties().get("x1")-selectedShape.getProperties().get("Length")/2);y2=(int)(selectedShape.getProperties().get("y1")+selectedShape.getProperties().get("Length")/2);
                            x1=(int)(selectedShape.getProperties().get("x1") + selectedShape.getProperties().get("Length").intValue()/2);y1= (int) (selectedShape.getProperties().get("y1") - selectedShape.getProperties().get("Length").intValue()/2);break;
                        case 3:x2=(int)(selectedShape.getProperties().get("x1")-selectedShape.getProperties().get("Length")/2);y2=(int)(selectedShape.getProperties().get("y1")-selectedShape.getProperties().get("Length")/2);
                            x1=(int)(selectedShape.getProperties().get("x1") + selectedShape.getProperties().get("Length").intValue()/2);y1= (int) (selectedShape.getProperties().get("y1") + selectedShape.getProperties().get("Length").intValue()/2);break;
                    }
                    selectedX1=x1-4;
                    selectedY1=y1-4;
                    g.fillRect(x1-4,y1-4, 8, 8);
                    g.fillRect(x2,y1-4, 8, 8);
                    g.fillRect(x1-4, y2, 8, 8);
                    g.setColor(Color.red);
                    if(selectedShape.getProperties().get("type")<3) {
                        g.fillRect (x2, y2, 12, 12);
                        selectedX2=x2;selectedY2=y2;
                    }
                    else{
                        g.fillRect(x2, y2-12, 12, 12);
                        selectedX2=x2;selectedY2=y2-12;
                    }
 
                }
                if (selectedShape.getClass() == Circle.class) {int x2=0,y2=0,x1=0,y1=0;
                    switch (selectedShape.getProperties().get("type").intValue()){
                        case 0:x2=(int)(selectedShape.getProperties().get("x1")+selectedShape.getProperties().get("Radius"));y2=(int)(selectedShape.getProperties().get("y1")+selectedShape.getProperties().get("Radius"));
                            x1=(int)(selectedShape.getProperties().get("x1") - selectedShape.getProperties().get("Radius").intValue());y1= (int) (selectedShape.getProperties().get("y1") - selectedShape.getProperties().get("Radius").intValue());break;
                        case 1:x2=(int)(selectedShape.getProperties().get("x1")+selectedShape.getProperties().get("Radius"));y2=(int)(selectedShape.getProperties().get("y1")-selectedShape.getProperties().get("Radius"));
                            x1=(int)(selectedShape.getProperties().get("x1") - selectedShape.getProperties().get("Radius").intValue());y1= (int) (selectedShape.getProperties().get("y1") + selectedShape.getProperties().get("Radius").intValue());break;
                        case 2:x2=(int)(selectedShape.getProperties().get("x1")-selectedShape.getProperties().get("Radius"));y2=(int)(selectedShape.getProperties().get("y1")+selectedShape.getProperties().get("Radius"));
                            x1=(int)(selectedShape.getProperties().get("x1") + selectedShape.getProperties().get("Radius").intValue());y1= (int) (selectedShape.getProperties().get("y1") - selectedShape.getProperties().get("Radius").intValue());break;
                        case 3:x2=(int)(selectedShape.getProperties().get("x1")-selectedShape.getProperties().get("Radius"));y2=(int)(selectedShape.getProperties().get("y1")-selectedShape.getProperties().get("Radius"));
                            x1=(int)(selectedShape.getProperties().get("x1") + selectedShape.getProperties().get("Radius").intValue());y1= (int) (selectedShape.getProperties().get("y1") + selectedShape.getProperties().get("Radius").intValue());break;
                    }
                    selectedX1=x1-4;selectedY1=y1-4;
                    selectedX2=x2-4;selectedY2=y2-4;
                    g.fillRect(x1 - 4, y1- 4, 8, 8);
                    g.fillRect(x2, y1 - 4, 8, 8);
                    g.fillRect(x1- 4, y2, 8, 8);
                    g.setColor(Color.red);
                    g.fillRect(x2- 4 , y2 - 4, 12, 12);
 
 
                }
                if (selectedShape.getClass() == Ellipse.class) {
                    selectedX1=(int) (selectedShape.getProperties().get("x1") - 4);
                    selectedY1=(int) (selectedShape.getProperties().get("y1") - 4);
                    selectedX2=(int) (selectedShape.getProperties().get("x2")+0);
                    selectedY2=(int) (selectedShape.getProperties().get("y2") - 0);
                    g.fillRect((int) (selectedShape.getProperties().get("x1") - 4), (int) (selectedShape.getProperties().get("y1") - 4), 8, 8);
                    g.fillRect((int) (selectedShape.getProperties().get("x2") - 4), (int) (selectedShape.getProperties().get("y1") - 4), 8, 8);
                    g.fillRect((int) (selectedShape.getProperties().get("x1") - 4), (int) (selectedShape.getProperties().get("y2") - 4), 8, 8);
                    g.setColor(Color.red);
                    g.fillRect((int) (selectedShape.getProperties().get("x2")+0), (int) (selectedShape.getProperties().get("y2") - 0), 12, 12);
 
                }
                if (selectedShape.getClass() == Triangle.class) {
                    selectedY1=selectedShape.getProperties().get("y1").intValue()-4;
                    selectedX1=selectedShape.getProperties().get("x1").intValue()-4;
                    selectedX2=selectedShape.getProperties().get("x2").intValue()-4;
                    selectedY2=selectedShape.getProperties().get("y2").intValue()-4;
                    selectedX3=selectedShape.getProperties().get("x3").intValue()-4;
                    selectedY3=selectedShape.getProperties().get("y3").intValue()-4;
                    g.setColor(Color.red);
                    g.fillRect(selectedShape.getProperties().get("x1").intValue()-4, selectedShape.getProperties().get("y1").intValue()-4 , 12, 12);
                    g.fillRect(selectedShape.getProperties().get("x2").intValue()-4, selectedShape.getProperties().get("y2").intValue()-4, 12, 12);
                    g.fillRect(selectedShape.getProperties().get("x3").intValue()-4, selectedShape.getProperties().get("y3").intValue()-4, 12, 12);
 
                }
                if (selectedShape.getClass() == LineSegment.class) {
                    g.setColor(Color.red);
                    g.fillRect(selectedShape.getProperties().get("x1").intValue(), selectedShape.getProperties().get("y1").intValue(), 12, 12);
                    g.fillRect(selectedShape.getProperties().get("x2").intValue(), selectedShape.getProperties().get("y2").intValue(), 12, 12);
                    selectedX1=selectedShape.getProperties().get("x1").intValue();
                    selectedY1=selectedShape.getProperties().get("y1").intValue();
                    selectedX2=selectedShape.getProperties().get("x2").intValue();
                    selectedY2=selectedShape.getProperties().get("y2").intValue();
                }
 
 
 
        }
    }
    public void keepShapes(Graphics panelGraphics) {
        engine.refresh(panelGraphics);
    }
   
    public void brush(Graphics canvas, Point p, Color fillColor, Color strokeColor) {
        Shape s = getSelectedShape(p);
        if(s != null) {
            s.setFillColor(fillColor);
            s.setColor(strokeColor);
            s.draw(canvas);
        }
    }
   
    public void setSelectedShape(Point p) {
        this.selectedShape = getSelectedShape(p);
    }
   
    public Shape getSelectedShape(Point p) {
        Shape[] shapes = engine.getShapes();
        for(int i = shapes.length-1; i >= 0; i--) {
            if(contains(shapes[i], p)) {
                return shapes[i];
            }
        }
        return null;
    }
   
    public void setFirstPoint(Point p) {
        this.firstPoint = p;
    }
 
    private boolean contains(Shape s, Point p) {
        if (s.getClass().getSimpleName().equals("FreeDrawing")) {
            return false;
        }
        int x1 = (int) Math.floor(s.getProperties().get("x1"));
        int y1 = (int) Math.floor(s.getProperties().get("y1"));
        int x2 = (int) Math.floor(s.getProperties().get("x2"));
        int y2 = (int) Math.floor(s.getProperties().get("y2"));
        switch(s.getClass().getSimpleName()) {
            case "LineSegment":
                return contLine(x1, y1, x2, y2, p);
            case "Rectangle":
                return contRect(x1, y1, x2, y2, p);
            case "Square":
                return contSqr(x1, y1, x2, y2, p);
            case "Circle":
                return contCir(x1, y1, x2, y2, p);
            case "Ellipse":
                return contEll(x1, y1, x2, y2, p);
            case "Triangle":
                return contTr(x1, y1, x2, y2, (int) Math.floor(s.getProperties().get("x3")), (int) Math.floor(s.getProperties().get("y3")), p);
        }
        return false;
    }
 
    private boolean contTr(int x1, int y1, int x2, int y2, int x3, int y3, Point p) {
    	double len1 = Math.hypot(p.x - x1, p.y - y1);
		double len2 = Math.hypot(p.x - x2, p.y - y2);
		double len3 = Math.hypot(p.x - x3, p.y - y3);
		
		double len12 = Math.hypot(x2 - x1, y2 - y1);
		double len23 = Math.hypot(x2 - x3, y2 - y3);
		double len13 = Math.hypot(x3 - x1, y3 - y1);
		
		double p1 = len1 + len2 +len12;
		double p2 = len2 + len3 + len23;
		double p3 = len1 + len3 + len13;
		
		return Math.abs((calculateTriangleArea(len1, len2, len12, p1) + calculateTriangleArea(len2, len3, len23, p2) + 
				calculateTriangleArea(len1, len3, len13, p3)) - (calculateTriangleArea(len12, len23, len13, len12+len23+len13))) < Math.pow(10.0, -10);
    }
 
    private boolean contEll(int x1, int y1, int x2, int y2, Point p) {
        int centerX = (x1 + x2) / 2;
        int centerY = (y1 + y2) / 2;
        double min = Math.min((double) Math.abs(x2-x1), (double) Math.abs(y2-y1));
        double max = Math.max((double) Math.abs(x2-x1), (double) Math.abs(y2-y1));
        if((int)(1.0 * min) == (int)(Math.abs(x2 - x1))) {
            return (Math.pow(p.x - centerX, 2.0) / Math.pow(min / 2, 2.0)) +
                    (Math.pow(p.y - centerY, 2.0) / Math.pow(max / 2, 2.0)) <= 1;
                   
        }else {
            return (Math.pow(p.x - centerX, 2.0) / Math.pow(max / 2, 2.0)) +
                    (Math.pow(p.y - centerY, 2.0) / Math.pow(min / 2, 2.0)) <= 1;
        }
    }
 
    private boolean contCir(int x1, int y1, int x2, int y2, Point p) {
        return (int)(Math.pow(p.x-x1, 2.0) + Math.pow(p.y-y1, 2.0)) <= (int)Math.pow(Math.max(Math.abs(x2-x1), Math.abs(y2-y1)), 2.0);
    }
 
    private boolean contSqr(int x1, int y1, int x2, int y2, Point p) {
        int len = (int)(2.0 * Math.max(Math.abs(x2-x1), Math.abs(y2-y1)));
        return ((p.x >= (x1 - (int)(0.5 * len))) && (p.x <= (x1 + (int)(0.5 * len)))) &&
                ((p.y >= (y1 - (int)(0.5 * len))) && (p.y <= (y1 + (int)(0.5 * len))));
    }
 
    private boolean contRect(int x1, int y1, int x2, int y2, Point p) {
        return ((p.x <= Math.max(x1, x2) && p.x >= Math.min(x1, x2)) &&
                (p.y <= Math.max(y1, y2) && p.y >= Math.min(y1, y2)));
    }
 
    private boolean contLine(int x1, int y1, int x2, int y2, Point p) {
        return Math.floor(Math.hypot(p.x-x1, p.y-y1) + Math.hypot(p.x-x2, p.y-y2)) == Math.floor(Math.hypot(x2 - x1, y2 - y1));
    }
   
    private double calculateTriangleArea(double a, double b, double c, double p) {
        p /= 2;      
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }
 
    public void move(Graphics canvas, Point p) {
        if (this.selectedShape == null || this.firstPoint == null) {
            return; // not selecting anything
        }
       
        int x1 = (int) Math.floor(selectedShape.getProperties().get("x1"));
        int y1 = (int) Math.floor(selectedShape.getProperties().get("y1"));
        int x2 = (int) Math.floor(selectedShape.getProperties().get("x2"));
        int y2 = (int) Math.floor(selectedShape.getProperties().get("y2"));
       
        selectedShape.getProperties().put("x1", (double) (x1 + (p.x - this.firstPoint.x)));
        selectedShape.getProperties().put("y1", (double) (y1 + (p.y - this.firstPoint.y)));
        selectedShape.getProperties().put("x2", (double) (x2 + (p.x - this.firstPoint.x)));
        selectedShape.getProperties().put("y2", (double) (y2 + (p.y - this.firstPoint.y)));
        if(selectedShape.getClass()==Triangle.class){
            selectedShape.getProperties().put("x3", (double) ((int) Math.floor(selectedShape.getProperties().get("x3")) + (p.x - this.firstPoint.x)));
            selectedShape.getProperties().put("y3",  (double) ((int) Math.floor(selectedShape.getProperties().get("y3")) + (p.y - this.firstPoint.y)));
        }
        this.firstPoint = p;
        selectedShape.draw(canvas);
       
    }
   
}
