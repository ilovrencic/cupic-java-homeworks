package hr.fer.zemris.java.raytracer.model;

/**
 * Class that represents ray intersection with the {@link Sphere} object. In
 * this class
 * 
 * @author ilovrencic
 *
 */
public class SphereRayIntersection extends RayIntersection {

	/**
	 * Represents a {@link Sphere} instance.
	 */
	private Sphere sphere;

	/**
	 * Inherited constructor
	 * 
	 * @param point    - closest point of intersection
	 * @param distance - distance between start of ray and intersection
	 * @param outer    - boolean if the intersection is inner or outer
	 */
	protected SphereRayIntersection(Point3D point, double distance, boolean outer) {
		super(point, distance, outer);
	}

	/**
	 * Default constructor
	 * 
	 * @param point    - closest point of intersection
	 * @param distance - distance between start of ray and intersection
	 * @param outer    - boolean if the intersection is inner or outer
	 * @param sphere   - instance of {@link Sphere}
	 */
	public SphereRayIntersection(Point3D point, double distance, boolean outer, Sphere sphere) {
		this(point, distance, outer);
		this.sphere = sphere;
	}

	/*============= GETTERS =================*/
	@Override
	public Point3D getNormal() {
		Point3D normal = getPoint().sub(sphere.getCenter());
		return normal.normalize();
	}

	@Override
	public double getKdr() {
		return sphere.getKdr();
	}

	@Override
	public double getKdg() {
		return sphere.getKdg();
	}

	@Override
	public double getKdb() {
		return sphere.getKdb();
	}

	@Override
	public double getKrr() {
		return sphere.getKrr();
	}

	@Override
	public double getKrg() {
		return sphere.getKrg();
	}

	@Override
	public double getKrb() {
		return sphere.getKrb();
	}

	@Override
	public double getKrn() {
		return sphere.getKrn();
	}
	/*=======================================*/

}
