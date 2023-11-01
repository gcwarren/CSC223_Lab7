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
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertEquals(PNF.get(point1), point1);
		assertEquals(PNF.get(point2), point2);
		assertEquals(PNF.get(point3), point3);
	}
	
	@Test 
	void getDoubleTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertEquals(PNF.get(2.0, 5.0), point1);
		assertEquals(PNF.get(3.0, 6.0), point2);
		assertEquals(PNF.get(4.0, 7.0), point3);
	}
	
	@Test 
	void containsPointTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertTrue(PNF.contains(point1));
		assertTrue(PNF.contains(point2));
		assertTrue(PNF.contains(point3));
	}
	
	@Test 
	void containsDoubleTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertTrue(PNF.contains(2.0, 5.0));
		assertTrue(PNF.contains(3.0, 6.0));
		assertTrue(PNF.contains(4.0, 7.0));
	}
	
	@Test 
	void getAllPointsTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertEquals(PNF.getAllPoints().size(), 3);
	}
	
	@Test 
	void clearTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertNull(PNF.clear());
	}
	
	@Test 
	void sizeTest() {
		
	}
}
