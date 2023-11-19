package preprocessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;
import geometry_objects.Segment;

public class Preprocessor
{
	// The explicit points provided to us by the user.
	// This database will also be modified to include the implicit
	// points (i.e., all points in the figure).
	protected PointDatabase _pointDatabase;

	// Minimal ('Base') segments provided by the user
	protected Set<Segment> _givenSegments;

	// The set of implicitly defined points caused by segments
	// at implicit points.
	protected Set<Point> _implicitPoints;

	// The set of implicitly defined segments resulting from implicit points.
	protected Set<Segment> _implicitSegments;

	// Given all explicit and implicit points, we have a set of
	// segments that contain no other subsegments; these are minimal ('base') segments
	// That is, minimal segments uniquely define the figure.
	protected Set<Segment> _allMinimalSegments;

	// A collection of non-basic segments
	protected Set<Segment> _nonMinimalSegments;

	// A collection of all possible segments: maximal, minimal, and everything in between
	// For lookup capability, we use a map; each <key, value> has the same segment object
	// That is, key == value. 
	protected Map<Segment, Segment> _segmentDatabase;
	public Map<Segment, Segment> getAllSegments() { return _segmentDatabase; }

	public Preprocessor(PointDatabase points, Set<Segment> segments)
	{
		_pointDatabase  = points;
		_givenSegments = segments;

		_segmentDatabase = new HashMap<Segment, Segment>();

		analyze();
	}

	/**
	 * Invoke the precomputation procedure.
	 */
	public void analyze()
	{
		//
		// Implicit Points
		//
		_implicitPoints = ImplicitPointPreprocessor.compute(_pointDatabase, _givenSegments.stream().toList());

		//
		// Implicit Segments attributed to implicit points
		//
		//Note to self: IMPLIED minimal segments
		_implicitSegments = computeImplicitBaseSegments(_implicitPoints);

		//
		// Combine the given minimal segments and implicit segments into a true set of minimal segments
		//     *givenSegments may not be minimal
		//     * implicitSegmen
		//
		//Note to self: ALL minimal segments 
		_allMinimalSegments = identifyAllMinimalSegments(_implicitPoints, _givenSegments, _implicitSegments);

		//
		// Construct all segments inductively from the base segments
		//
		//Note to self: ALL NON minimal segments
		_nonMinimalSegments = constructAllNonMinimalSegments(_allMinimalSegments);

		//
		// Combine minimal and non-minimal into one package: our database
		//
		_allMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
		_nonMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
	}

	/**
	 * If two segments cross at an unnamed point, the result is an implicit point.
	 *
	 * This new implicit point may be found on any of the existing segments (possibly
	 * with others implicit points on the same segment).
	 * This results in new, minimal sub-segments.
	 *
	 * For example,   A----*-------*----*---B will result in 4 new base segments.
	 */
	public Set<Segment> computeImplicitBaseSegments(Set<Point> implicitPoints) {

		Set<Segment> implicitSegments = new HashSet<Segment>();

		//for each segment in _givenSegments, 
		for (Segment seg : _givenSegments) {
			SortedSet<Point> ptsOnSeg = seg.collectOrderedPointsOnSegment(implicitPoints);

			//if there is at least one implicit point on the segment ,
			if (ptsOnSeg.size() > 2) { 
				//iterate over all points on the segment, and create all segments for which the segment is 
				//	minimal, and 
				//	contains two points which are not equal to one another 
				for (Point pt1 : ptsOnSeg) {
					for (Point pt2 : ptsOnSeg) {
						Segment implicitSeg = new Segment(pt1, pt2);
						if ((!pt1.equals(pt2)) && isMinimal(implicitSeg)) {
							implicitSegments.add(implicitSeg);
						}
					}
				}
			}
		}
		return implicitSegments;
	}

	public boolean isMinimal(Segment seg) {
		//isMinimal confirms that no points in the database lie on a given segment 
		return seg.collectOrderedPointsOnSegment(_pointDatabase.toSet()).size() == 2;
	}

	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments) {
		//check that there is nothing inside a segment
		//use hasSubSegment


		// add implicitSegment to to the allMininalSegment 
		Set<Segment> allMinimalSegments = new HashSet<Segment>();
		allMinimalSegments.addAll(implicitSegments);

		// loop through the givenSegments
		for (Segment seg : givenSegments) {
			boolean passes = true;
			for (Segment impSeg : implicitSegments) {
				if (seg.HasSubSegment(impSeg)) {
					passes = false;
				}
			}
			//	if the minimalSegment contains no implicitSegments, add it
			//		note: containing an implicit segment - for which all calculated are minimal - would imply a NON minimal segment
			if (passes) allMinimalSegments.add(seg);
		}
		return allMinimalSegments;
	}

	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minimalSegments) {

		Set<Segment> nonMinimalSegments = new HashSet<Segment>();
		Set<Segment> tempSegments = new HashSet<Segment>();
		Set<Point> minSegPoints = findAllMinimalSegmentPoints(minimalSegments);

		//create a set of all possible segments, given the full _pointDatabase (explicit and implicit points) 
		for (Point pt1 : _pointDatabase.getPoints()) {
			for (Point pt2 : _pointDatabase.getPoints()) {
				if (!pt1.equals(pt2)) {
					tempSegments.add(new Segment(pt1, pt2));
				}
			}
		}

		/** for each of those created segments, if that segment 
		 * 		contains any minimal segment as a minimal segment,
		 * 		is not a minimal segment, and 
		 * 		contains two points which are both included in some minimal segment
		 * 	add it to a set of non-minimal segments 
		 */
		for (Segment tempSeg : tempSegments) {
			for (Segment minSeg : minimalSegments) {
				if (tempSeg.HasSubSegment(minSeg) && !tempSeg.equals(minSeg) && 
					minSegPoints.contains(tempSeg.getPoint1()) && minSegPoints.contains(tempSeg.getPoint2())) {
					nonMinimalSegments.add(tempSeg);
				}
			}
		}
		return nonMinimalSegments;
	}
	
	public Set<Point> findAllMinimalSegmentPoints(Set<Segment> minimalSegments) {
		Set<Point> minSegPoints = new TreeSet<Point>();
		
		//	for each minimal segment, add each point to a set to calculate all points that are actually in the figure 
		//	(not in the file in general) 
		for (Segment minSeg : minimalSegments) {
			minSegPoints.add(minSeg.getPoint1());
			minSegPoints.add(minSeg.getPoint2());
		}
		return minSegPoints;
	}
}
