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
import base.graphics.TileGrid;

import java.io.File;
import java.io.FileInputStream;

import game.Main;

public class GameObject extends GameEntity implements Drawable {
	
	private GamePanel.PanelType panelToDrawOn;

	public Sprite sprite;
	public SpriteType spriteType;
	
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
			graphics.drawImage(sprite.getImage(),position.x, position.y, sprite.size.x, sprite.size.y, null);			
		}
			
	}
	
//	public void draw(Graphics graphics) {
//		if(isActive()) {
//			
//			
//			int col = 0;
//			int row = 0;
//			int x = 0;
//			int y = 0;
//			
//			
//			while(col < Main.MAP_HEIGHT && row < Main.MAP_WIDTH) {
//				
//				graphics.drawImage(tile[0].sprite.getImage(), x, y, Main.TILE_SIZE, Main.TILE_SIZE, null);
//				
//				col ++;
//				x += sprite.size.x;
//				
//				if(col == Main.MAP_HEIGHT) {
//					col = 0;
//					x = 0;
//					row++;
//					y += sprite.size.x;
//				}
//			}
//			
//		}
		
	


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(GetPosition().x).append( ";").append(GetPosition().y).append(",").append(sprite.size.x).append(";").append(sprite.size.y).append(" ");
		return stringBuilder.toString();
	}

	
}
