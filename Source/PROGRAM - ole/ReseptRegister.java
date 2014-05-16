import java.io.Serializable;
import java.util.*;


/**
 *
 * @author Ole Bøe Andreassen - s188097
 */

//ReseptRegisterklasse
public class ReseptRegister implements Serializable
{
    private final List<Resept> reseptReg = new ArrayList<>();
    private final Logg logg = new Logg();
    private int reseptID=10000;
    
    //Sjekker om legen er godkjent for den medisinen
    public boolean godkjentLege(Lege l, Medisin m)
    {
       for(char x :l.getReseptGruppe() )
            {
                if(m.getReseptGruppe() == x)
                    return true;
            }
       return false;
    }
    
    //Oppretter nytt resept objekt og legger den inn i registeret
    public void nyResept( Lege l, Pasient p, Medisin m,String mengde, String la)
    {
        int id  = reseptID++;
    	String utskrevet = logg.getDate( "Resept " + id + " er opprettet");
        Resept r = new Resept(id,l,p,m,mengde,la, utskrevet);
        reseptReg.add(r);
    }
	public Resept finn (int id) {
		if(!reseptReg.isEmpty()) {
			for( Resept r: reseptReg) {
				if(r.getID()==(id)) {
					return r;
				}
			}
		}
		return null;
	}
        //Returnerer en tilfeldig resept
    public Resept finnRandom( )
  {
          if(!reseptReg.isEmpty())
          {
              
           int random = 0 + (int)(Math.random() * ((reseptReg.size() - 0) + 1));
            
              
            for( int i = 0; i < reseptReg.size();i++)
            {
                if(random == i)
                {
                    return reseptReg.get(i);
                }
                
            }
          }
          return null;
  }
    
    //Oversikt
    
    //MEDISIN:
    //Liste over alle leger som har skrevet ut denne medisinen
    public List<Lege> legerMedisin ( Medisin m  )
    {
        List<Lege> l = new ArrayList<>();
        for ( Resept x: reseptReg)
        {
            if(x.getMedisin() == m )
                l.add(x.getLege());
        }
        return l;// hvis null har ingen leger skrevet ut denne medisinen
    }
    public String legerMedisinString ( Medisin m )
    {
        String s = "";
        for ( Resept x: reseptReg)
        {
            if(x.getMedisin() == m )
                s+= x.getLege().getNavn()+ " " + x.getLege().getEtternavn() +"\n";
        }
        if (!s.equals(""))
            return s;
        else
            return "Ingen leger har skrevet ut denne medisinen!";
    }
    
    
    //Liste over alle pasienter som har blitt utskrevet denne medisinen
        public List<Pasient> pasienterMedisin ( Medisin m  )
    {
        List<Pasient> p = new ArrayList<>();
        for ( Resept x: reseptReg)
        {
            if(x.getMedisin() == m )
                p.add(x.getPasient());
        }
        return p;// hvis null har ingen pasienter blit utskrevet denne medisinen
    }
    public String pasienterMedisinString ( Medisin m )
    {
        String s = "";
        for ( Resept x: reseptReg)
        {
            if(x.getMedisin() == m )
                s+= x.getPasient().getFNavn()+ " " + x.getPasient().getENavn() +"\n";
        }
        if (!s.equals(""))
            return s;
        else
            return "Ingen pasienter har blitt utskrevet denne medisinen!";
    }
    
    //Returnerer en array av alle reseptene i registeret. Hvis tom returnerer den et String objekt
     public Object[] returnObjekt()
    {
       String[] emptyArray = {"Det er ingen resepter registrert ennå"};
       if(!reseptReg.isEmpty())  
          return reseptReg.toArray();
       else
          return emptyArray;
    }
    
    
    
