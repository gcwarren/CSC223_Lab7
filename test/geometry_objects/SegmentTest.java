package geometry_objects;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import geometry_objects.points.*;

import org.junit.jupiter.api.Test;

public class SegmentTest {
	
	public Segment builder() {
		Segment _segment = new Segment(new Point("A", 1,2), new Point("B", 5,4));
		
		return _segment;
	}
	
	@Test 
	public void hasSubSegmentTest() {
		Segment segment = builder(); 
		
		Point pointA = new Point("A", 1,2);
		Point pointB = new Point("B", 5,4);
		Point pointC = new Point("C", 1, 2);
		Point pointD = new Point("D", 3, 3);
		Point pointE = new Point("E", 2, 2.5);
		
		
		Segment segment1 = new Segment(pointC, pointB); 
		Segment segment2 = new Segment(pointD, pointE);
		Segment segment3 = new Segment(pointA, pointD);
		
		assertTrue(segment.HasSubSegment(segment1));
		assertTrue(segment.HasSubSegment(segment2));
		assertTrue(segment.HasSubSegment(segment3));

		
		
		
	}
	
	@Test
	public void coincideWithoutOverlapTest() {
		
	}
	
	@Test
	public void collectOrderedPointsOnSegmentTest() {
		
		Set<Point> givenPoints = new HashSet<Point>();
		
	}

}
