package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class implementation of interface {@link Command}. This class is used to pop
 * top state from the stack in the {@link Context}.
 * 
 * @author ilovrencic
 *
 */
public class PopCommand implements Command {

	/**
	 * Method that pops state from the {@link Context}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
