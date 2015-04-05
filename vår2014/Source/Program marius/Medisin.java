
import java.io.Serializable;

//MEDISINKLASSEN
public class Medisin implements Serializable
{
	  
	  private String navn, info, kategori,medID;
          private char reseptGruppe;
	  public Medisin( String id, String n, String i, String k, char rGruppe  )
	  {
                  medID = id;
                  navn = n;
                  info = i;
                  kategori = k;
                  reseptGruppe = rGruppe;   
	  }
	public String getMedID()
	{
		return medID;
	}
	public String getNavn()
	{
		return navn;
	}
        public String getInformasjon()
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
	public void setMedID(String id)
	{
		 medID = id;
	}
	public void setNavn(String n)
	{
                navn = n;
	}
        public void setInformasjon(String i)
        {
                info = i;
        }
        public void setKategori(String k)
        {
                kategori = k;
        }
        public void setReseptGruppe(char rGruppe)
        {
                reseptGruppe = rGruppe;
        }

	@Override
        public String toString(){
            return navn + ", " + reseptGruppe + "  " + medID;
        }
        
	public String getInfo()
	  {
		return "Medisin info:"
                        + "\nID:\t" + medID + "\n"
                        + "\nNavn:\t" + navn + "\n"
                        + "\nKategori:\t" + kategori + "\n"
                        + "\nReseptgruppe:\t" + reseptGruppe + "\n"
                        + "\nInfo:\n\n" + info + "\n"
                        + "\n\t-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-\t\n\n";
	  }
}