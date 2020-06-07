package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Custom made exception that will be thrown when stack is empty, but there is some action
 * that tries to pop or peek from it.
 * @author ilovrencic
 *
 */
public class EmptyStackException extends RuntimeException {
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 8346992031479365987L;

	/**
	 * Default {@link EmptyStackException} constructor that accepts message
	 * @param message 
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Default {@link EmptyStackException} constructor that accepts error
	 * @param error
	 */
	public EmptyStackException(Throwable error) {
		super(error);
	}
	
	/**
	 * Default {@link EmptyStackException} constructor that accepts message and error
	 * @param message
	 * @param error
	 */
	public EmptyStackException(String message,Throwable error) {
		super(message,error);
	}
}
