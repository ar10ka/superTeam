


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
    Logg logg;
    JTextArea logomraade;
    JScrollPane loggRull;
    JPanel feltPanel,listPanel,knappePanel,searchPanel,loggPanel;
    GridBagConstraints gbc ;
    Border feltBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    FilBehandler fil;
    ReseptRegister reseptRegister;
    PasientRegister pasientRegister;
    MedisinRegister medisinRegister;
    LegeRegister legeRegister;
    ReseptInfoVindu ReseptInfoVindu;
    InfoVindu InfoVindu;
    JFrame infoFrame;
    Color greyWhite,blue,grey;
    Border lineBorder;
    
    //KONSTRUKTØR
    public panelSuper(){
            blue = new Color(153,153,255);
            greyWhite = new Color(224,224,224);
            grey = new Color(128,128,128);
            lineBorder = BorderFactory.createLineBorder(grey);
            feltPanel = new JPanel();
            listPanel = new JPanel();
            knappePanel = new JPanel();
            searchPanel = new JPanel(new GridBagLayout());
            logg = new Logg();
            gbc = new GridBagConstraints();
            knappePanel.setBackground(blue);
            listPanel.setBackground(blue);
            searchPanel.setBackground(blue);
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
        
        //Metode som tar imot en melding som man ønsker å få opp i et errorvindu
	public void error(String s)
        {
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        }
        //Metode som lager et nytt vundu med utvidet informasjon om resepter på en bestemt lege
        public void visReseptInfoVindu( Lege l ) 
        {
            if(!list.isSelectionEmpty())
            {
		    	infoFrame = new JFrame("ReseptInfo-vindu");
		    	infoFrame.setLayout(new BorderLayout());
                        ReseptInfoVindu = new ReseptInfoVindu(l);
		    	infoFrame.add(ReseptInfoVindu);
		    	infoFrame.setVisible(true);
		    	infoFrame.setSize(1100,600);
                        logomraade.append(logg.toString("Fant lege: " + l.toString()));
		}		
                else
                    error("Ingen lege er valgt");
        }
        //Metode som lager et nytt vundu med utvidet informasjon om resepter på en bestemt pasient
        public void visReseptInfoVindu( Pasient p ) 
            {
                if(!list.isSelectionEmpty())
                {
                            infoFrame = new JFrame("ReseptInfo-vindu");
                            infoFrame.setLayout(new BorderLayout());
                            ReseptInfoVindu = new ReseptInfoVindu(p);
                            infoFrame.add(ReseptInfoVindu);
                            infoFrame.setVisible(true);
                            infoFrame.setSize(1100,600);
                            logomraade.append(logg.toString("Fant pasient: " + p.toString())+"\n");
                    }		
                    else
                        error("Ingen pasient er valgt");
            }
        //Disse metodene viser info om enten lege,pasient eller medisin fra resepten du har valgt i vinduet
        public void visInfoVindu( Lege l ) 
            {        
                            InfoVindu = new InfoVindu(l,"Lege");
                            logomraade.append(logg.toString("Vis mer info om lege: " + l.toString()));
            }
        public void visInfoVindu( Pasient p ) 
            {
                            InfoVindu = new InfoVindu(p,"Pasient");
                            logomraade.append(logg.toString("Vis mer info om pasient: " + p.toString())+"\n");
            }
        public void visInfoVindu( Medisin m ) 
            {
                    InfoVindu = new InfoVindu(m,"Medisin");
                    logomraade.append(logg.toString("Vis mer info om Medisin: " + m.toString())+"\n");
            }
}
