package base.gameObjects;

import base.graphics.*;
import base.GameLoop;
import java.awt.*;

public abstract class Structure extends GameObject implements TileBased {

	// Structures have a mainTile that is used when placing them it is the tile that
	// will be on the cursor when trying to place a structure. The tiles are just an
	// array of all tiles that are a part of a structure since a structure since a
	// structure is made up of tiles
	public Tile mainTile;
	public Tile[] tiles;
	private GameLoop gameLoop;
	public TileGrid tileGrid;

	//Structures can be attack Attacked and destroyed, which means they need health and MaxHealth
	private int maxHealth = 100;
	private int health;
	
	//we need a healthbar to display that health
	private Color healthBarColor = new Color(255, 0, 0, 255);
	private RectangleComponent healthBar;
	
	public int getHealth() {
		return health;
	}
	
	//setHealth should aslo immediately refelct the changes of the health on the healthbar
	public void setHealth(int health) {
		double healthChange = this.health/health;
		this.health = health;
		healthBar.width = (int) (healthBar.width * healthChange);
	}

	

	// The main tile is what determines the Position so it is determined as the
	// positional argument of the superclass of GameObject
	public Structure(Tile[] tiles, Tile mainTile, TileGrid tileGrid) {
		super(mainTile.getPosition(), mainTile.getSprite());
		this.mainTile = mainTile;
		this.position = mainTile.getPosition();
		this.tiles = tiles;
		this.tileGrid = tileGrid;
		this.gameLoop = tileGrid.gameLoop;
		this.healthBar = new RectangleComponent(mainTile.position.x + mainTile.tileGrid.tileSize,
				mainTile.position.y + mainTile.tileGrid.tileSize/2, mainTile.tileGrid.tileSize * tiles.length, 5, healthBarColor);
		health = maxHealth;
		setStructureOfTiles();
		initializSprites();
	}

	private void initializSprites() {
		for (Tile tile : tiles) {
			tile.tileGrid.gameLoop.initializeSprite(tile);
		}
	}

	private void setStructureOfTiles() {
		for (Tile tile : tiles) {
			tile.structure = this;
		}
	}

	public void update() {
		if (health <= 0) {
			gameLoop.destroyGameObject(this);
		}

	}

	@Override
	public Tile getMainTile() {
		return mainTile;
	}

	@Override
	public Tile[] getTiles() {
		return tiles;
	}
	
	public Point getTilePosition() {
		return mainTile.getTilePosition();
	}
	
	@Override
	public void draw(Graphics graphics) {
		if(isActive()) {
			for (Tile tile : tiles) {
				tile.draw(graphics);
			}
		}
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(getPosition().x/tileGrid.tileSize).append( ";").append(getPosition().y/tileGrid.tileSize).append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}
