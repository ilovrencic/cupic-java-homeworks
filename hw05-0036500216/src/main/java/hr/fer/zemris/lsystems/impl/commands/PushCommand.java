package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements interface {@link Command}. This class is used to push
 * current {@link TurtleState} to the {@link Context}.
 * 
 * @author ilovrencic
 *
 */
public class PushCommand implements Command {

	/**
	 * Method that pushes current {@link TurtleState} to the {@link Context}.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		ctx.pushState(currentState.copy());
	}

}
