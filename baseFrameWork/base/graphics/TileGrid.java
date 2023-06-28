package base.graphics;
import base.gameObjects.PureGrassTile;
import java.awt.Point;
import java.util.HashMap;

import base.GameLoop;
import base.gameObjects.Tile;
import game.Main;

	//TODO here we should add all the basic tile logic
	//I want this to basically function a dual purpose
	//of being the map and the tile functionality as two

public class TileGrid {
	
	public HashMap<Point, Tile> tileMap = new HashMap<Point, Tile>();
	public GameLoop gameLoop;
	public int tileSize = Main.TILE_SIZE;
	public int tileMapWidth;
	public int tileMapHeight;
	
	public TileGrid(int tileSize, int tileMapWidth, int tileMapHeight, GameLoop gameLoop) {
		this.gameLoop = gameLoop;
		gameLoop.tileGrid = this;
		this.tileSize = tileSize;
		this.tileMapWidth = tileMapWidth;
		this.tileMapHeight = tileMapHeight;
//		fillAllTilesWithDefault(tileMapWidth, tileMapHeight);
	}

	
	//creates a new tile at each position of the Map, so that we don't get into errors when trying to interacting somewhere that does not actually has a tile
	public void fillAllTilesWithDefault(int tileMapWidth, int tileMapHeight) {
		
//		Sprite testSprite = new Sprite(new Point(tileSize,tileSize));
//		testSprite.imagePath = "res/Test.png";
//		testSprite.loadImage(testSprite.imagePath);
		for (int i = 0; i < tileMapWidth; i++) {
			for (int k = 0; k < tileMapHeight; k++) {
				gameLoop.gameObjects.add(gameLoop.createGameObject(PureGrassTile.class, new Object[] {new Point(i, k), this }));
			}
		}
	}	
//		Sprite testSprite2 = new Sprite(new Point(tileSize,tileSize));
//		testSprite2.imagePath = "res/Kreis.png";
//		testSprite2.loadImage(testSprite.imagePath);
//		for (int i = 0; i <  tileMapWidth; i++) {
//			for (int k = 0; k <  tileMapHeight; k++) {
//				new Tile(new Point(i, k), this,testSprite);
//			}
//		}
//	}
	
	//Hashmaps can carry multiple object with the same key, which we dont want to allow
	//so you can only replace tiles not set tiles.
	public void replaceTile(Point tilePosition, Tile tile) {
		gameLoop.gameObjects.remove(tileMap.get(tilePosition));
		tileMap.remove(tilePosition);
		tileMap.put(tilePosition, tile);
		
	}
}
