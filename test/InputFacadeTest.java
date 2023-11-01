import static org.junit.Assert.assertEquals;

import java.util.AbstractMap;
import java.util.Map;

import org.junit.Test;

import geometry_objects.points.PointDatabase;
import input.InputFacade;

public class InputFacadeTest {
	
	@Test
	void testToGeometryRepresentation() {
		
	}
	
	@Test
	void testConvertToPoints() {
		
	}
	
	@Test
	void testConvertToSegments() {
		
	}
	
	@Test
	void testTriangleToGeoRepresentation() {
		AbstractMap.Entry<PointDatabase, Set<Segment>> testMap = createTriangleMap();
		AbstractMap.Entry<PointDatabase, Set<Segment>> geometryMap = InputFacade.toGeometryRepresentation("single_triangle.json");
		PointDatabase testPointDB = testMap.getKey();
		PointDatabase geoPointDB = geometryMap.getKey();
		Set<Segment> testSegment = testMap.getValue();
		Set<Segment> geoSegment = geometryMap.getValue();
		
		assertEquals(0, testPointDB.getPoint("A").compareTo(geoPointDB.getPoint("A")));
		assertEquals(0, testPointDB.getPoint("B").compareTo(geoPointDB.getPoint("B")));
		assertEquals(0, testPointDB.getPoint("C").compareTo(geoPointDB.getPoint("C")));
		
		assertTrue(testSegment.containsAll(geoSegment));
		assertTrue(geoSegment.containsAll(testSegment));
		
	}
}
