import java.awt.BorderLayout;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class DesignTest extends JFrame{
	
	private JFrame f;
	private JPanel p;
	
	private JLabel l;
	private Border border;
	private JToolBar toolbar;
	private Lytter sensor;
	//private List<JButton> knappeListe = new ArrayList<>();
	private JButton[] knappeListe;
	private toolBarKnapper knapper;
	

	
	public DesignTest() {
		knapper = new toolBarKnapper();
		sensor = new Lytter();
		knappeListe = knapper.returnerObjektet();
		toolbar();

	}
	
	
	/*public int selectedKnappen() {
		
		for( int i = 0; i <= knappeListe.length; i++) {
			if(knappeListe[i].isSelected()) {
				return i;
			}
		}
		return -1;
	} */
	

	public void toolbar() {
		

		f = new JFrame("Design-test");
		p = new JPanel();
		f.setSize(1000,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		toolbar =  new JToolBar();
		
		
		p.setLayout(new GridLayout());
		f.add(toolbar, BorderLayout.NORTH);
		
		
		for(JButton x: knappeListe) {
			x.addActionListener(sensor);
			toolbar.add(x);
		}
		

		toolbar.setBackground(Color.GRAY);
		f.setVisible(true);
		toolbar.setFloatable(false);
		
		toolbar.setVisible(true);

		
	}
	
	  private class Lytter implements ActionListener {
		    @Override
		    
		    public void actionPerformed(ActionEvent e)
		    {
		    	
		    	
		    	
		    	for(int i = 0; i < knappeListe.length; i++) {

		    		if(e.getSource() == knappeListe[i]) {
		    			if(i == 0)
	    					System.out.println("LegeRegister");
		    			if(i == 1)
	    					System.out.println("2");
		    			if(i == 2)
	    					System.out.println("3");
		    			if(i == 3)
	    					System.out.println("4");
		    			if(i == 4)
	    					System.out.println("5");
		    			if(i == 5)
	    					System.out.println("6");
		    			
		    			knappeListe[i].setBackground(new Color(200,200,200));
		    			
		    		}
		    		else knappeListe[i].setBackground(new Color(160,160,160));
		    				
		    				
		    	}

		    	
		    	
		    	
		    	/*
		    	if(e.getSource() == knappeListe[0]) {
		    		System.out.println("LegeRegister");
		    		knappeListe[0].setBackground(Color.blue);
		    	}  else  knappeListe[0].setBackground(Color.lightGray); 
		    	
		    	
		    	if(e.getSource() == knappeListe[1] ) {
		    		System.out.println("PasientRegister");
		    		knappeListe[1].setBackground(Color.blue);
		    	} else  knappeListe[1].setBackground(Color.lightGray); 
		    	
		    	
		    	if(e.getSource() == knappeListe[2] ) {
		    		System.out.println("3");
		    		knappeListe[2].setBackground(Color.blue);
		    	} else  knappeListe[2].setBackground(Color.lightGray); 
		    	
		    	
		    	if(e.getSource() == knappeListe[3] ) {
		    		System.out.println("4");
		    		knappeListe[3].setBackground(Color.blue);
		    	} else  knappeListe[3].setBackground(Color.lightGray); 
		    	
		    	
		    	if(e.getSource() == knappeListe[4] ) {
		    		System.out.println("5");
		    		knappeListe[4].setBackground(Color.blue);
		    	} else  knappeListe[4].setBackground(Color.lightGray); 
		    	
		    	
		    	if(e.getSource() == knappeListe[5] ) {
		    		System.out.println("6");
		    		knappeListe[5].setBackground(Color.blue);
		    	} else  knappeListe[5].setBackground(Color.lightGray);  */

		    }
	  }
	


	
	
	public static void main (String [] args) {
		new DesignTest();
	}
	

}


class toolBarKnapper {
	
	
	//private JButton legeKnappen, medisinKnappen, reseptKnappen, statistikkKnappen, infoKnappen, pasientKnappen;
	private int antallKnapper = 6;
	

	private JButton toolbarKnappen [];
	

	
	public toolBarKnapper() {

		toolbarKnappen = new JButton[antallKnapper];

	
	toolbarKnappen[0] = new JButton("Lege Register");
	toolbarKnappen[1] = new JButton("Pasient Register");
	toolbarKnappen[2] = new JButton("Medisin Register");
	toolbarKnappen[3] = new JButton("Resept Register");
	toolbarKnappen[4] = new JButton("Statistikk");
	toolbarKnappen[5] = new JButton("Informasjon");
	

	
	//
	for(JButton knappen: toolbarKnappen) {
		knappen.setBackground((new Color(160,160,160)));


		
	}

	
	toolbarKnappen[0].setIcon(new ImageIcon("ikoner/lege.png"));
	toolbarKnappen[1].setIcon(new ImageIcon("ikoner/pasient.png"));
	toolbarKnappen[2].setIcon(new ImageIcon("ikoner/medisin.png"));
	toolbarKnappen[3].setIcon(new ImageIcon("ikoner/resept.png"));
	toolbarKnappen[4].setIcon(new ImageIcon("ikoner/statistikk.png"));
	toolbarKnappen[5].setIcon(new ImageIcon("ikoner/info.png"));

	

	
	}
	
	public JButton[] returnerObjektet () {

		return toolbarKnappen;
		
	}
	
	

}




