package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that will produce the data for fractal. In other words, this class will
 * do parallel work in calculating the necessary data for the fractal drawing.
 * This producer will create {@link ExecutorService} and assign jobs to
 * different threads to produce this fractal as efficiently it's possible.
 * 
 * @author ilovrencic
 *
 */
public class ProducerImpl implements IFractalProducer {

	/**
	 * Represents a polynomial
	 */
	private ComplexRootedPolynomial poly;

	/**
	 * Represents a pool of threads
	 */
	private ExecutorService pool;

	/**
	 * Default constructor that initializes {@link ExecutorService}
	 * 
	 * @param p - Instance of {@link ComplexRootedPolynomial}
	 */
	public ProducerImpl(ComplexRootedPolynomial p) {
		this.poly = p;
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DaemonThreadFactory());
	}

	/**
	 * Method that will produce fractal data by doing parallel work. It will
	 * distribute calculation work among {@link Thread}s.
	 */
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int heigth, long requestNo,
			IFractalResultObserver observer) {

		System.out.println("Starting with parallel computation of the fractal...");
		short[] data = new short[width * heigth];

		int splits = 8 * Runtime.getRuntime().availableProcessors();
		int sizeOfSplit = heigth / splits;

		List<Future<Void>> results = new ArrayList<Future<Void>>();

		for (int i = 0; i < splits; i++) {
			int yMin = i * sizeOfSplit;
			int yMax = i == splits - 1 ? heigth - 1 : (i + 1) * sizeOfSplit - 1;

			CalculateFractalJob job = new CalculateFractalJob(reMin, reMax, imMin, imMax, yMin, yMax, heigth, width,
					data, poly);

			results.add(pool.submit(job));
		}

		for (Future<Void> result : results) {
			try {
				result.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Finished producing fractal. Showing results...");
		observer.acceptResult(data, (short) (poly.toComplexPolynomial().order() + 1), requestNo);
	}

	/**
	 * Private thread factory class that creates all {@link Thread} to be Daemon, so
	 * that they don't interrupt GUI.
	 * 
	 * @author ilovrencic
	 *
	 */
	private static class DaemonThreadFactory implements ThreadFactory {

		/**
		 * Method that creates new thread and makes it daemon.
		 */
		public Thread newThread(Runnable r) {
			Thread worker = new Thread(r);
			worker.setDaemon(true);
			return worker;
		}

	}

}
