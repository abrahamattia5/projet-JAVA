package Ihm;

public class Compte {

	private int numCpt;
	private float solde;
	
	public Compte(int num, float s) {
		numCpt = num;
		solde = s;
	}
	
	public void consulter() {
		System.out.print("Solde = "+ solde +" ");
	}
	
	public int getNum() {
		return numCpt;
	}
	public float getSolde() {
		return solde;
	}
}
