package geometry_objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;

public class PointDatabaseTest {
	@Test
	void testPointDatabase() {
		//create a new pDB to test basic building and other methods
	}
	
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
		assertEquals(0, testDatabaseNull.size());
	}
	
	@Test
	void testPut() {
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);
		//need to find a way to assert points exists >> perhaps get? 
		assertEquals(4, testDatabase.size());
	}
	
	@Test
	void testGetNameDouble() {
		//Test PointNodes using PointNode(double, double) constructor
		PointNode d = new PointNode(5, 7);
		
		//Test PointNodes using PointNode(string, double, double) constructor
		PointNode ax = new PointNode("A", 3, 6);
		PointNode bx = new PointNode("B", 6, 0);
		PointNode cx = new PointNode("C", 4, 4);
		
		PointNodeDatabase testDatabase = new PointNodeDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(PointNode node)
		assertEquals(ax.getName(), "A");
		assertNotEquals(bx.getName(), "C");
		assertEquals(cx.getName(), "C");
		assertEquals(d.getName(), PointNode.ANONYMOUS); //point that exists but is unnamed
		
		//Test cases for getName(double x, double y)
		assertEquals(testDatabase.getName(3, 6), "A");
		assertNotEquals(testDatabase.getName(6, 0), "C");
		assertEquals(testDatabase.getName(4, 4), "C");
		assertEquals(testDatabase.getName(0, 0), null); //point that does not exist 
	}
	
	@Test
	void testGetNamePoint() {
		//Test PointNodes using PointNode(double, double) constructor
		PointNode d = new PointNode(5, 7);
		
		//Test PointNodes using PointNode(string, double, double) constructor
		PointNode ax = new PointNode("A", 3, 6);
		PointNode bx = new PointNode("B", 6, 0);
		PointNode cx = new PointNode("C", 4, 4);
		
		PointNodeDatabase testDatabase = new PointNodeDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(PointNode node)
		assertEquals(ax.getName(), "A");
		assertNotEquals(bx.getName(), "C");
		assertEquals(cx.getName(), "C");
		assertEquals(d.getName(), PointNode.ANONYMOUS); //point that exists but is unnamed
		
		//Test cases for getName(double x, double y)
		assertEquals(testDatabase.getName(3, 6), "A");
		assertNotEquals(testDatabase.getName(6, 0), "C");
		assertEquals(testDatabase.getName(4, 4), "C");
		assertEquals(testDatabase.getName(0, 0), null); //point that does not exist 
	}
	
	@Test 
	void testGetPointString() {
		//Test PointNodes using PointNode(string, double, double) constructor
		PointNode ax = new PointNode("A", 3, 6);
		PointNode bx = new PointNode("B", 6, 0);
		PointNode cx = new PointNode("C", 4, 4);
		PointNode dx = new PointNode("D", 0, 0);
		
		PointNodeDatabase testDatabase = new PointNodeDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(PointNode node)
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
	void testGetPointPoint() {
		//Test PointNodes using PointNode(string, double, double) constructor
		PointNode ax = new PointNode("A", 3, 6);
		PointNode bx = new PointNode("B", 6, 0);
		PointNode cx = new PointNode("C", 4, 4);
		PointNode dx = new PointNode("D", 0, 0);
		
		PointNodeDatabase testDatabase = new PointNodeDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(PointNode node)
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
		//Test PointNodes using PointNode(string, double, double) constructor
		PointNode ax = new PointNode("A", 3, 6);
		PointNode bx = new PointNode("B", 6, 0);
		PointNode cx = new PointNode("C", 4, 4);
		PointNode dx = new PointNode("D", 0, 0);
		
		PointNodeDatabase testDatabase = new PointNodeDatabase();
		testDatabase.put(ax);
		testDatabase.put(bx);
		testDatabase.put(cx);
		
		//Test cases for getName(PointNode node)
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
