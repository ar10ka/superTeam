//Liste.java
//Definerer noder og listeoperasjoner for enkel liste
import javax.swing.*;

class Node
{
  private int info;
  Node neste;

  public Node( int data )
  { info = data;
		  neste = null;
  }

  public int getInfo()
  { return info; }

  public void setInfo( int nyVerdi )
  { info = nyVerdi; }

}// end of class Node


class Liste
{
  private Node hode;

  public Liste()
  { hode = null; }

  public void settInnForrest( int verdi )  	//setter inn ny node forrest
  {
		Node ny = new Node( verdi );
    ny.neste = hode;
    hode = ny;
  }

  public void settInnForrest( Node ny )  //tar imot noden som argument
  { if ( ny == null )
  		return;
  	// Hvis vi kommer hit er ny != null
  	ny.neste = hode;
    hode = ny;
  }

  public void settInnBakerst( int n )  //setter inn n i ny node bakerst
	{  Node ny = new Node( n );
	   if ( hode == null ) //tom liste
	      hode = ny;
	   else
	   {
	     Node hjelp = hode;
	     while ( hjelp.neste != null )      //plasserer hjelp på siste node
	       hjelp = hjelp.neste;

	     hjelp.neste = ny;
	   }
	 }

	 public void settInnBakerst( Node ny )  //tar imot noden som argument
	 { if ( hode == null ) //tom liste
	     hode = ny;
	   else
	   {
	     Node hjelp = hode;
	     while ( hjelp.neste != null )    //plasserer hjelp på siste node
	       hjelp = hjelp.neste;

	     hjelp.neste = ny;
	   }
	 }

	 public Node fjernFørste()
	 {
		 if(hode == null )
		    return null;
		 // lista inneholder minst et element
		 Node noden = hode;
		 hode = hode.neste;  // hopper over første
		 return noden;
	 }

	 public Node fjernSiste()
	 {
		 if (hode == null)  // ingen elementer i lista
		    return null;
		 if( hode.neste == null)  // et elemente i lista
		 { Node noden = hode;
			 hode = null;
			 return hode;
		 }
		 // lista består av minst 2 elementer:
		 Node hjelp = hode;
		 while(hjelp.neste.neste != null) // løkka skal plassere hjelp på neste siste lemenet
		     hjelp = hjelp.neste;

		 Node noden = hjelp.neste;
		 hjelp.neste = null;
		 return noden;
	}


	public String toString()
	{
	  String s = "";

	  Node hjelp = hode;

	  while( hjelp != null )
	  {
		s += hjelp.getInfo() + " " ;
		hjelp = hjelp.neste;
	  }

	  return s + "\n";
  }


	public void skrivListe(JTextArea elementer )
	{ elementer.setText("");
	  if ( hode==null )
	       elementer.append( "Tom liste\n" );
	     else
	     {
	       Node hjelp = hode;
	       while ( hjelp!=null)
	       { elementer.append( hjelp.getInfo()+ " " );
	         hjelp = hjelp.neste;
	       }
	       elementer.append( "\n" );
	     }
	   }

} // end of class Liste
