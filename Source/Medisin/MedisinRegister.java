/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Medisin;

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

 public Medisin finn(String n, int id )//finner medisin
  {
          if(!reg.isEmpty())
          {
            for( Medisin m : reg)
            {
                 if( m.getNavn().equals(n) && m.getMedID() == id)
                    return m;
            }
          }
          return null;
  }

  public boolean finnes ( Medisin m ) // finnes medisinen?
  {
      if(finn(m.getNavn(),m.getMedID()) != null)
      {
          return true;
      }
      else
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
                
                 if( m.getNavn().equals( n.getNavn()) && m.getMedID() == n.getMedID())
                 {
                    reg.remove(m);
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

  public String getText()
  {
    String tekst = "                MEDISINREGISTER:\n";

    for (Medisin x : reg)
      tekst += x.toString() + "\n";
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
