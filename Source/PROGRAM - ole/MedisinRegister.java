
/**
 *
 * @author Ole Bøe Andreassen - s188097
 */
import java.io.Serializable;
import java.util.*;

public class MedisinRegister implements Serializable
{
  private List<Medisin> reg = new ArrayList<>();
  
  //Finner medisin ved medisin id
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
         public Medisin finn(Medisin m)//finner medisin ved objekt
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
         

 public Medisin finn(String n, String id )//finner medisin ved navn og id
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
 public Medisin finnRandom( )//finner tilfeldig medisin
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
    public Object[] returnObjekt() //Returnerer alle objektene i registeret i et array
    {
       String[] emptyArray = {"Det er ingen medisin registrert ennå"};
       if(!reg.isEmpty())  
          return reg.toArray();
         else
          return emptyArray;
    }
    
    //Returnerer et array av objekter som stemmer over ens med parameterene. Brukes av søkemetoder i andre klasser
    public Object[] finnObjekt ( String navn, String id, char cha ,String kat) {
		Set<Medisin> medisin = new HashSet<>();
		if(!reg.isEmpty())
                {
                    for( Medisin m: reg) {
                        if(cha!=0)//HVIS MAN HAR VALGT EN RESEPTGRUPPE
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

        //finnes medisinen?
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
        //Fjerner medisin gitt ved objekt
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
        //Endrer medisin gitt ved objekt
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
            public Object [] [] finnFraKategorien(String kategori) { // metoden som returnerer leger utifra byen
	   //String[] emptyArray = {"Ingen Leger er registrert i denne byen"};
	   
	   List<Medisin> medisiner = new ArrayList<>();
	   System.out.println("før object[][]");
	   if(!reg.isEmpty() ) {
               System.out.println("lista er ikke tom");
		   for(Medisin m: reg) {
                    if(kategori.equals(m.getKategori())) {
			   
			   medisiner.add(m);
		   }
		  }
		   Object[][] felter = new Object[medisiner.size()][];
			   
			int antall = medisiner.size();  
			   
			   
			   
			  for (int i = 0; i < medisiner.size(); i++) {
				   Medisin m = medisiner.get(i);
                                   
				   felter[i] = new Object[] {i+1,
						   m.getNavn(),
						   m.getKategori(),
						   m.getReseptGruppe(),
                                                    m.getKategori()
						   
				   } ;
				
				  //return felter;
			   }
			  return felter;
			   
		   }
	   System.out.println("returnerer null I legeRegister");
	   	return null;
	   }

	
	
 
  public void settInnNy( Medisin ny )//Registrerer nytt medisin objekt i registeret
  {
    reg.add(ny);
  }
  
  @Override
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
}
