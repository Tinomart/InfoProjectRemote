package base.graphics;

import java.awt.Point;
import java.util.HashMap;

import base.gameObjects.Tile;

public interface TileBased {
	//TODO this will be a short interface that should
	//store a few base rules for tile based GameObjects
	//So basically I want a special tile position variable
	//and potentially a sprite restriction or sprite cutter into
	// tile shape
	public Tile GetMainTile();
	public Tile[] GetTiles();
}
