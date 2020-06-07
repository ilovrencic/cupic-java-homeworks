package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Class that represents a Swing Component that shows data from passed
 * {@link BarChart}.
 * 
 * @author ilovrencic
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 6515695410759527416L;

	/**
	 * Represents a buffer distance at y-axis
	 */
	private static final int Y_COMPONENT_DISTANCE = 15;

	/**
	 * Represents a buffer distance at x-axis
	 */
	private static final int X_COMPONENT_DISTANCE = 10;

	/**
	 * Represents a buffer distance between elements of {@link BarChartComponent}
	 */
	private static final int AXIS_MINI_LINE = 3;

	/**
	 * Represents a default color of the text
	 */
	private static final Color TEXT_COLOR = Color.BLACK;

	/**
	 * Represents a default color of the axis
	 */
	private static final Color AXIS_COLOR = Color.DARK_GRAY;

	/**
	 * Represents a default color of the bars
	 */
	private static final Color BAR_COLOR = Color.ORANGE;

	/**
	 * Represents an instance of the {@link BarChart}
	 */
	private BarChart barChart;

	/**
	 * Default constructor
	 * 
	 * @param barChart - instance of the {@link BarChart}
	 */
	public BarChartComponent(BarChart barChart) {
		Objects.requireNonNull(barChart);
		this.barChart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Insets insets = getInsets();
		int maxWidthBetweenY = calculateMaxWidth(graphics);

		drawAxis(graphics, insets, maxWidthBetweenY);
		drawBars(graphics, insets, maxWidthBetweenY);
		drawTextOnAxis(graphics, insets, maxWidthBetweenY);
	}

	/**
	 * Method that draws the text on axis. The vertical axis requires us to draw
	 * text vertically and that's why we are doing the affine transformation.
	 * 
	 * @param graphics - instance of the {@link Graphics2D}
	 * @param insets   - instance of the {@link Insets}
	 * @param widthY   - maximum width of y axis
	 */
	private void drawTextOnAxis(Graphics2D graphics, Insets insets, int widthY) {
		FontMetrics font = graphics.getFontMetrics();
		int yAxisXCoordinate = insets.left + font.getHeight() + Y_COMPONENT_DISTANCE + X_COMPONENT_DISTANCE;
		int xAxisYCoordinate = getHeight()
				- (insets.bottom + font.getHeight() + Y_COMPONENT_DISTANCE + font.getHeight() + X_COMPONENT_DISTANCE);

		graphics.setColor(TEXT_COLOR);

		String xText = barChart.getxText();
		int y1 = getHeight() - (insets.bottom + font.getDescent() + AXIS_MINI_LINE);
		int x1 = yAxisXCoordinate + ((getWidth() - insets.right - yAxisXCoordinate) / 2 - font.stringWidth(xText) / 2);
		graphics.drawString(xText, x1, y1);

		String yText = barChart.getyText();

		AffineTransform defalutAt = graphics.getTransform();
		AffineTransform at = new AffineTransform(defalutAt);
		at.rotate(-Math.PI / 2);
		graphics.setTransform(at);

		int y2 = insets.left + font.getAscent();
		int x2 = (-insets.top - (xAxisYCoordinate - insets.top) / 2) - font.stringWidth(yText);
		graphics.drawString(yText, x2, y2);

	}

	/**
	 * Method that draws bars on the {@link BarChartComponent}. Bars have to be high
	 * as much as the {@link XYValue} commands.
	 * 
	 * @param graphics - instance of the {@link Graphics2D}
	 * @param insets   - instance of the {@link Insets}
	 * @param widthY   - maximum width of y axis
	 */
	private void drawBars(Graphics2D graphics, Insets insets, int widthY) {
		FontMetrics font = graphics.getFontMetrics();

		int x_0 = getWidth() - insets.left - insets.right - widthY - font.getHeight() - Y_COMPONENT_DISTANCE
				- X_COMPONENT_DISTANCE;
		int y_0 = getHeight() - insets.bottom - insets.top - font.getHeight() * 2 - Y_COMPONENT_DISTANCE
				- X_COMPONENT_DISTANCE;

		int xLength = x_0 - AXIS_MINI_LINE * 30;
		int yLength = y_0 - AXIS_MINI_LINE * 10;

		List<XYValue> values = barChart.getValues();
		int lengthOfBar = xLength / values.size();
		int lengthOfNumber = yLength / (barChart.getyMax() - barChart.getyMin());

		graphics.setColor(BAR_COLOR);
		for (int i = 0; i < values.size(); i++) {
			graphics.fillRect(x_0 - xLength + lengthOfBar * i, y_0 - lengthOfNumber * values.get(i).getY(),
					lengthOfBar - AXIS_MINI_LINE, lengthOfNumber * values.get(i).getY());
		}
	}

	/**
	 * Method that draws x and y axis. Apart from the axis, this method also writes
	 * numbers under the lines and adds arrows on the end of the axis.
	 * 
	 * @param graphics - instance of the {@link Graphics2D}
	 * @param insets   - instance of the {@link Insets}
	 * @param widthY   - maximum width of y axis
	 */
	private void drawAxis(Graphics2D graphics, Insets insets, int widthY) {
		FontMetrics font = graphics.getFontMetrics();

		int x_0 = getWidth() - insets.left - insets.right - widthY - font.getHeight() - Y_COMPONENT_DISTANCE
				- X_COMPONENT_DISTANCE;
		int y_0 = getHeight() - insets.bottom - insets.top - font.getHeight() * 2 - Y_COMPONENT_DISTANCE
				- X_COMPONENT_DISTANCE;

		int xLength = x_0 - AXIS_MINI_LINE * 30;
		int yLength = y_0 - AXIS_MINI_LINE * 10;

		graphics.setColor(AXIS_COLOR);
		graphics.drawLine(x_0 - xLength, y_0, x_0, y_0);
		graphics.drawLine(x_0 - xLength, y_0, x_0 - xLength, y_0 - yLength);

		Polygon yArrow = new Polygon(
				new int[] { x_0 - xLength - 2 * AXIS_MINI_LINE, x_0 - xLength, x_0 - xLength + 2 * AXIS_MINI_LINE },
				new int[] { y_0 - yLength + 2 * AXIS_MINI_LINE, y_0 - yLength - 2 * AXIS_MINI_LINE,
						y_0 - yLength + 2 * AXIS_MINI_LINE },
				3);
		graphics.fill(yArrow);

		Polygon xArrow = new Polygon(
				new int[] { x_0 - 2 * AXIS_MINI_LINE, x_0 + 2 * AXIS_MINI_LINE, x_0 - 2 * AXIS_MINI_LINE },
				new int[] { y_0 + 2 * AXIS_MINI_LINE, y_0, y_0 - 2 * AXIS_MINI_LINE }, 3);
		graphics.fill(xArrow);

		int currentNumber = barChart.getyMin();
		int lengthOfNumber = yLength / (barChart.getyMax() - barChart.getyMin());

		for (int i = currentNumber; i <= barChart.getyMax(); i += barChart.getyDiff()) {
			graphics.drawString(String.valueOf(i),
					x_0 - xLength - Y_COMPONENT_DISTANCE * 2 - font.stringWidth(String.valueOf(barChart.getyMin())),
					y_0 + font.getAscent() / 5 - lengthOfNumber * i);
			graphics.drawLine(x_0 - xLength - X_COMPONENT_DISTANCE, y_0 - lengthOfNumber * i, x_0 - xLength,
					y_0 - lengthOfNumber * i);
		}

		List<XYValue> values = barChart.getValues();
		int lengthOfBar = xLength / values.size();
		for (int i = 0; i <= values.size(); i++) {
			graphics.drawLine(x_0 - xLength + lengthOfBar * i, y_0 + X_COMPONENT_DISTANCE,
					x_0 - xLength + lengthOfBar * i, y_0);

			if (i != values.size()) {
				graphics.drawString(String.valueOf(values.get(i).getX()),
						x_0 - xLength + (lengthOfBar * i) + lengthOfBar / 2, y_0 + Y_COMPONENT_DISTANCE);
			}
		}
	}

	/**
	 * Method that will get the maximum width of the y axis
	 * 
	 * @param graphics - instance of the {@link Graphics2D}
	 * @return - maximum width of the y axis
	 */
	private int calculateMaxWidth(Graphics2D graphics) {
		FontMetrics font = graphics.getFontMetrics();

		int yMin = barChart.getyMin();
		int yMax = barChart.getyMax();
		int yDiff = barChart.getyDiff();

		int maxWidth = font.stringWidth(String.valueOf(yMin));
		for (int i = yMin; i <= yMax; i += yDiff) {
			int w = font.stringWidth(String.valueOf(i));
			if (w > maxWidth) {
				maxWidth = w;
			}
		}
		return maxWidth;
	}

}
