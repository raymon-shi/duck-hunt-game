import java.util.ArrayList;

public class DuckHunt {
	
	ArrayList<Bird> birds = new ArrayList<Bird>();
	public int points;
	public int highScore;
	
	public int getPoints() {
		return points;
	}
		
	public int getHighScore() {
		return highScore;
	}
	
	public void setHighScore(int newHighScore) {
		highScore = newHighScore;
	}
	
	public ArrayList<Bird> getBirdList() {
		return birds;
	}

	public ArrayList<Bird> createBirdList() {
		return new ArrayList<Bird>();
	}
	
	public void addBird (Bird someBird) {
		birds.add(someBird);
	}

	public void removeBird (Bird someBird) {
		birds.remove(someBird);
	}
}
