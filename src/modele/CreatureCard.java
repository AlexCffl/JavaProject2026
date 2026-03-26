package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CreatureCard extends Card{
	
	private static final long serialVersionUID = 1L;

	public enum Effects {TRAMPLE,HASTE,FLYING};
	
	private int atk;
	private int def;
	private Effects[] effects;
	private int tC;
	private int cC;

	public CreatureCard(Colors color, String nom, int attack, int defense, Effects[] bonus, int totalCost, int colorCost) {
		super(Type.CREATURE, color, nom);
		atk = attack;
		def = defense;
		effects = bonus;
		tC = totalCost;
		cC = colorCost;
	}

	public CreatureCard (String data) throws InvalidTypeException {
		super(Type.CREATURE, Colors.NONE, "");
		
		var serializedCard = data.replaceAll(" ", "").split("|");
		
		if (serializedCard[0].split(":")[1] == "CREATURE") {
			
			var laCouleurStr = serializedCard[1].split(":")[1];
				
			 switch (laCouleurStr) {
				 case "RED" :
				 	this.setColor(Colors.RED);
				 	break;
				 	
				 case "BLUE" :
				 	this.setColor(Colors.BLUE);
				 	break;
					 	
				 case "GREEN" :
				 	this.setColor(Colors.GREEN);
				 	break;
				 	
				 case "WHITE" :
					this.setColor(Colors.WHITE);
				 	break;
					 	
				 case "DARK" :
					this.setColor(Colors.DARK);
				 	break;
			 }
		
			this.setName(serializedCard[2].split(":")[1]);
			this.setTapped(serializedCard[3].split(":")[1].equals("true"));
			this.atk = Integer.parseInt(serializedCard[4].split(":")[1]);
			this.def = Integer.parseInt(serializedCard[5].split(":")[1]);
			var cost = serializedCard[6].split(":")[1].split("+");
			this.cC = Integer.parseInt(cost[1]);
			this.tC = Integer.parseInt(cost[0]) + Integer.parseInt(cost[1]);
			var cardEffects = serializedCard[7].split(":")[1].split(",");				
			var effects = new Effects[cardEffects.length-1];
			for (int i = 0; i < cardEffects.length; i++) {
				switch (cardEffects[i]) {
					case "FLYING" :
						effects[i] = Effects.FLYING;
						break;
					
					case "HASTE" :
						effects[i] = Effects.HASTE;
						break;
						
					case "TRAMPLE" :
						effects[i] = Effects.TRAMPLE;
						break;
				}
			}
			this.effects = effects;
		} else {
			throw new InvalidTypeException();
		}
	}
	
/*	public CreatureCard (File file) {
		super(Type.CREATURE,Colors.NONE,"");
		
		try (var fis = new FileInputStream(file); var ois = new ObjectInputStream(fis)){
			
			var serializedCard = ois.readLine().replaceAll(" ", "").split("\\|");
						
	    } catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			System.out.println("Mauvais type de carte");
			e.printStackTrace();
		}		
	}*/
	
	public void serialize(File file) {		
		try (var fos = new FileOutputStream(file); var oos = new ObjectOutputStream(fos)){
			oos.writeChars(toString());
	    } catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
		
	@Override
	public String toString() {
		var retour = super.toString();
		retour += " | attack : " + atk + " | defense : " + def + " | cost : " + (tC-cC) + "+" + cC + " | effects : ";
		for (var effect : effects) {
			retour += effect + ",";
		}
		return retour + '\n';
	}

	//Getters
	public int getTotalCost() {return tC;}
	public int getAtk() {return atk;}
	public int getDef() {return def;}
}