package base.gameObjects;

import java.awt.Point;

import base.GameLoop;
import base.graphics.Sprite;
import base.graphics.TileBased;

//this Class will be used for our Spells (surpise surprise)
// It is abstract because there is no need for any generic spell if we want one
// it should be a specific child of this class
public abstract class Spell extends GameObject{
	
	
	protected GameLoop gameLoop;


	public Spell(Point position, Sprite sprite, GameLoop gameLoop) {
		super(position, sprite);
		this.gameLoop = gameLoop;
	}

	public abstract void cast();
}
