package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modele.Card;

public class CarteGraphique extends JPanel {

	private static final long serialVersionUID = 1L;
	public CarteGraphique(Card carte) {
        super();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(135, 210));
        this.setMinimumSize(new Dimension(135, 210));
        this.setMaximumSize(new Dimension(135, 210));

        Color couleurPrincipale = getCouleurPrincipale(carte.getColor());
        Color couleurSecondaire = getCouleurSecondaire(carte.getColor());
        Color couleurTexteTitre = getCouleurTexteTitre(carte.getColor());

        this.setBackground(couleurPrincipale);

        this.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 4, true),
                new EmptyBorder(5, 5, 5, 5)
        ));

        this.add(creerBarreHaut(carte, couleurSecondaire, couleurTexteTitre), BorderLayout.NORTH);
        this.add(creerMilieuCarte(carte, couleurSecondaire), BorderLayout.CENTER);
        this.add(creerBarreBas(carte), BorderLayout.SOUTH);
    }

    private JPanel creerBarreHaut(Card carte, Color couleurSecondaire, Color couleurTexteTitre) {
        JPanel barreHaut = new JPanel(new BorderLayout());
        barreHaut.setBackground(couleurSecondaire);
        barreHaut.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 2),
                new EmptyBorder(3, 5, 3, 5)
        ));

        JLabel labelNom = new JLabel(raccourcirNom(carte.getName()));
        labelNom.setForeground(couleurTexteTitre);
        labelNom.setFont(new Font("Serif", Font.BOLD, 13));
        labelNom.setToolTipText(carte.getName());

        JLabel labelMana = new JLabel(" " + carte.getTotalCost() + " ");
        labelMana.setOpaque(true);
        labelMana.setBackground(new Color(230, 230, 230));
        labelMana.setForeground(Color.BLACK);
        labelMana.setFont(new Font("Arial", Font.BOLD, 14));
        labelMana.setBorder(new LineBorder(Color.BLACK, 1, true));

        barreHaut.add(labelNom, BorderLayout.CENTER);
        barreHaut.add(labelMana, BorderLayout.EAST);

        return barreHaut;
    }

    private JPanel creerMilieuCarte(Card carte, Color couleurSecondaire) {
        JPanel milieu = new JPanel(new BorderLayout());
        milieu.setOpaque(false);

        JPanel zoneImage = creerZoneImage(carte);
        milieu.add(zoneImage, BorderLayout.NORTH);

        JLabel labelType = new JLabel(getTypeCarte(carte));
        labelType.setOpaque(true);
        labelType.setBackground(couleurSecondaire);
        labelType.setForeground(getCouleurTexteTitre(carte.getColor()));
        labelType.setFont(new Font("Serif", Font.BOLD, 12));
        labelType.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 2),
                new EmptyBorder(3, 5, 3, 5)
        ));

        milieu.add(labelType, BorderLayout.CENTER);

        JTextArea zoneDescription = new JTextArea(getDescriptionCarte(carte));
        zoneDescription.setEditable(false);
        zoneDescription.setFocusable(false);
        zoneDescription.setLineWrap(true);
        zoneDescription.setWrapStyleWord(true);
        zoneDescription.setFont(new Font("SansSerif", Font.PLAIN, 10));
        zoneDescription.setForeground(Color.BLACK);
        zoneDescription.setBackground(new Color(230, 230, 230));
        zoneDescription.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 2),
                new EmptyBorder(5, 6, 5, 6)
        ));
        zoneDescription.setPreferredSize(new Dimension(118 , 50));

        milieu.add(zoneDescription, BorderLayout.SOUTH);

        return milieu;
    }

    private JPanel creerZoneImage(Card carte) {
        JPanel zoneImage = new JPanel(new BorderLayout());
        zoneImage.setBackground(Color.BLACK);
        zoneImage.setBorder(new LineBorder(Color.BLACK, 2));
        zoneImage.setPreferredSize(new Dimension(118, 68));

        try {
            java.net.URL imageURL = getClass().getClassLoader().getResource(carte.getImagePath());

            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                Image image = icon.getImage().getScaledInstance(118, 68, Image.SCALE_SMOOTH);

                JLabel labelImage = new JLabel(new ImageIcon(image));
                labelImage.setHorizontalAlignment(JLabel.CENTER);

                zoneImage.add(labelImage, BorderLayout.CENTER);
            } else {
                JLabel erreur = new JLabel("Image introuvable");
                erreur.setForeground(Color.WHITE);
                erreur.setHorizontalAlignment(JLabel.CENTER);
                zoneImage.add(erreur, BorderLayout.CENTER);
            }

        } catch (Exception e) {
            JLabel erreur = new JLabel("Erreur image");
            erreur.setForeground(Color.WHITE);
            erreur.setHorizontalAlignment(JLabel.CENTER);
            zoneImage.add(erreur, BorderLayout.CENTER);
        }

        return zoneImage;
    }

    private JPanel creerBarreBas(Card carte) {
        JPanel barreBas = new JPanel(new BorderLayout());
        barreBas.setOpaque(false);
        barreBas.setBorder(new EmptyBorder(4, 0, 0, 0));

        JLabel labelCouleur = new JLabel(" " + getTexteEffets(carte) + " ");
        labelCouleur.setForeground(Color.WHITE);
        labelCouleur.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel labelStats = new JLabel(carte.getAtk() + "/" + carte.getDef());
        labelStats.setHorizontalAlignment(JLabel.CENTER);
        labelStats.setOpaque(true);
        labelStats.setBackground(new Color(230, 230, 230));
        labelStats.setForeground(Color.BLACK);
        labelStats.setFont(new Font("Arial", Font.BOLD, 18));
        labelStats.setPreferredSize(new Dimension(55, 28));
        labelStats.setBorder(new LineBorder(Color.BLACK, 3, true));

        barreBas.add(labelCouleur, BorderLayout.WEST);
        barreBas.add(labelStats, BorderLayout.EAST);

        return barreBas;
    }
    private String getTexteEffets(Card carte) {
        if (carte.getEffects().isEmpty()) {
            return "NORMAL";
        }

        StringBuilder texte = new StringBuilder();

        for (Card.Effects effet : carte.getEffects()) {
            if (texte.length() > 0) {
                texte.append(" ");
            }

            texte.append(effet.name());
        }

        return texte.toString();
    }

    private String getDescriptionCarte(Card carte) {
        StringBuilder description = new StringBuilder();

        if (carte.getEffects().isEmpty()) {
            description.append("Creature sans effet special.\n");
            description.append("Elle attaque avec sa force naturelle.");
            return description.toString();
        }

        for (Card.Effects effet : carte.getEffects()) {
            if (effet == Card.Effects.FLYING) {
                description.append("FLYING : peut attaquer les creatures volantes.\n");
            }

            if (effet == Card.Effects.HASTE) {
                description.append("HASTE : peut attaquer des son arrivee.\n");
            }

            if (effet == Card.Effects.TRAMPLE) {
                description.append("TRAMPLE : les degats en trop touchent le joueur.\n");
            }
        }

        return description.toString();
    }

    private String getTypeCarte(Card carte) {
        if (carte.getEffects().contains(Card.Effects.FLYING)) {
            return "Creature - Volante";
        }

        if (carte.getColor() == Card.Colors.RED) {
            return "Creature - Guerrier";
        }

        if (carte.getColor() == Card.Colors.GREEN) {
            return "Creature - Sauvage";
        }

        if (carte.getColor() == Card.Colors.BLUE) {
            return "Creature - Mage";
        }

        if (carte.getColor() == Card.Colors.WHITE) {
            return "Creature - Chevalier";
        }

        if (carte.getColor() == Card.Colors.DARK) {
            return "Creature - Ombre";
        }

        return "Creature";
    }

    private Color getCouleurPrincipale(Card.Colors couleur) {
        if (couleur == Card.Colors.RED) {
            return new Color(120, 35, 25);
        }

        if (couleur == Card.Colors.BLUE) {
            return new Color(20, 70, 120);
        }

        if (couleur == Card.Colors.GREEN) {
            return new Color(35, 95, 45);
        }

        if (couleur == Card.Colors.WHITE) {
            return new Color(190, 175, 120);
        }

        if (couleur == Card.Colors.DARK) {
            return new Color(35, 35, 45);
        }

        return new Color(80, 80, 80);
    }

    private Color getCouleurSecondaire(Card.Colors couleur) {
        if (couleur == Card.Colors.RED) {
            return new Color(190, 80, 50);
        }

        if (couleur == Card.Colors.BLUE) {
            return new Color(55, 130, 190);
        }

        if (couleur == Card.Colors.GREEN) {
            return new Color(85, 145, 75);
        }

        if (couleur == Card.Colors.WHITE) {
            return new Color(230, 215, 160);
        }

        if (couleur == Card.Colors.DARK) {
            return new Color(80, 80, 95);
        }

        return new Color(130, 130, 130);
    }

    private Color getCouleurTexteTitre(Card.Colors couleur) {
        if (couleur == Card.Colors.WHITE) {
            return Color.BLACK;
        }

        return Color.WHITE;
    }

    private String raccourcirNom(String nom) {
        if (nom.length() > 22) {
            return nom.substring(0, 20) + "...";
        }

        return nom;
    }
}