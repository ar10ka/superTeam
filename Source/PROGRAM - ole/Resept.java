import java.io.Serializable;

/**
 *
 * @author Arthika Surendran - s198757
 */

//RESEPTKLASSEN  -  Lagrer all informasjon en resept har.
public class Resept implements Serializable
{
    private int reseptID;
    private final Lege lege;
    private final Pasient pasient;
    private final Medisin medisin;
    private final String utskrevet,legeanvisning,mengdeMg;
    private String setString;

    public Resept (int id, Lege l, Pasient p, Medisin m,String mengde, String la, String dato)
    {
        reseptID = id;
        lege = l;
        pasient = p;
        medisin = m;
        utskrevet = dato;
        mengdeMg = mengde;
        legeanvisning = la;           
        setString = reseptID + "   " +  utskrevet + " " + p.getFNavn() + " " + p.getENavn();
    }
    public int getID()
    {
        return reseptID;
    }
    public void setID(int id)
    {
        reseptID = id;
    }
    public Lege getLege()
    {
        return lege;
    }
    public Pasient getPasient()
    {
        return pasient;
    }
    public Medisin getMedisin()
    {
        return medisin;
    }
    public String getDato()
    {
        return utskrevet;
    }
    public String getMengde()
    {
        return mengdeMg;
    }
    public String getLegeAnvisning()
    {
        return legeanvisning;
    }
    public void setToString(String s) {
    	setString = s;
    }
    @Override
    public String toString() {
    	return setString;
    }
}