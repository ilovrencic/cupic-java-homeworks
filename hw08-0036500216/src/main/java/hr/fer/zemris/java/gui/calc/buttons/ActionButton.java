package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Class that models all the action buttons like "clr" or "push" or "pop".
 * 
 * @author ilovrencic
 *
 */
public class ActionButton extends JButton {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 7257409631439864790L;

	/**
	 * Represents an instance of {@link ActionListener}. Because of that we can pass
	 * custom listener that does whatever we want.
	 */
	private ActionListener listener;

	/**
	 * Represents a text on the button.
	 */
	private String text;

	/**
	 * Default constructor
	 * 
	 * @param text     - text on the button
	 * @param listener - instance of the {@link ActionListener}
	 */
	public ActionButton(String text, ActionListener listener) {
		this.text = text;
		this.listener = listener;

		initGUI();
		setOnClickListener();
	}

	/**
	 * Method that adds listener, and when someone presses the button it will
	 * trigger the model.
	 */
	private void setOnClickListener() {
		addActionListener(listener);
	}

	/**
	 * Method that initializes GUI for this button.
	 */
	private void initGUI() {
		setBackground(Color.DARK_GRAY);
		setHorizontalAlignment(SwingConstants.CENTER);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setFont(getFont().deriveFont(30f));
		setText(text);
	}

}
