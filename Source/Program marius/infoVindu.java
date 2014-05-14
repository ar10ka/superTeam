import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class infoVindu extends panelSuper {
	
	private JFrame f;
	private ReseptRegister resepter = new ReseptRegister();

	private JButton kVisInfo, kSlettResept;
        FilBehandler fil = new FilBehandler();
	
	
	public infoVindu( Object o) {
		
		setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
		kVisInfo = new JButton("Vis Info");
		kSlettResept = new JButton("Fjern Resepten");
		
		list = new JList(visListe(o));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        JScrollPane scrollpane = new JScrollPane(list);
        listPanel.setLayout(new BorderLayout());
        listPanel.add(searchPanel, BorderLayout.PAGE_START);
        listPanel.add(scrollpane, BorderLayout.CENTER);
        listPanel.add(knappePanel, BorderLayout.PAGE_END);
        sptop.add(listPanel);
        sptop.add(feltPanel);
        add(sptop, BorderLayout.CENTER);
        
        try{
        resepter = fil.lastInnFilResept("ReseptLagring");
        System.out.println("ok??");
        }	    
	    catch (FileNotFoundException ex)
	    {
	      System.out.println("Lager ny lagringsfil");
	    }
	    catch (EOFException ex)
	    {
	      System.out.println("Ferdig lastet!");
	    }
        catch (IOException ex)
        {
            
        }

		
	}
	
	public Object[] visListe(Object o) {
		
		Lege l;
		Pasient p;
		String [] tomListe = {"Inger resepter er registrert"};
		List<Object> reseptListe = new ArrayList<>();
		System.out.println("F�r instance of lege");
		if (o instanceof Lege ) {
			System.out.println("i ifen");
			l = (Lege)o;
			System.out.println("l=(Lege)o");
			
			if(resepter.getResepterLegeObject(l)!= null) {
				
			
			for(Resept x: resepter.getResepterLegeObject(l)) {
				System.out.println("i for l�kka");
				String s = x.getID() + " " + x.getPasient().getFNavn() + " " + x.getPasient().getENavn() + " " + x.getDato();
				//reseptList.add(x.getID());
				//reseptList.add(x.getPasient().getFNavn());
				//reseptList.add(x.getPasient().getENavn());
				
				x.setToString(s);
				
				reseptListe.add(x);
						
			}
			
			return reseptListe.toArray();	
		}
			System.out.println("Lista er TOM!");
		}
		
		if (o instanceof Pasient) {
				p = (Pasient)o;
				
					for(Resept x: resepter.getResepterPasientObject(p)) {
					
						String s = x.getID() + " " + x.getLege().getNavn() + " " + x.getLege().getEtternavn() + " "
						+ x.getLege().getlegeID() + " " + x.getDato();
						
						
						//reseptList.add(x.getID());
						//reseptList.add(x.getLege().getNavn());
						//reseptList.add(x.getLege().getEtternavn());
						//reseptList.add(x.getLege().getlegeID());
						x.setToString(s);
						reseptListe.add(x);

					}
					return reseptListe.toArray();
			}		
		
		return tomListe;
	}
	
	
	
	
	
	
	
}