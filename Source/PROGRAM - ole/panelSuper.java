


import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ole
 */
public class panelSuper extends JPanel{
   JList list;                    
   DocumentListener documentListener;
    JSplitPane sptop,spbottom;
    JPanel loggPanel;
    Logg logg;
    JTextArea logomraade;
    JScrollPane loggRull;
    	 JPanel feltPanel;
	 JPanel listPanel;
	 JPanel knappePanel;
         JPanel searchPanel;
     GridBagConstraints gbc ;
     Border feltBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
     Color blue;
     FilBehandler fil;
       ReseptRegister resepter;
     ReseptInfoVindu ReseptInfoVindu;
     InfoVindu InfoVindu;
     JFrame infoFrame;
    
    public panelSuper(){
            //reg = new ReseptRegister();
            feltPanel = new JPanel();
            listPanel = new JPanel();
            knappePanel = new JPanel();
            blue = new Color(153,153,255);
           
            gbc = new GridBagConstraints();
            searchPanel = new JPanel(new GridBagLayout());
            
            knappePanel.setBackground(blue);
            listPanel.setBackground(blue);
            searchPanel.setBackground(blue);


            logg = new Logg();
        
            logomraade  = new JTextArea();
	    logomraade.setText("");
            logomraade.setEditable(false);
	    logomraade.setVisible(true);
	    loggRull = new JScrollPane(logomraade);
            
        	    
	    Border rammer = BorderFactory.createEtchedBorder();
	    
	    feltPanel.setBorder(rammer);
	    knappePanel.setBorder(rammer);
	    listPanel.setBorder(rammer);
	    

       
            sptop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	   
            spbottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    
            sptop.setEnabled(true);
	    //sptop.setDividerSize(0);
	    sptop.setResizeWeight(0.4);
            spbottom.setEnabled(false);
	    spbottom.setDividerSize(0);
	    spbottom.setResizeWeight(0.8);
            loggPanel = new JPanel();
            loggPanel.setLayout(new BorderLayout());
            loggPanel.add(loggRull);
              
            
    }
    
	public void error(String s)
        {
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        }
    public void visReseptInfoVindu( Lege l ) 
        {
            if(!list.isSelectionEmpty())
            {
                
                	//System.out.println(l.getNavn());
                	//System.out.println("Knappen er selected");
		    	infoFrame = new JFrame("ReseptInfo-vindu");
		    	infoFrame.setLayout(new BorderLayout());
		    //	System.out.println("etter JFRAME");	
                        
                        ReseptInfoVindu = new ReseptInfoVindu(l);
                        //InfoVindu = new InfoVindu(l,"Lege");
		    	infoFrame.add(ReseptInfoVindu);
		    	infoFrame.setVisible(true);
		    	infoFrame.setSize(1100,600);
                
                        logomraade.append(logg.toString("Fant lege: " + l.toString()));
		}		
                else
                    error("Ingen lege er valgt");
        }
    public void visReseptInfoVindu( Pasient p ) 
        {
            if(!list.isSelectionEmpty())
            {
                
                	//System.out.println(l.getNavn());
                	//System.out.println("Knappen er selected");
		    	infoFrame = new JFrame("ReseptInfo-vindu");
		    	infoFrame.setLayout(new BorderLayout());
		    //	System.out.println("etter JFRAME");	
                        
                        ReseptInfoVindu = new ReseptInfoVindu(p);
		    	infoFrame.add(ReseptInfoVindu);
		    	infoFrame.setVisible(true);
		    	infoFrame.setSize(1100,600);
                
                        logomraade.append(logg.toString("Fant pasient: " + p.toString()));
		}		
                else
                    error("Ingen pasient er valgt");
        }
    public void visInfoVindu( Lege l ) 
        {
            if(!list.isSelectionEmpty())
            {
                        
                        InfoVindu = new InfoVindu(l,"Lege");
                        logomraade.append(logg.toString("Vis mer info om lege: " + l.toString()));
		}		
                else
                    error("Ingen lege er valgt");
        }
    public void visInfoVindu( Pasient p ) 
        {
            if(!list.isSelectionEmpty())
            {
                        InfoVindu = new InfoVindu(p,"Pasient");
                        logomraade.append(logg.toString("Vis mer info om pasient: " + p.toString()));
		}		
                else
                    error("Ingen pasient er valgt");
        }
    public void visInfoVindu( Medisin m ) 
        {
            if(!list.isSelectionEmpty())
            {
                InfoVindu = new InfoVindu(m,"Medisin");
                logomraade.append(logg.toString("Vis mer info om lege: " + m.toString()));
            }		
            else
                error("Ingen lege er valgt");
        }


    
    
}
