
/*

Studentnr: s198757
Navn: Marius Baltramaitis


Klasse: Dataingeniør

*/

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;


public class StatistikkPanel extends panelSuper {
	
	private JTable tabel;
	private JPanel searchPanel, tabelPanel;
	private JFrame vindu;
	private JTextField byFelt;
	private JTextField searchBy;
	private JButton searchKnappen;
	private Lytter sensor;
	private DefaultTableModel model;
        private final JRadioButton LegeStatistikk, MedisinStatistikk, ReseptStatistikk,PasientStatistikk;
        private final ButtonGroup radioGruppe;
	
	private String [] Legekoloner = {"Nr", "Navn", "Etternavn", "Arbeids Sted", "LegeID"};
	Object legeObjekt [] [];
	
	
	public StatistikkPanel() {
           // super.setLayout(new BorderLayout());
            super.setBackground(blue);
		fil = new FilBehandler();
                sensor = new Lytter();
                     try //LASTER INN LEGEREGISTERET
        	{
        		lastInnFil();
        	}
        	catch (IOException ex)
        	{
        		ex.printStackTrace();
        	}
		
		
		model = new DefaultTableModel(legeObjekt,Legekoloner);
                tabelPanel = new JPanel();
                searchPanel = new JPanel();
                LegeStatistikk = new JRadioButton("Lege Statistikk");
                MedisinStatistikk = new JRadioButton("Medisin Statistikk");
                ReseptStatistikk = new JRadioButton("Resept Statistikk");
                PasientStatistikk = new JRadioButton("Pasient Statistikk");
                radioGruppe = new ButtonGroup();
                radioGruppe.add(LegeStatistikk);
                radioGruppe.add(PasientStatistikk);
                radioGruppe.add(MedisinStatistikk);
                radioGruppe.add(ReseptStatistikk);
                
                tabel = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(tabel);
                byFelt = new JTextField(12);
                byFelt.setBorder(lineBorder);

                
		searchKnappen = new JButton("Søk");
                searchKnappen.setVisible(true);
                searchKnappen.setBackground(greyWhite);
                searchPanel.add(LegeStatistikk);
                searchPanel.add(PasientStatistikk);
                searchPanel.add(MedisinStatistikk);
                searchPanel.add(ReseptStatistikk);
		searchPanel.add(byFelt, BorderLayout.CENTER);
		searchPanel.add(searchKnappen, BorderLayout.CENTER);
                listPanel.add(scrollPane);                
                spbottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                spbottom.setEnabled(true);
                spbottom.setDividerSize(0);
                spbottom.setResizeWeight(0.8);
                spbottom.add(searchPanel);
                spbottom.add(listPanel);
                aktivRadio();
                add(spbottom, BorderLayout.CENTER);
		
		searchKnappen.addActionListener(sensor);
                LegeStatistikk.addActionListener(sensor);
                MedisinStatistikk.addActionListener(sensor);
	}
	
	
	public void lastInnFil() throws IOException
	  {
	    try
	    {
	      legeRegister = fil.lastInnFilLege("LegeLagring");
	      reseptRegister = fil.lastInnFilResept("ReseptLagring");
	      medisinRegister = fil.lastInnFilMedisin("MedisinLagring");
	      pasientRegister = fil.lastInnFilPasient("PasientLagring");
            System.out.println(logg.toString("Registrene lastet inn!"));

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
        
        
	  public String aktivRadio()
          {
            if(LegeStatistikk.isSelected()) {
                

                return "L";
            }
            else if (MedisinStatistikk.isSelected()) {
                   return "M";
             }
              
            else if (ReseptStatistikk.isSelected()) {
                return "R";
             }
            else if (PasientStatistikk.isSelected()) {
                return "P";
             }
                      
            return null;

          }
	
	private void lastOppLegeneITabellen(String by) {
		
		System.out.println("Kommer i metoden");
		
		if(legeRegister.finnFraByen(by) != null && aktivRadio().equals("L")) {
			System.out.println("Kommer i if setningen");
			for(int i = 0; i < legeRegister.finnFraByen(by).length; i++) {
				model.addRow(legeRegister.finnFraByen(by)[i]);
			}
			
			
			System.out.println(legeRegister.getAntall());
			try {
			lastInnFil();
			
			}
			catch(IOException e){}
			}

	}
        
        	private void lastOppMedisinTabellen(String kategori) {
		
		System.out.println("Kommer i metoden");
		
		if (medisinRegister.finnFraKategorien(kategori) != null) {
                    System.out.println("Kommer i if setningen");
                    for(int i = 0; i < medisinRegister.finnFraKategorien(kategori).length; i++) {
                        model.addRow(medisinRegister.finnFraKategorien(kategori)[i]);
                        System.out.println("i for løkka");
                    }
                    try {
                        lastInnFil();
                    }
                    catch(IOException e){}
            }
	}
            private void lastOppPasientTabellen(String adresse) 
            {
		if (pasientRegister.finnFraAdresse(adresse) != null) 
                {
                    System.out.println("Kommer i if setningen");
                    for(int i = 0; i < pasientRegister.finnFraAdresse(adresse).length; i++) 
                    {
                        model.addRow(pasientRegister.finnFraAdresse(adresse)[i]);
                        System.out.println("i for løkka");
                    }
                    try {
                        lastInnFil();
                    }
                    catch(IOException e){}
            }
	}
            
            private void lastOppResepterITabellen(String kategori) {
		
		System.out.println("Kommer i metoden");
		
		if(reseptRegister.finnFraKategori(kategori) != null && aktivRadio().equals("R")) {

			for(int i = 0; i < reseptRegister.finnFraKategori(kategori).length; i++) {
				model.addRow(reseptRegister.finnFraKategori(kategori)[i]);
			}
			try {
			lastInnFil();
			}
			catch(IOException e){}
			}
	}
                
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                                       
			
               if (e.getSource() == searchKnappen && aktivRadio()=="L")
                  {
                      model.setRowCount(0);
                       tabel.repaint();
                   	String innskrift = byFelt.getText();
                        lastOppLegeneITabellen(innskrift);
                 }
               if ( e.getSource() == searchKnappen && aktivRadio()=="M") {
                   model.setRowCount(0);
                   System.out.println("Aktiv radio = M, Medisin er valgt");
                    String innskrift = byFelt.getText();
                    lastOppMedisinTabellen(innskrift);
               }
               if ( e.getSource() == searchKnappen && aktivRadio()=="P") {
                   model.setRowCount(0);
                   System.out.println("Aktiv radio = P, Pasient er valgt");
                    String innskrift = byFelt.getText();
                    lastOppPasientTabellen(innskrift);
               }
               if ( e.getSource() == searchKnappen && aktivRadio()=="R") {
                   model.setRowCount(0);
                   System.out.println("Aktiv radio = R, Resept er valgt");
                    String innskrift = byFelt.getText();
                    lastOppResepterITabellen(innskrift);
               }
            }
	}
}