import java.io.Serializable;
import java.util.ArrayList;



public class Lege implements Serializable {
		private String navn;
		private String etternavn;
		private String arbeidsSted;
		private char[] reseptGruppe;
		private ArrayList<String> reseptList = new ArrayList<>();
		private String fNr;

		public Lege ( String nr, String n, String enavn, String sted, char[] rgruppe ) {
			fNr = nr;
			navn = n;
			etternavn = enavn;
			arbeidsSted = sted;
			reseptGruppe = rgruppe;

		}

		public String getNavn() {
			return navn;
		}

		public String getEtternavn() {
			return etternavn;
		}

		
		public String getFNr() {
			return fNr;

		}

		private String getArbeidsSted() {
			return arbeidsSted;
		}

		private char[] getReseptGruppe() {
			return reseptGruppe;
		}

		@Override
		public String toString() {
			String teksten ="";
			for (char x : reseptGruppe)
			{
				teksten += x;
			}
			return "Navn: " + navn  + "\nEtternavn: " + etternavn + "\nFødselsnummer: " + fNr +  "\nArbeidssted : " + arbeidsSted +
					"\nBevilling: " + teksten + "\n";
		}

}