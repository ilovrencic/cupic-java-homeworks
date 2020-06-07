package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents an implementation of the {@link LSystemBuilder}
 * interface. Class offers a two ways of building a Lindermayer system. You can
 * build it through this builder or with through file/text that will be parsed.
 * 
 * @author ilovrencic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/* ------------CONSTANTS------------ */
	private static final String ORIGIN = "origin";
	private static final String ANGLE = "angle";
	private static final String UNIT_LENGTH = "unitLength";
	private static final String UNIT_LENGTH_DEGREE_SCALER = "unitLengthDegreeScaler";
	private static final String COMMAND = "command";
	private static final String AXIOM = "axiom";
	private static final String PRODUCTION = "production";

	private static final String DRAW = "draw";
	private static final String SKIP = "skip";
	private static final String SCALE = "scale";
	private static final String ROTATE = "rotate";
	private static final String PUSH = "push";
	private static final String POP = "pop";
	private static final String COLOR = "color";
	/* ---------------------------------- */

	/**
	 * Collection that holds pairs of <character,command> elements. E.g. "F" ->
	 * {@link DrawCommand}
	 */
	private Dictionary<Character, Command> commands;

	/**
	 * Collection that holds paris of <character,string> elements. E.g. "F" ->
	 * "F+F-F+F"
	 */
	private Dictionary<Character, String> productions;

	/**
	 * Length of initial unit that will turtle make.
	 */
	private double unitLength = 0.1;

	/**
	 * Number by which the unit length will be scaled each level.
	 */
	private double unitLengthDegreeScaler = 1;

	/**
	 * Inital angle of the {@link TurtleState} direction.
	 */
	private double angle = 0;

	/**
	 * Starting point for the {@link TurtleState}
	 */
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 * Initial axiom that represents a Lindermayer system
	 */
	private String axiom = "";

	/**
	 * Default constructor
	 */
	public LSystemBuilderImpl() {
		this.commands = new Dictionary<Character, Command>();
		this.productions = new Dictionary<Character, String>();
	}

	/**
	 * Method that returns an instance of {@link LSystemImpl}.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Method that registers a command for certain key. Command is parsed from the
	 * passed {@link String} value.
	 */
	@Override
	public LSystemBuilder registerCommand(char key, String value) {
		if (commands.get(key) != null) {
			throw new IllegalStateException("This command already exists!");
		}

		Command command = getCommandFromString(value);
		commands.put(key, command);
		return this;
	}

	/**
	 * Method that registers a production rule for a certain key.
	 */
	@Override
	public LSystemBuilder registerProduction(char key, String value) {
		if (productions.get(key) != null) {
			throw new IllegalStateException("This production already exists!");
		}

		productions.put(key, value.trim());
		return this;
	}

	/**
	 * Setter method for angle. Angle has to be transformed from degrees to radians.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle * (Math.PI / 180);
		return this;
	}

	/**
	 * Setter method for Axiom.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		if (axiom == null) {
			throw new NullPointerException("Axiom shouldn't be null!");
		}

		this.axiom = axiom;
		return this;
	}

	/**
	 * Setter method for origin.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Setter method for unit length.
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length shouldn't be negative!");
		}

		this.unitLength = length;
		return this;
	}

	/**
	 * Setter method for unit length degree scaler.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scale) {
		if (scale <= 0) {
			throw new IllegalArgumentException("Scaler shouldn't be negative!");
		}

		this.unitLengthDegreeScaler = scale;
		return this;
	}

	/**
	 * Method that configures a {@link LSystem} from {@link String} array. For each
	 * {@link String} this method parses it and the calls the appropriate builder
	 * function.
	 * 
	 * @param corpus - is a text from which we want to configure our {@link LSystem}
	 */
	@Override
	public LSystemBuilder configureFromText(String[] corpus) {
		for (String text : corpus) {
			if (text == null) {
				throw new NullPointerException("There shouldn't be nulls in data configuration!");
			}

			if (text.isEmpty()) {
				continue;
			}

			String[] parts = text.trim().split("\\s+|\\t+|\\n+|\\r+");
			analyizeText(parts);
		}
		return this;
	}

	/**
	 * Parser method that tries from {@link String} to actual builder method with
	 * right arguments.
	 * 
	 * @param parts - parts of texts we want to parse into a builder method.
	 */
	private void analyizeText(String[] parts) {
		String directiveName = parts[0].trim();
		if (directiveName.toLowerCase().equals(ORIGIN)) {
			if (parts.length != 3) {
				throw new IllegalArgumentException("Origin should only have 2 arguments!");
			}

			try {
				Double x = Double.parseDouble(parts[1]);
				Double y = Double.parseDouble(parts[2]);
				setOrigin(x, y);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Unfit argument for origin!");
			}
		} else if (directiveName.toLowerCase().equals(ANGLE)) {
			if (parts.length != 2) {
				throw new IllegalArgumentException("Angel should only have 1 argument!");
			}

			try {
				Double angle = Double.parseDouble(parts[1]);
				setAngle(angle);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Unfit argument for angle!");
			}
		} else if (directiveName.toLowerCase().equals(UNIT_LENGTH.toLowerCase())) {
			if (parts.length != 2) {
				throw new IllegalArgumentException("Unit length should only have 1 argument!");
			}

			try {
				Double length = Double.parseDouble(parts[1]);
				setUnitLength(length);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Unfit argument for unit length!");
			}
		} else if (directiveName.toLowerCase().equals(UNIT_LENGTH_DEGREE_SCALER.toLowerCase())) {
			analyizeUnitLength(parts);
		} else if (directiveName.toLowerCase().equals(AXIOM)) {
			if (parts.length != 2) {
				throw new IllegalArgumentException("Unfit number of arguments for axiom!");
			}

			setAxiom(parts[1]);
		} else if (directiveName.toLowerCase().equals(COMMAND)) {
			if (parts.length < 2) {
				throw new IllegalArgumentException("Unfit number of arguments for command!");
			}

			String key = parts[1];

			if (key.length() != 1) {
				throw new IllegalArgumentException("Key for command is not right length!");
			}

			if (commands.get(key.charAt(0)) != null) {
				throw new IllegalArgumentException("Command with that key is already defined!");
			}

			String command = "";
			for (int i = 2; i < parts.length; i++) {
				command += parts[i] + " ";
			}

			registerCommand(key.charAt(0), command);
		} else if (directiveName.toLowerCase().equals(PRODUCTION)) {
			if (parts.length != 3) {
				throw new IllegalArgumentException("Unfit number of arguments for production!");
			}

			String key = parts[1];

			if (key.length() != 1) {
				throw new IllegalArgumentException("Key for production is not right length!");
			}

			if (productions.get(key.charAt(0)) != null) {
				throw new IllegalArgumentException("Production with that key is already defined!");
			}

			String production = "";
			for (int i = 2; i < parts.length; i++) {
				production += parts[i] + " ";
			}

			registerProduction(key.charAt(0), production);
		} else {
			throw new IllegalArgumentException("Wrong arguments! Argument: " + directiveName);
		}
	}

	/**
	 * Method that parses unit length scaler. E.g. unitLengthDegreeScaler 1.0/3.0
	 * must be accepted, also unitLengthDegreeScaler 1.0, unitLengthDegreeScaler 1.0
	 * / 3.0 and unitLengthDegreeScaler 1.0/ 3.0
	 * 
	 * @param parts - unitLengthDegreeScaler string configuration
	 */
	private void analyizeUnitLength(String[] parts) {

		if (parts.length == 4) {
			try {
				Double value1 = Double.parseDouble(parts[1]);
				Double value2 = Double.parseDouble(parts[3]);
				setUnitLengthDegreeScaler(value1 / value2);
				return;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
			}
		}

		if (parts.length == 2) {
			if (parts[1].contains("/")) {
				String[] numbers = parts[1].split("/");

				try {
					Double value1 = Double.parseDouble(numbers[0]);
					Double value2 = Double.parseDouble(numbers[1]);
					setUnitLengthDegreeScaler(value1 / value2);
					return;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
				}
			} else {
				try {
					Double value = Double.parseDouble(parts[1]);
					setUnitLengthDegreeScaler(value);
					return;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
				}
			}
		}

		if (parts.length == 3) {
			if (parts[1].contains("/")) {
				String[] numbers = parts[1].split("/");

				try {
					Double value1 = Double.parseDouble(numbers[0]);
					Double value2 = Double.parseDouble(parts[2]);
					setUnitLengthDegreeScaler(value1 / value2);
					return;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
				}
			} else if (parts[2].contains("/")) {
				String[] numbers = parts[2].split("/");

				try {
					Double value1 = Double.parseDouble(parts[1]);
					Double value2 = Double.parseDouble(numbers[1]);
					setUnitLengthDegreeScaler(value1 / value2);
					return;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
				}
			} else {
				throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
			}
		}

		throw new IllegalArgumentException("Unfit arguments for Unit length scaler!");
	}

	/**
	 * Method that parses {@link String} into a {@link Command}. It does by parsing
	 * the {@link String} and finding to which type of the {@link Command} belongs
	 * to.
	 * 
	 * @param s - string we want to parse
	 * @return the right type of the {@link Command} implementation
	 */
	private Command getCommandFromString(String s) {
		if (s == null) {
			throw new NullPointerException("Command value shouldn't be null!");
		}

		String[] parts = s.trim().split("\\s+|\\t+");

		if (parts[0].toLowerCase().equals(DRAW)) {
			try {
				Double value = Double.parseDouble(parts[1]);
				return new DrawCommand(value);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Illegal argument for draw!");
			}
		} else if (parts[0].toLowerCase().equals(SKIP)) {
			try {
				Double value = Double.parseDouble(parts[1]);
				return new SkipCommand(value);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Illegal argument for skip!");
			}
		} else if (parts[0].toLowerCase().equals(SCALE)) {
			try {
				Double value = Double.parseDouble(parts[1]);
				return new ScaleCommand(value);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Illegal argument for scale!");
			}
		} else if (parts[0].toLowerCase().equals(ROTATE)) {
			try {
				Double value = Double.parseDouble(parts[1]);
				return new RotateCommand(value * (Math.PI / 180)); // angle has to be converted to radians
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Illegal argument for rotate!");
			}
		} else if (parts[0].toLowerCase().equals(PUSH)) {
			if (parts.length > 1) {
				throw new IllegalArgumentException("Too much of arguments for push!");
			}

			return new PushCommand();
		} else if (parts[0].toLowerCase().equals(POP)) {
			if (parts.length > 1) {
				throw new IllegalArgumentException("Too much of arguments for push!");
			}

			return new PopCommand();
		} else if (parts[0].toLowerCase().equals(COLOR)) {

			String color = parts[1].trim();

			if (color.length() != 6) {
				throw new IllegalArgumentException("Color has to be 6 characters long!");
			}

			String red = color.substring(0, 2);
			String green = color.substring(2, 4);
			String blue = color.substring(4, 6);

			try {
				return new ColorCommand(
						new Color(Integer.parseInt(red, 16), Integer.parseInt(green, 16), Integer.parseInt(blue, 16)

						));
			} catch (NumberFormatException e) {

			}
		} else {
			throw new IllegalArgumentException("Wrong command argument!");
		}

		return null;
	}

	/**
	 * Class that represents an implementation of the {@link LSystem}. Here we are
	 * generating an axiom for each level of the {@link LSystem}. Axioms are built
	 * by using production rules from our {@link Dictionary} productions. Here we
	 * are also drawing the image, based on the different actions from our
	 * {@link Dictionary} commands.
	 * 
	 * @author ilovrencic
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Method that draws the actual {@link LSystem}. It does that by generating
		 * axiom for each level of the system, and than going through all the actions in
		 * our {@link Dictionary} with commands. Those commands execute different
		 * actions that ultimately result in drawn {@link LSystem}.
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context context = new Context();

			Vector2D defaultDirection = new Vector2D(1, 0);
			defaultDirection.rotate(angle);
			double step = unitLength * Math.pow(unitLengthDegreeScaler, level);

			TurtleState turtle = new TurtleState(origin, defaultDirection, Color.black, step);
			context.pushState(turtle);

			char[] generatedState = generate(level).toCharArray();
			for (Character action : generatedState) {
				if (commands.get(action) == null) {
					continue;
				}

				Command command = commands.get(action);
				command.execute(context, painter);
			}
		}

		/**
		 * Method that generates axiom based on the current {@link LSystem} level and
		 * production rules we have in our {@link Dictionary}.
		 */
		@Override
		public String generate(int level) {
			if (level == 0) {
				return axiom;
			}

			String generatedAxiom = "";

			char[] currentAxiom = axiom.toCharArray();
			for (int i = 0; i < level; i++) {
				generatedAxiom = "";

				for (Character action : currentAxiom) {
					if (productions.get(action) == null) {
						generatedAxiom += action.toString();
						continue;
					}

					generatedAxiom += productions.get(action);
				}

				currentAxiom = generatedAxiom.toCharArray();
			}

			return generatedAxiom;
		}

	}

}
