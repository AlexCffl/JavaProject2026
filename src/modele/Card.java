package modele;

public class Card{
	
	private static final long serialVersionUID = 1L;

	public enum Effects {TRAMPLE,HASTE,FLYING};
	
	public enum Colors {RED,WHITE,GREEN,BLUE,DARK,NONE};

	private Colors color;
	private String name;
	private boolean tapped;
	private int atk;
	private int def;
	private Effects[] effects;
	private int tC;
	private int cC;

	public Card(Colors color, String name ,boolean tapped, int attack, int defense, Effects[] bonus, int totalCost, int colorCost) {
		atk = attack;
		def = defense;
		effects = bonus;
		tC = totalCost;
		cC = colorCost;
	}

	@Override
	public String toString() {
		String retour = "Color : " + color + " | nom : " + name + " | tapped : " + tapped + " | attack : " + atk + " | defense : " + def + " | cost : " + (tC-cC) + "+" + cC + " | effects : ";
		for (var effect : effects) {
			retour += effect + ",";
		}
		return retour + '\n';
	}

	@Override
	public boolean equals(Object obj) {
		return obj.toString() == toString();
	}
	
	//Getters
	public int getTotalCost() {return tC;}
	public int getColorCost() {return cC;}
	public int getAtk() {return atk;}
	public int getDef() {return def;}
	public Colors getColor() {return color;}
	public boolean isTapped() {return tapped;}
	public String getName() {return name;}
		
	//Setters
	public void setTapped(boolean tapped) {this.tapped = tapped;}	
	protected void setColor(Colors color) {this.color = color;}	
	protected void setName(String name) {this.name = name;}

}