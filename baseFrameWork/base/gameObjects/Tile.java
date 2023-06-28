package base.gameObjects;

import base.graphics.GamePanel;
import base.graphics.GamePanel.PanelType;

import base.graphics.RedSquareComponent;
import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileBased;
import base.graphics.TileGrid;
import game.Main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Tile extends GameObject implements TileBased {
	public Point tilePosition;
	
	public TileGrid tileGrid;
	
	public GamePanel mainPanel;
	
	public HashMap<Point, Tile> tileMap;
	
	
	public Sprite sprite;
	//There still needs to be a sprite field and have the sprite field be initialized in the constructor with an argument
	//The redSquare is the very, very basic implementation of the hovering feature and could be replaced later
	//if we find it unfitting
	
	public RedSquareComponent redsquare;

	
	
	public Tile(Point tilePosition, TileGrid tileGrid, Sprite sprite) {
		super(new Point(tilePosition.x * tileGrid.tileSize, tilePosition.y * tileGrid.tileSize), sprite);
		this.tileGrid = tileGrid;
		this.tilePosition = tilePosition;
		tileGrid.replaceTile(tilePosition, this);
		mainPanel = tileGrid.gameLoop.window.getPanels().get(PanelType.MainPanel);
		redsquare = new RedSquareComponent(tilePosition.x, tilePosition.y, tileGrid.tileSize);
		spriteType = SpriteType.TestSprite;
		this.sprite = sprite;
	}
	
	
//public void getTileImage() {
//		
//		try {
//			
//			//depends on how many blockarten we have, just copy and paste the follow lines and change the file
//			
//			sprite.setImage(ImageIO.read(getClass().getResourceAsStream("res/player/Test.png")));
////			tile[0] = new Tile(position, Main.tileGrid, sprite);
////			tile[0].sprite.setImage(ImageIO.read(getClass().getResourceAsStream("res/player/Test.png")));
//			
////			File file = new File("res/player/Test.png");
////            FileInputStream fis = new FileInputStream(file);
////            tile[0] = new Tile(position, null, sprite);
////            tile[0].image = ImageIO.read(fis);
//			
//			
//		} catch(IOException e){
//			
//			e.printStackTrace();
//			
//		}
//	}

		
	//when hovered display the redsquare on the main panel
	public void onHover() {
		mainPanel.add(redsquare);
		mainPanel.revalidate();
		tileGrid.gameLoop.window.revalidate();
		tileGrid.gameLoop.window.repaint();
		
	}
	
	public Point getTilePosition() {
		return tilePosition;
	}
	
	@Override
	public Tile getMainTile() {
		return this;
	}



	@Override
	public Tile[] getTiles() {
		return new Tile[]{this};
	}
	
	//Tile has one more argument than GameEntity, so it needs to have a new toString that also returns the value of its bonus argument, which is tileGrid
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(GetPosition().x/tileGrid.tileSize).append( ";").append(GetPosition().y/tileGrid.tileSize).append(",").append("TileGrid").append(",").append(sprite.size.x).append(";").append(sprite.size.y).append(" ");
		return stringBuilder.toString();
	}
	
}
