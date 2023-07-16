package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.graphics.*;

public class Temple extends Structure {
	
	public static Point[] shape = new Point[] {new Point(1,0),new Point(0,1),new Point(1,1),};
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
	    cost.put(Gold.class, 10);
	    // Add more key-value pairs as needed
	}
	
	public Temple(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		
	}

	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}

	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new TempleTopLeftTile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile, new TempleTopRightTile(new Point(position.x + 1, position.y), tileGrid),new TempleBottomLeftTile(new Point(position.x , position.y + 1), tileGrid),new TempleBottomRightTile(new Point(position.x + 1, position.y + 1), tileGrid)};
		return tiles;
	}


			
		
		
}