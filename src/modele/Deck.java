package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class Deck extends Stack<Card>{

	private static final long serialVersionUID = -2786129281330759002L;
	private String name;

	
	public Deck(String nom) {
		super();
		name = nom;
	}
	
	public Deck(File file) {
		super();
		try (var fis = new FileInputStream(file); var ois = new ObjectInputStream(fis)) {
			// on lit tout le deck d'un seul coup depuis le fichier 
			Deck deckCharge = (Deck) ois.readObject(); 
			this.name = deckCharge.name;
			this.addAll(deckCharge); // on remet toutes les cartes dans ce deck
		} catch (Exception e) {
			System.out.println("Erreur lors du chargement du deck.");
			e.printStackTrace();
		}
	}
	
	public void shuffle() {
		java.util.Collections.shuffle(this);
	}
	
	public Card draw() {
		return pop();
	}
	
	public void serialize(File file) {
		try (var fos = new FileOutputStream(file); var oos = new ObjectOutputStream(fos)) {
			// on sauvegarde tout l'objet deck (qui contient toutes ses cartes) en une ligne 
			oos.writeObject(this); 
		} catch (Exception e) {
			System.out.println("Erreur lors de la sauvegarde.");
			e.printStackTrace();
		}
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