


import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

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
    
    public panelSuper(){
            
            feltPanel = new JPanel();
            listPanel = new JPanel();
            knappePanel = new JPanel();
           
            gbc = new GridBagConstraints();
            searchPanel = new JPanel(new GridBagLayout());


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
	    sptop.setResizeWeight(1);
            spbottom.setEnabled(false);
	    spbottom.setDividerSize(0);
	    spbottom.setResizeWeight(0.8);
            loggPanel = new JPanel();
            loggPanel.setLayout(new BorderLayout());
            loggPanel.add(loggRull);
            
                knappePanel.setBackground(Color.red);
                listPanel.setBackground(Color.cyan);    
            
    }
    
    
}
