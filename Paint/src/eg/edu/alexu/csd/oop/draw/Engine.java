package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import eg.edu.alexu.csd.oop.json.*;
import eg.edu.alexu.csd.oop.test.ReflectionHelper;

public class Engine implements DrawingEngine {

	private Originator originator;
	private CareTaker careTaker;
	
	private ArrayList<Shape> shapes;
	
	private List<Class<? extends Shape>> supportedCls;
	
	public Engine() {
		supportedCls = new ArrayList<Class<? extends Shape>>();
		setInitialCls();
		installPluginShape("\\Paint\\RoundRectangle.jar");
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
		return this.supportedCls;
	}
//	public List<Class<? extends Shape>> getaddedShapes() {
//		return this.addedCls;
//	}
//	public void setarr(ArrayList<Shape>shapes){
//		this.shapes=shapes;
//	}

	
	
	@SuppressWarnings("unchecked")
	public void installPluginShape(String jarPath) {
		try {
			File jar = new File(jarPath);
			URL fileURL = jar.toURI().toURL();
			String jarURL = "jar:" + fileURL + "!/";
			URL[] urls = {new URL(jarURL)};
			URLClassLoader ucl = new URLClassLoader(urls, this.getClass().getClassLoader());
			String name = jarPath.replaceAll("/", "\\").substring(jarPath.lastIndexOf('\\') + 1, jarPath.indexOf('.'));
			Class<?> c = Class.forName(this.getClass().getPackage().getName() + "." + name, true, ucl);
			this.supportedCls.add((Class<? extends Shape>) c);
//			this.addedCls.add((Class<? extends Shape>) c);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void undo() {
		getDesiredState(this.careTaker.getIndex() - 1);
	}

	public void redo() {
		getDesiredState(this.careTaker.getIndex() + 1);
	}

	public void save(String path) {
		if(path.toLowerCase().contains(".xml")) {
			saveXML(path);
		}else if(path.toLowerCase().contains(".json")) {
			saveJSON(path);
		}
	}

	public void load(String path) {
		if(path.toLowerCase().contains(".xml")) {
			loadXML(path);
			this.careTaker = new CareTaker();
		}
        else if(path.toLowerCase().contains(".json")) {
            loadJSON(path);
            this.careTaker = new CareTaker();
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
	
	private void saveJSON(String path) {
		JSONObject obj = new JSONObject();
		JSONArray shapes = new JSONArray();
		for(int i = 0; i < this.shapes.size(); i++) {
			shapes.add(this.shapes.get(i));
		}
		obj.put("Shapes", shapes);
		
		JSONWriter writer = new JSONWriter(obj);
		new JSONParser(writer, path);
	}

	@SuppressWarnings("unchecked")
	private void loadXML(String path) {
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			XMLDecoder decoder = new XMLDecoder(fis);
			this.shapes = (ArrayList<Shape>)decoder.readObject();
			decoder.close();
			fis.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
    private void loadJSON(String path) {
        JSONFileReader x=new JSONFileReader();
	    try {
            this.shapes=x.output(path,this.getSupportedShapes());
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
	
    @SuppressWarnings("unchecked")
	private void setInitialCls() {
		List<Class<?>> cls = ReflectionHelper.findClassesImpmenenting(Shape.class, this.getClass().getPackage());
		for(Class<?> c : cls) {
			supportedCls.add((Class<? extends Shape>) c);
		}
	}
	
}
