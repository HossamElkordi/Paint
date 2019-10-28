package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Engine implements DrawingEngine {

	private Originator originator;
	private CareTaker careTaker;
	
	private ArrayList<Shape> shapes;
	
	public Engine() {
		shapes = new ArrayList<Shape>();
		originator = new Originator();
		careTaker = new CareTaker();
		/*
		 * Saving the first state (empty screen)
		 */
		this.originator.setState(this.getShapes());
		this.careTaker.addMemento(this.originator.saveStateToMemento());
	}
	
	public void refresh(Graphics canvas) {
		for (Shape s : shapes) {
			s.draw(canvas);
		}
	}

	public void addShape(Shape shape) {
		this.shapes.add(shape);
		this.originator.setState(this.getShapes());
		this.careTaker.addMemento(this.originator.saveStateToMemento());
	}

	public void removeShape(Shape shape) {
		this.shapes.remove(shape);
		this.originator.setState(this.getShapes());
		this.careTaker.addMemento(this.originator.saveStateToMemento());
	}

	public void updateShape(Shape oldShape, Shape newShape) {
		for(int i = 0; i < this.shapes.size(); i++) {
			if(this.shapes.get(i).equals(oldShape)) {
				this.shapes.remove(i);
				this.shapes.add(i, newShape);
				break;
			}
		}
		this.originator.setState(this.getShapes());
		this.careTaker.addMemento(this.originator.saveStateToMemento());
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
		getDesiredState(this.careTaker.getIndex() - 1);
	}


	public void redo() {
		getDesiredState(this.careTaker.getIndex() + 1);
	}

	public void save(String path) {
		if(path.contains(".xml")) {
			saveXML(path);
		}
	}

	public void load(String path) {
		if(path.contains(".xml")) {
			loadXML(path);
		}
	}
	
	private void getDesiredState(int index) {
		Memento memento = this.careTaker.getMemento(index);
		if (memento == null) {
			JOptionPane.showMessageDialog(null, "No more saves!");
			return;
		}
		this.originator.getStateFromMemento(memento);
		Shape[] state = this.originator.getState();
		this.toArrayList(state);
	}
	
	private void toArrayList(Shape[] state) {
		if(state.length < this.shapes.size()) {
			int i;
			for(i = 0; i < state.length; i++) {
				this.shapes.remove(i);
				this.shapes.add(i, state[i]);
			}
			for(int j = this.shapes.size() - 1; j >= i ; j--) {
				this.shapes.remove(j);
			}
		}else {
			for(int i = 0; i < state.length; i++) {
				if(i < this.shapes.size()) {
					this.shapes.remove(i);
				}
				this.shapes.add(i, state[i]);
			}
		}
	}
	
	private void saveXML(String path) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(this.shapes);
			encoder.close();
			fos.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadXML(String path) {
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			XMLDecoder decoder = new XMLDecoder(fis);
			this.shapes = (ArrayList<Shape>)decoder.readObject();
			this.careTaker = new CareTaker();
			decoder.close();
			fis.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
