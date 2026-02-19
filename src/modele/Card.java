package modele;

public class Card {
	public enum Type {RED,WHITE,GREEN,BLUE,DARK,NONE};
	private Type color;
	private int totalCost;
	private int colorCost;
	private int atk;
	private int def;
	private boolean land;
	private String name;
	private String description;
	private String[] skills;
	private boolean tapped;
	
	
	/**
	 * This constructor is used to create any card
	*/
	public Card( Type color, int totalcost, int colorcost, int attack, int defense, boolean land, String nom, String[] bonus ) {
		this.color = color;
		this.land = land;
		totalCost = totalcost;
		colorCost = colorcost;
		atk = attack;
		def = defense;
		name = nom;
		tapped = false;
		if (land) {			
			description = "Terrain";			
		} else {
			description = "Créature";
		}
	}
}