package cz.gattserver.minesweeper;

import java.awt.AWTException;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws AWTException {
		// Intermediate
		Config config = new Config().setScreenX(541).setScreenY(120).setScreenW(276).setScreenH(318).setInScreenX(10)
				.setInScreenY(52).setFieldH(16).setFieldW(16).setStatusX(125).setStatusY(13).setStatusW(26)
				.setStatusH(26).setImagesDirectory("minesweeperonline").setFieldsXCount(16).setFieldsYCount(16);

		// Beginner
		// Config config = new
		// Config().setScreenX(541).setScreenY(120).setScreenW(164).setScreenH(206).setInScreenX(10)
		// .setInScreenY(52).setFieldH(16).setFieldW(16).setStatusX(69).setStatusY(13).setStatusW(26)
		// .setStatusH(26).setImagesDirectory("minesweeperonline").setFieldsXCount(9).setFieldsYCount(9);

		Scanner scanner = new Scanner("c:\\Users\\gatta\\Downloads\\", config);

		// po�kej 3s aby se dalo p�epnout na obrazovku s minesweeperem
		IntStream.range(0, 3).forEach(i -> {
			logger.info("Start za {}s", 3 - i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("P�i �ek�n� do�lo k chyb�", e);
			}
		});

		Solver solver = new Solver(new MouseRobot(config), scanner);
		logger.info("Spou�t�m �e�en�");
		solver.solve();
	}
}
