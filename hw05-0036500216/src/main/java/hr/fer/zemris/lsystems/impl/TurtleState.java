package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents a current state of the drawing pointer on the canvas.
 * We can image there is a turtle on the screen that moves and behind it leaves
 * a trace. That trace is actually a line that is drawing.
 * 
 * @author ilovrencic
 *
 */
public class TurtleState {

	/**
	 * Vector that represent a current position of the turtle.
	 */
	private Vector2D currentPosition;

	/**
	 * Vector that represent a current direction the turtle is facing to.
	 */
	private Vector2D direction;

	/**
	 * Color of the line that turtle will leave.
	 */
	private Color drawColor;

	/**
	 * Factor by which we want to take a leap.
	 */
	private double step;

	/**
	 * Default constructor
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color drawColor, double step) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.drawColor = drawColor;
		this.step = step;
	}

	/* --------- GETTERS -------------- */

	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public Color getDrawColor() {
		return drawColor;
	}

	public double getStep() {
		return step;
	}

	/* -------------- SETTERS --------------- */

	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	public void setStep(double step) {
		this.step = step;
	}

	/**
	 * Method that makes and returns a new instance of the {@link TurtleState}
	 * 
	 * @return - new instance of the {@link TurtleState}.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition, direction, drawColor, step);
	}

}
