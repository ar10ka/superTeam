/*

Studentnr: s188097
Navn: Ole Bøe Andreassen


Klasse: Dataingeniør

*/

import java.io.Serializable;
import java.util.*;

public class PasientRegister implements Serializable
{
  private List<Pasient> reg = new LinkedList<>();
  private Iterator<Pasient> iter;
  //private Bil hode;
  public PasientRegister()
  {
	  //Konstruktør

    //hode = null;
  }

 public Pasient finn(String n, int fd )//finner pasient
  {
	  for( Pasient p : reg)
	  {

	  }
  }

  public boolean finnes ( String n, int fd ) // finnes bilen?
  {
	  if (finn(n,fd) != null)
	  	return true;
	  else
	  	return false;
  }


  public boolean tom () //er lista tom?
  {
	  if (reg.isEmpty())
	  	return true;
	  else
	  	return false;
  }

 public boolean fjern( String n, int fd )
  {

	iter = reg.iterator();

		if( reg.isEmpty() )
		  return false;

		if( iter.getNavn() == n && iter.getFDato() == fd )
		{
			iter.remove();
			return true;
		}


    while( løper.appendix != null  )
    {
	  if(iter.getNavn() == n && iter.getFDato() == fd)
       {
         iter.remove();
         return true;
	   }
	  else
		iter.next();
    }
    return false;
  }

  public void setInnNy( Pasient ny )
  {
    reg.add(ny);
  }

  public void sorter()
  {
	  Collections.sort(liste);
  }

  public String getText()
  {
    String tekst = "                PASIENTER:\n";

    for (Pasient x : reg)
      tekst += x.toString() + "\n";
    if (tekst.equals("") == false)
	  return tekst;
    else
      return "Listen er tom";
  }


}