package modele;

import java.io.File;

public class Partie {

	public static void main(String[] args) {
		System.out.println("initialisation de la partie");

		Deck deckJoueur1 = new Deck("deck test");

		for (int i = 0; i < 10; i++) {
			deckJoueur1.push(new LandCard(Card.Colors.RED, "Montagne"));
		}

		CreatureCard.Effects[] pasDeffets = {}; 
		for (int i = 0; i < 10; i++) {
			deckJoueur1.push(new CreatureCard(Card.Colors.RED, "Gobelin", 2, 1, pasDeffets, 1, 1));
		}

		System.out.println("Melange du deck...");
		deckJoueur1.shuffle();

		Joueur joueur1 = new Joueur("A", deckJoueur1);
		System.out.println("Joueur " + joueur1.getNom() + " créé avec " + joueur1.getPointsDeVie() + " PV.");

		System.out.println(joueur1.getNom() + " pioche ses 7 cartes...");
		joueur1.piocherMainDeDepart();

		System.out.println(" main de depart de " + joueur1.getNom().toUpperCase() );
		for (int i = 0; i < joueur1.getMain().size(); i++) {
			Card carteEnMain = joueur1.getMain().get(i);
			System.out.println("carte " + (i + 1) + " : " + carteEnMain.toString().replace("\n", ""));
		}
		
		System.out.println("simulation tour 1 ");
		LandCard terrainAJouer = null;
		for (Card c : joueur1.getMain()) {
			if (c instanceof LandCard) { 
				terrainAJouer = (LandCard) c;
				break; 
			}
		}
		
		if (terrainAJouer != null) {
			joueur1.jouerCarte(terrainAJouer);
			joueur1.engagerTerrain(terrainAJouer);
		}

		CreatureCard creatureAJouer = null;
		for (Card c : joueur1.getMain()) {
			if (c instanceof CreatureCard) {
				creatureAJouer = (CreatureCard) c;
				break;
			}
		}
		
		if (creatureAJouer != null) {
			joueur1.jouerCarte(creatureAJouer);
		}
		
		System.out.println("cartes restantes dans le deck : " + joueur1.getDeck().size());
		
		// Test de sauvegarde de ton camarade
		var file = new File("testDeck.txt");
		deckJoueur1.serialize(file);
		System.out.println("Deck sauvegardé dans " + file.getName());
	}
}