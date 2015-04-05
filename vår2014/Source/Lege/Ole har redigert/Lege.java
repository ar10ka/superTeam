

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

		public void setNavn(String n) {
			navn = n;
		}

		public void setEtternavn(String e) {
			etternavn = e;
		}

		public void setFNr(String nr) {
			fNr = nr;
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


		public String getFNr() {
			return fNr;

		}

		public String getArbeidsSted() {
			return arbeidsSted;
		}

		public char[] getReseptGruppe() {
			return reseptGruppe;
		}

		@Override
		public String toString() {
			String teksten ="";
			for (char x : reseptGruppe)
			{
				teksten += x;
			}
			return       "Navn: " + navn  + "\nEtternavn: " + etternavn
                                +  "\nLege ID: " + fNr
                                +  "\nArbeidssted : " + arbeidsSted
                                +  "\nBevilling: " + teksten
                                +  "\n";
		}

}