package cz.gattserver.minesweeper;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Solver {

	private static Logger logger = LoggerFactory.getLogger(Solver.class);

	private MouseRobot mouseRobot;
	private Scanner scanner;

	public Solver(MouseRobot clicker, Scanner scanner) {
		this.mouseRobot = clicker;
		this.scanner = scanner;
	}

	public void solve() throws AWTException {

		// dej myš nìkam do pryè
		logger.info("Mouse temp shift");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mouseRobot.move(dim.getWidth(), dim.getHeight());

		Game game = scanner.capture();
		// TODO cykl

	}

}
