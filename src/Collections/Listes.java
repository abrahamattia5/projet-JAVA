package Collections;

import java.util.LinkedList;

public class Listes {

	public static void main(String[] args) {
		// Liste contenant des objets de types differents
		// Creation d'une liste vide
		LinkedList liste1 = new LinkedList();
		
		// Ajouter les elements suivants a la liste
		liste1.add(7);
		liste1.add("Bonjour");
		liste1.add(3.14f);
		
		// Taille de la liste
		System.out.println("Taille liste "+liste1.size());
		
		// Recuperer un element de la liste grace a son indice
		System.out.println("Element 1 "+liste1.get(1));
		
		// Parcours de la liste est affichage des elements de
		// cette liste
		for(int i=0; i < liste1.size(); i++) {
			System.out.println("Element "+liste1.get(i));
		}
		
		// Liste contenant des objets de meme type 
		// Creation d'une liste vide
		LinkedList<String> liste2 = new LinkedList<String>();
		liste2.add("Bonjour");
		liste2.add("Salut");
	}
}
