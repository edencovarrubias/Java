//	Titulo: Tarea04
//	Autor:  Eden Covarrubias Guel 
//	Fecha:  29-11-2020

//	Programa simulador de un juego de Beisbol 
   
import java.io.IOException;
import java.util.Random;
import java.util.*;


class Tarea04
{
	public static void main(String args[])
	{
		Pantalla p= new Pantalla ();
		Jugadas  j= new Jugadas ();
		Marcador m= new Marcador ();
		Diamante d= new Diamante ();
		String modo=p.Menu();
		if (modo.equals("A")==true || modo.equals("B")==true)
		{
			int jugador=1;
			int jugada=10;
			int entrada=1;
			int outs=0;
			int carreras=0;
			boolean salir=false; 
			do
			{		
				
				if (outs==3)
				{
					if (jugador==1)
					{
						jugador=2;
						outs=0;
						d.LimpiaDiamante();
					}
					else
					{
						entrada=entrada+1;
						if (entrada>4)
						{
							salir=true;
							entrada=4;
						}
						else
						{
							jugador=1;
							outs=0;
							d.LimpiaDiamante();
						}
					}
				}				
								
				p.LimpiaPantalla();	
				j.ImprimeJugada(jugada);
				m.ImprimeMarcador(jugador,entrada,d.ObtieneCarreras(),modo);
				d.ImprimeDiamante(jugador);
				System.out.println("Outs: "+outs);	
				System.out.println();
				
				if (salir == false)
				{
					System.out.print("Batear (Enter)... ");
					j.Esperar();
					
					p.LimpiaPantalla();	
					j.ImprimeJugada(11);
					m.ImprimeMarcador(jugador,entrada,0,modo);
					d.ImprimeDiamante(jugador);
					System.out.println("Outs: "+outs);	
					System.out.println();
					System.out.print("Batear (Enter)... ");
					if (modo.equals("B")==true && jugador==2)
					{
						j.Esperar();
						jugada=j.Tira();
					}
					else
					{
						j.Presionar();
						jugada=j.Tira();						
					}	
					d.MueveJugadores(jugador,jugada);
					outs=outs+d.ObtieneOuts();
				}
			}
			while (salir==false);
			m.Ganador();
			j.Presionar();
		}
		else
		{
			System.out.println();
			System.out.println("	 GAME OVER!!!");
		}
	}
}

class Pantalla
{
	public void LimpiaPantalla()
	{
		for (int i=0;i<24;i++)
		{
			System.out.println();
		}
	}
	
	public String Menu()
	{
		String opcion;
		do
		{
			System.out.println("	     _--_     dMb     ");
			System.out.println(" B	  __(._  )   d0P  ");
			System.out.println(" A	    <  (D)  .MP    ");
			System.out.println(" S	   .~ \\ /~```M-.    ");
			System.out.println(" E	 .~    V    Mo_ \\   ");
			System.out.println("	 (   (___. {:)-./     ");
			System.out.println(" B	  ~._____.(:}      ");
			System.out.println(" A 	   /     .M\\       ");
			System.out.println(" L	  /     \"\"\" \\   ");
			System.out.println(" L	  |    /\\   |      ");
			System.out.println("  	  /   /  \\   \\    ");
			System.out.println(" J	 /   /    \\   \\   ");
			System.out.println(" A	 \\__/      \\__/  ");
			System.out.println(" V	 / /        | |     ");
			System.out.println(" A	.^V^.      .^V^.   ");
			System.out.println(" 	 +-+        +-+    ");
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ");
			System.out.println("	 MODO  DE JUEGO ");
			System.out.println();
			System.out.println("	 (a) 1P Vs 2P  ");
			System.out.println("	 (b) 1P Vs CPU ");
			System.out.println("	 (c) EXIT ");
			System.out.println();
			System.out.print("	 Select: ");
			opcion = System.console().readLine();
			opcion=opcion.toUpperCase();
		}
		while (opcion.equals("A")==false && opcion.equals("B")==false && opcion.equals("C")==false);
		return opcion;
	}
}

