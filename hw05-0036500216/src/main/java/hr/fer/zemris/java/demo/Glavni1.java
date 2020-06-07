package hr.fer.zemris.java.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo show case for {@link LSystemBuilderImpl} 1.
 * 
 * @author ilovrencic
 *
 */
public class Glavni1 {

	/**
	 * Main method where we show case the usage
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

	}

	/**
	 * Static method that creates Koch Curve
	 * 
	 * @param provider an object which has the ability to configure itself and
	 *                 create a {@link LSystem}.
	 * 
	 * @return a complete Lindermayer system
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder().registerCommand('F', "draw 1").registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60").setOrigin(0, 0.5).setAngle(0).setUnitLength(1)
				.setUnitLengthDegreeScaler(1.0 / 3.0).registerProduction('F', "F+F--F+F").setAxiom("F").build();
	}

}
