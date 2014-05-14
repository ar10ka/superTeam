


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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;



public class PasientRegisterPanel extends panelSuper {
	
	private final JTextField  searchAdr,searchFNavn,searchENavn,searchID;
        private final JFormattedTextField fNr;
        private final JTextField  navnFelt, etternavnFelt, adresseFelt;
	private final JButton kRegPasient, kSlettPasient, kVisPasient,kEndrePasient,kNyPasient;
        private final JButton search;
	private final JRadioButton radioMann, radioKvinne;//, CRadio;
        private static final int _PERSON = 1;
        private static final int _FIRMA  = 2;        
        private MaskFormatter fNrformatter;
	//private char [] reseptGruppe = new char [] {'A', 'B', 'C'};

          private char gender;
	private PasientRegister bibliotek;
	private final Lytter sensor;
	private final FilBehandler fil;
        
//      private int legeID;
//	private static final int feilPasientId = 0;

	
        //KONSTRUKTØR
	public PasientRegisterPanel() {
            
           bibliotek  = new PasientRegister();
            fil = new FilBehandler();
	    
            try //LASTER INN PASIENTREGISTER OG SETTER FORMAT FOR FNR INPUT
	    {
	      lastInnFil();
              this.fNrformatter = new MaskFormatter("###### #####");
             } 
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    } catch (ParseException ex) {
                ex.printStackTrace(); 
            }
		
		
		//legeID = feilPasientId;
		setLayout(new BorderLayout());
                setBackground(Color.DARK_GRAY);
                
                searchAdr = new JTextField(11);
                searchFNavn = new JTextField(11);
                searchENavn = new JTextField(11);
                searchID = new JTextField(11);
	
		navnFelt = new JTextField(10);
		etternavnFelt = new JTextField(10);
		adresseFelt = new JTextField(20);
                
                fNr = new JFormattedTextField(fNrformatter);
                fNr.setColumns(14);
                       

                radioMann  = new JRadioButton("Mann");
                radioKvinne   = new JRadioButton("Kvinne");
                ButtonGroup radioGruppe = new ButtonGroup();
                radioGruppe.add(radioMann);
                radioGruppe.add(radioKvinne);

                kEndrePasient = new JButton("Endre Pasient");
		kRegPasient = new JButton("Reg Pasient");
		kSlettPasient = new JButton("Slett Pasient");
		kVisPasient = new JButton("Vis Pasient");
		kNyPasient = new JButton("Ny Pasient");
		search = new JButton("Generer Nye Pasienter");
                


                knappePanel.add(kNyPasient);
                knappePanel.add(kRegPasient);
                knappePanel.add(kSlettPasient);
                knappePanel.add(kEndrePasient);
                knappePanel.add(search);
                knappePanel.add(kVisPasient);

                addFeltPanel();
                addSearchPanel();
        
                list = new JList(bibliotek.returnObjekt()); //data has type Object[]
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
                kRegPasient.addActionListener(sensor);
                kEndrePasient.addActionListener(sensor);
                kSlettPasient.addActionListener(sensor);
                kVisPasient.addActionListener(sensor);
                kNyPasient.addActionListener(sensor);
                search.addActionListener(sensor);

                kRegPasient.setEnabled(false);
                kEndrePasient.setEnabled(false);

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

                        String idfelt = searchID.getText();
                        String fnavn = searchFNavn.getText();
                        String enavn = searchENavn.getText();
                        String adr = searchAdr.getText();

                         Document source = documentEvent.getDocument();
                         String[] emptyArray = {"Ingen pasient som stemmer med søket  " + fnavn + " " + enavn + " " + idfelt + " " + adr};
                         int length = source.getLength();
                         boolean b = false;

                           if(bibliotek.finnObjekt(fnavn, enavn, adr,idfelt)!=null)
                           {
                               b = true;
                               list.setListData(bibliotek.finnObjekt(fnavn, enavn, adr,idfelt));
                           }
                            /*
                           if(bibliotek.finnObjekt(idfelt)!=null)//idfelt.matches("-?\\d+(\\.\\d+)?") && 
                           {
                               b= true;
                               List<Pasient> l = new ArrayList<>();
                               l.add(bibliotek.finn(idfelt));
                               list.setListData(l.toArray());
                           }*/
                           if(!b)
                               list.setListData(emptyArray);

