


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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;



public class ReseptRegisterPanel extends panelSuper {
	
        
	private final JTextField  searchNavn,searchKategori,searchID;
        private final JTextField  navnFelt, medKategori,reseptIDFelt;
        private JTextArea medInfo;
	private final JButton kRegResept, kSlettResept, kVisResept,kEndreResept,kNyResept;
        private final JButton search;
	JRadioButton 	radioA, radioB,radioC;
	JRadioButton 	searchRadioA, searchRadioB,searchRadioC,searchAny;
        //JSpinner medKategori;
        ButtonGroup searchRadioGruppe,radioGruppe;
        private JList list = new JList();
        
        MaskFormatter medIDformatter;
	private ReseptRegister bibliotek;
	private final Lytter sensor;
	private final FilBehandler fil;
        
        public static final int _A = 1;
        public static final int _B  = 2;
        public static final int _C  = 3;
        private String reseptID;

	
        //KONSTRUKTØR
	public ReseptRegisterPanel() {
            
            gbc = new GridBagConstraints();
            bibliotek = new ReseptRegister();
            fil = new FilBehandler();
	    
            try //LASTER INN LEGEREGISTERET
	    {
	      lastInnFil();
	    }
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    } 
		
		
		reseptID = "";
		setLayout(new BorderLayout());
                setBackground(Color.DARK_GRAY);
                
                searchNavn = new JTextField(11);
                searchKategori = new JTextField(11);
                searchID = new JTextField(11);
		
		navnFelt = new JTextField(10);
		medKategori = new JTextField(10);
                medInfo    = new JTextArea(15,30);
                medInfo.setEditable(true);
                JScrollPane rulle2 = new JScrollPane(medInfo);		
                //arbeidsStedFelt = new JTextField(20);
		//reseptIDFelt.setEditable(false);
                
                reseptIDFelt = new JFormattedTextField(medIDformatter);
                reseptIDFelt.setColumns(14);
                
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
 /*               
                
                 // KATEGORI RULLEVINDU
                SpinnerListModel listModel = new SpinnerListModel(getKategori());
                medKategori    = new JSpinner(listModel);
                Dimension d = medKategori.getPreferredSize();
                d.width = 100;
                medKategori.setPreferredSize(d);
                medKategori.setValue( getKategori().get(getKategori().size()-1));
*/
                kEndreResept = new JButton("Endre Resept");
		kRegResept = new JButton("Reg Resept");
		kSlettResept = new JButton("Slett Resept");
		kVisResept = new JButton("Vis Resept");
		kNyResept = new JButton("Ny Resept");
		search = new JButton("Generer Nye Resepter");
                


                knappePanel.add(kNyResept);
                knappePanel.add(kRegResept);
                knappePanel.add(kSlettResept);
                knappePanel.add(kEndreResept);
                knappePanel.add(search);
                knappePanel.add(kVisResept);

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
                kRegResept.addActionListener(sensor);
                kEndreResept.addActionListener(sensor);
                kSlettResept.addActionListener(sensor);
                kVisResept.addActionListener(sensor);
                kNyResept.addActionListener(sensor);
                search.addActionListener(sensor);

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

                        String idfelt = searchID.getText();
                        String navn = searchNavn.getText();
                        String kat = searchKategori.getText();
                        char cha = searchaktivRadio();

                         Document source = documentEvent.getDocument();
                         String[] emptyArray = {"Ingen resept som stemmer med søket  " + navn + " " + idfelt + " " + cha + " " + kat};
                         int length = source.getLength();
                         boolean b = true;

                           if(bibliotek.finnObjekt(navn, idfelt, cha, kat)!=null)
                           {
                               b = false;
                               list.setListData(bibliotek.finnObjekt(navn, idfelt, cha, kat));
                           }
                           /*
                           if(idfelt.matches("-?\\d+(\\.\\d+)?") && bibliotek.finn(Integer.parseInt(idfelt))!=null)
                           {
                               b= false;
                               List<Resept> l = new ArrayList<>();
                               l.add(bibliotek.finn(Integer.parseInt(idfelt)));
                               list.setListData(l.toArray());
                           }*/
                           if(b)
                               list.setListData(emptyArray);

