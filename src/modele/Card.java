package modele;

import java.util.TreeSet;
import java.io.Serializable;

public class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum Effects {TRAMPLE,HASTE,FLYING};
	
	public enum Colors {RED,WHITE,GREEN,BLUE,DARK,NONE};

	private Colors color;
	private String name;
	private boolean tapped;
	private int atk;
	private int def;
	private TreeSet<Effects> effects;
	private int tC;
	private int cC;
	private int pvPerdus;

	public Card(Colors color, String name ,boolean tapped, int attack, int defense, TreeSet<Effects> bonus, int totalCost, int colorCost) {
		
		this.color = color;
		this.name = name;
		this.tapped = tapped;
		
		atk = attack;
		def = defense;
		effects = bonus;
		tC = totalCost;
		cC = colorCost;
		pvPerdus = 0;
	}

	@Override
	public String toString() {
		String retour = "Color : " + color + " | nom : " + name + " | tapped : " + tapped + " | attack : " + atk + " | defense : " + def + " | cost : " + (tC-cC) + "+" + cC + " | effects : ";
		for (var effect : effects) {
			retour += effect + ",";
		}
		return retour + '\n';
	}
	
	public boolean subirDgts (int dmg) {
		pvPerdus += dmg;
		return pvPerdus < def;
	}
	
	//Getters
	public int getTotalCost() {return tC;}
	public int getColorCost() {return cC;}
	public int getAtk() {return atk;}
	public int getDef() {return def;}
	public Colors getColor() {return color;}
	public boolean isTapped() {return tapped;}
	public String getName() {return name;}
	public TreeSet<Effects> getEffects() {return effects;}
		
	//Setters
	public void setPvPerdus(int valeur) {pvPerdus = valeur;}
	public void setTapped(boolean tapped) {this.tapped = tapped;}	
	protected void setColor(Colors color) {this.color = color;}	
	protected void setName(String name) {this.name = name;}

}