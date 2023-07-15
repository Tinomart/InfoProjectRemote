package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.graphics.*;
import base.gameObjects.*;

public class CityHall extends Structure {
	
	public static Point[] shape = new Point[] {new Point(-1,0),new Point(1,0),new Point(-1,1),new Point(0,1),new Point(1,1),new Point(-1,2),new Point(0,2),new Point(1,2),};
	
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
		Tile mainTile = new CityHallTopMidTile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile, new CityHallTopLeftTile(new Point(position.x - 1, position.y), tileGrid),new CityHallTopRightTile(new Point(position.x + 1, position.y), tileGrid),new CityHallMidLeftTile(new Point(position.x - 1, position.y + 1), tileGrid),new CityHallMidMidTile(new Point(position.x , position.y + 1), tileGrid),new CityHallMidRightTile(new Point(position.x + 1, position.y + 1), tileGrid),new CityHallBottomLeftTile(new Point(position.x - 1, position.y + 2), tileGrid),new CityHallBottomMidTile(new Point(position.x , position.y + 2), tileGrid),new CityHallBottomRightTile(new Point(position.x +  1, position.y + 2), tileGrid),};
		return tiles;
	}
}
