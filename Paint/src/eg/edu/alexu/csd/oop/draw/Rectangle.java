package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shapes {

	public Rectangle() {
		super();
	}
	
	public Rectangle(int x1, int y1, Color fillColor, Color strokeColor) {
		super(x1, y1, fillColor, strokeColor);
	}
	
	public void draw(Graphics canvas) {	
		int x1 = (int) Math.floor(super.getProperties().get("x1"));
		int y1 = (int) Math.floor(super.getProperties().get("y1"));
		int x2 = (int) Math.floor(super.getProperties().get("x2"));
		int y2 = (int) Math.floor(super.getProperties().get("y2"));
				
		if (x2 > x1 && y2 > y1) {
			canvas.setColor(getColor());
			canvas.fillRect(x1, y1, x2-x1+1, y2-y1+1);
			canvas.setColor(getFillColor());
			canvas.fillRect(x1+1, y1+1, x2-x1-1, y2-y1-1);
		}else if (x1 > x2 && y1 > y2){
			canvas.setColor(getColor());
			canvas.fillRect(x2, y2, x1-x2+1, y1-y2+1);
			canvas.setColor(getFillColor());
			canvas.fillRect(x2+1, y2+1, x1-x2-1, y1-y2-1);
		}else if (x2 > x1 && y2 < y1) {
			canvas.setColor(getColor());
			canvas.fillRect(x1, y2, x2-x1+1, y1-y2+1);
			canvas.setColor(getFillColor());
			canvas.fillRect(x1+1, y2+1, x2-x1-1, y1-y2-1);
		}else {
			canvas.setColor(getColor());
			canvas.fillRect(x2, y1, x1-x2+1, y2-y1+1);
			canvas.setColor(getFillColor());
			canvas.fillRect(x2+1, y1+1, x1-x2-1, y2-y1-1);
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
		Rectangle r = new Rectangle(x1, y1, super.getFillColor(), super.getColor());
		r.getProperties().put("x2", (double) x2);
		r.getProperties().put("y2", (double) y2);
		r.getProperties().put("Height", (double) Math.abs(y2-y1));
		r.getProperties().put("Width", (double) Math.abs(y2-y1));
		r.getProperties().put("Area", (double) (Math.abs(x2-x1) * Math.abs(y2-y1)));
		r.getProperties().put("Perimeter", 2.0 * (Math.abs(x2-x1) + Math.abs(y2-y1)));
		r.setPosition(super.getPosition());
		return r;
	}
	
}
