import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTextArea;

/*

Studentnr: s198757
Navn: Marius Baltramaitis


Klasse: Dataingeniør

*/


public class LegeRegister implements Serializable
{

	private Lege lege;
	private List<Lege> reg = new ArrayList<>();
	private int legeID = 1000; 
	
	public LegeRegister() {
		
	}
	
	
	public void settInn(Lege ny) {
		//int nyttID = legeID++;
		if(ny.getNavn() != null)
		{
			ny.setlegeID(legeID++);
			reg.add(ny);
		}
	}
	

	
	public Lege finn (int id) {
		if(!reg.isEmpty()) {
			for( Lege l: reg) {
				if(l.getlegeID()==(id)) {
					return l;
				}
			}
		}
		return null;
	}
	
	
	public boolean slettLege(int id) {
		if(!reg.isEmpty())
		{
			for(Lege l: reg) {
				if(l.getlegeID()==(id)) {
					reg.remove(l);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean tomListe() {
		return reg.isEmpty();
	}
	
	public boolean finnes(String n, String e) {
		return finnOgReturner(n, e) != null;
	}
	
	public String finnOgReturner ( String n, String e) {
		String utskrift = "";
		for( Lege l: reg) {
			if(l.getNavn().equals(n) && l.getEtternavn().equals(e)) {
				utskrift +=  l.getNavn() + " " + l.getEtternavn() +" " + l.getlegeID() + "\n";
			}
		}
		return utskrift;
	}
	
	public boolean finnes (int f) {
		for ( Lege l: reg) {
			if(l.getlegeID()==(f))
				return true;		
		}
		return false;
	}
	
	public boolean endreLege(Lege l) {
		
		if(!tomListe()) {
			
			for( Lege x: reg) {
				if(x.getlegeID() == l.getlegeID()) {
					x.setNavn(l.getNavn());
					x.setEtternavn(l.getEtternavn());
					x.setArbeidsSted(l.getArbeidsSted());
					x.setReseptGruppe(l.getReseptGruppe());
					return true;
				}
			}
			
		}
		return false;
	}
	
	public void sorter() {
		Collections.sort(reg,new ComparatorImpl());
	}
	
	public String getText() {
		String utskrift = "";
		
		for (Lege x: reg){
			if (!tomListe()) {
				utskrift += x.toString() + "\n";
			}
		}
		return utskrift;
	}
	
	public int tellOppLeger() {
		int antall = 0;
		
		for ( Lege l : reg) {
			if(l.getNavn() != null) {
				antall++;
				
			}
		}
		return antall;
	}
	
}

class ComparatorImpl implements Comparator<Lege> {

    public ComparatorImpl() {
    }

    @Override
    public int compare(Lege o1, Lege o2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
	

