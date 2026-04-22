package vue;

import javax.swing.*;

import controleur.ControleurJeu;
import modele.Card;
import modele.Deck;
import modele.Joueur;
import modele.Partie;

import java.awt.*;
import java.util.TreeSet;

public class FenetreJeu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel labelPvAdversaire;
    private JLabel labelPvJoueur;
    private JLabel labelTour;
    private JLabel labelMessage;
    private JButton boutonFinDeTour;
    private JButton boutonAttaquer;
    private JPanel panelMainJoueur;
    private JPanel panelTerrainAdversaire;
    private JPanel panelTerrainJoueur;
    private ControleurJeu controleur;

    public FenetreJeu() {
        this.setTitle("plateau");
        this.setSize(1150, 850);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        JPanel panelAdversaire = new JPanel();
        panelAdversaire.setBackground(new Color(150, 50, 50));
        labelPvAdversaire = new JLabel("Adversaire : 20 PV");
        labelPvAdversaire.setForeground(Color.WHITE);
        labelPvAdversaire.setFont(new Font("Arial", Font.BOLD, 20));
        panelAdversaire.add(labelPvAdversaire);

        JPanel panelJoueur = new JPanel();
        panelJoueur.setLayout(new BorderLayout());
        panelJoueur.setBackground(new Color(50, 100, 150));

        JPanel panelInfosJoueur = new JPanel(new GridLayout(2, 1));
        panelInfosJoueur.setOpaque(false);

        labelTour = new JLabel("Tour de : Joueur 1");
        labelTour.setForeground(Color.YELLOW);
        labelTour.setFont(new Font("Arial", Font.BOLD, 22));

        labelPvJoueur = new JLabel("Joueur 1 : 20 PV | Mana : 1/1");
        labelPvJoueur.setForeground(Color.WHITE);
        labelPvJoueur.setFont(new Font("Arial", Font.BOLD, 20));

        panelInfosJoueur.add(labelTour);
        panelInfosJoueur.add(labelPvJoueur);
        panelJoueur.add(panelInfosJoueur, BorderLayout.NORTH);

        panelMainJoueur = new JPanel();
        panelMainJoueur.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelMainJoueur.setOpaque(false);
        panelJoueur.add(panelMainJoueur, BorderLayout.CENTER);

        JPanel panelPlateau = new JPanel();
        panelPlateau.setLayout(new GridLayout(2, 1));

        panelTerrainAdversaire = new JPanel();
        panelTerrainAdversaire.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelTerrainAdversaire.setBackground(new Color(60, 40, 40));
        panelTerrainAdversaire.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Ligne de front adverse",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                Color.LIGHT_GRAY
        ));

        panelTerrainJoueur = new JPanel();
        panelTerrainJoueur.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelTerrainJoueur.setBackground(new Color(40, 60, 80));
        panelTerrainJoueur.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Votre ligne de front",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                Color.LIGHT_GRAY
        ));

        panelPlateau.add(panelTerrainAdversaire);
        panelPlateau.add(panelTerrainJoueur);
        this.add(panelPlateau, BorderLayout.CENTER);

        JPanel panelActions = new JPanel();
        panelActions.setLayout(new GridLayout(3, 1, 10, 10));
        panelActions.setBackground(new Color(60, 60, 60));

        boutonFinDeTour = new JButton("passer le tour");
        boutonFinDeTour.setFont(new Font("Arial", Font.BOLD, 16));
        panelActions.add(boutonFinDeTour);

        boutonAttaquer = new JButton("Attaque directe");
        boutonAttaquer.setFont(new Font("Arial", Font.BOLD, 16));
        boutonAttaquer.setBackground(new Color(200, 50, 50));
        boutonAttaquer.setForeground(Color.WHITE);
        panelActions.add(boutonAttaquer);

        labelMessage = new JLabel("Bienvenue dans la partie !");
        labelMessage.setForeground(Color.WHITE);
        labelMessage.setFont(new Font("Arial", Font.BOLD, 14));
        labelMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelMessage = new JPanel(new BorderLayout());
        panelMessage.setBackground(new Color(45, 45, 45));
        panelMessage.setPreferredSize(new Dimension(260, 100));
        panelMessage.add(labelMessage, BorderLayout.CENTER);

        this.add(panelAdversaire, BorderLayout.NORTH);
        this.add(panelJoueur, BorderLayout.SOUTH);
        this.add(panelPlateau, BorderLayout.CENTER);
        this.add(panelActions, BorderLayout.EAST);
        this.add(panelMessage, BorderLayout.WEST);
    }

    public void setControleur(ControleurJeu controleur) {
        this.controleur = controleur;
    }

    public static void main(String[] args) {
        var effets = new TreeSet<Card.Effects>();
        effets.add(Card.Effects.TRAMPLE);

        Card[] decklist1 = new Card[20];
        for (int i = 0; i < 20; i++) {
            decklist1[i] = new Card(Card.Colors.BLUE, "Elfe", false, 3, 2, effets, 1, 1);
        }

        Card[] decklist2 = new Card[20];
        for (int i = 0; i < 20; i++) {
            decklist2[i] = new Card(Card.Colors.BLUE, "Elfe", false, 3, 2, effets, 1, 1);
        }

        Deck d1 = new Deck("Deck J1", decklist1);
        Deck d2 = new Deck("Deck J2", decklist2);

        Joueur j1 = new Joueur("JoueurA", d1);
        Joueur j2 = new Joueur("JoueurX", d2);

        Partie monModele = new Partie(j1, j2);
        monModele.demarrerTour();

        FenetreJeu maVue = new FenetreJeu();

        new ControleurJeu(monModele, maVue);
        maVue.actualiser(monModele.getJoueurCourant(), monModele.getJoueurAdverse());
        maVue.setVisible(true);

        EcranDemarrage intro = new EcranDemarrage(maVue);
        intro.setVisible(true);

        if (!intro.isCommencer()) {
            maVue.dispose();
            return;
        }
    }

    public void actualiser(Joueur joueurCourant, Joueur joueurAdverse) {
        labelTour.setText("Tour de : " + joueurCourant.getNom());

        labelPvJoueur.setText(
                joueurCourant.getNom() + " : " + joueurCourant.getPointsDeVie()
                        + " PV | Mana : " + joueurCourant.getReserveMana() + "/" + joueurCourant.getManaMax()
                        + " | cimetiere : " + joueurCourant.getCimetiere().size()
                        + " | Deck : " + joueurCourant.getDeck().size()
        );

        labelPvAdversaire.setText(
                joueurAdverse.getNom() + " : " + joueurAdverse.getPointsDeVie()
                        + " PV | cimetiere : " + joueurAdverse.getCimetiere().size()
                        + " | Deck : " + joueurAdverse.getDeck().size()
        );

        panelMainJoueur.removeAll();
        for (Card c : joueurCourant.getMain()) {
            CarteGraphique vueCarte = new CarteGraphique(c);

            if (this.controleur != null) {
                if (c == controleur.getAttaquantSelectionne()) {
                    vueCarte.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                }

                java.awt.event.MouseAdapter listenerMain = new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        controleur.jouerCarte(c);
                    }
                };

                rendreCarteCliquable(vueCarte, listenerMain, Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            panelMainJoueur.add(vueCarte);
        }

        panelTerrainJoueur.removeAll();
        for (Card c : joueurCourant.getTerrain()) {
            CarteGraphique vueCarte = new CarteGraphique(c);

            if (this.controleur != null) {
                if (c == controleur.getAttaquantSelectionne()) {
                    vueCarte.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                }

                java.awt.event.MouseAdapter listenerTerrainAmi = new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        controleur.clicSurCarteTerrain(c, true);
                    }
                };

                rendreCarteCliquable(vueCarte, listenerTerrainAmi, Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }

            panelTerrainJoueur.add(vueCarte);
        }

        panelTerrainAdversaire.removeAll();
        for (Card c : joueurAdverse.getTerrain()) {
            CarteGraphique vueCarte = new CarteGraphique(c);

            if (this.controleur != null) {
                java.awt.event.MouseAdapter listenerTerrainAdverse = new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        controleur.clicSurCarteTerrain(c, false);
                    }
                };

                rendreCarteCliquable(vueCarte, listenerTerrainAdverse, Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }

            panelTerrainAdversaire.add(vueCarte);
        }

        panelMainJoueur.revalidate();
        panelMainJoueur.repaint();
        panelTerrainJoueur.revalidate();
        panelTerrainJoueur.repaint();
        panelTerrainAdversaire.revalidate();
        panelTerrainAdversaire.repaint();
    }

    private void rendreCarteCliquable(Component composant, java.awt.event.MouseListener listener, Cursor curseur) {
        composant.setCursor(curseur);
        composant.addMouseListener(listener);

        if (composant instanceof Container) {
            for (Component enfant : ((Container) composant).getComponents()) {
                rendreCarteCliquable(enfant, listener, curseur);
            }
        }
    }

    public void setMessage(String message) {
        labelMessage.setText(message);
    }

    public JButton getBoutonFinDeTour() {
        return boutonFinDeTour;
    }

    public JButton getBoutonAttaquer() {
        return boutonAttaquer;
    }
}