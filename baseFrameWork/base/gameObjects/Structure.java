package base.gameObjects;

import base.graphics.Sprite;
import base.graphics.TileBased;

public abstract class Structure extends GameObject implements TileBased {

	// Structures have a mainTile that is used when placing them it is the tile that
	// will be on the cursor when trying to place a structure. The tiles are just an
	// array of all tiles that are a part of a structure since a structure since a
	// structure is made up of tiles
	public Tile mainTile;
	public Tile[] tiles;

	// The main tile is what determines the Position so it is determined as the
	// positional argument of the superclass of GameObject
	public Structure(Tile[] tiles, Tile mainTile, Sprite sprite) {
		super(mainTile.GetPosition(), sprite);
		this.mainTile = mainTile;
		this.tiles = tiles;
	}

	@Override
	public Tile getMainTile() {
		return mainTile;
	}

	@Override
	public Tile[] getTiles() {
		return tiles;
	}

}
