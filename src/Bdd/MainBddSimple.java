package Bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MainBddSimple {

	public static void main(String[] args) {
		
		Connection cnx;
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData resMeta;
		

		// Recherche et chargement du driver en memoire
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver trouve!");
			
			// Connexion a la BDD
			try {
				cnx = DriverManager.getConnection("jdbc:mysql://localhost:3308/javaprojetbanque?useSSL=false", "root", "");
				System.out.println("BDD trouvee!");
				
				// Creation du conteneur de requete
				stmt = cnx.createStatement();
				
				// Envoi de la requete et recuperation de la reponse
				rs = stmt.executeQuery("SELECT * FROM compte");
				
				//Recuperer de rs les meta data
				//la metadata va chercher le type de structuration de la BDD
				resMeta = rs.getMetaData();
				//recuperer le nombre de colonne de la reponse
				int nbCols = resMeta.getColumnCount();	
				
				//affichage des noms des colonnes
				for(int i = 1; i<= nbCols; i++) {
					System.out.print(resMeta.getColumnName(i) + " |"+" " );
				}
				System.out.println();
				System.out.println("--------------------");
				
				//traitement de la requete
				while(rs.next()){
					for(int i = 1; i <= nbCols; i++) {
						System.out.print(rs.getObject(i).toString() + " ");
					}
					System.out.println();
				}
				
	 
				
				// Requete INSERT
				stmt.executeUpdate("insert into compte VALUES(12,100,'premier client')");
				//requette 
			} catch (SQLException e) {
				
				System.out.println("BDD NON trouvee!");
			}

		} catch (ClassNotFoundException e) {
			
			System.out.println("Driver NON trouve!");
		}
	}
}

/*
 
 rs = stmt.executeQuery("SELECT * FROM compte");
stmt.executeUpdate         pour tout le reste
 */
