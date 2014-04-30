/*

Studentnr: s188097
Navn: Ole Boee Andreassen


Klasse: Dataingenioer

*/

package Medisin;
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

public class MedisinVinduTest extends JFrame
{
  JTextField 	medID, medNavn, medKategori;
  JButton 		kNyMedisin, kSlettMedisin, kVisAlt, kVisEier;
  JRadioButton 	radioA, radioB,radioC;
  JTextArea		tekstområde,medInfo;

  private MedisinRegister bibliotek;
  private Lytter sensor;
  public static final int _A = 1;
  public static final int _B  = 2;
  public static final int _C  = 3;

  public MedisinVinduTest()//konstruktør
  {
    super("MEDISIN - REGISTERET");

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

    medID      = new JTextField(14);
    medNavn    = new JTextField(14);
    medKategori    = new JTextField(14);
    
    medInfo    = new JTextArea(15,30);
    medInfo.setEditable(true);
    JScrollPane rulle2 = new JScrollPane(medInfo);

    kNyMedisin      = new JButton("Reg medisin");
    kSlettMedisin   = new JButton("Slett medisin");
    kVisEier     = new JButton("Vis medisin");
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
    c.add(kVisEier);
    c.add(kVisAlt);
    c.add(rulle);

    bibliotek  = new MedisinRegister();

    setSize(850, 400);
    setVisible(true);


    sensor = new Lytter();


    kNyMedisin.addActionListener(sensor);

    kSlettMedisin.addActionListener(sensor);

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
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MedisinLagring.dat"));
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
      FileInputStream fileHandle = new FileInputStream("MedisinLagring.dat");
      ObjectInputStream in = new ObjectInputStream(fileHandle);
      bibliotek = (MedisinRegister) in.readObject();
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
      if (e.getSource() == kNyMedisin)
      {
          nyMedisin();
      }
      else if (e.getSource() == kSlettMedisin)
      {
        slettMedisin();
      }
      else if (e.getSource() == kVisEier)
      {
        visMedisin();
      }
      else if (e.getSource() == kVisAlt)
      {
        visAlt();
      }
    }
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


  public void nyMedisin()
  {
    try
    {
      int id = Integer.parseInt(medID.getText());
      String navn = medNavn.getText();
      char rbStatus = aktivRadio();
      String info = medInfo.getText();
      String k = medKategori.getText();
      
        


      if (!navn.equals("") && !medID.getText().equals("\\d+") )
      {
        Medisin medisin = new Medisin(id, navn, info, k, rbStatus);
        if (!bibliotek.finnes(medisin) )
        {
            System.out.print("Finnes medisin?");
            bibliotek.settInnNy(medisin);
            tekstområde.setText("Medisin lagt til");
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
      int id = Integer.parseInt(medID.getText());
      String navn = medNavn.getText();
      char rbStatus = aktivRadio();
      String info = medInfo.getText();
      String k = medKategori.getText();


      if (!navn.equals("") && !medID.getText().equals("") )
      {
        Medisin medisin = new Medisin(id, navn, info, k, rbStatus);
        if(bibliotek.fjern(medisin))
            tekstområde.setText("Medisin fjernet!");

      }
      else
        tekstområde.setText("Fyll ut alle feltene!");
    }
    catch (NumberFormatException e)
    {
      tekstområde.setText("Fyll ut alle feltene!");
    }
  }



  public void visMedisin()
  {
     try
    {
      int id = Integer.parseInt(medID.getText());
      String navn = medNavn.getText();


      if (!navn.equals("") && !medID.getText().equals("") )
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

