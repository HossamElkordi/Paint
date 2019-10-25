package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Shapes {

	public Circle() {
		super();
	}
	
	public Circle(int x1, int y1, Color fillColor, Color strokeColor) {
		super(x1, y1, fillColor, strokeColor);
		setPosition(new Point(x1, y1));
	}
	
	public void draw(Graphics canvas) {
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		
		int len = (int)(2.0 * Math.max(Math.abs(x2-x1), Math.abs(y2-y1)));
		int firstY = y1-(int)(0.5 * len);
				
		canvas.setColor(getColor());
		canvas.fillOval(x1-(int)(0.5 * len), firstY, len+1, len+1);
		canvas.setColor(getFillColor());
		canvas.fillOval(x1-(int)(0.5 * len)+1, firstY+1, len-1, len-1);
	}
	
	public Object clone() throws CloneNotSupportedException{
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		Circle c = new Circle(x1, y1, super.getFillColor(), super.getColor());
		c.getProperties().put("x2", (double) x2);
		c.getProperties().put("y2", (double) y2);
		c.getProperties().put("Radius", (double) Math.max(Math.abs(x2-x1), Math.abs(y2-y1)));
		c.getProperties().put("Area", Math.PI * Math.pow(super.getProperties().get("Radius"), 2));
		c.getProperties().put("Perimeter", 2 * Math.PI * super.getProperties().get("Radius"));
		c.setPosition(super.getPosition());
		return c;
	}
	
}
