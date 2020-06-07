package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that implements {@link Command} interface. This class moves
 * {@link TurtleState} by passed <code>step</code> without drawing a line.
 * 
 * @author ilovrencic
 *
 */
public class SkipCommand implements Command {

	/**
	 * Factor by which we want to move {@link TurtleState}
	 */
	private double step;

	/**
	 * Default constructor
	 * 
	 * @param step - factor by which we want to move {@link TurtleState}.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Method that moves {@link TurtleState} by the passed <code>step</code>.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		Vector2D currentPosition = currentState.getCurrentPosition();
		Vector2D direction = currentState.getDirection().copy();

		direction.scale(step * currentState.getStep());
		Vector2D newPosition = currentPosition.translated(direction);

		currentState.setCurrentPosition(newPosition);
	}

}
