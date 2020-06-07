package hr.fer.zemris.java.raytracer.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class that's generating image by casting {@link Ray} on a
 * {@link GraphicalObject}s. In our case we have two {@link Sphere} object.
 * 
 * @author ilovrencic
 *
 */
public class RayCaster {

	private final static int AMBIENT_COMPONENT_RED = 15;
	private final static int AMBIENT_COMPONENT_GREEN = 15;
	private final static int AMBIENT_COMPONENT_BLUE = 15;
	private final static double EPSILON = 1E-04;

	/**
	 * Main function of the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Method that produces {@link IRayTracerProducer} for certain {@link Point3D}s.
	 * 
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).normalize();
				Point3D VUV = viewUp.normalize();

				Point3D j = VUV.sub(OG.scalarMultiply(OG.scalarProduct(VUV))).normalize();
				Point3D i = OG.vectorProduct(j).normalize();

				Point3D screenCorner = view.sub(i.scalarMultiply(horizontal / 2)).add(j.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {

						Point3D screenPoint = screenCorner.add(i.scalarMultiply((double) x / (width - 1) * horizontal))
								.sub(j.scalarMultiply((double) y / (height - 1) * vertical));

						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Tracer function that goes finds closest intersection with the given
	 * {@link Ray}. Function returnes determined colors for that particular
	 * {@link Ray}. Depending if the {@link Ray} hits the object, pixel could be
	 * colored or black.
	 * 
	 * @param scene - instance of {@link Scene}
	 * @param ray   - instance of {@link Ray}
	 * @param rgb   - colors of the pixel in the {@link Scene}.
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);

		if (closest == null) {
			return;
		}

		short[] determinedColors = determineColors(closest, scene, ray);

		rgb[0] = determinedColors[0];
		rgb[1] = determinedColors[1];
		rgb[2] = determinedColors[2];
	}

	/**
	 * Method that finds the closest intersection of object with {@link Ray}. It
	 * goes through all the intersection there are, and returnes the one with the
	 * smallest distance.
	 * 
	 * @param scene - instance of the {@link Scene}
	 * @param ray   - instance of the {@link Ray}.
	 * @return - closest intersection for specific {@link Ray}.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		RayIntersection closest = objects.get(0).findClosestRayIntersection(ray);

		for (int i = 1; i < objects.size(); i++) {
			RayIntersection intersection = objects.get(i).findClosestRayIntersection(ray);
			if (closest == null) {
				closest = intersection;

			} else if (intersection != null && (intersection.getDistance() < closest.getDistance())) {
				closest = intersection;
			}

		}
		return closest;
	}

	/**
	 * Method that will determine colors of the pixel. Based on the
	 * {@link RayIntersection} and the available light sources that are in
	 * {@link Scene} we determine which object should be colored and which
	 * shouldn't.
	 * 
	 * @param closest - closest ray intersection with eye ray
	 * @param scene   - instance of the {@link Scene}
	 * @param viewRay - ray that intersected with the {@link GraphicalObject}.
	 * @return - colors of the pixel we wanted to determine
	 */
	private static short[] determineColors(RayIntersection closest, Scene scene, Ray viewRay) {
		short[] colors = new short[] { AMBIENT_COMPONENT_RED, AMBIENT_COMPONENT_GREEN, AMBIENT_COMPONENT_BLUE };
		List<LightSource> lights = scene.getLights();

		for (LightSource light : lights) {
			Ray lightRay = new Ray(light.getPoint(), closest.getPoint().sub(light.getPoint()).modifyNormalize());

			RayIntersection lightIntersection = findClosestIntersection(scene, lightRay);
			if (lightIntersection != null && closest.getPoint().sub(lightIntersection.getPoint()).norm() > EPSILON)
				continue;

			Point3D normal = closest.getNormal();

			Point3D lightVector = normal.difference(light.getPoint(), closest.getPoint()).normalize();
			Point3D reflectionVector = normal.scalarMultiply(2)
					.scalarMultiply(lightRay.direction.negate().scalarProduct(normal)).sub(lightRay.direction.negate())
					.modifyNormalize();
			Point3D view = viewRay.direction.negate();

			colors[0] += (short) (light.getR() * (closest.getKdr() * normal.scalarProduct(lightVector)
					+ closest.getKrr() * Math.pow(reflectionVector.scalarProduct(view), closest.getKrn())));
			colors[1] += (short) (light.getG() * (closest.getKdg() * normal.scalarProduct(lightVector)
					+ closest.getKrg() * Math.pow(reflectionVector.scalarProduct(view), closest.getKrn())));
			colors[2] += (short) (light.getB() * (closest.getKdb() * normal.scalarProduct(lightVector)
					+ closest.getKrb() * Math.pow(reflectionVector.scalarProduct(view), closest.getKrn())));
		}

		return colors;
	}

}
