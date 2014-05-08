/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Program;

import java.io.*;

/**
 *
 * @author Ole
 */
public class FilBehandler {
       
  
    private final Logg logg;

    
    

        
        
    public FilBehandler()
    {
        logg = new Logg();  
    }
    
  public void lagreFil(ReseptRegister o, String filnavn) throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filnavn + ".dta"));
      out.writeObject(o);
      System.out.println(logg.toString("Lagret!"));
    }
    catch (FileNotFoundException ex)
    {
      ex.printStackTrace();
    }
  }
  public void lagreFil(LegeRegister o, String filnavn) throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filnavn + ".dta"));
      out.writeObject(o);
      System.out.println(logg.toString("Lagret!"));
    }
    catch (FileNotFoundException ex)
    {
      ex.printStackTrace();
    }
  }
  public void lagreFil(PasientRegister o, String filnavn) throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filnavn + ".dta"));
      out.writeObject(o);
      System.out.println(logg.toString("Lagret!"));
    }
    catch (FileNotFoundException ex)
    {
      ex.printStackTrace();
    }
  }
  public void lagreFil(MedisinRegister o, String filnavn) throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filnavn + ".dta"));
      out.writeObject(o);
      System.out.println(logg.toString("Lagret!"));
    }
    catch (FileNotFoundException ex)
    {
      ex.printStackTrace();
    }
  }

  public LegeRegister lastInnFilLege(String filnavn) throws IOException
  {System.out.println(logg.toString("Lastet inn!"));
    LegeRegister lege = new LegeRegister();
    System.out.println(logg.toString("før try!"));
    try
    {
      System.out.println(logg.toString("før fileinput!"));
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
      System.out.println(logg.toString("før objectinput"));
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      System.out.println(logg.toString("Lastet inn!"));
      lege = (LegeRegister) in.readObject(); 

    }
    catch (FileNotFoundException ex)
    {
      System.out.println(logg.toString("Lager ny lagringsfil"));
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    catch (EOFException ex)
    {
      System.out.println(logg.toString("Ferdig lastet lagringsfil!"));
    }
    return lege;
  }
  public PasientRegister lastInnFilPasient(String filnavn) throws IOException
  {
      PasientRegister pasient = new PasientRegister();
    try
    {
      
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      System.out.println(logg.toString("Lastet inn!"));
 
      pasient = (PasientRegister) in.readObject();
      

    }
    catch (FileNotFoundException ex)
    {
      System.out.println(logg.toString("Lager ny lagringsfil"));
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    catch (EOFException ex)
    {
      System.out.println(logg.toString("Ferdig lastet lagringsfil!"));
    }
    return pasient;
  }
  public MedisinRegister lastInnFilMedisin(String filnavn) throws IOException
  {
      MedisinRegister medisin = new MedisinRegister();
    try
    {
      
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      System.out.println(logg.toString("Lastet inn!"));

    
            medisin = (MedisinRegister) in.readObject(); 

    }
    catch (FileNotFoundException ex)
    {
      System.out.println(logg.toString("Lager ny lagringsfil"));
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    catch (EOFException ex)
    {
      System.out.println(logg.toString("Ferdig lastet lagringsfil!"));
    }
    return medisin;
  }
  public ReseptRegister lastInnFilResept(String filnavn) throws IOException
  {
      ReseptRegister resept = new ReseptRegister();
    try
    {
      
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      System.out.println(logg.toString("Lastet inn!"));

            resept = (ReseptRegister) in.readObject();
  
    }
    catch (FileNotFoundException ex)
    {
      System.out.println(logg.toString("Lager ny lagringsfil"));
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    catch (EOFException ex)
    {
      System.out.println(logg.toString("Ferdig lastet lagringsfil!"));
    }
    return resept;
  }

    
    
    
    
}
