package eg.edu.alexu.csd.oop.memento;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Originator {

	private Shape[] state;

	public Shape[] getState() {
		return state;
	}

	public void setState(Shape[] state) {
		this.state = new Shape[state.length];
		for(int i = 0; i < state.length; i++) {
			try {
				this.state[i] = (Shape)state[i].clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}	

	public Memento saveStateToMemento() {
		return new Memento(state);
	}
	
	public void getStateFromMemento(Memento memento) {
		state = memento.getState();
	}
}
