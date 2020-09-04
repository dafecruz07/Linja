import java.util.Vector;

/**
 * Clase encargada de guardar las variables de estado y calcular posibles jugadas.
 * @author Miguel Angel Askar Rodriguez - 1355842
 * @author Danny Fernando Cruz Arango   - 1449949
 */

public class Estado 
{
	private int[][] tablero; //las fichas en el tablero estarán representadas por: vacío= 0, roja= 1, negra= 2.
	private int primerMovimiento; //guarda la columna a la que se hizo la primera jugada
	
	public Estado(int[][] tablero)
	{
		this.tablero= tablero;
		primerMovimiento= 0; 
	}
	
	public int posiblePrimeraJugada(int[] ficha) //Verifica la posible primera jugada de una ficha, retorna la columna a la que puede saltar.
	{												  
		if(tablero[ficha[0]][ficha[1]]==0) //Si es una posición vacía
		{
			return ficha[1];
		}
		if(tablero[ficha[0]][ficha[1]]==1) //Si es una ficha roja
		{
			if(columnaDisponible(ficha[1]+1)) 
			{
				if(ficha[1]==0) ficha[1]= ficha[1]+1; 
				return (ficha[1]+1); //si la siguiente columna está disponible, retorna dicha columna.
			}
			else return 0; //En otro caso, dice 0 porque nunca llegará a esa columna.
		}
		if(tablero[ficha[0]][ficha[1]]==2) //Si es una ficha negra
		{
			if(columnaDisponible(ficha[1]-1)) 
			{
				if (ficha[1]==9) ficha[1]=  ficha[1]-1;
				return (ficha[1]-1); //si la siguiente columna está disponible, retorna dicha columna.
			}
			else return 9; //En otro caso, dice 7 porque nunca llegará a esa columna.
		}
		return 23; //nunca llegará a esta línea de código
	}
	
	@SuppressWarnings("rawtypes")
	public Vector<int[][]> posiblesSegundasJugadas(int tipo) //Retorna un vector con los tableros nuevos generados con todas las posibles segundas jugadas.
	{
		@SuppressWarnings("unchecked")
		Vector<int[][]> resultado= new Vector(0,1);
		int cantidadSaltos= 0; //guarda la cantidad de saltos de acuerdo a la columna donde se hizo el primer movimiento.
		for(int i=0; i<6; i++)
		{
			if(tablero[i][primerMovimiento]!=0) cantidadSaltos++;
		}
		cantidadSaltos--; //Se resta la ficha del turno.
		for(int i= 0; i<6; i++)
		{
			for(int j= 1; j<9; j++)
			{
				if(tablero[i][j]==tipo && tipo==1)
				{
					if((j+cantidadSaltos)>8){}
					else
					{
						int[] ficha= {i,j};
						int[][] tableroTemporal= new int[6][10];
						for(int m= 0; m<6; m++)
						{
							System.arraycopy(tablero[m], 0, tableroTemporal[m], 0, tablero[m].length); //Se copia el tablero por valor.
						}
						ponerFicha(ficha, j+cantidadSaltos, tipo);
						resultado.add(tablero);
						this.tablero= tableroTemporal; //Se guarda el tablero con la jugada nueva realizada.
					}
				}
				if(tablero[i][j]==tipo && tipo==2)
				{
					if((j-cantidadSaltos)<1){}
					else
					{
						int[] ficha= {i,j};
						int[][] tableroTemporal= new int[6][10];
						for(int m= 0; m<6; m++)
						{
							System.arraycopy(tablero[m], 0, tableroTemporal[m], 0, tablero[m].length); //Se copia el tablero por valor.
						}
						ponerFicha(ficha, j-cantidadSaltos, tipo);
						resultado.add(tablero);
						this.tablero= tableroTemporal; //Se guarda el tablero con la jugada nueva realizada.
					}
				}
			}
		}			
		return resultado;
	}
	
	public int puntacionRojo() //Se calcula la puntación del jugador rojo.
	{
		int resultado= 0;
		for(int j=5; j<10; j++)
		{
			int cantidad= 0;
			for(int i= 0; i<6; i++)
			{
				if(tablero[i][j]==1) cantidad++;
			}
			if(j==5) resultado+= (cantidad);
			if(j==6) resultado+= (cantidad*2);
			if(j==7) resultado+= (cantidad*3);
			if(j==8 || j==9) resultado+= (cantidad*5);
		}
		return resultado;
	}
	
