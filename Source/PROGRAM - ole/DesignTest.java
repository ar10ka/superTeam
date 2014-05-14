



import java.awt.BorderLayout;
import java.util.*;


import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.border.EmptyBorder;



public class DesignTest extends JFrame{
	
	private JFrame f;

	private JToolBar toolbar;
	private Lytter sensor;
	//private List<JButton> knappeListe = new ArrayList<>();
	private JButton[] knappeListe;
	private toolBarKnapper knapper;
        private Logg logg;
        private CardLayout card;
        private JPanel hovedpanel,panel5,panel6;
        private LegeRegisterPanel panel1;
        private PasientRegisterPanel  panel2;
        private MedisinRegisterPanel  panel3;
        private ReseptRegisterPanel  panel4;
     /*   private StatistikkPanel  panel5;
        private InformasjonPanel  panel6;*/
        
        
        
  
  	

	
	public DesignTest() {
                logg = new Logg();
		f = new JFrame("Design-test");
		knapper = new toolBarKnapper();
		sensor = new Lytter();
		knappeListe = knapper.returnerObjekter();
		toolbar();
                

                
                
                //LEGGER TIL VERKTØYLINJE
		f.add(toolbar, BorderLayout.NORTH);	 
                
                //Layouten
                card = new CardLayout();
                //Hovedpanel
          hovedpanel = new JPanel();
          hovedpanel.setLayout(card);
          
          // paneler
          panel1 = new LegeRegisterPanel();
          panel2= new PasientRegisterPanel();
          panel3= new MedisinRegisterPanel();
          panel4= new ReseptRegisterPanel();
          panel5= new JPanel();//StatestikkPanel();
          panel6= new JPanel();//InformasjonsPanel();
          
          
          // ***** MÅ LAGE NY METODE FOR Å GJØRE KODE LETTERE
          //panel1.setLayout(null);
         // panel2.setLayout(null);
         // panel3.setLayout(null);
          //panel4.setLayout(null);
          panel5.setLayout(null);
          panel6.setLayout(null);
          
          panel1.setName("LegeRegister");
         
          panel2.setName("PasientRegister");
          panel3.setName("MedisinRegister");
          panel4.setName("ReseptRegister");
          //panel4.setBackground(Color.ORANGE);
          panel5.setBackground(Color.pink);
          panel6.setBackground(Color.yellow);
          
          
          hovedpanel.add(panel1, "1");
          hovedpanel.add(panel2, "2");
          hovedpanel.add(panel3, "3");
          hovedpanel.add(panel4, "4");
          hovedpanel.add(panel5, "5");
          hovedpanel.add(panel6, "6");
          
          card.show(hovedpanel, "0");



          hovedpanel.setBorder(new EmptyBorder(12, 12, 12, 12));
          
          
            //JFRAME egenskaper
          f.getContentPane().setBackground(toolbar.getBackground());
          f.setSize(1200,900);
          f.setVisible(true);
          f.getContentPane().add(hovedpanel,BorderLayout.CENTER);          
         // f.getContentPane().add(logg,BorderLayout.SOUTH);          

          
          //f.add(new JList(new LegeRegister().returnObjekt()),BorderLayout.SOUTH);
          //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
                
          
                
                               
                //LEGGE TIL ANDRE KOMPONENTER
          
      	    f.addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
                    //BURDE LAGRE ALLE PANELS
	            lagreFilJPanel();
	            System.exit(0);
	        }
	     });
	   
		
          

                
                
                //ObjectList til venstre
                //User Interface til høyre
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
	
		
		for(JButton x: knappeListe) {
			x.addActionListener(sensor);
			toolbar.add(x);
		}
		

		//toolbar.setBackground(new Color(128,128,228));
		
		toolbar.setFloatable(false);
		knappeListe[0].setBackground(null);
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
		    				card.show(hovedpanel, ""+(i+1));
		    			if(i == 1)
	    					card.show(hovedpanel,  ""+(i+1));
		    			if(i == 2)
	    					card.show(hovedpanel,  ""+(i+1));
		    			if(i == 3)
	    					card.show(hovedpanel, ""+(i+1));
		    			if(i == 4)
	    					card.show(hovedpanel,  ""+(i+1));
		    			if(i == 5)
	    					card.show(hovedpanel,  ""+(i+1));
		    			
		    			knappeListe[i].setBackground(null);
		    		}
		    		else  { knappeListe[i].setBackground(new Color(128,128,128));
		    				knappeListe[i].setBorder(utenlinjer); }
		    				
		    	}


		    }
	  }
	  
private void lagreFilJPanel()
{
   try
    {
                panel1.lagreFil();
                panel2.lagreFil();
                panel3.lagreFil();
                panel4.lagreFil();
                //panel5.lagreFil();
                //panel6.lagreFil();
                
    }
    catch (IOException ex)
            {
               ex.printStackTrace();
            }
}

	
	public static void main (String [] args) {
		new DesignTest();
	}
	

}


class toolBarKnapper 
{
	private int antallKnapper = 6;
	private JButton toolbarKnappen [];
	Border emptyBorder = BorderFactory.createEmptyBorder();
	

	
	public toolBarKnapper() 
        {
            toolbarKnappen = new JButton[antallKnapper];


            toolbarKnappen[0] = new JButton(" Lege Register");
            toolbarKnappen[1] = new JButton(" Pasient Register");
            toolbarKnappen[2] = new JButton(" Medisin Register");
            toolbarKnappen[3] = new JButton(" Resept Register");
            toolbarKnappen[4] = new JButton(" Statistikk");
            toolbarKnappen[5] = new JButton(" Informasjon");

            for(JButton knappen: toolbarKnappen) 
            {
                    knappen.setBackground((new Color(128,128,128)));
                    knappen.setBorder(emptyBorder);
                    knappen.setFocusPainted(false);
            }
/*
	toolbarKnappen[0].setIcon(new ImageIcon(getClass().getResource("../ikoner/lege.png")));
	toolbarKnappen[1].setIcon(new ImageIcon(getClass().getResource("../ikoner/pasient.png")));
	toolbarKnappen[2].setIcon(new ImageIcon(getClass().getResource("../ikoner/medisin.png")));
	toolbarKnappen[3].setIcon(new ImageIcon(getClass().getResource("../ikoner/resept.png")));
	toolbarKnappen[4].setIcon(new ImageIcon(getClass().getResource("../ikoner/statistikk.png")));
	toolbarKnappen[5].setIcon(new ImageIcon(getClass().getResource("../ikoner/info.png")));*/
	
        toolbarKnappen[0].setIcon(new ImageIcon(getClass().getResource("ikoner/lege.png")));
	toolbarKnappen[1].setIcon(new ImageIcon(getClass().getResource("ikoner/pasient.png")));
	toolbarKnappen[2].setIcon(new ImageIcon(getClass().getResource("ikoner/medisin.png")));
	toolbarKnappen[3].setIcon(new ImageIcon(getClass().getResource("ikoner/resept.png")));
	toolbarKnappen[4].setIcon(new ImageIcon(getClass().getResource("ikoner/statistikk.png")));
	toolbarKnappen[5].setIcon(new ImageIcon(getClass().getResource("ikoner/info.png")));
        
        }
	
	public JButton[] returnerObjekter () 
        {
		return toolbarKnappen;	
	}
	
	

}




