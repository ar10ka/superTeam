package Program;




import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logg {

        private String logg = "";
        private String date = "";
        
        public String toString (String tekst) {
        	
            
                Date today = new Date();
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[d MM yyyy 'kl.' HH:mm:ss]    ");
        	date = DATE_FORMAT.format(today);
                String output = date + " " + tekst;
                regLogg(output);
        	return output;
        }
        
        public String getDate(String s)
        {
            toString(s);
            return date;
        }
        
        private void regLogg(String output)
        {
            try
            {
                lastInnFil();
            }
            
	    catch (IOException ex)
            {
                ex.printStackTrace();
            }
                
                    logg += output + "\n";
            
            try
            {
               lagreFil();
            }

            catch (IOException ex)
            {
               ex.printStackTrace();
            }
        }
       
        private void lastInnFil() throws IOException
	  {
	    try
	    {
	      FileInputStream fileHandle = new FileInputStream("logg.txt");
	      ObjectInputStream in = new ObjectInputStream(fileHandle);
	      logg = (String) in.readObject();
	    }
	    catch (FileNotFoundException ex)
	    {
	      System.out.println("Lager ny lagringsfil");
	    }
	    catch (ClassNotFoundException ex)
	    { 
	    	 System.out.println("CLASS not found Exception");
	      ex.printStackTrace();
	    }
	    catch (EOFException ex)
	    {
	      System.out.println("Ferdig lastet!");
	    }
	  }
	
	  private void lagreFil() throws IOException
	  {
	    try
	    {
	      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("logg.txt"));
	      out.writeObject(logg);
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
}
