package eg.edu.alexu.csd.oop.memento;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Memento {

	private Shape[] state;

	public Memento(Shape[] shapes) {
		this.state = shapes;
	}

	public Shape[] getState() {
		return state;
	}
		
}
