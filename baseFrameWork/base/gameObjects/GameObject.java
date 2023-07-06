package base.gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.IOException;

import base.graphics.Drawable;
import base.graphics.GamePanel;
import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.physics.Positional.Corner;
import base.graphics.TileBased;
import base.graphics.TileGrid;

import java.io.File;
import java.io.FileInputStream;

import game.Main;

public class GameObject extends GameEntity implements Drawable {
	
	private GamePanel.PanelType panelToDrawOn;

	private Sprite sprite;
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}




	public SpriteType spriteType;
	public int drawLayer = 0;
	
	GamePanel gp;
	
	//we need this array for loading and implementing the map tiles later on (ca. min 15)
//	Tile[] tile;
	
	public GameObject(Point position, Sprite sprite) {
		// We want GameObjects to be drawn on the main panel only since they are not 
		//part of a menu or some special pop up
		super(position);
		this.sprite = sprite;
		panelToDrawOn = GamePanel.PanelType.MainPanel;
		spriteType = SpriteType.CircleSprite;
	
	}






	
	
	
	
	@Override
	public void update() {
		

	}

	@Override
	public void start() {
		

	}

	@Override
	public GamePanel.PanelType getPanelToDrawOn() {
		return panelToDrawOn;
	}
	
	@Override
	public Point getPosition(Corner corner) {
		
		switch (corner) {
			case topleft: {
				return position;
			}
			case topright: {
				return new Point(position.x + sprite.size.x, position.y);
			}
			case bottomleft: {
				return new Point(position.x, position.y + sprite.size.y);
			}
			case bottomright: {
				return new Point(position.x + sprite.size.x, position.y + sprite.size.y);
			}
			default:
				return position;
		}
	}
	
//	@Override
//	public void Draw(GamePanel gamePanel) {
//		if(isActive()) {
//			Graphics2D g2d = (Graphics2D) sprite.getImage().getGraphics();
//			g2d.drawImage(sprite.getImage(), position.x, position.y, sprite.size.x, sprite.size.y, gamePanel);
//	            
//			
//			//TODO Add code that draws the sprite for the GameObject to the Correct GamePanel
//			//obviously only possible after a sprite system is implemented
//				
//			g2d.dispose();
//	        
//		}
//	}
	public void draw(Graphics graphics) {
		if(isActive()) {
			graphics.drawImage(sprite.getImage(), getPosition().x, getPosition().y, sprite.size.x, sprite.size.y, null);
		}
			
	}
	
	public boolean equals(GameObject gameObject) {
		if (this.getPosition().x == gameObject.getPosition().x
				&& this.getPosition().y == gameObject.getPosition().y
				&& this.sprite.size == gameObject.sprite.size
				&& this.getClass() == gameObject.getClass()) {
			return true;
		} else {
			return false;
		}
	}
		
	


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(getPosition().x).append( ";").append(getPosition().y).append(",").append(sprite.size.x).append(";").append(sprite.size.y).append(" ");
		return stringBuilder.toString();
	}

	
}
