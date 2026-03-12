package modele;

public class CreatureCard extends Card{
	
	public enum Effects {TRAMPLE,HASTE,FLYING};
	
	private int atk;
	private int def;
	private Effects[] effects;
	private int tC;
	private int cC;

	public CreatureCard(Colors color, String nom, int attack, int defense, Effects[] bonus, int totalCost, int colorCost) {
		super(color, nom, Type.CREATURE);
		atk = attack;
		def = defense;
		effects = bonus;
		tC = totalCost;
		cC = colorCost;
	}

	@Override
	public String toString() {
		var retour = super.toString();
		retour += " | attack : " + atk + " | defense : " + def + " | cost : " + (tC-cC) + "+" + cC + " | effects : ";
		for (var effect : effects) {
			retour += effect + " ";
		}
		return retour + '\n';
	}
	
}
