package cz.gattserver.minesweeper;

import java.awt.AWTException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private static volatile boolean wPressed = false;

	public static boolean isWPressed() {
		synchronized (Main.class) {
			return wPressed;
		}
	}

	public static void main(String[] args) throws AWTException {

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
				synchronized (Main.class) {
					switch (ke.getID()) {
					case KeyEvent.KEY_PRESSED:
						if (ke.getKeyCode() == KeyEvent.VK_W) {
							wPressed = true;
						}
						break;

					case KeyEvent.KEY_RELEASED:
						if (ke.getKeyCode() == KeyEvent.VK_W) {
							wPressed = false;
						}
						break;
					}
					return false;
				}
			}
		});

		// Beginner
		// Config config = new
		// Config().setScreenX(541).setScreenY(120).setScreenW(164).setScreenH(206).setInScreenX(10)
		// .setInScreenY(52).setFieldH(16).setFieldW(16).setStatusX(69).setStatusY(13).setStatusW(26)
		// .setStatusH(26).setImagesDirectory("minesweeperonline").setFieldsXCount(9).setFieldsYCount(9);

		// Intermediate
		Config config = new Config().setScreenX(541).setScreenY(120).setScreenW(276).setScreenH(318).setInScreenX(10)
				.setInScreenY(52).setFieldH(16).setFieldW(16).setStatusX(125).setStatusY(13).setStatusW(26)
				.setStatusH(26).setImagesDirectory("minesweeperonline").setFieldsXCount(16).setFieldsYCount(16);

		// Expert
		// Config config = new
		// Config().setScreenX(531).setScreenY(120).setScreenW(500).setScreenH(318).setInScreenX(10)
		// .setInScreenY(52).setFieldH(16).setFieldW(16).setStatusX(237).setStatusY(13).setStatusW(26)
		// .setStatusH(26).setImagesDirectory("minesweeperonline").setFieldsXCount(30).setFieldsYCount(16);

		Scanner scanner = new Scanner("c:\\Users\\gatta\\Downloads\\", config);

		// poèkej 3s aby se dalo pøepnout na obrazovku s minesweeperem
		IntStream.range(0, 3).forEach(i -> {
			logger.info("Start za {}s", 3 - i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Pøi èekání došlo k chybì", e);
			}
		});

		Solver solver = new Solver(new MouseRobot(config), scanner);
		logger.info("Spouštím øešení");
		solver.solve();
	}
}
