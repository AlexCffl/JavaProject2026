package modele;

import java.util.ArrayList;

public class Joueur {
	
	private String nom;
	private int pointsDeVie;
	private Deck deck; 
	private ArrayList<Card> main; 
	private ArrayList<Card> terrain;
	private ArrayList<Card> cimetiere;
	private int reserveMana;
	private int manaMax;

	public Joueur(String nom, Deck deckDeDepart) {
		this.nom = nom;
		this.pointsDeVie = 20; 
		this.deck = deckDeDepart;	
		this.main = new ArrayList<>();
		this.terrain = new ArrayList<>();
		this.cimetiere = new ArrayList<>();
		this.reserveMana = 0;
		this.manaMax = 0;
	}
	
	public void nouveauTour() {
		if (this.manaMax < 10) {
			this.manaMax += 1; 
		}
		for (Card carte : terrain) {
			carte.setTapped(false);
			carte.setPvPerdus(0);
			carte.setPeutAttaquer(true);
		}
		this.reserveMana = this.manaMax; 
		
		System.out.println("debut du tour de " + this.nom);
		System.out.println(" Mana rechargé : " + this.reserveMana + "/" + this.manaMax);
		
		piocherCarte();
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
		if (!main.contains(carte)) return; 

		int cout = carte.getTotalCost(); 
			
		if (this.reserveMana >= cout) {
			this.reserveMana -= cout; 
			main.remove(carte);

			if (carte.getEffects().contains(Card.Effects.HASTE)) {
				carte.setPeutAttaquer(true);
			} else {
				carte.setPeutAttaquer(false);
			}

			terrain.add(carte);
			System.out.println(this.nom + " invoque une créature en payant " + cout + " mana ! (Reste: " + this.reserveMana + ")");
		} else {
			System.out.println(this.nom + " n'a pas assez de mana (" + this.reserveMana + "/" + cout + ") pour la jouer !");
		}
	}

	public void perdreCarte(Card carte) {
		terrain.remove(carte);
		cimetiere.add(carte);
	}
	
	public void subirDegats(int degats) {
		this.pointsDeVie -= degats;
	}

	public boolean estVivant() {
		return this.pointsDeVie > 0;
	}
	
	
	public String getNom() { return nom; }
	public int getPointsDeVie() { return pointsDeVie; }
	public Deck getDeck() { return deck; }
	public ArrayList<Card> getMain() { return main; }
	public ArrayList<Card> getTerrain() { return terrain; }
	public ArrayList<Card> getCimetiere() { return cimetiere; }
	public int getReserveMana() { return reserveMana; }
	public int getManaMax() { return manaMax; }
}