package hr.fer.zemris.math;

/**
 * Class that represents Vector in 2-dimensional space. It's paired with all
 * possible operations you can do on 2D vector.
 * 
 * @author ilovrencic
 *
 */
public class Vector2D {

	/**
	 * Represents x coordinate in 2D space
	 */
	private double x;

	/**
	 * Represents y coordinate in 2D space
	 */
	private double y;

	/**
	 * Default constructor
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x coordinate
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y coordinate
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Method that offsets a vector for a passed {@link Vector2D} value.
	 * 
	 * @param offset - {@link Vector2D} value that will translate current vector
	 */
	public void translate(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException("Offset can't be null!");
		}

		this.x += offset.getX();
		this.y += offset.getY();
	}

	/**
	 * Method that offsets a vector for a passed {@link Vector2D} value.
	 * 
	 * @param offset - {@link Vector2D} value that will translate current vector
	 * @return - new {@link Vector2D} instance with translated values.
	 */
	public Vector2D translated(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException("Offset can't be null!");
		}

		return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
	}

	/**
	 * Method that rotates a {@link Vector2D} for a passed @param angle.
	 * 
	 * @param angle - angle expressed in radians
	 */
	public void rotate(double angle) {
		double new_x = x * Math.cos(angle) - y * Math.sin(angle);
		double new_y = x * Math.cos(angle) + y * Math.sin(angle);
		this.x = new_x;
		this.y = new_y;
	}

	/**
	 * Method that returns rotated {@link Vector2D} for a passed @param angle.
	 * 
	 * @param angle
	 * @return - {@link Vector2D} instance that's rotated.
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle), x * Math.cos(angle) + y * Math.sin(angle));
	}

	/**
	 * Method that scales a {@link Vector2D} by passed @param scaler.
	 * 
	 * @param scaler - value by which we want to scaler a {@link Vector2D}
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}

	/**
	 * Method that returns scaled {@link Vector2D} for a passed @param scaler.
	 * 
	 * @param scaler - value by which we want to scale our {@link Vector2D}
	 * @return scaled instance of {@link Vector2D}
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}

	/**
	 * Method that returns a copy of {@link Vector2D}.
	 * 
	 * @return - instance of {@link Vector2D} with the same values
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

}
