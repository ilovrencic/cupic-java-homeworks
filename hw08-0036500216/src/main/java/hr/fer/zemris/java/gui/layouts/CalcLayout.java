package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Class that represents a custom layout manager for calculator. It implements
 * {@link LayoutManager2}.
 * 
 * @author ilovrencic
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Represents a distance between elements on screen
	 */
	private int distance;

	/**
	 * Represents a list of components
	 */
	private Component[][] components = new Component[ROWS][COLUMNS];

	/**
	 * Represents a preferred dimension of the screen
	 */
	private Dimension preferredDimension;

	/**
	 * Represents a minimum dimension of the screen
	 */
	private Dimension minimumDimension;

	/**
	 * Represents a maximum dimension of the screen
	 */
	private Dimension maximumDimension;

	/**
	 * Represents a number of rows on {@link CalcLayout}
	 */
	private final static int ROWS = 5;

	/**
	 * Represents a number of columns on {@link CalcLayout}
	 */
	private final static int COLUMNS = 7;

	/**
	 * Represents a default distance for {@link CalcLayout}
	 */
	private final static int DEFAULT_DISTANCE = 0;

	/**
	 * Represents a length of first element on {@link CalcLayout}
	 */
	private final static int FIRST_COMPONENT_LENGTH = 4;

	/**
	 * Default constructor
	 */
	public CalcLayout() {
		this(DEFAULT_DISTANCE);
	}

	/**
	 * Default constructor
	 * 
	 * @param distance - wanted distance between elements
	 */
	public CalcLayout(int distance) {
		if (distance < 0) {
			throw new IllegalArgumentException("Distance shouldn't be lower than zero!");
		}
		this.distance = distance;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (comp == null) {
			throw new NullPointerException("Passed value for component can't be null!");
		}

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if(components[i][j] != null) {
					if (components[i][j].equals(comp)) {
						components[i][j] = null;
						break;
					}
				}
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return adjustDimension(preferredDimension);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return adjustDimension(minimumDimension);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxHeight = parent.getHeight() - (insets.bottom - insets.top);
		int maxWidth = parent.getWidth() - (insets.left - insets.right);

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Component component = components[i][j];
				if (component == null)
					continue;
				setBounds(component, insets, maxHeight, maxWidth, i, j);
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) {
			throw new NullPointerException("Passed value to CalcLayout was null!");
		}

		if (!(constraints instanceof String || constraints instanceof RCPosition)) {
			throw new IllegalArgumentException("Passed value for constraints is not fitting type!");
		}

		RCPosition position;
		if (constraints instanceof String) {
			position = RCPosition.parse((String) constraints);
		} else {
			position = (RCPosition) constraints;
		}

		if (!isPositionValid(position)) {
			throw new CalcLayoutException("Uallowed row and column position!");
		}

		components[position.getRow() - 1][position.getColumn() - 1] = comp;
		boolean isFirstComponent = constraints.equals(new RCPosition(1, 1));
		updateLayout(comp, isFirstComponent);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return adjustDimension(maximumDimension);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// empty for now
	}

	/**
	 * Method that sets the bounds of the component. For that to make we have to
	 * find dimension of the screen and set component based on that.
	 * 
	 * @param comp   - instance of the {@link Component}.
	 * @param insets - instance of {@link Insets} we got from the {@link Container}
	 * @param height - max height of the container
	 * @param width  - max width of the container
	 * @param i      - current row
	 * @param j      - current column
	 */
	private void setBounds(Component comp, Insets insets, int height, int width, int i, int j) {
		int componentHeigth = (int) ((height - (ROWS - 1) * distance) / (double) (ROWS));
		int componentWidth = (int) ((width - (COLUMNS - 1) * distance) / (double) (COLUMNS));

		if (i == 0 && j == 0) {
			componentWidth = componentWidth * (FIRST_COMPONENT_LENGTH + 1) + FIRST_COMPONENT_LENGTH * distance;
		}

		int x = j * (componentWidth + distance) + insets.left;
		int y = i * (componentHeigth + distance) + insets.top;

		comp.setBounds(x, y, componentWidth, componentHeigth);
		updateLayout(comp, i == 0 && j == 0);
	}

	/**
	 * Method that updates the dimensions based on some changes on component we have
	 * added or modifed.
	 * 
	 * @param component        - instance of the {@link Component}
	 * @param isFirstComponent - check whether the component we are updating is the
	 *                         first component
	 */
	private void updateLayout(Component component, boolean isFirstComponent) {
		Dimension minimum = component.getMinimumSize();
		Dimension maximum = component.getMaximumSize();
		Dimension preferred = component.getPreferredSize();

		updateDimension(minimum, isFirstComponent, DimensionType.MINIMUM);
		updateDimension(maximum, isFirstComponent, DimensionType.MAXIMUM);
		updateDimension(preferred, isFirstComponent, DimensionType.PREFERRED);
	}

	/**
	 * Method that will update dimension based on the biggest element on the screen
	 * 
	 * @param dimension        - instance of the {@link Dimension}
	 * @param isFirstComponent - check whether the component first
	 * @param type             - instance of the {@link DimensionType} to check on
	 *                         which dimension are we doing the update
	 */
	private void updateDimension(Dimension dimension, boolean isFirstComponent, DimensionType type) {
		if (dimension == null)
			return;

		int width;
		int height = dimension.height;

		if (isFirstComponent) {
			width = (dimension.width - FIRST_COMPONENT_LENGTH * distance) / (FIRST_COMPONENT_LENGTH + 1);
		} else {
			width = dimension.width;
		}

		switch (type) {
		case MAXIMUM:
			updateMaximum(width, height);
		case MINIMUM:
			updateMinimum(width, height);
		case PREFERRED:
			updatePreferred(width, height);
		}
	}

	/**
	 * Method that updates the minimum dimension.
	 * 
	 * @param width  - new width value
	 * @param height - new height value
	 */
	private void updateMinimum(int width, int height) {
		if (minimumDimension == null) {
			minimumDimension = new Dimension(width, height);
		} else {
			if (width > minimumDimension.width) {
				minimumDimension.width = width;
			}

			if (height > minimumDimension.height) {
				minimumDimension.height = height;
			}
		}
	}

	/**
	 * Method that updates the maximum dimension.
	 * 
	 * @param width  - new width value
	 * @param height - new height value
	 */
	private void updateMaximum(int width, int height) {
		if (maximumDimension == null) {
			maximumDimension = new Dimension(width, height);
		} else {
			if (width > maximumDimension.width) {
				maximumDimension.width = width;
			}

			if (height > maximumDimension.height) {
				maximumDimension.height = height;
			}
		}
	}

	/**
	 * Method that updates the preferred dimension
	 * 
	 * @param width  - new width value
	 * @param height - new height value
	 */
	private void updatePreferred(int width, int height) {
		if (preferredDimension == null) {
			preferredDimension = new Dimension(width, height);
		} else {
			if (width > preferredDimension.width) {
				preferredDimension.width = width;
			}

			if (height > preferredDimension.height) {
				preferredDimension.height = height;
			}
		}
	}

	/**
	 * Method that checks whether the entered {@link RCPosition} is valid.
	 * 
	 * @param position - instance of the {@link RCPosition}
	 * @return - true if it is valid, othervise false
	 */
	private boolean isPositionValid(RCPosition position) {
		int row = position.getRow();
		int column = position.getColumn();

		if (row < 1 || row > 5 || column < 1 || column > 7) {
			return false;
		}

		if (row == 1 && (column > 1 && column < 6)) {
			return false;
		}

		return true;
	}

	/**
	 * Method that adjusts dimension based on the distance and the number of columns
	 * and rows.
	 * 
	 * @param dimension - instance of the {@link Dimension}
	 * @return - dimension with adjusted height and width
	 */
	private Dimension adjustDimension(Dimension dimension) {
		int width = dimension.width * COLUMNS + distance * (COLUMNS - 1);
		int heigth = dimension.height * ROWS + distance * (ROWS - 1);
		return new Dimension(width, heigth);
	}
}

/**
 * Enum that represent the type of dimension we have.
 * 
 * @author ilovrencic
 *
 */
enum DimensionType {
	MINIMUM, MAXIMUM, PREFERRED
}