                          if(length == 0)  
                           list.setListData(bibliotek.returnObjekt());


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
                 gbc.gridy=1;      
                 searchPanel.add(new JLabel("Navn:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("ID:"),gbc);
                 gbc.gridx++;
                 searchPanel.add(new JLabel("Kategori:"),gbc);
                 gbc.gridy=2;
                 gbc.gridx=0;     
                 searchPanel.add(searchNavn, gbc);
                 gbc.gridx++;
                 searchPanel.add(searchID, gbc);
                 gbc.gridx++;
                 gbc.gridwidth = 2;
                 searchPanel.add(searchKategori, gbc);
                 gbc.gridwidth = 1;
                 gbc.gridx++;
                 gbc.gridy = 0;
                 searchPanel.add(searchRadioA);
                 gbc.gridy = 1;
                 searchPanel.add(searchRadioB);
                 gbc.gridy = 2;
                 searchPanel.add(searchRadioC);
                 gbc.gridy = 3;
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

                 feltPanel.add(new JLabel("Resept ID:"), gbc);

                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 feltPanel.add(reseptIDFelt, gbc);
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
                 feltPanel.add(medInfo, gbc);
                 gbc.gridwidth=1;
                 //gbc.gridy++;
                 gbc.gridx=0;
                 //feltPanel.add(new JLabel("KNAPPER"), gbc);

      
                 
                 gbc.gridy++;


                 gbc.gridheight = 1;
 
             gbc.gridy=12;
             
             gbc.ipady=1;
             
             
             
             gbc.gridx=3;
             feltPanel.add(kEndreResept,gbc);
             gbc.gridx=4;
             feltPanel.add(kRegResept,gbc);
             		
            feltPanel.setVisible(true);
      }
	
	
	  private void lastInnFil() throws IOException
	  {
	    try
	    {
	      bibliotek = fil.lastInnFilResept("ReseptLagring");
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
                fil.lagreFil(bibliotek, "ReseptLagring");
                System.out.println(logg.toString("ReseptRegister lagret!"));
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
	
	
	
	
  
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
            return aktivRadio() != 0 
                    && !reseptIDFelt.getText().equals("")
                    && !navnFelt.getText().equals("")
                    && !medInfo.getText().equals("")
                    //&& !medKategori.getValue().toString().equals(""); Hvis JSpinner
                    && !medKategori.getText().equals("");
        }
	
        public void regResept() 
        {
            try
             {
                    String id = reseptIDFelt.getText().replaceAll("\\s","");
                    String navn = navnFelt.getText();
                    char rbStatus = aktivRadio();
                    String info = medInfo.getText();
                    //String k = medKategori.getValue().toString(); Hvis JSpinner
                    String k = medKategori.getText();

                    if ( feltFylt() )
                    {
                    Resept resept = new Resept(id, navn, info, k, rbStatus);
                    bibliotek.settInnNy(resept);
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

        }
        
        public void endreResept()
        {
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
                  
                  
                  System.out.println(bibliotek.finn(id).getMedID());
                if (bibliotek.finnes(id))
                {          
                    Resept resept = new Resept(id, navn, info, k, rbStatus);
                        if(bibliotek.endre(resept))
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
    catch (NumberFormatException e)
    {
      error("Fyll ut alle feltene!");
    }
    catch (IndexOutOfBoundsException ex)
    {
        error("Fyll ut alle feltene!");
    }
        }
        
	public void visResept( Resept l ) 
        {
            if(getSelectedObject() != null)
            {
                kEndreResept.setEnabled(true);
                reseptIDFelt.setEnabled(false);
                        
                
			reseptIDFelt.setText(l.getMedID());			
			navnFelt.setText(l.getNavn());
			//medKategori.setValue(l.getKategori());JSPINNER
			medKategori.setText(l.getKategori());
			medInfo.setText(l.getInformasjon());
                        char x = l.getReseptGruppe();
			
				if(x == 'A') 
					radioA.setSelected(true);		
				if(x == 'B') 
					radioB.setSelected(true);
				if(x == 'C')
					radioC.setSelected(true);
			
                        logomraade.append(logg.toString("Fant resept: " + l.toString()));
		}		
                else
                    error("Ingen resept er valgt");
        }
	public void slettResept( Resept l) {
            
             int n = JOptionPane.showConfirmDialog(null,
                            "Vil du fjerne reseptn?",
                            "Fjern Resept!",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION)
                    {  

                        if(bibliotek.fjern(l)) {
                                String utskrift = "Reseptn " + l.getNavn() + " " + l.getMedID()+" - "+ l.getReseptGruppe()+ " er fjernet \n"; 
                                logomraade.append(logg.toString(utskrift));

                        }
                        else
                            error("Finner ikke resept \n");
                    }
                    else 
                        error("Resept er ikke fjernet ");
	}
        
	public void  emptyFields() {

            reseptIDFelt.setText("");
            navnFelt.setText("");
            //medKategori.setValue("---");
            medKategori.setText("");
            medInfo.setText("");
            
            radioGruppe.clearSelection();
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
		
		list.setListData(bibliotek.returnObjekt());
	}
      /*  
public static List<String> getKategori()
{

   List<String> k = Arrays.asList(getKategoriString());
   
   Collections.sort(k); 
   Collections.reverse(k);
   
   //k.add(" ");
   return k;
}*/
public static String[] getKategoriString(){
       String[] arr = 
   { "---",
       "Hypnotikum","Antibiotika","Sedativum",
       "Hemodialysekonsentrat","Glaukommiddel",
       "Adrenergikum","Progestogen"
   
   };
    
    return arr;
}
        
        private void generateResepter()
        {
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
                bibliotek.settInnNy(resept);
                
                
                
                
            }
            
        }

	
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                    
                       kRegResept.setEnabled(false);
                       kEndreResept.setEnabled(false);
                       
                      reseptIDFelt.setEnabled(true);
			
                    if (e.getSource() == kRegResept)
                    {
                        if (aktivRadio()!=0)
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
                        radioA.setSelected(true);
                        kRegResept.setEnabled(true);                      
                        emptyFields();
     		    }
                    else if (e.getSource() == kEndreResept)
                    {
                        endreResept();
                        emptyFields();
                    }
                    
		    else if ( e.getSource() == search ) 
                    {
		    	generateResepter();
		    }                    
                    oppdaterListe();
		}
	}
}
