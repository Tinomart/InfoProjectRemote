package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;

public class Lightning extends Spell {

	public Lightning(Point position, Sprite sprite) {
		super(position, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.LightningSprite;
		
	}

	@Override
	public void cast() {
	
		
	}

}
