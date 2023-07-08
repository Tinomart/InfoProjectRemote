package base.graphics;

import java.awt.Point;

import base.gameObjects.Tile;

//infterface to allow specific code for all TileBased gameObjects
public interface TileBased {
	// all tilebased object should have a main tile an array that stores all of its
	// tiles and should have a tilePosition at all times
	public Tile getMainTile();

	public Tile[] getTiles();

	public Point getTilePosition();
}
