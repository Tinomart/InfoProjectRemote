package base.graphics;

import java.awt.Point;
import java.util.HashMap;

import base.gameObjects.Tile;
import base.graphics.GamePanel.PanelType;

	//TODO here we should add all the basic tile logic
	//I want this to basically function a dual purpose
	//of being the map and the tile functionality as two

public class TileGrid {
	
	public HashMap<Point, Tile> tileMap = new HashMap<Point, Tile>();;
	
	public TileGrid(int tileSize, int tileMapWidth, int tileMapHeight) {
		//FillAllTilesWithDefault(tileMapWidth, tileMapHeight);
		
	}

	public void FillAllTilesWithDefault(int tileMapWidth, int tileMapHeight) {
		for (int i = 0; i < tileMapWidth; i++) {
			for (int k = 0; k < tileMapHeight; k++) {
				new Tile(new Point(i, k));
			}
		}
	}
	
	public void ReplaceTile(Point tilePosition, Tile tile) {
		tileMap.remove(tilePosition);
		tileMap.put(tilePosition, tile);
		
	}
}
