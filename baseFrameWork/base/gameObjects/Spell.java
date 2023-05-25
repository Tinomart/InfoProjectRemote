package base.gameObjects;

import base.graphics.TileBased;

public abstract class Spell extends GameObject implements TileBased{
	
	public Tile mainTile;
	public Tile[] tiles;

	public Spell(Tile[] tiles, Tile mainTile) {
		super(mainTile.GetPosition());
		this.mainTile = mainTile;
		this.tiles = tiles;
	}

	public abstract void Cast();
}
