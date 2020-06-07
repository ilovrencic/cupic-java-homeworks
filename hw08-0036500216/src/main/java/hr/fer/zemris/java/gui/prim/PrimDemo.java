package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that starts the screen and program that showcases the
 * {@link PrimListModel}.
 * 
 * @author ilovrencic
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -1305147476023758530L;

	private PrimListModel model;

	/**
	 * Default constructor
	 */
	public PrimDemo() {
		model = new PrimListModel();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(500, 500);

		initGUI();
	}

	/**
	 * Method that creates GUI for {@link PrimDemo}. We create one
	 * {@link PrimListModel} and add it twice on the screen.
	 */
	private void initGUI() {
		Container c = getContentPane();
		JButton next = new JButton("Next prime number!");
		next.addActionListener(l -> {
			model.next();
		});
		c.add(next, BorderLayout.PAGE_END);

		JPanel lists = new JPanel(new GridLayout(1, 2));
		JScrollPane list1 = new JScrollPane(new JList<Integer>(model));
		list1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		JScrollPane list2 = new JScrollPane(new JList<Integer>(model));
		list2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		lists.add(list1);
		lists.add(list2);

		c.add(lists, BorderLayout.CENTER);
	}

	/**
	 * Main method that starts the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo demo = new PrimDemo();
			demo.setVisible(true);
		});
	}
}
