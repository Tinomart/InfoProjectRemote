package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.TileBased;

public abstract class Spell extends GameObject{
	// TODO this Class will be used for our Spells (surpise surprise)
	// It is abstract because there is no need for any generic spell if we want one
	// it should be a specific child of this class



	public Spell(Point position, Sprite sprite) {
		super(position, sprite);
	}

	public abstract void cast();
}
