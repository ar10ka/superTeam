


/*

Studentnr: s188097
Navn: Ole Bøe Andreassen


Klasse: Dataingeniør

*/

import java.io.Serializable;

public class Pasient implements Serializable
{
	private String fnavn, enavn,adresse,fNr;
        private char gender;


	public Pasient(  String fn, String en, String fnr, char g , String a)
	{
                fnavn = ordneString(fn);
		enavn = ordneString(en);
		fNr = fnr;
                gender = g;
                adresse = ordneString(a);
		

	}
        public static String ordneString(String string) 
        {
            char[] chars = string.toLowerCase().toCharArray();
            boolean funnet = false;
            for (int i = 0; i < chars.length; i++) 
            {
                if (!funnet && Character.isLetter(chars[i])) 
                {
                    chars[i] = Character.toUpperCase(chars[i]);
                    funnet = true;
                } 
                else if (Character.isWhitespace(chars[i]) || chars[i]=='.')//aktiverer funnet når den har funnet et blankt tegn, punktum 
                { 
                    funnet = false;
                }
            }
            
          return String.valueOf(chars).replaceAll("([^\\d-]?)(-?[\\d\\.]+)([^\\d]?)", "$1 $2 $3").replaceAll(" +", " ").trim();
        }

	public String getFNavn()
	{
		return fnavn;
	}

        public String getENavn()
        {
            return enavn;
        }

	public String getFNr()
	{
		return fNr;
	}

        public char getGender()
	{
		return gender;
	}

        public String getAdresse()
	{
                return adresse;
	}

        public void setGender(char g)
	{
		gender = g;
	}

        public void setAdresse(String a)
	{
		adresse = a;
	}

        public void setFNavn(String fn)
	{
		fnavn = fn;
	}

        public void setENavn(String en)
        {
            enavn = en;
        }

	public void setFnr(String nr)
	  {
		 fNr = nr;
	  }



	@Override
	public String toString()
	  {
		
              return enavn + ", " + fnavn + "\t" + fNr;
	  }
        public String getInfo()
	  {
		
              return "Navn:\t" + fnavn + " " + enavn
                        + "\nfNr:\t" + fNr
                        + "\nKjønn:\t" + gender
                        + "\nAdresse:\t" + adresse
                        + "\n\n";
	  }

}