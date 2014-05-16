


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



public class ReseptRegisterPanel extends panelSuper {
	
    	private JButton kVisInfo;//, kSlettResept;

        //Search
        private final JTextField searchdatofelt,searchidfelt,searchlegeENavnfelt,searchlegeFNavnfelt,
                                 searchlegeIdfelt,searchpasientENavnfelt,searchpasientFNavnfelt,
                                 searchpasientFnrfelt,searchmedisinNavnfelt,
                                 searchmedisinKategorifelt,searchmedisinIdfelt;
        
        //FELT
        private final JFormattedTextField medisinId;
        private JTextField legeId,pasientFnr,mengde,dato,reseptId;
        private final JTextArea legeanvfelt;
        private JScrollPane legeanv;
        
        //KNAPPEPANEL
        private JButton kVisPasient,kVisMedisin,kVisLege,kNyResept;//,kEndreResept;
        
        
        private Resept resept;
        
       
        //LIST
	private final JButton kRegResept, kSlettResept, kVisResept;
        private final JButton generer;
	JRadioButton 	searchRadioA, searchRadioB,searchRadioC,searchAny;
        ButtonGroup searchRadioGruppe;
              
        MaskFormatter medIDformatter;
	private final Lytter sensor;
	private final FilBehandler fil;
        
