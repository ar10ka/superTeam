
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

//MEDISINREGISTERPANEL er en subklasse av panelSuper, som er en subklasse av JPanel
public class MedisinRegisterPanel extends panelSuper {
	
        private final JFormattedTextField medisinIDFelt;
	private final JTextField  searchNavn,searchKategori,searchID;
        private final JTextField  navnFelt, medKategori;
        private final JTextArea medInfo;
	private final JButton kRegMedisin, kSlettMedisin, kVisMedisin,kEndreMedisin,kNyMedisin;
        private final JButton search;
	private final JRadioButton 	radioA, radioB,radioC;
	private final JRadioButton 	searchRadioA, searchRadioB,searchRadioC,searchAny;
        //JSpinner medKategori; // HVIS MAN ØNSKER Å BENYTTE SEG AV EN JSPINNER ISTEDETFOR JTEXTFIELD
        JScrollPane inforull;
        private final  ButtonGroup searchRadioGruppe,radioGruppe;
        private MaskFormatter medIDformatter;
	private final Lytter sensor;
	
        //KONSTRUKTØR
	public MedisinRegisterPanel() {
            
            gbc = new GridBagConstraints();
            medisinRegister = new MedisinRegister();
            fil = new FilBehandler();
	    
            try //LASTER INN MEDISINREGISTERET og setter format for input av medisinID
	    {
              medIDformatter = new MaskFormatter("U##U U##");
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
                
                searchNavn = new JTextField(11);
                searchKategori = new JTextField(11);
                searchID = new JTextField(11);
		
		navnFelt = new JTextField(10);
		medKategori = new JTextField(10);
                medInfo    = new JTextArea(5,30);
                medInfo.setEditable(true);
                inforull = new JScrollPane(medInfo);
                medisinIDFelt = new JFormattedTextField(medIDformatter);
                medisinIDFelt.setColumns(14);
                
		radioA = new JRadioButton("A");
                radioB = new JRadioButton("B");
                radioC = new JRadioButton("C");

                radioGruppe = new ButtonGroup();
                radioGruppe.add(radioA);
                radioGruppe.add(radioB);
                radioGruppe.add(radioC);
                
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
 /*               
                HVIS MAN ØNSKER RULLEVINDU
                
                 // KATEGORI RULLEVINDU
                SpinnerListModel listModel = new SpinnerListModel(getKategori());
                medKategori    = new JSpinner(listModel);
                Dimension d = medKategori.getPreferredSize();
                d.width = 100;
                medKategori.setPreferredSize(d);
                medKategori.setValue( getKategori().get(getKategori().size()-1));
*/
                kEndreMedisin = new JButton("Endre Medisin");
		kRegMedisin = new JButton("Reg Medisin");
		kSlettMedisin = new JButton("Slett Medisin");
		kVisMedisin = new JButton("Vis Medisin");
		kNyMedisin = new JButton("Ny Medisin");
		search = new JButton("Generer Nye Medisiner");
                


                knappePanel.add(kNyMedisin);
                knappePanel.add(kRegMedisin);
                knappePanel.add(kSlettMedisin);
                knappePanel.add(kEndreMedisin);
                knappePanel.add(search);
                knappePanel.add(kVisMedisin);

                //Metoder som setter layouten for hver sitt panel
                addFeltPanel();
                addSearchPanel();
      
                //Objektliste som skal inneholde alle reseptobjektene i reseptRegister
                list = new JList(medisinRegister.returnObjekt()); //data has type Object[]
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
                kRegMedisin.addActionListener(sensor);
                kEndreMedisin.addActionListener(sensor);
                kSlettMedisin.addActionListener(sensor);
                kVisMedisin.addActionListener(sensor);
                kNyMedisin.addActionListener(sensor);
                search.addActionListener(sensor);

                kRegMedisin.setEnabled(false);
                kEndreMedisin.setEnabled(false);

               
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
                    
                    String idfelt = searchID.getText();
                    String navn = searchNavn.getText();
                    String kat = searchKategori.getText();
                    char cha = searchaktivRadio();
                    
                    Document source = documentEvent.getDocument();
                    String[] emptyArray = {"Ingen medisin som stemmer med søket  " + navn + " " + idfelt + " " + cha + " " + kat};
                    int length = source.getLength();
                    boolean b = true;
                    
                    if(medisinRegister.finnObjekt(navn, idfelt, cha, kat)!=null)
                    {
                        b = false;
                        list.setListData(medisinRegister.finnObjekt(navn, idfelt, cha, kat));
                    }
                    if(b)
                        list.setListData(emptyArray);
                    
                    if(length == 0)
                        list.setListData(medisinRegister.returnObjekt());
                    
                    
                }
                
                
            };
                       searchNavn.getDocument().addDocumentListener(documentListener);
                       searchKategori.getDocument().addDocumentListener(documentListener);
                       searchID.getDocument().addDocumentListener(documentListener);		
                   }
        
        private void addSearchPanel()
        {  
            
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;        
                 gbc.gridwidth=1;
                 gbc.weightx = 0.6;
                 gbc.weighty=0.6;
                 gbc.gridx=0;
                 gbc.gridy = 0;
                 searchPanel.add(searchRadioA);                 
                 gbc.gridy=1;      
                 searchPanel.add(new JLabel("Navn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("ID:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Kategori:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(searchRadioB);
                 gbc.gridy=2;
                 gbc.gridx=0;     
                 searchPanel.add(searchNavn, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchID, gbc);
                 gbc.gridx++;
                 gbc.gridwidth = 2;
                 searchPanel.add(searchKategori, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchRadioC);
                 gbc.gridy=3;
                 searchPanel.add(searchAny);
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

                 feltPanel.add(new JLabel("Velg Reseptgruppe"),gbc);


                 gbc.gridx=1;


                 feltPanel.add(radioA,gbc);
                   gbc.gridx=2;

                 feltPanel.add(radioB,gbc);
                    gbc.gridx=3;
                 feltPanel.add(radioC,gbc);


                 gbc.gridy++;
                 gbc.gridx=0;

                 feltPanel.add(new JLabel("Medisin ID:"), gbc);

                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 feltPanel.add(medisinIDFelt, gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;

                 feltPanel.add(new JLabel("Navn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 feltPanel.add(navnFelt,gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Kategori:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;

                 feltPanel.add(medKategori, gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 feltPanel.add(new JLabel("Informasjon:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 feltPanel.add(inforull, gbc);
                 gbc.gridwidth=1;
                 //gbc.gridy++;
                 gbc.gridx=0;
                 //feltPanel.add(new JLabel("KNAPPER"), gbc);

      
                 
                 gbc.gridy++;


                 gbc.gridheight = 1;
 
             gbc.gridy=12;
             
             gbc.ipady=1;
             
             
             
             gbc.gridx=3;
             feltPanel.add(kEndreMedisin,gbc);
             gbc.gridx=4;
             feltPanel.add(kRegMedisin,gbc);
             		
            feltPanel.setVisible(true);
      }
	
	
        //Metode som bruker FilBehandler-objekt for å laste innholdet fra fil til registeret 
	  private void lastInnFil() throws IOException
	  {
	    try
	    {
	      medisinRegister = fil.lastInnFilMedisin("MedisinLagring");
              System.out.println(logg.toString("MedisinRegister lastet inn!"));
  
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
	
	//Metode som bruker FilBehnadler-objekt for å lagre innholdet i medisinRegisteret til fil
	  void lagreFil() throws IOException
	  {
	    try
	    {
                fil.lagreFil(medisinRegister, "MedisinLagring");
                System.out.println(logg.toString("MedisinRegister lagret!"));
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
          
          //Returnerer den radioknappen som er valgt, hvis ingen så returnerer den 0
            public char aktivRadio()
              {
                if(radioA.isSelected())
                  return 'A';
                if (radioB.isSelected())
                  return 'B';
                 if (radioC.isSelected())
                  return 'C';

             return 0;
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
                return aktivRadio() != 0 
                        && !medisinIDFelt.getText().equals("")
                        && !navnFelt.getText().equals("")
                        && !medInfo.getText().equals("")
                        //&& !medKategori.getValue().toString().equals(""); Hvis JSpinner
                        && !medKategori.getText().equals("");
            }

         //Metode som registrerer ny medisin
            public void regMedisin() 
            {
                try
                 {
                        String id = medisinIDFelt.getText().replaceAll("\\s","");
                        String navn = navnFelt.getText();
                        char rbStatus = aktivRadio();
                        String info = medInfo.getText();
                        //String k = medKategori.getValue().toString(); Hvis JSpinner
                        String k = medKategori.getText();
                        
                        //True hvis alle felt er fylt
                        if ( feltFylt() )
                        {
                                Medisin medisin = new Medisin(id, navn, info, k, rbStatus);
                                medisinRegister.settInnNy(medisin);
                                logomraade.append(logg.toString("Medisin lagt til")+"\n");
                                visMedisin(medisin);
                        }
                        else  {
                            error("Fyll ut alle feltene!");
                            kRegMedisin.setEnabled(true);
                        }
                }
                catch (NumberFormatException | IndexOutOfBoundsException e)
                {
                     error("Fyll ut alle feltene!");
                     kRegMedisin.setEnabled(true);
                }

            }
            //Metode som endrer den valgte medisin-objektet
            public void endreMedisin()
            {
                try
                    {


                        String id = medisinIDFelt.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn

                        String navn = navnFelt.getText();
                        char rbStatus = aktivRadio();
                        String info = medInfo.getText();
                        //String k = medKategori.getValue().toString();JSPINNER
                        String k = medKategori.getText();

                  if(feltFylt() )
                  {
                      System.out.println(id);


                      System.out.println(medisinRegister.finn(id).getMedID());
                    if (medisinRegister.finnes(id))
                    {          
                        Medisin medisin = new Medisin(id, navn, info, k, rbStatus);
                            if(medisinRegister.endre(medisin))
                            {
                                medisinIDFelt.setText(""+id);
                                logomraade.append(logg.toString("Medisin endret")+"\n");
                            }
                            else
                                logomraade.append(logg.toString("Kunne ikke endre medisin! Prøv igjen!")+"\n");
                    }
                    else
                    {//Får anledning til å lage nytt objekt dersom den ikke finnes fra før av
                        error("Medisinn finnes ikke");

                        int n = JOptionPane.showConfirmDialog(null,
                                "Medisin finnes ikke!\nVil du legge til medisin?",
                                "Ny Medisin!",
                                JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.YES_OPTION)
                        {    
                            regMedisin();
                            logomraade.append(logg.toString("Medisin lagt til")+"\n");
                        }
                        else 
                            error("Medisin er ikke lagt til");
                        emptyFields();
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

             //Metode som tar imot medisin-objekt og fyller feltene i feltPanelet  med informasjon fra objektet
            public void visMedisin( Medisin m ) 
            {
                if(m != null)
                {
                    kEndreMedisin.setEnabled(true);
                    medisinIDFelt.setEditable(false);


                            medisinIDFelt.setText(m.getMedID());			
                            navnFelt.setText(m.getNavn());
                            //medKategori.setValue(l.getKategori());JSPINNER
                            medKategori.setText(m.getKategori());
                            medInfo.setText(m.getInformasjon());
                            char x = m.getReseptGruppe();

                                    if(x == 'A') 
                                            radioA.setSelected(true);		
                                    if(x == 'B') 
                                            radioB.setSelected(true);
                                    if(x == 'C')
                                            radioC.setSelected(true);

                            logomraade.append(logg.toString("Fant medisin: " + m.toString())+"\n");
                    }		
                    else
                        error("Ingen medisin er valgt");
            }
            
        //Metode som fjerner valgt medisin
            public void slettMedisin( Medisin l) {

                 int n = JOptionPane.showConfirmDialog(null,
                                "Vil du fjerne medisinn?",
                                "Fjern Medisin!",
                                JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.YES_OPTION)
                        {  

                            if(medisinRegister.fjern(l)) {
                                    String utskrift = "Medisinn " + l.getNavn() + " " + l.getMedID()+" - "+ l.getReseptGruppe()+ " er fjernet \n"; 
                                    logomraade.append(logg.toString(utskrift));

                            }
                            else
                                error("Finner ikke medisin \n");
                        }
                        else 
                            error("Medisin er ikke fjernet ");
            }

            public void  emptyFields() {

            medisinIDFelt.setText("");
            navnFelt.setText("");
            //medKategori.setValue("---");
            medKategori.setText("");
            medInfo.setText("");
            
            radioGruppe.clearSelection();
	}

            public Medisin getSelectedObject() {

                    if(!list.isSelectionEmpty()) {
                            Medisin l = (Medisin)list.getSelectedValue();

                            return l;

                    }
                    else 
                        return null;
            }

            public void oppdaterListe() {
		
		list.setListData(medisinRegister.returnObjekt());
	}
        /*  
            BRUKES HVIS JSPINNER BRUKES
          public static List<String> getKategori()
          {

             List<String> k = Arrays.asList(getKategoriString());

             Collections.sort(k); 
             Collections.reverse(k);

             //k.add(" ");
             return k;
          }*/
            //Returnerer en array av gitte kategorier på medisiner. Her kan man legge inn kategorier
            public static String[] getKategoriString(){
       String[] arr = 
   { "---",
       "Hypnotikum","Antibiotika","Sedativum",
       "Hemodialysekonsentrat","Glaukommiddel",
       "Adrenergikum","Progestogen"
   
   };
    
    return arr;
}
            //Lager nye tilfeldige medisiner i registeret
        private void generateMedisiner()
         {
            int Min;
            int Max;
            int index;
            
            String[] navn = {"Advate","Evista","Estradot","Eposin","Efedrin","Lamisil",
                "Lastin","Lutinus","Gemzar","Globoid","Opatanol","Oramorph","Qutenza","Preben",
                "Wilzin","Tondelis","Burinex","Benetor","Ultravist","Donepezil"};
            
            String info = "SKRIV INN INFO HER:\n";
            String[] kat = getKategoriString();
            char[] gruppe = {'A','B','C'};
            char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
            
                
            String n,k;
                    char g;
            for (String navnet : navn) {
                // NAVN
                n = navnet;
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
                Medisin medisin = new Medisin(idstring,n, info,k, g);
                medisinRegister.settInnNy(medisin);
            }
            
        }
	
            private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                    
                       kRegMedisin.setEnabled(false);
                       kEndreMedisin.setEnabled(false);
                       
                      medisinIDFelt.setEditable(true);
			
                    if (e.getSource() == kRegMedisin)
                    {
                        if (aktivRadio()!=0)
                            regMedisin();
                        else
                            error("Husk av reseptgruppe!");
                        
                    }			
		    else if (e.getSource() == kSlettMedisin)
		    {
                        slettMedisin(getSelectedObject());
		    }			
		    else if (e.getSource() == kVisMedisin)
		    {
                      emptyFields();
                      medisinIDFelt.setEditable(false);
		      visMedisin(getSelectedObject());
		    }		
               
        	    else if (e.getSource() == kNyMedisin)
		    {
                        radioA.setSelected(true);
                        kRegMedisin.setEnabled(true);                      
                        emptyFields();
     		    }
                    else if (e.getSource() == kEndreMedisin)
                    {
                        endreMedisin();
                    }
                    
		    else if ( e.getSource() == search ) 
                    {
		    	generateMedisiner();
		    }                    
                    oppdaterListe();
		}
	}
}
