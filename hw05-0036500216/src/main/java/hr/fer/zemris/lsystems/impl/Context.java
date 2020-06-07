package hr.fer.zemris.lsystems.impl;

import java.util.NoSuchElementException;
import hr.fer.zemris.lsystems.custom.collections.ObjectStack;

/**
 * Class that represents the {@link Context} of the current system building
 * process. It holds all the states the {@link TurtleState} is.
 * 
 * @author ilovrencic
 *
 */
public class Context {

	/**
	 * Collection that holds {@link TurtleState}.
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Default constructor
	 */
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}

	/**
	 * Method that returns the current state. That's the state that is on the top of
	 * the stack.
	 * 
	 * @return
	 */
	public TurtleState getCurrentState() {
		if (stack.isEmpty()) {
			throw new NoSuchElementException("Context is empty!");
		}

		return (TurtleState) stack.peek();
	}

	/**
	 * Method that pushes the @param state on the top of the stack.
	 * 
	 * @param state
	 */
	public void pushState(TurtleState state) {
		if (state == null) {
			throw new NullPointerException("State can't be null!");
		}

		stack.push(state);
	}

	/**
	 * Method that removes the top state from the stack.
	 */
	public void popState() {
		if (stack.isEmpty()) {
			throw new IllegalStateException("Stack is already empty!");
		}

		stack.pop();
	}

}
