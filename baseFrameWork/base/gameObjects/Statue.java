package base.gameObjects;

import java.awt.Point;

import base.graphics.*;

public class Statue extends Structure {
	
	public static Point[] shape = new Point[] {};
	

	
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

