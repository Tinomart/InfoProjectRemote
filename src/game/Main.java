package game;

import base.GameLoop;
import base.graphics.GamePanel;
import base.graphics.GameWindow;
import base.GameRunningManager;

public class Main {
	//these are in tile size, not in pixels
	public final static int SCREEN_WIDTH = 48;
	public final static int SCREEN_HEIGHT = 32;
	
	public final static String GAME_TITLE = "101100";
	
	public final static int FPS = 60;

	private static GamePanel mainPanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGHT);

	private static GameWindow gameWindow = new GameWindow(GAME_TITLE, GamePanel.AssignAllPanels(mainPanel));
	
	private static GameLoop gameLoop = new GameLoop(gameWindow);
	
	private static GameRunningManager gameRunningManager = new GameRunningManager(gameLoop);
	
	public static void main(String[] args) {
		gameRunningManager.StartGame();
	}
}
