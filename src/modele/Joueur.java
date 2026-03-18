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
		if (!main.contains(carte)) return; 

		if (carte instanceof LandCard) {
			main.remove(carte);
			terrain.add(carte);
			System.out.println(this.nom + " pose un terrain : " + carte.getColor());
		} 
		else if (carte instanceof CreatureCard) {
			// carte generique >> creature pour pouvoir lire ses infos specifiques
			CreatureCard creature = (CreatureCard) carte; 
			
			// lecture du prix de la carte
			int cout = creature.getTotalCost(); 
			
			// 3.  vérifier assez de mana ?
			if (this.reserveMana >= cout) {
				this.reserveMana -= cout; 
				main.remove(carte);
				terrain.add(carte);
				System.out.println(this.nom + " invoque une créature en payant " + cout + " mana !");
			} else {
				System.out.println(this.nom + " n'a pas assez de mana (" + this.reserveMana + "/" + cout + ") pour la jouer !");
			}
		}
	}

	public void engagerTerrain(LandCard terrainCard) {
		if (this.terrain.contains(terrainCard) && !terrainCard.isTapped()) {
			terrainCard.setTapped(true);
			this.reserveMana += 1;
			System.out.println(this.nom + " engage " + terrainCard.getColor() + " et gagne 1 mana.");
		} else {
			System.out.println("impossible d'engager ce terrain.");
		}
	}

	public void subirDegats(int degats) {
		this.pointsDeVie -= degats;
	}

	public boolean estVivant() {
		return this.pointsDeVie > 0;
	}
	
	// Getters
	public String getNom() { return nom; }
	public int getPointsDeVie() { return pointsDeVie; }
	public Deck getDeck() { return deck; }
	public ArrayList<Card> getMain() { return main; }
	public ArrayList<Card> getTerrain() { return terrain; }
	public ArrayList<Card> getCimetiere() { return cimetiere; }
	public int getReserveMana() { return reserveMana; }
}