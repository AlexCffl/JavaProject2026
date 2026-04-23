package vue;

import modele.Card;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EcranBibliotheque extends JDialog {

    private static final long serialVersionUID = 1L;
    private ArrayList<Card> catalogueComplet;
    private JPanel panelGrilleCartes;
    private JComboBox<String> filtreCouleur;

    public EcranBibliotheque(JFrame parent, ArrayList<Card> catalogue) {
        super(parent, "Bibliotheque de Cartes", true);
        this.catalogueComplet = catalogue;
        
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelFiltres = new JPanel();
        panelFiltres.setBackground(new Color(45, 45, 45));
        
        JLabel labelCouleur = new JLabel("Couleur : ");
        labelCouleur.setForeground(Color.WHITE);
        panelFiltres.add(labelCouleur);
        
        filtreCouleur = new JComboBox<>(new String[]{"TOUTES", "RED", "BLUE", "GREEN", "WHITE", "DARK"});
        panelFiltres.add(filtreCouleur);

        JButton boutonRechercher = new JButton("Filtrer");
        panelFiltres.add(boutonRechercher);
        
        this.add(panelFiltres, BorderLayout.NORTH);

        panelGrilleCartes = new JPanel(new GridLayout(0, 4, 15, 15));
        panelGrilleCartes.setBackground(new Color(30, 30, 40));
        panelGrilleCartes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(panelGrilleCartes);
        this.add(scrollPane, BorderLayout.CENTER);

        boutonRechercher.addActionListener(e -> appliquerFiltres());
        appliquerFiltres();
    }
    private void appliquerFiltres() {
        panelGrilleCartes.removeAll(); 
        String couleurChoisie = (String) filtreCouleur.getSelectedItem();
        for (Card c : catalogueComplet) {
            boolean correspondCouleur = couleurChoisie.equals("TOUTES") || c.getColor().name().equals(couleurChoisie); 
            if (correspondCouleur) {
                panelGrilleCartes.add(new CarteGraphique(c));
            }
        }
        panelGrilleCartes.revalidate();
        panelGrilleCartes.repaint();
    }
}