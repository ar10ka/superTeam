
/*

Studentnr: s198757
Navn: Marius Baltramaitis


Klasse: Dataingeniør

*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.*;
import javax.swing.ListSelectionModel;


public class ReseptInfoVindu extends panelSuper {
	
	ActionListener lytter;
	private JButton kVisInfo;

        
        private final JTextField datofelt,idfelt,lENavn,lFNavn,lId,pENavn,pFNavn,pFnr,mNavn,mRgruppe,mId,mengdefelt;
        private final JTextArea legeanvfelt;
        private JButton kVisPasient,kVisMedisin,kVisLege;
        private Resept resept;
        private JScrollPane legeanv;
	
        //KLASSE SOM VISER INFORMASJON OM EN RESEPT PÅ ENTEN ET LEGE ELLER ET PASIENT-OBJEKT
	public ReseptInfoVindu( final Object o) {
            
            fil = new FilBehandler();
            reseptRegister = new ReseptRegister();
            
            //Laster inn reseptRegisteret fra fil
            try
            {
              reseptRegister = fil.lastInnFilResept("ReseptLagring");
            }	    
	    catch (FileNotFoundException ex)
	    {
	      System.out.println("Lager ny lagringsfil");
	    }
	    catch (EOFException ex)
	    {
	      System.out.println("Ferdig lastet!");
	    }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            setLayout(new BorderLayout());
            setBackground(Color.DARK_GRAY);
            
            //INITIALISERER DATAFELT
            kVisInfo = new JButton("Vis Info");
            kVisPasient = new JButton("Vis Pasient");
            kVisMedisin = new JButton("Vis Medisin");
            kVisLege = new JButton("Vis Lege");
            datofelt = new JTextField(10);
            idfelt = new JTextField(10);
            lENavn = new JTextField(10);
            lFNavn = new JTextField(10);
            lId = new JTextField(10);
            pENavn = new JTextField(10);
            pFNavn = new JTextField(10);
            pFnr = new JTextField(10);
            mNavn = new JTextField(10);
            mRgruppe = new JTextField(10);
            mId = new JTextField(10);
            mengdefelt = new JTextField(10);
            legeanvfelt = new JTextArea(5,10);
            legeanv = new JScrollPane(legeanvfelt);
            
            //SIDEN VI BARE SKAL VISE INFO TRENGER IKKE FELTENE VÆRE REDIGERBARE
            datofelt.setEditable(false);
            idfelt.setEditable(false);
            lENavn.setEditable(false);
            lFNavn.setEditable(false);
            lId.setEditable(false);
            pENavn.setEditable(false);
            pFNavn.setEditable(false);
            pFnr.setEditable(false);
            mNavn.setEditable(false);
            mRgruppe.setEditable(false);
            mId.setEditable(false);
            mengdefelt.setEditable(false);
            legeanvfelt.setEditable(false);
            
            
            
            //OPPRETTER LYTTER-OBJEKT
            lytter = new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                         if (e.getSource() == kVisInfo)
                             visResept(getSelectedObject());
                         else if (e.getSource() == kVisLege)
                         {
                             if(getSelectedObject() != null)
                                 visInfoVindu(resept.getLege());
                             else
                                 error("Ingen resept er valgt");
                         }			
                         else if (e.getSource() == kVisMedisin)
                         {
                             if(getSelectedObject() != null)
                                visInfoVindu(resept.getMedisin());
                             else
                                 error("Ingen resept er valgt");
                         }			
                         else if (e.getSource() == kVisPasient)
                         {
                             if(getSelectedObject() != null)
                                visInfoVindu(resept.getPasient());
                             else
                                error("Ingen resept er valgt");
                         }			
                        }
                    };
            //KNAPPENE FÅR LYTTEROBJEKT TILSATT
            kVisInfo.addActionListener(lytter);
            kVisPasient.addActionListener(lytter);
            kVisMedisin.addActionListener(lytter);
            kVisLege.addActionListener(lytter);

            knappePanel.setLayout(new BorderLayout());
            knappePanel.add(kVisInfo, BorderLayout.LINE_END);
            
            list = new JList();
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setLayoutOrientation(JList.VERTICAL);
            JScrollPane scrollpane = new JScrollPane(list);
            listPanel.setLayout(new BorderLayout());
            listPanel.add(searchPanel, BorderLayout.PAGE_START);
            listPanel.add(scrollpane, BorderLayout.CENTER);
            listPanel.add(knappePanel, BorderLayout.PAGE_END);
            addFeltPanel();
            sptop.add(listPanel);
            sptop.add(feltPanel);
            add(sptop, BorderLayout.CENTER);
            
            String [] tomListe = {"Inger reseptRegister er registrert"};
            if(visListe(o) !=null)
            {
                list.setListData(visListe(o));
            }
            else
                list.setListData(tomListe);
            
            }
            
            private Resept getSelectedObject() 
            {	
                    if(!list.isSelectionEmpty()) {
                            Resept r = (Resept)list.getSelectedValue();
                            return r;
                    }
                    else 
                        return null;
            }
            //Fyller alle feltene med informasjon fra resept-objektet
            private void visResept( Resept r ) 
            {
                if(getSelectedObject() != null)
                {        
                        resept = getSelectedObject();
                        datofelt.setText(r.getDato());
                        idfelt.setText(r.getID()+"");
                        lENavn.setText(r.getLege().getEtternavn());
                        lFNavn.setText(r.getLege().getNavn());
                        lId.setText(r.getLege().getlegeID()+"");
                        pENavn.setText(r.getPasient().getENavn());
                        pFNavn.setText(r.getPasient().getFNavn());
                        pFnr.setText(r.getPasient().getFNr());
                        mNavn.setText(r.getMedisin().getNavn());
                        mRgruppe.setText(r.getMedisin().getReseptGruppe()+"");
                        mId.setText(r.getMedisin().getMedID());
                        mengdefelt.setText(r.getMengde()+"");      
                        legeanvfelt.setText(r.getLegeAnvisning());      

                        logomraade.append(logg.toString("Fant resept: " + r.toString()));
                    }		
                    else
                        error("Ingen resept er valgt");
            }

            //Setter opp layout for feltpanelet
             private void addFeltPanel()
            {   
                     feltPanel.setLayout( new GridBagLayout() );

                     gbc.anchor = GridBagConstraints.NORTH;
                     gbc.fill = GridBagConstraints.HORIZONTAL;
                     gbc.insets = new Insets(5,10,5,15);
                     gbc.ipady = 20;

                     gbc.weightx = 0.5;
                     gbc.weighty=1;

                     gbc.gridy=0;
                     gbc.gridx=2;

                     //feltPanel.add(new JLabel("RESEPT - INFORMASJON"),gbc);

                     gbc.gridx=0;
                     gbc.gridy++;

                     feltPanel.add(new JLabel("Skrevet ut:"), gbc);

                     gbc.gridx++;
                     //gbc.gridwidth =1;
                     feltPanel.add(datofelt, gbc);
                     //gbc.gridwidth=1;
                     gbc.gridx++;
                     feltPanel.add(new JLabel("Reseptnummer:"), gbc);
                     gbc.gridx++;
                     feltPanel.add(idfelt, gbc);


                     gbc.gridy++;
                     gbc.gridx=0;

                     feltPanel.add(new JLabel("Lege:"), gbc);
                     gbc.gridx++;
                     feltPanel.add(lENavn,gbc);
                     gbc.gridx++;
                     feltPanel.add(lFNavn,gbc);
                     gbc.gridx++;
                     feltPanel.add(lId,gbc);
                     gbc.gridx++;
                     feltPanel.add(kVisLege,gbc);


                     gbc.gridy++;
                     gbc.gridx=0;
                     feltPanel.add(new JLabel("Pasient:"), gbc);
                     gbc.gridx++;
                     feltPanel.add(pENavn,gbc);
                     gbc.gridx++;
                     feltPanel.add(pFNavn,gbc);
                     gbc.gridx++;
                     feltPanel.add(pFnr,gbc);
                     gbc.gridx++;
                     feltPanel.add(kVisPasient,gbc);



                     gbc.gridy++;
                     gbc.gridx=0;
                     feltPanel.add(new JLabel("Medisin:"), gbc);
                     gbc.gridx++;
                     feltPanel.add(mNavn,gbc);
                     gbc.gridx++;
                     feltPanel.add(mRgruppe,gbc);
                     gbc.gridx++;
                     feltPanel.add(mId,gbc);
                     gbc.gridx++;
                     feltPanel.add(kVisMedisin,gbc);


                     gbc.gridy++;
                     gbc.gridx=0;
                     feltPanel.add(new JLabel("ANTALL/MENGDE"), gbc);
                     gbc.gridx++;
                     feltPanel.add(mengdefelt,gbc);


                     gbc.gridy++;
                     gbc.gridx=0;
                     feltPanel.add(new JLabel("LEGEANVISNING"), gbc);
                     gbc.gridx++;
                     gbc.gridwidth=4;
                     feltPanel.add(legeanv,gbc);

                feltPanel.setVisible(true);
          }
             //Metoden returnerer en array av objekter som blir brukt til å sette 
             //opp listen med objekter av typen Lege eller Pasienet, 
             //avhengig av hva slags objekt som blir sendt til metoden
            public Object[] visListe(Object o) {

                    Lege l;
                    Pasient p;

                    List<Object> reseptListe = new ArrayList<>();

                    if (o instanceof Lege ) 
                    {	
                        l = (Lege)o;
                            if(reseptRegister.getResepterLegeObject(l)!= null) 
                            {
                                for(Resept x: reseptRegister.getResepterLegeObject(l)) 
                                {
                                        String s = x.getID() + " " + x.getPasient().getFNavn() + " " + x.getPasient().getENavn() + " " + x.getDato();
                                        x.setToString(s);
                                        reseptListe.add(x);
                                }
                            }
                            else
                                return null;
                        }

                    if (o instanceof Pasient)
                    {
                        p = (Pasient)o;
                            if(reseptRegister.getResepterPasientObject(p)!= null) 
                            {
                               for(Resept x: reseptRegister.getResepterPasientObject(p)) 
                               {
                                        String s = x.getID() + " " + x.getLege().getNavn() + " " + x.getLege().getEtternavn() + " "
                                        + x.getLege().getlegeID() + " " + x.getDato();

                                        x.setToString(s);
                                        reseptListe.add(x);
                                }
                            }
                            else
                                return null;



                    }

               return reseptListe.toArray();
            }
    }