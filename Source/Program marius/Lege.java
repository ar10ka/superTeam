
import java.io.Serializable;
import java.util.ArrayList;


//LEGEKLASSEN
public class Lege implements Serializable 
{
		private String navn,etternavn,arbeidsSted;
		private char[] reseptGruppe;
		private final ArrayList<String> reseptList = new ArrayList<>();
		private int legeID;

                /**
                 * Konstruktør som initialiserer alle datafeltene og oppretter en ny lege.
                 * @param n Navn på legen
                 * @param enavn Etternavn på legen
                 * @param sted Arbeidssted for legen
                 * @param rgruppe Reseptgruppe char Array for bevilling
                 * @param id Legeid genereres automatisk i legeregiter klasse
                 */
		public Lege (String n, String enavn, String sted, char[] rgruppe, int id ) {
			
			legeID = id;
			navn = ordneString(n);
			etternavn = ordneString(enavn);
			arbeidsSted = ordneString(sted);
			reseptGruppe = rgruppe;

		}
                 //Metoden ordner stringen i parameteret slik at det blir stor bokstav i starten av en setning, etter punktum og mellomrom. Resten blir liten bokstav.
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
		
		public void setNavn(String n) {
			navn = n;
		}
		
		public void setEtternavn(String e) {
			etternavn = e;
		}
		
		public void setlegeID(int nr) {
			legeID = nr;
		}
		
		public void setArbeidsSted(String s) {
			arbeidsSted = s;
		}
		
		public void setReseptGruppe(char [] r) {
			reseptGruppe = r;
		}
                
		public String getNavn() {
			return navn;
		}

		public String getEtternavn() {
			return etternavn;
		}

		
		public int getlegeID() {
			return legeID;

		}
		
		public String getArbeidsSted() {
			return arbeidsSted;
		}

		public char[] getReseptGruppe() {
			return reseptGruppe;
		}

                public String getInfo(){
                    String rgrupper ="";
			for (char x : reseptGruppe)
			{
				rgrupper += x;
			}
			return "Navn: " + navn  + "\nEtternavn: " + etternavn + "\nLegeID: " + legeID +  "\nArbeidssted : " + arbeidsSted +
					"\nBevilling: " + rgrupper + "\n";
                }
		@Override
		public String toString() {
			return etternavn  + ", " + navn + "  " + legeID + "\n";
		}

}