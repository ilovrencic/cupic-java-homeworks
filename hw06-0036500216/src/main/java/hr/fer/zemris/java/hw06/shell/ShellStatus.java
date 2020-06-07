package hr.fer.zemris.java.hw06.shell;

/**
 * Enum that represents a current status of the {@link MyShell}.
 * 
 * @author ilovrencic
 *
 */
public enum ShellStatus {

	/**
	 * Represents a state where the {@link MyShell} continues to work.
	 */
	CONTINUE,

	/**
	 * Represents a state where {@link MyShell} stops operating and shuts down.
	 */
	TERMINATE

}
