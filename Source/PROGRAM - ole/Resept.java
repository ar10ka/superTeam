/*
 

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
    private String setString;
    
    
    public Resept (int id, Lege l, Pasient p, Medisin m,int mengde, String la, String dato)
    {
        reseptID = id;
        lege = l;
        pasient = p;
        medisin = m;
        utskrevet = dato;
        mengdeMg = mengde;
        legeanvisning = la;
        setString = reseptID + " " +  utskrevet + " " + p.getFNavn() + " " + p.getENavn();
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
    public int getMengde()
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