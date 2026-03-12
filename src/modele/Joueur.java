package modele;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	
	private String nom;
	private int pointsDeVie;
	private Deck deck; 
	private ArrayList<Card> main; 
	private ArrayList<Card> terrain;
	private ArrayList<Card> cimetiere;
	private int reserveMana;

	public Joueur(String nom, Deck deckDeDepart) {
		this.nom = nom;
		this.pointsDeVie = 20; 
		this.deck = deckDeDepart;	
		this.main = new ArrayList<>();
		this.terrain = new ArrayList<>();
		this.cimetiere = new ArrayList<>();
		this.reserveMana = 0;
	}

	public void piocherCarte() {
		if (!deck.isEmpty()) { 
			main.add(deck.draw());
		} else {
			this.pointsDeVie = 0; 
		}
	}


	public void piocherMainDeDepart() {
		for (int i = 0; i < 7; i++) {
			piocherCarte();
		}
	}

	public void jouerCarte(Card carte) {
		if (main.contains(carte)) {
			main.remove(carte);
			terrain.add(carte);
		}
	}

	public void subirDegats(int degats) {
		this.pointsDeVie -= degats;
	}


	public boolean estVivant() {
		return this.pointsDeVie > 0;
	}

	
	public String getNom() {
		return nom;
	}

	public int getPointsDeVie() {
		return pointsDeVie;
	}

	public Deck getDeck() {
		return deck;
	}

	public ArrayList<Card> getMain() {
		return main;
	}

	public ArrayList<Card> getTerrain() {
		return terrain;
	}

	public ArrayList<Card> getCimetiere() {
		return cimetiere;
	}
	
	public int getReserveMana() {
		return reserveMana;
	}
}