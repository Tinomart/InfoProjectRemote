package base.gameObjects;

import base.graphics.Sprite;
import base.graphics.TileBased;

public abstract class Spell extends GameObject implements TileBased {
	// TODO this Class will be used for our Spells (surpise surprise)
	// It is abstract because there is no need for any generic spell if we want one
	// it should be a specific child of this class

	public Tile mainTile;
	public Tile[] tiles;

	public Spell(Tile[] tiles, Tile mainTile,Sprite sprite) {
		super(mainTile.getPosition(), sprite);
		this.mainTile = mainTile;
		this.tiles = tiles;
	}

	public abstract void cast();
}
