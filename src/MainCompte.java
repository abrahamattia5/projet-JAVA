
public class MainCompte {

	public static void main(String[] args) {
		
		// Creation d'un objet Compte
		Compte cpt = new Compte();
		Compte cpt2 = new Compte(2, 200);
		int s;
		
		s = cpt.consulter();
		//System.out.println("Votre solde est de: "+s);
		
		cpt.debiter(100);
		s = cpt.consulter();
		//System.out.println("Votre solde est de: "+s);
		
		cpt.crediter(50);
		s = cpt.consulter();
		System.out.println("Votre solde est de: "+s);
		
		cpt.debiter(1000);
		s = cpt.consulter();
		System.out.println("Votre solde est de: "+s);
		
		s = cpt2.consulter();
		System.out.println("Votre solde est de: "+s);
	}

}
