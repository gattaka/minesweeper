package cz.gattserver.minesweeper;

public class Game {

	private GameState state = GameState.PLAYING;
	private int width;
	private int height;

	private FieldType[][] fields;
	private boolean[][] skipHint;

	public Game(int width, int height) {
		fields = new FieldType[width][height];
		skipHint = new boolean[width][height];
		this.width = width;
		this.height = height;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public FieldType[][] getFields() {
		return fields;
	}

	public boolean[][] getSkipHint() {
		return skipHint;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
