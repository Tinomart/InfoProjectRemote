package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;

public abstract class Character extends GameObject{
	//TODO This class is meant for living, moving beings so soldiers and the like
	//it really depends on what we want to do with them and they still need 
	//sprites working and such before we can resonably implement them
	
	public Character(Point position, Sprite sprite) {
		super(position, sprite);
	}
	
}
