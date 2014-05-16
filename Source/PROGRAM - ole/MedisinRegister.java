

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.*;


/**
 *
 * @author Ole
 */
public class MedisinRegister implements Serializable
{
  private List<Medisin> reg = new ArrayList<>();

  public MedisinRegister()
  {
  }

  
  	public Medisin finn (String id) {
		if(!reg.isEmpty()) {
			for( Medisin m: reg) {
				if(m.getMedID().equals(id)) {
					return m;
				}
			}
		}
		return null;
	}
         public Medisin finn(Medisin m)//finner medisin
  {
          if(!reg.isEmpty())
          {
            for( Medisin x : reg)
            {
                 if( x == m)
                    return x;
            }
          }
          return null;
   }
         

 public Medisin finn(String n, String id )//finner medisin
  {
          if(!reg.isEmpty())
          {
            for( Medisin m : reg)
            {
            
                 if( m.getNavn().equals(n) || m.getMedID().equals(id))
                    return m;
            }
          }
          return null;
  }
 public Medisin finnRandom( )//finner medisin
  {
          if(!reg.isEmpty())
          {
              
           int random = 1 + (int)(Math.random() * ((reg.size() - 2) + 1));
            
              
            for( int i = 0; i < reg.size();i++)
            {
                if(random == i)
                {
                    return reg.get(i);
                }
                
            }
          }
          return null;
  }
    public Object[] returnObjekt()
    {
       String[] emptyArray = {"Det er ingen medisin registrert ennå"};
       if(!reg.isEmpty())  
          return reg.toArray();
         else
          return emptyArray;
    }
 	public Object[] finnObjekt ( String navn, String id, char cha ,String kat) {
		Set<Medisin> medisin = new HashSet<>();
		if(!reg.isEmpty())
                {
                    
                    for( Medisin m: reg) {
                        if(cha!=0)
                        {
                            if(m.getReseptGruppe()==cha && m.getNavn().toLowerCase().contains(navn.toLowerCase()) && m.getMedID().toLowerCase().contains(id.toLowerCase())&& m.getKategori().toLowerCase().contains(kat.toLowerCase()) )
                                medisin.add(m);
                        }
                        else{
                            if(m.getNavn().toLowerCase().contains(navn.toLowerCase()) && m.getMedID().toLowerCase().contains(id.toLowerCase())&& m.getKategori().toLowerCase().contains(kat.toLowerCase()) )
                                medisin.add(m);
                        }
                            
                    }
                    if (!medisin.isEmpty())
                        return medisin.toArray();
                    else
                        return null;
                }
             return null;
	}

//finnes medisin?
	public boolean finnes(String n, String e) {
		return finn(n, e) != null;
	}
        public boolean finnes(Medisin l)
        {
            return finn(l)!=null;
        }
	public boolean finnes (String f) {
		for ( Medisin m: reg) {
			if(m.getMedID().equals(f))
				return true;		
		}
		return false;
	}

  public boolean tom () //er lista tom?
  {
      return reg.isEmpty();
  }

 public boolean fjern( Medisin n )
  {
          if(!tom())
          {
            for( Medisin m : reg)
            {
                
                 if( m.getNavn().equals( n.getNavn()) && m.getMedID().equals(n.getMedID()))
                 {
                    reg.remove(m);
                    return true;
                 }
                 
            }
          }
          return false;
  }
 	public boolean endre(Medisin l) {
		
		if(!tom()) {
			
			for( Medisin x: reg) {
				if(x.getMedID().equals(l.getMedID())) {
					x.setNavn(l.getNavn());
					x.setKategori(l.getKategori());
					x.setMedID(l.getMedID());
					x.setInformasjon(l.getInformasjon());
                                        x.setReseptGruppe(l.getReseptGruppe());
					return true;
				}
			}
			
		}
		return false;
	}
	
 
  public void settInnNy( Medisin ny )
  {
    reg.add(ny);
  }

  public void sorter()
  {
	 Collections.sort(reg,new ComparatorImpl());
  }

  public String toString()
  {
    String tekst = "                MEDISINREGISTER:\n";

    for (Medisin x : reg)
      tekst += x.getInfo()+ "\n";
    if (tekst.equals("") == false)
	  return tekst;
    else
      return "Listen er tom";
  }

    private static class ComparatorImpl implements Comparator<Medisin> {

        public ComparatorImpl() {
        }

        @Override
        public int compare(Medisin o1, Medisin o2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
