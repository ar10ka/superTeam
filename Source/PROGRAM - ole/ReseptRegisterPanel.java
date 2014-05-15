


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;



public class ReseptRegisterPanel extends panelSuper {
	
    	private JButton kVisInfo;//, kSlettResept;

        //Search
        private final JTextField searchdatofelt,searchidfelt,searchlegeENavnfelt,searchlegeFNavnfelt,searchlegeIdfelt,searchpasientENavnfelt,searchpasientFNavnfelt,searchpasientFnrfelt,searchmedisinNavnfelt,searchmedisinKategorifelt,searchmedisinIdfelt,mengdefelt;
        
        //FELT
        private final JTextArea legeanvfelt;
        private JButton kVisPasient,kVisMedisin,kVisLege,kEndreResept,kNyResept;
        private Resept resept;
        private JScrollPane legeanv;
        
        private final JTextField reseptIDFelt;
	
        //LIST
	private final JButton kRegResept, kSlettResept, kVisResept;
        private final JButton generer;
	JRadioButton 	searchRadioA, searchRadioB,searchRadioC,searchAny;

        ButtonGroup searchRadioGruppe;
        private JList list = new JList();
              
        MaskFormatter medIDformatter;
	private final Lytter sensor;
	private final FilBehandler fil;
        
        public static final int _A = 1;
        public static final int _B  = 2;
        public static final int _C  = 3;
        private String reseptID;

	
        //KONSTRUKTØR
	public ReseptRegisterPanel() {
            
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
		reseptIDFelt = new JTextField(10);
		
		reseptID = "";
		setLayout(new BorderLayout());
                setBackground(Color.DARK_GRAY);
                
		searchRadioA = new JRadioButton("A");
                searchRadioB = new JRadioButton("B");
                searchRadioC = new JRadioButton("C");
                searchAny = new JRadioButton("ALT");

                searchRadioGruppe = new ButtonGroup();
                searchRadioGruppe.add(searchRadioA);
                searchRadioGruppe.add(searchRadioB);
                searchRadioGruppe.add(searchRadioC);
                searchRadioGruppe.add(searchAny);
                searchRadioA.setBackground(blue);
                searchRadioB.setBackground(blue);
                searchRadioC.setBackground(blue);
                searchAny.setBackground(blue);

                kEndreResept = new JButton("Endre Resept");
		kRegResept = new JButton("Reg Resept");
		kSlettResept = new JButton("Slett Resept");
		kVisResept = new JButton("Vis Resept");
		kNyResept = new JButton("Ny Resept");
		generer = new JButton("Generer Nye Resepter");
                kVisInfo = new JButton("Vis Info");
;
            kVisPasient = new JButton("Vis Pasient");
            kVisMedisin = new JButton("Vis Medisin");
            kVisLege = new JButton("Vis Lege");
            
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
            
            mengdefelt = new JTextField(10);
            legeanvfelt = new JTextArea(5,10);
            legeanv = new JScrollPane(legeanvfelt);
                


                knappePanel.add(kNyResept);
               // knappePanel.add(kRegResept);
                knappePanel.add(kSlettResept);
                //knappePanel.add(kEndreResept);
                knappePanel.add(generer);
                knappePanel.add(kVisResept);

                addFeltPanel();
                addSearchPanel();
      
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
                kEndreResept.addActionListener(sensor);
                kSlettResept.addActionListener(sensor);
                kVisResept.addActionListener(sensor);
                kNyResept.addActionListener(sensor);
                generer.addActionListener(sensor);

                kRegResept.setEnabled(false);
                kEndreResept.setEnabled(false);

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
                               //Resept:id, dato, reseptgruppe
                               //Lege: id, Navn,etternavn
                               //Pasient:id, navn etternavn
                               //Medisin:id, navn kategori
                               
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
                FELT REG
            
            Lege: legeid
            pasient: fnr
            medisin: medid
            Resept: mengde, legeAnvisning
            
            
            *//*
                feltPanel.setLayout( new GridBagLayout() );
                
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 //gbc.insets = new Insets(5,10,5,15);
                 //gbc.ipady = 20;
            
                 gbc.weightx = 0.5;
                 gbc.weighty=1;
                 
                 gbc.gridy=0;
                 gbc.gridx=2;
                 
                 //feltPanel.add(new JLabel("RESEPT - INFORMASJON"),gbc);
                 
                 gbc.gridx=0;
                 gbc.gridy++;
                 
                 //SET FALSE NÅR NY RESEPT
                 feltPanel.add(new JLabel("Skrevet ut:"), gbc);

                 gbc.gridx++;
                 //gbc.gridwidth =1;
                 feltPanel.add(searchdatofelt, gbc);
                 //gbc.gridwidth=1;
                 gbc.gridx++;
                 feltPanel.add(new JLabel("Reseptnummer:"), gbc);
                 gbc.gridx++;
                 feltPanel.add(searchidfelt, gbc);
                 
                 
                 gbc.gridy++;
                 gbc.gridx=0;

                 feltPanel.add(new JLabel("Lege:"), gbc);
                 gbc.gridx++;
                 feltPanel.add(lENavn,gbc);
                 gbc.gridx++;
                 feltPanel.add(lFNavn,gbc);
                 gbc.gridx++;
                 feltPanel.add(lId,gbc);
                 gbc.gridx++;
                 feltPanel.add(kVisLege,gbc);
                

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Pasient:"), gbc);
                 gbc.gridx++;
                 feltPanel.add(pENavn,gbc);
                 gbc.gridx++;
                 feltPanel.add(pFNavn,gbc);
                 gbc.gridx++;
                 feltPanel.add(pFnr,gbc);
                 gbc.gridx++;
                 feltPanel.add(kVisPasient,gbc);
                 
           

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Medisin:"), gbc);
                 gbc.gridx++;
                 feltPanel.add(mNavn,gbc);
                 gbc.gridx++;
                 feltPanel.add(mRgruppe,gbc);
                 gbc.gridx++;
                 feltPanel.add(mId,gbc);
                 gbc.gridx++;
                 feltPanel.add(kVisMedisin,gbc);
             
               
                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("ANTALL/MENGDE"), gbc);
                 gbc.gridx++;
                 feltPanel.add(mengdefelt,gbc);
      
                 
                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("LEGEANVISNING"), gbc);
                 gbc.gridx++;
                 feltPanel.add(legeanv,gbc);
             		
            feltPanel.setVisible(true);*/
      }
	
	  private void lastInnFil() throws IOException
	  {
	    try
	    {
	      reseptRegister = fil.lastInnFilResept("ReseptLagring");
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
	
	  void lagreFil() throws IOException
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
        {/*
            return aktivRadio() != 0 
                    && !reseptIDFelt.getText().equals("")
                    && !navnFelt.getText().equals("")
                    && !medInfo.getText().equals("")
                    //&& !medKategori.getValue().toString().equals(""); Hvis JSpinner
                    && !medKategori.getText().equals("");*/
            return false;
        }
	
        public void regResept() 
        {/*
            try
             {
                    Lege l = reseptIDFelt.getText().replaceAll("\\s","");
                    String navn = navnFelt.getText();
                    char rbStatus = aktivRadio();
                    String info = medInfo.getText();
                    //String k = medKategori.getValue().toString(); Hvis JSpinner
                    String k = medKategori.getText();

                    if ( feltFylt() )
                    {
                    reseptRegister.nyResept(lege,pasient,medisin,mengde,legeanvisning);
                    logomraade.append(logg.toString("Resept lagt til")+"\n");
                    }
                    else  {
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
                                */
        }
        
        public void endreResept()
        {/*
            try
                {
               
                    
                    String id = reseptIDFelt.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn
          
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
    }*/
        }
        //FERDIG
	private void visResept( Resept r ) 
        {/*
            if(getSelectedObject() != null)
            {                
                    searchdatofelt.setText(r.getDato());
                    searchidfelt.setText(r.getID()+"");
                    lENavn.setText(r.getLege().getEtternavn());
                    lFNavn.setText(r.getLege().getNavn());
                    lId.setText(r.getLege().getlegeID()+"");
                    pENavn.setText(r.getPasient().getENavn());
                    pFNavn.setText(r.getPasient().getFNavn());
                    pFnr.setText(r.getPasient().getFNr());
                    mNavn.setText(r.getMedisin().getNavn());
                    mRgruppe.setText(r.getMedisin().getReseptGruppe()+"");
                    mId.setText(r.getMedisin().getMedID());
                    mengdefelt.setText(r.getMengde()+"");      
                    legeanv.setText(r.getLegeAnvisning());      

                    logomraade.append(logg.toString("Fant resept: " + r.toString()));
		}		
                else
                    error("Ingen resept er valgt");*/
        }
        
	public void slettResept( Resept l) {
            /*
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne reseptn?",
                            "Fjern Resept!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {  

                        if(reseptRegister.fjern(l)) {
                                String utskrift = "Reseptn " + l.getNavn() + " " + l.getMedID()+" - "+ l.getReseptGruppe()+ " er fjernet \n"; 
                                logomraade.append(logg.toString(utskrift));

                        }
                        else
                            error("Finner ikke resept \n");
                    }
                    else 
                        error("Resept er ikke fjernet ");*/
	}
        
	public void  emptyFields() {
/*
            reseptIDFelt.setText("");
            navnFelt.setText("");
            //medKategori.setValue("---");
            medKategori.setText("");
            medInfo.setText("");
            */
            searchRadioGruppe.clearSelection();
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
        {/*
            int Min=0;
            int Max;
            int index;
            
            String[] navn = {"Advate","Evista","Estradot","Eposin","Efedrin","Lamisil",
                "Lastin","Lutinus","Gemzar","Globoid","Opatanol","Oramorph","Qutenza","Preben",
                "Wilzin","Tondelis","Burinex","Benetor","Ultravist","Donepezil"};
            
            String info = "SKRIV INN INFO HER:\n";
            String id = "";
            String[] kat = getKategoriString();
            char[] gruppe = {'A','B','C'};
            char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
            
                
            String n,k;
                    char g;
            
            for(int i = 0; i < navn.length; i++)
            {
                
                // NAVN

                n = navn[i];
                //KATEGORI
                Min=1;
                Max=kat.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                k = kat[index];
                //GRUPPE
                Min = 0;
                Max=gruppe.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                g = gruppe[index];
                
                //ID
                String idstring = "";
                Max=alfabet.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                idstring += alfabet[index];
                
                idstring  += 0 + (int)(Math.random() * ((9 - 0) + 1));
                idstring  += 0 + (int)(Math.random() * ((9 - 0) + 1));
                
                
                Max=alfabet.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                idstring += alfabet[index];
                
                
                Max=alfabet.length-1;
                index = Min + (int)(Math.random() * ((Max - Min) + 1));
                idstring += alfabet[index];
                
                idstring  += 0 + (int)(Math.random() * ((9 - 0) + 1));
                idstring  += 0 + (int)(Math.random() * ((9 - 0) + 1));
                
                
                
                
                
                Resept resept = new Resept(idstring,n, info,k, g);
                reseptRegister.settInnNy(resept);
                
                
                
                
            }*/
            
        }

	
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                    
                       kRegResept.setEnabled(false);
                       kEndreResept.setEnabled(false);
                       
                      reseptIDFelt.setEnabled(true);
			
                    if (e.getSource() == kRegResept)
                    {
                        if (searchaktivRadio()!=0)
                            regResept();
                        else
                            error("Huk av reseptgruppe!");
                        emptyFields();
                    }			
		    else if (e.getSource() == kSlettResept)
		    {
                        slettResept(getSelectedObject());
		    }			
		    else if (e.getSource() == kVisResept)
		    {
                      reseptIDFelt.setEnabled(false);
		      visResept(getSelectedObject());
		    }		
               
        	    else if (e.getSource() == kNyResept)
		    {
                        kRegResept.setEnabled(true);                      
                        emptyFields();
     		    }
                    else if (e.getSource() == kEndreResept)
                    {
                        endreResept();
                        emptyFields();
                    }
                    
		    else if ( e.getSource() == generer ) 
                    {
		    	generateResepter();
		    }                    
                    oppdaterListe();
		}
	}
}
