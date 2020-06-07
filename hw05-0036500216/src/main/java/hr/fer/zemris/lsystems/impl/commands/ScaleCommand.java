package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements {@link Command} interface. This class is used to scale
 * the current step thats the {@link TurtleState} taking.
 * 
 * @author ilovrencic
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Factor by which we want the change the current step
	 */
	private double factor;

	/**
	 * Default constructor
	 * 
	 * @param factor - by which we want to change the current step
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Method that multiples current step by the factor we passed.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		double currentStep = currentState.getStep();
		currentState.setStep(currentStep * factor);
	}

}
