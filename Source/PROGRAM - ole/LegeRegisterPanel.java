
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.Document;



public class LegeRegisterPanel extends panelSuper {
	
	private final JTextField  searchAdr,searchFNavn,searchENavn,searchID,legeIDFelt, navnFelt, etternavnFelt, arbeidsStedFelt;
	private final JButton kRegLege, kSlettLege, kVisLege,kEndreLege;
        private JButton kNyLege;
        private JButton search;
	private JRadioButton ARadio, BRadio, CRadio;
	//private JTextArea tekstomraade;

	private char [] reseptGruppe = new char [] {'A', 'B', 'C'};
	private int legeID;
        
        	private JList list = new JList();
	private JLabel legeListe, sLabel;
	private JList nyListe;
        private GridBagConstraints gbc ;

	
	private LegeRegister leger;
	private Lytter sensor;
	
	private FilBehandler fil;
	private static int feilLegeId = 0;
	//private String reseptGruppe;
	

	
	public LegeRegisterPanel() {
            
            
            gbc = new GridBagConstraints();
            leger = new LegeRegister();
            fil = new FilBehandler();
            
            sLabel = new JLabel("Søk Her: ");
		
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
                setBackground(Color.DARK_GRAY);
                
                searchAdr = new JTextField(11);
                searchFNavn = new JTextField(11);
                searchENavn = new JTextField(11);
                searchID = new JTextField(11);
		legeIDFelt = new JTextField(11);
		navnFelt = new JTextField(10);
		etternavnFelt = new JTextField(10);
		arbeidsStedFelt = new JTextField(20);
		legeIDFelt.setEditable(false);
                       

		ARadio = new JRadioButton("A");
		BRadio = new JRadioButton("B");
		CRadio = new JRadioButton("C");
 
                kEndreLege = new JButton("Endre Lege");
		kRegLege = new JButton("Reg Lege");
		kSlettLege = new JButton("Slett Lege");
		kVisLege = new JButton("Vis Lege");
		kNyLege = new JButton("Ny Lege");
		search = new JButton("Generer Nye Leger");
                


       knappePanel.add(kNyLege);
        knappePanel.add(kRegLege);
        knappePanel.add(kSlettLege);
        knappePanel.add(kEndreLege);
        knappePanel.add(search);
       knappePanel.add(kVisLege);
        
        addFeltPanel();
addSearchPanel();
        
	    knappePanel.setBackground(Color.red);
	    listPanel.setBackground(Color.cyan);
            
            list = new JList(leger.returnObjekt()); //data has type Object[]
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
	    //list.setVisibleRowCount(5);
	    JScrollPane scrollpane = new JScrollPane(list);
	    //listPanel.add(new JScrollPane(list));
	    //list.add(new JScrollPane());
	    //listPanel.add(list);
	    
	    listPanel.setLayout(new BorderLayout());

            listPanel.add(searchPanel, BorderLayout.PAGE_START);
	    listPanel.add(scrollpane, BorderLayout.CENTER);
            listPanel.add(knappePanel, BorderLayout.PAGE_END);
	    sptop.add(listPanel);
	    sptop.add(feltPanel);

            
            spbottom.add(sptop);
            spbottom.add(loggPanel);
        
	    add(spbottom, BorderLayout.CENTER);
	

		sensor = new Lytter();
		
	    kRegLege.addActionListener(sensor);
            kEndreLege.addActionListener(sensor);
	    kSlettLege.addActionListener(sensor);
	    kVisLege.addActionListener(sensor);
	    kNyLege.addActionListener(sensor);
	    search.addActionListener(sensor);
            
            kRegLege.setEnabled(false);
            kEndreLege.setEnabled(false);
                
 DocumentListener documentListener = new DocumentListener() {
      public void changedUpdate(DocumentEvent documentEvent) {
        printIt(documentEvent);
      }
      public void insertUpdate(DocumentEvent documentEvent) {
       printIt(documentEvent);
       
      }
      public void removeUpdate(DocumentEvent documentEvent) {
        printIt(documentEvent);
      }
      private void printIt(DocumentEvent documentEvent) {

         String idfelt = searchID.getText();
         String fnavn = searchFNavn.getText();
         String enavn = searchENavn.getText();
         String adr = searchAdr.getText();
         
          Document source = documentEvent.getDocument();
          String[] emptyArray = {"Ingen lege som stemmer med søket  " + fnavn + " " + enavn + " " + idfelt + " " + adr};
          int length = source.getLength();
          boolean b = false;
           
            if(leger.finnObjekt(fnavn, enavn, adr)!=null)
            {
                b = true;
                list.setListData(leger.finnObjekt(fnavn, enavn, adr));
            }
   
            if(idfelt.matches("-?\\d+(\\.\\d+)?") && leger.finn(Integer.parseInt(idfelt))!=null)
            {
                b= true;
                List<Lege> l = new ArrayList<>();
                l.add(leger.finn(Integer.parseInt(idfelt)));
                list.setListData(l.toArray());
            }
            if(!b)
                list.setListData(emptyArray);
         
           if(length == 0)  
            list.setListData(leger.returnObjekt());
    
                  
        }
     
      
    };
        searchAdr.getDocument().addDocumentListener(documentListener);
        searchFNavn.getDocument().addDocumentListener(documentListener);
        searchENavn.getDocument().addDocumentListener(documentListener);
        searchID.getDocument().addDocumentListener(documentListener);		
	}
            
