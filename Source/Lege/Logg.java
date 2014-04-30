

import java.text.SimpleDateFormat;
import java.util.Date;


public class Logg {


        public static String toString (String tekst) {
        	Date today = new Date();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[d MM yyyy 'kl.' HH:mm:ss]    ");
        	String date = DATE_FORMAT.format(today);
        	return (date + " " + tekst);
        }

}
