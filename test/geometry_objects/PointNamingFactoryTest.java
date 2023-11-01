package geometry_objects;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;
import geometry_objects.points.PointNamingFactory;

public class PointNamingFactoryTest {
	
	@Test
	void nullTest() {
	
	}
	
	//test the constructor to ensure that it initializes correctly 
	@Test
	void testInitilization() {
		PointNamingFactory PNF = new PointNamingFactory(); 
		
		//asserts that the PNF object is not null
		assertNotNull(PNF);
		
	}
	
	//test put(point pt)
	@Test
	void insertionPointTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point("A", 2.0, 3.0)); 
		
		assertTrue(PNF.contains(2.0, 3.0));
	}
	
	//test put(double x, double y) 
	@Test
	void insertionDoubleTest() { 
		PointNamingFactory PNF = new PointNamingFactory();
		//point coordinates 
		double x = 2.0;
		double y = 3.0;
		
		PNF.put(x, y); 
		
		assertTrue(PNF.contains(x, y));
	}

}
