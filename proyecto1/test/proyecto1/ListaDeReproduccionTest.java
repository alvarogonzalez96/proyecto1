package proyecto1;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListaDeReproduccionTest {

	private ListaDeReproduccion lr1;
	private ListaDeReproduccion lr2;
	private final File FIC_TEST1 = new File("/Users/Alvaro/Desktop/res/No del grupo.mp4");
	
	@Before
	public void setUp() throws Exception {
		lr1 = new ListaDeReproduccion();
		lr2 = new ListaDeReproduccion();
		lr2.add(FIC_TEST1);
	}

	@After
	public void tearDown() throws Exception {
		lr2.clear();
	}
	
	@Test
	public void irARandom() {
		String carpetaTest = "/Users/Alvaro/desktop/res/";
		String filtroTest = "*Pentatonix*.mp4";
		
		ListaDeReproduccion lr = new ListaDeReproduccion();
		lr.add(carpetaTest, filtroTest);
		
		assertEquals(true, lr.irARandom());
	}
	
	@Test
	public void addCarpeta() {
		String carpetaTest = "/Users/Alvaro/desktop/res/";
		String filtroTest = "*Pentatonix*.mp4";
		
		ListaDeReproduccion lr = new ListaDeReproduccion();
		lr.add(carpetaTest, filtroTest);
		
		assertEquals(3, lr.add(carpetaTest, filtroTest));
		
		//fail("Método sin acabar");
	}

	//Chequeo de error por getFic(índice) por encima de final
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_Exc1() {
			lr1.getFic(0); 
	}
	
	//Chequeo de error por get(índice) por debajo de 0
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_Exc2() {
		lr2.getFic(-1);
	}
	
	//Chequeo de funcionamiento correcto de get(índice)
	@Test
	public void testGet() {
		assertEquals(FIC_TEST1, lr2.getFic(0));
	}
	
	//Chequeo de intercambio de elementos
	@Test
	public void testIntercambio() {
		
	} 
	
	//Chequeo de añadido y borrado de elementos
	@Test
	public void testAdd() {
		
	}
	
	//Chequeo de tamaño
	@Test
	public void testSize() {
		
	}
	
	
	public void test() {
		
		//fail("Not yet implemented");
	}

}
