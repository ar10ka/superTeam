/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/

import java.io.*;
import java.util.*;


//PASIENTREGISTERET
public class PasientRegister implements Serializable
{
  private List<Pasient> reg = new ArrayList<>();

  //Returnerer pasienter i et objekt array. Metoden brukes av søkemetoder i andre klasser.
  public Object[] finnObjekt ( String n, String e, String adr, String fnr) {
		Set<Pasient> pasienter = new HashSet<>();
		if(!reg.isEmpty())
                {
                    
                    for( Pasient l: reg) {
                        if(l.getFNr().contains(fnr) &&l.getFNavn().toLowerCase().contains(n.toLowerCase()) && l.getENavn().toLowerCase().contains(e.toLowerCase())&& l.getAdresse().toLowerCase().contains(adr.toLowerCase()) )
                            pasienter.add(l);
                            
                    }
                    if (!pasienter.isEmpty())
                        return pasienter.toArray();
                    else
                        return null;
                }
             return null;
	}  
public Pasient finnRandom( )//finner pasient
  {
          if(!reg.isEmpty())
          {
              
           int random = 1 + (int)(Math.random() * ((reg.size() - 1) + 1));
            
              
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
 public List<Pasient> finn(String fn, String en )//finner pasient
  {
     List<Pasient> pas = new ArrayList<>();
          if(!reg.isEmpty())
          {
            for( Pasient p : reg)
            {
                 if( p.getFNavn().equals(fn) && p.getENavn().equals(en))
                    pas.add(p);
            }
            return pas;
          }
          return null;
  }
 public Pasient finn(Pasient f)//finner pasient
  {
          if(!reg.isEmpty())
          {
            for( Pasient p : reg)
            {
                 if( p == f)
                    return p;
            }
          }
          return null;
   }
 public Pasient finn(String fNr)//finner pasient
  {

     
          if(!reg.isEmpty())
          {
            for( Pasient p : reg)
            {
                 if( p.getFNr().equals(fNr))
                    return p;
            }
          }
          return null;
   }
  public boolean finnes ( String fnavn, String enavn ) // finnes pasienten?
  {
      return finn(fnavn,enavn) != null;
  }
  public boolean finnes (Pasient p)
  {
      return finn(p)!=null;
  }
  public boolean finnes ( String fnr ) // finnes pasienten?
  {
      return finn(fnr) != null;
  }


  public boolean tom () //er lista tom?
  {
      return reg.isEmpty();
  }

  //Fjerner pasienten n fra registeret
 public boolean fjern( Pasient n )
  {
          if(!tom())
          {
            for( Pasient p : reg)
            {

                 if( p.getENavn().equals( n.getENavn()) && p.getFNavn().equals( n.getFNavn()) && p.getFNr().equals( n.getFNr()))
                 {
                    reg.remove(p);

                    return true;
                 }

            }
          }
          return false;
  }
 
 //Legger til pasient i registeret
  public void settInnNy( Pasient ny )
  {
    reg.add(ny);

  }
  //Endrer pasient av gitt parameter
  public boolean endre( Pasient n )
  {
            if(!tom())
          {
            for( Pasient p : reg)
            {

                 if(p.getFNr().equals( n.getFNr()))
                 {
                    p.setFNavn(n.getFNavn());
                    p.setENavn(n.getENavn());
                    p.setAdresse(n.getAdresse());
                    p.setGender(n.getGender());

                    return true;
                 }

            }
          }
          return false;
  }
  //Returnerer hele lista som objektarray
  public Object[] returnObjekt()
  {
      String[] emptyArray = {"Det er ingen pasienter registrert!"};
      if(!reg.isEmpty())  
        return reg.toArray();
      else
          return emptyArray;
  }
  public Object [] [] finnFraAdresse (String adresse ) { // metoden returnerer leger utifra byen han/hun er fra
	   //String[] emptyArray = {"Ingen Leger er registrert i denne byen"};
	   
	   List<Pasient> pasienter = new ArrayList<>();
	   ;
	   if(!reg.isEmpty() ) {
               
		   for(Pasient p: reg) {
                    if(adresse.equals(p.getAdresse())) {
			   
			   pasienter.add(p);
		   }
		  }
		   Object[][] felter = new Object[pasienter.size()][];
			   
			//antall = medisiner.size();  
			   
			   
			   
			  for (int i = 0; i < pasienter.size(); i++) {
				   Pasient p = pasienter.get(i);
                                   
				   felter[i] = new Object[] {i+1,
						   p.getENavn(),
						   p.getFNavn(),
						   p.getGender(),
                                                    p.getFNr()
						   
				   } ;
				
				  //return felter;
			   }
			  return felter;
			   
		   }
	   System.out.println("returnerer null I legeRegister");
	   	return null;
	   }

 //Returnerer hele lista som String
  @Override
  public String toString()
  {
    String tekst = "                PASIENTER:\n";

    for (Pasient x : reg)
      tekst += x.getInfo() + "\n";
    if (tekst.equals("") == false)
	  return tekst;
    else
      return "Listen er tom";
  }
}