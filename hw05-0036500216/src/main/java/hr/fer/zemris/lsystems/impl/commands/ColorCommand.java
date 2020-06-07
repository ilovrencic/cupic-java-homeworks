package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class implementation of interface {@link Command}. This class serves as a
 * command that changes a line color.
 * 
 * @author ilovrencic
 *
 */
public class ColorCommand implements Command {

	/**
	 * Represents a color to which we want to change line.
	 */
	private Color color;

	/**
	 * Default constructor
	 * 
	 * @param color - color we want for line color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Method that changes the current line color of the {@link TurtleState}.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		currentState.setDrawColor(color);
	}

}
