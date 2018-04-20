package cz.gattserver.minesweeper;

public class Game {

	private GameState state = GameState.PLAYING;
	private int width;
	private int height;

	private FieldType[][] fields;

	public Game(int width, int height) {
		fields = new FieldType[width][height];
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

	public void setFields(FieldType[][] fields) {
		this.fields = fields;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
