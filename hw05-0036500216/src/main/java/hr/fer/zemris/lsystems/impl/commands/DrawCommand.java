package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * 
 * Class implementation of the interface {@link Command}. This class draws a
 * line with a <code>step</code> we pass through.
 * 
 * @author ilovrencic
 *
 */
public class DrawCommand implements Command {

	/**
	 * Represents a step for how far do we want to move {@link TurtleState}.
	 */
	private double step;

	/**
	 * Default constructor where we pass @param step
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Method that moves {@link TurtleState} to certain position on the screen and
	 * draws a line while moving it.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		Vector2D currentPosition = currentState.getCurrentPosition();
		Vector2D direction = currentState.getDirection().copy();

		direction.scale(step * currentState.getStep());
		Vector2D newPosition = currentPosition.translated(direction);

		painter.drawLine(currentPosition.getX(), currentPosition.getY(), newPosition.getX(), newPosition.getY(),
				currentState.getDrawColor(), 1);
		currentState.setCurrentPosition(newPosition);
	}

}
