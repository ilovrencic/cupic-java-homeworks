package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that implements {@link Command} interface. This class serves as
 * rotation command that rotates the current direction of the
 * {@link TurtleState}.
 * 
 * @author ilovrencic
 *
 */
public class RotateCommand implements Command {

	/**
	 * Angle for which we want to rotate current direction
	 */
	private double angle;

	/**
	 * Default constructor
	 * 
	 * @param angle - for which we want to rotate current direction
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Method that rotates the current direction for passed @param angle.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentDirection = currentState.getDirection();
		currentDirection.rotate(angle);
	}

}
