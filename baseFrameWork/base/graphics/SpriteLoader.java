package base.graphics;

import java.awt.Point;
import java.util.*;

import game.Main;


public class SpriteLoader {
	public enum SpriteType{
		TestSprite,
		CircleSprite
	}
	
	public HashMap<SpriteType, Sprite> sprites = new HashMap<SpriteType, Sprite>();
	
	public SpriteLoader() {
		loadSprites();
	}

	private void loadSprites() {
		Sprite testSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		testSprite.loadImage("res/Test.png");
		sprites.put(SpriteType.TestSprite, testSprite);
		
		Sprite circleSprite = new Sprite(new Point(50, 50));
		circleSprite.loadImage("res/Kreis.png");
		sprites.put(SpriteType.CircleSprite, circleSprite);
		
		
	}
}