        private void addSearchPanel()
        {  
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;        
                 gbc.gridwidth=1;
                 gbc.weightx = 1;
                 gbc.weighty=1;
                 gbc.gridx=0;
                 gbc.gridy=0;      
                 searchPanel.add(new JLabel("Etternavn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Fornavn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Arbeidsplass:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("LegeID:"),gbc);
                 gbc.gridy=1;
                 gbc.gridx=0;     
                 searchPanel.add(searchENavn, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchFNavn, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchAdr, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchID, gbc);
        }
        
        private void addFeltPanel()
        {
           
	
            
                 feltPanel.setLayout( new GridBagLayout() );
                
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 gbc.insets = new Insets(5,10,5,15);
                 gbc.ipady = 20;
            
                 gbc.weightx = 0.5;
                 gbc.weighty=1;
                 gbc.gridx=0;
                 gbc.gridy=0;

                 feltPanel.add(new JLabel("Velg Reseptgruppe(er)"),gbc);


                 gbc.gridx=1;


                 feltPanel.add(ARadio,gbc);
                   gbc.gridx=2;

                 feltPanel.add(BRadio,gbc);
                    gbc.gridx=3;
                 feltPanel.add(CRadio,gbc);


                 gbc.gridy++;
                 gbc.gridx=0;

                 feltPanel.add(new JLabel("Lege ID:"), gbc);

                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 feltPanel.add(legeIDFelt, gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;

                 feltPanel.add(new JLabel("Fornavn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 feltPanel.add(navnFelt,gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Etternavn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;

                 feltPanel.add(etternavnFelt, gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("ArbeidsSted:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 feltPanel.add(arbeidsStedFelt, gbc);
                 gbc.gridwidth=1;
                 //gbc.gridy++;
                 gbc.gridx=0;
                 //feltPanel.add(new JLabel("KNAPPER"), gbc);

      
                 
                 gbc.gridy++;


                 gbc.gridheight = 1;
 
             gbc.gridy=12;
             
             gbc.ipady=1;
             
             
             
             gbc.gridx=3;
             feltPanel.add(kEndreLege,gbc);
             gbc.gridx=4;
             feltPanel.add(kRegLege,gbc);
             		
            feltPanel.setVisible(true);
      }
	
	
	  private void lastInnFil() throws IOException
	  {
	    try
	    {
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
                System.out.println(logg.toString("LegeRegister lagret!"));
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
	
	
	
	
	public char[]  ActivRadio() {				
		if(ARadio.isSelected() && !BRadio.isSelected() && !CRadio.isSelected()) 
			reseptGruppe = new char [] {'A'};
		if(!ARadio.isSelected() && BRadio.isSelected() && !CRadio.isSelected()) 
			reseptGruppe = new char [] {'B'};
		if(!ARadio.isSelected() && !BRadio.isSelected() && CRadio.isSelected()) 
			reseptGruppe = new char [] {'C'};
		 if(ARadio.isSelected() && BRadio.isSelected() && !CRadio.isSelected())
			reseptGruppe = new char [] {'A', 'B'};
		if(ARadio.isSelected() && !BRadio.isSelected() && CRadio.isSelected()) 
			reseptGruppe = new char [] {'A', 'C'};
		if(!ARadio.isSelected() && BRadio.isSelected() && CRadio.isSelected())
			reseptGruppe = new char [] {'B', 'C'};		
		if(ARadio.isSelected() && BRadio.isSelected() && CRadio.isSelected()) 
			reseptGruppe = new char [] {'A', 'B', 'C'};		
                if(!ARadio.isSelected() && !BRadio.isSelected() && !CRadio.isSelected())
                    reseptGruppe = null;
		
		return reseptGruppe;
	}

	public void error(String s)
        {
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        }
	
        public void regLege() 
        {
            
            try
             {

                     String fnavn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
                     String enavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
                     String arb = arbeidsStedFelt.getText();




              if (!fnavn.equals("") && !enavn.equals("")&& ActivRadio()!=null && !arb.equals("") )
              {
                     Lege lege = new Lege(fnavn, enavn,arb, reseptGruppe, 0);
                    leger.settInn(lege);
                    logomraade.append(logg.toString("Lege lagt til")+"\n");



              }
                    if (fnavn.equals("") || enavn.equals("")|| ActivRadio()==null || arb.equals("") )
              {
                 error("Fyll ut alle feltene!");
              }
            }
            catch (NumberFormatException e)
            {
              error("Fyll ut alle feltene!");
            }
            catch (IndexOutOfBoundsException ex)
            {
                error("Fyll ut alle feltene!");
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

              if(getSelectedObject() != null)
               legeID = getSelectedObject().getlegeID();
                
              else if (!fnavn.equals("") && !enavn.equals("") && !legeIDFelt.equals("") && !arb.equals("") )
              {
                if (leger.finnes(id))
                {          
                    Lege lege = new Lege(fnavn, enavn,arb, ActivRadio(), id);


                        if(leger.endreLege(lege))
                        {
                            legeIDFelt.setText(""+id);
                            logomraade.append(logg.toString("Lege endret")+"\n");
                        }
                        else
                            logomraade.append(logg.toString("Kunne ikke endre lege! Prøv igjen!")+"\n");


                }
                else
                {
                    error("Legen finnes ikke");

                    int n = JOptionPane.showConfirmDialog(null,
                            "Legen finnes ikke!\nVil du legge til legen?",
                            "Ny Lege!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {    
                        regLege();
                        logomraade.append(logg.toString("Lege lagt til")+"\n");
                    }
                    else 
                        error("Lege er ikke lagt til");

                }


              }
                else
                 error("Fyll ut alle feltene!");
            }
    catch (NumberFormatException e)
    {
      error("Fyll ut alle feltene!");
    }
    catch (IndexOutOfBoundsException ex)
    {
        error("Fyll ut alle feltene!");
    }
        }
        
        
	
	public void visLege( Lege l ) 
        {
          /*  if(getSelectedObject() != null)
               legeID = getSelectedObject().getlegeID();
            else if(!legeIDFelt.getText().equals(""))
                legeID = Integer.parseInt(legeIDFelt.getText());
               
                if(legeID == feilLegeId)
                    error("Skriv inn legeID");
                
                else if(leger.finnes(legeID)) 
                { */
          
     
			//Lege l = leger.finn(legeID);
            if(getSelectedObject() != null)
            {
                kEndreLege.setEnabled(true);
                legeIDFelt.setEnabled(false);
                        
                
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
                        logomraade.append(logg.toString("Fant lege: " + l.toString()));
		}		
                else
                    error("Ingen lege er valgt");
        }
	
	public void slettLege( Lege l) {
            
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne legen?",
                            "Fjern Lege!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {  

                        if(leger.slettLege(l.getlegeID())) {
                                String utskrift = "Legen " + l.getNavn() + " " + l.getEtternavn() +" "+ l.getlegeID() + " er fjernet \n"; 
                                logomraade.append(logg.toString(utskrift));

                        }
                        else
                            error("Finner ikke legen med forekommende fødselsnummeret \n");
                    }
                    else 
                        error("Lege er ikke fjernet til");
	}
	
	
	public void finn() {
            /*
                //Gjør at første bokstav i navnene blir stor og resten små bokstaver
                String navn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
		String etternavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
		
               if(getSelectedObject() != null)
               legeID = getSelectedObject().getlegeID();
            else if(!legeIDFelt.getText().equals(""))
                legeID = Integer.parseInt(legeIDFelt.getText());
               
                if(legeID == feilLegeId)
                    error("Skriv inn legeID");
                
                else if(leger.finnes(legeID)) 
                {  /*              
                if(!legeIDFelt.getText().equals(""))
                {
                    legeID = Integer.parseInt(legeIDFelt.getText());
                 */       
                   /*
                        Lege l = leger.finn(legeID);
                        visLege(l);
/*                     tekstomraade.append(l.getNavn() + " " + l.getEtternavn() + " " +l.getlegeID() + "\n");
                        String utskrift = "Lege(er) med er funnet med forekommende lege id: "+ legeID +" \n";
                        logomraade.append(logg.toString(utskrift)+"\n\n");*/
                 /*   
                }
                else
                        error(logg.toString("Feil legeID. Prøv igjen!"));

                if(!navn.equals("") && !etternavn.equals(""))
                {
                    if(leger.finnes(navn, etternavn)) {
                        String temp ="";

                        for (Lege x: leger.finn(navn, etternavn))
                            temp += x.getInfo()+"\n";
                        
                       // tekstomraade.append(logg.toString(temp) + "\n");
                        logomraade.append(logg.toString("Lege(er) med er funnet med forekommende navn og etternavn \n"));
                    }
                    else
                        error("Feil navn. Prøv igjen!");
		}
                
                if (navn.equals("") && etternavn.equals("") && legeIDFelt.getText().equals(""))
                    error(logg.toString("Skriv inn enten legeID eller navn for å søke på legen!"));
                    */
                
	}
        
	public void  emptyFields() {

            legeIDFelt.setText("");
            navnFelt.setText("");
            etternavnFelt.setText("");
            arbeidsStedFelt.setText("");
            ARadio.setSelected(false);		
            BRadio.setSelected(false);
            CRadio.setSelected(false);
	}
        
        public Lege getSelectedObject() {
		
		if(!list.isSelectionEmpty()) {
			Lege l = (Lege)list.getSelectedValue();
			
			return l;
			
		}
                else 
                    return null;
	}
        public void oppdaterListe() {
		
		list.setListData(leger.returnObjekt());
	}
        
        private void generateLeger()
        {
            int Min=0;
            int Max;
            int index;
                    
            String[] fornavn = {"Anders","Bernt","Jon","Karl","Jonas","Erik","Marius","Anne","Kari","Gunn","Ola","Per","Benjamin","Preben","Karsten","Lise","Katrine","Mia","Maria","Sofie"};
            String[] etternavn = {"Jensen","Hansen","Jarnæs","Karlsen","Langeli","Haaland","Bøe","Andreassen","Andersen","Skavlan","Ellingsen","Skjellvik","Vik","Teigland","Håvik","Lauritsen","Reksten","Holmelin","Tombra","Knudsen"};
            String[] arbeid = {"Tønsberg","Oslo","Trondheim","Bergen","Kristiansand","Tromsø","Vikersund"};
            char[] gruppe = {'A','B','C'};
            
            String navn, enavn,arb;
            
            
            for(int i = 0; i < 1000; i++)
            {
                Max=fornavn.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                navn = fornavn[index];
                
                index = 0 + (int) (Math.random() * ((1-0)+1));
                if(index == 1)
                {
                    Max=etternavn.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    navn += " " + etternavn[index];
                }
                
                Max=etternavn.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                enavn = etternavn[index];
                Max=arbeid.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                arb = arbeid[index];
                
                
                Lege lege = new Lege(navn, enavn,arb, gruppe, 0);
                leger.settInn(lege);
                
                
                
                
            }
            
        }

	
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                    
                       kRegLege.setEnabled(false);
                       kEndreLege.setEnabled(false);
                       legeIDFelt.setEnabled(true);
                       legeIDFelt.setBackground(Color.white);  
			
                    if (e.getSource() == kRegLege)
                    {
                        regLege();
                        emptyFields();
                    }			
		    else if (e.getSource() == kSlettLege)
		    {
                        slettLege(getSelectedObject());
		    }			
		    else if (e.getSource() == kVisLege)
		    {
                       
		      visLege(getSelectedObject());
		    }		
               
        	    else if (e.getSource() == kNyLege)
		    {
                        generateLeger();
                        kRegLege.setEnabled(true);
                        legeIDFelt.setEnabled(false);
                        legeIDFelt.setBackground(Color.LIGHT_GRAY);                        
                        emptyFields();
     		    }
                    else if (e.getSource() == kEndreLege)
                    {
                        endreLege();
                        emptyFields();
                    }
                    
		    else if ( e.getSource() == search ) 
                    {
		    	generateLeger();
		    }                    
                    oppdaterListe();
		}
	}
}
