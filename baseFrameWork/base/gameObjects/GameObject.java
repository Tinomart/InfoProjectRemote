package base.gameObjects;

import java.awt.Point;

import base.graphics.Drawable;
import base.graphics.GamePanel;

public class GameObject extends GameEntity implements Drawable {
	
	private GamePanel.PanelType panelToDrawOn;

	public GameObject() {
		// We want GameObjects to be drawn on the main panel only since they are not 
		//part of a menu or some special pop up
		panelToDrawOn = GamePanel.PanelType.MainPanel;
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Start() {
		// TODO Auto-generated method stub

	}

	@Override
	public GamePanel.PanelType getPanelToDrawOn() {
		return panelToDrawOn;
	}

	@Override
	public Point GetPosition(Corner corner) {
		
		//TODO: The return in the statement will be based off the sprite's
		//width and height, so until the proper sprite system is implemented
		//this will stay this way
		switch (corner) {
			case topleft: {
				return null;
			}
			case topright: {
				return null;
			}
			case bottomleft: {
				return null;
			}
			case bottomright: {
				return null;
			}
			default:
				return null;
		}
	}

	@Override
	public void Draw() {
		if(isActive()) {
			//TODO Add code that draws the sprite for the GameObject to the Correct GamePanel
			//obviously only possible after a sprite system is implemented
		}
	}

}
