package vue;

import modele.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EcranBibliotheque extends JDialog {

    private static final long serialVersionUID = 1L;

    private ArrayList<Card> catalogueComplet;
    private JPanel panelGrilleCartes;
    private JComboBox<String> filtreCouleur;
    private JComboBox<String> filtreTri;

    public EcranBibliotheque(JFrame parent, ArrayList<Card> catalogue) {
        super(parent, "Bibliotheque de Cartes", true);

        this.catalogueComplet = catalogue;

        setSize(1000, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelFiltres = new JPanel();
        panelFiltres.setBackground(new Color(25, 25, 35));
        panelFiltres.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelCouleur = new JLabel("Couleur : ");
        labelCouleur.setForeground(Color.WHITE);
        panelFiltres.add(labelCouleur);

        filtreCouleur = new JComboBox<>(new String[]{
                "TOUTES", "RED", "BLUE", "GREEN", "WHITE", "DARK"
        });
        panelFiltres.add(filtreCouleur);

        JLabel labelTri = new JLabel("   Trier par : ");
        labelTri.setForeground(Color.WHITE);
        panelFiltres.add(labelTri);

        filtreTri = new JComboBox<>(new String[]{
                "AUCUN",
                "COULEUR",
                "ATTAQUE CROISSANTE",
                "ATTAQUE DECROISSANTE",
                "DEFENSE CROISSANTE",
                "DEFENSE DECROISSANTE"
        });
        panelFiltres.add(filtreTri);

        JButton boutonRechercher = new JButton("Appliquer");
        panelFiltres.add(boutonRechercher);

        this.add(panelFiltres, BorderLayout.NORTH);

        panelGrilleCartes = new JPanel(new GridLayout(0, 4, 20, 20));
        panelGrilleCartes.setBackground(new Color(20, 20, 30));
        panelGrilleCartes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panelGrilleCartes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);

        boutonRechercher.addActionListener(e -> appliquerFiltres());

        filtreCouleur.addActionListener(e -> appliquerFiltres());
        //filtreTri.addActionListener(e -> appliquerFiltres());

        appliquerFiltres();
    }

    private void appliquerFiltres() {
        panelGrilleCartes.removeAll();

        String couleurChoisie = (String) filtreCouleur.getSelectedItem();
        String triChoisi = (String) filtreTri.getSelectedItem();

        ArrayList<Card> cartesAffichees = new ArrayList<>();

        for (Card c : catalogueComplet) {
            boolean correspondCouleur =
                    couleurChoisie.equals("TOUTES")
                    || c.getColor().name().equals(couleurChoisie);

            if (correspondCouleur) {
                cartesAffichees.add(c);
            }
        }
        
        if (triChoisi.equals("COULEUR")) {
            cartesAffichees.sort((c1, c2) -> {
                int comp = c1.getColor().compareTo(c2.getColor());
                if (comp == 0) {
                    return c1.getName().compareTo(c2.getName());
                }
                return comp;
            });
        }

        if (triChoisi.equals("ATTAQUE CROISSANTE")) {
            cartesAffichees.sort(Comparator.comparingInt(Card::getAtk));
        }

        if (triChoisi.equals("ATTAQUE DECROISSANTE")) {
            cartesAffichees.sort(Comparator.comparingInt(Card::getAtk).reversed());
        }

        if (triChoisi.equals("DEFENSE CROISSANTE")) {
            cartesAffichees.sort(Comparator.comparingInt(Card::getDef));
        }

        if (triChoisi.equals("DEFENSE DECROISSANTE")) {
            cartesAffichees.sort(Comparator.comparingInt(Card::getDef).reversed());
        }

        for (Card c : cartesAffichees) {
            panelGrilleCartes.add(new CarteGraphique(c));
        }

        panelGrilleCartes.revalidate();
        panelGrilleCartes.repaint();
    }
}