/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.io.Serializable;
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author Ole
 */
public class Resept implements Serializable
{
    private int reseptID;
    private Logg logg;
    private Lege lege;
    private Pasient pasient;
    private Medisin medisin;
    private String utskrevet;
    private String legeanvisning;
    private int mengdeMg;
    
    
    public Resept (int id, Lege l, Pasient p, Medisin m,int mengde, String la)
    {
        reseptID = id;
        lege = l;
        pasient = p;
        medisin = m;
        utskrevet = logg.getDate( "Resept " + reseptID + " er opprettet");
        mengdeMg = mengde;
        legeanvisning = la;
    }
    
    public int getID()
    {
        return reseptID;
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
    public int getMengde()
    {
        return mengdeMg;
    }
    public String getLegeAnvisning()
    {
        return legeanvisning;
    }
}
