package preprocessor;

import java.util.HashMap;
import java.util.HashSet;
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
	
	public Set<Segment> computeImplicitBaseSegments(Set<Point> implicitPoints) {
		
		for (Point implicit : implicitPoints) {
			for (Segment seg : _givenSegments) {
				if (seg.pointLiesBetweenEndpoints(implicit)) {
					_implicitSegments.add(new Segment(implicit, seg.getPoint1()));
					_implicitSegments.add(new Segment(implicit, seg.getPoint2()));
				}
			}
		}
		return _implicitSegments;
	}
	
	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments) {
		
		_allMinimalSegments.addAll(implicitSegments);
		
		for (Segment seg : givenSegments) {
			boolean passesAllSegs = true;
			for (Point pt : _pointDatabase.getPoints()) {
				if (seg.pointLiesBetweenEndpoints(pt)) {
					passesAllSegs = false;
				}
			}
			if (passesAllSegs) {
				_allMinimalSegments.add(seg);
			}
		}
		return _allMinimalSegments;
	}
	
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minimalSegments) {
		
		for (Segment seg : _givenSegments) {
			if (!minimalSegments.contains(seg)) {
				_nonMinimalSegments.add(seg);
			}
		}
		return _nonMinimalSegments;
	}
}