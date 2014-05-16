import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.border.Border;

//KLASSEN VISER MER INFO AV GITT OBJEKT
public class InfoVindu extends JFrame {
	
    private final JPanel panel;
    private final GridBagConstraints gbc;
	private final JTextField  searchAdr,searchFNavn,searchENavn,searchID,medKategori,medInfo,medisinIDFelt;
        private final JTextField  legeIDFelt, navnFelt, etternavnFelt, arbeidsStedFelt,adressefelt,fnr;
        private final ButtonGroup radioGruppe, medradioGruppe;

	private final JRadioButton ARadio, BRadio, CRadio,radioMann, radioKvinne,radioA, radioB,radioC;; 
	Color greyWhite = new Color(224,224,224);
	Color grey = new Color(128,128,128);
	Border lineBorder = BorderFactory.createLineBorder(grey);

            //KONSTRUKTØR
            public InfoVindu( Object o, String s) {
               super(s+" - Infovindu");
                panel = new JPanel(new GridBagLayout());
                setLayout(new BorderLayout());
                setBackground(Color.DARK_GRAY);

                setVisible(true);
		setSize(800,300);
                    
                gbc = new GridBagConstraints();
                medisinIDFelt = new JTextField(11);
                medInfo = new JTextField(11);
                medKategori = new JTextField(11);
                searchAdr = new JTextField(11);
                searchFNavn = new JTextField(11);
                searchENavn = new JTextField(11);
                searchID = new JTextField(11);
		legeIDFelt = new JTextField(11);
		navnFelt = new JTextField(10);
		fnr = new JTextField(10);
		etternavnFelt = new JTextField(10);
		arbeidsStedFelt = new JTextField(20);
		adressefelt = new JTextField(20);

                medisinIDFelt.setEditable(false);
                medInfo.setEditable(false);
                medKategori.setEditable(false);
                searchAdr.setEditable(false);
                searchFNavn.setEditable(false);
                searchENavn.setEditable(false);
                searchID.setEditable(false);
		legeIDFelt.setEditable(false);
		navnFelt.setEditable(false);
		fnr.setEditable(false);
		etternavnFelt.setEditable(false);
		arbeidsStedFelt.setEditable(false);
		adressefelt.setEditable(false);
                
                ARadio = new JRadioButton("A");
		BRadio = new JRadioButton("B");
		CRadio = new JRadioButton("C");
                   
                radioA = new JRadioButton("A");
                radioB = new JRadioButton("B");
                radioC = new JRadioButton("C");

                radioMann  = new JRadioButton("Mann");
                radioKvinne   = new JRadioButton("Kvinne");
                radioGruppe = new ButtonGroup();
                radioGruppe.add(radioMann);
                radioGruppe.add(radioKvinne);
                
                medradioGruppe = new ButtonGroup();
                medradioGruppe.add(radioA);
                medradioGruppe.add(radioB);
                medradioGruppe.add(radioC);
                             		
		searchAdr.setBorder(lineBorder);
		searchFNavn.setBorder(lineBorder);
		searchENavn.setBorder(lineBorder);
		searchID.setBorder(lineBorder);
                navnFelt.setBorder(lineBorder);  
                etternavnFelt.setBorder(lineBorder);
                arbeidsStedFelt.setBorder(lineBorder);
                adressefelt.setBorder(lineBorder);
                fnr.setBorder(lineBorder);
                
                ARadio.setEnabled(false);
		BRadio.setEnabled(false);
		CRadio.setEnabled(false);               
                radioMann.setEnabled(false);
                radioKvinne.setEnabled(false);        
                radioA.setEnabled(false);
                radioB.setEnabled(false);
                radioC.setEnabled(false);
                
                lagLayout(o);
	}
            //Hvis metoden blir kalt så blir vinduet seende slik ut, avhengig av hvilket objekt lagLayout får
        private void addLegeFeltPanel()
        {

                 legeIDFelt.setEditable(false);  
                 panel.setLayout( new GridBagLayout() );
                
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 gbc.insets = new Insets(5,10,5,15);
                 gbc.ipady = 20;
            
                 gbc.weightx = 0.5;
                 gbc.weighty=1;
                 gbc.gridx=0;
                 gbc.gridy=0;

                 panel.add(new JLabel("Reseptgruppe(er)"),gbc);


                 gbc.gridx=1;


                 panel.add(ARadio,gbc);
                   gbc.gridx=2;

                 panel.add(BRadio,gbc);
                    gbc.gridx=3;
                 panel.add(CRadio,gbc);


                 gbc.gridy++;
                 gbc.gridx=0;

                 panel.add(new JLabel("Lege ID:"), gbc);

                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 panel.add(legeIDFelt, gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;

                 panel.add(new JLabel("Fornavn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(navnFelt,gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Etternavn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;

                 panel.add(etternavnFelt, gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("ArbeidsSted:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(arbeidsStedFelt, gbc);
                 gbc.gridwidth=1;
                 //gbc.gridy++;
                 gbc.gridx=0;
                 //panel.add(new JLabel("KNAPPER"), gbc);

             		
            panel.setVisible(true);
            add(panel,BorderLayout.CENTER);
      }
	private void addPasientFeltPanel()
        {
                 panel.setLayout( new GridBagLayout() );
                
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 gbc.insets = new Insets(5,10,5,15);
                 gbc.ipady = 20;
            
                 gbc.weightx = 0.5;
                 gbc.weighty=1;
                 gbc.gridx=0;
                 gbc.gridy=0;

                 panel.add(new JLabel("Kjønn"),gbc);


                 gbc.gridx=1;


                 panel.add(radioMann,gbc);
                   gbc.gridx=2;

                 panel.add(radioKvinne,gbc);
 


                 gbc.gridy++;
                 gbc.gridx=0;

                 panel.add(new JLabel("Personnummer:"), gbc);

                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 panel.add(fnr, gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;

                 panel.add(new JLabel("Fornavn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(navnFelt,gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Etternavn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;

                 panel.add(etternavnFelt, gbc);
                 gbc.gridwidth=1;

                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Adresse:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(adressefelt, gbc);

             		
            panel.setVisible(true);
            add(panel,BorderLayout.CENTER);
      }
          private void addMedisinFeltPanel()
        {
                 panel.setLayout( new GridBagLayout() );
                 gbc.anchor = GridBagConstraints.NORTH;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 gbc.insets = new Insets(5,10,5,15);
                 gbc.ipady = 20;
                 gbc.weightx = 0.5;
                 gbc.weighty=1;
                 gbc.gridx=0;
                 gbc.gridy=0;
                 panel.add(new JLabel("Reseptgruppe"),gbc);
                 gbc.gridx=1;
                 panel.add(radioA,gbc);
                   gbc.gridx=2;
                 panel.add(radioB,gbc);
                    gbc.gridx=3;
                 panel.add(radioC,gbc);
                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Medisin ID:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth = 4;
                 panel.add(medisinIDFelt, gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Navn:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(navnFelt,gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Kategori:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(medKategori, gbc);
                 gbc.gridwidth=1;
                 gbc.gridy++;
                 gbc.gridx=0;
                 panel.add(new JLabel("Informasjon:"), gbc);
                 gbc.gridx++;
                 gbc.gridwidth=4;
                 panel.add(medInfo, gbc);
   
            panel.setVisible(true);
            add(panel,BorderLayout.CENTER);
      }
	
	
    //metoden får et objekt av enten pasient,lege eller pasient og sjekker hva slags objekt det er. 
          //Lager layout og sender deretter objektet videre til enten visPasient, visLege eller visMedisin    
	public void lagLayout(Object o) {
	
		if (o instanceof Lege )
                {
                    Lege l = (Lege)o;
                    addLegeFeltPanel();
                    visLege(l);
                }
                if (o instanceof Pasient)
                {
                    Pasient p = (Pasient)o;
                    addPasientFeltPanel();
                    visPasient(p);
                }
                if (o instanceof Medisin)
                {
                    Medisin m = (Medisin)o;
                    addMedisinFeltPanel();
                    visMedisin(m);
                }
        }
                
        //Disse metodene setter feltene i panelet til objektets innhold.
        private void visPasient( Pasient p ) 
        {       
                fnr.setText(""+p.getFNr());			
                navnFelt.setText(p.getFNavn());
                etternavnFelt.setText(p.getENavn());
                adressefelt.setText(p.getAdresse());
                char x = p.getGender();

                if(x == 'M') 
                    radioMann.setSelected(true);		
                else
                    radioKvinne.setSelected(true);
        }
        private void visLege( Lege l ) 
        {       
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
        }
        private void visMedisin( Medisin m ) 
        {                        
                medisinIDFelt.setText(m.getMedID());			
                navnFelt.setText(m.getNavn());
                medKategori.setText(m.getKategori());
                medInfo.setText(m.getInformasjon());
                char x = m.getReseptGruppe();

                if(x == 'A') 
                        radioA.setSelected(true);		
                if(x == 'B') 
                        radioB.setSelected(true);
                if(x == 'C')
                        radioC.setSelected(true);
        }
}