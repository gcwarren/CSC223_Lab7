package geometry_objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import geometry_objects.points.Point;
import geometry_objects.points.PointNamingFactory;

public class PointNamingFactoryTest {
	
	public void nullTest() {
	
	}
	
	//test the constructor to ensure that it initializes correctly 
	public void testInitilization() {
		PointNamingFactory PNF = new PointNamingFactory(); 
		
		//asserts that the PNF object is not null
		assertNotNull(PNF);
		
	}
	
	//test put(point pt)
	public void insertionPointTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point = new Point("A1", 2.0, 3.0);
		Point addedPoint = PNF.put(point); 
		
		assertEquals(point, addedPoint);
		
		
		
		
	}
	
	//test put(double x, double y) 
	public void insertionDoubleTest() { 
		
	}
	
	
	

}
