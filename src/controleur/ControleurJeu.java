package controleur;

import modele.Partie;
import modele.Card;
import vue.FenetreJeu;

public class ControleurJeu {
    private Partie modele;
    private FenetreJeu vue;
    private Card attaquantSelectionne = null;

    public ControleurJeu(Partie modele, FenetreJeu vue) {
        this.modele = modele;
        this.vue = vue;
        
        this.vue.setControleur(this);
            

        this.vue.getBoutonFinDeTour().addActionListener(e -> {
            poursuivrePartie();
        });
        this.vue.getBoutonAttaquer().addActionListener(e ->{
        	lancerAssaut();
        });
    }

    public void poursuivrePartie() {
        System.out.println("clic controleur reçu, passage au tour suivant");
        
        modele.getJ1().nouveauTour();
        vue.actualiser(modele.getJ1(), modele.getJ2());
    }
    
    public void jouerCarte(Card carte) {
    	System.out.println("tentative de jouer la carte : " + carte.getName());
    	modele.getJ1().jouerCarte(carte);
    	vue.actualiser(modele.getJ1(), modele.getJ2());
    }
    
    public void lancerAssaut() {
        int degatsTotaux = 0;     
        for (Card c : modele.getJ1().getTerrain()) {
            if (!c.isTapped()) {
                degatsTotaux += c.getAtk();
                c.setTapped(true);
            }
        }
        if (degatsTotaux > 0) {
            System.out.println("Assaut! " + degatsTotaux + " degats infligés");
            modele.getJ2().subirDegats(degatsTotaux);
        } else {
            System.out.println("aucune carte disponible pour attaquer !");
        }
        vue.actualiser(modele.getJ1(), modele.getJ2());
    }

    public void clicSurCarteTerrain(Card carteCliquee, boolean estAmi) {   
        if (estAmi) {
            if (!carteCliquee.isTapped()) {
                attaquantSelectionne = carteCliquee;
                System.out.println("attaquant pret: " + carteCliquee.getName());
            } else {
                System.out.println("cette carte est épuisée pour ce tour !");
            }
            vue.actualiser(modele.getJ1(), modele.getJ2());
            
        } else {
            if (attaquantSelectionne != null) {
                System.out.println( attaquantSelectionne.getName() + carteCliquee.getName());
                
                int defRestante = carteCliquee.getDef() - attaquantSelectionne.getAtk();
                
                if (defRestante <= 0) {
                    System.out.println(carteCliquee.getName() + " adverse est detruite");
                    modele.getJ2().perdreCarte(carteCliquee); 
                } else {
                    System.out.println("la carte adverse survit");
                }
                attaquantSelectionne.setTapped(true);
                attaquantSelectionne = null; 
                
                vue.actualiser(modele.getJ1(), modele.getJ2());
            }
        }
    }
    public Card getAttaquantSelectionne() {
        return attaquantSelectionne;
    }
}