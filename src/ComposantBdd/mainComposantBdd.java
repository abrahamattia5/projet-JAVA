package ComposantBdd;

import java.sql.SQLException;

public class mainComposantBdd {

	public static void main(String[] args) {
		
		//creation de l'objet composant BDD
		
		composantBdd bdd = new composantBdd();
		
		bdd.chargerDriver();
		bdd.connexionBdd("javaprojetbanque");
		bdd.creationConteneur();
		bdd.envoiReqSelect("select * from compte");
		try {
			bdd.recupReponse();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

