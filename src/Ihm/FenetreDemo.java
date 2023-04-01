package Ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FenetreDemo extends JFrame implements ActionListener {

	// Attributs 
	// Les labels
	private JLabel labelNom = new JLabel("Saisir un nom");
	private JLabel labelNumCpt = new JLabel("Numero compte");
	
	// Les zones de saisie
	private JTextField zoneSaisie = new JTextField(20);
	private JTextField zoneNumCpt = new JTextField(20);
	
	// Les boutons
	private JButton bouton1 = new JButton("Recuperer num cpt");
	private JButton bouton2 = new JButton("Recuperer nom");
	private JButton boutonCreer = new JButton("Creation compte");
	
	// Cases a cocher
	private JCheckBox case1 = new JCheckBox("Compte courant");
	private JCheckBox case2 = new JCheckBox("Compte Epargne");
	
	// Radio boutons
	private JRadioButton radMr = new JRadioButton("Mr");
	private JRadioButton radMme = new JRadioButton("Mme");
	
	// Pour grouper les radio boutons
	private ButtonGroup bg = new ButtonGroup();
	
	// Zone texte AREA
	private JTextArea zoneTextes = new JTextArea(20,50);
	
	// JPanel -> correspond a la toile du cadre
	private JPanel toile = new JPanel();
	
	// Variables booleenes
	private boolean isCptCourant = false;
	private boolean isCptEpargne = false;
	
	private Compte cpt;
	
	// Pour des valeurs de debut de num et solde
	private int debNumCpt =0;
	private int debValSolde =200;
	
	// Creation d'une liste d'objets Compte vide
	LinkedList<Compte> listeComptes = new LinkedList<Compte>();
	
	// Constructeur 
	public FenetreDemo() {
		
		//Définit un titre pour votre fenêtre
		setTitle("Ma fenêtre perso");
		
		//Définit une taille de la fenetre ; ici, 
		// 400 px de large et 500 px de haut
		setSize(400, 500);
		
		// Nous allons maintenant dire à notre objet de se
		// positionner au centre
		setLocationRelativeTo(null);
		
		//Définition de la couleur de fond du panel
		toile.setBackground (Color.green);
		
		// Positionner les controles sur le panel (toile)
		toile.add(labelNom);
		toile.add(zoneSaisie);
		toile.add(labelNumCpt);
		toile.add(zoneNumCpt);
		
		// Remplir par defaut le champ zoneNumCpt
		zoneNumCpt.setText("Entrez uniquement du numerique!");
		
		toile.add(bouton1);
		toile.add(bouton2);
		toile.add(boutonCreer);
		toile.add(case1);
		toile.add(case2);
		toile.add(zoneTextes);
		toile.add(radMr);
		toile.add(radMme);
		
		// Grouper les radio boutons
		bg.add(radMr);
		bg.add(radMme);
		
		// Associer l'evenement bouton (click) au traitement
		// correspondant defini dans actionPerformed()
		bouton1.addActionListener(this);
		bouton2.addActionListener(this);
		boutonCreer.addActionListener(this);
		case1.addActionListener(this);
		case2.addActionListener(this);
		radMr.addActionListener(this);
		radMme.addActionListener(this);
		
		//On associe le panel cree (toile) au JFrame (cadre) 
		// qui sera son contentPane
		setContentPane(toile);
		
		// Rendre la fenetre visible
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String s;
		int num;
		
		// Determiner de l'evenement
		if(e.getSource() == bouton1) {
			//System.out.println("Appui sur Bouton1");
			
			// Recuperer le contenu de la zone de saisie
			s = zoneNumCpt.getText();
						
			// Tester si la chaine s est vide
			if(!s.isEmpty()) {
				
				if( isCptCourant == true) {
					zoneTextes.append("compte courant ");
				}
				if( isCptEpargne == true) {
					zoneTextes.append("compte Epargne ");
				}
				
				//Conversion d'une String en int
				//num = Integer.parseInt(s);
				//System.out.println(num);
				zoneTextes.append(s);
				
				// Saut de ligne
				zoneTextes.append("\n");
			}
			else {
				System.out.println("Ce champ doit etre rempli!!");
			}
		}
		else if(e.getSource() == bouton2) {
			//System.out.println("Appui sur Bouton2");
			
			// Recuperer le contenu de la zone de saisie
			s = zoneSaisie.getText();
			
			// Tester si la chaine s est vide
			if(!s.isEmpty()) {
				System.out.println(s);
			}
			
			else {
				System.out.println("Ce champ doit etre rempli!!");
			}
		}
		else if(e.getSource() == boutonCreer) {
			// Creation d'un objet Compte
			debNumCpt = debNumCpt+1;
			debValSolde = debValSolde +20;
			cpt = new Compte(debNumCpt,debValSolde);
			
			listeComptes.add(cpt);
			
			for(int i =0; i < listeComptes.size(); i++) {
				listeComptes.get(i).consulter();
			}
			System.out.println();
		}
		else if(e.getSource() == case1) {
			if(case1.isSelected()) {
				System.out.println("Vous avez selectionne compte courant !!");
				
				isCptCourant = true;
			}
			else {
				System.out.println("Compte courant non selectionne !!");
				isCptCourant = false;
			}
		}
		else if(e.getSource() == case2) {
			if(case2.isSelected()) {
				System.out.println("Vous avez selectionne compte epargne  !!");
				isCptEpargne = true;
			}
			else {
				System.out.println("Compte epargne non selectionne  !!");
				isCptEpargne = false;
			}
		}
		else if(e.getSource() == radMr) {
			System.out.println("Monsieur !!");
		}
		else if(e.getSource() == radMme) {
			System.out.println("Madame !!");
		}
	}
}
