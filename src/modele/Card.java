package modele;

public abstract class Card {
	
	public enum Colors {RED,WHITE,GREEN,BLUE,DARK,NONE};
	public enum Type {CREATURE,LAND};
	
	private Colors color;
	private Type type; 
	private String name;
	private boolean tapped;	
	
	public Card( Colors color, String nom, Type type ) {
		this.type = type;
		this.color = color;
		name = nom;
		tapped = false;
	}
	
	public Colors getColor() {
		return color;
	}
	
	public boolean isTapped() {
		return tapped;
	}
	
	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}
	
	@Override
	public String toString() {
		return "Type : " + type + "\ncolor : " + color + "\nnom : " + name + "\ntapped : " + tapped;		
	}
}