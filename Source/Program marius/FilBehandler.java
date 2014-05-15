
import java.io.*;
import java.util.*;


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
  public void lagreFil(String o, String filnavn) throws IOException
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
  {
    LegeRegister lege = new LegeRegister();
    try
    {
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
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
  public String lastInnFil(String filnavn) throws IOException
  {
      String s= "";
    LegeRegister lege = new LegeRegister();
    try
    {
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      System.out.println(logg.toString("Lastet inn!"));
      s = (String) in.readObject(); 

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
    return s;
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
