
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


public class LegeRegisterVinduTest extends JFrame {
	
	private JTextField  fNrFelt, navnFelt, etternavnFelt, arbeidsStedFelt;
	private JButton kNyLege, kSlettLege, kVisAlt, kVisLege, search;
	private JRadioButton ARadio;
	private JRadioButton BRadio;
	private JRadioButton CRadio;
	private JTextArea tekstomraade;
	private JTextArea logomraade;
	private char [] reseptGruppe = new char [] {'A', 'B', 'C'};
	
	private LegeRegister leger;
	private Lytter sensor;
	private Logg logg;
	
	
	//private String reseptGruppe;
	

	
	public LegeRegisterVinduTest() {
		
		super("LegeRegister DEMO");
		
	    try
	    {
	    	System.out.println("INNN I TRY");
	      lastInnFil();
	      System.out.println("ETTER LAST FIL");
	    }

	    catch (IOException ex)
	    {
	    	System.out.println("CATCH PÅ KON");
	      ex.printStackTrace();
	    }
		
		
		
		fNrFelt = new JTextField(11);
		navnFelt = new JTextField(20);
		etternavnFelt = new JTextField(20);
		arbeidsStedFelt = new JTextField(20);
		
		ARadio = new JRadioButton("ReseptGruppe A");
		BRadio = new JRadioButton("ReseptGruppe B");
		CRadio = new JRadioButton("ReseptGruppe C");
		
		kNyLege = new JButton("Reg Lege");
		kSlettLege = new JButton("Slett Lege");
		kVisLege = new JButton("Vis Lege");
		kVisAlt = new JButton("Vis Alt");
		search = new JButton("Søk Lege");
		
		tekstomraade  = new JTextArea(15, 55);
	    tekstomraade.setEditable(false);
	    JScrollPane rulle = new JScrollPane(tekstomraade);
	    
		logomraade  = new JTextArea(15, 55);
	    logomraade.setEditable(false);
	    JScrollPane rulle2 = new JScrollPane(logomraade);
	    
		Container c   = getContentPane();
	    c.setLayout( new FlowLayout() );
	    c.add(ARadio);
	    c.add(BRadio);
	    c.add(CRadio);
	    c.add(new JLabel("FødselsNR"));
	    c.add(fNrFelt);
	    c.add(new JLabel("Fornavn:"));
	    c.add(navnFelt);
	    c.add(new JLabel("Etternavn:"));
	    c.add(etternavnFelt);
	    c.add(new JLabel("ArbeidsSted:"));
	    c.add(arbeidsStedFelt);
	    c.add(kNyLege);
	    c.add(kSlettLege);
	    c.add(kVisLege);
	    c.add(kVisAlt);
	    c.add(search);
	    c.add(rulle);
	    c.add(rulle2);
		
		
		leger = new LegeRegister();
		
		setSize(850, 400);
		setVisible(true);
		
		sensor = new Lytter();
		
	    kNyLege.addActionListener(sensor);

	    kSlettLege.addActionListener(sensor);

	    kVisLege.addActionListener(sensor);
	    kVisAlt.addActionListener(sensor);
	    search.addActionListener(sensor);
	    logomraade.setText("");
	    
	    addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	        	try
	        	{
	               lagreFil();
	               System.out.println("Lagret!");
	            }

	            catch (IOException ex)
	            {
	               ex.printStackTrace();
	            }
	            System.exit(0);
	         }
	     });
	   
		
		
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
		
		return reseptGruppe;
	}
	
	public boolean validerNr() {
		String fNr = fNrFelt.getText();
		char[] array = fNr.toCharArray();
		int antall = 0;
		for(char x: array){
			antall++;
		}
		if(fNr.matches("\\d+") && antall == 11)  {
			return true;
		}
		else 
			logomraade.append("Feil i fødselsnummeret" + "\n");
			return false;
	}
	
	
	public void nyLege() {
		String utskrift = "";
		String fNr = fNrFelt.getText();
		String navn = navnFelt.getText();
		String etternavn = etternavnFelt.getText();
		String sted = arbeidsStedFelt.getText();
		try {
			System.out.println("Inne i try");
			System.out.println("etter id");
			ActivRadio();
			System.out.println("etter active");
			
			if(leger.finnes(fNr)) {
				utskrift += ("Legen med fødselsnummeret [" + fNr + "] er allerede registrert fra før. \n");			
				logomraade.append(logg.toString(utskrift) + "\n");
				return;
			}
				
			
			if(validerNr() && !fNrFelt.getText().equals("")  && !navn.equals("") && !etternavn.equals("") && !sted.equals("")  && ActivRadio() != null ) {
				Lege ny = new Lege(fNr, navn, etternavn, sted, reseptGruppe);
				System.out.println("Inne i ifen");
				
				leger.settInn(ny);
				utskrift += (fNr + " " + navn + " " + etternavn + " er registrert i lege registert");
				
				logomraade.append(logg.toString(utskrift) + "\n");

				
			}
			else
				JOptionPane.showMessageDialog(null, "Legen er ikke registrert: sjekk logg!");
			} catch (NumberFormatException e)
			
			{
				
			}
				
			
		}
	
	public void search() {
		String fNr = fNrFelt.getText();
		String navn = navnFelt.getText();
		String etternavn = etternavnFelt.getText();
		char [] grupper;
		
		if(leger.finnes(fNr)) {				
			Lege l = leger.finn(fNr);						
			fNrFelt.setText(l.getFNr());			
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
		String fNr = fNrFelt.getText();
		if(leger.slettLege(fNr)) {
			String utskrift = "Legen med fødselsnummeret: " + fNr + " Er fjernet \n"; 
			logomraade.append(Logg.toString(utskrift));
			
		}
		else
		logomraade.append(Logg.toString("Finnes ikke legen med forekommende fødselsnummeret \n"));
	}
	
	
	public void visLege() {
		String fNr = fNrFelt.getText();
		String navn = navnFelt.getText();
		String etternavn = etternavnFelt.getText();
		
		if(leger.finn(fNr)!=null) {
			Lege l = leger.finn(fNr);
			tekstomraade.append(l.getNavn() + " " + l.getEtternavn() + " " +l.getFNr() + "\n");
			String utskrift = "Lege(er) med er funnet med forekommende fødselsnummeret \n";
			logomraade.append(Logg.toString(utskrift));
			
		}
		
		else if(leger.finnes(navn, etternavn)) {
			/*List<Lege> l = leger.finn(navn, etternavn); */
			//for( Lege x: l) {
			tekstomraade.append(leger.finnOgReturner(navn, etternavn) + "\n");
			//}
			String utskrift = "Lege(er) med er funnet med forekommende navn og etternavn \n";
			logomraade.append(Logg.toString(utskrift));
			
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
		     else if ( e.getSource() == search ) {
		    	 search();
		     }
		}

	}
	
	
	

}
