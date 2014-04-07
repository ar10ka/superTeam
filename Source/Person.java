import java.util.ArrayList;



public abstract class Person {
	
	private String navn;
	abstract String getID();
	
	public Person ( String n) {
		
		navn = n;	
		
	}
	
	class Lege extends Person {
		
		private String arbeidsSted;
		private boolean reseptGruppe;
		private ArrayList<String> reseptList = new ArrayList<>();
		private String legeID;
		
		public Lege ( String n, String sted, boolean rgruppe, ArrayList<String> rList, String id ) {
			super(n);
			arbeidsSted = sted;
			reseptGruppe = rgruppe;
			reseptList = rList;	
			legeID = id;
			
		}
		
		@Override
		
		public String getID() {
			return legeID;
		}
		
	}
	
	class Pasient extends Person {
		
		private String fodselsdato;
		private String fodselsNr;
		private ArrayList<String> reseptList = new ArrayList<>();
		
		public Pasient (String n, String fd, String fn, ArrayList<String> rList) {
			
			super(n);
			fodselsdato = fd;
			fodselsNr = fn;
			reseptList = rList;
		}
		
		@Override
		
		public String getID() {
			return fodselsNr;
		}
		
	}


	

}
