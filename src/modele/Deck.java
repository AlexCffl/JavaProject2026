package modele;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Deck extends Stack<Card>{

	private static final long serialVersionUID = -2786129281330759002L;

	public Deck() {
		super();
	}
	
	public void shuffle() {
		var buffer = new ArrayList<Card>();
		for (int i = 0; i<size(); i++) {
			buffer.add(pop());
		}
		var rd = new Random();
		while (!buffer.isEmpty()) {
			push(buffer.get(rd.nextInt(buffer.size())));
		}
	}
	
	public Card draw() {
		return pop();
	}
}
