

package ReseptRegister;

import Lege.Lege;
import Lege.LegeRegister;
import Medisin.Medisin;
import Medisin.MedisinRegister;
import pasient.PasientRegister;
import pasient.Pasient;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ole
 */
public class ReseptRegister 
{
    MedisinRegister medReg;
    PasientRegister pasReg;
    LegeRegister legReg;
    private String medNavn,medKat;
    private int mengde;
    private char rGruppe;
    private JTextArea legeAnv;

    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'kl.' HH:mm");
    private Date date = new Date();
    private String dato = dateFormat.format(date);


    
    

    
    public ReseptRegister()
    {
     
    }
    
    public void setLegeAnvisning(JTextArea la)
    {
        legeAnv = la;
    }

    public String PasientData(String n, String pnr)
    {
        Pasient p = pasReg.finn(n, pnr);
        return p.toString();
    }
    public String LegeData(String n, String pnr)
    {
        Lege l = legReg.finnOgReturner(n, pnr);
        return l.toString();
    }
    public void setMedisinData(String n, int nr)
    {
        Medisin m = medReg.finn(n, nr);
        medNavn = m.getNavn();
        rGruppe = m.getReseptGruppe();
        medKat = m.getKategori();
    }
    public void setMengdeMedisin(int nr)
    {
        mengde = nr;
        
    }
    
    public boolean godkjentLege(Lege l, Medisin m)
    {
       for(char x :l.getReseptGruppe() )
            {
                if(m.getReseptGruppe() == x)
                    return true;
            }
       return false;
    }
    public String nyResept(Lege l, Pasient p, Medisin m)
    {
        
        if(legReg.finnes(l.getFNr()))
        {
            if(godkjentLege(l,m))
            {
                if(pasReg.finnes(p.getFNr()))
                {
                    return l.toString();
                }
                else
                {
                    //Kall på metode som registrerer pasienten
                    return "Pasienten er ikke registrert";
                }
            }    
            else
                return "Lege er ikke godkjent";
        } 
        else
            return "Lege er ikke registrert";

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
DETTE MÅ GJØRES
-Når legen skal registrere resept, må han sjekkes om han er godkjent for den kategorien
dvs. at han skal finnes i lege-registeret og bevilling for angitt reseptgruppe
-Hvis pasienten ikke finnes i pasientregisteret må han registreres før registrering av resept
-Hvis det over er i orden kan medisinen utleveres/selges og resepten blir registrert i dette registeret
hvis ikke skal brukeren få melding om hva som var grunnen til dette.


*/

