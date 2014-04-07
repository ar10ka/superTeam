/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pasient;

/*

Studentnr: s188097
Navn: Ole Bøe Andreassen


Klasse: Dataingeniør

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
		//liste over resepter skrevet ut på pasienten.

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