package proyecto1;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListaDeReproduccionTest {

	private ListaDeReproduccion lr1;
	private ListaDeReproduccion lr2;
	private final File FIC_TEST1 = new File("test/res/No del grupo.mp4");
	
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
	public void test() {
		fail("Not yet implemented");
	}

}
