package geometry_objects;

import java.util.HashSet;
import geometry_objects.points.*;

import org.junit.jupiter.api.Test;

public class SegmentTest {
	
	public Segment builder() {
		Segment _segment = new Segment(new Point("A", 1,1), new Point("B", 5,4));
		
		return _segment;
	}
	
	@Test 
	public hasSubSegmentTest() {
		
		
	}
	
	@Test
	public coincideWithoutOverlapTest() {
		
	}
	
	@Test
	public collectOrderedPointsOnSegmentTest() {
		
		Set<Point> givenPoints = new HashSet<Point>();
		
	}

}
