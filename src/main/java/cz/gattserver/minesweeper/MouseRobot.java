package cz.gattserver.minesweeper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MouseRobot {

	private static Logger logger = LoggerFactory.getLogger(MouseRobot.class);

	private Config config;

	public MouseRobot(Config config) {
		this.config = config;
	}

	public void move(double x, double y) throws AWTException {
		Robot bot = new Robot();
		bot.mouseMove((int) x, (int) y);
	}

	public void moveOnField(int x, int y) throws AWTException {
		logger.info("Posouvám myš na souøadnice x:{}, y:{}", x, y);
		Robot bot = new Robot();
		bot.mouseMove(x, y);
	}

	public void clickOnField(int x, int y, int button) throws AWTException {
		moveOnField(config.getScreenX() + config.getInScreenX() + config.getFieldW() * x + config.getFieldW() / 2,
				config.getScreenY() + config.getInScreenY() + config.getFieldH() * y + config.getFieldH() / 2);
		Robot bot = new Robot();
		logger.info("Provádím kliknutí na pole x:{}, y:{}", x, y);
		switch (button) {
		case 1:
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			break;
		case 2:
			bot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
			break;
		case 3:
			bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			break;
		}

	}

}
