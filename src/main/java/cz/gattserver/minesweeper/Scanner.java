package cz.gattserver.minesweeper;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scanner {

	private static Logger logger = LoggerFactory.getLogger(Scanner.class);

	private File testScreensDir;
	private ScannerConfig config;

	public Scanner(String testScreensDir, ScannerConfig config) {
		this.testScreensDir = new File(testScreensDir);
		this.config = config;
	}

	public Game capture() {
		BufferedImage image;
		try {
			logger.info("Screenshot");
			image = new Robot().createScreenCapture(
					new Rectangle(config.getScreenX(), config.getScreenY(), config.getScreenW(), config.getScreenH()));
			ImageIO.write(image, "png", new File(testScreensDir, "screenshot.png"));
		} catch (Exception e) {
			throw new IllegalStateException("Nezdaøilo se získat screenshot obrazovky");
		}
		return parse(image);
	}

	private boolean compare(BufferedImage imgA, File toCompare) {
		try {
			BufferedImage imgB = ImageIO.read(toCompare);
			int width1 = imgA.getWidth();
			int width2 = imgB.getWidth();
			int height1 = imgA.getHeight();
			int height2 = imgB.getHeight();
			if ((width1 != width2) || (height1 != height2))
				return false; // 100% rozdíl
			else {
				long difference = 0;
				for (int y = 0; y < height1; y++) {
					for (int x = 0; x < width1; x++) {
						int rgbA = imgA.getRGB(x, y);
						int rgbB = imgB.getRGB(x, y);

						int redA = (rgbA >> 16) & 0xff;
						int greenA = (rgbA >> 8) & 0xff;
						int blueA = (rgbA) & 0xff;
						int redB = (rgbB >> 16) & 0xff;
						int greenB = (rgbB >> 8) & 0xff;
						int blueB = (rgbB) & 0xff;
						difference += Math.abs(redA - redB);
						difference += Math.abs(greenA - greenB);
						difference += Math.abs(blueA - blueB);
					}
				}

				// Total number of red pixels = width * height
				// Total number of blue pixels = width * height
				// Total number of green pixels = width * height
				// So total number of pixels = width * height * 3
				double totalPixels = width1 * height1 * 3.0;

				// Normalizing the value of different pixels
				// for accuracy(average pixels per color
				// component)
				double avgDifferentPixels = difference / totalPixels;

				// There are 255 values of pixels in total
				return avgDifferentPixels == 0;
			}
		} catch (Exception e) {
			logger.error("Nezdaøilo se porovnat obrázky", e);
			return false;
		}
	}

	private GameState parseGameState(BufferedImage image) {
		BufferedImage bi = image.getSubimage(config.getStatusX(), config.getStatusY(), config.getStatusW(),
				config.getStatusH());
		if (compare(bi, config.getStatusPlayingFile()))
			return GameState.PLAYING;
		if (compare(bi, config.getStatusLostFile()))
			return GameState.LOST;
		return GameState.WON;
	}

	private FieldType parseFieldType(BufferedImage image, int x, int y) {
		BufferedImage bi;
		try {
			bi = image.getSubimage(config.getInScreenX() + x * config.getFieldW(),
					config.getInScreenY() + y * config.getFieldH(), config.getFieldW(), config.getFieldH());
		} catch (Exception e) {
			logger.error("Nezdaøilo se získat sub-image pro x:{} y:{}", x, y);
			throw new IllegalStateException(e);
		}

		if (compare(bi, config.getFieldUnknownFile()))
			return FieldType.UNKNOWN;
		if (compare(bi, config.getField1File()))
			return FieldType.HINT_1;
		if (compare(bi, config.getField2File()))
			return FieldType.HINT_2;
		if (compare(bi, config.getField3File()))
			return FieldType.HINT_3;
		if (compare(bi, config.getField4File()))
			return FieldType.HINT_4;
		if (compare(bi, config.getField5File()))
			return FieldType.HINT_5;
		if (compare(bi, config.getFieldBlankFile()))
			return FieldType.BLANK;
		return FieldType.FLAG;
	}

	private Game parse(BufferedImage image) {
		Game game = new Game(config.getFieldsXCount(), config.getFieldsYCount());

		// Game state
		game.setState(parseGameState(image));

		// Game fields
		IntStream.range(0, config.getFieldsYCount()).forEach(y -> IntStream.range(0, config.getFieldsXCount())
				.forEach(x -> game.getFields()[x][y] = parseFieldType(image, x, y)));

		// DEBUG
		System.out.println("State: " + game.getState());
		IntStream.range(0, config.getFieldsYCount()).forEach(y -> {
			IntStream.range(0, config.getFieldsXCount()).forEach(x -> {
				switch (game.getFields()[x][y]) {
				case BLANK:
					System.out.print("  ");
					break;
				case HINT_1:
					System.out.print("1 ");
					break;
				case HINT_2:
					System.out.print("2 ");
					break;
				case HINT_3:
					System.out.print("3 ");
					break;
				case HINT_4:
					System.out.print("4 ");
					break;
				case HINT_5:
					System.out.print("5 ");
					break;
				case UNKNOWN:
					System.out.print("- ");
					break;
				default:
					System.out.print("% ");
					break;
				}
			});
			System.out.println();
		});

		return game;
	}

}
