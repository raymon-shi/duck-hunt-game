import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Game_Canvas extends JPanel {
    public DuckHunt duckHunt = new DuckHunt();
    private ArrayList<Bird> kill = new ArrayList<Bird>();

    public static int boardW = 1280;
    public static int boardH = 720;

    //time in milliseconds
    public static final int timeInterval = 200;
    public static final int spawnInterval = 2000;
    public static final int despawnInterval = 4000;


    public GameStates gameStates = GameStates.START;
    private final JLabel status = new JLabel();

    //check for game state
    boolean stageTwoCheck = false;
    boolean stageThreeCheck = false;
    boolean gameWinCheck = false;

    //images for main bird objects
    private BufferedImage duckImage;
    private BufferedImage chickenImage;

    //images for all other background components
    private BufferedImage startImage;
    private BufferedImage stageOneImage;
    private BufferedImage stageTwoImage;
    private BufferedImage stageThreeImage;
    private BufferedImage pauseImage;
    private BufferedImage gameOverImage;
    private BufferedImage gameWinImage;
    private BufferedImage rulesImage;
    private BufferedImage level;

    //counts how many ticks without a kill
    public int ticksWithoutKill = 0;

    //checks if swarm condition was met
    public boolean swarmCheck;
    public int swarmTick;

    public int points;

    public Game_Canvas() {
        try {
            if (duckImage == null) {
                duckImage = ImageIO.read(new File("./DuckHunt_Images/duck.png"));
            }
        } catch (IOException io) {
            System.out.println("The duck image is missing or invalid!");
        }

        try {
            if (chickenImage == null) {
                chickenImage = ImageIO.read(new File("./DuckHunt_Images/chicken.png"));
            }
        } catch (IOException io) {
            System.out.println("The chicken image is missing or invalid!");
        }

        try {
            if (startImage == null) {
                startImage = ImageIO.read(new File("./DuckHunt_Images/title_screen.png"));
            }
        } catch (IOException io) {
            System.out.println("The title screen image is missing or invalid!");
        }

        try {
            if (stageOneImage == null) {
                stageOneImage = ImageIO.read(new File("./DuckHunt_Images/stage_one.png"));
            }
        } catch (IOException io) {
            System.out.println("The first stage image is missing or invalid!");
        }

        try {
            if (stageTwoImage == null) {
                stageTwoImage = ImageIO.read(new File("./DuckHunt_Images/stage_two.png"));
            }
        } catch (IOException io) {
            System.out.println("The second stage image is missing or invalid!");
        }

        try {
            if (stageThreeImage == null) {
                stageThreeImage = ImageIO.read(new File("./DuckHunt_Images/stage_three.png"));
            }
        } catch (IOException io) {
            System.out.println("The third stage image is missing or invalid!");
        }

        try {
            if (pauseImage == null) {
                pauseImage = ImageIO.read(new File("./DuckHunt_Images/pause_screen.png"));
            }
        } catch (IOException io) {
            System.out.println("The pause screen image is missing or invalid!");
        }

        try {
            if (gameOverImage == null) {
                gameOverImage = ImageIO.read(new File("./DuckHunt_Images/game_over_screen.png"));
            }
        } catch (IOException io) {
            System.out.println("The game over screen image is missing or invalid!");
        }

        try {
            if (gameWinImage == null) {
                gameWinImage = ImageIO.read(new File("./DuckHunt_Images/game_win_screen.png"));
            }
        } catch (IOException io) {
            System.out.println("The game win screen image is missing or invalid!");
        }

        try {
            if (rulesImage == null) {
                rulesImage = ImageIO.read(new File("./DuckHunt_Images/rules_screen.png"));
            }
        } catch (IOException io) {
            System.out.println("The rules screen image is missing or invalid!");
        }


        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //create important timers
        Timer gameTimer = new Timer(timeInterval, e -> tick());
        Timer spawnTimer = new Timer(spawnInterval, e -> spawnBird());
        Timer despawnTimer = new Timer(despawnInterval, e -> despawnBird());

        //starts all game related timers
        gameTimer.start();
        spawnTimer.start();
        despawnTimer.start();

    }

    //reset game to beginning state
    public void reset() {
        duckHunt = new DuckHunt();
        kill = new ArrayList<Bird>();

        level = stageOneImage;
        gameStates = GameStates.START;
        stageTwoCheck = false;
        stageThreeCheck = false;
        gameWinCheck = false;

        ticksWithoutKill = 0;
        swarmCheck = false;
        swarmTick = 0;

        status.setText("Running....");
    }

    public void spawnBird() {
        //only spawn the birds if you're actually in the state of playing the game
        if (gameStates == GameStates.PLAYING) {
            if (Math.random() < .4) {
                duckHunt.addBird(new Chicken());
            } else {
                duckHunt.addBird(new Duck());
            }
        }
    }

    public void forceSpawnBird(String birdType) {
        if (gameStates == GameStates.PLAYING) {
            if (birdType == "Duck") {
                duckHunt.addBird(new Duck());
            }
            if (birdType == "Chicken") {
                duckHunt.addBird(new Chicken());
            }
        }
    }

    public void despawnBird() {
        //only despawn the birds if you're in state of playing the game
        if (gameStates == GameStates.PLAYING) {
            //remove the least recently spawned bird
            if (duckHunt.getBirdList().size() > 0) {
                duckHunt.getBirdList().remove(0);
            }
        }
    }

    public int swarmTime = 0;
    public int currentHighScore = 0;

    public void tick() {
        if (gameStates == GameStates.PLAYING) {
            if (swarmCheck) {
                swarmTime += 150;
            }
            MouseClickListener click =
                    (MouseClickListener) this.getParent().getMouseListeners()[0];
            for (int i = duckHunt.getBirdList().size() - 1; i >= 0; i--) {
                if (click.mouseEvent != null) {
                    //use this to find
                    if (duckHunt.getBirdList().get(i).contains(click.getMouseOutput())) {
                        if (duckHunt.getBirdList().get(i).getClass() == (Chicken.class)) {
                            //bird is chicken
                            swarmCheck = true;
                            //if swarm met, will spawn 2 additional chickens along with the chicken swarm
                            for (int j = 0; j < 2; j++) {
                                forceSpawnBird("Chicken");
                            }
                        }

                        duckHunt.points =
                                duckHunt.points + duckHunt.getBirdList().get(i).getPoints();
                        if (duckHunt.getPoints() > currentHighScore) {
                            duckHunt.setHighScore(duckHunt.getPoints());
                            currentHighScore = duckHunt.getHighScore();
                        }
                        kill.add(duckHunt.getBirdList().get(i));
                        duckHunt.getBirdList().remove(i);
                        ticksWithoutKill = 0;
                    }

                }
            }

            ticksWithoutKill++;
            if (ticksWithoutKill >= 100) {
                //if no kill in 100*150 ms, you lose
                gameStates = GameStates.GAMEOVER;
            }
            click.mouseEvent = null;

            ArrayList<Bird> toRemove = duckHunt.createBirdList();

            //keep track of birds that move out of borders
            for (Bird b : duckHunt.getBirdList()) {
                b.move();
                if (b.getXPosition() > boardW - 160 || b.getYPosition() > boardH - 120 || b.getXPosition() < 0
                        || b.getYPosition() < 0) {
                    toRemove.add(b);
                }
            }

            //relocate birds that moved out of border range
            for (Bird b : toRemove) {
                Bird tempBird = b;
                duckHunt.removeBird(b);
                if (tempBird.getClass() == Duck.class) {
                    forceSpawnBird("Duck");
                }
                if (tempBird.getClass() == Chicken.class) {
                    forceSpawnBird("Chicken");
                }
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //depending on what state the game is in, draw that state of the game
        switch (gameStates) {
            case START:
                draw_start(g);
                break;
            case PLAYING:
                draw_playing(g);
                break;
            case PAUSE:
                draw_pause(g);
                break;
            case RESET:
                draw_reset(g);
                break;
            case RULES:
                draw_rules(g);
                break;
            case GAMEWIN:
                draw_gamewin(g);
                break;
            case GAMEOVER:
                draw_gameover(g);
                break;
            default:
                throw new RuntimeException("GAME STATE: " + gameStates);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardW, boardH);
    }

    public void save() throws Exception {
        SavedData saveGame = new SavedData();
        try {
            saveGame.gameStates = gameStates;
            saveGame.points = duckHunt.points;
            saveGame.kill = kill;
            saveGame.birds = duckHunt.birds;
            saveGame.highScore = duckHunt.highScore;
            DataManager.saveAction(saveGame, "DuckHuntSaved.save");

        } catch (IOException io) {
            System.out.println("Save Failed: Error:" + io.getMessage());
        }
    }

    public void load() throws Exception {
        //load data
        try {
            SavedData saveGame = (SavedData) DataManager.loadAction("DuckHuntSaved.save");
            gameStates = saveGame.gameStates;
            duckHunt.birds = saveGame.birds;
            kill = saveGame.kill;
            duckHunt.points = saveGame.points;
            duckHunt.highScore = saveGame.highScore;
            System.out.println("Your game file has loaded successfully");
        } catch (IOException io) {
            System.out.println("Load Failed: A save file does not exists! Error:" + io.getMessage());
        }
    }


    public void draw_start(Graphics g) {
        //draws the background of the start menu
        g.drawImage(startImage, 0, 0, 1280, 720, null);

        JPanel p = this;

        //creates the start button
        JButton start = new JButton("Start");
        //clicking start will make the game change to playing state
        start.addActionListener(e -> {
            gameStates = GameStates.PLAYING;
            this.removeAll();
            ;
        });
        p.add(start);

        //creates the rules button
        JButton rules = new JButton("Rules");
        //clicking rules will make the game change to rules state; brings up rules of game
        rules.addActionListener(e -> gameStates = GameStates.RULES);
        p.add(rules);

        //creates load button
        JButton load = new JButton("Load");
        //clicking will load state of game
        load.addActionListener(e -> {
            try {
                load();
            } catch (Exception io) {
                io.printStackTrace();
            }
        });
        p.add(load);
    }

    public void draw_playing(Graphics g) {
        //draws the background of the main stage
        g.drawImage(level, 0, 0, 1280, 720, null);

        JPanel p = this;

        //creates the pause button
        JButton pause = new JButton("Pause");
        //clicking will bring up the pause menu
        pause.addActionListener(e -> gameStates = GameStates.PAUSE);
        p.add(pause);
        pause.setSize(100, 30);
        pause.setLocation(640, 0);
        pause.setVisible(true);

        //creates the reset button
        JButton reset = new JButton("Reset");
        //clicking will reset the game to default
        reset.addActionListener(e -> {
            reset();
            gameStates = GameStates.PLAYING;
            this.removeAll();
        });
        p.add(reset);
        reset.setSize(100, 30);
        reset.setLocation(500, 0);
        reset.setVisible(true);

        //keeping track of your points
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        points = duckHunt.getPoints();
        g.drawString("Total Points " + duckHunt.getPoints(), 0, 25);


        //a condition for you to lose the game if points < 0
        if (duckHunt.getPoints() < 0) {
            gameStates = GameStates.GAMEOVER;
        }

        //check if stage two criteria is met
        if (!stageTwoCheck) {
            if (duckHunt.getPoints() >= 500) {
                level = stageTwoImage;
                stageTwoCheck = true;
            }
        }

        //check if stage three criteria is met
        if (!stageThreeCheck) {
            if (duckHunt.getPoints() >= 1000) {
                level = stageThreeImage;
                stageThreeCheck = true;
            }
        }

        //check if game win criteria is met
        if (!gameWinCheck) {
            if (duckHunt.getPoints() >= 1500) {
                gameStates = GameStates.GAMEWIN;
                gameWinCheck = true;
            }
        }

        //draw birds and apply stage multipliers
        for (Bird b : duckHunt.getBirdList()) {
            if (b.getClass() == Chicken.class) {
                b.draw(g, chickenImage);
            }
            if (b.getClass() == Duck.class) {
                b.draw(g, duckImage);
            }
            if (stageTwoCheck) {
                b.ratioMultiplier(1.5, 1, 1, 2);
            }
            if (stageThreeCheck) {
                b.ratioMultiplier(2, 1, 1, 5);
            }
        }

        Chicken someChicken = new Chicken();
        //clicking a chicken will make lots of chickens appear on screen
        if (swarmCheck) {
            someChicken.swarm(g, chickenImage);
        }

        //allow the swarm to last for 2000 msec
        if (swarmTime >= 2000) {
            swarmTime = 0;
            swarmCheck = false;
        }
    }


    public void draw_pause(Graphics g) {
        //draws the pause menu
        g.drawImage(pauseImage, 0, 0, 1280, 720, null);

        JPanel p = this;

        //creates the resume button
        JButton resume = new JButton("Resume");
        //clicking will bring you back to the game
        resume.addActionListener(e -> {
            gameStates = GameStates.PLAYING;
            this.removeAll();
        });
        p.add(resume);
        resume.setSize(100, 30);
        resume.setLocation(540, 360);
        resume.setVisible(true);

        //creates the save button
        JButton save = new JButton("Save");
        //clicking will save game
        save.addActionListener(e -> {
            try {
                save();
            } catch (Exception io) {
                io.printStackTrace();
            }
        });
        p.add(save);
        save.setSize(100, 30);
        save.setLocation(400, 360);
        save.setVisible(true);

        //creates the total kills button
        JButton kills = new JButton("Total Kills");
        //clicking will bring up a list of all the birds you killed in the game
        JFrame killList = new JFrame("killList");

        kills.addActionListener(e -> JOptionPane.showMessageDialog(killList,
                "All you've killed: " + kill.toString()));
        p.add(kills);
        kills.setSize(100, 30);
        kills.setLocation(800, 360);
        kills.setVisible(true);

        //creates the home button
        JButton home = new JButton("Home");
        //clicking will bring you to the start screen again
        home.addActionListener(e -> {
            gameStates = GameStates.RESET;
            draw_reset(g);
            this.removeAll();
        });
        p.add(home);
        home.setSize(100, 30);
        home.setLocation(675, 360);
        home.setVisible(true);

        //keeps track of your points in the pause menu
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("Total Points " + duckHunt.getPoints(), 0, 25);
    }

    public void draw_reset(Graphics g) {
        //draws the start menu again
        g.drawImage(startImage, 0, 0, 1280, 720, null);

        JPanel p = this;
        //creates the resume button
        JButton resume2 = new JButton("Resume");
        //clicking will bring you back to the game
        resume2.addActionListener(e -> {
            gameStates = GameStates.PLAYING;
            this.removeAll();
        });
        p.add(resume2);
        resume2.setSize(100, 30);
        resume2.setLocation(500, 0);
        resume2.setVisible(true);

        //creates the rules button
        JButton rules2 = new JButton("Rules");
        //clicking will bring up the rules page
        rules2.addActionListener(e -> gameStates = GameStates.RULES);
        p.add(rules2);
        rules2.setSize(100, 30);
        rules2.setLocation(650, 0);
        rules2.setVisible(true);

        //creates the load button
        JButton load2 = new JButton("Load");
        //clicking will load a state of the game
        load2.addActionListener(e -> {
            try {
                load();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        p.add(load2);
        load2.setSize(100, 30);
        load2.setLocation(800, 0);
        load2.setVisible(true);
    }

    public void draw_rules(Graphics g) {
        //draws the rules background
        g.drawImage(rulesImage, 0, 0, 1280, 720, null);
    }

    public void draw_gameover(Graphics g) {
        //draws the gameover page
        g.drawImage(gameOverImage, 0, 0, 1280, 720, null);

        JPanel p = this;

        //draws the play again button
        JButton playAgain = new JButton("Play Again");
        //clicking will start the game in a resetted state
        playAgain.addActionListener(e -> {
            reset();
            gameStates = GameStates.PLAYING;
            this.removeAll();
        });
        p.add(playAgain);
        playAgain.setSize(100, 30);
        playAgain.setLocation(400, 650);
        playAgain.setVisible(true);

        //draws the high score button
        JButton highScore = new JButton("High Scores");
        JFrame topScorex = new JFrame("Top Scores");
        //clicking will show you the top high scores
        highScore.addActionListener(e -> JOptionPane.showMessageDialog(topScorex,
                "THIS IS THE HIGH SCORE: " + currentHighScore));
        p.add(highScore);
        highScore.setSize(100, 30);
        highScore.setLocation(550, 650);
        highScore.setVisible(true);

        //draws the game score button
        JButton gameScore = new JButton("Game Score");
        //clicking will show you how many points you got before you lost
        JFrame yourGameScore = new JFrame("Your Game Score");
        gameScore.addActionListener(e -> JOptionPane.showMessageDialog(yourGameScore,
                "THIS WAS YOUR SCORE: " + duckHunt.getPoints()));
        p.add(gameScore);
        gameScore.setSize(100, 30);
        gameScore.setLocation(700, 650);
        gameScore.setVisible(true);
    }

    public void draw_gamewin(Graphics g) {
        g.drawImage(gameWinImage, 0, 0, 1280, 720, null);

        JPanel p = this;

        //draws the play again button
        JButton playAgain = new JButton("Play Again");
        //clicking will start the game in a resetted state
        playAgain.addActionListener(e -> {
            reset();
            gameStates = GameStates.PLAYING;
            this.removeAll();
        });
        p.add(playAgain);
        playAgain.setSize(100, 30);
        playAgain.setLocation(400, 650);
        playAgain.setVisible(true);

        //draws the high score button
        JButton highScore = new JButton("High Scores");
        JFrame topScore = new JFrame("Top Scores");
        //clicking will show you the top high scores
        highScore.addActionListener(e -> JOptionPane.showMessageDialog(topScore,
                "THIS IS THE HIGH SCORE: " + currentHighScore));
        p.add(highScore);
        highScore.setSize(100, 30);
        highScore.setLocation(550, 650);
        highScore.setVisible(true);

        //draws the game score button
        JButton gameScore = new JButton("Game Score");
        //clicking will show you how many points you got before you lost
        JFrame yourGameScore = new JFrame("Your Game Score");
        gameScore.addActionListener(e -> JOptionPane.showMessageDialog(yourGameScore,
                "THIS WAS YOUR SCORE: " + duckHunt.getPoints()));
        p.add(gameScore);
        gameScore.setSize(100, 30);
        gameScore.setLocation(700, 650);
        gameScore.setVisible(true);
    }
}