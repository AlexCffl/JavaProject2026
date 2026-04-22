package modele;

public class Partie {

	private Joueur j1;
	private Joueur j2;
	private Joueur joueurCourant;
	private Joueur joueurAdverse;
	private boolean isOver;
	
	public Partie(Joueur j1, Joueur j2) {
		this.j1 = j1;
		this.j2 = j2;
		isOver = false;
		this.joueurCourant = j1;
		this.joueurAdverse = j2;
		j1.piocherMainDeDepart();
		j2.piocherMainDeDepart();
	}
	
	public Joueur getJ1() {
	    return j1;
	}

	public Joueur getJ2() {
	    return j2;
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public Joueur getJoueurAdverse() {
		return joueurAdverse;
	}

	public void demarrerTour() {
		joueurCourant.nouveauTour();
	}

	public void passerAuTourSuivant() {
		Joueur temp = joueurCourant;
		joueurCourant = joueurAdverse;
		joueurAdverse = temp;
		joueurCourant.nouveauTour();
	}

	public Pair<Pair<Boolean,Boolean>,Integer> combat1v1(Card attaquant, Card defenseur) {
		var attaquantSurvit = attaquant.subirDgts(defenseur.getAtk());
		var defenseurSurvit = defenseur.subirDgts(attaquant.getAtk());
		var survivants = new Pair<Boolean,Boolean>(attaquantSurvit, defenseurSurvit);
		var extraDmg = 0;

		if (attaquant.getEffects().contains(Card.Effects.TRAMPLE) && attaquant.getAtk() > defenseur.getDef()) {
			extraDmg += attaquant.getAtk() - defenseur.getDef();
		}

		return new Pair<Pair<Boolean,Boolean>,Integer>(survivants, extraDmg);
	}

	public boolean attaqueAutorisee(Card attaquant, Card defenseur) {
		if (attaquant == null || defenseur == null) {
			return false;
		}

		if (defenseur.getEffects().contains(Card.Effects.FLYING)
				&& !attaquant.getEffects().contains(Card.Effects.FLYING)) {
			return false;
		}

		return true;
	}

	public void attaqueDirecte(Card attaquant) {
		if (attaquant == null) {
			return;
		}

		if (!joueurCourant.getTerrain().contains(attaquant)) {
			return;
		}

		if (attaquant.isTapped() || !attaquant.isPeutAttaquer()) {
			return;
		}

		joueurAdverse.subirDegats(attaquant.getAtk());
		attaquant.setTapped(true);
	}

	public void resoudreCombatCarte(Card attaquant, Card defenseur) {
		if (attaquant == null || defenseur == null) {
			return;
		}

		if (!joueurCourant.getTerrain().contains(attaquant)) {
			return;
		}

		if (!joueurAdverse.getTerrain().contains(defenseur)) {
			return;
		}

		if (attaquant.isTapped() || !attaquant.isPeutAttaquer()) {
			return;
		}

		if (!attaqueAutorisee(attaquant, defenseur)) {
			return;
		}

		var resultats = combat1v1(attaquant, defenseur);

		boolean attaquantSurvit = resultats.getLeft().getLeft();
		boolean defenseurSurvit = resultats.getLeft().getRight();
		int degatsSupplementaires = resultats.getRight();

		if (!attaquantSurvit) {
			joueurCourant.perdreCarte(attaquant);
		} else {
			attaquant.setTapped(true);
		}

		if (!defenseurSurvit) {
			joueurAdverse.perdreCarte(defenseur);
		}

		if (degatsSupplementaires > 0) {
			joueurAdverse.subirDegats(degatsSupplementaires);
		}
	}
}