package Program;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class LegeRegisterPanel extends JPanel {
	
	private JTextField  legeIDFelt, navnFelt, etternavnFelt, arbeidsStedFelt;
	private JButton kNyLege, kSlettLege, kVisAlt, kVisLege, search,kEndreLege;
	private JRadioButton ARadio;
	private JRadioButton BRadio;
	private JRadioButton CRadio;
	private JTextArea tekstomraade;
	private JTextArea logomraade;
	private char [] reseptGruppe = new char [] {'A', 'B', 'C'};
	private int legeID;
	
	private LegeRegister leger;
	private Lytter sensor;
	private Logg logg;
	
	
	//private String reseptGruppe;
	

	
	public LegeRegisterPanel() {
		
            logg = new Logg();
		
		leger = new LegeRegister();
		
	    try
	    {
	    	System.out.println("INNN I TRY");
	      lastInnFil();
	      System.out.println("ETTER LAST FIL");
	    }

	    catch (IOException ex)
	    {
	    	System.out.println("CATCH P� KON");
	      ex.printStackTrace();
	    }
		
		
		
		legeIDFelt = new JTextField(11);
		navnFelt = new JTextField(20);
		etternavnFelt = new JTextField(20);
		arbeidsStedFelt = new JTextField(20);
		
		ARadio = new JRadioButton("ReseptGruppe A");
		BRadio = new JRadioButton("ReseptGruppe B");
		CRadio = new JRadioButton("ReseptGruppe C");
		
                kEndreLege = new JButton("Endre Lege");
		kNyLege = new JButton("Reg Lege");
		kSlettLege = new JButton("Slett Lege");
		kVisLege = new JButton("Vis Lege");
		kVisAlt = new JButton("Vis Alt");
		search = new JButton("S�k Lege");
		
		tekstomraade  = new JTextArea(15, 55);
	    tekstomraade.setEditable(false);
	    JScrollPane rulle = new JScrollPane(tekstomraade);
	    
		logomraade  = new JTextArea(15, 55);
	    logomraade.setEditable(false);
	    JScrollPane rulle2 = new JScrollPane(logomraade);
	    
            setLayout( new FlowLayout() );
	    add(ARadio);
	    add(BRadio);
	    add(CRadio);
	    add(new JLabel("Lege ID:"));
	    add(legeIDFelt);
	    add(new JLabel("Fornavn:"));
	    add(navnFelt);
	    add(new JLabel("Etternavn:"));
	    add(etternavnFelt);
	    add(new JLabel("ArbeidsSted:"));
	    add(arbeidsStedFelt);
	    add(kNyLege);
	    add(kSlettLege);
	    add(kVisLege);
	    add(kVisAlt);
	    add(search);
            add(kEndreLege);
	    add(rulle);
	    add(rulle2);
		
		
		
		
		setSize(850, 400);
		setVisible(true);
		
		sensor = new Lytter();
		
	    kNyLege.addActionListener(sensor);
            kEndreLege.addActionListener(sensor);
	    kSlettLege.addActionListener(sensor);

	    kVisLege.addActionListener(sensor);
	    kVisAlt.addActionListener(sensor);
	    search.addActionListener(sensor);
	    logomraade.setText("");
	    
	
	   
		
		
	}
	
	/*private void groupButton() {
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(ARadio);
		bg1.add(BRadio);
		bg1.add(CRadio);
		
	} */
	
	
	  private void lastInnFil() throws IOException
	  {
	    try
	    {
	      FileInputStream fileHandle = new FileInputStream("LegeData.txt");
	      ObjectInputStream in = new ObjectInputStream(fileHandle);
	      leger = (LegeRegister) in.readObject();
	    }
	    catch (FileNotFoundException ex)
	    {
	      System.out.println("Lager ny lagringsfil");
	    }
	    catch (ClassNotFoundException ex)
	    { 
	    	 System.out.println("CLASS not found Exception");
	      ex.printStackTrace();
	    }
	    catch (EOFException ex)
	    {
	      System.out.println("Ferdig lastet!");
	    }
	  }
	
	  void lagreFil() throws IOException
	  {
	    try
	    {
	      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("LegeData.txt"));
	      out.writeObject(leger);
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
	
	
	
	
	public char[]  ActivRadio() {
				
		
		if(ARadio.isSelected() && !BRadio.isSelected() && !CRadio.isSelected()) {
			reseptGruppe = new char [] {'A'};

		}
		if(!ARadio.isSelected() && BRadio.isSelected() && !CRadio.isSelected()) {
			reseptGruppe = new char [] {'B'};

		}
		
		if(!ARadio.isSelected() && !BRadio.isSelected() && CRadio.isSelected()) {
			reseptGruppe = new char [] {'C'};

		}
		
		 if(ARadio.isSelected() && BRadio.isSelected() && !CRadio.isSelected()){
			reseptGruppe = new char [] {'A', 'B'};

		}
		
		
		if(ARadio.isSelected() && !BRadio.isSelected() && CRadio.isSelected()) {
			reseptGruppe = new char [] {'A', 'C'};

		}
		
		if(!ARadio.isSelected() && BRadio.isSelected() && CRadio.isSelected()){
			reseptGruppe = new char [] {'B', 'C'};

		}
		
		if(ARadio.isSelected() && BRadio.isSelected() && CRadio.isSelected()) {
			reseptGruppe = new char [] {'A', 'B', 'C'};	
		}
                if(!ARadio.isSelected() && !BRadio.isSelected() && !CRadio.isSelected())
                    reseptGruppe = null;
		
		return reseptGruppe;
	}
	
	/*public boolean validerNr() {
		String fNr = legeIDFelt.getText();
		char[] array = fNr.toCharArray();
		int antall = 0;
		for(char x: array){
			antall++;
		}
		if(fNr.matches("\\d+") && antall == 11)  {
			return true;
		}
		else 
			logomraade.append("Feil i f�dselsnummeret" + "\n");
			return false;
	} */
	
	
	public void nyLege() {
		
   try
    {
      
      String fnavn = navnFelt.getText();
      String enavn = etternavnFelt.getText();
      String arb = arbeidsStedFelt.getText();
      



      if (!fnavn.equals("") && !enavn.equals("")&& ActivRadio()!=null && !arb.equals("") )
      {
             Lege lege = new Lege(fnavn, enavn,arb, reseptGruppe, 0);
            leger.settInn(lege);
            logomraade.setText(logg.toString("Lege lagt til"));
     
      
        
      }
            if (fnavn.equals("") || enavn.equals("")|| ActivRadio()==null || arb.equals("") )
      {
         logomraade.setText("Fyll ut alle feltene!");
      }
    }
    catch (NumberFormatException e)
    {
      logomraade.setText("Fyll ut alle feltene!");
    }			
			
		}
        
        public void endreLege()
        {
            try
    {
      int id = Integer.parseInt(legeIDFelt.getText());
      String fnavn = navnFelt.getText();
      String enavn = etternavnFelt.getText();
      String arb = arbeidsStedFelt.getText();
      


      if (!fnavn.equals("") && !enavn.equals("") && !legeIDFelt.equals("") && !arb.equals("") )
      {
        if (leger.finnes(id))
        {          
            Lege lege = new Lege(fnavn, enavn,arb, ActivRadio(), id);
            
            
                if(leger.endreLege(lege))
                {
                    legeIDFelt.setText(""+id);
                    logomraade.setText(logg.toString("Pasient endret"));
                }
                else
                    logomraade.setText(logg.toString("Kunne ikke endre pasient! Prøv igjen!"));
            
      
        }
        else
        {
            logomraade.setText("Pasienten finnes ikke");
            
            int n = JOptionPane.showConfirmDialog(null,
                    "Pasienten finnes ikke!\nVil du legge til pasienten?",
                    "Ny pasient!",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION)
            {    
                nyLege();
                logomraade.setText(logg.toString("Pasient lagt til"));
            }
            else 
                logomraade.setText("Pasient er ikke lagt til");
                
        }
      
        
      }
        else
         logomraade.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      logomraade.setText("Fyll ut alle feltene!");
    }
            
        }
        
        
	
	public void search() {
		int legeID = Integer.parseInt(legeIDFelt.getText());
		String navn = navnFelt.getText();
		String etternavn = etternavnFelt.getText();
		char [] grupper;
		
		if(leger.finnes(legeID)) {
			Lege l = leger.finn(legeID);
			legeIDFelt.setText(""+l.getlegeID());			
			navnFelt.setText(l.getNavn());
			etternavnFelt.setText(l.getEtternavn());
			arbeidsStedFelt.setText(l.getArbeidsSted());
			grupper = l.getReseptGruppe();
			
			for(char x : grupper ){
				if(x == 'A') {
					ARadio.setSelected(true);
				}
				
				
				if(x == 'B') {
					BRadio.setSelected(true);
				}
				
				if(x == 'C') {
					CRadio.setSelected(true);
				}
				
				
			}
                        
			
		}
		
		
		
	}
	
	public void slettLege() {
		int legeID = Integer.parseInt(legeIDFelt.getText());
		if(leger.slettLege(legeID)) {
			String utskrift = "Legen med legeID: " + legeID + " Er fjernet \n"; 
			logomraade.append(logg.toString(utskrift));
			
		}
		else
		logomraade.append(logg.toString("Finnes ikke legen med forekommende f�dselsnummeret \n"));
	}
	
	
	public void visLege() {
		int legeID = Integer.parseInt(legeIDFelt.getText());
		String navn = navnFelt.getText();
		String etternavn = etternavnFelt.getText();
		
		if(leger.finnes(legeID)) {
			Lege l = leger.finn(legeID);
			tekstomraade.append(l.getNavn() + " " + l.getEtternavn() + " " +l.getlegeID() + "\n");
			String utskrift = "Lege(er) med er funnet med forekommende lege id \n";
			logomraade.append(logg.toString(utskrift));
			
		}
		
		else if(leger.finnes(navn, etternavn)) {
			/*List<Lege> l = leger.finn(navn, etternavn); */
			//for( Lege x: l) {
			tekstomraade.append(leger.finnOgReturner(navn, etternavn) + "\n");
			//}
			String utskrift = "Lege(er) med er funnet med forekommende navn og etternavn \n";
			logomraade.append(logg.toString(utskrift));
			
		}
		
	}
		
	
	
	public void visAlt() {
	 
		  tekstomraade.setText(leger.getText());
	}
		
	
	
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == kNyLege)
			{
				nyLege();
			}
			
		    else if (e.getSource() == kSlettLege)
		    {
		        slettLege();
		    }
			
		    else if (e.getSource() == kVisLege)
		    {
		      visLege();
		    }
			
		     else if (e.getSource() == kVisAlt)
		    {
		       visAlt();
		    }
                    else if (e.getSource() == kEndreLege)
                    {
                        endreLege();
                    }
		     else if ( e.getSource() == search ) {
		    	search();
		     }
		}

	}
	
	
	

}
