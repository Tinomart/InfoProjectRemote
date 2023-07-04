package base.gameObjects;

import java.awt.Point;

import base.GameLoop;
import base.physics.Positional;

//This is just so that we have an abstract class that defines all the basic properties of anything that can be in our game

public class GameEntity implements Positional {
	
	protected Point position;
	
	private boolean active = true;
	
	public GameEntity(Point position) {
		this.position = position;
		start();
	}

	

	// basic logic for having stuff exhibit behaviour when it gets created and get
	// Updated over time in our game
	
	//getter for active
	public boolean isActive() {
		return active;
	}

	//setter for active with a bonus
	// So that we are able to remove any entity from our game and not have it
	// execute its code anymore and reactivate it when we need it
	public void setActive(boolean isActive) {
		if (isActive) {
			active = true;
			if(isActive == active) {
				start();
			}
		} else {
			active = false;
		}
	}
	
	//
	// this is the override for the setter for active that makes you be able to specify if you
	// want to have the start method be executed when the entity is activated the next time
	public void setActive(boolean isActive, boolean doRestart) {
		if (isActive) {
			active = true;
			if(isActive == active) {
				if(doRestart) {
					 start();
					}
			}
		} else {
			active = false;
		}
		
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}



	//happens every frame if entity is active
	public void update() {};

	//happens once if the entity is activated
	public void start() {};

	@Override
	public Point getPosition(Corner corner) {
		
		switch (corner) {
			case topleft: {
				return position;
			}
			case topright: {
				return position;
			}
			case bottomleft: {
				return position;
			}
			case bottomright: {
				return position;
			}
			default:
				return position;
		}
	}
	//the toString will return all information that is needed to reproduce the Gameentity, so that it can be saved later. that is its type and all its arguments
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(getPosition().x).append( ";").append(getPosition().y).append(" ");
		return stringBuilder.toString();
	}

	
}
