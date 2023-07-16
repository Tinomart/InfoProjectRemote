package base.gameObjects;

import java.awt.Point;
import java.util.HashMap;

import base.Faith;
import base.Gold;
import base.Resource;
import base.ResourceGenerating;
import base.graphics.*;

public class Temple extends Structure implements ResourceGenerating {

	public static Point[] shape = new Point[] { new Point(1, 0), new Point(0, 1), new Point(1, 1), };

	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();

	static {
		// initializing resource cost
		cost.put(Gold.class, 30);
	}

	private int goldGenerationAmount = 15;
	private int faithGenerationAmount = 5;

	public Temple(Point position, TileGrid tileGrid) {
		super(initializeTiles(position, tileGrid), initializeMainTile(position, tileGrid), tileGrid);
		tileGrid.gameLoop.setTempleExist(true);
		
		// update the spellbutton, to check if we have a temple yet and thus can cast a
		// spell
		GUI.updateSpellButtonColor();

	}

	// common code needed to make sure all tiles are correctly assigned in the
	// constructor,
	// this has to be done because we want all tiles to be saved within the
	// Structure definition itself
	private static Tile initializeMainTile(Point position, TileGrid tileGrid) {
		return tileGrid.tileMap.get(position);
	}

	private static Tile[] initializeTiles(Point position, TileGrid tileGrid) {
		Tile mainTile = new TempleTopLeftTile(position, tileGrid);
		Tile[] tiles = new Tile[] { mainTile, new TempleTopRightTile(new Point(position.x + 1, position.y), tileGrid),
				new TempleBottomLeftTile(new Point(position.x, position.y + 1), tileGrid),
				new TempleBottomRightTile(new Point(position.x + 1, position.y + 1), tileGrid) };
		return tiles;
	}

	// override of resourceGenerating that adds the correct amount to the resource
	// argument
	@Override
	public void generateResources(Resource[] resources) {
		for (Resource resource : resources) {
			if (resource instanceof Gold) {
				resource.changeAmount(goldGenerationAmount);
			}
			if (resource instanceof Faith) {
				resource.changeAmount(faithGenerationAmount);
			}
		}

	}

}