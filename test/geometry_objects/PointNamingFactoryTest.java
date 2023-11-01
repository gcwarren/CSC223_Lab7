package geometry_objects;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;
import geometry_objects.points.PointNamingFactory;

public class PointNamingFactoryTest {
	
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
		Point putPointTester = new Point("B", 2.0, 4.0);
		PNF.put(putPointTester);
		PNF.put(3.0, 7.0);
		
		assertTrue(PNF.contains(2.0, 3.0)); 
		assertTrue(PNF.contains(new Point(3.0, 7.0))); 
		assertTrue(PNF.contains(putPointTester));
	}
	
	//test put(double x, double y) 
	@Test
	void insertionDoubleTest() { 
		PointNamingFactory PNF = new PointNamingFactory();
		//point coordinates 
		double x1 = 2.0;
		double x2 = 3.0;
		double x3 = 4.0;
		double y1 = 5.0;
		double y2 = 6.0;
		double y3 = 7.0;
		PNF.put(x1, y1); 
		PNF.put(x2, y2);
		PNF.put(x3, y3);
		
		assertTrue(PNF.contains(x1, y1));
		assertTrue(PNF.contains(x2, y2));
		assertTrue(PNF.contains(x3, y3));
	}
	
	@Test
	void getPointTest() {
		
	}
	
	@Test 
	void getDoubleTest() {
		
	}
	
	@Test 
	void containsPointTest() {
		
	}
	
	@Test 
	void containsDoubleTest() {
		
	}
	
	@Test 
	void getCurrentNameTest() {
		
	}
	
	@Test 
	void getAllPointsTest() {
		
	}
	
	@Test 
	void clearTest() {
		
	}
	
	@Test 
	void sizeTest() {
		
	}
}
