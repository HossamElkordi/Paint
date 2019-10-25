package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Square extends Shapes {

	public Square() {
		super();
	}
	
	public Square(int x1, int y1, Color fillColor, Color strokeColor) {
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
		canvas.fillRect(x1-(int)(0.5 * len), firstY, len+1, len+1);
		canvas.setColor(getFillColor());
		canvas.fillRect(x1-(int)(0.5 * len)+1, firstY+1, len-1, len-1);
	}
	
	public Object clone() throws CloneNotSupportedException{
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		Square s = new Square(x1, y1, super.getFillColor(), super.getColor());
		s.getProperties().put("x2", (double) x2);
		s.getProperties().put("y2", (double) y2);
		s.getProperties().put("Length", 2.0 * Math.max(Math.abs(x2-x1), Math.abs(y2-y1)));
		s.getProperties().put("Area", Math.pow(2.0 * Math.abs(x2-x1), 2));
		s.getProperties().put("Perimeter", 4.0 * 2.0 * Math.abs(x2-x1));
		s.setPosition(super.getPosition());
		return s;
	}
	
}
