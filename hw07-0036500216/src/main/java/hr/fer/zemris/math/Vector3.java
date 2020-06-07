package hr.fer.zemris.math;

/**
 * Class that represents unmodifiable Vector in three dimensions. Class supports
 * all vector operations.
 * 
 * @author ilovrencic
 *
 */
public class Vector3 {
	
	protected static final double EPSILON_VALUE = 1e-06;

	/* ==== Coordinates ===== */
	private double x;
	private double y;
	private double z;
	/* ====================== */

	/**
	 * Default constructor
	 * 
	 * @param x - first dimension
	 * @param y - second dimension
	 * @param z - third dimension
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Method that returns the norm, or length of the vector
	 * 
	 * @return - length of the vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Method that returns normalized vector
	 * 
	 * @return - vector that was divided by its length
	 */
	public Vector3 normalized() {
		double norm = norm();
		if (norm != 0) {
			return new Vector3(x / norm, y / norm, z / norm);
		} else {
			throw new IllegalArgumentException("Norm is zero!");
		}
	}

	/**
	 * Method that adds two vectors together
	 * 
	 * @param other - Instance of {@link Vector3}
	 * @return - this and passed vector added
	 */
	public Vector3 add(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Other vector can't be null when you add!");
		}
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Method that subs one vector for another
	 * 
	 * @param other - Instance of {@link Vector3}
	 * @return - difference between two vectors
	 */
	public Vector3 sub(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Other vector cant be null when you subtract!");
		}
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Method that does a dot product between two vectors
	 * 
	 * @param other - Instance of {@link Vector3}
	 * @return - product between two vectors
	 */
	public double dot(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Other vector can't be null when you do dot product!");
		}
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Method that does a cross product between two vectors
	 * 
	 * @param other - Instance of {@link Vector3}
	 * @return - cross product between two vectors
	 */
	public Vector3 cross(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Other vector can't be null when you do cross product!");
		}
		double newX = y * other.z - z * other.y;
		double newY = x * other.z - z * other.x;
		double newZ = x * other.y - y * other.x;
		return new Vector3(newX, -newY, newZ);
	}

	/**
	 * Method that scales vector by factor scale
	 * 
	 * @param scale - factor of scaling
	 * @return - scaled vector
	 */
	public Vector3 scale(double scale) {
		return new Vector3(x * scale, y * scale, z * scale);
	}

	/**
	 * Method that calculates cosinus angle between two vectors
	 * 
	 * @param other - Instance of another vector
	 * @return - angle between vectors
	 */
	public double cosAngle(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Other vector can't be null when you search for angle!");
		}
		
		if(this.norm() == 0 || other.norm() == 0) {
			return 0;
		}
		return this.dot(other) / (this.norm() * other.norm());
	}

	/* ======== GETTERS ============ */

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	/* ============================= */

	/**
	 * Method that transforms vector into array
	 * 
	 * @return - array of vector values
	 */
	public double[] toArray() {
		double[] array = new double[] { x, y, z };
		return array;
	}

	/**
	 * Method that returns {@link String} of {@link Vector3}
	 */
	public String toString() {
		String output = "";
		output += "x:" + x + " y:" + y + " z:" + z;
		return output;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vector3) {
			Vector3 vector = (Vector3) obj;
			
			 if(Math.abs(vector.x - x) < EPSILON_VALUE && Math.abs(vector.y - y) < EPSILON_VALUE && Math.abs(vector.z - z) < EPSILON_VALUE){
				 return true;
			 }
		}
		
		return false;
	}

}
