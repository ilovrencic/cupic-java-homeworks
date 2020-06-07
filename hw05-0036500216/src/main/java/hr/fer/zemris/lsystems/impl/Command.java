package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface {@link Command}.
 * 
 * @author ilovrencic
 *
 */
public interface Command {

	/**
	 * Method that executes certain task with {@link Context} and {@link Painter}.
	 * 
	 * @param ctx     - Context that holds current {@link TurtleState}
	 * @param painter - instance that draws on the canvas
	 */
	void execute(Context ctx, Painter painter);

}
