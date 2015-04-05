/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class PasientVinduTest extends JFrame
{
    JFormattedTextField fNr;
  JTextField 	 fNavn, eNavn, adresse;
  JButton 		kNyPasient, kSlettPasient, kVisAlt, kVisPasient;
  JRadioButton 	radioMann, radioKvinne;
  JTextArea		tekstområde;

  MaskFormatter fNrformatter;

  private PasientRegister bibliotek;
  private char gender;
  private Lytter sensor;
  public static final int _PERSON = 1;
  public static final int _FIRMA  = 2;
  private final Logg logg;


  public PasientVinduTest()//konstruktør
  {
    super("PASIENT - REGISTERET");
      try {
          this.fNrformatter = new MaskFormatter("###### #####");
      } catch (ParseException ex) {
         ex.printStackTrace();
      }

    logg = new Logg();
    bibliotek  = new PasientRegister();

    try
    {
      lastInnFil();
    }

    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    radioMann  = new JRadioButton("Mann");
    radioKvinne   = new JRadioButton("Kvinne");
    ButtonGroup radioGruppe = new ButtonGroup();
    radioGruppe.add(radioMann);
    radioGruppe.add(radioKvinne);


      //For å gjøre det mer oversiktelig i input så kommer det et blankt tegn etter de første 6 tallene

    fNr = new JFormattedTextField(fNrformatter);
    fNr.setColumns(14);

    fNavn    = new JTextField(14);
    eNavn = new JTextField(14);
    adresse = new JTextField(14);

    kNyPasient      = new JButton("Reg pasient");
    kSlettPasient   = new JButton("Slett pasient");
    kVisPasient     = new JButton("Vis pasient");
    kVisAlt      = new JButton("Vis alle");

	tekstområde  = new JTextArea(15, 55);
    tekstområde.setEditable(false);
    JScrollPane rulle = new JScrollPane(tekstområde);


	Container c   = getContentPane();
    c.setLayout( new FlowLayout() );
    c.add(radioMann);
    c.add(radioKvinne);
    c.add(new JLabel("Personnummer:"));
    c.add(fNr);
    c.add(new JLabel("Fornavn:"));
    c.add(fNavn);
    c.add(new JLabel("Etternavn:"));
    c.add(eNavn);
    c.add(new JLabel("Adresse:"));
    c.add(adresse);


    c.add(kNyPasient);
    c.add(kSlettPasient);
    c.add(kVisPasient);
    c.add(kVisAlt);
    c.add(rulle);



    setSize(850, 400);
    setVisible(true);


    sensor = new Lytter();


    kNyPasient.addActionListener(sensor);

    kSlettPasient.addActionListener(sensor);

    kVisPasient.addActionListener(sensor);
    kVisAlt.addActionListener(sensor);


    addWindowListener(new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            try
            {
              lagreFil();
            }

            catch (IOException ex)
            {
               ex.printStackTrace();
            }

            System.exit(0);
         }
     });
  }
  private void lagreFil() throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("PasientLagring.txt"));
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
      FileInputStream fileHandle = new FileInputStream("PasientLagring.txt");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      bibliotek = (PasientRegister) in.readObject();
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
      if (e.getSource() == kNyPasient)
      {
        //int rbStatus = aktivRadio();
        //if(rbStatus == _PERSON)
        //{
          nyPasient();
        //}
        /*else if (rbStatus == _FIRMA)
        {
          nyFirma();
        }*/
      }
      else if (e.getSource() == kSlettPasient)
      {
        slettPasient();
      }
      else if (e.getSource() == kVisPasient)
      {
        visPasient();
      }
      else if (e.getSource() == kVisAlt)
      {
        visAlt();
      }
    }
  }

  public void emptyInputField()
  {

  }

public char aktivRadio()
  {
    if(radioMann.isSelected())
      return 'M';
    else if (radioKvinne.isSelected())
      return 'F';

    tekstområde.setText("Huk av for Mann eller Kvinne!");
    return 0;
  }


  public void nyPasient()
  {
    try
    {
      String id = fNr.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn
      String fnavn = fNavn.getText();
      String enavn = eNavn.getText();
      char gen = aktivRadio();
      String adr = adresse.getText();


      if (!fnavn.equals("") && !enavn.equals("") && !id.equals("") && gen!=0 && !adr.equals("") )
      {
        if (!bibliotek.finnes(id))
        {
            Pasient pasient = new Pasient(fnavn, enavn, id, gen,adr);
            bibliotek.settInnNy(pasient);
            tekstområde.setText(logg.toString("Pasient lagt til"));
        }
        else
            tekstområde.setText("Pasienten er allerede registrert");


      }
        else
         tekstområde.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }




  public void slettPasient()
  {
    try
    {
      String id = fNr.getText().replaceAll("\\s","");



      if (!id.equals("") )
      {
        if (bibliotek.finnes(id))
        {
            Pasient pasient = bibliotek.finn(id);

            if(bibliotek.fjern(pasient))
               tekstområde.setText(logg.toString("Pasient fjernet!"));
        }
        else
            tekstområde.setText("Pasienten finnes ikke i registeret");
      }
      else
        tekstområde.setText("Skriv inn Personnummeret på pasienten du ønsker å fjerne!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }



  public void visPasient()
  {
     try
    {
      String id = fNr.getText().replaceAll("\\s","");//får personnummer og fjerner blanke tegn
      String fnavn = fNavn.getText();
      String enavn = eNavn.getText();



      if (!id.equals("") )
      {

        if (bibliotek.finnes(id))
        {
            String temp = "Viser pasient:\n" + bibliotek.finn(id).toString();
  	  	 tekstområde.setText(logg.toString(temp));
        }
        else
            tekstområde.setText("Pasienten finnes ikke i registeret");

      }
      else
        tekstområde.setText("Skriv inn personnummer");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }

  public void visAlt()
  {
      logg.toString("Viser alle pasienter");
    tekstområde.setText(bibliotek.toString());
  }
}

