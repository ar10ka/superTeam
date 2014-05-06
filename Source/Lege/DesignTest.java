import java.awt.BorderLayout;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class DesignTest extends JFrame{
	
	private JFrame f;
	
	private JLabel l;
	private JToolBar toolbar;
	private Lytter sensor;
	//private List<JButton> knappeListe = new ArrayList<>();
	private JButton[] knappeListe;
	private toolBarKnapper knapper;
	private JPanel legeRegisterPanel;
	//private final int x = 4;
	

	
	public DesignTest() {
		f = new JFrame("Design-test");
		knapper = new toolBarKnapper();
		sensor = new Lytter();
		knappeListe = knapper.returnerObjektet();
		toolbar();
		f.getContentPane().setBackground(new Color(102,102,255));
		f.setSize(1000,500);
		f.setVisible(true);

	}


	public void toolbar() {
		
		//Border nedreLinja = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0,0,0));
		Border utenLinjer = BorderFactory.createEmptyBorder();
		//f.setUndecorated( true );
		//f.getRootPane().setWindowDecorationStyle(x);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		toolbar =  new JToolBar();		
		toolbar.setLayout(new GridLayout());
		toolbar.setBorder(utenLinjer);
		
		 legeRegisterPanel = new JPanel ();
		 legeRegisterPanel.setSize(20,20);
		 legeRegisterPanel.setBackground(Color.red);
		 legeRegisterPanel.setVisible(true);
		 
		//JFrame.setDefaultLookAndFeelDecorated(true);  
		f.add(toolbar,BorderLayout.NORTH);	 
		//f.add(legeRegisterLabel, BorderLayout.NORTH);
		
		
		for(JButton x: knappeListe) {
			x.addActionListener(sensor);
			toolbar.add(x);
		}
		

		toolbar.setBackground(new Color(128,128,228));
		
		toolbar.setFloatable(false);
		
		toolbar.setVisible(true);
		
	}
	
	 private class Lytter implements ActionListener {
		    @Override
		    
		    
		    public void actionPerformed(ActionEvent e)
		    {
		    	
		    	Border sidelinjer = BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(112,112,112));
		    	Border utenlinjer = BorderFactory.createEmptyBorder();
		    	
		    	
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
		    			
		    			knappeListe[i].setBackground(new Color(102,102,255));
		    			knappeListe[i].setBorder(sidelinjer);

		    		}
		    		else  { knappeListe[i].setBackground(new Color(128,128,128));
		    				knappeListe[i].setBorder(utenlinjer); }
		    				
		    	}


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
	Border emptyBorder = BorderFactory.createEmptyBorder();
	

	
	public toolBarKnapper() {

		toolbarKnappen = new JButton[antallKnapper];

	
	toolbarKnappen[0] = new JButton(" Lege Register");
	toolbarKnappen[1] = new JButton(" Pasient Register");
	toolbarKnappen[2] = new JButton(" Medisin Register");
	toolbarKnappen[3] = new JButton(" Resept Register");
	toolbarKnappen[4] = new JButton(" Statistikk");
	toolbarKnappen[5] = new JButton(" Informasjon");
	

	
	//
	for(JButton knappen: toolbarKnappen) {
		knappen.setBackground((new Color(128,128,128)));
		knappen.setBorder(emptyBorder);
		knappen.setFocusPainted(false);
		

		
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




