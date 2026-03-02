package modele;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	
	private String nom;
	private int pointsDeVie;
	private Deck deck; 
	private List<Card> main; 
	private List<Card> terrain;
	private List<Card> cimetiere;
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
			Card cartePiochee = deck.draw(); 
			main.add(cartePiochee);
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

	public List<Card> getMain() {
		return main;
	}

	public List<Card> getTerrain() {
		return terrain;
	}

	public List<Card> getCimetiere() {
		return cimetiere;
	}
	
	public int getReserveMana() {
		return reserveMana;
	}
}