class Marcador
{
	private int icarreras[][]=new int[2][5];
	private String scarreras[][]=new String[2][4];
	private	String alias1;
	private	String alias2;
		
	Marcador ()
	{
		scarreras[0][0]=" ";
		scarreras[0][1]=" ";
		scarreras[0][2]=" ";
		scarreras[0][3]=" ";
		scarreras[1][0]=" ";
		scarreras[1][1]=" ";
		scarreras[1][2]=" ";
		scarreras[1][3]=" ";
	}
	
	public void ImprimeMarcador(int jugador,int entrada,int carreras,String modo)
	{
		icarreras[jugador-1][4]=icarreras[jugador-1][4]+carreras;
		icarreras[jugador-1][entrada-1]=icarreras[jugador-1][entrada-1]+carreras;
		scarreras[jugador-1][entrada-1]=Integer.toString(icarreras[jugador-1][entrada-1]);
		
		if (modo.equals("A")==true)
		{
			alias1="P1 ";
			alias2="P2 ";
		}
		else
		{
			alias1="P1 ";
			alias2="CPU";			
		}
        System.out.println("        1     2     3     4     F");
        System.out.println(alias1 +" ☺ [ "+scarreras[0][0]+" ] [ "+scarreras[0][1]+" ] [ "+scarreras[0][2]+" ] [ "+scarreras[0][3]+" ] [ "+icarreras[0][4]+" ]");
		System.out.println(alias2 +" ☻ [ "+scarreras[1][0]+" ] [ "+scarreras[1][1]+" ] [ "+scarreras[1][2]+" ] [ "+scarreras[1][3]+" ] [ "+icarreras[1][4]+" ]");	
		System.out.println();
	}	
	
	public void Ganador ()
	{
		if (icarreras[0][4]>icarreras[1][4])
		{
			System.out.print("	<<< GANADOR " + alias1 +" >>>");
		}
		if (icarreras[1][4]>icarreras[0][4])
		{
			System.out.print("	<<< GANADOR " + alias2+" >>>");
		}
		if (icarreras[0][4]==icarreras[1][4])
		{
			System.out.print("	<<< EMPATADOS >>>");
		}
	}	
}

class Jugadas
{	
	private String arreglo[]=new String [12];
	Jugadas()
	{
		arreglo[0]="      HIT       ";
		arreglo[1]=" BASE POR BOLA  ";
		arreglo[2]=" BASE POR GOLPE ";
		arreglo[3]="      DOBLE     ";
		arreglo[4]="     TRIPLE     ";
		arreglo[5]="    HOME RUN    ";
		arreglo[6]="     PONCHE     ";
		arreglo[7]="      OUT       ";
		arreglo[8]="      OUT       ";
		arreglo[9]="      OUT       ";
        arreglo[10]="   PLAY BALL    ";
        arreglo[11]="                ";
	}
	
	public int Tira()
	{
		String jugada;
		Random r = new Random();
		int aleatorio;
		aleatorio=r.nextInt(10);
		jugada=arreglo[aleatorio];
		return aleatorio;
	}	
	
	public void ImprimeJugada(int jugada)
	{
		System.out.println("	******************");
		System.out.println("	*"+arreglo[jugada]+"*");
		System.out.println("	******************");
		System.out.println();
	}	
	
	
	public void Presionar()
	{
		try
		{
			System.in.read();
		}
		catch(java.io.IOException e) {}
	}	
	
	public void Esperar()
	{
		try 
		{
			Thread.sleep (700);
		} 
		catch (InterruptedException e) {}
	}
	
}

class Diamante
{
	private String posiciones[]=new String [10];
	private String pitcher;
	private int outs;
	private int carreras;
	
	Diamante ()
	{
		pitcher=" ";
		posiciones[0]=" ";
		posiciones[1]=" ";
		posiciones[2]=" ";
		posiciones[3]=" ";
		posiciones[4]=" ";
	}
	
	public void LimpiaDiamante()
	{
		pitcher=" ";
		posiciones[0]=" ";
		posiciones[1]=" ";
		posiciones[2]=" ";
		posiciones[3]=" ";
		posiciones[4]=" ";	
	}
		
