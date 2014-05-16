import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class StatistikkPanel extends panelSuper {
	
	private LegeRegister legeRegister;
	private MedisinRegister medisinRegister;
	private PasientRegister pasientRegister;
	private ReseptRegister reseptRegister;
	private JTable tabel;
	private JPanel panel;
	private JFrame vindu;
	private JTextField byFelt;
	private JTextField searchBy;
	private JButton searchKnappen;
	private Lytter sensor;
	private DefaultTableModel model ;
	
	private String [] koloner = {"LegeID", "Navn", "Etternavn", "Arbeids Sted"};
	Object legeObjekt [] [];
	
	
	public StatistikkPanel() {
		super.setLayout(new GridBagLayout());
		sensor = new Lytter();
		fil = new FilBehandler();
		legeRegister = new LegeRegister();
		pasientRegister = new PasientRegister();
		medisinRegister = new MedisinRegister();
		reseptRegister = new ReseptRegister();
		model = new DefaultTableModel(legeObjekt,koloner);
	
		
        try //LASTER INN LEGEREGISTERET
        	{
        		lastInnFil();
        	}
        	catch (IOException ex)
        	{
        		ex.printStackTrace();
        	}
		
		
		
		
		//vindu = new JFrame();
		panel = new JPanel();
		byFelt = new JTextField("Skriv inn byen");
		//searchBy = new JTextField(20);
		searchKnappen = new JButton("S�k Lege Utifra byen");
		panel.setBackground(Color.red);
		panel.add(byFelt, BorderLayout.NORTH);
		panel.add(searchKnappen, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout());
		tabel = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tabel);
		panel.add(scrollPane, BorderLayout.CENTER);
		//panel.add(searchBy);
		//tabel = new JTable(legene,koloner);
		//vindu.setSize(800,400);
		//vindu.add(panel);
		//vindu.setVisible(true);
		add(panel);
		
		searchKnappen.addActionListener(sensor);
		
		
		
	}
	
	
	private void lastInnFil() throws IOException
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
	
	
	private void lastOppTabelen(String by) {
		
		System.out.println("Kommer i metoden");
		
		if(legeRegister.finnFraByen(by) != null) {
			System.out.println("Kommer i if setningen");
			//Object legeObjekt [] [] = null;
			Object [] [] legeObject = legeRegister.finnFraByen(by);
			for(int i = 0; i < legeRegister.finnFraByen(by).length; i++) {
				model.addRow(legeRegister.finnFraByen(by)[i]);

				
				
			}
			
			
			System.out.println(legeRegister.getAntall());
			try {
			lastInnFil();
			
			}
			catch(IOException e){}
			}
		 
	if(legeRegister.finnFraByen(by) == null)

		System.out.println("returnerer null");

	}
	
	private class Lytter implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                                       
			
               if (e.getSource() == searchKnappen)
                  {
            	   //tabel.removeAll();
            	   model.setRowCount(0);
            	   tabel.repaint();
                   	String stedet = byFelt.getText();
                        
                    lastOppTabelen(stedet);
                    
                        
                 }			
		    
		}
	}
}