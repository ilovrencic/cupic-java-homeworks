package hr.fer.zemris.hw05.db;

/**
 * LexerException is custom exception that's triggered when user tries to get
 * new token when there isn't new token.
 * 
 * @author ilovrencic
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -6893320123466462239L;
	
	/**
	 * Default constructor
	 */
	public LexerException() {
		super();
	}

	/**
	 * Default constructor
	 * @param msg - error message
	 */
	public LexerException(String msg) {
		super(msg);
	}

	/**
	 * Default constructor
	 * @param error - error instance
	 */
	public LexerException(Throwable error) {
		super(error);
	}

	/**
	 * Default constructor
	 * @param msg - error message
	 * @param error - error instance
	 */
	public LexerException(String msg, Throwable error) {
		super(msg, error);
	}
}
