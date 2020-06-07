package hr.fer.zemris.java.gui.charts;

/**
 * Class that represents a data point on {@link BarChart}.
 * 
 * @author ilovrencic
 *
 */
public class XYValue {

	/**
	 * Represents x value
	 */
	private int x;

	/**
	 * Represents y value
	 */
	private int y;

	/**
	 * Default constructor
	 * 
	 * @param x - x value
	 * @param y - y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "x:" + x + " " + "y:" + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XYValue) {
			XYValue value = (XYValue) obj;
			return x == value.x && y == value.y;
		}

		return false;
	}

}
