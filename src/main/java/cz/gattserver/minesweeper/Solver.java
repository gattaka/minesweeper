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

	private boolean clicked;
	private boolean stuck;

	private int stuckTolerance = 0;

	public Solver(MouseRobot clicker, Scanner scanner) {
		this.mouseRobot = clicker;
		this.scanner = scanner;
	}

	private void moveMouseAndCapture() throws AWTException {
		// dej my� n�kam do pry�
		logger.info("Odsouv�m my�");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mouseRobot.move(dim.getWidth(), dim.getHeight());

		// po�kej aby se stihla vykreslit zm�na
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			logger.error("P�i �ek�n� do�lo k chyb�", e);
		}

		scanner.capture();
	}

	public void solve() throws AWTException {

		moveMouseAndCapture();

		Game game = scanner.getGame();

		// za�ni kliknut�m doprost�ed
		logger.info("N�hodn� v�b�r");
		mouseRobot.clickOnField(game.getWidth() / 2, game.getHeight() / 2, 1);

		stuck = false;
		clicked = true;

		// prov�d� se O(x*y) kliknut�
		for (int i = 0; i < game.getWidth() * game.getHeight(); i++) {

			if (Main.isWPressed())
				return;

			if (!clicked)
				stuck = true;
			clicked = false;

			moveMouseAndCapture();

			switch (game.getState()) {
			case LOST:
				logger.info("Game lost...");
				return;
			case PLAYING:
				performSolving(game);
				break;
			default:
			case WON:
				logger.info("Game won!");
				return;
			}

			if (stuck) {
				stuckTolerance++;
				logger.info("Stuck... tolerance: {}", stuckTolerance);
				scanner.debugPrint(game);
			}
		}

	}

	private void performSolving(Game game) throws AWTException {
		for (int y = 0; y < game.getHeight(); y++)
			for (int x = 0; x < game.getWidth(); x++) {

				if (game.getSkipHint()[x][y])
					continue;

				if (stuck) {
					// hledej nezn�m� pole, obklopen� nezn�m�mi poli a
					// tam zkusmo klikni
					if (FieldType.UNKNOWN.equals(game.getFields()[x][y])) {
						int surroundingUknownCount = countSurroundFields(game, x, y, FieldType.UNKNOWN);
						if (surroundingUknownCount == 8 - stuckTolerance
								|| (x == 0 || x == game.getWidth() || y == 0 || y == game.getHeight())
										&& surroundingUknownCount == 5 - stuckTolerance) {
							clicked = true;
							stuck = false;
							mouseRobot.clickOnField(x, y, 1);
							moveMouseAndCapture();
						}
					}
				} else {
					// jinak proch�zej pouze ��sla a zkou�ej akce v
					// jejich okol�
					FieldType outcome = tryField(game, x, y);
					if (outcome != null) {
						clicked = true;
						stuck = false;
						// pokud se kliklo na unknown, je pot�eba redraw
						if (FieldType.UNKNOWN.equals(outcome))
							moveMouseAndCapture();
					}
				}
			}
	}

	private FieldType tryField(Game game, int x, int y) throws AWTException {
		int targetCount;
		switch (game.getFields()[x][y]) {
		case HINT_1:
			targetCount = 1;
			break;
		case HINT_2:
			targetCount = 2;
			break;
		case HINT_3:
			targetCount = 3;
			break;
		case HINT_4:
			targetCount = 4;
			break;
		case HINT_5:
			targetCount = 5;
			break;
		case HINT_6:
			targetCount = 6;
			break;
		case UNKNOWN:
		case BLANK:
		case FLAG:
		default:
			return null;
		}

		mouseRobot.clickOnField(x, y, 2);

		int surroundingFlagCount = countSurroundFields(game, x, y, FieldType.FLAG);
		int surroundingUknownCount = countSurroundFields(game, x, y, FieldType.UNKNOWN);

		if (surroundingUknownCount == 0) {
			game.getSkipHint()[x][y] = true;
			return null;
		}

		// proch�zej okoln� pole, kter� jsou nezn�m�
		for (int sy = y - 1; sy <= y + 1; sy++)
			for (int sx = x - 1; sx <= x + 1; sx++) {
				if (sx == x && sy == y || sx >= game.getWidth() || sx < 0 || sy >= game.getHeight() || sy < 0)
					continue;
				if (FieldType.UNKNOWN.equals(game.getFields()[sx][sy])) {
					// pokud je po�et min v okol� zkouman�ho ��sla roven tomu
					// ��slu, pak je bezpe�n� na pole klinout
					if (targetCount == surroundingFlagCount) {
						mouseRobot.clickOnField(sx, sy, 1);
						surroundingUknownCount--;
						return FieldType.UNKNOWN;
					}
					// pokud je po�et nezn�m�ch pol� v okol� zkouman�ho ��sla
					// roven tomu ��slu m�nus u� ozna�en� po�et pol�, pak
					// je pole mo�n� ozna�it jako zaminovan�
					if (targetCount - surroundingFlagCount == surroundingUknownCount) {
						mouseRobot.clickOnField(sx, sy, 3);
						surroundingFlagCount++;
						surroundingUknownCount--;
						game.getFields()[sx][sy] = FieldType.FLAG;
						game.getSkipHint()[sx][sy] = true;
						// return FieldType.UNKNOWN;
						return FieldType.FLAG;
					}
				}
			}

		return null;
	}

	private int countSurroundFields(Game game, int x, int y, FieldType surroundType) {
		int count = 0;
		for (int sy = y - 1; sy <= y + 1; sy++)
			for (int sx = x - 1; sx <= x + 1; sx++) {
				if (sx == x && sy == y || sx >= game.getWidth() || sx < 0 || sy >= game.getHeight() || sy < 0)
					continue;
				if (surroundType.equals(game.getFields()[sx][sy]))
					count++;
			}
		return count;
	}

}
