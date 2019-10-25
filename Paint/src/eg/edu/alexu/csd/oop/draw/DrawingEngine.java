package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.List;


public interface DrawingEngine {
	
	public void refresh(Graphics canvas);

	public void addShape(Shape shape);
	public void removeShape(Shape shape);
	public void updateShape(Shape oldShape, Shape newShape);
	
	public Shape[] getShapes();
	
	public List<Class<? extends Shape>> getSupportedShapes();
	
	public void undo();
	public void redo();
	
	public void save(String path);
	public void load(String path);
	
}
