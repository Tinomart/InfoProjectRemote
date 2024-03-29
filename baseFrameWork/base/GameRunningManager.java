package base;

import base.graphics.*;
import base.graphics.GamePanel.PanelType;

public class GameRunningManager {
	
	private GameWindow gameWindow;
	
	private GameLoop gameLoop;
	
	public GameRunningManager(GameLoop gameLoop){
		this.gameLoop = gameLoop;
		gameWindow = gameLoop.window;
	}

	public void startGame() {
		gameWindow.setPanel(PanelType.MainMenu);
		gameWindow.openWindow();
		gameLoop.start();
	}

	
}
