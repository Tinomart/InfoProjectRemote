package base.gameObjects;

import java.awt.Point;
import base.graphics.*;

public class CityHall extends Structure {
	
	public CityHall(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
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
