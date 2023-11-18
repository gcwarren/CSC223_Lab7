package preprocessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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
	 *
	 * @param impPoints -- implicit points computed from segment intersections
	 * @return a set of implicitly defined segments
	 */
	public Set<Segment> computeImplicitBaseSegments(Set<Point> implicitPoints) {

		Set<Segment> implicitSegments = new HashSet<Segment>();

		for (Segment seg : _givenSegments) {
			SortedSet<Point> ptsOnSeg = seg.collectOrderedPointsOnSegment(implicitPoints);
			
			if (ptsOnSeg.size() > 2) { //makes sure there are implicit points on the segment 
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
		return seg.collectOrderedPointsOnSegment(_pointDatabase.toSet()).size() == 2;
	}

	// then we have the class contain all implicit segment 

	/**
	 * From the 'given' segments we remove any non-minimal segment.
	 * 
	 * @param impPoints -- the implicit points for the figure
	 * @param givenSegments -- segments provided by the user
	 * @param minimalImpSegments -- minimal implicit segments computed from the implicit points
	 * @return -- a 
	 */
	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments) {
		//check that there is nothing inside a segment
		//use hasSubSegment


		// add implicitSegment to to the allMininalSegment 
		Set<Segment> allMinimalSegments = new HashSet<Segment>();
		allMinimalSegments.addAll(implicitSegments);

		// loop through the givenSegment 
		for (Segment seg : givenSegments) {
			//create a boolean passesAllSegs 

			boolean passesAllSegs = true;
			for (Point pt : _pointDatabase.getPoints()) {
				if (seg.pointLiesBetweenEndpoints(pt)) {
					passesAllSegs = false;
				}

			}
			if (passesAllSegs) {
				allMinimalSegments.add(seg);
			}
		}
		return allMinimalSegments;
	}

	/**
	 * Given a set of minimal segments, build non-minimal segments by appending
	 * minimal segments (one at a time).
	 * 
	 * (Recursive construction of segments.)
	 */
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minimalSegments) {

		//build every possible nonminimal from calculated minimal 
		//this is combinations 

		//recursive or nested loops
		//go through all minimal segments with all minimal segments to create the set of all segments that have 2 minimal segments
		//then go through all 2 segs with minimal segs... 
		//your stopping condition is when an iteration occurs where you created nothing new (i.e. no 4 segs from your 3 segs) 

		Set<Segment> nonMinimalSegments = new HashSet<Segment>();

		for (Segment seg : _givenSegments) {
			if (!minimalSegments.contains(seg)) {
				nonMinimalSegments.add(seg);
			}
		}
		return nonMinimalSegments;
	}
}
