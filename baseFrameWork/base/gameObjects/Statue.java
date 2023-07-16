package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.graphics.*;

public class Statue extends Structure {
	
	public static Point[] shape = new Point[] {};
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
	    cost.put(Gold.class, 10);
	    // Add more key-value pairs as needed
	}

	
	public Statue(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		
	}

	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}

	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new StatueTile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile};
		return tiles;
	}


			
		
		
}

