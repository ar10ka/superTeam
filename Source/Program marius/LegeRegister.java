


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextArea;

/*

Studentnr: s198757
Navn: Marius Baltramaitis


Klasse: Dataingeniï¿½r

*/


public class LegeRegister implements Serializable
{

	private Lege lege;
	private List<Lege> reg = new ArrayList<>();
	private int legeID = 1000;
	private int antall;


	
	public LegeRegister() {
		antall = 0;
		/*String Marius = "Marius";
		String A = "A";
		String Sted = "Oslo";
		char[] a = {'A'};
		Lege l = new Lege(Marius, A, Sted, a, 7777); */
		
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
         public Lege finn(Lege l)//finner lege
  {
          if(!reg.isEmpty())
          {
            for( Lege x : reg)
            {
                 if( x == l)
                    return x;
            }
          }
          return null;
   }
         
   public Object[] returnObjekt()
    {
       String[] emptyArray = {"Det er ingen lege registrert ennå"};
       if(!reg.isEmpty())  
          return reg.toArray();
         else
          return emptyArray;
    }
   
   public int getAntall() {
	   return antall;
   }
   
   public Object [] [] finnFraByen(String by) { // metoden som returnerer leger utifra byen
	   //String[] emptyArray = {"Ingen Leger er registrert i denne byen"};
	   
	   List<Lege> leger = new ArrayList<>();
	   
	   if(!reg.isEmpty() ) {
		   for(Lege lege: reg) {
		   if(by.equals(lege.getArbeidsSted())) {
			   
			   leger.add(lege);
		   }
		  }
		   Object[][] felter = new Object[leger.size()][];
			   System.out.println("Stedet " + by);
			antall = leger.size();  
			   
			   
			   
			  for (int i = 0; i < leger.size(); i++) {
				   lege = leger.get(i);
				   
				   felter[i] = new Object[] {
						   lege.getlegeID(),
						   lege.getNavn(),
						   lege.getEtternavn(),
						   lege.getArbeidsSted()
						   
				   } ;
				  System.out.println("ID: " + lege.getlegeID());
				  //return felter;
			   }
			  return felter;
			   
		   }
	   System.out.println("returnerer null I legeRegister");
	   	return null;
	   }
	   /*if(!reg.isEmpty() &&  by.equals(lege.getArbeidsSted())) {
		   for(int i = 0; i <= tellOppLeger(); i++) {
			   
			   reg.add(x); 
			   
		   
			   
		   }
		   return r;
		   
	   }*/
	   	/*if(!reg.isEmpty()) {
			for( Lege l: reg) {
				if(l.getArbeidsSted()==(by)) {
					reg.add(l);
				}
			}
			return reg;
		} */

	   
         
	public List<Lege> finn ( String n, String e) {
		List<Lege> leger = new ArrayList<>();
		if(!reg.isEmpty())
                {
                    for( Lege l: reg) {
                            if(l.getNavn().equals(n) && l.getEtternavn().equals(e)) {
                                    leger.add(l);
                            }
                    }
                    return leger;
                }
             return null;
	}    
                 
	public Object[] finnObjekt ( String n, String e, String adr) {
		Set<Lege> leger = new HashSet<>();
		if(!reg.isEmpty())
                {
                    
                    for( Lege l: reg) {
                        if(l.getNavn().toLowerCase().contains(n.toLowerCase()) && l.getEtternavn().toLowerCase().contains(e.toLowerCase())&& l.getArbeidsSted().toLowerCase().contains(adr.toLowerCase()) )
                            leger.add(l);
                            
                    }
                    if (!leger.isEmpty())
                        return leger.toArray();
                    else
                        return null;
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
		if(reg.isEmpty()) {
		return true;
		}
		return false;
	}
	
	public boolean finnes(String n, String e) {
		return finn(n, e) != null;
	}
        public boolean finnes(Lege l)
        {
            return finn(l)!=null;
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
				utskrift += x.getInfo() + "\n";
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
    public int compare(Lege l1, Lege l2) {
        if(l1.getEtternavn().compareTo(l2.getEtternavn()) == 0) {
        	return 0;
        }
        return 1;
    }
 
}
	