                          if(length == 0)  
                           list.setListData(bibliotek.returnObjekt());


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
                 searchPanel.add(new JLabel("Personnummer:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Adresse:"),gbc);
                 gbc.gridy=1;
                 gbc.gridx=0;     
                 searchPanel.add(searchENavn, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchFNavn, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchID, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchAdr, gbc);
        }
        
        /**
         * 
         */
        
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

                 feltPanel.add(new JLabel("Velg Kjønn"),gbc);


                 gbc.gridx=1;


                 feltPanel.add(radioMann,gbc);
                   gbc.gridx=2;

                 feltPanel.add(radioKvinne,gbc);
 


                 gbc.gridy++;
                 gbc.gridx=0;

                 feltPanel.add(new JLabel("Personnummer:"), gbc);

                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 feltPanel.add(fNr, gbc);
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
                 feltPanel.add(new JLabel("Adresse:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 feltPanel.add(adresseFelt, gbc);
                 gbc.gridwidth=1;
                 //gbc.gridy++;
                 gbc.gridx=0;
                 //feltPanel.add(new JLabel("KNAPPER"), gbc);

      
                 
                 gbc.gridy++;


                 gbc.gridheight = 1;
 
             gbc.gridy=12;
             
             gbc.ipady=1;
             
             
             
             gbc.gridx=3;
             feltPanel.add(kEndrePasient,gbc);
             gbc.gridx=4;
             feltPanel.add(kRegPasient,gbc);
             		
            feltPanel.setVisible(true);
      }
	
	
            private void lastInnFil() throws IOException
            {
              try
              {
                bibliotek = fil.lastInnFilPasient("PasientLagring");
                System.out.println(logg.toString("PasientRegister lastet inn!"));
              }
              catch (FileNotFoundException ex)
              {
                System.out.println(logg.toString("Lager ny lagringsfil"));
              }
              catch (EOFException ex)
              {
                System.out.println(logg.toString("Ferdig lastet lagringsfil!"));
              }
            }
	
            public void lagreFil() throws IOException
            {
              try
              {
                fil.lagreFil(bibliotek, "PasientLagring");
                System.out.println(logg.toString("PasientRegister lagret!"));
              }
              catch (FileNotFoundException ex)
              {
                ex.printStackTrace();
              }
            }

	
	
	
private char aktivRadio()
  {
    if(radioMann.isSelected())
      return 'M';
    else if (radioKvinne.isSelected())
      return 'F';

        error("Huk av for Mann eller Kvinne!");
    return 0;
  
  }

      
        private void error(String s)
        {
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
	
        private void regPasient() 
        {
            try
             {
                    String id = fNr.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn
                    String fnavn = navnFelt.getText();
                    String enavn = etternavnFelt.getText();
                    char gen = aktivRadio();
                    String adr = adresseFelt.getText();



             if (!fnavn.equals("") && !enavn.equals("") && !id.equals("") && gen!=0 && !adr.equals("") )            {
                 
                  if (!bibliotek.finnes(id))
                  { 
                    Pasient pasient = new Pasient(fnavn, enavn, id, gen,adr);
                    bibliotek.settInnNy(pasient);
                    logomraade.append(logg.toString("Pasient lagt til")+"\n");
                   }
                   else
                      error("Pasienten er allerede registrert");
              }
              else
              {
                 error("Fyll ut alle feltene!");
                 kRegPasient.setEnabled(true);
                 fNr.setEnabled(false);
                 fNr.setBackground(Color.LIGHT_GRAY);
              }
            }
            catch (NumberFormatException e)
            {
                 error("Fyll ut alle feltene!");
                 kRegPasient.setEnabled(true);
                 fNr.setEnabled(false);
                 fNr.setBackground(Color.LIGHT_GRAY);
            }
            catch (IndexOutOfBoundsException ex)
            {
                 error("Fyll ut alle feltene!");
                 kRegPasient.setEnabled(true);
                 fNr.setEnabled(false);
                 fNr.setBackground(Color.LIGHT_GRAY);
            }

        }
        
        private void endrePasient()
        {
            try
                {
               
                String id = fNr.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn
                String fnavn = navnFelt.getText();
                String enavn = etternavnFelt.getText();
                char gen = aktivRadio();
                String adr = adresseFelt.getText();

              if (!fnavn.equals("") && !enavn.equals("") && !id.equals("") && gen!=0 && !adr.equals("") )
              {
                if (bibliotek.finnes(id))
                {          
                    Pasient pasient = new Pasient(fnavn, enavn, id, gen,adr);


                        if(bibliotek.endre(pasient))
                        {
                            fNr.setText(id);
                            logomraade.append(logg.toString("Pasient endret")+"\n");
                        }
                        else
                            logomraade.append(logg.toString("Kunne ikke endre pasient! Prøv igjen!")+"\n");


                }
                else
                {
                    error("Pasienten finnes ikke");

                    int n = JOptionPane.showConfirmDialog(null,
                            "Pasienten finnes ikke!\nVil du legge til pasient?",
                            "Ny Pasient!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {    
                        regPasient();
                        logomraade.append(logg.toString("Pasient lagt til")+"\n");
                    }
                    else 
                        error("Pasient er ikke lagt til");

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
        
	private void visPasient( Pasient p ) 
        {
            if(getSelectedObject() != null)
            {
                kEndrePasient.setEnabled(true);
                fNr.setEnabled(false);
                        
                
			fNr.setText(""+p.getFNr());			
			navnFelt.setText(p.getFNavn());
			etternavnFelt.setText(p.getENavn());
			adresseFelt.setText(p.getAdresse());
                        char x = p.getGender();
			
				if(x == 'M') 
					radioMann.setSelected(true);		
				else
					radioKvinne.setSelected(true);

			
                        logomraade.append(logg.toString("Fant lege: " + p.toString())+"\n");
		}		
                else
                    error("Ingen lege er valgt");
        }
	private void slettPasient( Pasient p ) {
            
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne legen?",
                            "Fjern Pasient!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {  

                        if(bibliotek.fjern(p)) {
                                String utskrift = "Pasienten " + p.getFNavn() + " " + p.getENavn()+" "+ p.getFNr()+ " er fjernet \n"; 
                                logomraade.append(logg.toString(utskrift));

                        }
                        else
                            error("Finner ikke pasienten med forekommende fødselsnummeret \n");
                    }
                    else 
                        error("Pasient er ikke fjernet");
	}
        
	private void  emptyFields() {

            fNr.setText("");
            navnFelt.setText("");
            etternavnFelt.setText("");
            adresseFelt.setText("");
            radioMann.setSelected(false);		
            radioKvinne.setSelected(false);
	}
        
        private Pasient getSelectedObject() {
		
		if(!list.isSelectionEmpty()) {
			Pasient l = (Pasient)list.getSelectedValue();
			
			return l;
			
		}
                else 
                    return null;
	}
        
        private void oppdaterListe() {
		
		list.setListData(bibliotek.returnObjekt());
	}
        
        private void generatePasienter()
        {
            int Min=0;
            int Max;
            int index;
                    
            String[] gfornavn = {"Anders","Bernt","Jon","Karl","Jonas","Erik","Marius","Ola","Per","Benjamin","Preben","Karsten","Jørgen","Aksel","Mads","Andreas","Ole","Svein","Alex","Eirik"};
            String[] jfornavn = {"Anne","Kari","Gunn","Lise","Katrine","Mia","Maria","Sofie","Hanne","Linda","Ida","Triana","Pia","Frida","Arthika","Else","Hilde","Olga","Astrid","Beate","Alexandra"};
            String[] etternavn = {"Jensen","Hansen","Jarnæs","Karlsen","Langeli","Haaland","Bøe","Andreassen","Andersen","Skavlan","Ellingsen","Skjellvik","Vik","Teigland","Håvik","Lauritsen","Reksten","Holmelin","Tombra","Knudsen"};
            String[] adresse = {"Tønsberg","Oslo","Trondheim","Bergen","Kristiansand","Tromsø","Vikersund","Hamnes","Kristiansund","Fredrikstad","Hamar","Lillhammer","Halden","Moss","Sandefjord"};
            char[] gruppe = {'F','M'};
            int dag,mnd,year,fpnr,mpnr,epnr;
            int[]jente = {0,2,4,6,8}; 
            int[]gutt  = {1,3,5,7,9};
            String navn, enavn,arb,fnr;
            char gender;
            
            for(int i = 0; i < 1000; i++)
            {
                //FNR
                    Min=1;
                    Max=12;
                    mnd = Min + (int)(Math.random() * ((Max - Min) + 1));  
                    Min=1;
                    Max=28;
                    dag = Min + (int)(Math.random() * ((Max - Min) + 1));                        
                    Min=50;
                    Max=93;
                    year = Min + (int)(Math.random() * ((Max - Min) + 1));    
                    Min=10;
                    Max=99;
                    fpnr = Min + (int)(Math.random() * ((Max - Min) + 1));    
                    Min=10;
                    Max=99;
                    epnr = Min + (int)(Math.random() * ((Max - Min) + 1));    
                    
                    if(dag<10)
                        fnr = "0"+dag;
                    else
                        fnr = ""+dag;
                    if(mnd<10)
                        fnr += "0"+mnd;
                    else
                        fnr += mnd;
                    Min=0;
                
                //MANN ELLER KVINNE
                index = 0 + (int) (Math.random() * ((1-0)+1));
                if(index == 1)
                {//GUTT
                    gender = gruppe[1];
                    Max=gfornavn.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    navn = gfornavn[index];
                    Max=gutt.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    mpnr = gutt[index];
                }
                else//JENTE
                {
                    gender = gruppe[0];
                    Max=jfornavn.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    navn = jfornavn[index];
                    Max=jente.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    mpnr = jente[index];
                }
                fnr += ""+year+""+fpnr + mpnr + epnr;
                
                //EKSTRA ETTERNAVN?
                    index = 0 + (int) (Math.random() * ((1-0)+1));
                    if(index == 1)
                    {
                        Max=etternavn.length-1;
                        index = Min + (int)(Math.random() * ((Max - Min) + 1));
                        navn += " " + etternavn[index];
                    }
                       //ETTERNAVN OG ADRESSE
                    Max=etternavn.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    enavn = etternavn[index];
                    Max=adresse.length-1;
                    index = Min + (int)(Math.random() * ((Max - Min) + 1));
                    arb = adresse[index];
                Pasient pasient = new Pasient(navn, enavn, fnr, gender, arb);
                bibliotek.settInnNy(pasient); 
            }
            
        }

	
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                       
                       radioMann.setEnabled(true);
                       radioKvinne.setEnabled(true);
                       kRegPasient.setEnabled(false);
                       kEndrePasient.setEnabled(false);
                       fNr.setEnabled(true);
                       fNr.setBackground(Color.white);  
			
                    if (e.getSource() == kRegPasient)
                    {
                        regPasient();
                        emptyFields();
                    }			
		    else if (e.getSource() == kSlettPasient)
		    {
                        slettPasient(getSelectedObject());
		    }			
		    else if (e.getSource() == kVisPasient)
		    {
                      
                       radioMann.setEnabled(false);
                       radioKvinne.setEnabled(false); 
		      visPasient(getSelectedObject());
		    }		
               
        	    else if (e.getSource() == kNyPasient)
		    {
                        kRegPasient.setEnabled(true);
                        fNr.setEnabled(false);
                        fNr.setBackground(Color.LIGHT_GRAY);                        
                        emptyFields();
     		    }
                    else if (e.getSource() == kEndrePasient)
                    {
                        endrePasient();
                        emptyFields();
                    }
                    
		    else if ( e.getSource() == search ) 
                    {
		    	generatePasienter();
		    }                    
                    oppdaterListe();
		}
	}
}
