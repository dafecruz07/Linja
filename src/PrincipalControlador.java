import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * Clase encargada de manejar lo relacionado con la IA de la maquina al igual que lajugabilidad
 * Esta es la clase que debe ser ejecutada.
 * @author Miguel Angel Askar Rodriguez - 1355842
 * @author Danny Fernando Cruz Arango   - 1449949
 */
public class PrincipalControlador implements ActionListener, MouseListener
{
	private Vector<Nodo> cero; 
	private Vector<Nodo> primero;
	private Vector<Nodo> segundo;
	private Vector<Nodo> tercero;
	private JButton[][] botonesTablero;
	private PrincipalVista ventanaInicial;
	private int x, y;
	private int largoTiro;
	private int contadorTurno;
	
	/**
	 * Metodo constructor en el cual se inicializan gran cantidad de variables necesarias
	 * Se carga un talero desde archivo de texto
	 * y se inicializa la interfaz
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PrincipalControlador()
	{
		x= y= 0;
		cero= new Vector(0,1);
		primero= new Vector(0,1);
		segundo= new Vector(0,1);
		tercero= new Vector(0,1);
		contadorTurno= 0;
		//----------------LECTURA DE ARCHIVOS----------------------
		Archivos lector= new Archivos();
		lector.leer("tablero.txt"); //Si desea cambiar el estado inicial, se puede modificar o reemplazar este archivo.
		String leido= lector.getLeido();
		StringTokenizer numeros= new StringTokenizer(leido, "\n");
		int[][] tablero= new int[6][10];
		for(int i=0; i<6; i++)
		{
			String numero= numeros.nextToken();
			StringTokenizer valores= new StringTokenizer(numero, " ");
			for(int j= 0; j<10; j++)
			{
				tablero[i][j]= Integer.parseInt(valores.nextToken());
			}
		}
		//----------------FIN LECTURA DE ARCHIVOS------------------
		Nodo inicial= new Nodo(new Estado(tablero), -1, 0); //Se crea el nodo inicial con el tablero cargado.
		inicial.setPosicion(0);
		cero.add(inicial);
		
		botonesTablero = new JButton[6][10];
		crearBotones();
		
		ventanaInicial = new PrincipalVista(botonesTablero, this);
		this.activarDesactivarBotones(2);
		ventanaInicial.setVisible(true);
	}
	
	/**
	 * Metodo usado para crear un arreglo de botones con propiedad de casilla vacia de forma predeterminada
	 * Para casgar como estado inicial antes de iniciar un partida
	 */
	public void crearBotones(){
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 10; j++) {
				JButton btn = new JButton();
				btn.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Verde.png")));
				btn.putClientProperty("row", i);
				btn.putClientProperty("column", j);
				btn.addMouseListener(this);
				botonesTablero[i][j]=btn;
			}			
		}
	}
	
	/**
	 * Metodo que recibe un arreglo de enteros con las siguiente convenciones:
	 * 0 = Vacio   (verde)
	 * 1 = Usuario (Roja)
	 * 2 = maquina (Negra)
	 * Dicho arreglo representa el estado de un tablero y de igual manera los botones en dicha posición seran
	 * Para representar en tablero en ese estado
	 * @param tablero El tablero que debe ser representado
	 */
	public void actualizarTablero(int[][] tablero){
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				if (tablero[i][j]==0){
					botonesTablero[i][j].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Verde.png")));
					botonesTablero[i][j].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Verde.png")));
					botonesTablero[i][j].putClientProperty("User", 0);
				}
				if (tablero[i][j]==1){
					botonesTablero[i][j].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
					botonesTablero[i][j].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
					botonesTablero[i][j].putClientProperty("User", 1);
				}
				if (tablero[i][j]==2){
					botonesTablero[i][j].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Negra.png")));
					botonesTablero[i][j].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Negra.png")));
					botonesTablero[i][j].putClientProperty("User", 2);
				}
			}
		}
	}
	
	/**
	 * Metodo encargado de crear el arbol por niveles, expandiendo el nodo original
	 * hasta llegar a las hojas del nivel 3
	 */
	public void crearArbol()
	{
		primero= expandirNodo(cero.get(0), 2);
		for(int i=0; i<primero.size(); i++)
		{
			segundo.addAll(expandirNodo(primero.get(i), 2));
		}
		for(int i=0; i<segundo.size(); i++)
		{
			tercero.addAll(expandirNodo(segundo.get(i), 2));			
		}
	}
	
	/**
	 * Calcula la decisión MiniMax, recorriendo el árbol desde sus hojas hasta la raiz (3 niveles)
	 * @return Entero que representa el nodo que se debe tomar del primer nivel
	 */
	public int calcularDecisionMinMax()
	{
		//El nivel 3 es quien define al 2, decisión max
		for(int i= 0; i<tercero.size(); i++)
		{
			if(segundo.elementAt(tercero.get(i).getPadre()).getMinimax()< tercero.get(i).getPuntajeNegro()-tercero.get(i).getPuntajeRojo())
			{
				Nodo auxiliar = segundo.elementAt(tercero.get(i).getPadre());
				auxiliar.setMinimax(tercero.get(i).getPuntajeNegro()-tercero.get(i).getPuntajeRojo());
				segundo.setElementAt(auxiliar, tercero.get(i).getPadre());
			}
		}
		//El nivel 2 es quien define al 1, decisión min
		for(int i= 0; i<primero.size(); i++)
		{
			primero.elementAt(i).setMinimax(9999); //Se utiliza un número no alcanzable
		}
		for(int i= 0; i<segundo.size(); i++)
		{
			if(primero.elementAt(segundo.get(i).getPadre()).getMinimax()> segundo.get(i).getPuntajeNegro()-segundo.get(i).getPuntajeRojo())
			{
				Nodo auxiliar= primero.elementAt(segundo.get(i).getPadre());
				auxiliar.setMinimax(segundo.get(i).getPuntajeNegro()-segundo.get(i).getPuntajeRojo());
				primero.setElementAt(auxiliar, segundo.get(i).getPadre());
			}
		}
		//Se busca la decisión minimax (max) en el nivel 1
		int minimax= primero.get(0).getMinimax();
		int resultado= 0;
		for(int i= 1; i<primero.size(); i++)
		{
			if(primero.get(i).getMinimax()>minimax)
			{
				resultado= i;
			}
		}
		return resultado;
	}
	
	/**
	 * Metodo que expande el nodo que llega por parametro, y genera todas sus posibles jugadas
	 * @param nodo El nodo que debe ser expandido
	 * @param tipo Representa el jugador a expandir
	 * @return Un vector de nodos que contiene todas las posibles jugadas del jugador solicitado
	 */
	public Vector<Nodo> expandirNodo(Nodo nodo, int tipo) //tipo, para saber si expande rojas(1) o negras(2)
	{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Vector<Nodo> resultado= new Vector(0,1);
		for(int i=0; i<6; i++)
		{
			for(int j=1; j<9; j++)
			{
				if(nodo.getEstado().getTablero()[i][j]==tipo) //Verifica si es la ficha que se desea usar
				{
					int[] ficha= {i,j};
					int nuevaPosicion= nodo.getEstado().posiblePrimeraJugada(ficha);
					if(nuevaPosicion!=0 && nuevaPosicion!=9)
					{
						int[][] tablero= new int[6][10];
						for(int m= 0; m<6; m++)
						{
							for(int n=0; n<10; n++)
							{
								tablero[m][n]= nodo.getEstado().getTablero()[m][n]; //Se copia el tablero por valor.
							}
						}				 
						Estado nuevo= new Estado(tablero);
						ficha= nuevo.ponerFicha(ficha, nuevaPosicion, tipo); //Se mueve la ficha
						nuevo.setPrimerMovimiento(nuevaPosicion); //Se guarda el primer movimiento que se hizo
						Nodo nuevoNodo= new Nodo(nuevo, nodo.getPosicion(), nodo.getProfundidad()+1);
						nuevoNodo.setPuntajeNegro(nuevo.puntacionNegro());
						nuevoNodo.setPuntajeRojo(nuevo.puntacionRojo());
						resultado.add(nuevoNodo); //Se asigna este nodo como si no se decidiera hacer segunda jugada.
						//Ahora asignamos la segunda jugada
						Vector<int[][]> nuevosEstados= nuevoNodo.getEstado().posiblesSegundasJugadas(tipo);
						for(int k= 0; k<nuevosEstados.size(); k++) //Se guardan las posibles segundas jugadas a partir de la primera realizada.
						{
							Estado nuevoSegundo= new Estado(nuevosEstados.get(k));
							Nodo nuevoNodoSegundo= new Nodo(nuevoSegundo, nodo.getPosicion(), nodo.getProfundidad()+1);
							nuevoNodoSegundo.setPuntajeNegro(nuevoSegundo.puntacionNegro());
							nuevoNodoSegundo.setPuntajeRojo(nuevoSegundo.puntacionRojo());
							resultado.add(nuevoNodoSegundo);							
						}						
					}						
				}
			}
		}
		for(int i=0; i<resultado.size(); i++)
		{
			resultado.elementAt(i).setPosicion(i); //Se guarda la posición de los nodos para poder referenciar los padres en los hijos.
		}
		return resultado;
	}
	
	/**
	 * Metodo que activa y desactiva los botones necesarios dentro del tablero para la jugada
	 * Si es el turno dej jugador usuario, se activan los botones donde se encuentran sus fichas
	 * Si es el turno de la maquina, todos los botones del tablero se desactivan
	 * @param num Representa el jugador 1 = Usuario; 2 = Maquina
	 */
	public void activarDesactivarBotones(int num){
		if (num==1) {
			for (int i = 0; i < botonesTablero.length; i++) {
				for (int j = 0; j < botonesTablero[0].length; j++) {
					if(botonesTablero[i][j].getClientProperty("User").equals(2)){
						botonesTablero[i][j].setEnabled(false);
					}else{
						if(botonesTablero[i][j].getClientProperty("User").equals(0)){
							botonesTablero[i][j].setEnabled(false);
						}else botonesTablero[i][j].setEnabled(true);
					}
				}
			}
		}
		
		if (num==2) {
			for (int i = 0; i < botonesTablero.length; i++) {
				for (int j = 0; j < botonesTablero[0].length; j++) {
						botonesTablero[i][j].setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * Metodo usado para par inicio al juego
	 * @param jugador Representa el jugador 1 = Usuario; 2 = Maquina
	 */
	public void partida(int jugador){
		ventanaInicial.actualizarTurno("Usuario");
		activarDesactivarBotones(jugador);
		largoTiro=1;
	}
	
	/**
	 * Los disparos son realizados de forma automática despues de seleccionar la ficha con la que se
	 * desea jugar, este metodo se encarga de determinar la posición final de la ficha seleccionada
	 * teniendo en cuenta su posición inicial, y el largo del tiro
	 * @param x Ubicación en x de la ficha seleccionada
	 * @param y Ubicación en y de la ficha seleccionada
	 */
	public void realizarTiro (int x, int y){
		botonesTablero[x][y].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Verde.png")));
		botonesTablero[x][y].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Verde.png")));
		botonesTablero[x][y].putClientProperty("User", 0);
		if(y+largoTiro < 8) {
			for (int i = 0; i < botonesTablero.length; i++) {
				if (botonesTablero[i][y+largoTiro].getClientProperty("User").equals(0)) {
					botonesTablero[i][y+largoTiro].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
					botonesTablero[i][y+largoTiro].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
					botonesTablero[i][y+largoTiro].putClientProperty("User", 1);
					break;
				}
			}
			int cont = 0;
			for (int i = 0; i < botonesTablero.length; i++) {
				if (botonesTablero[i][y+largoTiro].getClientProperty("User").equals(0)) {
					cont++;
				}
			}
			System.out.println(6-(cont+1));
			largoTiro=6-(cont+1);
		}else{
			boolean flag = true;
			for (int i = 0; i < botonesTablero.length; i++) {
				if (botonesTablero[i][8].getClientProperty("User").equals(0)) {
					botonesTablero[i][8].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
					botonesTablero[i][8].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
					botonesTablero[i][8].putClientProperty("User", 1);
					flag = !flag;
					break;
				}
			}
			if (flag) {
				for (int i = 0; i < botonesTablero.length; i++) {
					if (botonesTablero[i][9].getClientProperty("User").equals(0)) {
						botonesTablero[i][9].setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
						botonesTablero[i][9].setDisabledIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Roja.png")));
						botonesTablero[i][9].putClientProperty("User", 1);
						flag = !flag;
						break;
					}
				}
			}
			int cont = 0;
			for (int i = 0; i < botonesTablero.length; i++) {
				if (botonesTablero[i][8].getClientProperty("User").equals(0)) {
					cont++;
				}
			}
			for (int i = 0; i < botonesTablero.length; i++) {
				if (botonesTablero[i][9].getClientProperty("User").equals(0)) {
					cont++;
				}
			}
			System.out.println(12-(cont+1));
			largoTiro=12-(cont+1);
		}
	}

	/**
	 * Metodo Main que inicia el juego
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		PrincipalControlador p= new PrincipalControlador();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ventanaInicial.getBtnIniciarPartida()){
			actualizarTablero(cero.get(0).getEstado().getTablero());
			partida(1);
			ventanaInicial.getBtnIniciarPartida().setEnabled(false);
		}
		if(e.getSource()==ventanaInicial.getBtnReglas())
		{
			JOptionPane.showMessageDialog(ventanaInicial, 
			"1-) Se hace un primer movimiento (de una casilla) y el segundo movimiento se hace a partir\n"
			+ "del número de piezas (propias y contrarias) que había en la franja donde se hizo el  primer\n"
			+ "movimiento (sin incluir la pieza que se mueve).");
			
			JOptionPane.showMessageDialog(ventanaInicial, 
			"2-) Nunca pueden haber mas de 6 piezas en una franja (con excepción de las últimas  franjas).");
			
			JOptionPane.showMessageDialog(ventanaInicial, "3-) Si con el primer movimiento se alcanza una franja vacía, no habrá segundo movimiento.");

		}
		if(e.getSource()==ventanaInicial.getBtnInfo())
		{
			JOptionPane.showMessageDialog(ventanaInicial, "Juego implementado por: \n"
					  + "Miguel Angel Askar Rodriguez - 201355842\n" 
					  + "Danny Fernando Cruz Arango   - 201449949");			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(botonesTablero[x][y].isEnabled()){
			realizarTiro(x, y); //Se realiza el movimiento de la ficha.
			int[][] tablero= new int[6][10];
			for(int i= 0; i<6; i++)
			{
				for (int j = 0; j < 10; j++) 
				{
					tablero[i][j]= (int)(botonesTablero[i][j].getClientProperty("User")); //Se obtiene el tablero actual desde la interfaz.
				}
			} 
			Nodo comprobacion= new Nodo(new Estado(tablero), -1, 0); //Se crea un nodo para saber si el juego se acabó
			if(comprobacion.getEstado().juegoAcabado()) //Pregunta si el juego se acabó
			{
				if(comprobacion.getEstado().puntacionNegro()>comprobacion.getEstado().puntacionRojo())
				{
					JOptionPane.showMessageDialog(ventanaInicial, "Ganó el computador, una IA es superior a ti");
				}
				else
				{
					if(comprobacion.getEstado().puntacionNegro()<comprobacion.getEstado().puntacionRojo())
					{
						JOptionPane.showMessageDialog(ventanaInicial, "Ganaste... ¡¿ganaste?! ¿Qué clase de brujería es esta?");
					}
					else
					{
						JOptionPane.showMessageDialog(ventanaInicial, "Empate... se supone que esto no debía pasar");
					}
				}
			}
			contadorTurno++; //Se aumenta el contador para saber en cuál jugada va el usuario.
			if(contadorTurno==1 && largoTiro==0) contadorTurno++; //Caso específico donde primera jugada cae en una columna vacía.
			if(contadorTurno==2) //Si ya se hicieron los dos turnos.
			{
				ventanaInicial.actualizarTurno("Máquina"); //Se actualiza el turno.
				activarDesactivarBotones(2); //Se desactivan los botones durante la jugada de la máquina.
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				                jugarMaquina();  //Empieza el turno de la máquina con 0,5s de retraso. Para que las acciones anteriores puedan tener lugar.
				            }
				        }, 
				        500
				);
			}
		}
	}
	/**
	 * Método que realiza la jugada de la máquina
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void jugarMaquina()
	{
		int[][] tablero= new int[6][10];
		for(int i= 0; i<6; i++)
		{
			for (int j = 0; j < 10; j++) 
			{
				tablero[i][j]= (int)(botonesTablero[i][j].getClientProperty("User")); //Se obtiene el tablero actualizado desde la interfaz.
			}
		}
		Nodo inicial= new Nodo(new Estado(tablero), -1, 0);
		inicial.setPosicion(0); //Se actualiza el nodo inicial con el nuevo tablero.
		cero.set(0, inicial);
		crearArbol(); //Se crea el árbol para conocer las posiblies jugadas.		
		Nodo decision= getPrimero().get(calcularDecisionMinMax()); //Se crea un nodo con el estado decidio a través de minimax.
		actualizarTablero(decision.getEstado().getTablero()); //Se actualiza el tablero en la interfaz gráfica.
		int[][] tableroComprobacion= new int[6][10];
		//--------------------------Se comprueba si el juego terminó
		for(int i= 0; i<6; i++)
		{
			for (int j = 0; j < 10; j++) 
			{
				tableroComprobacion[i][j]= (int)(botonesTablero[i][j].getClientProperty("User")); 
			}
		}
		Nodo comprobacion= new Nodo(new Estado(tableroComprobacion), -1, 0);
		if(comprobacion.getEstado().juegoAcabado())
		{
			if(comprobacion.getEstado().puntacionNegro()>comprobacion.getEstado().puntacionRojo())
			{
				JOptionPane.showMessageDialog(ventanaInicial, "Ganó el computador, una IA es superior a ti");
			}
			else
			{
				if(comprobacion.getEstado().puntacionNegro()<comprobacion.getEstado().puntacionRojo())
				{
					JOptionPane.showMessageDialog(ventanaInicial, "Ganaste... ¡¿ganaste?! ¿Qué clase de brujería es esta?");
				}
				else
				{
					JOptionPane.showMessageDialog(ventanaInicial, "Empate... se supone que esto no debía pasar");
				}
			}
		}
		//--------------------------Se comprueba si el juego terminó
		primero= new Vector(0,1); //Se reinician los vectores donde se guardan los niveles del árbol.
		segundo= new Vector(0,1);
		tercero= new Vector(0,1);
		contadorTurno= 0; //Se reinicia el contador de turno para el usuario.
		largoTiro= 1; //Se determina que la primera jugada tendrá una posición de alcance.
		ventanaInicial.actualizarTurno("Usuario"); //Se da paso al turno del usuario.
		activarDesactivarBotones(1); //Se reactivan los botones para la interacción del usuario con Linja
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton btn = (JButton) (e.getSource());
		x = (int)btn.getClientProperty("row");
		y = (int)btn.getClientProperty("column");
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable cero
	 */
	public Vector<Nodo> getCero() {
		return cero;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param cero El nuevo valor que sera asignado a la variable cero
	 */
	public void setCero(Vector<Nodo> cero) {
		this.cero = cero;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable primero
	 */
	public Vector<Nodo> getPrimero() {
		return primero;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param primero El nuevo valor que sera asignado a la variable primero
	 */
	public void setPrimero(Vector<Nodo> primero) {
		this.primero = primero;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable segundo
	 */
	public Vector<Nodo> getSegundo() {
		return segundo;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param segundo El nuevo valor que sera asignado a la variable segundo
	 */
	public void setSegundo(Vector<Nodo> segundo) {
		this.segundo = segundo;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable tercero
	 */
	public Vector<Nodo> getTercero() {
		return tercero;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param tercero El nuevo valor que sera asignado a la variable tercero
	 */
	public void setTercero(Vector<Nodo> tercero) {
		this.tercero = tercero;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable botonesTablero
	 */
	public JButton[][] getBotonesTablero() {
		return botonesTablero;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param botonesTablero El nuevo valor que sera asignado a la variable botonesTablero
	 */
	public void setBotonesTablero(JButton[][] botonesTablero) {
		this.botonesTablero = botonesTablero;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable ventanaInicial
	 */
	public PrincipalVista getVentanaInicial() {
		return ventanaInicial;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param ventanaInicial El nuevo valor que sera asignado a la variable ventanaInicial
	 */
	public void setVentanaInicial(PrincipalVista ventanaInicial) {
		this.ventanaInicial = ventanaInicial;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param x El nuevo valor que sera asignado a la variable x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param y El nuevo valor que sera asignado a la variable y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable largoTiro
	 */
	public int getLargoTiro() {
		return largoTiro;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param largoTiro El nuevo valor que sera asignado a la variable largoTiro
	 */
	public void setLargoTiro(int largoTiro) {
		this.largoTiro = largoTiro;
	}

	/**
	 * Metodo get de la variable bare_field_name
	 * @return El valor actual de la variable contadorTurno
	 */
	public int getContadorTurno() {
		return contadorTurno;
	}

	/**
	 * Metodo set de la variable bare_field_name
	 * @param contadorTurno El nuevo valor que sera asignado a la variable contadorTurno
	 */
	public void setContadorTurno(int contadorTurno) {
		this.contadorTurno = contadorTurno;
	}
}