	public int puntacionNegro() //Se calcula la puntación del jugador negro.
	{
		int resultado= 0;
		for(int j=4; j>=0; j--)
		{
			int cantidad= 0;
			for(int i= 0; i<6; i++)
			{
				if(tablero[i][j]==2) cantidad++;
			}
			if(j==4) resultado+= (cantidad);
			if(j==3) resultado+= (cantidad*2);
			if(j==2) resultado+= (cantidad*3);
			if(j==1 || j==0) resultado+= (cantidad*5);
		}
		return resultado;
	}
	
	public boolean juegoAcabado() //Se verifica si el juego ya se acabó.
	{
		if(rojaMasAIzquierda()> negraMasADerecha()) return true;
		else return false;
	}
	
	/***
	 * 
	 * @param ficha recibe la ficha a cambiar.
	 * @param destino recibe la posición nueva de la ficha.
	 * @param tipo define si la ficha es negra o roja.
	 * @return	Retorna un entero con las coordenas de la nueva ficha
	 * Este método pone una ficha en una nueva posición si es posible y deja vacío el espacio donde estaba.
	 */
	
	public int[] ponerFicha(int[] ficha, int destino, int tipo)
	{
		for(int i= 0; i<6; i++)
		{
			if(destino==1 && tipo==2)
			{
				for(int j=1; j>=0; j--)
				{
					if(tablero[i][j]==0)
					{
						tablero[i][j]= tablero[ficha[0]][ficha[1]]; //Se asigna la ficha.
						tablero[ficha[0]][ficha[1]]= 0; //la posición antigua queda en 0.
						int[] resultado= {i,j};
						return resultado;						
					}
				}
			}
			else
			{
				if(destino==8 && tipo==1)
				{
					for(int j=8; j<=9; j++)
					{
						if(tablero[i][j]==0)
						{
							tablero[i][j]= tablero[ficha[0]][ficha[1]]; //Se asigna la ficha.
							tablero[ficha[0]][ficha[1]]= 0;	//La posición antigua queda en 0.
							int[] resultado= {i,j};
							return resultado;	
						}
					}
				}
				else
				{
					if(tablero[i][destino]==0)
					{
						tablero[i][destino]= tablero[ficha[0]][ficha[1]]; //Se asigna la ficha.
						tablero[ficha[0]][ficha[1]]= 0; //La posición antigua queda en 0.
						int[] resultado= {i,destino};
						return resultado;	
					}
				}
			}
		}
		return ficha; //nunca llegará a esta linea
	}
	
	public int rojaMasAIzquierda() //Busca la ficha roja que más está a la izquierda.
	{
		for(int j= 0; j<9; j++)
		{
			for(int i= 0; i<6; i++)
			{
				if(tablero[i][j]==1) return j;
			}
		}
		return 8;
	}
	
	public int negraMasADerecha() //Busca la ficha negra que más está a la derecha.
	{
		for(int j= 9; j>0; j--)
		{
			for(int i= 0; i<6; i++)
			{
				if(tablero[i][j]==2) return j;
			}
		}
		return 8;
	}
	

	public boolean columnaDisponible(int columna) //Verifica si una columna tiene espacios disponibles.
	{
		if(columna==1 || columna==8) return true; //la primera y la última fila siempre estarán disponibles para llegar.
		for (int i = 0; i <6 ; i++) 
		{
			if(tablero[i][columna]==0) return true; //Retorna true si hay un espacio en la columna
		}
		return false; //Retorna false si no encontró espacios disponibles en la columna
	}
	
	public int[][] getTablero() {
		return tablero;
	}

	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}

	/**
	 * @return the primerMovimiento
	 */
	public int getPrimerMovimiento() {
		return primerMovimiento;
	}

	/**
	 * @param primerMovimiento the primerMovimiento to set
	 */
	public void setPrimerMovimiento(int primerMovimiento) {
		this.primerMovimiento = primerMovimiento;
	}
	
}
