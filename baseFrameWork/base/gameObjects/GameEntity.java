package base.gameObjects;

import java.awt.Point;

import base.GameLoop;
import base.physics.Positional;

//This is just so that we have an abstract class that defines all the basic properties of anything that can be in our game

public class GameEntity implements Runnable, Positional {
	
	private Point position;
	
	private Thread objectThread;

	// by default the start method always executes when the entity gets set to active
	private boolean executeStart = true;
	
	private boolean active = true;
	
	public GameEntity(Point position) {
		this.position = position;
	}

	

	// basic logic for having stuff exhibit behaviour when it gets created and get
	// Updated over time in our game
	@Override
	public void run() {
		// Sometimes we dont want an object that gets reactivated to do its start method
		// again
		if (executeStart) {
			Start();
		}
		while (objectThread != null) {
			GameLoop.ExecuteEveryFrame(objectThread);
			Update();
		}
	}
	
	//getter for active
	public boolean isActive() {
		return active;
	}

	//setter for active with a bonus
	// So that we are able to remove any entity from our game and not have it
	// execute its code anymore and reactivate it when we need it
	public void setActive(boolean isActive) {
		if (isActive) {
			objectThread = new Thread();
			active = true;
		} else {
			objectThread = null;
			active = false;
		}
	}
	
	//
	// this is the override for the setter for active that makes you be able to specify if you
	// want to have the start method be executed when the entity is activated the next time
	public void setActive(boolean isActive, boolean doRestart) {
		setActive(isActive);
		executeStart = doRestart;
	}
	
	//happens every frame is entity is active
	public void Update() {};

	//happens once if the entity is activated
	public void Start() {};

	@Override
	public Point GetPosition(Corner corner) {
		// TODO Auto-generated method stub
		return position;
	}

	
}
