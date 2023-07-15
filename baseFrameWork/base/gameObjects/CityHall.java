package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.graphics.*;

public class CityHall extends Structure {
	
	public static Point[] shape = new Point[] {new Point(-1,0)};
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
	    cost.put(Gold.class, 10);
	    // Add more key-value pairs as needed
	}
	
	public CityHall(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		tileGrid.gameLoop.cityHall = this;
		
	}

	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}

	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new BeachTile_Bottom(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile, new BeachTile_Left(new Point(position.x - 1, position.y), tileGrid)};
		return tiles;
	}
}
