
public class Compte {

	// Commentaire sur une ligne
	// Attributs
	
	private int numCompte;
	private int solde;
	
	// Constructeur
	public Compte() {
		numCompte = 1;
		solde = 500;
	}
	
	public Compte(int num, int s) {
		numCompte = num;
		solde = s;
	}
	
	
	// Definition des methodes
	public void debiter(int somme) {
		
		if(solde >= somme) {
			solde -= somme;
		}
		else {
			System.out.println("Solde insuffisant");
		}
	}
	
	public void crediter(int somme) {
		solde += somme;
	}
	
	public int consulter() {
		return solde;
	}
}
