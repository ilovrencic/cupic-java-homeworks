package hr.fer.zemris.java.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo for {@link LSystemBuilderImpl}
 * @author ilovrencic
 *
 */
public class Glavni3 {

	public static void main(String[] args) {
	
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
