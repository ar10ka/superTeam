import java.io.Serializable;
import java.util.ArrayList;



public class Lege implements Serializable {
		private String navn;
		private String etternavn;
		private String arbeidsSted;
		private char[] reseptGruppe;
		private ArrayList<String> reseptList = new ArrayList<>();
		//private String legeID;
		private int legeID;

		public Lege (String n, String enavn, String sted, char[] rgruppe, int id ) {
			
			legeID = id;
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

		@Override
		public String toString() {
			String teksten ="";
			for (char x : reseptGruppe)
			{
				teksten += x;
			}
			return "Navn: " + navn  + "\nEtternavn: " + etternavn + "\nLegeID: " + legeID +  "\nArbeidssted : " + arbeidsSted +
					"\nBevilling: " + teksten + "\n";
		}

}