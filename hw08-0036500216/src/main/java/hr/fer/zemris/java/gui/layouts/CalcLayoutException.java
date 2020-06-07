package hr.fer.zemris.java.gui.layouts;

/**
 * Class that defines {@link CalcLayout} exception. This error is thrown where
 * there is some {@link CalcLayout} related exception.
 * 
 * @author ilovrencic
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Generated serial UID
	 */
	private static final long serialVersionUID = -3503721957041493250L;

	/**
	 * Default constructor that passed the error message
	 * 
	 * @param msg - error message
	 */
	public CalcLayoutException(String msg) {
		super(msg);
	}

}
