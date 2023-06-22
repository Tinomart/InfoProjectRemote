package game;

import base.GameLoop;
import base.graphics.*;
import base.GameRunningManager;


public class Main {
	//these are in tile size, not in pixels
	public final static int SCREEN_WIDTH = 48;
	public final static int SCREEN_HEIGHT = 32;
	public final static int MAP_WIDTH = 50;
	public final static int MAP_HEIGHT = 50;
	
	public final static int TILE_SIZE = 16;
	
	public final static String GAME_TITLE = "101100";
	
	public final static int FPS = 60;

	private static GamePanel mainPanel = new Map(MAP_WIDTH, MAP_HEIGHT, 0);
	
	//TODO Adjust Panel sizes if these should be differently sized
	private static Menu mainMenu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, 3);
	private static Menu pauseMenu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, 2);
	private static Menu inGameGUI = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, 1);
	
	//we need to set panels in some contexts, so this has to be public;
	public static GameWindow gameWindow = new GameWindow(GAME_TITLE, GamePanel.AssignAllPanels(mainPanel, mainMenu, pauseMenu, inGameGUI));
	
	public static GameLoop gameLoop = new GameLoop(gameWindow);
	
	public static TileGrid tileGrid = new TileGrid(TILE_SIZE, MAP_WIDTH, MAP_HEIGHT, gameLoop);
	
	private static GameRunningManager gameRunningManager = new GameRunningManager(gameLoop);
	
	public static void main(String[] args) {
		gameRunningManager.StartGame();
	}
}
