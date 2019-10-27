package eg.edu.alexu.csd.oop.draw;

public class Memento {

	private Shape[] state;

	public Memento(Shape[] shapes) {
		this.state = shapes;
	}

	public Shape[] getState() {
		return state;
	}
		
}
