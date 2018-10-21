package proyecto1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


/** Clase para crear instancias como listas de reproducción,
 * que permite almacenar listas de ficheros con posición de índice
 * (al estilo de un array / arraylist)
 * con marcas de error en los ficheros y con métodos para cambiar la posición
 * de los elementos en la lista, borrar elementos y añadir nuevos.
 */
public class ListaDeReproduccion implements ListModel<String> {
	ArrayList<File> ficherosLista;     // ficheros de la lista de reproducción
	int ficheroEnCurso = -1;           // Fichero seleccionado (-1 si no hay ninguno seleccionado)
	
	/** Devuelve uno de los ficheros de la lista
	 * @param posi	Posición del fichero en la lista (de 0 a size()-1)
	 * @return	Devuelve el fichero en esa posición
	 * @throws IndexOutOfBoundsException	Si el índice no es válido
	 */
	public File getFic( int posi ) throws IndexOutOfBoundsException {
		return ficherosLista.get( posi );
	}	
	
	//Constructor por defecto que inicializa la lista vacía
	public ListaDeReproduccion() {
		super();
		this.ficherosLista = new ArrayList<File>();
		this.ficheroEnCurso = -1;
		this.misEscuchadores = new ArrayList<>();
	}
	
	//Intercambia las dos posiciones
	public void intercambia(int posi1, int posi2) {
		ArrayList<File> aux = new ArrayList<>();
		if(ficherosLista.get(posi1) != null && ficherosLista.get(posi2) != null && posi1 > 0 && posi2 > 0) {
			for (int i = 0; i < ficherosLista.size(); i++) {
				if(i == posi1) {
					aux.add(ficherosLista.get(posi2));
				} else if(i == posi2) {
					aux.add(ficherosLista.get(posi1));
				} else {
					aux.add(ficherosLista.get(i));
				}
			}
			ficherosLista.clear();
			ficherosLista.addAll(aux);
		}
	}
	
	//Devuelve el número de elementos de la lista
	public int size() {
		return ficherosLista.size();
	}
	
	//Añade un fichero al final de la lista
	public void add(File f) {
		ficherosLista.add(f);
	}

	//Eliminar un fichero de la lista dada su posición
	public void removeFic(int posi) {
		ficherosLista.remove(posi);
	}
	
	//Borra la lista
	public void clear() {
		ficherosLista.clear();
	}
	
	/** Añade a la lista de reproducción todos los ficheros que haya en la 
	 * carpeta indicada, que cumplan el filtro indicado.
	 * Si hay cualquier error, la lista de reproducción queda solo con los ficheros
	 * que hayan podido ser cargados de forma correcta.
	 * @param carpetaFicheros	Path de la carpeta donde buscar los ficheros
	 * @param filtroFicheros	Filtro del formato que tienen que tener los nombres de
	 * 							los ficheros para ser cargados.
	 * 							String con cualquier letra o dígito. Si tiene un asterisco
	 * 							hace referencia a cualquier conjunto de letras o dígitos.
	 * 							Por ejemplo p*.* hace referencia a cualquier fichero de nombre
	 * 							que empiece por p y tenga cualquier extensión.
	 * @return	Número de ficheros que han sido añadidos a la lista
	 */
	private static Logger logger = Logger.getLogger(ListaDeReproduccion.class.getName());

