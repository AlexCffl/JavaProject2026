package vue;

import javax.swing.*;
import java.awt.*;

public class EcranDemarrage extends JDialog {

    private static final long serialVersionUID = 1L;
    private boolean commencer = false;

    public EcranDemarrage(JFrame parent) {
        super(parent, "Début de partie", true);

        setSize(600, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel fond = new JPanel(new BorderLayout());
        fond.setBackground(new Color(25, 25, 45));

        JLabel titre = new JLabel("CARD BATTLE", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 34));
        titre.setForeground(new Color(255, 215, 0));
        titre.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel sousTitre = new JLabel("Prépare-toi pour le combat !", SwingConstants.CENTER);
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 20));
        sousTitre.setForeground(Color.WHITE);
        sousTitre.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JTextArea texte = new JTextArea();
        texte.setText(
                "Bienvenue dans la partie.\n\n" +
                "Règles rapides :\n" +
                "- Pose tes cartes\n" +
                "- Attaque les cartes adverses\n" +
                "- Attaque directement si le terrain adverse est vide\n" +
                "- Réduis les PV adverses à 0 pour gagner"
        );
        texte.setEditable(false);
        texte.setFocusable(false);
        texte.setOpaque(false);
        texte.setForeground(new Color(230, 230, 230));
        texte.setFont(new Font("Arial", Font.PLAIN, 16));
        texte.setMargin(new Insets(10, 30, 10, 30));

        JButton boutonCommencer = new JButton("Commencer la partie");
        boutonCommencer.setFont(new Font("Arial", Font.BOLD, 18));
        boutonCommencer.setBackground(new Color(200, 50, 50));
        boutonCommencer.setForeground(Color.WHITE);
        boutonCommencer.setFocusPainted(false);

        boutonCommencer.addActionListener(e -> {
            commencer = true;
            dispose();
        });

        JPanel centre = new JPanel(new BorderLayout());
        centre.setOpaque(false);
        centre.add(sousTitre, BorderLayout.NORTH);
        centre.add(texte, BorderLayout.CENTER);

        JPanel bas = new JPanel();
        bas.setOpaque(false);
        bas.setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 10));
        bas.add(boutonCommencer);

        fond.add(titre, BorderLayout.NORTH);
        fond.add(centre, BorderLayout.CENTER);
        fond.add(bas, BorderLayout.SOUTH);

        add(fond, BorderLayout.CENTER);
    }

    public boolean isCommencer() {
        return commencer;
    }
}