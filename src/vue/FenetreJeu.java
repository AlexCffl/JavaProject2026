package vue;

import javax.swing.*;

import controleur.ControleurJeu;
import modele.Deck;
import modele.Joueur;
import modele.Partie;

import java.awt.*;

public class FenetreJeu extends JFrame {

    private JLabel labelPvAdversaire;
    private JLabel labelPvJoueur;
    private JButton boutonFinDeTour;

    public FenetreJeu() {
        this.setTitle("plateau");
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());

        JPanel panelAdversaire = new JPanel();
        panelAdversaire.setBackground(new Color(150, 50, 50));
        labelPvAdversaire = new JLabel("Adversaire : 20 PV");
        labelPvAdversaire.setForeground(Color.WHITE);
        labelPvAdversaire.setFont(new Font("Arial", Font.BOLD, 20));
        panelAdversaire.add(labelPvAdversaire);
        
        JPanel panelJoueur = new JPanel();
        panelJoueur.setBackground(new Color(50, 100, 150)); 
        labelPvJoueur = new JLabel("Joueur 1 : 20 PV | Mana : 1/1");
        labelPvJoueur.setForeground(Color.WHITE);
        labelPvJoueur.setFont(new Font("Arial", Font.BOLD, 20));
        panelJoueur.add(labelPvJoueur);

        JPanel panelPlateau = new JPanel();
        panelPlateau.setBackground(new Color(40, 40, 40)); 
        JLabel textePlateau = new JLabel("endroit pour lescartes");
        textePlateau.setForeground(Color.LIGHT_GRAY);
        panelPlateau.add(textePlateau);

        JPanel panelActions = new JPanel();
        panelActions.setLayout(new GridLayout(3, 1, 10, 10));
        panelActions.setBackground(new Color(60, 60, 60));
        
        boutonFinDeTour = new JButton("passer le tour");
        boutonFinDeTour.setFont(new Font("Arial", Font.BOLD, 16));
        panelActions.add(boutonFinDeTour); 

        this.add(panelAdversaire, BorderLayout.NORTH);
        this.add(panelJoueur, BorderLayout.SOUTH);
        this.add(panelPlateau, BorderLayout.CENTER);
        this.add(panelActions, BorderLayout.EAST);
    }

    public static void main(String[] args) {
    	Deck d1 = new Deck("Deck J1");
        Joueur j1 = new Joueur("JoueurA", d1);
        Joueur j2 = new Joueur("JoueurX", new Deck("Deck J2"));
        Partie monModele = new Partie(j1, j2);

        FenetreJeu maVue = new FenetreJeu();

        new ControleurJeu(monModele, maVue);

        maVue.setVisible(true);
    }
    
    public void actualiser(Joueur j1, Joueur j2) {
        labelPvJoueur.setText(j1.getNom() + " : " + j1.getPointsDeVie() + " PV");
        labelPvAdversaire.setText(j2.getNom() + " : " + j2.getPointsDeVie() + " PV");
    }

    public JButton getBoutonFinDeTour() {
        return boutonFinDeTour;
    }
}