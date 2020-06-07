package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo class that opens a {@link JFrame} that contains a {@link JLabel} that
 * holds a path and a {@link BarChartComponent}.
 * 
 * @author ilovrencic
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Generated serial version UIDs
	 */
	private static final long serialVersionUID = 5151434034409506855L;

	/**
	 * Default constructor
	 * 
	 * @param barChart - instance of the {@link BarChart}
	 * @param path     - string value of path to the file that generated
	 *                 {@link BarChart}
	 */
	public BarChartDemo(BarChart barChart, String path) {
		setSize(800, 800);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI(barChart, path);
	}

	/**
	 * Method that initializes GUI.
	 * 
	 * @param barChart - instance of the {@link BarChart}
	 * @param path     - instance of {@link String} that contains path to the file
	 *                 that generated {@link BarChart}
	 */
	private void initGUI(BarChart barChart, String path) {
		BarChartComponent comp = new BarChartComponent(barChart);
		JLabel pathLabel = new JLabel(path, SwingConstants.CENTER);

		getContentPane().add(pathLabel, BorderLayout.PAGE_START);
		getContentPane().add(comp, BorderLayout.CENTER);
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Requires only one input! Shutting off...");
			return;
		}
		try {
			BarChart barChart = getChartFromInput(args[0].trim());
			SwingUtilities.invokeLater(() -> {
				BarChartDemo demo = new BarChartDemo(barChart, Paths.get(args[0].trim()).toAbsolutePath().toString());
				demo.setVisible(true);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	/**
	 * Static method that opens a {@link BufferedReader} to read from the file and
	 * create {@link BarChart}.
	 * 
	 * @param path - path to the file that hold info about {@link BarChart}
	 * @return - instance of the {@link BarChart}
	 */
	private static BarChart getChartFromInput(String path) {
		try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
			String xText = br.readLine().trim();
			String yText = br.readLine().trim();
			List<XYValue> values = parseValues(br.readLine().trim());
			int yMin = Integer.parseInt(br.readLine().trim());
			int yMax = Integer.parseInt(br.readLine().trim());
			int yDiff = Integer.parseInt(br.readLine().trim());

			return new BarChart(values, xText, yText, yMin, yMax, yDiff);
		} catch (IOException | NumberFormatException e) {
			throw new IllegalArgumentException("Path you have entered is illegal! Error: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Static method that parses list of {@link XYValue} values from the
	 * {@link String}.
	 * 
	 * @param line - line that contains a list of {@link XYValue} values.
	 * @return - list of {@link XYValue} values
	 */
	private static List<XYValue> parseValues(String line) {
		List<XYValue> xyvalues = new ArrayList<XYValue>();
		String[] values = line.split(" ");
		for (String value : values) {
			String[] points = value.split(",");
			if (points.length != 2)
				throw new IllegalArgumentException("Wrong number of arguments!");

			try {
				int x = Integer.parseInt(points[0]);
				int y = Integer.parseInt(points[1]);

				xyvalues.add(new XYValue(x, y));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Error while formatting numbers!");
			}
		}

		return xyvalues;
	}

}
