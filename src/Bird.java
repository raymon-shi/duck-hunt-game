import java.awt.Graphics;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface Bird extends Shape {
	public String getName();
	
	public int getHeightBird();
	public int getWidthBird();
	
	public int getXPosition();
	public int getYPosition();
	
	public void setXPosition(int xPos);
	public void setYPosition(int yPos);
	
	public double getXSpeed();
	public double getYSpeed();
	
	public int getPoints();
	
	public void ratioMultiplier(double speedR, int heightR, int widthR, int pointsR);
	
	public void move();
	public void swarm(Graphics g, BufferedImage b);

	void draw(Graphics g, BufferedImage b);
}
