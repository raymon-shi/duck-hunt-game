import static org.junit.Assert.*;

import java.nio.file.NoSuchFileException;

import javax.swing.JLabel;

import org.junit.Test;

public class Game_TEST {

	@Test
	public void getDuckInitialDataTest() {
		Duck duck = new Duck();
		assertTrue(duck.getName().equals("Duck"));
		assertEquals(100, duck.getHeightBird());
		assertEquals(100, duck.getWidthBird());
		assertEquals(35, duck.getXSpeed());
		assertEquals(35, duck.getYSpeed());
		assertEquals(100, duck.getPoints());
	}
	
	@Test
	public void getChickenInitialDataTest() {
		Chicken chicken = new Chicken();
		assertTrue(chicken.getName().equals("Chicken"));
		assertEquals(100, chicken.getHeightBird());
		assertEquals(100, chicken.getWidthBird());
		assertEquals(25, chicken.getXSpeed());
		assertEquals(25, chicken.getYSpeed());
		assertEquals(-100, chicken.getPoints());
	}
	
	@Test
	public void ratioMultiplierTest() {
		Duck duck = new Duck();
		Chicken chicken = new Chicken();
		
		duck.ratioMultiplier(2, 2, 2, 2);
		assertEquals(70, duck.getXSpeed());
		assertEquals(70, duck.getYSpeed());
		assertEquals(200, duck.getHeightBird());
		assertEquals(200, duck.getWidthBird());
		assertEquals(200, duck.getPoints());
		
		duck.ratioMultiplier(2, 2, 2, 3);
		assertEquals(70, duck.getXSpeed());
		assertEquals(70, duck.getYSpeed());
		assertEquals(200, duck.getHeightBird());
		assertEquals(200, duck.getWidthBird());
		assertEquals(300, duck.getPoints());
		
		chicken.ratioMultiplier(0, 0, 0, 0);
		assertEquals(0, chicken.getXSpeed());
		assertEquals(0, chicken.getYSpeed());
		assertEquals(0, chicken.getHeightBird());
		assertEquals(0, chicken.getWidthBird());
		assertEquals(0, chicken.getPoints());
		
		chicken.ratioMultiplier(0, 0, 0, 10);
		assertEquals(0, chicken.getXSpeed());
		assertEquals(0, chicken.getYSpeed());
		assertEquals(0, chicken.getHeightBird());
		assertEquals(0, chicken.getWidthBird());
		assertEquals(-1000, chicken.getPoints());
	}
	
	@Test
	public void birdToStringTest() {
		Duck duck = new Duck();
		Chicken chicken = new Chicken();
		
		assertTrue(duck.toString().equals("Duck"));
		assertTrue(chicken.toString().equals("Chicken"));
	}
	
	@Test
	public void birdVoidTest() {
		Duck duck = null;
		Chicken chicken = null;
		
		assertEquals(null, duck);
		assertEquals(null, chicken);
	}
	
	@Test
	public void gameCanvasTest() {
		JLabel status = new JLabel();
		Game_Canvas gc = new Game_Canvas();
		
		assertEquals(1280, gc.boardW);
		assertEquals(720, gc.boardH);		
	}
	
	@Test
	public void gameCanvasResetTest() {
		JLabel status = new JLabel();
		Game_Canvas gc = new Game_Canvas();
		
		gc.reset();
		assertTrue(gc.swarmCheck == false);
		assertEquals(0, gc.swarmTick);
		assertEquals(GameStates.START, gc.gameStates);
		assertTrue(gc.stageTwoCheck == false);
		assertTrue(gc.stageThreeCheck== false);
		assertEquals(0, gc.ticksWithoutKill);
	}
	
	@Test
	public void gameCanvasSpawnTest() {
		JLabel status = new JLabel();
		Game_Canvas gc = new Game_Canvas();
		gc.gameStates = GameStates.PLAYING;
		
		gc.spawnBird();
		assertEquals(1, gc.duckHunt.getBirdList().size());
	}
	
	@Test (expected = NoSuchFileException.class)
	public void loadtest() throws Exception { 
		String filename = "abc";
			DataManager.loadAction(filename);

		assertEquals(NoSuchFileException.class, DataManager.loadAction(filename));
	}
}
