import java.io.Serializable;
import java.util.ArrayList;



public class Lege implements Serializable {
		private String navn;
		private String arbeidsSted;
		private char reseptGruppe;
		private ArrayList<String> reseptList = new ArrayList<>();
		private String legeID;
		
		public Lege ( String n, String sted, char rgruppe, ArrayList<String> rList, String id ) {
			navn = n;
			arbeidsSted = sted;
			reseptGruppe = rgruppe;
			reseptList = rList;	
			legeID = id;
			
			
		}
		
		public String getNavn() {
			return navn;
		}
		
		public String getLegeID() {
			return legeID;
			
		}
		
		private String getArbeidsSted() {
			return arbeidsSted;
		}
		
		private char getReseptGruppe() {
			return reseptGruppe;
		}
		
		@Override
		public String toString() {
			return "Navn: " + navn + "\nLege ID: " + legeID + "\nArbeids Sted: " + arbeidsSted
					+ "\nResept Gruppe: " + reseptGruppe;
		}
	
}