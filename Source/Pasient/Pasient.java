package pasient;

/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/

import java.io.Serializable;

public class Pasient implements Serializable
{
	  private int fdato;
	  private String navn;


	  public Pasient(  String n, int fd )
	  {
		navn = n;
		fdato = fd;
		//liste over resepter skrevet ut paa pasienten.

	  }

	public String getNavn()
	{
		return navn;
	}
	  public int getFDato()
	  {
		  return fdato;
	  }



	@Override
	public String toString()
	  {
		return "Navn:\t" + navn
				+ "\nFoedselsdato:\t" + fdato + "\n\n";
	  }
}