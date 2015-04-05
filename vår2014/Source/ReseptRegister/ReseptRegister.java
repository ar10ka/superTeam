

import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ole
 */
public class ReseptRegister
{
    private List<Resept> reseptReg = new ArrayList<>();



    public boolean godkjentLege(Lege l, Medisin m)
    {
       for(char x :l.getReseptGruppe() )
            {
                if(m.getReseptGruppe() == x)
                    return true;
            }
       return false;
    }
    public void nyResept(Lege l, Pasient p, Medisin m)
    {

    }

    public void nyResept(Resept r)
    {

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



    //Liste over resepter pasienter med navn og fnr har blitt forskrevet, i tillegg oversikt innen kategori og i tillegg oversikt innen reseptgruppe
    public List<Resept> getResepterPasientNavn( Pasient p, String kat, char rgruppe)//Husk å ta getText() fra kategori og char fra rgruppe inn selvom det er 0/""
    {
        List<Resept> r = new ArrayList<>();
        for ( Resept x: reseptReg)
        {
                if(x.getLege().getFNr().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getKategori().equals(kat) && x.getMedisin().getReseptGruppe()==rgruppe  )
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
                if(x.getLege().getFNr().equals(p.getFNavn()) && x.getLege().getEtternavn().equals(p.getENavn()) && x.getMedisin().getKategori().equals(kat) && x.getMedisin().getReseptGruppe()==rgruppe  )
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




}



//Dette skal lagres:
//-dato for registrering av resept



//-Pasientdata for den pasienten resepten er skrevet ut til



//-Legedata for legen som skrev ut resepten

//-Hvilken medisin resepten er på

//-Mengde av medisin
//-Medisinens kategori
//-Legens anvisning (JTextArea) om bruk av medisinen.



/*
DETTE MÅ GJØRES (PROGRAM VINDUET)
-Når legen skal registrere resept, må han sjekkes om han er godkjent for den kategorien
dvs. at han skal finnes i lege-registeret og bevilling for angitt reseptgruppe
-Hvis pasienten ikke finnes i pasientregisteret må han registreres før registrering av resept
-Hvis det over er i orden kan medisinen utleveres/selges og resepten blir registrert i dette registeret
hvis ikke skal brukeren få melding om hva som var grunnen til dette.


*/

