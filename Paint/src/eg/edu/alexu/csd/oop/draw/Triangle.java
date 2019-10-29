package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Triangle extends Shapes {

	public Triangle() {
		super();
	}
	
	public Triangle(int x1, int y1, Color fillcolor, Color strokecolor) {
		super(x1, y1, fillcolor, strokecolor);
		super.getProperties().put("x3", (double)x1);
		super.getProperties().put("y3", (double) y1);
		setPosition(new Point(x1, y1));
	}
	
	public void draw(Graphics canvas) {
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		int x3 = (int) Math.floor(super.getProperties().get("x3"));
		int y3 = (int) Math.floor(super.getProperties().get("y3"));
		
		int[] x = {x1,x2,x3};
		int[] y = {y1,y2,y3};
		canvas.setColor(getColor());
		canvas.fillPolygon(x, y, 3);
		
		if(x2 >= x1 && y2 >= y1) {
			int[] xs = {x1+1,x2-2,x3+1};
			int[] ys = {y1+2,y2-1,y3-1};
			canvas.setColor(getFillColor());
			canvas.fillPolygon(xs, ys, 3);
		}else if(x2 >= x1 && y2 < y1) {
			int[] xs = {x1+1,x2-2,x3+1};
			int[] ys = {y1-2,y2+1,y3+1};
			canvas.setColor(getFillColor());
			canvas.fillPolygon(xs, ys, 3);
		}else if(x1 >= x2 && y2 >= y1) {
			int[] xs = {x1-1,x2+2,x3-1};
			int[] ys = {y1+2,y2-1,y3-1};
			canvas.setColor(getFillColor());
			canvas.fillPolygon(xs, ys, 3);
		}else {
			int[] xs = {x1-1,x2+2,x3-1};
			int[] ys = {y1-2,y2+1,y3+1};
			canvas.setColor(getFillColor());
			canvas.fillPolygon(xs, ys, 3);
		}
	}
	
	public Object clone() throws CloneNotSupportedException{
		Object o = super.clone();
		if(o != null) {
			return o;
		}
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		int x3 = (int) Math.floor(super.getProperties().get("x3"));
		int y3 = (int) Math.floor(super.getProperties().get("y3"));
		Triangle t = new Triangle(x1, y1, super.getFillColor(), super.getColor());
		t.getProperties().put("x2", (double) x2);
		t.getProperties().put("y2", (double) y2);
		t.getProperties().put("x3", (double) x3);
		t.getProperties().put("y3", (double) y3);		
		t.setPosition(super.getPosition());
		return t;
	}
	
}
