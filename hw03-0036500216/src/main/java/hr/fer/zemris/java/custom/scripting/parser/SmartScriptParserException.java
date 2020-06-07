package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Custom exception for {@link SmartScriptParser}
 * @author ilovrencic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Random generated serial version
	 */
	private static final long serialVersionUID = -7146405271722626823L;
	
	/**
	 * Default constructor for {@link SmartScriptParserException}
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Default constructor for {@link SmartScriptParserException}
	 * @param msg - error message
	 */
	public SmartScriptParserException(String msg) {
		super(msg);
	}
	
	/**
	 * Default constructor for {@link SmartScriptParserException}
	 * @param error - error type
	 */
	public SmartScriptParserException(Throwable error) {
		super(error);
	}
	
	/**
	 * Default constructor for {@link SmartScriptParserException}
	 * @param msg - error message
	 * @param error - error type
	 */
	public SmartScriptParserException(String msg,Throwable error) {
		super(msg,error);
	}

}
