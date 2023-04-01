package ComposantBdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class composantBdd 
{
	// attributs de ma classe composant bdd
	private Connection cnx;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData resMeta;
	
	
	// 1 chargement  DRIVER
	public void chargerDriver()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver trouve!");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver non trouve!");
			e.printStackTrace();
		}
	}
	
	
	// 2 CONNEXION BDD	
	public void connexionBdd(String nomBase)
	{
		try 
		{
			cnx = DriverManager.getConnection("jdbc:mysql://localhost:3308/"+nomBase+"?useSSL=false", "root", "");
			System.out.println("BDD trouvee!");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("BDD nom trouvee!");
		}
	}
	
	
	
	// 3 creation CONTENEUR REQUETTE 
	public void creationConteneur()
	{
		try {
			stmt = cnx.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 4 envoi requette SELECT
	public void envoiReqSelect(String req)
	{
		try 
		{
			rs = stmt.executeQuery(req);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	// 5 envoi requette UPDATE
		public void envoiReqUpdate(String req)
		{
			try 
			{
				stmt.executeUpdate(req);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
		
		// 6 recuperer la reponse de la requette contenue dans rs 
		public void recupReponse() throws SQLException
		{
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
			
		}
		
	
	
		
}
	

