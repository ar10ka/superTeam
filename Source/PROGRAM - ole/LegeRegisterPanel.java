package Program;
import javax.swing.*;
import javax.swing.border.Border;
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
	private JPanel feltPanel;
	private JPanel listPanel;
	private JPanel knappePanel;
	
        
	
	private LegeRegister leger;
	private Lytter sensor;
	private Logg logg;
	private GridBagConstraints gbc;
	private FilBehandler fil;
	private static int feilLegeId = 0;
	//private String reseptGruppe;
	

	
	public LegeRegisterPanel() {
		
            logg = new Logg();
            leger = new LegeRegister();
            fil = new FilBehandler();
		
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
		
		
		legeID = feilLegeId;
		setLayout(new BorderLayout());
	    super.setBackground(Color.DARK_GRAY);
	    feltPanel = new JPanel();
	    listPanel = new JPanel();
	    knappePanel = new JPanel();
		legeIDFelt = new JTextField(11);
		navnFelt = new JTextField(10);
		etternavnFelt = new JTextField(10);
		arbeidsStedFelt = new JTextField(20);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.gridx = 0;
		ARadio = new JRadioButton("A");
		BRadio = new JRadioButton("B");
		CRadio = new JRadioButton("C");
		
        kEndreLege = new JButton("Endre Lege");
		kNyLege = new JButton("Reg Lege");
		kSlettLege = new JButton("Slett Lege");
		kVisLege = new JButton("Vis Lege");
		kVisAlt = new JButton("Vis Alt");
		search = new JButton("Søk Lege");
		
		
		tekstomraade  = new JTextArea(10,50);
	    tekstomraade.setEditable(false);
	    tekstomraade.setVisible(true);
	    JScrollPane rulle = new JScrollPane(tekstomraade);
	    
		logomraade  = new JTextArea(15, 30);
	    logomraade.setEditable(false);
	    logomraade.setVisible(true);
	    JScrollPane rulle2 = new JScrollPane(logomraade);
	    
	    Border rammer = BorderFactory.createEtchedBorder();
	    
	    feltPanel.setBorder(rammer);
	    knappePanel.setBorder(rammer);
	    listPanel.setBorder(rammer);
	    
	    
	    
        feltPanel.setLayout( new GridBagLayout() );
        gbc.gridy++;
        feltPanel.add(new JLabel("Velg Reseptgruppe(er)"),gbc);
	    feltPanel.add(ARadio,gbc);
	    feltPanel.add(BRadio,gbc);
	    feltPanel.add(CRadio,gbc);
	    gbc.gridy++;
	    feltPanel.add(new JLabel("Lege ID:"), gbc);
	    feltPanel.add(legeIDFelt, gbc);
	    gbc.gridy++;
	    feltPanel.add(new JLabel("Fornavn:"), gbc);
	    feltPanel.add(navnFelt,gbc);
	    gbc.gridy++;
	    feltPanel.add(new JLabel("Etternavn:"), gbc);
	    feltPanel.add(etternavnFelt, gbc);
	    gbc.gridy++;
	    feltPanel.add(new JLabel("ArbeidsSted:"), gbc);
	    feltPanel.add(arbeidsStedFelt, gbc);
	    gbc.gridy++;
	    //feltPanel.add(new JLabel("HER!!"),gbc);
	    feltPanel.add(tekstomraade,gbc);
	    gbc.gridx++;
        feltPanel.add(rulle,gbc);
        gbc.gridy++;
        feltPanel.add(kNyLege,gbc);
	   // add(rulle2);
	    
	    /*
	    feltPanel.add(kNyLege,gbc);
	    gbc.gridy++;
	    feltPanel.add(kSlettLege,gbc);
	    gbc.gridy++;
	    feltPanel.add(kVisLege, gbc);
	    gbc.gridy++;
	    feltPanel.add(kVisAlt, gbc);
	    gbc.gridy++;
	    feltPanel.add(kEndreLege,gbc);
	    gbc.gridy++;
        feltPanel.add(search, gbc);
        gbc.gridy++;
        */
	    feltPanel.setBackground(Color.green);
	    knappePanel.setBackground(Color.red);
	    listPanel.setBackground(Color.cyan);
	    JSplitPane spr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    sp.setResizeWeight(0.6);
	    sp.setEnabled(false);
	    sp.setDividerSize(0);
	    sp.add(listPanel);
	    sp.add(knappePanel);
	    spr.setEnabled(false);
	    spr.setDividerSize(0);
	    spr.setResizeWeight(0.6);
	    spr.add(sp);
	    spr.add(feltPanel);
        
	    add(spr, BorderLayout.CENTER);
		
		
        feltPanel.setVisible(true);
		
		sensor = new Lytter();
		
	    kNyLege.addActionListener(sensor);
            kEndreLege.addActionListener(sensor);
	    kSlettLege.addActionListener(sensor);

	    kVisLege.addActionListener(sensor);
	    kVisAlt.addActionListener(sensor);
	    search.addActionListener(sensor);
	   // logomraade.setText("");
	    		
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
	    {/*
	      FileInputStream fileHandle = new FileInputStream("LegeData.txt");
	      ObjectInputStream in = new ObjectInputStream(fileHandle);
	      leger = (LegeRegister) in.readObject();*/
	      leger = fil.lastInnFilLege("LegeLagring");
              System.out.println(logg.toString("LegeRegister lastet inn!"));
  
	    }
	    catch (FileNotFoundException ex)
	    {
	      System.out.println("Lager ny lagringsfil");
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
                fil.lagreFil(leger, "LegeLagring");
	      /*ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("LegeData.txt"));
	      out.writeObject(leger);*/
                System.out.println(logg.toString("LegeRegister lagret!"));
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
      
     String fnavn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
		String enavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
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
    catch (IndexOutOfBoundsException ex)
    {
        logomraade.setText("Fyll ut alle feltene!");
    }
			
		}
        
        public void endreLege()
        {
            try
                {
                 int id = Integer.parseInt(legeIDFelt.getText());
                 //Gjør at første bokstav i navnene blir stor og resten små bokstaver
                String fnavn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
                String enavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
                String arb = arbeidsStedFelt.getText();



              if (!fnavn.equals("") && !enavn.equals("") && !legeIDFelt.equals("") && !arb.equals("") )
              {
                if (leger.finnes(id))
                {          
                    Lege lege = new Lege(fnavn, enavn,arb, ActivRadio(), id);


                        if(leger.endreLege(lege))
                        {
                            legeIDFelt.setText(""+id);
                            logomraade.setText(logg.toString("Lege endret"));
                        }
                        else
                            logomraade.setText(logg.toString("Kunne ikke endre lege! Prøv igjen!"));


                }
                else
                {
                    logomraade.setText("Legen finnes ikke");

                    int n = JOptionPane.showConfirmDialog(null,
                            "Legen finnes ikke!\nVil du legge til legen?",
                            "Ny Lege!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {    
                        nyLege();
                        logomraade.setText(logg.toString("Lege lagt til"));
                    }
                    else 
                        logomraade.setText("Lege er ikke lagt til");

                }


              }
                else
                 logomraade.setText("Fyll ut alle feltene!");
            }
    catch (NumberFormatException e)
    {
      logomraade.setText("Fyll ut alle feltene!");
    }
    catch (IndexOutOfBoundsException ex)
    {
        logomraade.setText("Fyll ut alle feltene!");
    }
        }
        
        
	
	public void search() 
        {   
                if(!legeIDFelt.getText().equals(""))
                    legeID = Integer.parseInt(legeIDFelt.getText());
               
                if(legeID == feilLegeId)
                    logomraade.setText("Skriv inn legeID");
                
                else if(leger.finnes(legeID)) 
                {
			Lege l = leger.finn(legeID);
			legeIDFelt.setText(""+l.getlegeID());			
			navnFelt.setText(l.getNavn());
			etternavnFelt.setText(l.getEtternavn());
			arbeidsStedFelt.setText(l.getArbeidsSted());
                        
			for(char x : l.getReseptGruppe() )
                        {
				if(x == 'A') 
					ARadio.setSelected(true);		
				if(x == 'B') 
					BRadio.setSelected(true);
				if(x == 'C')
					CRadio.setSelected(true);
			}
                        logomraade.setText(logg.toString("Fant lege:\n" + l.toString()));
		}		
                else
                    logomraade.setText("Legen finnes ikke");
        }
	
	public void slettLege() {
		
                if(!legeIDFelt.getText().equals(""))
                    legeID = Integer.parseInt(legeIDFelt.getText());
                
		if(leger.slettLege(legeID)) {
			String utskrift = "Legen med legeID: " + legeID + " Er fjernet \n"; 
			logomraade.append(logg.toString(utskrift));
			
		}
		else
		logomraade.append(logg.toString("Finnes ikke legen med forekommende f�dselsnummeret \n"));
	}
	
	
	public void visLege() {
            
                //Gjør at første bokstav i navnene blir stor og resten små bokstaver
                String navn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
		String etternavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
		
                if(!legeIDFelt.getText().equals(""))
                {
                    legeID = Integer.parseInt(legeIDFelt.getText());
                        
                    if(leger.finnes(legeID)) 
                    {
                        Lege l = leger.finn(legeID);
                        tekstomraade.append(l.getNavn() + " " + l.getEtternavn() + " " +l.getlegeID() + "\n");
                        String utskrift = "Lege(er) med er funnet med forekommende lege id \n";
                        logomraade.append(logg.toString(utskrift));
                    }                    
                    else
                        logomraade.append(logg.toString("Feil legeID. Prøv igjen!"));
                }

                if(!navn.equals("") && !etternavn.equals(""))
                {
                    if(leger.finnes(navn, etternavn)) {
                        String temp ="";

                        for (Lege x: leger.finn(navn, etternavn))
                            temp += x.getInfo()+"\n";
                        
                        tekstomraade.append(logg.toString(temp) + "\n");
                        logomraade.append(logg.toString("Lege(er) med er funnet med forekommende navn og etternavn \n"));
                    }
                    else
                        logomraade.append(logg.toString("Feil navn. Prøv igjen!"));
		}
                
                if (navn.equals("") && etternavn.equals("") && legeIDFelt.getText().equals(""))
                    logomraade.append(logg.toString("Skriv inn enten legeID eller navn for å søke på legen!"));
                    
                
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
		    else if ( e.getSource() == search ) 
                    {
		    	search();
		    }
		}
	}
}
