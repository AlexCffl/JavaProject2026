package modele;


import java.util.Stack;

public class Deck extends Stack<Card>{

	private static final long serialVersionUID = -2786129281330759002L;
	private String name;

	public Deck(String nom, Card[] decklist) {
		super();
		name = nom;
		for (var carte : decklist) {
			push(carte);
		}
	}
	
	
	
	public void shuffle() {
		java.util.Collections.shuffle(this);
	}
	
	public Card draw() {
		return pop();
	}
	
	
	@Override
	public synchronized String toString() {
		var retour = "Nom : " + name + "\nCartes : [\n";
		for (Card card : this) {
			if (card != null) {
				retour += card.toString();
			}
		}
		return retour + ']';
	}
}