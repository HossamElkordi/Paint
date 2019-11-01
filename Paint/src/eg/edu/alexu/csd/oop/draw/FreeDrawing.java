package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

public class FreeDrawing extends Shapes{
	
	public FreeDrawing() {
		
	}
	
	public FreeDrawing(Color color) {
		super.setProperties(new HashMap<String, Double>());
		if(color == Color.WHITE) {
			color = Color.BLACK;
		}
		super.setColor(color);
		super.setFillColor(color);
	}

	public void draw(Graphics canvas) {
		canvas.setColor(getFillColor());
		int i = 1;
		while(super.getProperties().get("x" + (i+1)) != null) {
			canvas.drawLine(super.getProperties().get("x" + i).intValue(), super.getProperties().get("y" + i).intValue(),
					super.getProperties().get("x" + (i+1)).intValue(), super.getProperties().get("y" + (i+1)).intValue());
			i++;
		}
		canvas.drawLine(super.getProperties().get("x" + i).intValue(), super.getProperties().get("y" + i).intValue(),
				super.getProperties().get("x" + (i-1)).intValue(), super.getProperties().get("y" + (i-1)).intValue());
	}
	
	public Object clone() throws CloneNotSupportedException{
		FreeDrawing f = new FreeDrawing(getFillColor());
		f.setProperties(super.getProperties());
		f.setFillColor(getFillColor());
		f.setColor(getColor());
		return f;
	}
}
