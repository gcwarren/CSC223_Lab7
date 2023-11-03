package preprocessor.delegates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import geometry_objects.Segment;
import geometry_objects.delegates.intersections.SegmentIntersectionDelegate;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;

public class ImplicitPointPreprocessor
{
	/**
	 * It is possible that some of the defined segments intersect
	 * and points that are not named; we need to capture those
	 * points and name them.
	 * 
	 * Algorithm:
	 *    From the Segments given:
	 *    	Calculate the intersection of all permutations of two segments
	 *    	Check that the intersection is on both segments, using the following formula:
	 *    		for each segment AB, to check the potential point N, calculate
	 *     		(dist(A, N) + dist(N, B)) == dist(A, B)
	 *    	Then add it to the set of implicitPoints
	 */
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments)
	{
		Set<Point> implicitPoints = new LinkedHashSet<Point>();
		
		Map<Segment, Set<Segment>> segmentPermutations = findSegmentPermutations(givenSegments);
		
		for (Segment keySeg: segmentPermutations.keySet()) {
			for (Segment valueSeg : segmentPermutations.get(keySeg)) {
				if (getValidImplicitPoint(keySeg, valueSeg) != null) {
					Point implicit = getValidImplicitPoint(keySeg, valueSeg);
					if (givenPoints.getPoint(implicit) == null) {
						implicitPoints.add(implicit);
						givenPoints.put(Point.ANONYMOUS, implicit.getX(), implicit.getY());	
					}
				}
			}
		}

		return implicitPoints;
	}
	
	public static Map<Segment, Set<Segment>> findSegmentPermutations(List<Segment> givenSegments) {
		
		Map<Segment, Set<Segment>> segPerm = new HashMap<Segment, Set<Segment>>();
		
		for (int index = 0; index < givenSegments.size(); index++) {
			
			Segment currSegKey = givenSegments.get(index);
			Set<Segment> edgeSegs = new HashSet<Segment>();
			for (int count = index++; index < segPerm.size(); count++) {
				edgeSegs.add(givenSegments.get(count));
			}
			segPerm.put(currSegKey, edgeSegs);
		}
		return segPerm;
	}
	
	public static Point getValidImplicitPoint(Segment seg1, Segment seg2) {
		
		return SegmentIntersectionDelegate.findIntersection(seg1, seg2);
	}

}
