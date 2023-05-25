package base.gameObjects;

import base.graphics.TileBased;
import base.graphics.TileGrid;
import game.Main;

public abstract class Structure extends GameObject implements TileBased {

	public Tile mainTile;
	public Tile[] tiles;
	
	public Structure(Tile[] tiles, Tile mainTile) {
		super(mainTile.GetPosition());
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
