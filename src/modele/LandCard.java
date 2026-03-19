package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import modele.Card.Colors;
import modele.Card.Type;

public class LandCard extends Card {

	public LandCard(Colors color, String nom) {
		super(Type.LAND, color, nom);		
	}
	
	public LandCard (File file) {
		super(Type.LAND,Colors.NONE,"");
		
		try (var fis = new FileInputStream(file); var ois = new ObjectInputStream(fis)){
			
			var serializedCard = ois.readLine().split("\\|");
			
			if (serializedCard[0].split(":")[1] == " LAND") {
				this.setType(Type.LAND);
						
				var laCouleurStr = serializedCard[1].split(":")[1];
				
				 switch (laCouleurStr) {
					 case " RED" :
					 	this.setColor(Colors.RED);
					 	break;
					 	
					 case " BLUE" :
					 	this.setColor(Colors.BLUE);
					 	break;
						 	
					 case " GREEN" :
					 	this.setColor(Colors.GREEN);
					 	break;
					 	
					 case " WHITE" :
						this.setColor(Colors.WHITE);
					 	break;
						 	
					 case " DARK" :
						this.setColor(Colors.DARK);
					 	break;
				 }
			
				this.setName(serializedCard[2].split(":")[1]);
				this.setTapped(serializedCard[3].split(":")[1].equals(" true"));
			} else {
				throw new InvalidTypeException();
			}
			
	    } catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			System.out.println("Mauvais type de carte");
			e.printStackTrace();
		}		
	}
	
	@Override
	public String toString() {
		return super.toString()+'\n';
	}
}
