package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class Deck extends Stack<Card>{

	private static final long serialVersionUID = -2786129281330759002L;
	private String name;

	
	public Deck(String nom) {
		super();
		name = nom;
	}
	
	public void shuffle() {
		java.util.Collections.shuffle(this);
	}
	
	public Card draw() {
		return pop();
	}
	
	public void serialize(File file) {
		try (var fos = new FileOutputStream(file); var oos = new ObjectOutputStream(fos)){
			oos.writeChars(toString());
	    } catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable");
			e.printStackTrace();
		} catch (IOException e) {
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