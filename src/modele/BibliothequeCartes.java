package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeSet;

public class BibliothequeCartes {

    public static ArrayList<Card> chargerCartesDepuisCSV(String cheminFichier) {
        ArrayList<Card> catalogue = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne = br.readLine(); 

            while ((ligne = br.readLine()) != null) {
                String[] donnees = ligne.split(";");

                if (donnees.length < 6) continue;

                String nom = donnees[0];
                Card.Colors couleur = Card.Colors.valueOf(donnees[1]);
                int coutTotal = Integer.parseInt(donnees[2]);
                int coutCouleur = Integer.parseInt(donnees[3]);
                int attaque = Integer.parseInt(donnees[4]);
                int defense = Integer.parseInt(donnees[5]);

                TreeSet<Card.Effects> effets = new TreeSet<>();
                
                if (donnees.length == 7 && !donnees[6].trim().isEmpty()) {
                    String[] listeEffets = donnees[6].split(",");
                    for (String effetStr : listeEffets) {
                        effets.add(Card.Effects.valueOf(effetStr.trim()));
                    }
                }

                Card nouvelleCarte = new Card(couleur, nom, false, attaque, defense, effets, coutTotal, coutCouleur);
                catalogue.add(nouvelleCarte);
            }
            
        } catch (Exception e) {
            System.out.println("erreur lors de la lecture du fichier " + cheminFichier);
            e.printStackTrace();
        }

        return catalogue; 
    }
}