package base.gameObjects;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

import base.graphics.RectangleComponent;
import base.graphics.Sprite;
import base.GameLoop;
import base.graphics.GamePanel.PanelType;
import base.physics.Damageable;
import game.Main;

public abstract class Character extends GameObject implements Damageable {
	// TODO This class is meant for living, moving beings so soldiers and the like
	// it really depends on what we want to do with them and they still need
	// sprites working and such before we can resonably implement them

	protected GameLoop gameLoop;

	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;

	}

	protected Tile currentTileTopLeft;
	protected Tile currentTileBottomLeft;
	protected Tile currentTileTopRight;
	protected Tile currentTileBottomRight;
	protected Structure currentTarget;

	protected double moveDistance = 0;
	protected boolean avoidingTile = false;

	protected ArrayList<Tile> standingTiles = new ArrayList<Tile>();

	protected double defaultMoveDirection = Math.random();
	protected Point previousPosition;
	protected Tile tileInMovementDirection;

	// just like structures, characters should also have health and healthbars that
	// dynamically change
	private int maxHealth = 100;
	public int health;

	private Color healthBarColor = new Color(190, 25, 25, 255);
	private int healthBarWidth;
	private RectangleComponent healthBar;

	@Override
	public RectangleComponent getHealthBar() {
		return healthBar;
	}

	@Override
	public int getHealth() {
		return health;
	}

	// setHealth should aslo immediately refelct the changes of the health on the
	// healthbar
	@Override
	public void setHealth(int health) {
		// save how much percent of the health is already missing
		double healthPercent = (double) health / (double) maxHealth;
		this.health = health;
		// set the healthbars width to be the original width times the remaing
		// healthPercent
		healthBar.setBounds(healthBar.getX(), healthBar.getY(), (int) (healthBarWidth * healthPercent),
				healthBar.getHeight());
	}

	@Override
	public void reduceHealth(int health) {
		// use setHealth, so that the healthbarchanges are immediately visible
		setHealth(this.health - health);
	}

	protected int attackDamage;
	protected int moveSpeed;

	public Character(Point position, Sprite sprite) {
		super(position, sprite);
		healthBarWidth = 32;

		health = maxHealth;
		// initiailze health bar to be the size of the amount of tiles and be positioned
		// at the middle of the mainTile horizontal and slightly above it vertically
		this.healthBar = new RectangleComponent(position.x - 16, position.y - 7, healthBarWidth, 5, healthBarColor);
	}

	public void update() {
		move(moveSpeed);
		attack(attackDamage);

		// display healthbar as soon as the structure has taken damage
		if (health != maxHealth) {
			Main.gameLoop.panels.get(PanelType.MainPanel).add(healthBar);
		}
		//update the healthBar position
		healthBar.setLocation(new Point(position.x - 16, position.y - 7));
		//check if gameLoop is null
		if (gameLoop != null) {
			//set the Tile to the top left tile to the tile at the postion of the top left corner
			currentTileTopLeft = gameLoop.tileGrid.tileMap.get(new Point(getPosition().x / gameLoop.tileGrid.tileSize,
					getPosition().y / gameLoop.tileGrid.tileSize));
			//set the Tile to the top left tile to the tile at the postion of the bottom left corner
			currentTileBottomLeft = gameLoop.tileGrid.tileMap
					.get(new Point(getPosition(Corner.bottomleft).x / gameLoop.tileGrid.tileSize,
							getPosition(Corner.bottomleft).y / gameLoop.tileGrid.tileSize));
			//set the Tile to the top left tile to the tile at the postion of the top right corner
			currentTileTopRight = gameLoop.tileGrid.tileMap
					.get(new Point(getPosition(Corner.topright).x / gameLoop.tileGrid.tileSize,
							getPosition(Corner.topright).y / gameLoop.tileGrid.tileSize));
			//set the Tile to the top left tile to the tile at the postion of the bottom right corner
			currentTileBottomRight = gameLoop.tileGrid.tileMap
					.get(new Point(getPosition(Corner.bottomright).x / gameLoop.tileGrid.tileSize,
							getPosition(Corner.bottomright).y / gameLoop.tileGrid.tileSize));
			
			//add all those tiles to standing tiles
			if (!standingTiles.contains(currentTileBottomLeft)) {
				standingTiles.add(currentTileBottomLeft);
			}
			if (!standingTiles.contains(currentTileBottomRight)) {
				standingTiles.add(currentTileBottomRight);
			}
			if (!standingTiles.contains(currentTileTopLeft)) {
				standingTiles.add(currentTileTopLeft);
			}
			if (!standingTiles.contains(currentTileTopRight)) {
				standingTiles.add(currentTileTopRight);
			}
		}

		// as soon as a strucutre dies aka their health goes to 0 or below that the
		// strucutre will be destroyed automatically
		
		//destroy the character if its health reaches zero
		if (health <= 0) {
			Main.gameLoop.destroyGameObject(this);
		}

	}

	// method that returns a random tile, that the character can move to
	public Tile getMovableTile() {
		ArrayList<Tile> nonSolidTiles = new ArrayList<Tile>();
		for (Tile tile : standingTiles) {
			if (tile != null) {
				// if the tile to the right is not solid, add it to our nonSolidTiles
				if (gameLoop.tileGrid.tileMap
						.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)) != null) {
					if (!gameLoop.tileGrid.tileMap
							.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap
								.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)));
					}
				}

				// if the tile to the left is not solid, add it to our nonSolidTiles
				if (gameLoop.tileGrid.tileMap
						.get(new Point(tile.getTilePosition().x - 1, tile.getTilePosition().y)) != null) {
					if (!gameLoop.tileGrid.tileMap
							.get(new Point(tile.getTilePosition().x - 1, tile.getTilePosition().y)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap
								.get(new Point(tile.getTilePosition().x - 1, tile.getTilePosition().y)));
					}
				}

				// if the tile to the top is not solid, add it to our nonSolidTiles
				if (gameLoop.tileGrid.tileMap
						.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y + 1)) != null) {
					if (!gameLoop.tileGrid.tileMap
							.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y + 1)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap
								.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y + 1)));
					}
				}

				// if the tile to the bottom is not solid, add it to our nonSolidTiles
				if (gameLoop.tileGrid.tileMap
						.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y - 1)) != null) {
					if (!gameLoop.tileGrid.tileMap
							.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y - 1)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap
								.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y - 1)));
					}
				}
			}
		}

		// select a random tile in the array and return it if there are any non solid
		// tiles
		if (nonSolidTiles.size() != 0) {
			int chosenTileIndex = (int) Math.ceil(defaultMoveDirection * nonSolidTiles.size());
			return nonSolidTiles.get(chosenTileIndex);
		}

		// else just return null
		return null;

	}

	public void moveTowardsPoint(Point positionToMoveTowards) {
		// make a vector from the position to the destinationg
		Point movementVector = new Point(positionToMoveTowards.x - position.x, positionToMoveTowards.y - position.y);

		// set the current tileInMovementDirection as the direction according to the
		// signs of the movementVector
		if (movementVector.x <= 0 && movementVector.y <= 0) {
			tileInMovementDirection = currentTileBottomLeft;
		} else if (movementVector.x >= 0 && movementVector.y <= 0) {
			tileInMovementDirection = currentTileBottomRight;
		} else if (movementVector.x <= 0 && movementVector.y >= 0) {
			tileInMovementDirection = currentTileTopLeft;
		} else if (movementVector.x >= 0 && movementVector.y >= 0) {
			tileInMovementDirection = currentTileTopRight;
		}
		
		//norm the movementvector and make it ten times the size to avoid int division rounding errors 
		movementVector.x /= 0.1 * Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));
		movementVector.y /= 0.1 * Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));

		//add the normed and divided movementVector that is still 10 times the size times the moveSpeed and divide it by 10 again
		position.x += movementVector.x * moveSpeed / 10;
		position.y += movementVector.y * moveSpeed / 10;
	}

	//every character should define an attack and move method
	protected abstract void attack(int attackDamage);

	protected abstract void move(int moveSpeed);

}
