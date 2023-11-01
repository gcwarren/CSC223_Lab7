package geometry_objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		Point point = new Point("A1", 2.0, 3.0);
		Point addedPoint = PNF.put(point); 
		
		assertEquals(point, addedPoint);	
		
	}
	
	//test put(double x, double y) 
	@Test
	void insertionDoubleTest() { 
		PointNamingFactory PNF = new PointNamingFactory();
		//point coordinates 
		double x = 2.0;
		double y = 3.0;
		
		Point addedPoint = PNF.put(x, y); 
		
		assertEquals(x, addedPoint.getX(), 0.0001);
		assertEquals(y, addedPoint.getY(), 0.0001);
	}

}
