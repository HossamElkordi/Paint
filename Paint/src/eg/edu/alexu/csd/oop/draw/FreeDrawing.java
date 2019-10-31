package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class FreeDrawing extends Shapes{
	
	private ArrayList<Point> points;
	private Color color;
	
	public FreeDrawing() {
		
	}
	
	public FreeDrawing(Color color) {
		points = new ArrayList<Point>();
		super.setProperties(new HashMap<String, Double>());
		super.setColor(color);
		super.setFillColor(color);
		this.color = color;
	}
	public void addPoint(int x, int y) {
		points.add(new Point(x, y));
	}
	
	public void draw(Graphics canvas) {
		if(super.getFillColor() != Color.WHITE) {
			canvas.setColor(super.getFillColor());
		}else {
			canvas.setColor(Color.black);
		}
		int i = 1;
		while(super.getProperties().get("x" + (i+1)) != null) {
			canvas.drawLine(super.getProperties().get("x" + i).intValue(), super.getProperties().get("y" + i).intValue(),
					super.getProperties().get("x" + (i+1)).intValue(), super.getProperties().get("y" + (i+1)).intValue());
			i++;
		}
		canvas.drawLine(super.getProperties().get("x" + i).intValue(), super.getProperties().get("y" + i).intValue(),
				super.getProperties().get("x" + (i-1)).intValue(), super.getProperties().get("y" + (i-1)).intValue());
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}

	private void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public Object clone() throws CloneNotSupportedException{
		FreeDrawing f = new FreeDrawing(this.color);
		f.setPoints(this.getPoints());
		f.setProperties(super.getProperties());
		f.setFillColor(getFillColor());
		f.setColor(getColor());
		return f;
	}
}
