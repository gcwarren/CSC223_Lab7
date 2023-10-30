package geometry_objects.points;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import utilities.math.MathUtilities;

/*
 * Given a pair of coordinates; generate a unique name for it;
 * return that point object.
 *
 * Names go from A..Z..AA..ZZ..AAA...ZZZ  (a name such as ABA does not occur)
 */
public class PointNamingFactory
{
	// Prefix associated with each generated name so those names are easily distinguishable
	private static final String _PREFIX = "*_";

    // Constants reflecting our naming characters for generated names.
	private static final char START_LETTER = 'A';
	private static final char END_LETTER = 'Z';

    //
    // the number of characters in the generated names:
	// "A" and 1 -> "A"
	// "B" and 3 -> "BBB"
	//
	private String _currentName = "A";
	private int _numLetters = 1;

	//
	// A hashed container for the database of points; this requires the Point
	// class implement equals based solely on the individual coordinates and
	// not a name. We need a get() method; HashSet doesn't offer one.
	// Each entry is a <Key, Value> pair where Key == Value
	//
	protected Map<Point, Point> _database;

	public PointNamingFactory() {
		_database = new LinkedHashMap<Point, Point>();
	}

	/**
	 * Initialize the database with points; must call put() to ensure all points are named
	 *
	 * @param points -- a list of points, named or not named
	 */
	public PointNamingFactory(List<Point> points) {
		PointNamingFactory pnf = new PointNamingFactory();
		for (Point p: points) {
			if (p._name != null) {
				pnf.put(p._name, p._x, p._y);
			}
			else {
				pnf.put(getCurrentName(), p._x, p._y);
				updateName();
			}
		}
	}

	/**
	 * Overloaded add / lookup mechanism for this database.
	 *
	 * @param pt -- a Point object (may or may not be named)
	 
	 * @return THE point object in the database corresponding to its coordinate pair
                    * the object in the database if it already exists or
					* a completely new point that has been added to the database
	 */
	public Point put(Point pt) {
		_database.put(pt, null);
		return pt;
	}

	/**
	 * Overloaded add / lookup mechanism for this database for an unnamed coordinate pair.
	 *
	 * @param x -- single coordinate
	 * @param y -- single coordinate

	 * @return THE point object in the database corresponding to its coordinate pair
                    * the object in the database if it already exists or
					* a completely new point that has been added to the database (with generated name)
	 */
	public Point put(double x, double y) {
		Point newPoint = new Point(getCurrentName(), x, y);
		_database.put(newPoint, null);
		return newPoint;
	}

	/**
	 * The 'main' overloaded add / lookup mechanism for this database.
	 * 
	 * @param name -- the name of the point 
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * 
	 * @return a point (if it already exists in the database) or a completely new point that
	 *         has been added to the database.
	 *         
	 *         If the point is in the database and the name differs from what
	 *         is given, nothing in the database will be changed; essentially
	 *         this means we use the first name given for a point.
	 *            e.g., a valid name cannot overwrite an existing valid name ;
	 *                  a generated name cannot be overwritten by another generated name
	 *         
	 *         The exception is that a valid name can overwrite an unnamed point.
	 */
	public Point put(String name, double x, double y) {
		Point newPoint = new Point(name, x, y);
		if (_database.containsKey(new Point(x, y)) || _database.containsValue(new Point(x, y))) {
			//return;
		}
		_database.put(newPoint, null);
		return newPoint;
	}    

	/**
	 * Strict access (read-only of the database)
	 * 
	 * @param x
	 * @param y
	 * @return stored database Object corresponding to (x, y) 
	 */
	public Point get(double x, double y) {
		for (Point p: _database.values()) {
			if (MathUtilities.doubleEquals(p._x, x) & MathUtilities.doubleEquals(p._y, y)) {
				return p;
			}
		}
		return null;
	}	
	public Point get(Point pt) {
		for (Point p: _database.values()) {
			if (p._name.equals(pt._name) & MathUtilities.doubleEquals(p._x, pt._x) & MathUtilities.doubleEquals(p._y, pt._y)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return simple containment; no updating
	 */
	public boolean contains(double x, double y) { return (_database.containsValue(new Point(x, y)) || _database.containsKey(new Point(x, y))); }
	public boolean contains(Point p) { return (_database.containsValue(p) || _database.containsKey(p)); }

	/**
	 * Constructs the next (complete with prefix) generated name.
	 * Names should be of the form PREFIX + current name
	 *
	 * This method should also invoke updating of the current name
	 * to reflect the 'next' name in the sequence.
     *	 
	 * @return the next complete name in the sequence including prefix.
	 */
	private String getCurrentName() {
        updateName();
        _currentName = _PREFIX + _currentName;
        return _currentName;
	}

	/**
	 * Advances the current generated name to the next letter in the alphabet:
	 * 'A' -> 'B' -> 'C' -> 'Z' --> 'AA' -> 'BB'
	 */
	private void updateName() {
		if (_currentName.charAt(_numLetters-1) != END_LETTER) {
			_currentName = _currentName.substring(0, _numLetters-2) + _currentName.charAt(_numLetters-1);
		}
		if (_currentName.charAt(_numLetters) == END_LETTER) {
			if (_currentName.charAt(_numLetters - 1) == END_LETTER) {
				
			}
			_currentName = _currentName.substring(0, _currentName.length() - 2) + START_LETTER;
			_numLetters += 1;
		}
        if ((_numLetters == 1) & (_currentName.equals("Z"))) {
        	_currentName = START_LETTER + START_LETTER;
        }
	}

	/**
	 * @return The entire database of points.
	 */
	public  Set<Point> getAllPoints() {
		Set<Point> allPoints = new Set<Point>();
		for (Point entryPoint: _database.keySet()) {
			allPoints.add(entryPoint);
		}
		for (Point connectingPoint: _database.values()) {
			allPoints.add(connectingPoint);
		}
		return allPoints;
	}

	public void clear() { _database.clear(); }
	public int size() { return _database.size(); }

	@Override
	public String toString() {
        // TODO
	}
}