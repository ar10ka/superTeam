package Program;




/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/

import java.io.*;
import java.util.*;

public class PasientRegister implements Serializable
{
  private List<Pasient> reg = new ArrayList<>();

  public PasientRegister()
  {
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

  public void settInnNy( Pasient ny )
  {
    reg.add(ny);

  }
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
  
  public Object[] returnObjekt()
  {
      String[] emptyArray = {"Det er ingen pasienter registrert!"};
      if(!reg.isEmpty())  
        return reg.toArray();
      else
          return emptyArray;
  }

  public void sorter()
  {
	 Collections.sort(reg,new ComparatorImpl());
  }

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

    private static class ComparatorImpl implements Comparator<Pasient> {

        public ComparatorImpl() {
        }

        @Override
        public int compare(Pasient o1, Pasient o2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }


}