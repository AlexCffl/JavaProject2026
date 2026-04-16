package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

public class Partie {

	private Joueur j1;
	private Joueur j2;
	private boolean isOver;
	
	public Partie(Joueur j1, Joueur j2) {
		this.j1 = j1;
		this.j2 = j2;
		isOver = false;
	}
	
	public Joueur getJ1() {
	    return j1;
	}

	public Joueur getJ2() {
	    return j2;
	}
	
	public Pair<Pair<Boolean,Boolean>,Integer> combat1v1 (Card attaquant, Card defenseur) {
		var attaquantSurvit = attaquant.subirDgts(defenseur.getAtk());
		var defenseurSurvit = defenseur.subirDgts(attaquant.getAtk());
		var survivants = new Pair<Boolean,Boolean>(attaquantSurvit,defenseurSurvit);
		var extraDmg = 0;
		if (attaquant.getEffects().contains(Card.Effects.TRAMPLE) && attaquant.getAtk()>defenseur.getDef()) {
			extraDmg += attaquant.getAtk()-defenseur.getDef();
		}
		return new Pair<Pair<Boolean,Boolean>,Integer>(survivants,extraDmg);
	}
	
	public void combat(Joueur attaquant, Joueur defenseur) {		
		var attaquants = attaquant.attaquer();
		var défenses = defenseur.defendre(attaquants);
		var combats = défenses.getLeft();
		var extraDmg = 0;
		for (var combattants : combats) {
			var résultats = combat1v1(combattants.getLeft(),combattants.getRight());
			if (!résultats.getLeft().getLeft()) {
				attaquant.perdreCarte(combattants.getLeft());
			}
			if (!résultats.getLeft().getRight()) {
				defenseur.perdreCarte(combattants.getRight());
			}
			extraDmg += résultats.getRight();
		}
		for (var attaquantQuiPasse : défenses.getRight()) {
			extraDmg += attaquantQuiPasse.getAtk();
		}
		defenseur.subirDegats(extraDmg);
	}
	
	public static void main(String[] args) {
		System.out.println("initialisation de la partie");

		Deck deckJoueur1 = new Deck("deck test");

		var effets = new TreeSet<Card.Effects>();  
		for (int i = 0; i < 20; i++) {
			deckJoueur1.push(new Card(Card.Colors.RED, "Gobelin", false, 2, 1, effets, 1, 1));
		}

		System.out.println("Melange du deck...");
		deckJoueur1.shuffle();

		Joueur joueur1 = new Joueur("JoueurA", deckJoueur1);
		System.out.println("Joueur " + joueur1.getNom() + " créé avec " + joueur1.getPointsDeVie() + " PV.");

		System.out.println(joueur1.getNom() + " pioche ses 7 cartes...");
		joueur1.piocherMainDeDepart();

		System.out.println(" main de depart de " + joueur1.getNom().toUpperCase() );
		for (int i = 0; i < joueur1.getMain().size(); i++) {
			Card carteEnMain = joueur1.getMain().get(i);
			System.out.println("carte " + (i + 1) + " : " + carteEnMain.toString().replace("\n", ""));
		}
		
		
		
		Deck deckJoueur2 = new Deck("Deck Cible");
		for (int i = 0; i < 20; i++) {
			deckJoueur2.push(new Card(Card.Colors.BLUE, "Elfe", false, 1, 2, effets, 1, 1));
		}
		deckJoueur2.shuffle(); 
		Joueur joueur2 = new Joueur("joeurX", deckJoueur2);
		
		joueur2.piocherMainDeDepart();
		Partie moteurPartie = new Partie(joueur1, joueur2);
		int tour = 1;
		System.out.println("debut de la bataille");
		

		while (joueur1.estVivant() && joueur2.estVivant()) {
			System.out.println(" TOUR " + tour);
			
			joueur1.nouveauTour();
			
			if (!joueur1.getMain().isEmpty()) {
				joueur1.jouerCarte(joueur1.getMain().get(0));
			}
			
			System.out.println("> Phase d'attaque de " + joueur1.getNom());
			moteurPartie.combat(joueur1, joueur2);
			System.out.println("PV restants de " + joueur2.getNom() + " : " + joueur2.getPointsDeVie());
			
			if (!joueur2.estVivant()) {
				break;
			}
			
			joueur2.nouveauTour();
			
			if (!joueur2.getMain().isEmpty()) {
				joueur2.jouerCarte(joueur2.getMain().get(0));
			}
			
			System.out.println("> Phase d'attaque de " + joueur2.getNom());
			moteurPartie.combat(joueur2, joueur1);
			System.out.println("PV restants de " + joueur1.getNom() + " : " + joueur1.getPointsDeVie());
		
			tour++;
		}
		System.out.println(" fin de la partie ! ");
		
		if (joueur1.estVivant()) {
			System.out.println("   " + joueur1.getNom() + " a gagné !");
		} else {
			System.out.println("   " + joueur2.getNom() + " a gagné !");
		}
		
		System.out.println("cartes restantes dans le deck J1 : " + joueur1.getDeck().size());	
		var file = new File("testDeck.txt");
		deckJoueur1.serialize(file);
		System.out.println("Deck sauvegardé dans " + file.getName());
		Deck deckRecharge = new Deck(file);
		System.out.println("deck rechargé avec succès. il contient : " + deckRecharge.size() + " cartes.");
	}
	
}