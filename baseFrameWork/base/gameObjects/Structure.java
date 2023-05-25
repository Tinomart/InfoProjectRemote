package base.gameObjects;

import base.graphics.TileBased;
import base.graphics.TileGrid;

public class Structure extends GameObject implements TileBased {

	public Tile mainTile;
	public Tile[] tiles;
	
	public Structure(TileGrid tileMap, Tile[] tiles, Tile mainTile) {
		this.mainTile = mainTile;
		this.tiles = tiles;
	}
		
	@Override
	public Tile GetMainTile() {
		return mainTile;
	}

	@Override
	public Tile[] GetTiles() {
		return tiles;
	}

}
