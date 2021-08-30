import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Chicken extends Rectangle implements Bird {
	private String name;
	
	private int height;
	private int width;
	private int heightStart;
	private int widthStart;
	
	private int xPosition;
	private int yPosition;
	
	private double xSpeed;
	private double ySpeed;
	private double xSpeedStart;
	private double ySpeedStart;

	private int points;
	private int pointsStart;

	
	public Chicken () {
		name = "Chicken";
		height = 150;
		heightStart = 120;
		width = 200;
		widthStart = 160;
		xPosition = (int) ((1280 - width) * Math.random());
		yPosition = (int) ((720 - height) * Math.random());
		this.setRect(xPosition, yPosition, width, height);
		points = -100;
		pointsStart = -100;
		xSpeedStart = 25;
		ySpeedStart = 25;
		xSpeed = 25;
		ySpeed = 25;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getHeightBird() {
		return height;
	}

	@Override
	public int getWidthBird() {
		return width;
	}
	
	@Override
	public int getXPosition() {
		return xPosition;
	}

	@Override
	public int getYPosition() {
		return yPosition;
	}
	
	@Override
	public void setXPosition(int xPos) {
		 xPosition = xPos;
	}

	@Override
	public void setYPosition(int yPos) {
		yPosition = yPos;
	}
	
	@Override
	public double getXSpeed() {
		return xSpeed;
	}

	@Override
	public double getYSpeed() {
		return ySpeed;
	}
	
	@Override
	public int getPoints() {
		return points;
	}
	
	@Override
	public void ratioMultiplier(double speedR, int heightR, int widthR, int pointsR) {
		xSpeed = xSpeedStart * speedR;
		ySpeed = ySpeedStart * speedR;
//		height = heightStart * heightR;
//		width = widthStart * widthR;
		points = pointsStart * pointsR;
	}
	
	@Override
	public void draw(Graphics g, BufferedImage b) {
		g.drawImage(b, xPosition, yPosition, widthStart, heightStart, null);
	}
	
	public void drawRandom(Graphics g, BufferedImage b) {
		int randomNum = (int) Math.round((Math.random() * (1280 - width)));
		int randomNum2 = (int) Math.round((Math.random() * (720 - height)));
		g.drawImage(b, randomNum, randomNum2, width ,
				height, null);
		this.setRect(xPosition, yPosition, width, height);
	}

	@Override
	public void move() {
		xPosition = (int) (xPosition + (xSpeed * Math.pow(-1, Math.round(Math.random()))));
		yPosition = (int) (yPosition + (ySpeed * Math.pow(-1, Math.round(Math.random()))));
		this.setRect(xPosition, yPosition, width, height);
	}
	
	public void swarm(Graphics g , BufferedImage b) {
		for (int i = 0; i < 5; i++) {
			drawRandom(g, b);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
}