    //RESEPT;
    //Liste over resepter lege/leger med navn x har skrevet ut, i tillegg kategori, i tillegg reseptgruppe
    public List<Resept> getResepterLegeNavn( Lege l, String kat, char rgruppe)//Husk å ta getText() fra kategori og char fra rgruppe inn selvom det er 0/""
    {
        List<Resept> r = new ArrayList<>();
        for ( Resept x: reseptReg)
        {
                if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()) && x.getMedisin().getKategori().equals(kat) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    r.add(x);
                else if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()) && x.getMedisin().getKategori().equals(kat))
                    r.add(x);
                else if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    r.add(x);
                else if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()))
                    r.add(x);
        }
        return r;
    }
        public String getResepterLegeNavnString( Lege l, String kat, char rgruppe)//Husk å ta getText() fra kategori og char fra rgruppe inn selvom det er 0/""
    {
        String s = "";
        for ( Resept x: reseptReg)
        {
                if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()) && x.getMedisin().getKategori().equals(kat) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    s += x.getID();
                else if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()) && x.getMedisin().getKategori().equals(kat))
                    s += x.getID();
                else if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    s += x.getID();
                else if(x.getLege().getNavn().equals(l.getNavn()) && x.getLege().getEtternavn().equals(l.getEtternavn()))
                    s += x.getID();
        }
        if (!s.equals(""))
            return s;
        else
            return "Ingen resepter har blitt skrevet av denne legen!";
    }
        
        //Returnerer en liste over resepeter skrevet ut av spesifik lege
        public List<Resept> getResepterLegeObject( Lege l)
        { 
        	List<Resept> r = new ArrayList<>();
        	
        	if(!reseptReg.isEmpty()){
                    for ( Resept x: reseptReg)
                    {
                       if(x.getLege().getlegeID() == l.getlegeID())
                       { 
                           r.add(x);
                       }
                    }
            	return r;
        	}
                else
                    return null;
        }
    

    
    //Liste over resepter pasienter med navn og fnr har blitt forskrevet, i tillegg oversikt innen kategori og i tillegg oversikt innen reseptgruppe
    public List<Resept> getResepterPasientNavn( Pasient p, String kat, char rgruppe)//Husk å ta getText() fra kategori og char fra rgruppe inn selvom det er 0/""
    {
        List<Resept> r = new ArrayList<>();
        for ( Resept x: reseptReg)
        {
                if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getKategori().equals(kat) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    r.add(x);
                else if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getKategori().equals(kat))
                    r.add(x);
                else if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    r.add(x);
                else if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()))
                    r.add(x);
        }
        return r;
    }
        public String getResepterPasientNavnString( Pasient p, String kat, char rgruppe)//Husk å ta getText() fra kategori og char fra rgruppe inn selvom det er 0/""
    {
        String s = "";
        for ( Resept x: reseptReg)
        {
                if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getKategori().equals(kat) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    s += x.getID();
                else if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getKategori().equals(kat))
                    s += x.getID();
                else if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getReseptGruppe()==rgruppe  )
                    s += x.getID();
                else if(x.getLege().getNavn().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()))
                    s += x.getID();                   
        }
        if (!s.equals(""))
            return s;
        else
            return "Ingen resepter har blitt skrevet av denne legen!";
    }
        
        
        //Returnerer en liste over resepeter skrevet ut av spesifik pasient
        public List<Resept> getResepterPasientObject( Pasient p)
        {
        	List<Resept> r = new ArrayList<>();
        	
        	if(reseptReg.isEmpty())
                    return null;
                else{
        		
        
                    for ( Resept x: reseptReg)
                    {
                       if(x.getPasient().getFNr().equals(p.getFNr()))
                       { 
                           r.add(x);
                       }
                    }
            	return r;
        	}
        }
        
        //Returnerer objekter som stemmer over ens med parameterene i metoden. Brukes for diverse søk i andre klasser
        public Object[] finnObjekt (String rId, String rDato, char rGruppe, 
                                   String lId, String lNavn, String lEnavn,
                                   String pId, String pNavn, String pEnavn,
                                   String mId,  String mNavn, String mKat) {
		Set<Resept> resepter = new HashSet<>();
		if(!reseptReg.isEmpty())
                {
                         
                    for( Resept r: reseptReg) {
                        if(rGruppe!=0)
                        {
                            //Har huket av reseptgruppe
                            if(        String.valueOf(r.getID()).toLowerCase().contains(rId.toLowerCase()) 
                                    && r.getDato().toLowerCase().contains(rDato.toLowerCase()) 
                                    && r.getMedisin().getReseptGruppe()== rGruppe
                                    && String.valueOf(r.getLege().getlegeID()).toLowerCase().contains(lId.toLowerCase())
                                    && r.getLege().getNavn().toLowerCase().contains(lNavn.toLowerCase())
                                    && r.getLege().getEtternavn().toLowerCase().contains(lEnavn.toLowerCase())
                                    && r.getPasient().getFNr().toLowerCase().contains(pId.toLowerCase())
                                    && r.getPasient().getFNavn().toLowerCase().contains(pNavn.toLowerCase())
                                    && r.getPasient().getENavn().toLowerCase().contains(pEnavn.toLowerCase())
                                    && r.getMedisin().getMedID().toLowerCase().contains(mId.toLowerCase())
                                    && r.getMedisin().getNavn().toLowerCase().contains(mNavn.toLowerCase())
                                    && r.getMedisin().getKategori().toLowerCase().contains(mKat.toLowerCase())
                                    ){
                                        resepter.add(r);
                            }
                            
                        }
                        else //Har ikke huket av Reseptgruppe
                        {
                            try{
                            if(        String.valueOf(r.getID()).toLowerCase().contains(rId.toLowerCase()) 
                                    && r.getDato().toLowerCase().contains(rDato.toLowerCase()) 
                                    
                                    && String.valueOf(r.getLege().getlegeID()).toLowerCase().contains(lId.toLowerCase())
                                    && r.getLege().getNavn().toLowerCase().contains(lNavn.toLowerCase())
                                    && r.getLege().getEtternavn().toLowerCase().contains(lEnavn.toLowerCase())
                                    && r.getPasient().getFNr().toLowerCase().contains(pId.toLowerCase())
                                    && r.getPasient().getFNavn().toLowerCase().contains(pNavn.toLowerCase())
                                    && r.getPasient().getENavn().toLowerCase().contains(pEnavn.toLowerCase())
                                    && r.getMedisin().getMedID().toLowerCase().contains(mId.toLowerCase())
                                    && r.getMedisin().getNavn().toLowerCase().contains(mNavn.toLowerCase())
                                    && r.getMedisin().getKategori().toLowerCase().contains(mKat.toLowerCase())
                                    ){
                                        resepter.add(r);
                            }
                        }
                        catch(NullPointerException e)
                                {
                                    System.out.println(r.getID());
                                }
                         }   
                    }
                    if (!resepter.isEmpty())
                        return resepter.toArray(); //Returnerer lista
                    else
                        return null; //Hvis lista er tom, returnerer null
                }
             return null;//Hvis ingen resepter er registrert
	}
        //Sletter resept, bestemt av parameteret
        public boolean slettResept(Resept r) {
		if(!reseptReg.isEmpty())
		{
			for(Resept x: reseptReg) {
				if(x.getID()==(r.getID())) {
					reseptReg.remove(x);
					return true;
				}
			}
		}
		return false;
	}
        public Object [] [] finnFraKategori (String kategori ) { // metoden som returnerer leger utifra byen
	   //String[] emptyArray = {"Ingen Leger er registrert i denne byen"};
	   
	   List<Resept> resepter = new ArrayList<>();
	   
	   if(!reseptReg.isEmpty() ) {
               
		   for(Resept r: reseptReg) {
                    if(r.getMedisin().getKategori().equals(kategori))
			   
			   resepter.add(r);
		   }
		  
		   Object[][] felter = new Object[resepter.size()][];
			   
			//antall = medisiner.size();  
			   
			   
			   
			  for (int i = 0; i < resepter.size(); i++) {
				   Resept r = resepter.get(i);
                                   
				   felter[i] = new Object[] {i+1,
						   r.getMedisin().getReseptGruppe(),
						   r.getMedisin().getKategori(),
                                                   r.getID(),
                                                   r.getDato()
						   
				   } ;
				
				  //return felter;
			   }
			  return felter;
			   
		   }

	   	return null;
	   }
        //Returnerer det siste objektet som ble lagt til i registeret
        public Resept getLatestAdded()
        {
            return reseptReg.get(reseptReg.size()-1);
        }
} 