import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author Miguel Ángel Askar Rodríguez - 201355842
 * @author Danny Fernando Cruz Arango - 201449949
 * Clase encargada de gestionar los archivos, hay que tener presente, que estos deben estar  
 * al nivel del src o al nivel del jar, según corresponda. (Para este proyecto lee siempre sdk.txt).
 */

public class Archivos 
{
	private String leido;
	/**
	 * 
	 * Método constructor de la clase Archivos
	 */
	public Archivos()
	{
		leido="";
	}
	
	/**
	 * Método encargado de leer los archivos
	 * @param nombre Nombre del archivo a leer
	 */
	@SuppressWarnings("resource")
	public void leer(String nombre) //Lee el archivo con el nombre que el usuario ingresa
	{
		String resultado="";
		try
		{
			FileReader lector=new FileReader(nombre);
			BufferedReader contenido=new BufferedReader(lector);
			String texto="";			
			while((texto=contenido.readLine())!=null)
			{
				resultado+= (texto + "\n");//Guarda en un string, el texto con los saltos de línea incluídos.
			}	
		}
		catch(Exception e)
		{
			System.out.println("Error al leer el archivo");
		}
		leido= resultado;
	}

	/**
	 * Método Get de la variable leido
	 * @return El valor de leido
	 */
	public String getLeido() {
		return leido;		
	}

	/**
	 * Método set de la variable leido
	 * @param leido El valor de leido a modificar
	 */
	public void setLeido(String leido) {
		this.leido = leido;
	}	
}
