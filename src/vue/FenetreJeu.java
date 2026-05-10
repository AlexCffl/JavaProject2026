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
        this.setTitle("Card Battle Arena");
        this.setSize(1150, 850);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // =========================
        // HAUT : ADVERSAIRE
        // =========================
        JPanel panelAdversaire = new JPanel();
        panelAdversaire.setBackground(new Color(12, 10, 18));
        panelAdversaire.setBorder(BorderFactory.createLineBorder(new Color(90, 70, 130), 2));

        labelPvAdversaire = new JLabel("Adversaire : 20 PV");
        labelPvAdversaire.setForeground(Color.WHITE);
        labelPvAdversaire.setFont(new Font("Arial", Font.BOLD, 20));

        panelAdversaire.add(labelPvAdversaire);

        // =========================
        // CENTRE : ARENE
        // =========================
        JPanel panelPlateau = new PanelImage("/images/fond_arene.jpg");
        panelPlateau.setLayout(new GridLayout(2, 1));

        panelTerrainAdversaire = new PanelZone(
                new Color(5, 15, 30, 135),
                new Color(0, 190, 255, 140)
        );
        panelTerrainAdversaire.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panelTerrainAdversaire.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 180, 255)),
                        "Ligne de front adverse",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 14),
                        Color.WHITE
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        panelTerrainJoueur = new PanelZone(
                new Color(5, 20, 45, 145),
                new Color(0, 220, 255, 160)
        );
        panelTerrainJoueur.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panelTerrainJoueur.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 180, 255)),
                        "Votre ligne de front",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 14),
                        Color.WHITE
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        panelPlateau.add(panelTerrainAdversaire);
        panelPlateau.add(panelTerrainJoueur);

        // =========================
        // BAS : JOUEUR + MAIN
        // =========================
        JPanel panelJoueur = new JPanel();
        panelJoueur.setLayout(new BorderLayout());
        panelJoueur.setBackground(new Color(15, 12, 18));
        panelJoueur.setBorder(BorderFactory.createLineBorder(new Color(120, 90, 180), 2));

        JPanel panelInfosJoueur = new JPanel(new GridLayout(2, 1));
        panelInfosJoueur.setOpaque(false);

        labelTour = new JLabel("Tour de : Joueur 1");
        labelTour.setForeground(new Color(210, 190, 255));
        labelTour.setFont(new Font("Arial", Font.BOLD, 22));

        labelPvJoueur = new JLabel("Joueur 1 : 20 PV | Mana : 1/1");
        labelPvJoueur.setForeground(Color.WHITE);
        labelPvJoueur.setFont(new Font("Arial", Font.BOLD, 20));

        panelInfosJoueur.add(labelTour);
        panelInfosJoueur.add(labelPvJoueur);

        panelJoueur.add(panelInfosJoueur, BorderLayout.NORTH);

        panelMainJoueur = new JPanel();
        panelMainJoueur.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelMainJoueur.setBackground(new Color(18, 14, 22));
        panelMainJoueur.setOpaque(true);
        panelMainJoueur.setBorder(BorderFactory.createLineBorder(new Color(90, 70, 130), 2));

        panelJoueur.add(panelMainJoueur, BorderLayout.CENTER);

        // =========================
        // DROITE : ACTIONS
        // =========================
        JPanel panelActions = new JPanel();
        panelActions.setLayout(new BoxLayout(panelActions, BoxLayout.Y_AXIS));
        panelActions.setBackground(new Color(12, 10, 18));
        panelActions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 70, 130), 2),
                BorderFactory.createEmptyBorder(20, 12, 20, 12)
        ));
        panelActions.setPreferredSize(new Dimension(210, 0));

        JLabel titreActions = new JLabel("ACTIONS");
        titreActions.setForeground(new Color(210, 190, 255));
        titreActions.setFont(new Font("Serif", Font.BOLD, 20));
        titreActions.setAlignmentX(Component.CENTER_ALIGNMENT);

        boutonFinDeTour = creerBoutonAction(
                "Passer le tour",
                new Color(35, 25, 55),
                new Color(160, 120, 255)
        );

        boutonAttaquer = creerBoutonAction(
                "Attaque directe",
                new Color(90, 25, 35),
                new Color(255, 90, 90)
        );

        panelActions.add(titreActions);
        panelActions.add(Box.createVerticalStrut(25));
        panelActions.add(boutonFinDeTour);
        panelActions.add(Box.createVerticalStrut(18));
        panelActions.add(boutonAttaquer);
        panelActions.add(Box.createVerticalGlue());

        // =========================
        // GAUCHE : MESSAGES
        // =========================
        labelMessage = new JLabel("Bienvenue dans la partie !");
        labelMessage.setForeground(Color.WHITE);
        labelMessage.setFont(new Font("Arial", Font.BOLD, 14));
        labelMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelMessage = new JPanel(new BorderLayout());
        panelMessage.setBackground(new Color(12, 10, 18));
        panelMessage.setPreferredSize(new Dimension(260, 100));
        panelMessage.setBorder(BorderFactory.createLineBorder(new Color(90, 70, 130), 2));
        panelMessage.add(labelMessage, BorderLayout.CENTER);

        // =========================
        // AJOUT FINAL
        // =========================
        this.add(panelAdversaire, BorderLayout.NORTH);
        this.add(panelPlateau, BorderLayout.CENTER);
        this.add(panelJoueur, BorderLayout.SOUTH);
        this.add(panelActions, BorderLayout.EAST);
        this.add(panelMessage, BorderLayout.WEST);
    }

    private JButton creerBoutonAction(String texte, Color couleurFond, Color couleurBordure) {
        JButton bouton = new JButton(texte);

        bouton.setPreferredSize(new Dimension(170, 48));
        bouton.setMaximumSize(new Dimension(170, 48));
        bouton.setMinimumSize(new Dimension(170, 48));

        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setFont(new Font("Serif", Font.BOLD, 16));
        bouton.setForeground(Color.WHITE);
        bouton.setBackground(couleurFond);
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setBorder(BorderFactory.createLineBorder(couleurBordure, 2));
        bouton.setOpaque(true);

        bouton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bouton.setBackground(couleurFond.brighter());
                bouton.setBorder(BorderFactory.createLineBorder(new Color(220, 210, 255), 3));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bouton.setBackground(couleurFond);
                bouton.setBorder(BorderFactory.createLineBorder(couleurBordure, 2));
            }
        });

        return bouton;
    }

    public void setControleur(ControleurJeu controleur) {
        this.controleur = controleur;
    }

    public static void main(String[] args) {
        java.util.ArrayList<Card> catalogue =
                modele.BibliothequeCartes.chargerCartesDepuisCSV("base_cartes.csv");

        if (catalogue.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Aucune carte trouvee dans base_cartes.csv",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        java.util.Random rand = new java.util.Random();

        Card[] decklist1 = new Card[20];
        Card[] decklist2 = new Card[20];

        for (int i = 0; i < 20; i++) {
            Card modeleJ1 = catalogue.get(rand.nextInt(catalogue.size()));

            decklist1[i] = new Card(
                    modeleJ1.getColor(),
                    modeleJ1.getName(),
                    false,
                    modeleJ1.getAtk(),
                    modeleJ1.getDef(),
                    new TreeSet<>(modeleJ1.getEffects()),
                    modeleJ1.getTotalCost(),
                    modeleJ1.getColorCost(),
                    modeleJ1.getImagePath()
            );

            Card modeleJ2 = catalogue.get(rand.nextInt(catalogue.size()));

            decklist2[i] = new Card(
                    modeleJ2.getColor(),
                    modeleJ2.getName(),
                    false,
                    modeleJ2.getAtk(),
                    modeleJ2.getDef(),
                    new TreeSet<>(modeleJ2.getEffects()),
                    modeleJ2.getTotalCost(),
                    modeleJ2.getColorCost(),
                    modeleJ2.getImagePath()
            );
        }

        Deck d1 = new Deck("Deck J1", decklist1);
        Deck d2 = new Deck("Deck J2", decklist2);

        d1.shuffle();
        d2.shuffle();

        Joueur j1 = new Joueur("JoueurA", d1);
        Joueur j2 = new Joueur("JoueurX", d2);

        Partie monModele = new Partie(j1, j2);
        monModele.demarrerTour();

        FenetreJeu maVue = new FenetreJeu();

        new ControleurJeu(monModele, maVue);
        maVue.actualiser(monModele.getJoueurCourant(), monModele.getJoueurAdverse());

        EcranDemarrage intro = new EcranDemarrage(null);
        intro.setVisible(true);

        if (intro.isCommencer()) {
            maVue.setLocationRelativeTo(null);
            maVue.setExtendedState(JFrame.MAXIMIZED_BOTH);
            maVue.setVisible(true);
            maVue.toFront();
            maVue.requestFocus();
        } else {
            maVue.dispose();
        }
    }

    public void actualiser(Joueur joueurCourant, Joueur joueurAdverse) {
        labelTour.setText("Tour de : " + joueurCourant.getNom());

        labelPvJoueur.setText(
                joueurCourant.getNom() + " : " + joueurCourant.getPointsDeVie()
                        + " PV | Mana : " + joueurCourant.getReserveMana() + "/" + joueurCourant.getManaMax()
                        + " | Cimetiere : " + joueurCourant.getCimetiere().size()
                        + " | Deck : " + joueurCourant.getDeck().size()
        );

        labelPvAdversaire.setText(
                joueurAdverse.getNom() + " : " + joueurAdverse.getPointsDeVie()
                        + " PV | Cimetiere : " + joueurAdverse.getCimetiere().size()
                        + " | Deck : " + joueurAdverse.getDeck().size()
        );

        // =========================
        // MAIN DU JOUEUR
        // =========================
        panelMainJoueur.removeAll();

        for (Card c : joueurCourant.getMain()) {
            CarteGraphique vueCarte = new CarteGraphique(c);

            ajouterContourBlanc(vueCarte);

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

                rendreCarteCliquable(
                        vueCarte,
                        listenerMain,
                        Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                );
            }

            panelMainJoueur.add(vueCarte);
        }

        // =========================
        // TERRAIN DU JOUEUR
        // =========================
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

                rendreCarteCliquable(
                        vueCarte,
                        listenerTerrainAmi,
                        Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)
                );
            }

            panelTerrainJoueur.add(vueCarte);
        }

        // =========================
        // TERRAIN ADVERSAIRE
        // =========================
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

                rendreCarteCliquable(
                        vueCarte,
                        listenerTerrainAdverse,
                        Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)
                );
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

    // ==========================================================
    // CONTOUR BLANC SUR LES CARTES DE LA MAIN
    // ==========================================================
    private void ajouterContourBlanc(JPanel carte) {
        javax.swing.border.Border bordureNormale = carte.getBorder();

        java.awt.event.MouseAdapter effetContour = new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                carte.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
                carte.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    Point positionSouris = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(positionSouris, carte);

                    if (!carte.contains(positionSouris)) {
                        carte.setBorder(bordureNormale);
                        carte.repaint();
                    }
                });
            }
        };

        ajouterContourAuxEnfants(carte, effetContour);
    }

    private void ajouterContourAuxEnfants(Component composant, java.awt.event.MouseListener listener) {
        composant.addMouseListener(listener);

        if (composant instanceof Container) {
            for (Component enfant : ((Container) composant).getComponents()) {
                ajouterContourAuxEnfants(enfant, listener);
            }
        }
    }

    // ==========================================================
    // RENDRE LES CARTES CLIQUABLES
    // ==========================================================
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

    // ==========================================================
    // PANEL AVEC IMAGE DE FOND POUR L'ARENE
    // ==========================================================
    private static class PanelImage extends JPanel {

        private static final long serialVersionUID = 1L;
        private Image imageFond;

        public PanelImage(String cheminImage) {
            java.net.URL imageURL = getClass().getResource(cheminImage);

            if (imageURL != null) {
                imageFond = new ImageIcon(imageURL).getImage();
            } else {
                System.out.println("Image d'arene introuvable : " + cheminImage);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            int largeur = getWidth();
            int hauteur = getHeight();

            if (imageFond != null) {
                g2.drawImage(imageFond, 0, 0, largeur, hauteur, this);
            } else {
                g2.setColor(new Color(20, 20, 30));
                g2.fillRect(0, 0, largeur, hauteur);
            }

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillRect(0, 0, largeur, hauteur);

            g2.dispose();
        }
    }

    // ==========================================================
    // ZONE TRANSPARENTE POUR TERRAIN JOUEUR / ADVERSAIRE
    // ==========================================================
    private static class PanelZone extends JPanel {

        private static final long serialVersionUID = 1L;

        private Color couleurFond;
        private Color couleurBordure;

        public PanelZone(Color couleurFond, Color couleurBordure) {
            this.couleurFond = couleurFond;
            this.couleurBordure = couleurBordure;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int marge = 8;
            int largeur = getWidth() - marge * 2;
            int hauteur = getHeight() - marge * 2;

            g2.setColor(couleurFond);
            g2.fillRoundRect(marge, marge, largeur, hauteur, 35, 35);

            g2.setColor(new Color(0, 120, 255, 55));
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(marge + 2, marge + 2, largeur - 4, hauteur - 4, 35, 35);

            g2.setColor(couleurBordure);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(marge, marge, largeur, hauteur, 35, 35);

            g2.setColor(new Color(150, 240, 255, 70));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(marge + 6, marge + 6, largeur - 12, hauteur - 12, 28, 28);

            g2.dispose();
        }
    }
}