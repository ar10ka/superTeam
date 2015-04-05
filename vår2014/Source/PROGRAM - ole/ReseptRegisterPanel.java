
/**
 *
 * @author Ole Bøe Andreassen - s188097
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;


//RESEPTREGISTERPANEL er en subklasse av panelSuper, som er en subklasse av JPanel
public class ReseptRegisterPanel extends panelSuper {
	

        //Search
        private final JTextField searchdatofelt,searchidfelt,searchlegeENavnfelt,searchlegeFNavnfelt,
                                 searchlegeIdfelt,searchpasientENavnfelt,searchpasientFNavnfelt,
                                 searchpasientFnrfelt,searchmedisinNavnfelt,
                                 searchmedisinKategorifelt,searchmedisinIdfelt;
        
        //FELT
        private final JFormattedTextField medisinId;
        private final JTextField legeId,pasientFnr,mengde,dato,reseptId;
        private final JTextArea legeanvfelt;
        private final JScrollPane legeanv;
        
        //KNAPPEPANEL
        private final JButton kVisPasient,kVisMedisin,kVisLege,kNyResept;
       
        //LIST
	private final JButton kRegResept, kSlettResept, kVisResept;
        private final JButton generer;
	JRadioButton 	searchRadioA, searchRadioB,searchRadioC,searchAny;
        ButtonGroup searchRadioGruppe;
              
        MaskFormatter medIDformatter;
	private final Lytter sensor;
        
        
        //KONSTRUKTØR
	public ReseptRegisterPanel() {
           
            gbc = new GridBagConstraints();
            reseptRegister = new ReseptRegister();
            pasientRegister = new PasientRegister();
            medisinRegister = new MedisinRegister();
            legeRegister = new LegeRegister();
            fil = new FilBehandler();
	    
            try //LASTER INN LEGEREGISTERET og setter format for input av medisinID
	    {
              this.medIDformatter = new MaskFormatter("U##U U##");
	      lastInnFil();
	    }
	    catch (IOException | ParseException ex)
	    {
	      ex.printStackTrace();
	    }
            
            
                setLayout(new BorderLayout());
                setBackground(Color.DARK_GRAY);

                //FELTPANEL
                reseptId = new JTextField(10);
                dato = new JTextField(10);
                mengde = new JTextField(10);
                legeanvfelt = new JTextArea(5,10);
                legeanv = new JScrollPane(legeanvfelt);
                medisinId = new JFormattedTextField(medIDformatter);
                medisinId.setColumns(14);
                legeId = new JTextField(10);
                pasientFnr = new JTextField(10);
               
                reseptId.setBorder(lineBorder);
                dato.setBorder(lineBorder);
                mengde.setBorder(lineBorder);
                legeanvfelt.setBorder(lineBorder);
                legeanv.setBorder(lineBorder);
                medisinId.setBorder(lineBorder);
                legeId.setBorder(lineBorder);
                pasientFnr.setBorder(lineBorder);
               
                //KNAPPER
                kVisPasient = new JButton("Vis Pasient");
                kVisMedisin = new JButton("Vis Medisin");
                kVisLege = new JButton("Vis Lege");
		kRegResept = new JButton("Reg Resept");


		searchRadioA = new JRadioButton("A");
                searchRadioB = new JRadioButton("B");
                searchRadioC = new JRadioButton("C");
                searchAny = new JRadioButton("ALT");
                
                
                kVisPasient.setBackground(greyWhite);
                kVisMedisin.setBackground(greyWhite);
                kVisLege.setBackground(greyWhite);
		kRegResept.setBackground(greyWhite);
		searchRadioA.setBackground(greyWhite);
                searchRadioB.setBackground(greyWhite);
                searchRadioC.setBackground(greyWhite);
                searchAny.setBackground(greyWhite);
                
                
                            
                //SEARCHPANEL
                searchdatofelt = new JTextField(10);
                searchidfelt = new JTextField(10);
                searchlegeENavnfelt = new JTextField(10);
                searchlegeFNavnfelt = new JTextField(10);
                searchlegeIdfelt = new JTextField(10);
                searchpasientENavnfelt = new JTextField(10);
                searchpasientFNavnfelt = new JTextField(10);
                searchpasientFnrfelt = new JTextField(10);
                searchmedisinNavnfelt = new JTextField(10);
                searchmedisinKategorifelt = new JTextField(10);
                searchmedisinIdfelt = new JTextField(10);
                
                searchdatofelt.setBorder(lineBorder);
                searchidfelt.setBorder(lineBorder);
                searchlegeENavnfelt.setBorder(lineBorder);
                searchlegeFNavnfelt.setBorder(lineBorder);
                searchlegeIdfelt.setBorder(lineBorder);
                searchpasientENavnfelt.setBorder(lineBorder);
                searchpasientFNavnfelt.setBorder(lineBorder);
                searchpasientFnrfelt.setBorder(lineBorder);
                searchmedisinNavnfelt.setBorder(lineBorder);
                searchmedisinKategorifelt.setBorder(lineBorder);
                searchmedisinIdfelt.setBorder(lineBorder);

                searchRadioGruppe = new ButtonGroup();
                searchRadioGruppe.add(searchRadioA);
                searchRadioGruppe.add(searchRadioB);
                searchRadioGruppe.add(searchRadioC);
                searchRadioGruppe.add(searchAny);
                searchRadioA.setBackground(blue);
                searchRadioB.setBackground(blue);
                searchRadioC.setBackground(blue);
                searchAny.setBackground(blue);

		kSlettResept = new JButton("Slett Resept");
		kVisResept = new JButton("Vis Resept");
		kNyResept = new JButton("Ny Resept");
		generer = new JButton("Generer Nye Resepter");
             
		kSlettResept.setBackground(greyWhite);
		kVisResept.setBackground(greyWhite);
		kNyResept.setBackground(greyWhite);
		generer.setBackground(greyWhite);
             
                
                knappePanel.add(kNyResept);
                knappePanel.add(kSlettResept);
                knappePanel.add(generer);
                knappePanel.add(kVisResept);
                
                //Metoder som setter layouten for hver sitt panel
                addSearchPanel();
                addFeltPanel();
                
                
                //Objektliste som skal inneholde alle reseptobjektene i reseptRegister
                list = new JList(reseptRegister.returnObjekt());
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
                kRegResept.addActionListener(sensor);
                kSlettResept.addActionListener(sensor);
                kVisResept.addActionListener(sensor);
                kNyResept.addActionListener(sensor);
                generer.addActionListener(sensor);
                kVisMedisin.addActionListener(sensor);
                kVisPasient.addActionListener(sensor);
                kVisLege.addActionListener(sensor);

                kRegResept.setEnabled(false);
                
                reseptId.setEditable(false);
                dato.setEditable(false);
                reseptId.setBackground(Color.white);
                dato.setBackground(Color.white);

                //Lytter til søkepanelets JTextFields
                documentListener = new DocumentListener() {
                     public void changedUpdate(DocumentEvent documentEvent) {
                       findIt(documentEvent);
                     }
                     public void insertUpdate(DocumentEvent documentEvent) {
                      findIt(documentEvent);

                     }
                     public void removeUpdate(DocumentEvent documentEvent) {
                       findIt(documentEvent);
                     }
                     //Søkemetoden for list. Oppdaterer og viser listen etter det du skriver i de forskjellige feltene
                     private void findIt(DocumentEvent documentEvent) {

                        String rId = searchidfelt.getText();
                        String rDato = searchdatofelt.getText();
                        char rGruppe  = searchaktivRadio();
                        String lId = searchlegeIdfelt.getText();
                        String lNavn = searchlegeFNavnfelt.getText();
                        String lEnavn = searchlegeENavnfelt.getText();
                        String pId = searchpasientFnrfelt.getText();
                        String pNavn = searchpasientFNavnfelt.getText();
                        String pEnavn = searchpasientENavnfelt.getText();
                        String mId = searchmedisinIdfelt.getText();
                        String mNavn = searchmedisinNavnfelt.getText();
                        String mKat = searchmedisinKategorifelt.getText();
                        

                         Document source = documentEvent.getDocument();
                         String[] emptyArray = {"Ingen resept som stemmer med søket  " + rId + " " + rDato + " " +  rGruppe 
                                                    + " " + lId + " " + lNavn + " " + lEnavn + " " + pId + " " 
                                                    + pNavn + " " + pEnavn + " " + mId + " " + mNavn + " " + mKat};
                         int length = source.getLength();
                         boolean b = true;

                           if(reseptRegister.finnObjekt(rId, rDato, rGruppe,lId,lNavn,lEnavn,pId,pNavn,pEnavn,mId,mNavn,mKat)!=null)
                           {                               
                               b = false;
                               list.setListData(reseptRegister.finnObjekt(rId, rDato, rGruppe,lId,lNavn,lEnavn,pId,pNavn,pEnavn,mId,mNavn,mKat));
                           }
   
                           if(b)
                               list.setListData(emptyArray);

                          if(length == 0)  
                               list.setListData(reseptRegister.returnObjekt());


                       }
                     

                   };
                
                setStatus(false);
                
                //Legger til lytterobjekt til søkefeltene
                searchdatofelt.getDocument().addDocumentListener(documentListener);	
                searchidfelt.getDocument().addDocumentListener(documentListener);	
                searchlegeENavnfelt.getDocument().addDocumentListener(documentListener);	
                searchlegeFNavnfelt.getDocument().addDocumentListener(documentListener);	
                searchlegeIdfelt.getDocument().addDocumentListener(documentListener);	
                searchpasientENavnfelt.getDocument().addDocumentListener(documentListener);	
                searchpasientFNavnfelt.getDocument().addDocumentListener(documentListener);	
                searchpasientFnrfelt.getDocument().addDocumentListener(documentListener);	
                searchmedisinNavnfelt.getDocument().addDocumentListener(documentListener);	
                searchmedisinKategorifelt.getDocument().addDocumentListener(documentListener);	
                searchmedisinIdfelt.getDocument().addDocumentListener(documentListener);		
                   }
            
        private void addSearchPanel()
        {  
                //Resept:id, dato, reseptgruppe
               //Lege: id, Navn,etternavn
               //Pasient:id, navn etternavn
               //Medisin:id, navn kategori
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;        
                 gbc.gridwidth=1;
                 gbc.weightx = 0.6;
                 gbc.weighty=0.6;
                 
                 gbc.gridx=0;
                 gbc.gridy=1;
                 
                 
                 searchPanel.add(new JLabel("ReseptID:"),gbc);
                 gbc.gridwidth=3;
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Utskrevet Dato:"),gbc);
                 gbc.gridwidth=1;
                 gbc.gridx= 0;
                 gbc.gridy=2;  
                 
                 searchPanel.add(searchidfelt,gbc);
                 gbc.gridx++;
                 gbc.gridwidth=3;
                 searchPanel.add(searchdatofelt,gbc);
                 
                 gbc.gridwidth=1;
                 gbc.gridx= 0;
                 gbc.gridy=3; 
                 
                 searchPanel.add(new JLabel("LegeID:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Etternavn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Fornavn:"),gbc);
                 
                 gbc.gridy=4;
                 gbc.gridx=0; 
                 
                 searchPanel.add(searchlegeIdfelt, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchlegeENavnfelt, gbc);
                 gbc.gridx++;
                 gbc.gridwidth = 2;
                 searchPanel.add(searchlegeFNavnfelt, gbc);
                 gbc.gridwidth = 1;
                 
                 gbc.gridx=0;
                 gbc.gridy = 5;
                   
                 searchPanel.add(new JLabel("Pasient Fnr:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Etternavn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Fornavn:"),gbc);
                 
                 gbc.gridx=0;
                 gbc.gridy=6;
                 
                 searchPanel.add(searchpasientFnrfelt, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchpasientENavnfelt, gbc);
                 gbc.gridx++;
                 gbc.gridwidth = 2;
                 searchPanel.add(searchpasientFNavnfelt, gbc);
                 gbc.gridwidth = 1;
                 
                 gbc.gridx=0;
                 gbc.gridy = 7;
                   
                 searchPanel.add(new JLabel("Medisin ID:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Navn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Kategori:"),gbc);
                 
                 gbc.gridx=0;
                 gbc.gridy=8;
                 
                 searchPanel.add(searchmedisinIdfelt, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchmedisinNavnfelt, gbc);
                 gbc.gridx++;
                 gbc.gridwidth = 2;
                 searchPanel.add(searchmedisinKategorifelt, gbc);
                 gbc.gridwidth = 1;
                 
                 gbc.gridx=0;
                 gbc.gridy = 0;
                 
                 
                 //Radiobutton
                 searchPanel.add(searchRadioA);
                 gbc.gridy = 3;
                 searchPanel.add(searchRadioB);
                 gbc.gridy = 4;
                 searchPanel.add(searchRadioC);
                 gbc.gridy = 5;
                 searchPanel.add(searchAny);
        }
        private void addFeltPanel()
        {   
            
            
            /*            
            
            DATO ------ REGID
            
                FELT REG
            
            Lege: legeid
            pasient: fnr
            medisin: medid
            Resept: mengde, legeAnvisning
            
            
            */
                feltPanel.setLayout( new GridBagLayout() );
                
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 gbc.insets = new Insets(5,15,5,15);
                 gbc.ipady = 20;
          
                 gbc.weightx = 0.5;
                 gbc.weighty=1;
                 gbc.gridy=0;
                 gbc.gridx=0;
                 
                 
                 feltPanel.add(new JLabel("RESEPT ID:"), gbc);
                 gbc.gridx++;
                 feltPanel.add(reseptId, gbc);
                 
                 gbc.gridx++;
                 feltPanel.add(new JLabel("UTSKREVET:"), gbc);
                 gbc.gridx++;
                 feltPanel.add(dato, gbc);
                 
                 
                 gbc.anchor = GridBagConstraints.SOUTH;
                 gbc.gridx=1;
                 gbc.gridy++;
                 feltPanel.add(new JLabel("ID felt:"), gbc);
                 
                 gbc.anchor = GridBagConstraints.NORTH;
                 
                 
                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Lege:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=2;
                 feltPanel.add(legeId,gbc);
                 gbc.gridwidth=1;
                 gbc.gridx+=2;
                 feltPanel.add(kVisLege,gbc);
                 
                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Pasient:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=2;
                 feltPanel.add(pasientFnr,gbc);
                 gbc.gridwidth=1;
                 gbc.gridx+=2;
                 feltPanel.add(kVisPasient,gbc);
                 
           

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Medisin:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=2;
                 feltPanel.add(medisinId,gbc);
                 gbc.gridwidth=1;
                 gbc.gridx+=2;
                 feltPanel.add(kVisMedisin,gbc);
             
               
                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("ANTALL/MENGDE"), gbc);
                 gbc.gridx++;
                 feltPanel.add(mengde,gbc);
      
                 
                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("LEGEANVISNING"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=3;
                 feltPanel.add(legeanv,gbc);
             	
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=5;
                 feltPanel.add(kRegResept,gbc);
                 
            feltPanel.setVisible(true);
      }
	
        //Metode som bruker FilBehandler-objekt for å laste innholdet av alle registrene fra fil
	  private void lastInnFil() throws IOException
	  {
	    try
	    {
	      reseptRegister = fil.lastInnFilResept("ReseptLagring");
	      pasientRegister = fil.lastInnFilPasient("PasientLagring");
	      legeRegister = fil.lastInnFilLege("LegeLagring");
	      medisinRegister = fil.lastInnFilMedisin("MedisinLagring");
              System.out.println(logg.toString("ReseptRegister lastet inn!"));
  
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
	//Metode som bruker FilBehnadler-objekt for å lagre innholdet i reseptRegisteret til fil
	  public void lagreFil() throws IOException
	  {
	    try
	    {
                fil.lagreFil(reseptRegister, "ReseptLagring");
                System.out.println(logg.toString("ReseptRegister lagret!"));
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }

          //Returnerer den radioknappen som er valgt, hvis ingen så returnerer den 0
        public char searchaktivRadio()
        {
            if(searchRadioA.isSelected())
              return 'A';
            if (searchRadioB.isSelected())
              return 'B';
            if (searchRadioC.isSelected())
              return 'C';

          return 0;
        }
        
        //Metode som returnerer true eller false dersom alle feltene er fylt eller ikke
         public boolean feltFylt()
        {
            return      medisinId.getValue() != null
                    && !legeId.getText().equals("")
                    && !pasientFnr.getText().equals("")
                    && !legeanvfelt.getText().equals("")
                    && !mengde.getText().equals("");
        }
	
         //Metode som registrerer ny resept
        public void regResept() 
        {
            try
             { 
                 //True hvis alle felt er fylt
                 if(feltFylt())
                {
                       int idLege = Integer.parseInt(legeId.getText().replaceAll("\\s",""));
                       String idPasient = pasientFnr.getText().replaceAll("\\s","");
                       String idMed = medisinId.getText().replaceAll("\\s","");
                       String anvisning = legeanvfelt.getText();
                       String medMengde = mengde.getText();
                       boolean b = false;
                       
                       //Sjekker om legen finnes
                       Lege l;
                            if(legeRegister.finnes(idLege))
                                l= legeRegister.finn(idLege);
                            else
                            {
                                error("Legen er ikke registrert!\nLegen må være registrert for å kunne skrive ut resept");
                                return;
                            }
                       //Sjekker om pasient finnes
                       Pasient p;
                            if(pasientRegister.finnes(idPasient))
                                p= pasientRegister.finn(idPasient);
                            else
                            {
                                error("Pasienten finnes ikke!\nPasienten må registreres før du kan skrive ut resepten!");
                                return;
                            }
                        //sjekker om medisin finnes
                        Medisin m;
                            if(medisinRegister.finnes(idMed))
                                m= medisinRegister.finn(idMed);
                            else
                            {
                                error("Medsinen finnes ikke");
                                return;
                            }

                        //Finner ut om legen har bevilling til å skrive ut medisinen utifra reseptgruppe 'A','B' eller 'C'
                        for(char x : l.getReseptGruppe())
                        {                            
                            if(x==m.getReseptGruppe())
                                b=true;                 //Returnerer true hvis legen er godkjent for å skrive ut resept på denne medisinen
                        }


                       if ( b )
                       { //Brukeren får mulighet til å angre seg dersom han vet han har skrevet noe feil
                           int n = JOptionPane.showConfirmDialog(null,
                            "Vil du legge til resepten?",
                            "Ny Resept!",
                            JOptionPane.YES_NO_OPTION);
                                    if(n == JOptionPane.YES_OPTION)
                                    {
                                        emptyFields();
                                        reseptRegister.nyResept(l,p,m,medMengde,anvisning);
                                        logomraade.append(logg.toString("Resept lagt til")+"\n");
                                        visResept(reseptRegister.getLatestAdded());
                                    }
                                    else
                                    {
                                        logomraade.append(logg.toString("Registrering av resept avbrutt av bruker!")+"\n");
                                    }
                        }
                         else
                                error(logg.toString("Legen er ikke godkjent for å skrive ut reseptgruppe '" + m.getReseptGruppe() + "' !"));
                       
                    }
                    else  
                    {
                           error("Fyll ut alle feltene!");
                           kRegResept.setEnabled(true);
                    }
            }
            catch (NumberFormatException | IndexOutOfBoundsException e)
            {
                 error("Fyll ut alle feltene!");
                 kRegResept.setEnabled(true);
            }
            
        }
        
        //Metode som tar imot Resept-objekt og fyller feltene i feltPanelet  med informasjon fra objektet
	 private void visResept( Resept r ) 
        {
            if(r != null)
            {
                    dato.setText(r.getDato());
                    reseptId.setText(r.getID()+"");
                    legeId.setText(r.getLege().getlegeID()+"");
                    pasientFnr.setText(r.getPasient().getFNr());
                    medisinId.setText(r.getMedisin().getMedID());
                    mengde.setText(r.getMengde()+"");      
                    legeanvfelt.setText(r.getLegeAnvisning());      

                    logomraade.append(logg.toString("Fant resept: " + r.toString())+"\n");
		}		
                else
                    error("Ingen resept er valgt");
        }
        
        //Metode som fjerner valgt resept
	public void slettResept( Resept r) {
            //Får mulighet til å angre seg
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne resepten?",
                            "Fjern Resept!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {
                        if(reseptRegister.slettResept(r)) {
                                String utskrift = "Resept " + r.getID()+ " " + r.getMedisin().getReseptGruppe()+" - "+ r.getDato()+ " er fjernet \n"; 
                                logomraade.append(logg.toString(utskrift));
                        }
                        else
                            error("Finner ikke resept \n");
                    }
                    else 
                        error("Resept er ikke fjernet ");
	}
        //Tømmer alle feltene i feltPanel
	public void  emptyFields() {
            medisinId.setValue(null);
            legeId.setText("");
            pasientFnr.setText("");
            mengde.setText("");
            dato.setText("");
            reseptId.setText("");
            legeanvfelt.setText("");
            searchRadioGruppe.clearSelection();
	}
        //Setter feltene til enabled eller disabled
        public void setStatus(Boolean b)
        {
            medisinId.setEditable(b);
            legeId.setEditable(b);
            pasientFnr.setEditable(b);
            mengde.setEditable(b);
            dato.setEditable(b);
            reseptId.setEditable(b);
            legeanvfelt.setEditable(b);
            kRegResept.setEnabled(b);
        }
        //metode som returnerer Resept-objekt som er markert i objektlisten
        public Resept getSelectedObject() {
		if(!list.isSelectionEmpty()) {
			Resept l = (Resept)list.getSelectedValue();
			
			return l;
			
		}
                else 
                    return null;
	}
        
        //Oppdaterer listen
        public void oppdaterListe() {
		
		list.setListData(reseptRegister.returnObjekt());
	}
 
        //Lager nye tilfeldige resepter med tilfeldige objekter av type lege,pasient og medisin
        private void generateResepter() throws IOException
        {
            //Laster inn registrene slik at metoden kan hente objekter fra dem
            lastInnFil();
            int Min=0;
            int Max;
            int index;
            
            String[] lstring = {"Ta to om dagen","Ta en om dagen","Ta så mange du vil","Ikke overdriv bruk av medisin",
                "Kast halvparten","Dette er ikke narkotika",
                "Bruk pinsett","Ikke kast denne","Ikke egnet for barn","Gratis medisin",
                "Handleliste:\n\nMelk, brød, egg, bacon, majones, reker, skinke, taco, sånn lilla såpe til madammen.",
                "Benyttes kun av verge","Bruk høvelen som følger med","Ikke overdrive inntaket",
                "Ikke si at du fikk denne av meg","Skal brukes varsomt","Smør på irriterte områder",
                "Løft opp benet og påfør under","Rist før bruk","IKKE rist før bruk"};
            String[] enhet = {"Mg","Enheter","Enhet"};
                
            String lanv;
            String m;
            String en;
            int antall;
            
            //lager 1000 nye forskjellige resepter
            for(int i = 0; i < 100; i++)
            {
                
                // LegeAnv
                Max=lstring.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                lanv = lstring[index];
                // Enhet
                Max=enhet.length-2;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                en = enhet[index];
                //Mengde
                Min = 0;
                Max=30;
                antall = Min + (int)(Math.random() * ((Max - Min) + 1));
                
                if(antall>1)
                    m = antall + "  " + en;
                else
                    m= antall + enhet[2];
                
                
                if(legeRegister.finnRandom() != null && pasientRegister.finnRandom()!=null&& medisinRegister.finnRandom()!=null)
                    reseptRegister.nyResept(legeRegister.finnRandom(), pasientRegister.finnRandom(), medisinRegister.finnRandom(),m,lanv);
                //lagrer reseptregisteret
                lagreFil();
            }
            
        }

	
	//Lytterklasse som bestemmer hva som skjer når man trykker på knappene
	private class Lytter implements ActionListener {
		
                
		@Override
		public void actionPerformed(ActionEvent e) {
                    
                    
                    setStatus(false);
			
                    if (e.getSource() == kRegResept)
                    {
                        regResept();
                    }			
		    else if (e.getSource() == kSlettResept)
		    {
                        slettResept(getSelectedObject());
		    }			
		    else if (e.getSource() == kVisResept)
		    {
                        kVisLege.setEnabled(true);
                        kVisMedisin.setEnabled(true);
                        kVisPasient.setEnabled(true);
                        emptyFields();
                        visResept(getSelectedObject());
		    }		
               
        	    else if (e.getSource() == kNyResept)
		    {
                        setStatus(true);                     
                        emptyFields();
     		    }
                    
                    else if (e.getSource() == kVisLege)
                     {
                         int i;
                         if(legeId.getText().matches("\\d+"))
                            i = Integer.parseInt(legeId.getText());
                         else 
                            i = 0;
                         if(i != 0)
                             visInfoVindu(legeRegister.finn(Integer.parseInt(legeId.getText())));
                         else
                             error("Ingen resept er valgt");
                     }			

                     else if (e.getSource() == kVisMedisin)
                     {
                         if(medisinRegister.finn(pasientFnr.getText()) != null)
                            visInfoVindu(medisinRegister.finn(pasientFnr.getText()));
                         else
                             error("Ingen resept er valgt");
                     }			

                     else if (e.getSource() == kVisPasient)
                     {
                         if(pasientRegister.finn(pasientFnr.getText())!= null)
                            visInfoVindu(pasientRegister.finn(pasientFnr.getText()));
                         else
                            error("Ingen resept er valgt");
                     }

		    else if ( e.getSource() == generer ) 
                    {
                        try {
                            generateResepter();
                        } catch (IOException ex) {
                            Logger.getLogger(ReseptRegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
               
		    }                    
                    oppdaterListe();
		}
	}
}
