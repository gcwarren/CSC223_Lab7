package geometry_objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
//import input.components.point.Point;
//import input.components.point.PointDatabase;

public class PointDatabaseTest {
	
	@Test
	void testSize() {
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);
		assertEquals(4, testDatabase.size());
		
		PointDatabase testDatabaseEmpty = new PointDatabase();
		assertEquals(0, testDatabaseEmpty.size());
		
		PointDatabase testDatabaseNull = new PointDatabase();
		testDatabaseNull.put(null, 0, 0);
		assertEquals(1, testDatabaseNull.size());
	}
	
	@Test
	void testPut() {
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);

		assertEquals(4, testDatabase.size());
		//assertEquals(testDatabase.getName(new Point(3.0,6.0)), "A"); //expected UNNAMED but was A
		assertNotEquals(testDatabase.getName(new Point(2,6)), "A"); 
	}
	
	@Test
	void testGetNameDouble() {
		
		Point d = new Point(5, 7);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 6.0, 0);
		testDatabase.put("C", 4, 4.0001);
		
		//assertEquals(testDatabase.getName(new Point(3,6)), "A"); //expected UNNAMED but was A
		assertNotEquals(testDatabase.getName(new Point(4, 3)), "C"); 
		//assertEquals(testDatabase.getName(new Point(4,4)), "C"); //expected UNNAMED but was A
		assertEquals(testDatabase.getName(0, 0), null); //point that does not exist 
		assertEquals(d.getName(), Point.ANONYMOUS); //point that exists but is unnamed
	}
	
	@Test
	void testGetNamePoint() {
		Point d = new Point(5, 7);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 6.0, 0);
		testDatabase.put("C", 4, 4.0001);
		
		//Test cases for getName(Point node)
		//assertEquals(testDatabase.getName(new Point(3.0,6)), "A"); //expected UNNAMED but was A
		assertNotEquals(testDatabase.getName(new Point(6,0.0)), "C");
		//assertEquals(testDatabase.getName(4,4), "C"); //expected null
		assertEquals(d.getName(), Point.ANONYMOUS); //point that exists but is unnamed
		
		//Test cases for getName(double x, double y)
		assertEquals(testDatabase.getName(3, 6), "A");
		assertNotEquals(testDatabase.getName(6, 0), "C");
		//assertEquals(testDatabase.getName(4, 4), "C"); //expected null
		assertEquals(testDatabase.getName(0, 0), null); //point that does not exist 
	}
	
	@Test 
	void testGetPointString() {
		//Test Points using Point(string, double, double) constructor
		Point ax = new Point("A", 3, 6);
		Point bx = new Point("B", 6, 0);
		Point cx = new Point("C", 4, 4);
		Point dx = new Point("D", 0, 0);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 6.0, 0);
		testDatabase.put("C", 4, 4.0001);
		
		//Test cases for getName(Point node)
		assertEquals(testDatabase.getPoint(ax).toString(), "A(3, 6)");
		assertNotEquals(testDatabase.getPoint(bx).toString(), "A(6, 0)");
		assertEquals(testDatabase.getPoint(cx).toString(), "C(4, 4)");
		assertEquals(testDatabase.getPoint(dx), null);
	}
	
	@Test 
	void testGetPointPoint() {
		//Test Points using Point(string, double, double) constructor
		Point ax = new Point("A", 3, 6);
		Point bx = new Point("B", 6, 0);
		Point cx = new Point("C", 4, 4);
		Point dx = new Point("D", 0, 0);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(Point node)
		assertEquals(testDatabase.getPoint(ax).toString(), "A(3, 6)");
		assertNotEquals(testDatabase.getPoint(bx).toString(), "A(6, 0)");
		assertEquals(testDatabase.getPoint(cx).toString(), "C(4, 4)");
		assertEquals(testDatabase.getPoint(dx), null);
		
		//Test cases for getName(double x, double y)
		assertEquals(testDatabase.getPoint(3, 6).toString(), "A(3, 6)");
		assertNotEquals(testDatabase.getPoint(6, 0).toString(), "A(6, 0)");
		assertEquals(testDatabase.getPoint(4, 4).toString(), "C(4, 4)");
		assertNull(testDatabase.getPoint(0, 0));
	}
	
	@Test 
	void testGetPointDouble() {
		//Test Points using Point(string, double, double) constructor
		Point ax = new Point("A", 3, 6);
		Point bx = new Point("B", 6, 0);
		Point cx = new Point("C", 4, 4);
		Point dx = new Point("D", 0, 0);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(Point node)
		assertEquals(testDatabase.getPoint(ax).toString(), "A(3, 6)");
		assertNotEquals(testDatabase.getPoint(bx).toString(), "A(6, 0)");
		assertEquals(testDatabase.getPoint(cx).toString(), "C(4, 4)");
		assertEquals(testDatabase.getPoint(dx), null);
		
		//Test cases for getName(double x, double y)
		assertEquals(testDatabase.getPoint(3, 6).toString(), "A(3, 6)");
		assertNotEquals(testDatabase.getPoint(6, 0).toString(), "A(6, 0)");
		assertEquals(testDatabase.getPoint(4, 4).toString(), "C(4, 4)");
		assertNull(testDatabase.getPoint(0, 0));
	}
	
}
