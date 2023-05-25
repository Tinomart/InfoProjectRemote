package base;


import java.awt.Component;
import base.graphics.GamePanel;
import java.awt.Container;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import base.gameObjects.GameObject;

import base.graphics.GamePanel.PanelType;
import base.graphics.GameWindow;
import game.Main;

//What this class does on a basic level is pretty obvious
//But I want this to be the basic way to edit our game as well
//I want all our basic methods to edit the game world be stored here
//so that we can access them later in the Main Class of the game package
public class GameLoop implements Runnable {

	private Thread gameThread;
	public GameWindow window;
	
	public ArrayDeque<GameObject> gameObjects;
	
	// if we need to ever add anything based on a specific frame
	public int fpsCount;

	public GameLoop(GameWindow window) {
		this.window = window;
	}

	public void Start() {
		gameThread = new Thread(this);
		run();
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
			window.getPanels().get(PanelType.MainPanel).requestFocus();
			window.getPanels().get(PanelType.MainPanel).inputManager.ReadInputs();
			
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

	// TODO: Add methods that change the game world on a basic level
	// e.g Create Map, ChangeTile, AddEnemy, AddBuilding, etc.
}
