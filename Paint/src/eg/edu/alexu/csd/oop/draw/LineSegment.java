package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;

public class LineSegment extends Shapes {
	public LineSegment() {
		super();
	}

	public LineSegment(int x1, int y1, Color fillColor, Color strokeColor) {
		super(x1, y1, fillColor, strokeColor);
		if(fillColor == Color.WHITE) {
			super.setFillColor(Color.BLACK);
		}
	}
	

	public void draw(Graphics canvas) {
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		
		if(getFillColor() == Color.WHITE) {
			canvas.drawLine(x1, y1, x2, y2);
			return;
		}
		canvas.setColor(getFillColor());
		canvas.drawLine(x1, y1, x2, y2);
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
		LineSegment l = new LineSegment(x1, y1, super.getFillColor(), super.getColor());
		l.getProperties().put("x2", (double) x2);
		l.getProperties().put("y2", (double) y2);
		l.getProperties().put("Length", Math.hypot(x2 - x1, y2 - y1));
		l.setPosition(super.getPosition());
		return l;
	}
	
}
