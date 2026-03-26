package modele;

import java.io.File;

import modele.CreatureCard.Effects;

public class Partie {

	public static void main(String[] args) {
		System.out.println("initialisation de la partie");

		Deck deckJoueur1 = new Deck("deck test");

		CreatureCard.Effects[] pasDeffets = {Effects.FLYING,Effects.HASTE}; 
		for (int i = 0; i < 20; i++) {
			deckJoueur1.push(new CreatureCard(Card.Colors.RED, "Gobelin", 2, 1, pasDeffets, 1, 1));
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
		Joueur joueur2 = new Joueur("joeurX", deckJoueur2);

		
		//debut du tour (gagne 1 mana max, recharge la jauge, pioche 1 carte)
		joueur1.nouveauTour();
		
		//on cherche une créature dans la main pour la jouer
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
		
		System.out.println("COMBAT");
		if (creatureAJouer != null) {
			joueur1.attaquer(creatureAJouer, joueur2);
		}

		
		System.out.println("cartes restantes dans le deck : " + joueur1.getDeck().size());
		
		//test de sauvegarde du deckJoueur1
		var file = new File("testDeck.txt");
		deckJoueur1.serialize(file);
		System.out.println("Deck sauvegardé dans " + file.getName());
		System.out.println(deckJoueur1.toString());
	}
}