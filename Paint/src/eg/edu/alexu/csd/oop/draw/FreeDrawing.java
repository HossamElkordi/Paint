package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class FreeDrawing extends Shapes{
	
	private ArrayList<Point> points;
	private Color color;
	
	public FreeDrawing() {
		
	}
	
	public FreeDrawing(Color color) {
		points = new ArrayList<Point>();
		this.color = color;
	}

	public void addPoint(int x, int y) {
		points.add(new Point(x, y));
	}
	
	public void draw(Graphics canvas) {
		if(color != Color.WHITE) {
			canvas.setColor(super.getFillColor());
		}
		for(int i = 0; i < points.size() - 1; i++) {
			canvas.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
		}
	}
	
	private ArrayList<Point> getPoints() {
		return points;
	}

	private void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public Object clone() throws CloneNotSupportedException{
		FreeDrawing f = new FreeDrawing(this.color);
		f.setPoints(this.getPoints());
		return f;
	}
}
