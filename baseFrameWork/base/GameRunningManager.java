package base;

import base.graphics.*;

public class GameRunningManager {
	
	private GameWindow gameWindow;
	
	private GameLoop gameLoop;
	
	public GameRunningManager(GameLoop gameLoop){
		this.gameLoop = gameLoop;
		gameWindow = gameLoop.window;
	}

	

	public void StartGame() {
		gameWindow.SetPanel(GamePanel.PanelType.MainPanel);
		gameWindow.OpenWindow();
		gameLoop.Start();
	}

	
}
