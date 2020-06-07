package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * Class that represents a model of {@link BarChart}. BarChart consist of list
 * of values, texts on x and y components, minimum y,maximum y and step.
 * 
 * @author ilovrencic
 *
 */
public class BarChart {

	/**
	 * Represents a list of x-y values
	 */
	private List<XYValue> values;

	/**
	 * Represents a text on x component
	 */
	private String xText;

	/**
	 * Represents a text on y component
	 */
	private String yText;

	/**
	 * Represents a minimum y value
	 */
	private int yMin;

	/**
	 * Represents a maximum y value
	 */
	private int yMax;

	/**
	 * Represents a step bar will have
	 */
	private int yDiff;

	/**
	 * Default constructor
	 * 
	 * @param values - list of {@link XYValue} values.
	 * @param xText  - text for x component
	 * @param yText  - text for y component
	 * @param yMin   - minimum y value
	 * @param yMax   - maximum y value
	 * @param yDiff  - step of y value
	 */
	public BarChart(List<XYValue> values, String xText, String yText, int yMin, int yMax, int yDiff) {
		Objects.requireNonNull(xText);
		Objects.requireNonNull(yText);
		Objects.requireNonNull(values);

		if (yMin < 0 || yMin > yMax || yDiff < 0) {
			throw new IllegalArgumentException("Wrong arguments for BarChar!");
		}

		checkPassedValues(values, yMin);

		this.values = values;
		this.xText = xText;
		this.yText = yText;
		this.yDiff = yDiff;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	/**
	 * Method that checks whether are all y values in list in the allowed range
	 * 
	 * @param values - list of the {@link XYValue} values.
	 * @param yMin   - minimum y value
	 */
	private void checkPassedValues(List<XYValue> values, int yMin) {
		values.forEach(v -> {
			if (v.getY() < yMin) {
				throw new IllegalArgumentException("Passed value is not in the allowed range!");
			}
		});
	}

	/* ==========GETTERS============= */
	public List<XYValue> getValues() {
		return values;
	}

	public String getxText() {
		return xText;
	}

	public int getyDiff() {
		return yDiff;
	}

	public int getyMax() {
		return yMax;
	}

	public int getyMin() {
		return yMin;
	}

	public String getyText() {
		return yText;
	}
	/* ============================== */

}
