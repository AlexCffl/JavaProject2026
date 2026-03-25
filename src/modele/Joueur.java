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

		if (carte instanceof CreatureCard) {
			CreatureCard creature = (CreatureCard) carte; 
			int cout = creature.getTotalCost(); 
			
			if (this.reserveMana >= cout) {
				this.reserveMana -= cout; 
				main.remove(carte);
				terrain.add(carte);
				System.out.println(this.nom + " invoque une créature en payant " + cout + " mana ! (Reste: " + this.reserveMana);
			} else {
				System.out.println(this.nom + " n'a pas assez de mana (" + this.reserveMana + "/" + cout + ") pour la jouer !");
			}
		}
	}

	public void attaquer(CreatureCard attaquant, Joueur adversaire) {
		if (this.terrain.contains(attaquant) && !attaquant.isTapped()) {
			
			int degats = attaquant.getAtk();
			adversaire.subirDegats(degats); 
			attaquant.setTapped(true);      
			
			System.out.println(this.nom + " attaque avec une créature (" + degats + " ATK) !");
			System.out.println(adversaire.getNom() + " subit " + degats + " dégâts. Il lui reste " + adversaire.getPointsDeVie() + " PV.");
			
		} else {
			System.out.println("Impossible d'attaquer avec cette carte (pas sur le terrain ou déjà engagée)");
		}
	}

	public void subirDegats(int degats) {
		this.pointsDeVie -= degats;
	}

	public boolean estVivant() {
		return this.pointsDeVie > 0;
	}
	
	// Getters
	public String getNom() { return nom;}
	public int getPointsDeVie() { return pointsDeVie;}
	public Deck getDeck() { return deck; }
	public ArrayList<Card> getMain() { return main;}
	public ArrayList<Card> getTerrain() { return terrain;}
	public ArrayList<Card> getCimetiere() { return cimetiere;}
	public int getReserveMana() { return reserveMana;}
	public int getManaMax() { return manaMax;}
}