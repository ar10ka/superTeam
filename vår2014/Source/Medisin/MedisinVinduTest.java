/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class MedisinVinduTest extends JFrame
{
  JFormattedTextField medID;
  JTextField 	medNavn;
  JButton 		kNyMedisin, kSlettMedisin, kVisAlt, kVisMedisin;
  JRadioButton 	radioA, radioB,radioC;
  JTextArea		tekstområde,medInfo;
  JSpinner medKategori;


  MaskFormatter medIDformatter;
  private MedisinRegister bibliotek;
  private Lytter sensor;
  public static final int _A = 1;
  public static final int _B  = 2;
  public static final int _C  = 3;
  private Logg logg;


  public MedisinVinduTest()//konstruktør
  {
    super("MEDISIN - REGISTERET");

    try{
          this.medIDformatter = new MaskFormatter("U##U U##");
    }
    catch (ParseException ex) {
         ex.printStackTrace();
      }


    bibliotek  = new MedisinRegister();
    logg = new Logg();
    try
    {
      lastInnFil();
    }

    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    radioA = new JRadioButton("A");
    radioB = new JRadioButton("B");
    radioC = new JRadioButton("C");

    ButtonGroup radioGruppe = new ButtonGroup();
    radioGruppe.add(radioA);
    radioGruppe.add(radioB);
    radioGruppe.add(radioC);

    //MEDISIN INPUT FORMATTERING
    medID = new JFormattedTextField(medIDformatter);
    medID.setColumns(14);

    medNavn    = new JTextField(14);

    // KATEGORI RULLEVINDU
    SpinnerListModel listModel = new SpinnerListModel(getKategori());
    medKategori    = new JSpinner(listModel);
    Dimension d = medKategori.getPreferredSize();
    d.width = 100;
    medKategori.setPreferredSize(d);
    medKategori.setValue( getKategori().get(getKategori().size()-1));

    medInfo    = new JTextArea(15,30);
    medInfo.setEditable(true);
    JScrollPane rulle2 = new JScrollPane(medInfo);

    kNyMedisin      = new JButton("Reg medisin");
    kSlettMedisin   = new JButton("Slett medisin");
    kVisMedisin     = new JButton("Vis medisin");
    kVisAlt      = new JButton("Vis alle");

    tekstområde  = new JTextArea(15, 55);
    tekstområde.setEditable(false);
    JScrollPane rulle = new JScrollPane(tekstområde);


	Container c   = getContentPane();
    c.setLayout( new FlowLayout() );
    c.add(radioA);
    c.add(radioB);
    c.add(radioC);
    c.add(new JLabel("Medisin ID:"));
    c.add(medID);
    c.add(new JLabel("Medisin navn:"));
    c.add(medNavn);
    c.add(new JLabel("Medisin kategori:"));
    c.add(medKategori);
       c.add(new JLabel("Medisin info:"));
       c.add(rulle2);
    c.add(kNyMedisin);
    c.add(kSlettMedisin);
    c.add(kVisMedisin);
    c.add(kVisAlt);
    c.add(rulle);

    setSize(850, 500);
    setVisible(true);


    sensor = new Lytter();


    kNyMedisin.addActionListener(sensor);

    kSlettMedisin.addActionListener(sensor);

    kVisMedisin.addActionListener(sensor);
    kVisAlt.addActionListener(sensor);

    addWindowListener(new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
        	try
        	{
               lagreFil();
               System.out.println(logg.toString("Lagret!"));
            }

            catch (IOException ex)
            {
               ex.printStackTrace();
            }
            System.exit(0);
         }
     });
  }//slutt på konstruktør

 private void lagreFil() throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MedisinLagring.txt"));
      out.writeObject(bibliotek);
      System.out.println(logg.toString("Lagret!"));
    }
    catch (FileNotFoundException ex)
    {
      ex.printStackTrace();
    }
  }

  private void lastInnFil() throws IOException
  {
    try
    {
      FileInputStream fileHandle = new FileInputStream("MedisinLagring.txt");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      bibliotek = (MedisinRegister) in.readObject();
      System.out.println(logg.toString("Lastet inn!"));

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
  }

  private class Lytter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() == kNyMedisin)
      {
          nyMedisin();
      }
      else if (e.getSource() == kSlettMedisin)
      {
        slettMedisin();
      }
      else if (e.getSource() == kVisMedisin)
      {
        visMedisin();
      }
      else if (e.getSource() == kVisAlt)
      {
        System.out.print(medKategori.getValue().toString());
        visAlt();
      }
    }
  }

public static List<String> getKategori()
{
   String[] arr =
   {
       "Hypnotikum","Antibiotika","Sedativum",
       "Hemodialysekonsentrat","Glaukommiddel",
       "Adrenergikum","Progestogen"

   };
   List<String> k = Arrays.asList(arr);
   Collections.sort(k);
   Collections.reverse(k);
   return k;
}


public char aktivRadio()
  {
    if(radioA.isSelected())
      return 'A';
    if (radioB.isSelected())
      return 'B';
     if (radioC.isSelected())
      return 'C';

         tekstområde.setText("Huk av for Firma eller Medisin!");
         return 0;


  }

  public boolean feltFylt()
  {
      return aktivRadio() != 0
              && !medID.getText().equals("")
              && !medNavn.getText().equals("")
              && !medInfo.getText().equals("")
              && !medKategori.getValue().toString().equals("");
  }

  public void nyMedisin()
  {
    try
    {
      String id = medID.getText();
      String navn = medNavn.getText();
      char rbStatus = aktivRadio();
      String info = medInfo.getText();
      String k = medKategori.getValue().toString();

      if ( feltFylt() )
      {
        Medisin medisin = new Medisin(id, navn, info, k, rbStatus);
        if (!bibliotek.finnes(medisin) )
        {
            System.out.print("Finnes medisin?");
            bibliotek.settInnNy(medisin);
            tekstområde.setText(logg.toString("Medisin lagt til"));
        }
        else
            tekstområde.setText("Medisin allerede lagt til");
      }
      else
        tekstområde.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }




  public void slettMedisin()
  {
    try
    {
      String id = medID.getText();
      String navn = medNavn.getText();
      char rbStatus = aktivRadio();
      String info = medInfo.getText();
      String k = medKategori.getValue().toString();


      if ( !id.equals("") )
      {
        Medisin medisin = new Medisin(id, navn, info, k, rbStatus);
        if (bibliotek.finnes(medisin) )
            {
                if(bibliotek.fjern(medisin))
                    tekstområde.setText(logg.toString("Medisin fjernet!"));
            }
        else
           tekstområde.setText("Medisin finnes ikke!");

      }
      else
        tekstområde.setText("Fyll ut medisin ID!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut medisin ID!");
    }
  }



  public void visMedisin()
  {
     try
    {
      String id = medID.getText();
      String navn = medNavn.getText();


      if ( !id.equals(id) || !navn.equals(navn)  )
      {
        String temp = "Viser Medisin:\n" + bibliotek.finn(navn, id).toString();
  	tekstområde.setText(logg.toString(temp));
      }
      else
        tekstområde.setText("Fyll ut feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }

  public void visAlt()
  {
    tekstområde.setText(logg.toString(bibliotek.toString()));
  }
}

