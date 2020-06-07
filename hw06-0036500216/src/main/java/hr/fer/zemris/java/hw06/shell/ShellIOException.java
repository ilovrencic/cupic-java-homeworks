package hr.fer.zemris.java.hw06.shell;

public class ShellIOException extends RuntimeException {

	/**
	 * Generated serial key
	 */
	private static final long serialVersionUID = 3844731367581627444L;

	/**
	 * Default constructor without passed arguments
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Default constructor with passed message
	 * 
	 * @param msg - passed error message
	 */
	public ShellIOException(String msg) {
		super(msg);
	}

}
