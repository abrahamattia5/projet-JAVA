package ProjetBanqueEshel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// ATENTION !!! Tester si les zones de saisie sont non vide!!!
// ATTENTION!!! verifier que lorsqu'on demande l'ouverture d'un compte
//              pour un client, ce dernier n'en possede pas deja un
//              Mettre un flag cptOuvert à true lors de 
//              l'ouverture du compte et tester ce flag
public class IhmComptes extends JFrame implements ActionListener{

	// Creation d'un objet JPanel (toile)
	private JPanel pan;
	
	// Liste des comptes
	private LinkedList<Compte> listCpt;
	
	// Liste des clients
	private LinkedList<Client> listClients;
	
	// Numero de compte
	private int numeroCompte;
	
	// Reference a la BDD Comptes
	private BddAccess refBdd;
	
	// Etiquettes
	private JLabel labelSolde;
	private JLabel labelNom;
	private JLabel labelPrenom;
	private JLabel labelIdentClient;
	private JLabel labelAdresse;
	private JLabel labelResultat;
	private JLabel labelMontant;
	
	// Zones de texte
	private JTextField zoneSolde;
	private JTextField zoneNom;
	private JTextField zonePrenom;
	private JTextField zoneAdresse;
	private JTextField zoneIdentClient;
	private JTextField zoneMontant;
	
	// Zones de texte Area
	private JTextArea zoneResultat = new JTextArea();
	
	// Ascenseurs
	private JScrollPane scroll = new JScrollPane(zoneResultat);
	
	// Boutons
	private JButton bCreerClient;
	private JButton bOuvrirCpt;
	private JButton bDebiterCpt;
	private JButton bCrediterCpt;
	private JButton bConsulterCpt;
	
	private Compte cpt; // Variable temporaire
	private Client client;
	
	// Creation de l'objet Banque
	private Banque bnk; 
	
	// Constructeur
	public IhmComptes() 
	{
		
		// Creation des controles
		labelSolde = new JLabel("Solde: ");
		labelNom   = new JLabel("Nom client: ");
		labelPrenom   = new JLabel("Prenom client: ");
		labelAdresse   = new JLabel("Adresse client: ");
		labelIdentClient = new JLabel("Ident client: ");
		labelResultat = new JLabel("Resultat: ");
		labelMontant = new JLabel("Montant: ");
		
		zoneSolde = new JTextField(20);
		zoneNom   = new JTextField(20);
		zonePrenom   = new JTextField(20);
		zoneAdresse   = new JTextField(30);
		zoneIdentClient = new JTextField(20);
		zoneMontant= new JTextField(20);
		//zoneResultat = new JTextArea(10, 20);
		
		bCreerClient = new JButton("Creer client");
		bOuvrirCpt = new JButton("Aller ouvrir compte");
		bDebiterCpt = new JButton("Aller retirer somme");
		bCrediterCpt = new JButton("Aller deposer compte");
		bConsulterCpt = new JButton("Aller consulter compte");
		
		// Creation du panel
		pan = new JPanel();
		
		// Creation de la liste des comptes
		listCpt = new LinkedList<Compte>();
		
		// Creation de la liste des clients
		listClients= new LinkedList<Client>();
		
		numeroCompte = 0;
		
		// Creer l'objet BddAccess
		refBdd = new BddAccess();
		
		// Charger le driver de BDD
		refBdd.chargerDriver();
		
		// Se connecter a la BDD
		refBdd.connecterBdd();
		
		// Creer la requete
		refBdd.creerRequete();
		
		// Passer au composant BDD la reference a l'ihm
		refBdd.setRefIhm(this);
		
		// Creation de l'objet banque
		bnk = new Banque("SG","55 rue Dufric", 100000000, refBdd, this);
		
		// Demander a la banque de se synchroniser sa
		// liste de comptes (vide a chaque demarrage) avec
		// le contenu de la base de donnees
		bnk.synchroniserComptesAvecBdd();
		
		System.out.println("taille liste clients: "+listClients.size());
		
		// A FAIRE!! Restaurer la liste des clients à
		// partir de la base de données 
		synchroniserClientsAvecBdd();
		
		
		System.out.println("taille liste clients: "+listClients.size());
		
		// Ajoute un titre a la fenetre
		setTitle("Gestion Comptes IHM");
		
		// Dimensionner la fenetre (300 pixels de large 
		// sur 400 de haut
		setSize(300, 400);
				
		// Rend la fenetre visible a l'ecran
		setVisible(true);
		
		// A la fermeture de la fenetre fermer le programme
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Associer le panel au cadre defini
		setContentPane(pan);
		
		// Associer une couleur de fond au Panel
		pan.setBackground(Color.green);
		
		// Positionnement des controles sur le JPanel
		pan.add(labelNom);
		pan.add(zoneNom);
		pan.add(labelPrenom);
		pan.add(zonePrenom);
		pan.add(labelAdresse);
		pan.add(zoneAdresse);
		
		pan.add(labelIdentClient);
		pan.add(zoneIdentClient);
		
		pan.add(labelMontant);
		pan.add(zoneMontant);
		
		pan.add(labelSolde);
		pan.add(zoneSolde);
		
		pan.add(bCreerClient);
		pan.add(bOuvrirCpt);
		pan.add(bDebiterCpt);
		pan.add(bCrediterCpt);
		pan.add(bConsulterCpt);
		pan.add(labelResultat);
		//pan.add(zoneResultat);
		
		/*
		zoneResultat.setBackground(Color.lightGray);
		zoneResultat.setForeground(Color.blue);
		zoneResultat.setRows(20);
		zoneResultat.setColumns(30);
		*/
		zoneResultat.setBackground(Color.lightGray);
		zoneResultat.setForeground(Color.blue);
		zoneResultat.setRows(20);
		zoneResultat.setColumns(30);
		scroll.setAutoscrolls(true);
		pan.add(scroll);
		
		
		// Enregistrer les boutons comme sources
		// d'evenements aupres de la fenetre IhmComptes
		bCreerClient.addActionListener(this);
		bOuvrirCpt.addActionListener(this);
		bDebiterCpt.addActionListener(this);
		bCrediterCpt.addActionListener(this);
		bConsulterCpt.addActionListener(this);
		
	}

