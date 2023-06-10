package base.graphics;

import java.awt.Point;
import java.util.HashMap;

import base.gameObjects.Tile;

	//TODO here we should add all the basic tile logic
	//I want this to basically function a dual purpose
	//of being the map and the tile functionality as two

public class TileGrid {
	
	public HashMap<Point, Tile> tileMap = new HashMap<Point, Tile>();;
	
	public TileGrid(int tileSize, int tileMapWidth, int tileMapHeight) {
		FillAllTilesWithDefault(tileMapWidth, tileMapHeight);
	}

	//creates a new tile at each position of the Map, so that we don't get into errors when trying to interacting somewhere that does not actually has a tile
	public void FillAllTilesWithDefault(int tileMapWidth, int tileMapHeight) {
		for (int i = 0; i < tileMapWidth; i++) {
			for (int k = 0; k < tileMapHeight; k++) {
				new Tile(new Point(i, k), this);
			}
		}
	}
	
	//Hashmaps can carry multiple object with the same key, which we dont want to allow
	//so you can only replace tiles not set tiles.
	public void ReplaceTile(Point tilePosition, Tile tile) {
		tileMap.remove(tilePosition);
		tileMap.put(tilePosition, tile);
		
	}
}
