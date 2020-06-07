package hr.fer.zemris.java.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo for {@link LSystemBuilderImpl}
 * @author ilovrencic
 *
 */
public class Glavni2 {

	public static void main(String[] args) {

		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}

	/**
	 * 
	 * Static method that creates Koch Curve
	 * 
	 * @param provider an object which has the ability to configure itself and
	 *                 create a {@link LSystem}.
	 * 
	 * @return a complete Lindermayer system
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] { "origin                 0.05 0.4", "angle                  0",
				"unitLength             0.9", "unitLengthDegreeScaler 1.0 / 3.0", "", "command F draw 1",
				"command + rotate 60", "command - rotate -60", "", "axiom F", "", "production F F+F--F+F" };
		return provider.createLSystemBuilder().configureFromText(data).build();
	}

}
