


import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ole
 */
public class LoggTest extends JFrame{
    
    Logg logg;
    public LoggTest()
    {
        super("test");
        setSize(500, 500);
        logg = new Logg();
        
            getContentPane().add(logg);
    setVisible(true);
    }
}
