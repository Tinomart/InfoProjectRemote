package base.gameObjects;

import java.awt.Point;
import base.graphics.*;

public class Watchtower extends Structure {
	
	public static Point[] shape = new Point[] {new Point(0,1)};
	
	public Watchtower(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		tileGrid.gameLoop.Watchtower = this;
		
	}

	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}

	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new WatchtowerTopTile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile, new WatchtowerBottomTile(new Point(position.x , position.y + 1), tileGrid)};
		return tiles;
	}
}
