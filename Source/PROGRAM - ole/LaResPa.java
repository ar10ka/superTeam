
/**
 *
 * @author Arthika Surendran - s198757
 */

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;

//HOVEDKLASSEN
public class LaResPa extends JFrame{
	
	private final JFrame f;
	private JToolBar toolbar;
	private final Lytter sensor;
	private final JButton[] knappeListe;
	private final toolBarKnapper knapper;
        private final CardLayout card;
        private final JPanel hovedpanel;
        private final LegeRegisterPanel panel1;
        private final PasientRegisterPanel  panel2;
        private final MedisinRegisterPanel  panel3;
        private final ReseptRegisterPanel  panel4;
        private final StatistikkPanel  panel5;
     
	
	public LaResPa() {
		f = new JFrame("LaResPa-REG");
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
                panel5= new StatistikkPanel();
                
                panel1.setName("LegeRegister");
                panel2.setName("PasientRegister");
                panel3.setName("MedisinRegister");
                panel4.setName("ReseptRegister");
                panel5.setName("StatistikkRegister");

                hovedpanel.add(panel1, "1");
                hovedpanel.add(panel2, "2");
                hovedpanel.add(panel3, "3");
                hovedpanel.add(panel4, "4");
                hovedpanel.add(panel5, "5");
                

                card.show(hovedpanel, "0");
                hovedpanel.setBorder(new EmptyBorder(12, 12, 12, 12));


                  //JFRAME egenskaper
                f.getContentPane().setBackground(toolbar.getBackground());
                f.setExtendedState(JFrame.MAXIMIZED_BOTH);
                f.setSize(1000,900);
                f.setVisible(true);
                f.getContentPane().add(hovedpanel,BorderLayout.CENTER);          

                  f.addWindowListener(new WindowAdapter()
                  {
                      @Override
                      public void windowClosing(WindowEvent e)
                      {
                          //LAGRER ALLE FELT NÅR MAN LUKKER VINDUET
                          lagreFilAlle();
                          System.exit(0);
                      }
                   });

	}

        //Oppretter toolbar. Den henter knappene fra klassen toolBarKnapper
	public void toolbar() {
		
		Border utenLinjer = BorderFactory.createEmptyBorder();
		toolbar =  new JToolBar();		
		toolbar.setLayout(new GridLayout());
		toolbar.setBorder(utenLinjer);
	
		for(JButton x: knappeListe) {
			x.addActionListener(sensor);
			toolbar.add(x);
		}
		toolbar.setFloatable(false);
		knappeListe[0].setBackground(null);
		toolbar.setVisible(true);
	}
        
        //Lytterklasse
	 private class Lytter implements ActionListener {
		    @Override
		    
		    public void actionPerformed(ActionEvent e)
		    {
                        //Lagrer filen i det panelet du var i før du skiftet panel
		    	lagreFilJPanel();
		    	Border utenlinjer = BorderFactory.createEmptyBorder();
		    	
		    	
		    	for(int i = 0; i < knappeListe.length; i++) {
                            //Bytter panel
		    		if(e.getSource() == knappeListe[i]) {
		    			if(i == 0)
		    				card.show(hovedpanel, ""+(i+1));
		    			if(i == 1)
	    					card.show(hovedpanel,  ""+(i+1));
		    			if(i == 2)
	    					card.show(hovedpanel,  ""+(i+1));
		    			if(i == 3){
	    					card.show(hovedpanel, ""+(i+1));    
                                                try {
                                                    panel4.lagreFil();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(LaResPa.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                        }
                                        if(i == 4)
                                        {
	    					card.show(hovedpanel,  ""+(i+1));
                                                        try {
                                                    panel5.lastInnFil();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(LaResPa.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                        }
		    			
		    			knappeListe[i].setBackground(null);
		    		}
		    		else  { knappeListe[i].setBackground(new Color(128,128,128));
		    				knappeListe[i].setBorder(utenlinjer); }
		    				
		    	}


		    }
	  }
          // Kaller på lagreFil metoden i alle register objektene
        private void lagreFilAlle()
        {
           try
            {
                panel1.lagreFil();
                panel2.lagreFil();
                panel3.lagreFil();
                panel4.lagreFil();


            }
            catch (IOException ex)
                    {
                       ex.printStackTrace();
                    }
        }
        //Lagrer filen i det panel du var i før du skiftet panel
        private void lagreFilJPanel()
            {
               try
                {
                    for (Component comp : hovedpanel.getComponents()) {
                        if (comp.isVisible() == true) {
                            if("LegeRegister".equals(comp.getName()))
                                panel1.lagreFil();
                            if("PasientRegister".equals(comp.getName()))
                                panel2.lagreFil();
                            if("MedisinRegister".equals(comp.getName()))
                                panel3.lagreFil();
                            if("ReseptRegister".equals(comp.getName()))
                                panel4.lagreFil();
                        }
                    }

                }
                catch (IOException ex)
                        {
                           ex.printStackTrace();
                        }
            }

            //KJØRER PROGRAMMET
            public static void main (String [] args) {
                    new LaResPa();
            }
}


class toolBarKnapper 
{
	private final int antallKnapper = 5;
	private final JButton toolbarKnappen [];
	Border emptyBorder = BorderFactory.createEmptyBorder();
        
	public toolBarKnapper() 
        {
            toolbarKnappen = new JButton[antallKnapper];
            toolbarKnappen[0] = new JButton(" Lege Register");
            toolbarKnappen[1] = new JButton(" Pasient Register");
            toolbarKnappen[2] = new JButton(" Medisin Register");
            toolbarKnappen[3] = new JButton(" Resept Register");
            toolbarKnappen[4] = new JButton(" Statistikk");

            for(JButton knappen: toolbarKnappen) 
            {
                    knappen.setBackground((new Color(128,128,128)));
                    knappen.setBorder(emptyBorder);
                    knappen.setFocusPainted(false);
            }
        /*
                toolbarKnappen[0].setIcon(new ImageIcon("ikoner/lege.png"));
                toolbarKnappen[1].setIcon(new ImageIcon("ikoner/pasient.png"));
                toolbarKnappen[2].setIcon(new ImageIcon("ikoner/medisin.png"));
                toolbarKnappen[3].setIcon(new ImageIcon("ikoner/resept.png"));
                toolbarKnappen[4].setIcon(new ImageIcon("ikoner/statistikk.png"));

        */
                toolbarKnappen[0].setIcon(new ImageIcon(getClass().getResource("ikoner/lege.png")));
                toolbarKnappen[1].setIcon(new ImageIcon(getClass().getResource("ikoner/pasient.png")));
                toolbarKnappen[2].setIcon(new ImageIcon(getClass().getResource("ikoner/medisin.png")));
                toolbarKnappen[3].setIcon(new ImageIcon(getClass().getResource("ikoner/resept.png")));
                toolbarKnappen[4].setIcon(new ImageIcon(getClass().getResource("ikoner/statistikk.png")));
        }
	public JButton[] returnerObjekter () 
        {
		return toolbarKnappen;	
	}
}




