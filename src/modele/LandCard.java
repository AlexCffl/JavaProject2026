package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LandCard extends Card {

	private static final long serialVersionUID = 1L;

	public LandCard(Colors color, String nom) {
		super(Type.LAND, color, nom);		
	}
	
	/*public LandCard(String data) throws InvalidTypeException {
		super(Type.LAND,Colors.NONE,"");
		
		var serializedCard = data.replaceAll(" ", "").split("\\|");
		
		if (serializedCard[0].split(":")[1] == "LAND") {
					
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
		} else {
			throw new InvalidTypeException();
		}   	
}
	
	
	 public LandCard (File file) {
		super(Type.LAND,Colors.NONE,"");
		
		try (var fis = new FileInputStream(file); var ois = new ObjectInputStream(fis)){
			
			var data = ois.readLine();
			LandCard(data);
					
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
	
	
	public void serialize(File file) {		
		try (var fos = new FileOutputStream(file); var oos = new ObjectOutputStream(fos)){
			oos.writeChars(toString());
	    } catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}*/
		
	@Override
	public String toString() {
		return super.toString()+'\n';
	}
}