	private static final boolean ANYADIR_A_FIC_LOG = false; //poner true para no sobreescribir
	static {
		try {
			logger.addHandler(new FileHandler(
					ListaDeReproduccion.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creación fichero log");
		}
	}
	
	
	public int add(String carpetaFicheros, String filtroFicheros) {
		// TODO: Codificar este método de acuerdo a la práctica (pasos 3 y sucesivos)
		logger.log(Level.INFO, "Añadiendo ficheros con filtro " + filtroFicheros);
		filtroFicheros = filtroFicheros.replaceAll("\\.", "\\\\.").replaceAll("\\*", "\\.*");  // Pone el símbolo de la expresión regular \. donde figure un .
		logger.log(Level.INFO, "Ficheros añadidos  " + filtroFicheros);
		
		File fInic = new File(carpetaFicheros);
		String patron = filtroFicheros;
		System.out.println(patron);
		Pattern pat = Pattern.compile(patron);  // Se compila
		int n = 0;
		
		if(fInic.isDirectory()) {
			for (File f : fInic.listFiles()) {
				logger.log(Level.FINE, "Procesando fichero" + f.getName());
				//TODO: Comprobar que f.getName() cumple el patrón y añadirlo a la lista
				if (pat.matcher(f.getName()).matches()) {
					ficherosLista.add(f);
					logger.log(Level.INFO, "AÑADIDO!!! " + f.getName());
					n++;
				} else {
					logger.log(Level.SEVERE, "No se ha añadido a la lista");
				}
			}
		}
		return n;
	}
	
	
	//
	// Métodos de selección
	//
	
	//Seleción random
	public boolean irARandom() {
		boolean b = true;
		int n = (int)(Math.random()*ficherosLista.size()+1);
		
		if(n > ficherosLista.size()) {
			b = false;
		} else if(n < ficherosLista.size()) {
			b = false;
		} else {
			b = true;
			ficheroEnCurso = n;
		}
		
		return b;
	}
	
	/** Seleciona el primer fichero de la lista de reproducción
	 * @return	true si la selección es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irAPrimero() {
		ficheroEnCurso = 0;  // Inicia
		if (ficheroEnCurso>=ficherosLista.size()) {
			ficheroEnCurso = -1;  // Si no se encuentra, no hay selección
			return false;  // Y devuelve error
		}
		return true;
	}
	
	/** Seleciona el último fichero de la lista de reproducción
	 * @return	true si la selección es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irAUltimo() {
		ficheroEnCurso = ficherosLista.size()-1;  // Inicia al final
		if (ficheroEnCurso==-1) {  // Si no se encuentra, no hay selección
			return false;  // Y devuelve error
		}
		return true;
	}

	/** Seleciona el anterior fichero de la lista de reproducción
	 * @return	true si la selección es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irAAnterior() {
		if (ficheroEnCurso>=0) ficheroEnCurso--;
		if (ficheroEnCurso==-1) {  // Si no se encuentra, no hay selección
			return false;  // Y devuelve error
		}
		return true;
	}

	/** Seleciona el siguiente fichero de la lista de reproducción
	 * @return	true si la selección es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irASiguiente() {
		ficheroEnCurso++;
		if (ficheroEnCurso>=ficherosLista.size()) {
			ficheroEnCurso = -1;  // Si no se encuentra, no hay selección
			return false;  // Y devuelve error
		}
		return true;
	}

	/** Devuelve el fichero seleccionado de la lista
	 * @return	Posición del fichero seleccionado en la lista de reproducción (0 a n-1), -1 si no lo hay
	 */
	public int getFicSeleccionado() {
		return ficheroEnCurso;
	}

	//
	// Métodos de DefaultListModel
	//
	
	@Override
	public int getSize() {
		return ficherosLista.size();
	}

	@Override
	public String getElementAt(int index) {
		return ficherosLista.get(index).getName();
	}

		// Escuchadores de datos de la lista
		ArrayList<ListDataListener> misEscuchadores = new ArrayList<>();
	@Override
	public void addListDataListener(ListDataListener l) {
		misEscuchadores.add( l );
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		misEscuchadores.remove( l );
	}
	
	// Llamar a este método cuando se añada un elemento a la lista
	// (Utilizado para avisar a los escuchadores de cambio de datos de la lista)
	private void avisarAnyadido( int posi ) {
		for (ListDataListener ldl : misEscuchadores) {
			ldl.intervalAdded( new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, posi, posi ));
		}
	}
}
