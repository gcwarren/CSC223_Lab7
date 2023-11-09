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
		
		Point pointC = new Point("C", 1, 2);
		
		Segment segment1 = new Segment(pointC, new Point("B", 5,4)); 
		Segment segment2 = new Segment(new Point("D", 3,3), new Point("E", 2, 2.5));
		
		
		assertTrue(segment.HasSubSegment(segment1));
		assertTrue(segment.HasSubSegment(segment2));

		
		
		
	}
	
	@Test
	public void coincideWithoutOverlapTest() {
		
	}
	
	@Test
	public void collectOrderedPointsOnSegmentTest() {
		
		Set<Point> givenPoints = new HashSet<Point>();
		
	}

}
