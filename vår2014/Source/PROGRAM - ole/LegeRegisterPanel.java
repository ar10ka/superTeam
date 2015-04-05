
/*

Studentnr: s198757
Navn: Marius Baltramaitis


Klasse: Dataingeniør

*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;



public class LegeRegisterPanel extends panelSuper {
	
	private final JTextField  searchAdr,searchFNavn,searchENavn,searchID;
        private final JTextField  legeIDFelt, navnFelt, etternavnFelt, arbeidsStedFelt;
	private final JButton kRegLege, kSlettLege, kVisLege,kEndreLege,kNyLege, kGenerer, kResept;

	private final JRadioButton ARadio, BRadio, CRadio;
	private char [] reseptGruppe = new char [] {'A', 'B', 'C'};


	private final Lytter sensor;


	
        //KONSTRUKTØR
	public LegeRegisterPanel() {
            
            legeRegister = new LegeRegister();
            fil = new FilBehandler();
	    
            try //LASTER INN LEGEREGISTERET
	    {
	      lastInnFil();
	    }
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    }
		
		setLayout(new BorderLayout());
                setBackground(Color.DARK_GRAY);
                
                
                //Initialiserer datafelter
                searchAdr = new JTextField(11);
                searchFNavn = new JTextField(11);
                searchENavn = new JTextField(11);
                searchID = new JTextField(11);
		legeIDFelt = new JTextField(11);
		navnFelt = new JTextField(10);
		etternavnFelt = new JTextField(10);
		arbeidsStedFelt = new JTextField(20);
		legeIDFelt.setEditable(false);
		
		searchAdr.setBorder(lineBorder);
		searchFNavn.setBorder(lineBorder);
		searchENavn.setBorder(lineBorder);
		searchID.setBorder(lineBorder);
                navnFelt.setBorder(lineBorder);
                legeIDFelt.setBorder(lineBorder);
		navnFelt.setBorder(lineBorder);
                etternavnFelt.setBorder(lineBorder);
                arbeidsStedFelt.setBorder(lineBorder);
                       

		ARadio = new JRadioButton("A");
		BRadio = new JRadioButton("B");
		CRadio = new JRadioButton("C");
 
                kEndreLege = new JButton("Endre Lege");
		kRegLege = new JButton("Reg Lege");
		kSlettLege = new JButton("Slett Lege");
		kVisLege = new JButton("Vis Lege");
		kNyLege = new JButton("Ny Lege");
		kGenerer = new JButton("Generer Nye Leger");
		kResept = new JButton("Vis Reseptene");
		
		kSlettLege.setBackground(greyWhite);
		kVisLege.setBackground(greyWhite);
		kNyLege.setBackground(greyWhite);
		kGenerer.setBackground(greyWhite);
		kEndreLege.setBackground(greyWhite);
		kRegLege.setBackground(greyWhite);
		kResept.setBackground(greyWhite);
                


                knappePanel.add(kNyLege);
                knappePanel.add(kRegLege);
                knappePanel.add(kSlettLege);
                knappePanel.add(kEndreLege);
                knappePanel.add(kGenerer);
                knappePanel.add(kVisLege);
                knappePanel.add(kResept);

                //Metoder som setter layouten for hver sitt panel
                addFeltPanel();
                addSearchPanel();
           
                //Objektliste som skal inneholde alle reseptobjektene i reseptRegister
                list = new JList(legeRegister.returnObjekt());
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list.setLayoutOrientation(JList.VERTICAL);
                JScrollPane scrollpane = new JScrollPane(list);
                listPanel.setLayout(new BorderLayout());
                listPanel.add(searchPanel, BorderLayout.PAGE_START);
                listPanel.add(scrollpane, BorderLayout.CENTER);
                listPanel.add(knappePanel, BorderLayout.PAGE_END);
                //Legger panelene i splitpanel fra superklassen.
                sptop.add(listPanel);
                sptop.add(feltPanel);
                spbottom.add(sptop);
                spbottom.add(loggPanel);
                //legger panelene i dette panelet
                add(spbottom, BorderLayout.CENTER);

                //Legger til lytterobjekt av klassen Lytter til hver knapp
                sensor = new Lytter();	
                kRegLege.addActionListener(sensor);
                kEndreLege.addActionListener(sensor);
                kSlettLege.addActionListener(sensor);
                kVisLege.addActionListener(sensor);
                kNyLege.addActionListener(sensor);
                kGenerer.addActionListener(sensor);
                kResept.addActionListener(sensor);


                kRegLege.setEnabled(false);
                kEndreLege.setEnabled(false);
                    
                //Lytter til søkepanelets JTextFields
                        documentListener = new DocumentListener() 
                        {
                            @Override
                            public void changedUpdate(DocumentEvent documentEvent) {
                                findIt(documentEvent);
                            }
                            @Override
                            public void insertUpdate(DocumentEvent documentEvent) {
                                findIt(documentEvent);

                            }
                            @Override
                            public void removeUpdate(DocumentEvent documentEvent) {
                                findIt(documentEvent);
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
	
        //Søkemetoden for list. Oppdaterer og viser listen etter det du skriver i de forskjellige feltene
        private void findIt(DocumentEvent documentEvent) {

                        String idfelt = searchID.getText();
                        String fnavn = searchFNavn.getText();
                        String enavn = searchENavn.getText();
                        String adr = searchAdr.getText();

                         Document source = documentEvent.getDocument();
                         String[] emptyArray = {"Ingen lege som stemmer med søket  " + fnavn + " " + enavn + " " + idfelt + " " + adr};
                         int length = source.getLength();
                        // boolean b = false;

                           if(legeRegister.finnObjekt(fnavn, enavn, adr,idfelt)!=null)
                           {
                             //  b = true;
                               list.setListData(legeRegister.finnObjekt(fnavn, enavn, adr,idfelt));
                           }
                          else
                               list.setListData(emptyArray);

                          if(length == 0)  
                           list.setListData(legeRegister.returnObjekt());


                       }
	
        //Metode som bruker FilBehandler-objekt for å laste innholdet av alle registrene fra fil
        private void lastInnFil() throws IOException
	  {
	    try
	    {
	      legeRegister = fil.lastInnFilLege("LegeLagring");
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
	
	//Metode som bruker FilBehnadler-objekt for å lagre innholdet i legeRegisteret til fil
        void lagreFil() throws IOException
	  {
	    try
	    {
                fil.lagreFil(legeRegister, "LegeLagring");
                System.out.println(logg.toString("LegeRegister lagret!"));
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
	
        //Returnerer den radioknappen som er valgt, hvis ingen så returnerer den 0
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
        
        //Metode som registrerer ny lege
        public void regLege() 
        {
            try
             {
                     String fnavn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
                     String enavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
                     String arb = arbeidsStedFelt.getText();




                        if (!fnavn.equals("") && !enavn.equals("")&& ActivRadio()!=null && !arb.equals("") )
                        {
                              legeRegister.settInn(fnavn, enavn,arb, reseptGruppe);
                              logomraade.append(logg.toString("Lege lagt til")+"\n");
                              
                        }
                        else
                        {
                           error("Fyll ut alle feltene!");
                           kRegLege.setEnabled(true);
                           legeIDFelt.setEditable(false);
                           legeIDFelt.setBackground(Color.LIGHT_GRAY);
              }
            }
            catch (NumberFormatException | IndexOutOfBoundsException e)
            {
                 error("Fyll ut alle feltene!");
                 kRegLege.setEnabled(true);
                 legeIDFelt.setEditable(false);
                 legeIDFelt.setBackground(Color.LIGHT_GRAY);
            }

        }
        //Metode som endrer valgte lege
        public void endreLege()
        {
            try
                {
               
                 int id = Integer.parseInt(legeIDFelt.getText());
                 //Gjør at første bokstav i navnene blir stor og resten små bokstaver
                String fnavn = Character.toUpperCase(navnFelt.getText().charAt(0)) + navnFelt.getText().substring(1).toLowerCase();
                String enavn = Character.toUpperCase(etternavnFelt.getText().charAt(0)) + etternavnFelt.getText().substring(1).toLowerCase();
                String arb = arbeidsStedFelt.getText();

      //        if(getSelectedObject() != null)
        //       legeID = getSelectedObject().getlegeID();
                
              if (!fnavn.equals("") && !enavn.equals("") && !legeIDFelt.equals("") && !arb.equals("") )
              {
                if (legeRegister.finnes(id))
                {          
                    Lege lege = new Lege(fnavn, enavn,arb, ActivRadio(), id);


                        if(legeRegister.endreLege(lege))
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
        
	//Metode som tar imot lege-objekt og fyller feltene i feltPanelet  med informasjon fra objektet
	public void visLege( Lege l ) 
        {
            if(l != null)
            {
                kEndreLege.setEnabled(true);
                legeIDFelt.setEditable(false);
                        
                
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
        
        //Metode som fjerner valgt lege
	public void slettLege( Lege l) {
            
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne legen?",
                            "Fjern Lege!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {  

                        if(legeRegister.slettLege(l.getlegeID())) {
                                String utskrift = "Legen " + l.getNavn() + " " + l.getEtternavn() +" "+ l.getlegeID() + " er fjernet\n"; 
                                logomraade.append(logg.toString(utskrift));

                        }
                        else
                            error("Finner ikke legen med forekommende fødselsnummeret \n");
                    }
                    else 
                        error("Lege er ikke fjernet til");
	}
        
        //Tømmer alle feltene i feltPanel
	public void  emptyFields() {

            legeIDFelt.setText("");
            navnFelt.setText("");
            etternavnFelt.setText("");
            arbeidsStedFelt.setText("");
            ARadio.setSelected(false);		
            BRadio.setSelected(false);
            CRadio.setSelected(false);
	}
        
        //metode som returnerer lege-objekt som er markert i objektlisten
        public Lege getSelectedObject() {
		
		if(!list.isSelectionEmpty()) {
			Lege l = (Lege)list.getSelectedValue();
			
			return l;
			
		}
                else 
                    return null;
	}
        
        //Oppdaterer listen
        public void oppdaterListe() 
        {
		list.setListData(legeRegister.returnObjekt());
	}
        
        //Genererer nye tilfeldige leger
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
                
                
                legeRegister.settInn(navn, enavn,arb, gruppe);
            }
        }
        
	//Lytterklasse som bestemmer hva som skjer når man trykker på knappene
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                    
                       kRegLege.setEnabled(false);
                       kEndreLege.setEnabled(false);
                       legeIDFelt.setEditable(true);
                       legeIDFelt.setBackground(Color.white);  
			
                    if (e.getSource() == kRegLege)
                    {
                        regLege();
                    }			
		    else if (e.getSource() == kSlettLege)
		    {
                        slettLege(getSelectedObject());
		    }			
		    else if (e.getSource() == kVisLege)
		    {
                      emptyFields();      
		      visLege(getSelectedObject());
                    }		
               
        	    else if (e.getSource() == kNyLege)
		    {
                        kRegLege.setEnabled(true);
                        legeIDFelt.setEditable(false);
                        legeIDFelt.setBackground(Color.LIGHT_GRAY);                        
                        emptyFields();
     		    }
                    else if (e.getSource() == kEndreLege)
                    {
                        endreLege();
                    }
                    
		    else if ( e.getSource() == kGenerer ) 
                    {
		    	generateLeger();
		    }
                    
		    else if( e.getSource() == kResept) {
                        visReseptInfoVindu(getSelectedObject());

		    }
                    oppdaterListe();
		}
	}
}