	public void MueveJugadores(int jugador,int jugada)
	{
		String mono;
		String auxiliar[]=new String [10];
		
		outs=0;
		carreras=0;
		
		auxiliar[0]=" ";
		auxiliar[1]=" ";
		auxiliar[2]=" ";
		auxiliar[3]=" ";
		auxiliar[4]=" ";
		
		if (jugador==1)
		{
			mono="☺";
			posiciones[0]="☺";
			pitcher="☻";
		}
		else
		{
			mono="☻";
			posiciones[0]="☻";
			pitcher="☺";
		}
	
		if (jugada==0)
		{
				auxiliar[4]=posiciones[3];
				auxiliar[3]=posiciones[2];
				auxiliar[2]=posiciones[1];
				auxiliar[1]=posiciones[0];
				auxiliar[0]=" ";
				if (auxiliar[4].equals(mono)==true)
				{
					carreras=carreras+1;
				}
		}
		
		if (jugada==1 || jugada==2)
		{		
			if (posiciones[3].equals(mono)==true && posiciones[2].equals(mono)==true)
			{
				auxiliar[4]=posiciones[3];
				carreras=carreras+1;
			}
			else
			{
				auxiliar[3]=posiciones[3];
			}
				
			if (posiciones[2].equals(mono)==true && posiciones[1].equals(mono)==true)
			{
				auxiliar[3]=posiciones[2];
			}
			else
			{
				auxiliar[2]=posiciones[2];
			}
						
			if (posiciones[1].equals(mono)==true && posiciones[0].equals(mono)==true)
			{
				auxiliar[2]=posiciones[1];
			}
			else
			{
				auxiliar[1]=posiciones[1];
			}	
				
			if (posiciones[0].equals(mono)==true)
			{
				auxiliar[1]=posiciones[0];
			}
			else
			{
					auxiliar[0]=posiciones[0];
			}	
		}		
					
		if (jugada==3)
		{
			if (posiciones[3].equals(mono)==true)
			{
				carreras=carreras+1;
			}
			
			if (posiciones[2].equals(mono)==true)
			{
				carreras=carreras+1;
			}
			if (posiciones[1].equals(mono)==true)
			{
				auxiliar[3]=mono;
			}
			auxiliar[2]=mono;
		}

		if (jugada==4)
		{
			if (posiciones[3].equals(mono)==true)
			{
				carreras=carreras+1;
			}
			
			if (posiciones[2].equals(mono)==true)
			{
				carreras=carreras+1;
			}
			if (posiciones[1].equals(mono)==true)
			{
				carreras=carreras+1;
			}
			auxiliar[3]=mono;
		}

		if (jugada==5)
		{
			for (int i=0;i<4;i++) 
			{
				if (posiciones[i].equals(mono)==true)
				{
					carreras=carreras+1;
					posiciones[i]=" ";
				}					
			}				
		}
		
		if (jugada==6 || jugada==7 || jugada==8 || jugada==9)
		{
			auxiliar[4]=posiciones[4];
			auxiliar[3]=posiciones[3];
			auxiliar[2]=posiciones[2];
			auxiliar[1]=posiciones[1];
			auxiliar[0]=" ";
			outs=outs+1;
		}		

			System.arraycopy(auxiliar, 0, posiciones, 0, 4);
	}

	public void ImprimeDiamante(int jugador)
	{
		String mono;
		if (jugador==1)
		{
			mono="☺";
			posiciones[0]="☺";
			pitcher="☻";
		}
		else
		{
			mono="☻";
			posiciones[0]="☻";
			pitcher="☺";
		}
		System.out.println();
		System.out.println("	        2");
        System.out.println("	       ["+posiciones[2]+"]");
        System.out.println();
		System.out.println("	 3             1");
		System.out.println("	["+posiciones[3]+"]    {"+pitcher+"}    ["+posiciones[1]+"]");
		System.out.println();
		System.out.println();
		System.out.println("	       ["+posiciones[0]+"]");
		System.out.println("	        H");
		System.out.println();
	}
		
	public int ObtieneCarreras()
	{
			return carreras;
	}
	
	public int ObtieneOuts()
	{
		
			return outs;
	}	
}


