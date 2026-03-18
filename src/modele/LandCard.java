package modele;

public class LandCard extends Card {

	public LandCard(Colors color, String nom) {
		super(Type.LAND, color, nom);		
	}

	@Override
	public String toString() {
		return super.toString()+'\n';
	}
}
