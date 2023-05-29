package ProjetBanqueEshel;

public class Client {

	private String nom;
	private String prenom;
	private String adresse;
	private int idClient;
	private Banque refBanque;
	private IhmComptes refIhm;
	
	//private boolean isCptExists;
	
	public Client(int id, String name, String pren, String ad, Banque b) {
		
		idClient =id;
		nom = name;
		prenom = pren;
		adresse = ad;
		refBanque = b;
		//refIhm = ihm;
		//isCptExists = false;
	}
	
	public void allerOuvrirUnCompte(float s) {
		
		// Verifier si un compte est deja ouvert pour
		// ce client A FAIRE!!
		
		refBanque.ouvrirCompte(idClient,nom,prenom,adresse,s);
	}
	
	public void allerConsulterCompte() {
		refBanque.recupererMontantSolde(idClient);
	}
	
	public void allerDeposerSommeSurCompte(int ident, float somme) {
		refBanque.crediterCompte(ident,somme);
	}
	
	public void allerRetirerSommeDeCompte(int ident, float somme) {
		refBanque.debiterCompte(ident,somme);
	}
	
	public int getId() {
		return idClient;
	}
}
