package geometry_objects.points;

import java.util.List;
import java.util.Set;
import utilities.math.MathUtilities;

/**
 * This class represents a bi-directional database of points.
 * 
 * We can lookup a point given:
 *   (a) coordinates --> name
 *   (b) name --> coordinates
 * 
 * This is a Decorator class with the PointNamingFactory in the background
 * 
 * @author xxx
 */
public class PointDatabase
{
	//
	// The factory is the central means of representing all
    // points in a figure; all functionality in this class
	// will defer to the factory.
    //
    protected PointNamingFactory _factory;

    public Set<Point> getPoints() { return _factory.getAllPoints(); }
    
	public PointDatabase() {
		_factory = new PointNamingFactory();
	}

	public PointDatabase(List<Point> points) {
		PointDatabase pd = new PointDatabase();
		for (Point p: points) {
			pd.put(p.getName(), p.getX(), p.getY());
		}
	}

	public int size() { return _factory.size(); }
	
	/**
	 * Add a point to the database.
	 */
	public void put(String name, double x, double y) {
        _factory.put(name, x, y);
	}

	/**
	 * Given raw coordinates of a point, determine if it is named.
	 * 
	 * @param x,y -- doubles defining a point (x,y)
	 * @return a string corresponding to that point, if it is named.
	 */
	public String getName(double x, double y) {
        for (Point p: _factory.getAllPoints()) {
        	if (MathUtilities.doubleEquals(p._x, x) & MathUtilities.doubleEquals(p._y, y)) {
        		return p.getName();
        	}
        }
        return null;
	}
	public String getName(Point pt) {
        return pt._name;
	}

	/**
	 * Given the name of a point, determine the coordinates.
	 * 
	 * @param name -- a String name
	 * @return a Point object containing (x,y) corresponding to name, if it has been defined.
	 */
	public Point getPoint(String name) {
        for (Point p: _factory.getAllPoints()) {
        	if (p._name.equals(name)) {
        		return p;
        	}
        }
        return null;
	}

	/**
	 * Given a point, acquire the stored database object; facilitates
	 * singular objects and mitigates lingering copies of points.
	 * 
	 * @param pt -- a basic point
	 * @return the database entry for the point
	 */
	public Point getPoint(Point pt) {
		for (Point p: _factory.getAllPoints()) {
        	if (p.equals(pt)) return p;
        }
        return null;
	}

	/**
	 * Given a raw point (x, y), acquire the stored database object.
	 * 
	 * @param x,y -- doubles defining a point (x,y)
	 * @return the database entry for the point
	 */
	public Point getPoint(double x, double y) {
        return getPoint(new Point(x, y));
	}
}