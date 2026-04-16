package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
		this.add(barreDuHaut);
		
		var milieu = new JPanel(new GridLayout(2,0,0,0));
		var grille = new Component[2];
		zoneImage = new JPanel();
		grille[0] = zoneImage;
		zoneEffets = new JTextArea();
		for (var effet : carte.toString().split("|")[6].split(":")[1].split(",")) {
			zoneEffets.setText(zoneEffets.getText()+", "+effet);
		}
		zoneEffets.setText(zoneEffets.getText().replaceFirst(",", ""));
		grille[1] = zoneEffets;
		for (var comp : grille) {
			milieu.add(comp);
		}
		this.add(milieu);		
	}
}
