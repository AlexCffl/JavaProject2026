package controleur;

import javax.swing.JOptionPane;

import modele.Partie;
import modele.Card;
import vue.FenetreJeu;

public class ControleurJeu {
    private Partie modele;
    private FenetreJeu vue;
    private Card attaquantSelectionne = null;
    private boolean partieTerminee = false;

    public ControleurJeu(Partie modele, FenetreJeu vue) {
        this.modele = modele;
        this.vue = vue;

        this.vue.setControleur(this);

        this.vue.getBoutonFinDeTour().addActionListener(e -> {
            poursuivrePartie();
        });

        this.vue.getBoutonAttaquer().addActionListener(e -> {
            lancerAssaut();
        });
        mettreAJourBoutonAttaqueDirecte();
    }

    private void afficherMessage(String message) {
        System.out.println(message);
        vue.setMessage(message);
    }

    public void poursuivrePartie() {
        if (partieTerminee) {
            return;
        }

        afficherMessage("Passage au tour suivant...");

        attaquantSelectionne = null;
        modele.passerAuTourSuivant();
        vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
        mettreAJourBoutonAttaqueDirecte();
        verifierFinPartie();

        if (!partieTerminee && modele.getJoueurCourant() == modele.getJ2()) {
            jouerTourIA();
        } else if (!partieTerminee) {
            afficherMessage("À vous de jouer.");
        }
    }

    public void jouerCarte(Card carte) {
        if (partieTerminee) {
            return;
        }

        afficherMessage(modele.getJoueurCourant().getNom() + " joue " + carte.getName());
        modele.getJoueurCourant().jouerCarte(carte);
        vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
        mettreAJourBoutonAttaqueDirecte();
        verifierFinPartie();
    }

    public void lancerAssaut() {
        if (partieTerminee) {
            return;
        }

        if (!modele.getJoueurAdverse().getTerrain().isEmpty()) {
            JOptionPane.showMessageDialog(
                vue,
                "L'adversaire a encore des cartes sur le terrain.\nSélectionne une de tes cartes puis clique sur une carte adverse pour l'attaquer.",
                "Attaque impossible",
                JOptionPane.WARNING_MESSAGE
            );
            afficherMessage("Attaque directe impossible.");
            return;
        }

        boolean attaqueEffectuee = false;

        for (Card c : modele.getJoueurCourant().getTerrain()) {
            if (!c.isTapped() && c.isPeutAttaquer()) {
                modele.attaqueDirecte(c);
                attaqueEffectuee = true;
            }
        }

        if (attaqueEffectuee) {
            afficherMessage("Attaque directe effectuée.");
        } else {
            afficherMessage("Aucune carte disponible pour attaquer.");
        }

        vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
        mettreAJourBoutonAttaqueDirecte();
        verifierFinPartie();
    }

    public void clicSurCarteTerrain(Card carteCliquee, boolean estAmi) {
        if (partieTerminee) {
            return;
        }

        if (estAmi) {
            if (!carteCliquee.isTapped() && carteCliquee.isPeutAttaquer()) {
                attaquantSelectionne = carteCliquee;
                afficherMessage("Attaquant sélectionné : " + carteCliquee.getName());
            } else {
                afficherMessage("Cette carte ne peut pas attaquer ce tour.");
            }

            vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());

        } else {
            if (attaquantSelectionne != null) {
                afficherMessage(attaquantSelectionne.getName() + " attaque " + carteCliquee.getName());

                if (!modele.attaqueAutorisee(attaquantSelectionne, carteCliquee)) {
                    JOptionPane.showMessageDialog(
                        vue,
                        "Cette carte adverse a FLYING.\nIl faut aussi une carte avec FLYING pour l'attaquer.",
                        "Attaque impossible",
                        JOptionPane.WARNING_MESSAGE
                    );
                    afficherMessage("Attaque impossible à cause de FLYING.");
                    return;
                }

                modele.resoudreCombatCarte(attaquantSelectionne, carteCliquee);

                attaquantSelectionne = null;

                vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
                mettreAJourBoutonAttaqueDirecte();
                verifierFinPartie();
            }
        }
    }

    private void jouerTourIA() {
        if (partieTerminee) {
            return;
        }

        afficherMessage("Tour automatique de " + modele.getJoueurCourant().getNom());

        Card carteAJouer = null;
        for (Card c : modele.getJoueurCourant().getMain()) {
            if (c.getTotalCost() <= modele.getJoueurCourant().getReserveMana()) {
                carteAJouer = c;
                break;
            }
        }

        if (carteAJouer != null) {
            afficherMessage("L'IA joue : " + carteAJouer.getName());
            modele.getJoueurCourant().jouerCarte(carteAJouer);
            vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
            mettreAJourBoutonAttaqueDirecte();
            verifierFinPartie();
        }

        if (partieTerminee) {
            return;
        }

        Card attaquante = null;
        for (Card c : modele.getJoueurCourant().getTerrain()) {
            if (!c.isTapped() && c.isPeutAttaquer()) {
                attaquante = c;
                break;
            }
        }

        if (attaquante != null) {
            if (!modele.getJoueurAdverse().getTerrain().isEmpty()) {
                Card defenseurValide = null;

                for (Card defenseur : modele.getJoueurAdverse().getTerrain()) {
                    if (modele.attaqueAutorisee(attaquante, defenseur)) {
                        defenseurValide = defenseur;
                        break;
                    }
                }

                if (defenseurValide != null) {
                    afficherMessage("L'IA attaque " + defenseurValide.getName() + " avec " + attaquante.getName());
                    modele.resoudreCombatCarte(attaquante, defenseurValide);
                } else {
                    afficherMessage("L'IA ne trouve aucune cible autorisée.");
                }
            } else {
                afficherMessage("L'IA attaque directement avec " + attaquante.getName());
                modele.attaqueDirecte(attaquante);
            }

            vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
            mettreAJourBoutonAttaqueDirecte();
            verifierFinPartie();
        } else {
            afficherMessage("L'IA n'a aucune carte capable d'attaquer.");
        }

        if (partieTerminee) {
            return;
        }

        attaquantSelectionne = null;
        modele.passerAuTourSuivant();
        vue.actualiser(modele.getJoueurCourant(), modele.getJoueurAdverse());
        verifierFinPartie();

        if (!partieTerminee) {
            afficherMessage("À vous de jouer.");
        }
    }
    private void mettreAJourBoutonAttaqueDirecte() {
        if (partieTerminee) {
            vue.getBoutonAttaquer().setEnabled(false);
            return;
        }

        boolean adverseATerrain = !modele.getJoueurAdverse().getTerrain().isEmpty();
        vue.getBoutonAttaquer().setEnabled(!adverseATerrain);
    }
    

    private void verifierFinPartie() {
        if (!modele.getJoueurCourant().estVivant() || !modele.getJoueurAdverse().estVivant()) {
            partieTerminee = true;
            attaquantSelectionne = null;

            vue.getBoutonFinDeTour().setEnabled(false);
            vue.getBoutonAttaquer().setEnabled(false);

            String message;
            if (modele.getJoueurCourant().estVivant() && !modele.getJoueurAdverse().estVivant()) {
                message = modele.getJoueurCourant().getNom() + " a gagné !";
            } else if (!modele.getJoueurCourant().estVivant() && modele.getJoueurAdverse().estVivant()) {
                message = modele.getJoueurAdverse().getNom() + " a gagné !";
            } else {
                message = "Match nul !";
            }

            vue.setMessage(message);
            JOptionPane.showMessageDialog(vue, message, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Card getAttaquantSelectionne() {
        return attaquantSelectionne;
    }
}