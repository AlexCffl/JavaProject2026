package modele;

public class LandCard extends Card {

	public LandCard(Colors color, String nom) {
		super(color, nom, Type.LAND);		
	}

	@Override
	public String toString() {
		return super.toString()+'\n';
	}
}
