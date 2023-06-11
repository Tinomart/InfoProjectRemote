package base;


import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import base.graphics.GamePanel;
import java.util.ArrayDeque;
import java.util.HashMap;

import base.gameObjects.GameObject;

import base.graphics.GamePanel.PanelType;
import base.graphics.GameWindow;
import base.graphics.Menu;
import game.Main;

//What this class does on a basic level is pretty obvious
//But I want this to be the basic way to edit our game as well
//I want all our basic methods to edit the game world be stored here
//so that we can access them later in the Main Class of the game package
public class GameLoop implements Runnable {
	
	private Thread gameThread;
	public GameWindow window;
	
	public HashMap<PanelType, GamePanel> panels;
	private GamePanel mainPanel;
	
	public ArrayDeque<GameObject> gameObjects;
	
	// if we need to ever add anything based on a specific frame
	public int fpsCount;
	
	public int cameraSpeed = 4;
	
	public Point spawnPoint = new Point(400, 400);
	public Point cameraPosition = new Point(0, 0);
	
	public enum Direction{
		up, left, down, right
	}

	public GameLoop(GameWindow window) {
		this.window = window;
		panels = window.getPanels();
		mainPanel = panels.get(PanelType.MainPanel);
		
		SetClosingFunctionality(window);
		SetMenuResizability(window);
		
		//Move our Camera to the Spawnpoint
		for (int i = 0; i < spawnPoint.x/cameraSpeed; i++) {
			MoveCamera(Direction.right);
		}
		for (int j = 0; j < spawnPoint.y/cameraSpeed; j++) {
			MoveCamera(Direction.down);
		}
	}
	// This had to be its own thing, because for some reason the basic resize
	// implementation was failing me, so I had to write my own.
	
	private void SetMenuResizability(GameWindow window) {
		GameLoop gameLoop = this;

		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				for (GamePanel menu : window.getPanels().values()) {
					if (menu instanceof Menu) {
						menu.setSize(new Dimension(window.getSize()));
						menu.repaint();
						menu.revalidate();
						
						//this fixes a bug where the player was able to have a small window, move to the edge of the screen and resize the window to the 
						int outOfBoundsWidth = gameLoop.cameraPosition.x + window.getBounds().width - Main.MAP_WIDTH * Main.TILE_SIZE;
						int outOfBoundsHeight = gameLoop.cameraPosition.y + window.getBounds().height - Main.MAP_HEIGHT * Main.TILE_SIZE;
						if(outOfBoundsWidth > 0 || outOfBoundsHeight > 0) {
							for (int i = 0; i < (outOfBoundsWidth/Main.gameLoop.cameraSpeed) + 1; i++) {
								gameLoop.MoveCamera(Direction.left);
							}
							for (int i = 0; i < (outOfBoundsHeight/Main.gameLoop.cameraSpeed) + 1; i++) {
								gameLoop.MoveCamera(Direction.up);
							}
						}
					}
				}
			}
		});
	}
	
	// stops the gameloop and closes the program if the user exits the window

	private void SetClosingFunctionality(Window window) {
		GameLoop gameLoop = this;
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gameLoop.Stop();
				System.exit(0);
			}
		});

	}
	public void Start() {
		//start the thread, then start the main loop
		gameThread = new Thread(this);
		run();
	}
	
	public void Stop() {
		//stop the thread, thus stopping the main loop
		gameThread = null;
	}

	@Override
	public void run() {
		// main thread(happens very frame)
		while (gameThread != null) {
			ExecuteEveryFrame(gameThread);
			
			
			//this is part of the fps count, that I am still unsure we need
//			fpsCount += 1;
//			if(fpsCount >= 60) {
//				fpsCount = 1;
//			}

			//TODO this needs to be readded once the Draw() function in GameObject is properly 
			//implemented
			// DrawGameObjects();

			//The Main Menu has its inputs read;
			//requestFocus is so that the JFrame and JPanel doesnt all of the
			//sudden shift focus and stops listening to our MainPanel 
			//is is possible that this is inefficient and has to be reallocated
			//later
			
			panels.get(PanelType.InGameGUI).requestFocus();
			panels.get(PanelType.InGameGUI).inputManager.ReadInputs();
			
			//debug code that displays the layer of every Panel that is currently a component of the Window
//			Container container = window.getContentPane();
//			
//			for (Component component : container.getComponents()) {
//				if(component instanceof JPanel) {
//					GamePanel panel = (GamePanel)component;
//					System.out.println(panel.drawLayer);
//				}
//			}
			
			
			
			
			
			
		}

	}

	private void DrawGameObjects() {

		for (GameObject gameObject : gameObjects) {
			gameObject.Draw();
		}
	}

	public static void ExecuteEveryFrame(Thread thread) {

		// The time frame of one frame in nanoseconds
		// that has to pass until the loop can run again
		double loopSleepInterval = 1000000000 / Main.FPS;
		double nextLoopTime = /* This gives you the current system time in nanoseconds */System.nanoTime()
				+ loopSleepInterval;

		// Sees how long there still is till the time of the next time the run method
		// loops and turns the thread inactive until that time, to make the run loop
		// happen 60 times a second
		// code taken from this Youtube video:
		// https://www.youtube.com/watch?v=VpH33Uw-_0E&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=2&ab_channel=RyiSnow
		// At 26:36
		try {
			double timeTillNextLoop = (nextLoopTime - System.nanoTime());
			timeTillNextLoop = timeTillNextLoop / 1000000;
			Thread.sleep((long) timeTillNextLoop);

			// just for safety to make sure the thread doesnt sleep indefinitely if our pc
			// wasn't able to render the game at 60 FPS
			if (timeTillNextLoop < 0) {
				timeTillNextLoop = 0;
			}

			timeTillNextLoop = timeTillNextLoop + loopSleepInterval;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void MoveCamera(Direction direction) {
		mainPanel = panels.get(PanelType.MainPanel);
		
		//if the camera is inside the Maps bounds move it in a direction with the cameraSpeed
		switch (direction) {
			case up: 
				if(cameraPosition.y - cameraSpeed > 0) {
					cameraPosition.y -= cameraSpeed;
				}
				break;
			case left: 
				if(cameraPosition.x - cameraSpeed > 0) {
					cameraPosition.x -= cameraSpeed;
				}
				break;
			case down: 
				if(cameraPosition.y + cameraSpeed + Main.gameWindow.getBounds().height < Main.MAP_HEIGHT*Main.TILE_SIZE) {
					cameraPosition.y += cameraSpeed;
				}
				break;
			case right: 
				if(cameraPosition.x + cameraSpeed + Main.gameWindow.getBounds().width < Main.MAP_WIDTH*Main.TILE_SIZE) {
					cameraPosition.x += cameraSpeed;
				}
				
				break;
		}
		//set the negative location because we are moving the panel beneath the camera, not the actual camera itself, which
		//means we need to reverse the cameraPosition for the panels position
		
		mainPanel.setLocation(-cameraPosition.x, -cameraPosition.y);
		mainPanel.revalidate();
		
	}

	

	// TODO: Add methods that change the game world on a basic level
	// e.g Create Map, ChangeTile, AddEnemy, AddBuilding, etc.
}
