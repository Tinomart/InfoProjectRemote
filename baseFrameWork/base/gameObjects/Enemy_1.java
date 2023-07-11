package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.TileGrid;
import base.graphics.SpriteLoader.SpriteType;

public class Enemy_1 extends Character {

	public Enemy_1(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.Enemy_1TileSprite;
	}






	@Override
	protected void attack(int attackDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void move(int moveSpeed) {
		// TODO Auto-generated method stub
		
	}
 
}
