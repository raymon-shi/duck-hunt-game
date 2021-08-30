import java.util.ArrayList;

public class SavedData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public GameStates gameStates;
	public ArrayList<Bird> birds;
	public ArrayList<Bird> kill;
	public int points;
	public int highScore;
}
