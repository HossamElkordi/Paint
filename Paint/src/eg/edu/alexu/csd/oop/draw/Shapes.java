package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public abstract class Shapes implements Shape {

	private Point position;
	private Map<String, Double> properties;
	private Color fillColor;
	private Color strokeColor;
			
	public Shapes(int x1, int y1, Color fillColor, Color strokeColor) {
		
		properties = new HashMap<String, Double>();
		properties.put("x1", (double) x1);
		properties.put("y1", (double) y1);
		properties.put("x2", (double) x1);
		properties.put("y2", (double) y1);
		
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public Shapes() {
		properties = new HashMap<String, Double>();
		position = new Point(0, 0);
		properties.put("x1", position.getX());
		properties.put("y1", position.getX());
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	
	public Point getPosition() {
		return position;
	}

	
	public void setProperties(Map<String, Double> properties) {
		this.properties = properties;
	}

	
	public Map<String, Double> getProperties() {
		return properties;
	}

	public void setColor(Color color) {
		this.strokeColor = color;
	}
	
    public Color getColor() {
    	return this.strokeColor;
    }
	
	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	
	public Color getFillColor() {
		return fillColor;
	}

	
	abstract public void draw(Graphics canvas);

	public Object clone() throws CloneNotSupportedException{
		if(properties.size() == 2) {
			try {
				return this.getClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
