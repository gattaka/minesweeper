package cz.gattserver.minesweeper;

import java.awt.AWTException;
import java.awt.Robot;

public class MouseRobot {

	public void move(double x, double y) throws AWTException {
		Robot bot = new Robot();
		bot.mouseMove((int) x, (int) y);
	}

	public void click() {

	}

	public void clickRightMouse() {

	}

}
