package base.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.graphics.*;

//The Watchtower is a Gameobject that attacks enemies in its range and is your primary defense structure 
//This Gameobject consists out of 2 Tiles Called "Temple+Top/Bottom" 

public class Watchtower extends Structure {
	private int range = 300;
	private int damage = 1;
	private Character target;
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
		// initilaizing resource cost
		cost.put(Gold.class, 15);
	}

	public static Point[] shape = new Point[] { new Point(0, 1) };

	public Watchtower(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
	}

	

	@Override
	public void update() {
		// if see if any enemies are in range and check if they are closer than the
		// current target, if so set them to be the newest target
		for (GameObject gameObject : tileGrid.gameLoop.gameObjects) {
			if (position.distance(gameObject.position) < range) {
				if (gameObject instanceof Character) {
					if (target != null) {
						if (position.distance(target.position) > position.distance(gameObject.position)) {
							target = (Character) gameObject;
						}
					} else {
						// if there is no target yet, simply assign the object in range to be the target
						target = (Character) gameObject;
					}
				}
			}
		}

		// if there are any targets, deal damage to them
		if (target != null) {
			target.reduceHealth(damage);
			// remove the target if it has been destroyed and is not in our gameObjects
			// anymore
			if (!tileGrid.gameLoop.gameObjects.contains(target)) {
				target = null;
			}
		}

		super.update();
	}

	//common code needed to make sure all tiles are correctly assigned in the constructor,
	//this has to be done because we want all tiles to be saved within the Structure definition itself
	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}
	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new WatchtowerTopTile(position, tileGrid);
		Tile[] tiles = new Tile[] { mainTile,
				new WatchtowerBottomTile(new Point(position.x, position.y + 1), tileGrid) };
		return tiles;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		//special draw method that draws a targeting line, if the watchtower has a target
		if (target != null) {
			g.setColor(new Color(25, 220, 25, 255));
			g.drawLine(position.x + getSprite().size.x / 2, position.y + getSprite().size.y / 2,
					target.position.x + target.getSprite().size.x / 2,
					target.position.y + target.getSprite().size.x / 2);
		}
	}
}
