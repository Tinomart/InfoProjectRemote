package base.graphics;

import base.gameObjects.PureGrassTile;
import base.gameObjects.StructureTile;

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

	// creates a new tile at each position of the Map, so that we don't get into
	// errors when trying to interacting somewhere that does not actually has a tile
	public void fillAllTilesWithDefault(int tileMapWidth, int tileMapHeight) {

//		Sprite testSprite = new Sprite(new Point(tileSize,tileSize));
//		testSprite.imagePath = "res/Test.png";
//		testSprite.loadImage(testSprite.imagePath);
		for (int i = 0; i < tileMapWidth; i++) {
			for (int k = 0; k < tileMapHeight; k++) {
				gameLoop.createGameObject(PureGrassTile.class, new Object[] { new Point(i, k), this });
			}
		}
	}

	// method, which will be invoked in the constructor of each tile. We want this
	// to remove all traces of the previous tile and replace it with the new one
	public void replaceTile(Point tilePosition, Tile tile) {
		Tile tileToGetReplaced = tileMap.get(tilePosition);
		if (tileToGetReplaced != null) {
			// if a tile already exists, where the current Tile is, remove it from our
			// tileMap and alse gameObejct, to avoid old tiles still getting drawn and
			// updated, despite getting replaced
			gameLoop.gameObjects.remove(tileToGetReplaced);
			tileMap.remove(tilePosition);
		}
		// Put the new tile in the tileMap. Dont put it in gameObjects, as we save
		// tilebased objects that consist of multiple tiles, which already get saved and
		// draw all its tiles and could cause duplicate issues
		tileMap.put(tilePosition, tile);
		
		// to fix a bug, where placing structures, where there a previous structure had
		// been deleted, would be impossible it basically ensures to reset the structure
		// field of the new tile, which could prevent placement if not null
		tileMap.get(tilePosition).structure = null;

	}
}
