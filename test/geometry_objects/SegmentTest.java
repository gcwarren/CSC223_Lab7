package geometry_objects;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import geometry_objects.points.*;
import org.junit.jupiter.api.Test;

public class SegmentTest {
	
	public Set<Point> pointBuilder() {
		Set<Point> _points = new HashSet<Point>();
		_points.add(new Point(18, 6));
		_points.add(new Point(21, 8));
		_points.add(new Point(24, 11));
		_points.add(new Point(8, 9));
		_points.add(new Point(30, 36));
		return _points;
	}
	
	public Segment segBuilder() {
		Segment _segment = new Segment(new Point(15, 4), new Point(24, 10));
		return _segment;
	}
	
	@Test 
	public void hasSubSegmentTest() {
		
		
	}
	
	@Test
	public void coincideWithoutOverlapTest() {
		Segment segOne = segBuilder();
		Segment segTwo = new Segment(new Point(21, 8), new Point(30, 14));
		Segment segThree = new Segment(new Point(24, 10), new Point(30, 14));
		
		assertTrue(segOne.coincideWithoutOverlap(segThree));
		assertFalse(segOne.coincideWithoutOverlap(segTwo));
	}
	
	@Test
	public void collectOrderedPointsOnSegmentTest() {
		
		Set<Point> givenPoints = pointBuilder();
		Segment testSeg = segBuilder();
		
		Set<Point> testSS = new HashSet<Point>();
		testSS.add(new Point(15, 4));
		testSS.add(new Point(18, 6));
		testSS.add(new Point(21, 8));
		testSS.add(new Point(24, 10));
		
		assertEquals(testSeg.collectOrderedPointsOnSegment(givenPoints), testSS);
	}

}