        public static final int _A = 1;
        public static final int _B  = 2;
        public static final int _C  = 3;
       
	
        //KONSTRUKTØR
	public ReseptRegisterPanel() {
            list = new JList();
            gbc = new GridBagConstraints();
            reseptRegister = new ReseptRegister();
            pasientRegister = new PasientRegister();
            medisinRegister = new MedisinRegister();
            legeRegister = new LegeRegister();
            fil = new FilBehandler();
	    
            try //LASTER INN LEGEREGISTERET
	    {
              this.medIDformatter = new MaskFormatter("U##U U##");
	      lastInnFil();
	    }
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    } catch (ParseException ex) {
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
               
                kVisPasient = new JButton("Vis Pasient");
                kVisMedisin = new JButton("Vis Medisin");
                kVisLege = new JButton("Vis Lege");
                //kEndreResept = new JButton("Endre Resept");
		kRegResept = new JButton("Reg Resept");


		searchRadioA = new JRadioButton("A");
                searchRadioB = new JRadioButton("B");
                searchRadioC = new JRadioButton("C");
                searchAny = new JRadioButton("ALT");
                
                
                            
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
                kVisInfo = new JButton("Vis Info");
                
                knappePanel.add(kNyResept);
               // knappePanel.add(kRegResept);
                knappePanel.add(kSlettResept);
                //knappePanel.add(kEndreResept);
                knappePanel.add(generer);
                knappePanel.add(kVisResept);
                
                addSearchPanel();
                addFeltPanel();
      
                list = new JList(reseptRegister.returnObjekt()); //data has type Object[]
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list.setLayoutOrientation(JList.VERTICAL);
                JScrollPane scrollpane = new JScrollPane(list);
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
                kRegResept.addActionListener(sensor);
//                kEndreResept.addActionListener(sensor);
                kSlettResept.addActionListener(sensor);
                kVisResept.addActionListener(sensor);
                kNyResept.addActionListener(sensor);
                generer.addActionListener(sensor);
                kVisMedisin.addActionListener(sensor);
                kVisPasient.addActionListener(sensor);
                kVisLege.addActionListener(sensor);

                kRegResept.setEnabled(false);
                
            //    kEndreResept.setEnabled(false);
                
                reseptId.setEditable(false);
                dato.setEditable(false);
                reseptId.setBackground(Color.white);
                dato.setBackground(Color.white);

                DocumentListener documentListener = new DocumentListener() {
                     public void changedUpdate(DocumentEvent documentEvent) {
                       findIt(documentEvent);
                     }
                     public void insertUpdate(DocumentEvent documentEvent) {
                      findIt(documentEvent);

                     }
                     public void removeUpdate(DocumentEvent documentEvent) {
                       findIt(documentEvent);
                     }
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

	public void error(String s)
        {
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        }
         public boolean feltFylt()
        {
            return      medisinId.getValue() != null
                    && !legeId.getText().equals("")
                    && !pasientFnr.getText().equals("")
                    && !legeanvfelt.getText().equals("")
                    && !mengde.getText().equals("");
        }
	
        public void regResept() 
        {
            try
             { 
                 if(feltFylt())
                {
                       int idLege = Integer.parseInt(legeId.getText().replaceAll("\\s",""));
                       String idPasient = pasientFnr.getText().replaceAll("\\s","");
                       String idMed = medisinId.getText().replaceAll("\\s","");
                       String anvisning = legeanvfelt.getText();
                       String medMengde = mengde.getText();
                       boolean b = false;
                       
                       Lege l;
                            if(legeRegister.finnes(idLege))
                                l= legeRegister.finn(idLege);
                            else
                            {
                                error("Legen er ikke registrert!\nLegen må være registrert for å kunne skrive ut resept");
                                return;
                            }
                       Pasient p;
                            if(pasientRegister.finnes(idPasient))
                                p= pasientRegister.finn(idPasient);
                            else
                            {
                                error("Pasienten finnes ikke!\nPasienten må registreres før du kan skrive ut resepten!");
                                return;
                            }
                        Medisin m;
                            if(medisinRegister.finnes(idMed))
                                m= medisinRegister.finn(idMed);
                            else
                            {
                                error("Medsinen finnes ikke");
                                return;
                            }

                        
                        for(char x : l.getReseptGruppe())
                        {
                            System.out.println(x);
                            System.out.println(m.getReseptGruppe());
                            
                            if(x==m.getReseptGruppe())
                                b=true;
                        }


                       if ( b )
                       { 
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
            catch (NumberFormatException e)
            {
                 error("Fyll ut alle feltene!");
                 kRegResept.setEnabled(true);
            }
            catch (IndexOutOfBoundsException ex)
            {
                 error("Fyll ut alle feltene!");
                 kRegResept.setEnabled(true);
            }
            
        }
        /*
        public void endreResept()
        {
            try
                {
                    String id = medisinId.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn
                    String navn = navnFelt.getText();
                    char rbStatus = aktivRadio();
                    String info = medInfo.getText();
                    //String k = medKategori.getValue().toString();JSPINNER
                    String k = medKategori.getText();

              if(feltFylt() )
              {
                  System.out.println(id);
                  
                  
                  System.out.println(reseptRegister.finn(id).getMedID());
                if (reseptRegister.finnes(id))
                {          
                    Resept resept = new Resept(id, navn, info, k, rbStatus);
                        if(reseptRegister.endre(resept))
                        {
                            reseptIDFelt.setText(""+id);
                            logomraade.append(logg.toString("Resept endret")+"\n");
                        }
                        else
                            logomraade.append(logg.toString("Kunne ikke endre resept! Prøv igjen!")+"\n");
                }
                else
                {
                    error("Reseptn finnes ikke");

                    int n = JOptionPane.showConfirmDialog(null,
                            "Resept finnes ikke!\nVil du legge til resept?",
                            "Ny Resept!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {    
                        regResept();
                        logomraade.append(logg.toString("Resept lagt til")+"\n");
                    }
                    else 
                        error("Resept er ikke lagt til");
                    emptyFields();
                }
              }
                else
                 error("Fyll ut alle feltene!");
            }
    catch ( NumberFormatException | IndexOutOfBoundsException e)
    {
      error("Fyll ut alle feltene!");
    }
        }*/
        //FERDIG
	private void visResept( Resept r ) 
        {
            if(getSelectedObject() != null)
            {       
                    resept = r;
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
        
	public void slettResept( Resept r) {
            
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne reseptn?",
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
        
	public void  emptyFields() {
            resept = null;
            medisinId.setValue(null);
            legeId.setText("");
            pasientFnr.setText("");
            mengde.setText("");
            dato.setText("");
            reseptId.setText("");
            legeanvfelt.setText("");
            searchRadioGruppe.clearSelection();
	}
        public void setStatus(Boolean b)
        {
            medisinId.setEnabled(b);
            legeId.setEnabled(b);
            pasientFnr.setEnabled(b);
            mengde.setEnabled(b);
            dato.setEnabled(b);
            reseptId.setEnabled(b);
            legeanvfelt.setEnabled(b);
            kRegResept.setEnabled(b);
            //kEndreResept.setEnabled(b);
        }
        
        public Resept getSelectedObject() {
		if(!list.isSelectionEmpty()) {
			Resept l = (Resept)list.getSelectedValue();
			
			return l;
			
		}
                else 
                    return null;
	}
        
        public void oppdaterListe() {
		
		list.setListData(reseptRegister.returnObjekt());
	}
 
        
        private void generateResepter()
        {
            int Min=0;
            int Max;
            int index;
            
            String[] legeanv = {"Ta to om dagen","Ta en om dagen","Ta så mange du vil","Ikke overdriv bruk av medisin",
                "Kast halvparten","Dette er ikke narkotika",
                "Bruk pinsett","Ikke kast denne","Ikke egnet for barn","Gratis medisin",
                "Handleliste:\n\nMelk, brød, egg, bacon, majones, reker, skinke, taco, sånn lilla såpe til madammen.",
                "Benyttes kun av verge","Bruk høvelen som følger med","Ikke overdrive inntaket",
                "Ikke si at du fikk denne av meg","Skal brukes varsomt","Smør på irriterte områder",
                "Løft opp benet og påfør under","Rist før bruk","IKKE rist før bruk"};
            String[] enhet = {"Mg","Enheter","Enhet"};
                
            String lanv;
            String mengde;
            String en;
            int antall;
            
            for(int i = 0; i < 1000; i++)
            {
                
                // LegeAnv
                Max=legeanv.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                lanv = legeanv[index];
                // Enhet
                Max=enhet.length-2;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                en = enhet[index];
                //Mengde
                Min = 0;
                Max=30;
                antall = Min + (int)(Math.random() * ((Max - Min) + 1));
                
                if(antall>1)
                    mengde = antall + "  " + en;
                else
                    mengde= antall + enhet[2];
                
                
                if(legeRegister.finnRandom() != null && pasientRegister.finnRandom()!=null&& medisinRegister.finnRandom()!=null)
                    reseptRegister.nyResept(legeRegister.finnRandom(), pasientRegister.finnRandom(), medisinRegister.finnRandom(),mengde,lanv);
            }
            
        }

	
	
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
                        emptyFields();
                        visResept(getSelectedObject());
		    }		
               
        	    else if (e.getSource() == kNyResept)
		    {
                        setStatus(true);                     
                        emptyFields();
     		    }/*
                            
                    else if (e.getSource() == kEndreResept)
                    {
                        endreResept();
                        emptyFields();
                    }*/
                    
                    else if (e.getSource() == kVisLege)
                     {
                         System.out.println(resept.toString());
                         if(resept != null)
                             visInfoVindu(resept.getLege());
                         else
                             error("Ingen resept er valgt");
                     }			

                     else if (e.getSource() == kVisMedisin)
                     {
                         if(resept!= null)
                            visInfoVindu(resept.getMedisin());
                         else
                             error("Ingen resept er valgt");
                     }			

                     else if (e.getSource() == kVisPasient)
                     {
                         if(resept!= null)
                            visInfoVindu(resept.getPasient());
                         else
                            error("Ingen resept er valgt");
                     }

		    else if ( e.getSource() == generer ) 
                    {
                        try {
                            lastInnFil();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
		    	generateResepter();
                        try {
                            lagreFil();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
		    }                    
                    oppdaterListe();
		}
	}
}
