package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;

public class Ellipse extends Shapes {

	public Ellipse() {
		super();
	}
	
	public Ellipse(int x1, int y1, Color fillColor, Color strokeColor) {
		super(x1, y1, fillColor, strokeColor);
	}
	
	public void draw(Graphics canvas) {
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		
		if (x2 > x1 && y2 > y1) {
			canvas.setColor(getColor());
			canvas.fillOval(x1, y1, x2-x1+1, y2-y1+1);
			canvas.setColor(getFillColor());
			canvas.fillOval(x1+1, y1+1, x2-x1-1, y2-y1-1);
		}else if (x1 > x2 && y1 > y2){
			canvas.setColor(getColor());
			canvas.fillOval(x2, y2, x1-x2+1, y1-y2+1);
			canvas.setColor(getFillColor());
			canvas.fillOval(x2+1, y2+1, x1-x2-1, y1-y2-1);
		}else if (x2 > x1 && y2 < y1) {
			canvas.setColor(getColor());
			canvas.fillOval(x1, y2, x2-x1+1, y1-y2+1);
			canvas.setColor(getFillColor());
			canvas.fillOval(x1+1, y2+1, x2-x1-1, y1-y2-1);
		}else {
			canvas.setColor(getColor());
			canvas.fillOval(x2, y1, x1-x2+1, y2-y1+1);
			canvas.setColor(getFillColor());
			canvas.fillOval(x2+1, y1+1, x1-x2-1, y2-y1-1);
		}
	}
	
	public Object clone() throws CloneNotSupportedException{
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
		Ellipse e = new Ellipse(x1, y1, super.getFillColor(), super.getColor());
		e.getProperties().put("x2", (double) x2);
		e.getProperties().put("y2", (double) y2);
		e.getProperties().put("Major Axis", Math.max((double) Math.abs(x2-x1), (double) Math.abs(y2-y1)));
		e.getProperties().put("Minor Axis", Math.min((double) Math.abs(x2-x1), (double) Math.abs(y2-y1)));
		e.setPosition(super.getPosition());
		return e;
	}
	
}
