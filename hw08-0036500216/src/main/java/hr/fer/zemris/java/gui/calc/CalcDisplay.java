package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Class that represent the display on calculator. It's an observer on
 * {@link CalcModel}.
 * 
 * @author ilovrencic
 *
 */
public class CalcDisplay extends JLabel implements CalcValueListener {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 4650568173586419990L;

	/**
	 * Default constructor
	 */
	public CalcDisplay() {
		setBackground(Color.YELLOW);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setFont(getFont().deriveFont(30f));
	}

	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
	}

}
