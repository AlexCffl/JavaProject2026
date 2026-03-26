package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Colors {RED,WHITE,GREEN,BLUE,DARK,NONE};
	public enum Type {CREATURE,LAND};
	
	private Type type; 
	private Colors color;
	private String name;
	private boolean tapped;	
	
	public Card(Type type, Colors color, String name , boolean tapped) {
		this.type = type;
		this.color = color;
		this.name = name;
		this.tapped = tapped;
	}
	
	public Card(Type type, Colors color, String name) {
		this(type, color, name, false);
	}
	
	//Getters
	public Colors getColor() {return color;}
	public boolean isTapped() {return tapped;}
	
	//Setters
	public void setTapped(boolean tapped) {this.tapped = tapped;}	
	protected void setColor(Colors color) {this.color = color;}	
	protected void setName(String name) {this.name = name;}
	protected void setType(Type type) {this.type = type;}
	
	@Override
	public String toString() {
		return "Type : " + type + " | color : " + color + " | nom : " + name + " | tapped : " + tapped;		
	}
}