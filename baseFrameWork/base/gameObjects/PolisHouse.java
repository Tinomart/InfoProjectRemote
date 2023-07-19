package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.ResourceGenerating;
import base.graphics.*;


//PolisHouse is a Gameobject that gives you money at the end of each wave.
//This Gameobject consists out of 4 Tiles Called "PolisHouse_column_row" there is 0 and 1 row
//and 0 and 1 column. 

public class PolisHouse extends Structure implements ResourceGenerating {
	
	public static Point[] shape = new Point[] {new Point(1,0), new Point(1,1), new Point(0,1)};
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
		//initilaizing resource cost
	    cost.put(Gold.class, 20);
	}
	
	private int goldGenerationAmount = 10;
	
	public PolisHouse(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		maxHealth = 50;
		health = maxHealth;
	}

	//common code needed to make sure all tiles are correctly assigned in the constructor,
	//this has to be done because we want all tiles to be saved within the Structure definition itself
	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}
	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new House_0_0_Tile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile, new House_1_0_Tile(new Point(position.x + 1, position.y), tileGrid), new House_1_1_Tile(new Point(position.x + 1, position.y + 1), tileGrid), new House_0_1_Tile(new Point(position.x, position.y + 1), tileGrid)};
		return tiles;
	}

	@Override
	public void generateResources(Resource[] resources) {
		for (Resource resource : resources) {
			if(resource instanceof Gold) {
				resource.changeAmount(goldGenerationAmount);
			}
		}
		
	}
}
