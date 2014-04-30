/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReseptRegister;

import Lege.Lege;
import Medisin.Medisin;
import pasient.Pasient;
import java.io.Serializable;
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author Ole
 */
public class Resept implements Serializable 
{
    
    private Lege lege;
    private Pasient pasient;
    private Medisin medisin;
    private String utskrevet;
    private JTextArea legeanvisning;
    private int mengdeMg;
    
    
    public Resept ()
    {
        
    }
}
