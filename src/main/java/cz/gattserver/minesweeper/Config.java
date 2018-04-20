package cz.gattserver.minesweeper;

import java.io.File;

public class Config {

	private final static String FIELD_1 = "field_1.png";
	private final static String FIELD_2 = "field_2.png";
	private final static String FIELD_3 = "field_3.png";
	private final static String FIELD_4 = "field_4.png";
	private final static String FIELD_5 = "field_5.png";
	private final static String FIELD_BLANK = "field_blank.png";
	private final static String FIELD_UNKNOWN = "field_unknown.png";
	private final static String FIELD_FLAG = "field_flag.png";
	private final static String STATUS_WON = "status_won.png";
	private final static String STATUS_LOST = "status_lost.png";

	private int screenX;
	private int screenY;
	private int screenW;
	private int screenH;

	private int statusX;
	private int statusY;
	private int statusH;
	private int statusW;

	private int inScreenX;
	private int inScreenY;

	private int fieldsXCount;
	private int fieldsYCount;

	private int fieldW;
	private int fieldH;

	private File imagesDirectory;

	public int getFieldsXCount() {
		return fieldsXCount;
	}

	public Config setFieldsXCount(int fieldsXCount) {
		this.fieldsXCount = fieldsXCount;
		return this;
	}

	public int getFieldsYCount() {
		return fieldsYCount;
	}

	public Config setFieldsYCount(int fieldsYCount) {
		this.fieldsYCount = fieldsYCount;
		return this;
	}

	public int getScreenX() {
		return screenX;
	}

	public Config setScreenX(int screenX) {
		this.screenX = screenX;
		return this;
	}

	public int getScreenW() {
		return screenW;
	}

	public Config setScreenW(int screenW) {
		this.screenW = screenW;
		return this;
	}

	public int getScreenH() {
		return screenH;
	}

	public Config setScreenH(int screenH) {
		this.screenH = screenH;
		return this;
	}

	public int getScreenY() {
		return screenY;
	}

	public Config setScreenY(int screenY) {
		this.screenY = screenY;
		return this;
	}

	public int getStatusX() {
		return statusX;
	}

	public Config setStatusX(int statusX) {
		this.statusX = statusX;
		return this;
	}

	public int getStatusY() {
		return statusY;
	}

	public Config setStatusY(int statusY) {
		this.statusY = statusY;
		return this;
	}

	public int getStatusH() {
		return statusH;
	}

	public Config setStatusH(int statusH) {
		this.statusH = statusH;
		return this;
	}

	public int getStatusW() {
		return statusW;
	}

	public Config setStatusW(int statusW) {
		this.statusW = statusW;
		return this;
	}

	public int getInScreenX() {
		return inScreenX;
	}

	public Config setInScreenX(int inScreenX) {
		this.inScreenX = inScreenX;
		return this;
	}

	public int getInScreenY() {
		return inScreenY;
	}

	public Config setInScreenY(int inScreenY) {
		this.inScreenY = inScreenY;
		return this;
	}

	public int getFieldW() {
		return fieldW;
	}

	public Config setFieldW(int fieldW) {
		this.fieldW = fieldW;
		return this;
	}

	public int getFieldH() {
		return fieldH;
	}

	public Config setFieldH(int fieldH) {
		this.fieldH = fieldH;
		return this;
	}

	public Config setImagesDirectory(String imagesDirectory) {
		this.imagesDirectory = new File(getClass().getClassLoader().getResource("minesweeperonline").getFile());
		return this;
	}

	public File getStatusWonFile() {
		return new File(imagesDirectory, STATUS_WON);
	}

	public File getStatusLostFile() {
		return new File(imagesDirectory, STATUS_LOST);
	}

	public File getFieldUnknownFile() {
		return new File(imagesDirectory, FIELD_UNKNOWN);
	}

	public File getField1File() {
		return new File(imagesDirectory, FIELD_1);
	}

	public File getField2File() {
		return new File(imagesDirectory, FIELD_2);
	}

	public File getField3File() {
		return new File(imagesDirectory, FIELD_3);
	}

	public File getField4File() {
		return new File(imagesDirectory, FIELD_4);
	}

	public File getField5File() {
		return new File(imagesDirectory, FIELD_5);
	}

	public File getFieldBlankFile() {
		return new File(imagesDirectory, FIELD_BLANK);
	}

	public File getFieldFlagFile() {
		return new File(imagesDirectory, FIELD_FLAG);
	}

}
