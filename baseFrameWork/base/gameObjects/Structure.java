package base.gameObjects;

import base.graphics.*;
import base.graphics.GamePanel.PanelType;
import base.physics.Damageable;
import base.GameLoop;
import java.awt.*;
import java.util.HashMap;
import base.Resource;
import base.Gold;

public abstract class Structure extends GameObject implements TileBased, Damageable {

	// Structures have a mainTile that is used when placing them it is the tile that
	// will be on the cursor when trying to place a structure. The tiles are just an
	// array of all tiles that are a part of a structure since a structure since a
	// structure is made up of tiles
	public Tile mainTile;
	public Tile[] tiles;
	private GameLoop gameLoop;
	public TileGrid tileGrid;

	// initialize ResourceCost. This is done statically, as we need to be able to
	// access them in a static context
	public static HashMap<Class<? extends Resource>, Integer> cost = new HashMap<>();
	static {
		cost.put(Gold.class, 10);
	}

	// Structures can be attack Attacked and destroyed, which means they need health
	// and MaxHealth
	private int maxHealth = 100;
	protected int health;

	// we need a healthbar to display that health
	private Color healthBarColor = new Color(25, 190, 25, 255);
	private int healthBarWidth;
	private RectangleComponent healthBar;

	// implement the mothods for our interface of damageable
	@Override
	public RectangleComponent getHealthBar() {
		return healthBar;
	}

	@Override
	public int getHealth() {
		return health;
	}

	// setHealth should also immediately reflect the changes of the health on the
	// health bar
	@Override
	public void setHealth(int health) {
		// save how much percent of the health is already missing
		double healthPercent = (double) health / (double) maxHealth;
		this.health = health;
		// set the healthbar's width to be the original width times the remaing
		// healthPercent
		healthBar.setBounds(healthBar.getX(), healthBar.getY(), (int) (healthBarWidth * healthPercent),
				healthBar.getHeight());
	}

	@Override
	public void reduceHealth(int health) {
		// use setHealth, so that the healthbarchanges are immediately visible
		setHealth(this.health - health);
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
		healthBarWidth = mainTile.tileGrid.tileSize * tiles.length;

		health = maxHealth;
		// initiailze health bar to be the size of the amount of tiles and be positioned
		// at the middle of the mainTile horizontal and slightly above it vertically
		this.healthBar = new RectangleComponent(mainTile.position.x + mainTile.tileGrid.tileSize/2 - healthBarWidth/2,
				mainTile.position.y - 7, healthBarWidth, 5, healthBarColor);
		// set all of the tiles to have this as their structure, this is used as a check
		// to see if tiles are part of a structure and potential to interact with it
		setStructureOfTiles();
		// since the sprites are not created with createGameObject, to avoid them being
		// stored in gameLoop.gameobject we need to initialize their sprites manually
		// here
		initializSprites();
	}

	// for each of the strucutres sprites, call the initialize sprite method on them
	private void initializSprites() {
		for (Tile tile : tiles) {
			tile.tileGrid.gameLoop.initializeSprite(tile);
		}
	}

	// set the structure variable to this
	private void setStructureOfTiles() {
		for (Tile tile : tiles) {
			tile.structure = this;
		}
	}

	@Override
	public void update() {

		// display healthbar as soon as the structure has taken damage
		if (health != maxHealth) {
			gameLoop.panels.get(PanelType.MainPanel).add(healthBar);
		}

		// as soon as a strucutre dies aka their health goes to 0 or below that the
		// strucutre will be destroyed automatically
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

	@Override
	public Point getTilePosition() {
		return mainTile.getTilePosition();
	}

	@Override
	public void draw(Graphics graphics) {
		// to draw a structure, draw all of its tiles instead
		if (isActive()) {
			for (Tile tile : tiles) {
				tile.draw(graphics);
			}
		}
	}

	// apply cost in a static context, because it is the same for each Structure,
	// return true and does the cost application if there is enough resource
	// available, else it just return false
	public static boolean applyCost(GameLoop gameLoop, HashMap<Class<? extends Resource>, Integer> cost) {
		boolean canAfford = true;
		// check if the amount of the resources available in the GameLoop are enough to
		// pay for the cost
		for (Class<? extends Resource> resourceClass : cost.keySet()) {
			for (Resource resource : gameLoop.resources) {
				if (resource.getClass() == resourceClass) {
					// if the amount of the resource asked for in the cost is bigger than the amount
					// in the resource of the gameLoop, set can Afford to false
					if (resource.getAmount() < cost.get(resourceClass)) {
						canAfford = false;
					}
				}
			}
		}
		//if we can afford, substract the costs from the resources of the gameLoop
		if (canAfford) {
			for (Class<? extends Resource> resourceClass : cost.keySet()) {
				for (Resource resource : gameLoop.resources) {
					if (resource.getClass() == resourceClass) {
						resource.changeAmount(-cost.get(resourceClass));
					}
				}
			}
		}
		return canAfford;

	}

	// specific toString used for storing structures in the save file, containing
	// all needed information to rebuild the gameObject once starting up the game
	// again
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}
