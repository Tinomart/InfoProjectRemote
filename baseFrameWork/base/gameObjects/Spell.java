package base.gameObjects;

import base.graphics.TileBased;

public abstract class Spell extends GameObject implements TileBased {
	// TODO this Class will be used for our Spells (surpise surprise)
	// It is abstract because there is no need for any generic spell if we want one
	// it should be a specific child of this class

	public Tile mainTile;
	public Tile[] tiles;

	public Spell(Tile[] tiles, Tile mainTile) {
		super(mainTile.GetPosition());
		this.mainTile = mainTile;
		this.tiles = tiles;
	}

	public abstract void Cast();
}
