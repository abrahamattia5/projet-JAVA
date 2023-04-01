package Ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FenetrePerso extends JFrame implements ActionListener, ItemListener {

	// Attributs 
	
	private boolean isMr = false;
	private boolean isMm = false;
	private boolean isButtonsGrey = false;
	
	//private String coulChoisie = "";
	
	// Compteur pour verif entré 2 fois dans 
	// itemStateChanged() lors de la selection d'une option
	// dans la liste deroulante listeCouleurs
	int cpt = 0;
	
	// Boutons
	private JButton bouton1 = new JButton("OK");
	private JButton bouton2 = new JButton("Cancel");
	private JButton boutonGriser = new JButton("Griser");
	
	// Cases a cocher
	private JCheckBox case1 = new JCheckBox("Choix 1");
	
	// Radio boutons
	private JRadioButton rad1 = new JRadioButton("Mr");
	private JRadioButton rad2 = new JRadioButton("Mme");
	
	// Pour grouper les radio boutons
	private ButtonGroup bg = new ButtonGroup();
	
	// Les labels
	private JLabel label = new JLabel("Saisir un nom");
	
	// Les zones de saisie
	private JTextField zoneSaisie = new JTextField(20);
	
	// Les zones TextArea avec ascenseurs
	private JTextArea textPane = new JTextArea(10,30);
	private JScrollPane scroll = new JScrollPane(textPane);
	
	// Tableau de String contenant toutes les options de
	// la liste deroulante
	String [] optionsCouleurs = {"Rouge", "Bleu", "Vert", "Orange", "Jaune"};
	
	// Les listes deroulantes (JComboBox)
	private JComboBox listeCouleurs = new JComboBox(optionsCouleurs);
	
	
	
	// Constructeur 
	public FenetrePerso() {
		
		//Définit un titre pour votre fenêtre
		setTitle("Ma fenêtre perso");
				
		//Définit une taille de la fenetre ; ici, 
		// 400 px de large et 500 px de haut
		setSize(400, 500);
				
		// Nous allons maintenant dire à notre objet de se
		// positionner au centre
		setLocationRelativeTo(null);
				
		//Terminer le processus lorsqu'on clique sur
		// "Fermer" (croix)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Instanciation d'un objet JPanel (Toile du peintre)
		JPanel pan = new JPanel();
		
		//Définition de la couleur de fond du panel
		pan.setBackground (Color.cyan);
		
		//On associe le panel cree (toile) au JFrame (cadre) 
		// qui sera son contentPane
		setContentPane(pan);
		
		// Positionner les controles sur le panel
		pan.add(bouton1);
		pan.add(bouton2);
		pan.add(case1);
		pan.add(rad1);
		pan.add(rad2);
		pan.add(label);
		pan.add(zoneSaisie);
		pan.add(scroll);
		pan.add(listeCouleurs);
		
		// Grouper les radio boutons
		bg.add(rad1);
		bg.add(rad2);
		
		pan.add(boutonGriser);
		
		
		
		// Associer l'evenement bouton (click) au traitement
		// actionPerformed()
		bouton1.addActionListener(this);
		bouton2.addActionListener(this);
		case1.addActionListener(this);
		rad1.addActionListener(this);
		rad2.addActionListener(this);
		
		boutonGriser.addActionListener(this);
		
		listeCouleurs.addItemListener(this);
		// Selectionner Mme par defaut au lancement 
		// rad2.setSelected(true);
		
		// Desactiver le bouton
		//bouton1.setEnabled(false);
		
		// Rendre la fenetre visible
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String s;
		
		if(e.getSource() == bouton1) {
			
			// Recuperer le contenu de zoneSaisie
			s = zoneSaisie.getText();
			
			// Tester si la chaine est vide
			if(!s.isEmpty()) {
				
				if(isMr == true) {
					System.out.println("Bonjour Monsieur "+ s);
					textPane.append(s+"\n");
				}
				else 
					System.out.println("Bonjour Mme "+ s);
			}
			else 
				System.out.println(" Attention champ vide !!" );
			
			//System.out.println("color = "+coulChoisie);
		}
		
		else if(e.getSource() == bouton2) {
			System.out.println("Vous avez clique sur Cancel!!");
			
			// Effacer le contenu de la zone de saisie
			zoneSaisie.setText("");
		}
		else if(e.getSource() == case1) {
			if(case1.isSelected()) {
				System.out.println("Case cochee !!");
				label.setText("Hello");
			}
			else {
				System.out.println("Case NON cochee !!");
				label.setText("Saisir un nom");
			}
		}
		
		else if(e.getSource() == rad1) {
			//System.out.println("Monsieur !!");
			isMr = true;
			isMm = false;
		}
		else if(e.getSource() == rad2) {
			//System.out.println("Madame !!");
			isMr = false;
			isMm = true;
		}
		
		else if(e.getSource() == boutonGriser) {
			
			// Toggle sur le bouton boutonGriser
			if(isButtonsGrey) {
				bouton1.setEnabled(true);
				bouton2.setEnabled(true);
				isButtonsGrey = false;
			}
			else {
				bouton1.setEnabled(false);
				bouton2.setEnabled(false);
				isButtonsGrey = true;
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	
		//String coulChoisie;
		//System.out.println("Option "+e.getItem());
		//coulChoisie  = (String) e.getItem();
		cpt++;
		if(cpt==2) {
			//System.out.println("coulChoisie "+coulChoisie);
			System.out.println("getSelItem "+listeCouleurs.getSelectedItem());
			cpt=0;
		}
	}
}
