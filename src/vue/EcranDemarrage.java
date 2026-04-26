package vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EcranDemarrage extends JDialog {

    private static final long serialVersionUID = 1L;
    private boolean commencer = false;

    public EcranDemarrage(JFrame parent) {
        super(parent, "Card Battle Arena", true);

        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel fond = new FondAccueil();
        fond.setLayout(new BorderLayout());

        JPanel contenu = new JPanel(new BorderLayout());
        contenu.setOpaque(false);
        contenu.setBorder(new EmptyBorder(60, 90, 60, 90));

        contenu.add(creerZoneTitre(), BorderLayout.CENTER);
        contenu.add(creerZoneBoutons(parent), BorderLayout.EAST);

        fond.add(contenu, BorderLayout.CENTER);
        add(fond, BorderLayout.CENTER);

        ajouterRaccourcis();
        mettreEnPleinEcran();
    }

    private JPanel creerZoneTitre() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel petitTitre = new JLabel("JEU DE CARTES STRATEGIQUE");
        petitTitre.setForeground(new Color(230, 190, 100));
        petitTitre.setFont(new Font("Serif", Font.BOLD, 24));
        petitTitre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titre = new JLabel("CARD BATTLE");
        titre.setForeground(new Color(245, 235, 210));
        titre.setFont(new Font("Serif", Font.BOLD, 78));
        titre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titreArena = new JLabel("ARENA");
        titreArena.setForeground(new Color(230, 230, 230));
        titreArena.setFont(new Font("Serif", Font.BOLD, 90));
        titreArena.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea description = new JTextArea(
                "Entrez dans une arene magique ou chaque carte peut changer le cours du combat.\n"
              + "Posez vos creatures, attaquez l'adversaire et utilisez vos effets speciaux\n"
              + "pour prendre l'avantage et remporter la partie."
        );

        description.setOpaque(false);
        description.setEditable(false);
        description.setFocusable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setForeground(new Color(240, 240, 240));
        description.setFont(new Font("SansSerif", Font.PLAIN, 21));
        description.setMaximumSize(new Dimension(850, 150));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel info = new JLabel("Prepare ton deck. Choisis ta strategie. Domine l'arene.");
        info.setForeground(new Color(230, 180, 90));
        info.setFont(new Font("Serif", Font.ITALIC, 24));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(petitTitre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(titre);
        panel.add(titreArena);
        panel.add(Box.createVerticalStrut(25));
        panel.add(description);
        panel.add(Box.createVerticalStrut(25));
        panel.add(info);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel creerZoneBoutons(JFrame parent) {
        JPanel panelBoutons = new JPanel();
        panelBoutons.setOpaque(false);
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
        panelBoutons.setBorder(new EmptyBorder(120, 40, 120, 40));

        JButton boutonCommencer = creerBouton("ENTRER DANS L'ARENE");
        JButton boutonSavoirPlus = creerBouton("EN SAVOIR PLUS");
        JButton boutonBiblio = creerBouton("COLLECTION DE CARTES");
        JButton boutonQuitter = creerBouton("QUITTER");

        boutonCommencer.addActionListener(e -> {
            commencer = true;
            dispose();
        });

        boutonSavoirPlus.addActionListener(e -> afficherEnSavoirPlus());

        boutonBiblio.addActionListener(e -> {
            java.util.ArrayList<modele.Card> catalogue =
                    modele.BibliothequeCartes.chargerCartesDepuisCSV("base_cartes.csv");

            EcranBibliotheque biblio = new EcranBibliotheque(parent, catalogue);
            biblio.setVisible(true);
        });

        boutonQuitter.addActionListener(e -> System.exit(0));

        panelBoutons.add(Box.createVerticalGlue());
        panelBoutons.add(boutonCommencer);
        panelBoutons.add(Box.createVerticalStrut(22));
        panelBoutons.add(boutonSavoirPlus);
        panelBoutons.add(Box.createVerticalStrut(22));
        panelBoutons.add(boutonBiblio);
        panelBoutons.add(Box.createVerticalStrut(22));
        panelBoutons.add(boutonQuitter);
        panelBoutons.add(Box.createVerticalGlue());

        return panelBoutons;
    }

    private JButton creerBouton(String texte) {
        JButton bouton = new JButton(texte);

        bouton.setPreferredSize(new Dimension(330, 60));
        bouton.setMaximumSize(new Dimension(330, 60));
        bouton.setMinimumSize(new Dimension(330, 60));

        bouton.setFont(new Font("Serif", Font.BOLD, 19));
        bouton.setForeground(new Color(245, 235, 210));
        bouton.setBackground(new Color(25, 32, 45));
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setBorder(BorderFactory.createLineBorder(new Color(220, 150, 60), 2));
        bouton.setOpaque(true);

        bouton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bouton.setBackground(new Color(170, 75, 35));
                bouton.setForeground(Color.WHITE);
                bouton.setBorder(BorderFactory.createLineBorder(new Color(255, 210, 100), 3));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bouton.setBackground(new Color(25, 32, 45));
                bouton.setForeground(new Color(245, 235, 210));
                bouton.setBorder(BorderFactory.createLineBorder(new Color(220, 150, 60), 2));
            }
        });

        return bouton;
    }

    private void afficherEnSavoirPlus() {
        String message =
                "CARD BATTLE ARENA\n\n"
              + "Ce jeu est un jeu de cartes strategique au tour par tour.\n\n"
              + "Chaque joueur possede un deck, une main et un terrain.\n"
              + "Le but est de reduire les points de vie de l'adversaire a 0.\n\n"
              + "Regles principales :\n"
              + "- poser des cartes sur le terrain ;\n"
              + "- attaquer les cartes adverses ;\n"
              + "- attaquer directement si le terrain adverse est vide ;\n"
              + "- utiliser les effets speciaux des cartes ;\n"
              + "- gagner lorsque les PV adverses tombent a 0.\n\n"
              + "Le jeu reprend une ambiance fantasy avec une arene magique et des creatures.";

        JOptionPane.showMessageDialog(
                this,
                message,
                "En savoir plus",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void ajouterRaccourcis() {
        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private void mettreEnPleinEcran() {
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tailleEcran);
        setLocation(0, 0);
    }

    public boolean isCommencer() {
        return commencer;
    }

    private static class FondAccueil extends JPanel {

        private static final long serialVersionUID = 1L;
        private Image imageFond;

        public FondAccueil() {
            java.net.URL imageURL = getClass().getResource("/images/fond_accueil.jpg");

            if (imageURL != null) {
                imageFond = new ImageIcon(imageURL).getImage();
            } else {
                System.out.println("Image de fond introuvable : /images/fond_accueil.jpg");
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

            g2.setColor(new Color(0, 0, 0, 70));
            g2.fillRect(0, 0, largeur, hauteur);

            g2.setColor(new Color(0, 0, 0, 135));
            g2.fillRoundRect(50, 70, largeur / 2 + 150, hauteur - 140, 35, 35);

            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRoundRect(largeur - 480, 130, 400, hauteur - 260, 35, 35);

            g2.dispose();
        }
    }
}