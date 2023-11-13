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
	//change for pull
	public Set<Segment> computeImplicitBaseSegments(Set<Point> implicitPoints) {
		
		Set<Segment> implicitSegments = new HashSet<Segment>();
		// loop through the implicit point 
		for (Point implicit : implicitPoints) {
			//loop through the segmentDatabase (Map)
			for (Segment seg : _givenSegments) {
				// if the segment we are looping through have point lie between them 
				// then add _implicitSegment by the new point 
				if (seg.pointLiesBetweenEndpoints(implicit)) {
					if (!implicitSegments.contains(new Segment(implicit, seg.getPoint1())) && !_givenSegments.contains(new Segment(implicit, seg.getPoint1()))) {
						implicitSegments.add(new Segment(implicit, seg.getPoint1()));
					}
					if (!implicitSegments.contains(new Segment(implicit, seg.getPoint2()))  && !_givenSegments.contains(new Segment(implicit, seg.getPoint2()))) {
						implicitSegments.add(new Segment(implicit, seg.getPoint2()));						
					}
				}
			}
		}
		// then return the implicitSemgnet 
		return implicitSegments;
	}
	
	// then we have the class contain all implicit segment 
	
	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments) {
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
	
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minimalSegments) {
		
		Set<Segment> nonMinimalSegments = new HashSet<Segment>();
		
		for (Segment seg : _givenSegments) {
			if (!minimalSegments.contains(seg)) {
				nonMinimalSegments.add(seg);
			}
		}
		return nonMinimalSegments;
	}
}
