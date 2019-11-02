package eg.edu.alexu.csd.oop.memento;

import java.util.ArrayList;

public class CareTaker {

	private ArrayList<Memento> mementoList = new ArrayList<Memento>();
	private int index = 0;
	
	public void addMemento(Memento memento) {
		if (this.index < this.mementoList.size()-1) {
			for(int i = this.mementoList.size() - 1; i >= index + 1 ; i--) {
				this.mementoList.remove(i);
			}
		}
		if(this.mementoList.size() == 21) {
			this.mementoList.remove(0);
		}
		this.mementoList.add(memento);
		this.index = this.mementoList.indexOf(memento);
	}
	
	public Memento getMemento(int index) {
		if (index < 0 || index >= this.mementoList.size()) {
			return null;
		}
		this.index = index;
		return this.mementoList.get(index);
	}

	public int getIndex() {
		return index;
	}
}
