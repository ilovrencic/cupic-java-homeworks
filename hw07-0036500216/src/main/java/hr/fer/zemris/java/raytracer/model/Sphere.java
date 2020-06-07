package hr.fer.zemris.java.raytracer.model;

/**
 * Class that represents {@link GraphicalObject} Sphere. This class models the
 * Behavior of the 3D sphere.
 * 
 * @author ilovrencic
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Represents Sphere center
	 */
	private Point3D center;

	/**
	 * Represents Sphere radius
	 */
	private double radius;

	/**
	 * Represents coefficient for diffuse component for red color
	 */
	private double kdr;

	/**
	 * Represents coefficient for diffuse component for green color
	 */
	private double kdg;

	/**
	 * Represents coefficient for diffuse component for blue color
	 */
	private double kdb;

	/**
	 * Represents coefficient for reflective component for red color
	 */
	private double krr;

	/**
	 * Represents coefficient for reflective component for green color
	 */
	private double krg;

	/**
	 * Represents coefficient for reflective component for blue color
	 */
	private double krb;

	/**
	 * Represents coefficient for reflective component n
	 */
	private double krn;

	/**
	 * Default constructor
	 * 
	 * @param center - Center of the sphere
	 * @param radius - radius of the sphere
	 * @param kdr    - coefficient for diffuse component for red color
	 * @param kdg    - coefficient for diffuse component for green color
	 * @param kdb    - coefficient for diffuse component for blue color
	 * @param krr    - coefficient for reflective component for red color
	 * @param krg    - coefficient for reflective component for green color
	 * @param krb    - coefficient for reflective component for blue color
	 * @param krn    - coefficient for reflective component n
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdb = kdb;
		this.kdg = kdg;
		this.kdr = kdr;
		this.krb = krb;
		this.krg = krg;
		this.krn = krn;
		this.krr = krr;
	}

	/**
	 * Method that for a given ray returns an closest {@link RayIntersection}. It
	 * works by analytical formula where we analytical from line determine what are
	 * the intersections with the {@link Sphere}.
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D vectorFromCenter = ray.start.sub(center);

		double b = ray.direction.scalarMultiply(2).scalarProduct(vectorFromCenter);
		double c = vectorFromCenter.scalarProduct(vectorFromCenter) - radius * radius;
		double a = b * b - 4 * c;

		if (a < 0)
			return null;

		double firstIntersection = -b / 2 + Math.sqrt(a) / 2;
		double secondIntersection = -b / 2 - Math.sqrt(a) / 2;

		if (a == 0) {
			if (firstIntersection < 0)
				return null;
			Point3D intersection = getDistance(firstIntersection, secondIntersection, ray);
			return new SphereRayIntersection(intersection, firstIntersection, true, this);
		}

		if (firstIntersection < 0 && secondIntersection < 0) {
			return null;
		}

		Point3D intersection = getDistance(firstIntersection, secondIntersection, ray);
	
		if (firstIntersection > 0 && secondIntersection > 0) {
			if (firstIntersection < secondIntersection) {
				return new SphereRayIntersection(intersection, firstIntersection, true, this);
			} else {
				return new SphereRayIntersection(intersection, secondIntersection, true, this);
			}
		} else {
			if (firstIntersection > secondIntersection) {
				return new SphereRayIntersection(intersection, firstIntersection, false, this);
			} else {
				return new SphereRayIntersection(intersection, secondIntersection, false, this);
			}
		}

	}

	/**
	 * Method that returns point for specific distance on the {@link Ray} path.
	 * 
	 * @param first - first intersection
 	 * @param second - second intersection
	 * @param ray - instance of {@link Ray}
	 * @return - point of intersection 
	 */
	private Point3D getDistance(double first, double second, Ray ray) {
		if (first > 0 && second > 0) {
			first = first < second ? first : second;
			return ray.start.add(ray.direction.scalarMultiply(first));
		} else {
			first = first < second ? second : first;
			return ray.start.add(ray.direction.scalarMultiply(first));
		}
	}

	/* ============ GETTERS ================== */
	public Point3D getCenter() {
		return center;
	}

	public double getKdb() {
		return kdb;
	}

	public double getKdg() {
		return kdg;
	}

	public double getKdr() {
		return kdr;
	}

	public double getKrb() {
		return krb;
	}

	public double getKrg() {
		return krg;
	}

	public double getKrn() {
		return krn;
	}

	public double getKrr() {
		return krr;
	}

	public double getRadius() {
		return radius;
	}
	/* ===================================== */
}
