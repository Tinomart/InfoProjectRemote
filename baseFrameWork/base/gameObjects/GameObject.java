package base.gameObjects;

import java.awt.Point;

import base.graphics.Drawable;
import base.graphics.GamePanel;
import base.graphics.Sprite;

public class GameObject extends GameEntity implements Drawable {
	
	private GamePanel.PanelType panelToDrawOn;

	public Sprite sprite;
	
	public GameObject(Point position, Sprite sprite) {
		// We want GameObjects to be drawn on the main panel only since they are not 
		//part of a menu or some special pop up
		super(position);
		this.sprite = sprite;
		panelToDrawOn = GamePanel.PanelType.MainPanel;
		
		// wird in new Sprite executed: new GameObject(new Point(3,5),new Sprite(1,2));
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
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(GetPosition().x).append( ";").append(GetPosition().y).append(",").append(sprite.size.x).append(";").append(sprite.size.y).append(" ");
		return stringBuilder.toString();
	}
}
