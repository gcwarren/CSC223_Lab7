package geometry_objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;


public class PointTest {

		//Test Points for Point(double, double) constructor
		Point a = new Point(3, 6);
		Point b = new Point(2, 4);
		Point c = new Point(4.00045, 5.79034);
		Point d = new Point(9.00458, 7.00983);

		Point e = new Point(0, 6);
		Point f = new Point(2, 4.0);
		Point g = new Point(4.00045, 5.79034);
		Point h = new Point(9.00458, 7.0098);
		
		//Test Points for Point(string, double, double) constructor
		Point ax = new Point("A", 3, 6);
		Point bx = new Point("B", 4.00045, 5.79034);
		Point cx = new Point("C", 9.00458, 7.00983);

		Point dx = new Point("D", 0, 0);
		Point ex = new Point("E", 4.00045, 5.79034);
		Point fx = new Point("X", 9.00458, 7.0098);

		
		@Test
		void testIsUnamed() {
			
		}
		
		@Test 
		void testHashCode() {
			
		}
		
		@Test
		void testLexicographicOrdering() {
			
		}
		
		@Test
		void testCompareTo() {
			Point testPointNull = new Point(null, 0, 0);
			assertEquals(testPointNull.compareTo(null), 1);
			
			assertEquals(b.compareTo(f), b);
			
			assertNotEquals(b.compareTo(d), b);
		}
		
		@Test
		void testEquals() {
			//Tests for Point(double, double) constructor
			assertFalse(a.equals(e));
			assertTrue(b.equals(f));
			assertTrue(c.equals(g));
			assertFalse(d.equals(h));
			//Tests for Point(string, double, double) constructor
			assertFalse(ax.equals(dx));
			assertTrue(bx.equals(ex));
			assertFalse(cx.equals(fx));
		}
}
