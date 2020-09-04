/**
 * Clase encargada de guardar los atributos de los nodos, con el fin de facilitar la navegación en el árbol que genera el agente.
 * @author Miguel Angel Askar Rodriguez - 1355842
 * @author Danny Fernando Cruz Arango   - 1449949
 */
public class Nodo 
{
	private Estado estado;
	private int padre;
	private int profundidad;
	private int posicion;
	private int puntajeRojo;
	private int puntajeNegro;
	private int minimax;
	
	/**
	 * @return the minimax
	 */
	public int getMinimax() {
		return minimax;
	}

	/**
	 * @param minimax the minimax to set
	 */
	public void setMinimax(int minimax) {
		this.minimax = minimax;
	}

	public Nodo(Estado estado, int padre, int profundidad)
	{
		this.estado= estado;
		this.padre= padre;
		this.profundidad= profundidad;		
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getPadre() {
		return padre;
	}

	public void setPadre(int padre) {
		this.padre = padre;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
	/**
	 * @return the puntajeRojo
	 */
	public int getPuntajeRojo() {
		return puntajeRojo;
	}

	/**
	 * @param puntajeRojo the puntajeRojo to set
	 */
	public void setPuntajeRojo(int puntajeRojo) {
		this.puntajeRojo = puntajeRojo;
	}

	/**
	 * @return the puntajeNegro
	 */
	public int getPuntajeNegro() {
		return puntajeNegro;
	}

	/**
	 * @param puntajeNegro the puntajeNegro to set
	 */
	public void setPuntajeNegro(int puntajeNegro) {
		this.puntajeNegro = puntajeNegro;
	}

	public void printEstado()
	{
		int[][] tablero= estado.getTablero();
		for(int i= 0; i<6; i++)
		{
			for(int j=0; j<10; j++)
			{
				System.out.print(tablero[i][j]+ " ");
			}
			System.out.println("");
		}
	}

}
