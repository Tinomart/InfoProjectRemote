package base.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.graphics.*;

public class Watchtower extends Structure {
	private int range = 300;
	private int damage = 2;
	private Character target;
	
	public static Point[] shape = new Point[] {new Point(0,1)};
	
	public Watchtower(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		tileGrid.gameLoop.Watchtower = this;
	}
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
	    cost.put(Gold.class, 10);
	    // Add more key-value pairs as needed
	}
	
	@Override
	public void update() {
		for (GameObject gameObject : tileGrid.gameLoop.gameObjects) {
			if(position.distance(gameObject.position) < range) {
				if(gameObject instanceof Character) {
					if(target != null) {
						if(position.distance(target.position) > position.distance(gameObject.position)) {
							target = (Character) gameObject;
						}
					} else {
						target = (Character) gameObject;
					}
				}
			}
		}
		
		if(target != null) {
			target.reduceHealth(damage);
			if(!tileGrid.gameLoop.gameObjects.contains(target)) {
				target = null;
			}
		}
		
		super.update();
	}
	
	
	
	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}

	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new WatchtowerTopTile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile, new WatchtowerBottomTile(new Point(position.x , position.y + 1), tileGrid)};
		return tiles;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if(target != null) {
			g.setColor(new Color(25, 220, 25, 255));
			g.drawLine(position.x + getSprite().size.x/2, position.y + getSprite().size.y/2, target.position.x + target.getSprite().size.x/2, target.position.y + target.getSprite().size.x/2);
		}
	}
}
