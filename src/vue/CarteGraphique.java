package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import modele.Card;

public class CarteGraphique extends JPanel {

	private static final long serialVersionUID = 1L;
	private BorderLayout layout;
	private JPanel zoneAtk;
	private JPanel zoneDef;
	private JPanel zoneTitre;
	private JPanel zoneMana;
	private JPanel zoneImage;
	private JTextArea zoneEffets;
	
	public CarteGraphique(modele.Card carte ) {
		super();
		layout = new BorderLayout();
		this.setLayout(layout);
		
		this.setPreferredSize(new Dimension(120, 200));
		this.setBorder(new LineBorder(Color.BLACK, 2));
		
		var barreDuBas = new JPanel(new BorderLayout());		
		zoneAtk = new JPanel();
		zoneAtk.add(new JLabel(""+carte.getAtk()));
		barreDuBas.add(zoneAtk,BorderLayout.WEST);
		zoneDef = new JPanel();
		zoneDef.add(new JLabel(""+carte.getDef()));
		barreDuBas.add(zoneDef,BorderLayout.EAST);
		this.add(barreDuBas,BorderLayout.SOUTH);
		
		var barreDuHaut = new JPanel(new BorderLayout());
		zoneTitre = new JPanel();
		zoneTitre.add(new JLabel(carte.getName()));
		barreDuHaut.add(zoneTitre,BorderLayout.WEST);
		zoneMana = new JPanel();
		zoneMana.add(new JLabel(carte.getTotalCost()+" dont "+carte.getColorCost()+" "+carte.getColor()));
		barreDuHaut.add(zoneMana,BorderLayout.EAST);
		this.add(barreDuHaut, BorderLayout.NORTH);
		
		var milieu = new JPanel(new GridLayout(2,0,0,0));
		var grille = new Component[2];
		zoneImage = new JPanel();
		grille[0] = zoneImage;
		zoneEffets = new JTextArea();
		
		StringBuilder texteEffets = new StringBuilder();
		for (modele.Card.Effects effet : carte.getEffects()) {
			texteEffets.append(effet.toString()).append(" ");
		}
		
		zoneEffets.setText(texteEffets.toString());
		zoneEffets.setEditable(false);
		zoneEffets.setLineWrap(true);
		zoneEffets.setWrapStyleWord(true);
		grille[1] = zoneEffets;
		for (var comp : grille) {
			milieu.add(comp);
		}
		this.add(milieu, BorderLayout.CENTER);		
	}
}
