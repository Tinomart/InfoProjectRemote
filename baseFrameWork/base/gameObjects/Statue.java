package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Faith;
import base.Gold;
import base.Resource;
import base.ResourceGenerating;
import base.graphics.*;

public class Statue extends Structure implements ResourceGenerating{
	
	public static Point[] shape = new Point[] {};
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
	    cost.put(Gold.class, 5);
	}
	

	private int faithGenerationAmount = 5;

	
	public Statue(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		
	}

	//common code needed to make sure all tiles are correctly assigned in the constructor,
	//this has to be done because we want all tiles to be saved within the Structure definition itself
	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}
	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new StatueTile(position, tileGrid);
	    Tile[] tiles = new Tile[]{mainTile};
		return tiles;
	}
	
	// override of resourceGenerating that adds the correct amount to the resource
	// argument
	@Override
	public void generateResources(Resource[] resources) {
		for (Resource resource : resources) {
			if(resource instanceof Faith) {
				resource.changeAmount(faithGenerationAmount);
			}
		}
		
	}

			
		
		
}

