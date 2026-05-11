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
        bouton.setBackground(new Color(10, 25, 45));
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setBorder(BorderFactory.createLineBorder(new Color(220, 150, 60), 2));
        bouton.setOpaque(true);

        bouton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bouton.setBackground(new Color(0, 90, 150));
                bouton.setForeground(Color.WHITE);
                bouton.setBorder(BorderFactory.createLineBorder(new Color(255, 210, 100), 3));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bouton.setBackground(new Color(10, 25, 45));
                bouton.setForeground(new Color(245, 235, 210));
                bouton.setBorder(BorderFactory.createLineBorder(new Color(0, 190, 255), 2));
            }
        });

        return bouton;
    }

    private void afficherEnSavoirPlus() {
        JDialog dialogue = new JDialog(this, "En savoir plus", true);
        dialogue.setSize(620, 560);
        dialogue.setLocationRelativeTo(this);
        dialogue.setResizable(false);

        JPanel fond = new JPanel(new BorderLayout());
        fond.setBackground(new Color(8, 10, 20));
        fond.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 180, 255), 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titre = new JLabel("CARD BATTLE ARENA", JLabel.CENTER);
        titre.setForeground(new Color(220, 210, 255));
        titre.setFont(new Font("Serif", Font.BOLD, 34));

        JLabel sousTitre = new JLabel("Jeu de cartes strategique fantasy", JLabel.CENTER);
        sousTitre.setForeground(new Color(120, 230, 255));
        sousTitre.setFont(new Font("Serif", Font.ITALIC, 18));

        JPanel haut = new JPanel();
        haut.setOpaque(false);
        haut.setLayout(new BoxLayout(haut, BoxLayout.Y_AXIS));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        sousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        haut.add(titre);
        haut.add(Box.createVerticalStrut(8));
        haut.add(sousTitre);

        JTextPane texte = new JTextPane();
        texte.setContentType("text/html");
        texte.setEditable(false);
        texte.setFocusable(false);
        texte.setBackground(new Color(12, 15, 30));
        texte.setForeground(Color.WHITE);
        texte.setBorder(BorderFactory.createEmptyBorder(15, 18, 15, 18));

        texte.setText(
                "<html>"
              + "<body style='font-family:Arial; font-size:13px; color:white;'>"

              + "<h2 style='color:#78e6ff;'>Objectif du jeu</h2>"
              + "<p>Chaque joueur commence avec <b>20 points de vie</b>. "
              + "Le but est simple : poser des creatures, attaquer l'adversaire "
              + "et reduire ses points de vie a <b>0</b>.</p>"

              + "<h2 style='color:#78e6ff;'>Deroulement d'un tour</h2>"
              + "<p>A chaque tour, le joueur gagne du mana, pioche une carte, "
              + "peut jouer des creatures, puis attaquer avec les cartes disponibles.</p>"

              + "<h2 style='color:#78e6ff;'>Les effets speciaux</h2>"
              + "<p><b style='color:#ffaaaa;'>HASTE</b> : la creature peut attaquer directement apres son arrivee.</p>"
              + "<p><b style='color:#aaffaa;'>TRAMPLE</b> : les degats en trop touchent le joueur adverse.</p>"
              + "<p><b style='color:#aaaaff;'>FLYING</b> : la creature ne peut etre attaquee que par une creature volante.</p>"

              + "<h2 style='color:#78e6ff;'>Conseil de strategie</h2>"
              + "<p>Utilise les creatures rapides pour mettre la pression, "
              + "les creatures solides pour proteger ton terrain, et les creatures volantes "
              + "pour garder l'avantage.</p>"

              + "<p style='color:#d2beff; text-align:center; font-size:15px;'>"
              + "<b>Prepare ton deck. Choisis ta strategie. Domine l'arene.</b>"
              + "</p>"

              + "</body>"
              + "</html>"
        );

        JScrollPane scroll = new JScrollPane(texte);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(90, 70, 130), 2));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton boutonFermer = new JButton("FERMER");
        boutonFermer.setPreferredSize(new Dimension(180, 45));
        boutonFermer.setMaximumSize(new Dimension(180, 45));
        boutonFermer.setFont(new Font("Serif", Font.BOLD, 16));
        boutonFermer.setForeground(Color.WHITE);
        boutonFermer.setBackground(new Color(35, 25, 55));
        boutonFermer.setFocusPainted(false);
        boutonFermer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonFermer.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 255), 2));
        boutonFermer.setOpaque(true);

        boutonFermer.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boutonFermer.setBackground(new Color(0, 90, 150));
                boutonFermer.setBorder(BorderFactory.createLineBorder(new Color(120, 230, 255), 3));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boutonFermer.setBackground(new Color(35, 25, 55));
                boutonFermer.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 255), 2));
            }
        });

        boutonFermer.addActionListener(e -> dialogue.dispose());

        JPanel bas = new JPanel();
        bas.setOpaque(false);
        bas.add(boutonFermer);

        fond.add(haut, BorderLayout.NORTH);
        fond.add(Box.createVerticalStrut(15), BorderLayout.WEST);
        fond.add(scroll, BorderLayout.CENTER);
        fond.add(bas, BorderLayout.SOUTH);

        dialogue.setContentPane(fond);
        dialogue.setVisible(true);
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

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillRect(0, 0, largeur, hauteur);

            g2.setColor(new Color(0, 0, 0, 145));
            g2.fillRoundRect(50, 70, largeur / 2 + 150, hauteur - 140, 35, 35);

            g2.setColor(new Color(0, 0, 0, 165));
            g2.fillRoundRect(largeur - 480, 130, 400, hauteur - 260, 35, 35);

            g2.setColor(new Color(0, 180, 255, 120));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(50, 70, largeur / 2 + 150, hauteur - 140, 35, 35);
            g2.drawRoundRect(largeur - 480, 130, 400, hauteur - 260, 35, 35);

            g2.dispose();
        }
    }
}