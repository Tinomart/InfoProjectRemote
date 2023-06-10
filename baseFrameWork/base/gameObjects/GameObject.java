package base.gameObjects;

import java.awt.Point;

import base.graphics.Drawable;
import base.graphics.GamePanel;

public class GameObject extends GameEntity implements Drawable {
	
	private GamePanel.PanelType panelToDrawOn;

	public GameObject(Point position) {
		// We want GameObjects to be drawn on the main panel only since they are not 
		//part of a menu or some special pop up
		super(position);
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
	public void Draw() {
		if(isActive()) {
			//TODO Add code that draws the sprite for the GameObject to the Correct GamePanel
			//obviously only possible after a sprite system is implemented
		}
	}

}
