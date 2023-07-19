package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.GameLoop;
import base.Faith;
import base.Resource;
import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;


//Lightning spell that simply deals damage to enemies 

public class Lightning extends Spell {
	private int spellDamage = 85;
	private int frameCount = 0;

	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();
	static {
		//initialize cost
		cost.put(Faith.class, 10);
	}

	public Lightning(Point position, GameLoop gameLoop) {
		super(position, new Sprite(new Point(0, 0)), gameLoop);
		spriteType = SpriteType.LightningSprite;

	}

	@Override
	public void update() {
		// Count up a certain amount of frames, if the count is fished, start casting
		// the spell
		frameCount++;
		if (frameCount > 10) {
			cast();
		}
	}

	@Override
	public void cast() {
		//for each gameObject that is colliding with the spell when it is being cast, deal the spelldamage to their HP
		for (GameObject gameObject : gameLoop.gameObjects) {
			if (gameObject instanceof Character) {
				if (gameObject.collisionWith(this)) {
					((Character) gameObject).reduceHealth(spellDamage);
				}
			}
		}
		//once the casting is over destroy this gameObject
		gameLoop.destroyGameObject(this);
	}

}