	private void synchroniserClientsAvecBdd() 
	{
		
		ResultSet reponse;
		int nbCols = 4;
		Client cli;
		int id=0;
		String nom = null;
		String prenom = null;
		String adr = null;
		
		// Recuperer les identClient, numcompte et solde
		// de chaque compte en BDD et creer a partir de
		// ces infos, les objets Compte dans la liste de
		// la banque.
		
		// Requete select afin de recuperer que les contenus
		// des colonnes ident, numcompte et solde de la table
		// compte de la BDD.
		String req = "SELECT nom, prenom, Adresse, Ident from compte";
		refBdd.executerRequete(req);
		reponse = refBdd.recupereReponse();
		
		// Traiter la reponse
		if(reponse != null) {
			System.out.println("Reponse ok!!");
			
			try {
				System.out.println("Dans TRY while next!!"+reponse.next());
				if(reponse.first() != false) {
					
				//while(reponse.next()) {
					do {
					System.out.println("Dans while next!!");
					for(int i = 1; i <= nbCols; i++){ 
						if(i == 1) 
							nom = reponse.getObject(i).toString();
						else if(i == 2)
							prenom = reponse.getObject(i).toString();
						else if(i == 3)
							 adr = reponse.getObject(i).toString();
						else if(i == 4)
							id = Integer.parseInt(reponse.getObject(i).toString());
					}
					System.out.println("Nom = "+nom+ " "+ prenom+" "+adr+" "+id);
					// Creation d'un objet Client
					cli = new Client(id, nom, prenom, adr, bnk);
					
					// Placer le compte cree dans la liste
					listClients.add(cli);
					}while(reponse.next());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			afficherDansZoneArea("BDD vide!!");
			afficherDansZoneArea("\n");
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		
		String text, proprio;
		float nb;
		int numCpt;
		int id;
		Client cli;
		
		
		// Determiner la source de l'evenement
		if(evt.getSource() == bOuvrirCpt) {
			
			// Recuperer les contenus des zones ID, solde
			
			// Recuperer l'ident du client concerne
			text = zoneIdentClient.getText();
			id = Integer.parseInt(text);
		
			// Recuperer le solde et le convertir en float
			text = zoneSolde.getText();
			nb = Float.parseFloat(text);
			
			boolean trouve = false;
			int i = 0;
			
			// Verifier si cet Id existe dans la liste des clients
			while(trouve == false && i < listClients.size()) {
				cli = listClients.get(i);
				
				if(id == cli.getId()) {
					trouve = true;
					cli.allerOuvrirUnCompte(nb);
				}
				i++;
			}
			if(trouve == false) {
				zoneResultat.append("Client non trouve" +"\n");
			}
		}
		else if(evt.getSource() == bDebiterCpt) {
			float somme;
			
			// Recuperer l'ident du client
			text = zoneIdentClient.getText();
			
			// Convertir le numero saisi en int
			id = Integer.parseInt(text);
			
			// Recuperer le montant a crediter
			text = zoneMontant.getText();
			
			// Convertir le montant saisi en float
			somme = Float.parseFloat(text);
			
			// Recherche du client dans la liste
			if(listClients.isEmpty()) {
				zoneResultat.append("La liste clients est vide!!"+"\n");
			}
			else {
				boolean trouve = false;
				int i = 0;
				
				// Verifier si cet Id existe dans la liste des clients
				while(trouve == false && i < listClients.size()) {
					cli = listClients.get(i);
					
					if(id == cli.getId()) {
						trouve = true;
						cli.allerRetirerSommeDeCompte(id,somme);
					}
					i++;
				}
				if(trouve == false) {
					zoneResultat.append("Client non trouve" +"\n");
				}
			}
		}
		else if(evt.getSource() == bCrediterCpt) {
			float somme;
			
			// Recuperer l'ident du client
			text = zoneIdentClient.getText();
			
			// Convertir le numero saisi en int
			id = Integer.parseInt(text);
			
			// Recuperer le montant a crediter
			text = zoneMontant.getText();
			
			// Convertir le montant saisi en float
			somme = Float.parseFloat(text);
			
			// Recherche du client dans la liste
			if(listClients.isEmpty()) {
				zoneResultat.append("La liste clients est vide!!"+"\n");
			}
			else {
				boolean trouve = false;
				int i = 0;
				
				// Verifier si cet Id existe dans la liste des clients
				while(trouve == false && i < listClients.size()) {
					cli = listClients.get(i);
					
					if(id == cli.getId()) {
						trouve = true;
						cli.allerDeposerSommeSurCompte(id,somme);
					}
					i++;
				}
				if(trouve == false) {
					zoneResultat.append("Client non trouve" +"\n");
				}
			}
		}
		else if(evt.getSource() == bConsulterCpt) {
			// Recuperer l'id du client concerne
			text = zoneIdentClient.getText();
									
			// Convertir l'Id saisi en int
			id = Integer.parseInt(text);
			System.out.println( "Id "+id);
									
			// Recherche du client dans la liste
			if(listClients.isEmpty()) {
				zoneResultat.append("La liste clients est vide!!"+"\n");
			}
			else {
				boolean trouve = false;
				int i = 0;
				
				// Verifier si cet Id existe dans la liste des clients
				while(trouve == false && i < listClients.size()) {
					cli = listClients.get(i);
					
					if(id == cli.getId()) {
						trouve = true;
						cli.allerConsulterCompte();
					}
					i++;
				}
				if(trouve == false) {
					zoneResultat.append("Client non trouve" +"\n");
				}
			}
		}
		
		else if(evt.getSource() == bCreerClient) 
		{
			
			// Recuperer l'id client  et le convertir en int
			text = zoneIdentClient.getText();
			id = Integer.parseInt(text);
			
			String nom = zoneNom.getText();
			String prenom = zonePrenom.getText();
			String adresse = zoneAdresse.getText();
			
			boolean trouve = false;
			int i = 0;
			
			// Verifier si cet Id client existe dans la liste des clients
			while(trouve == false && i < listClients.size()) {
				cli = listClients.get(i);
				
				if(id == cli.getId()) {
					trouve = true;
				}
				i++;
			}
			if(trouve == true) {
				zoneResultat.append("Un client existe deja avec cet Id" +"\n");
			}
			else {
				client = new Client(id, nom, prenom, adresse, bnk);
				listClients.add(client);
			}
		}
	}
	
	public void afficherDansZoneArea(String message) {
		zoneResultat.append(message);
		//zoneResultat.append("\n");
	}
}
