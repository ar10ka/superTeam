import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*

Studentnr: s198757
Navn: Marius Baltramaitis


Klasse: Dataingeniør

*/


public class LegeRegister implements Serializable
{

	private Lege lege;
	private List<Lege> reg = new ArrayList<>();
	
	public LegeRegister() {
		
	}
	
	public void settInn(Lege ny) {
		if(ny.getLegeID() != null && ny.getNavn() != null)
		{
			reg.add(ny);
		}
	}
	
	public Lege finn(String n, String LegeID) {
		if(!reg.isEmpty())
		{
			for( Lege l: reg) {
				if(l.getNavn().equals(n) && l.getLegeID().equals(LegeID)) {
					return l;
				}
			}
			
		}
		return null;
	}
	
	public boolean slettLege(String n, String LegeID) {
		if(!reg.isEmpty())
		{
			for(Lege l: reg) {
				if(l.getNavn().equals(n) && l.getLegeID().equals(LegeID)) {
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
	
	public boolean finnes(String n, String LegeID) {
		return finn(n, LegeID ) != null;
	}
	
	public void sorter() {
		Collections.sort(reg,new ComparatorImpl());
	}
	
	public String getText() {
		String utskrift = "";
		
		for (Object x: reg){
			if (!tomListe()) {
				utskrift += reg.toString();
			}
		}
		return utskrift;
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
	

