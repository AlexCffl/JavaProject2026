package controleur;

import modele.Partie;
import vue.FenetreJeu;

public class ControleurJeu {
    private Partie modele;
    private FenetreJeu vue;

    public ControleurJeu(Partie modele, FenetreJeu vue) {
        this.modele = modele;
        this.vue = vue;

        this.vue.getBoutonFinDeTour().addActionListener(e -> {
            poursuivrePartie();
        });
    }

    public void poursuivrePartie() {
        System.out.println("clic controleur reçu et tour suivant");
        
        modele.getJ1().nouveauTour();
        

        vue.actualiser(modele.getJ1(), modele.getJ2());
    }
}