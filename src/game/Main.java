package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import base.GameLoop;
import base.graphics.*;
import base.GameRunningManager;
import base.Gold;
import base.Level;
import base.Resource;
import base.gameObjects.Character;
import base.gameObjects.Enemy_1;


public class Main {
	//these are in tile size, not in pixels
	public final static int SCREEN_WIDTH = 48;
	public final static int SCREEN_HEIGHT = 32;
	public final static int MAP_WIDTH = 100;
	public final static int MAP_HEIGHT = 100;
	
	public static final int TILE_SIZE = 32;
	
	public final static String GAME_TITLE = "101100";							
	
	public final static int FPS = 60;

	private static GamePanel mainPanel = new Map(MAP_WIDTH, MAP_HEIGHT, 0);
	
	//TODO Adjust Panel sizes if these should be differently sized
	private static Menu mainMenu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, 3);
	private static Menu pauseMenu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, 2);
	private static Menu inGameGUI = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, 1);
	
	//we need to set panels in some contexts, so this has to be public;
	public static GameWindow gameWindow = new GameWindow(GAME_TITLE, GamePanel.assignAllPanels(mainPanel, mainMenu, pauseMenu, inGameGUI));
	
	public static GameLoop gameLoop = new GameLoop(gameWindow);
	
	public static TileGrid tileGrid = new TileGrid(TILE_SIZE, MAP_WIDTH, MAP_HEIGHT, gameLoop);
	
	private static GameRunningManager gameRunningManager = new GameRunningManager(gameLoop);
	
	public static void main(String[] args) {
		
		//here we are defining custom waves, right now there are waves 1-7 and adding them to our waves.
		//it is just a few for loops and manually added enemies
		ArrayList<Character> level1Enemies = new ArrayList<Character>();
		level1Enemies.add(new Enemy_1(new Point(50, 400)));
		level1Enemies.add(new Enemy_1(new Point(400, 250)));
		level1Enemies.add(new Enemy_1(new Point(100, 600)));
		HashMap<Class<? extends Resource>, Integer> level1Reward = new HashMap<Class<? extends Resource>, Integer>();
		level1Reward.put(Gold.class, 10);
		Level level1 = new Level(level1Enemies, level1Reward, gameLoop);

		ArrayList<Character> level2Enemies = new ArrayList<Character>();
		level2Enemies.add(new Enemy_1(new Point(50, 50)));
		level2Enemies.add(new Enemy_1(new Point(50, 100)));
		level2Enemies.add(new Enemy_1(new Point(100, 50)));
		level2Enemies.add(new Enemy_1(new Point(100, 100)));
		level2Enemies.add(new Enemy_1(new Point(150, 50)));
		level2Enemies.add(new Enemy_1(new Point(150, 100)));
		HashMap<Class<? extends Resource>, Integer> level2Reward = new HashMap<Class<? extends Resource>, Integer>();
		level2Reward.put(Gold.class, 10);
		Level level2 = new Level(level2Enemies, level2Reward, gameLoop);
		
		ArrayList<Character> level3Enemies = new ArrayList<Character>();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				level3Enemies.add(new Enemy_1(new Point(50*i + 400, 50*j+200)));
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				level3Enemies.add(new Enemy_1(new Point(50*i + 1400, 50*j+200)));
			}
		}
		HashMap<Class<? extends Resource>, Integer> level3Reward = new HashMap<Class<? extends Resource>, Integer>();
		level3Reward.put(Gold.class, 15);
		Level level3 = new Level(level3Enemies, level3Reward, gameLoop);
		
		
		ArrayList<Character> level4Enemies = new ArrayList<Character>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				level4Enemies.add(new Enemy_1(new Point(50*i +800, 50*j + 1400)));
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				level4Enemies.add(new Enemy_1(new Point(50*i + 1600, 50*j + 1400)));
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				level4Enemies.add(new Enemy_1(new Point(50*i + 2400, 50*j + 1400)));
			}
		}
		HashMap<Class<? extends Resource>, Integer> level4Reward = new HashMap<Class<? extends Resource>, Integer>();
		level4Reward.put(Gold.class, 15);
		Level level4 = new Level(level4Enemies, level4Reward, gameLoop);
		
		ArrayList<Character> level5Enemies = new ArrayList<Character>();
		for (int k = 100; k < 2600; k+=500) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					level5Enemies.add(new Enemy_1(new Point(50*i + k, 50*j + 1400)));
				}
			}
		}
		
		HashMap<Class<? extends Resource>, Integer> level5Reward = new HashMap<Class<? extends Resource>, Integer>();
		level5Reward.put(Gold.class, 20);
		Level level5 = new Level(level5Enemies, level5Reward, gameLoop);

		ArrayList<Character> level6Enemies = new ArrayList<Character>();
		for (int k = 600; k < 2600; k+=500) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					level6Enemies.add(new Enemy_1(new Point(50*i + k, 50*j + 1600)));
				}
			}
		}
		for (int k = 100; k < 1600; k+=500) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					level6Enemies.add(new Enemy_1(new Point(50*i + 1400, 50*j + k)));
				}
			}
		}
		for (int k = 100; k < 1600; k+=500) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					level6Enemies.add(new Enemy_1(new Point(50*i + 2400, 50*j + k)));
				}
			}
		}
		HashMap<Class<? extends Resource>, Integer> level6Reward = new HashMap<Class<? extends Resource>, Integer>();
		level6Reward.put(Gold.class, 25);
		Level level6 = new Level(level6Enemies, level6Reward, gameLoop);
		
		ArrayList<Character> level7Enemies = new ArrayList<Character>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 15; j++) {
				level7Enemies.add(new Enemy_1(new Point(50*i + 2400, 50*j + 100)));
			}
		}
		HashMap<Class<? extends Resource>, Integer> level7Reward = new HashMap<Class<? extends Resource>, Integer>();
		level7Reward.put(Gold.class, 30);
		Level level7 = new Level(level7Enemies, level7Reward, gameLoop);
		
		gameLoop.waves.add(level1);
		gameLoop.waves.add(level2);
		gameLoop.waves.add(level3);
		gameLoop.waves.add(level4);
		gameLoop.waves.add(level5);
		gameLoop.waves.add(level6);
		gameLoop.waves.add(level7);
		gameRunningManager.startGame();
	}
}
