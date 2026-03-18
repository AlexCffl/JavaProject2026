package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum Colors {RED,WHITE,GREEN,BLUE,DARK,NONE};
	public enum Type {CREATURE,LAND};
	
	private Type type; 
	private Colors color;
	private String name;
	private boolean tapped;	
	
	public Card(Type type, Colors color, String nom ) {
		this.type = type;
		this.color = color;
		name = nom;
		tapped = false;
	}
	
	public Card loadCard (File file) {
		Card card = null;
		// Code de chargement mis en commentaire pour corriger les erreurs de syntaxe
		/*
		try (var fis = new FileInputStream(file); var ois = new ObjectInputStream(fis)){
			var serializedCard = ois.readLine().split("\\|");
			
			var leTypeStr = serializedCard[0].split(":")[1];
			Type leType = null;
			
			switch (leTypeStr) {
			case " LAND" :
				leType = Type.LAND;
				break;
			case " CREATURE" :
				leType = Type.CREATURE;
				break;
			}
			
			var laCouleurStr = serializedCard[1].split(":")[1];
			Colors laCouleur = null;
			
			// switch (laCouleurStr) {
			// case " RED" :
			// 	laCouleur = 
			// }
			
			var leNom = serializedCard[2].split(":")[1];
			var engage = serializedCard[3].split(":")[1].equals(" true");
			
	    } catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		return card;		
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
	
	public Colors getColor() {
		return color;
	}
	
	public boolean isTapped() {
		return tapped;
	}
	
	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}
	
	@Override
	public String toString() {
		return "Type : " + type + " | color : " + color + " | nom : " + name + " | tapped : " + tapped;		
	}
}