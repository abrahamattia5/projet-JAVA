package ProjetBanqueEshel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Banque {

	private String raisonSoc;
	private String adr;
	private int capital;
	private Compte cpt;
	private BddAccess refBdd;
	
	private IhmComptes refIhm;
	private imh_2 refIhm2;
	
	private int nbComptes = 0;
	
	private LinkedList<Compte> listComptes; 
	
	public Banque(String rs, String adresse, int cap, BddAccess bdd, IhmComptes ihm) {
		raisonSoc = rs;
		adr = adresse;
		capital = cap;
		refBdd = bdd;
		refIhm = ihm;
		
		// Creation de la liste de comptes
		listComptes = new LinkedList<Compte>();
	}

	public Banque(String rs, String adresse, int cap, BddAccess bdd, imh_2 ihm) {
		raisonSoc = rs;
		adr = adresse;
		capital = cap;
		refBdd = bdd;
		refIhm2 = ihm;
		
		// Creation de la liste de comptes
		listComptes = new LinkedList<Compte>();
	}

	public void ouvrirCompte(int idClient, String nom, String prenom, String adresse, float s) {
		
		cpt = new Compte((nbComptes+1), s, idClient, refIhm);
		
		// Placer le compte cree dans la liste
		listComptes.add(cpt);
		
		// Sauvegarder le compte dans la BDD
		// Formater la requete INSERT INTO
		String requete;
		int num = nbComptes+1;
		requete = "INSERT INTO Compte (nom, prenom, adresse,Ident, solde, numCompte) VALUES (";
		requete = requete + '"'+nom +'"' +","+ '"'+prenom +'"'+ "," + '"'+adresse +'"' +"," + idClient+"," + s  +"," +num+ ")";
		
		// Executer la requete
		refBdd.executerUpdate(requete);
		
		// Un compte de plus cree
		nbComptes++;
	}

	public void recupererMontantSolde(int idClient) {
		boolean trouve = false;
		int i = 0;
		
		// Verifier si cet Id existe dans la liste des comptes
		while(trouve == false && i < listComptes.size()) {
			cpt = listComptes.get(i);
			
			if(idClient == cpt.getIdCli()) {
				trouve = true;
				refIhm.afficherDansZoneArea(""+cpt.consulter());
			}
			i++;
		}
		if(trouve == false) {
			refIhm.afficherDansZoneArea("Id Client non trouve" +"\n");
		}
	}

	public void crediterCompte(int ident, float somme) {
		boolean trouve = false;
		int i = 0;
		String req;
		float s;
		
		// Verifier si cet Id existe dans la liste des comptes
		while(trouve == false && i < listComptes.size()) {
			cpt = listComptes.get(i);
			
			if(ident == cpt.getIdCli()) {
				trouve = true;
				cpt.crediter(somme);
				s = cpt.consulter();
				
				// MAJ BDD
				req = "update compte set solde=" +s +" where Ident="+ident;
				// Executer la requete
				refBdd.executerUpdate(req);
				refBdd.executerRequete("select * from compte");
				refBdd.recupereReponse();
			}
			i++;
		}
		if(trouve == false) {
			refIhm.afficherDansZoneArea("Id Client non trouve" +"\n");
		}
		
	}

	public void debiterCompte(int ident, float somme) {
		boolean trouve = false;
		int i = 0;
		String req;
		float s;
		
		// Verifier si cet Id existe dans la liste des comptes
		while(trouve == false && i < listComptes.size()) {
			cpt = listComptes.get(i);
			
			if(ident == cpt.getIdCli()) {
				trouve = true;
				cpt.debiter(somme);
				s = cpt.consulter();
				
				// MAJ BDD
				req = "update compte set solde=" +s +" where Ident="+ident;
				// Executer la requete
				refBdd.executerUpdate(req);
				refBdd.executerRequete("select * from compte");
				refBdd.recupereReponse();
			}
			i++;
		}
		if(trouve == false) {
			refIhm.afficherDansZoneArea("Id Client non trouve" +"\n");
		}
	}

	public void synchroniserComptesAvecBdd() {
		
		ResultSet reponse;
		int nbCols = 3;
		Compte cpt;
		int num=0, id=0;
		float s = 0;
		
		// Recuperer les identClient, numcompte et solde
		// de chaque compte en BDD et creer a partir de
		// ces infos, les objets Compte dans la liste de
		// la banque.
		
		// Requete select afin de recuperer que les contenus
		// des colonnes ident, numcompte et solde de la table
		// compte de la BDD.
		String req = "SELECT Ident, numCompte, solde from compte";
		refBdd.executerRequete(req);
		reponse = refBdd.recupereReponse();
		
		// Traiter la reponse
		if(reponse != null) {
			System.out.println("Reponse ok!!");
			
			try {
				if(reponse.first() != false) {
				do {
						
					for(int i = 1; i <= nbCols; i++){ 
						if(i == 1) 
							id = Integer.parseInt(reponse.getObject(i).toString());
						else if(i == 2)
							num = Integer.parseInt(reponse.getObject(i).toString());
						else if(i == 3)
							 s = Float.parseFloat(reponse.getObject(i).toString());
					}
					// Creation d'un objet Compte
					cpt = new Compte(num, s, id, refIhm);
					
					// Placer le compte cree dans la liste
					listComptes.add(cpt);
				}while(reponse.next());
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			refIhm.afficherDansZoneArea("BDD vide!!");
			refIhm.afficherDansZoneArea("\n");
		}
		
		// Recuperer le nombre de comptes(clients) de 
		// la table compte
		req = "SELECT count(*) from compte";
		refBdd.executerRequete(req);
		reponse = refBdd.recupereReponse();
		
		// Traiter la reponse
		if(reponse != null) {
			try {
				if(reponse.first() != false) {
					System.out.println("Count OK!!");
				
					try {
						nbComptes = Integer.parseInt(reponse.getObject(1).toString());
						refIhm.afficherDansZoneArea("Nb comptes "+nbComptes);
						refIhm.afficherDansZoneArea("\n");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
}
