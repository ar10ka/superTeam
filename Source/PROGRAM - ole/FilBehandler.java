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
    private Object b; 
    
    

        
        
    public FilBehandler()
    {
        logg = new Logg();  
    }
    
  public void lagreFil(Object o, String filnavn) throws IOException
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

  public Object lastInnFil(Object o, String filnavn) throws IOException
  {
    try
    {
      
      FileInputStream fileHandle = new FileInputStream(filnavn + ".dta");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      System.out.println(logg.toString("Lastet inn!"));
      b = (Object) in.readObject();
  
    }
    catch (FileNotFoundException ex)
    {
      System.out.println(logg.toString("Lager ny lagringsfil"));
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("feilen");
      ex.printStackTrace();
    }
    catch (EOFException ex)
    {
      System.out.println(logg.toString("Ferdig lastet lagringsfil!"));
    }
    return o;
  }

    
    
    
    
}
