/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/

package pasient;
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

public class PasientVinduTest extends JFrame
{
  JTextField 	fDato, fNavn, eNavn;
  JButton 		kNyPasient, kSlettPasient, kVisAlt, kVisEier;
  JRadioButton 	radioPasient, radioFirma;
  JTextArea		tekstområde;

  private PasientRegister bibliotek;
  private Lytter sensor;
  public static final int _PERSON = 1;
  public static final int _FIRMA  = 2;

  public PasientVinduTest()//konstruktør
  {
    super("PASIENT - REGISTERET");

    try
    {
      lastInnFil();
    }

    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    radioPasient  = new JRadioButton("Pasient");
    radioFirma   = new JRadioButton("Firma");
    ButtonGroup radioGruppe = new ButtonGroup();
    radioGruppe.add(radioPasient);
    radioGruppe.add(radioFirma);

    fDato      = new JTextField(14);
    fNavn    = new JTextField(14);
    eNavn = new JTextField(14);

    kNyPasient      = new JButton("Reg pasient");
    kSlettPasient   = new JButton("Slett pasient");
    kVisEier     = new JButton("Vis pasient");
    kVisAlt      = new JButton("Vis alle");

	tekstområde  = new JTextArea(15, 55);
    tekstområde.setEditable(false);
    JScrollPane rulle = new JScrollPane(tekstområde);


	Container c   = getContentPane();
    c.setLayout( new FlowLayout() );
    c.add(radioPasient);
    c.add(radioFirma);
    c.add(new JLabel("Fodselsdato:"));
    c.add(fDato);
    c.add(new JLabel("Fornavn:"));
    c.add(fNavn);
    c.add(new JLabel("Etternavn:"));
    c.add(eNavn);
    c.add(kNyPasient);
    c.add(kSlettPasient);
    c.add(kVisEier);
    c.add(kVisAlt);
    c.add(rulle);

    bibliotek  = new PasientRegister();

    setSize(850, 400);
    setVisible(true);


    sensor = new Lytter();


    kNyPasient.addActionListener(sensor);

    kSlettPasient.addActionListener(sensor);

    kVisEier.addActionListener(sensor);
    kVisAlt.addActionListener(sensor);

    addWindowListener(new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
        	try
        	{
               lagreFil();
               System.out.println("Lagret!");
            }

            catch (IOException ex)
            {
               ex.printStackTrace();
            }
            System.exit(0);
         }
     });
  }//slutt på konstruktør

  void lagreFil() throws IOException
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("PasientLagring.dat"));
      out.writeObject(bibliotek);
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
      FileInputStream fileHandle = new FileInputStream("PasientLagring.dat");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      bibliotek = (PasientRegister) in.readObject();
    }
    catch (FileNotFoundException ex)
    {
      System.out.println("Lager ny lagringsfil");
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    catch (EOFException ex)
    {
      System.out.println("Ferdig lastet!");
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
        slettEier();
      }
      else if (e.getSource() == kVisEier)
      {
        visEier();
      }
      else if (e.getSource() == kVisAlt)
      {
        visAlt();
      }
    }
  }

/*  public int aktivRadio()
  {
    if(radioPasient.isSelected())
      return _PERSON;
    else if (radioFirma.isSelected())
      return _FIRMA;

    tekstområde.setText("Huk av for Firma eller Pasient!");
    return 0;
  }*/


  public void nyPasient()
  {
    try
    {
      int id = Integer.parseInt(fDato.getText());
      String navn = fNavn.getText() + eNavn.getText();


      if (!navn.equals("") && !fDato.getText().equals("") )
      {
        Pasient pasient = new Pasient(navn, id);
        bibliotek.settInnNy(pasient);
        tekstområde.setText("Pasient lagt til");
      }
      else
        tekstområde.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }




  public void slettEier()
  {
    try
    {System.out.println("6");
      int id = Integer.parseInt(fDato.getText());
      String navn = fNavn.getText() + eNavn.getText();


      if (!navn.equals("") && !fDato.getText().equals("") )
      {System.out.println("hergerherh");
        Pasient pasient = new Pasient(navn, id);
        if(bibliotek.fjern(pasient))
            tekstområde.setText("Pasient fjernet!");

      }
      else
        tekstområde.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }



  public void visEier()
  {
     try
    {
      int id = Integer.parseInt(fDato.getText());
      String navn = fNavn.getText() + eNavn.getText();


      if (!navn.equals("") && !fDato.getText().equals("") )
      {
        String temp = bibliotek.finn(navn, id).toString();
  	  	 tekstområde.setText(temp);

      }
      else
        tekstområde.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }

  public void visAlt()
  {
    tekstområde.setText(bibliotek.getText());
  }
}

