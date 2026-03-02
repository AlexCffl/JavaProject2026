package modele;

public class Partie {

	public static void main(String[] args) {
		System.out.println("initialisation de la partie");

		Deck deckJoueur1 = new Deck();

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

		System.out.println(" main de depart de " + joueur1.getNom().toUpperCase());
		for (int i = 0; i < joueur1.getMain().size(); i++) {
			Card carteEnMain = joueur1.getMain().get(i);
		
			String descriptionCarte = carteEnMain.toString().replace("\n", " | "); 
			System.out.println("carte " + (i + 1) + " : " + descriptionCarte);
		}
		
		System.out.println("cartes restantes dans le deck : " + joueur1.getDeck().size());
	}
}