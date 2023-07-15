package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Gold;
import base.Resource;
import base.ResourceGenerating;
import base.graphics.*;

public class PolisHouse extends Structure implements ResourceGenerating {
	
	public static Point[] shape = new Point[] {new Point(1,0), new Point(1,1), new Point(0,1)};
	
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
	    cost.put(Gold.class, 10);
	    // Add more key-value pairs as needed
	}
	
	private int goldGenerationAmount = 15;
	
	public PolisHouse(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		
	}

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
