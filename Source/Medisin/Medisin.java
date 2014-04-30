/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Medisin;

/**
 *
 * @author Ole
 */
import java.io.Serializable;

public class Medisin implements Serializable
{
	  private int medID;
	  private String navn, info, kategori;
          private char reseptGruppe;


	  public Medisin( int id, String n, String i, String k, char rGruppe  )
	  {
                  medID = id;
                  navn = n;
                  info = i;
                  kategori = k;
                  reseptGruppe = rGruppe;   
		//liste over resepter skrevet ut paa pasienten.
	  }


	public int getMedID()
	{
		return medID;
	}
	public String getNavn()
	{
		return navn;
	}
        public String getInfo()
        {
            return info;
        }
        public String getKategori()
        {
            return kategori;
        }
        public char getReseptGruppe()
        {
            return reseptGruppe;
        }


	@Override
	public String toString()
	  {
		return "Medisin info:"
                        + "\nID:\t" + medID + "\n"
                        + "\nNavn:\t" + navn + "\n"
                        + "\nKategori:\t" + kategori + "\n"
                        + "\nReseptgruppe:\t" + reseptGruppe + "\n"
                        + "\nInfo:\t" + info + "\n"
                        + "\n\t-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-\t\n\n";
	  }
}