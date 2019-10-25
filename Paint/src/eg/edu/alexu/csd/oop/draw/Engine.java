package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Engine implements DrawingEngine {

	ArrayList<Shape> shapes;
	
	public Engine() {
		shapes = new ArrayList<Shape>();
	}
	
	public void refresh(Graphics canvas) {
		for (Shape s : shapes) {
			s.draw(canvas);
		}
	}

	public void addShape(Shape shape) {
		this.shapes.add(shape);
	}

	public void removeShape(Shape shape) {
		this.shapes.remove(shape);
	}

	public void updateShape(Shape oldShape, Shape newShape) {
		for(Shape s : shapes) {
			if(s.equals(oldShape)) {
				s = newShape;
				break;
			}
		}
	}

	public Shape[] getShapes() {
		Shape[] shapesArray = new Shape[this.shapes.size()];
		for(int i = 0; i < shapes.size(); i++) {
			shapesArray[i] = this.shapes.get(i);
		}
		return shapesArray;
	}

	public List<Class<? extends Shape>> getSupportedShapes() {
		
		return null;
	}

	public void undo() {
		
	}


	public void redo() {
		
	}

	public void save(String path) {
		
	}

	public void load(String path) {
		
	}

}
