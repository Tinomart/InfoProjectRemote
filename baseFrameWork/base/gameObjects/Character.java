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

public abstract class Character extends GameObject implements Damageable{
	//TODO This class is meant for living, moving beings so soldiers and the like
	//it really depends on what we want to do with them and they still need 
	//sprites working and such before we can resonably implement them
	
	protected GameLoop gameLoop;
	
	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
		
	}
	
	protected Tile currentTileTopLeft;
	protected Tile currentTileBottomLeft;
	protected Tile currentTileTopRight;
	protected Tile currentTileBottomRight;
	protected Structure currentTarget;
	
	protected ArrayList<Tile> standingTiles = new ArrayList<Tile>();
	
	
	
	protected double defaultMoveDirection = Math.random();
	protected Point previousPosition;
	protected Tile tileInMovementDirection;
	
	

	//just like structures, characters should also have health and healthbars that dynamically change
	private int maxHealth = 100;
	public int health;
	
	private Color healthBarColor = new Color(190,25, 25, 255);
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
		//save how much percent of the health is already missing
		double healthPercent = (double) health / (double) maxHealth;
		this.health = health;
		//set the healthbars width to be the original width times the remaing healthPercent
		healthBar.setBounds(healthBar.getX(), healthBar.getY(), (int) (healthBarWidth * healthPercent), healthBar.getHeight());
	}

	@Override
	public void reduceHealth(int health) {
		//use setHealth, so that the healthbarchanges are immediately visible
		setHealth(this.health - health);
	}
	
	protected  int attackDamage;
	protected int moveSpeed;
	
	public Character(Point position, Sprite sprite) {
		super(position, sprite);
		health = maxHealth;
		
		healthBarWidth = position.y - 7;
		//assign the healthbar a Rectangle Component in the middle
		this.healthBar = new RectangleComponent(position.x - sprite.size.x/2,
				healthBarWidth, 32, 5,
				healthBarColor);
		health = maxHealth;
	}
	
	
	public void update() {
		move(moveSpeed);
		attack(attackDamage);
		
		// as soon as a strucutre dies aka their health goes to 0 or below that the
		// strucutre will be destroyed automatically
		if (health <= 0) {
			Main.gameLoop.destroyGameObject(this);
		}
		//display healthbar as soon as the structure has taken damage
		if(health != maxHealth) {
			Main. gameLoop.panels.get(PanelType.MainPanel).add(healthBar);
		}
		
		if(gameLoop != null) {
			currentTileTopLeft = gameLoop.tileGrid.tileMap.get(new Point(getPosition().x/gameLoop.tileGrid.tileSize, getPosition().y/gameLoop.tileGrid.tileSize));
			currentTileBottomLeft = gameLoop.tileGrid.tileMap.get(new Point(getPosition(Corner.bottomleft).x/gameLoop.tileGrid.tileSize, getPosition(Corner.bottomleft).y/gameLoop.tileGrid.tileSize));
			currentTileTopRight = gameLoop.tileGrid.tileMap.get(new Point(getPosition(Corner.topright).x/gameLoop.tileGrid.tileSize, getPosition(Corner.topright).y/gameLoop.tileGrid.tileSize));
			currentTileBottomRight = gameLoop.tileGrid.tileMap.get(new Point(getPosition(Corner.bottomright).x/gameLoop.tileGrid.tileSize, getPosition(Corner.bottomright).y/gameLoop.tileGrid.tileSize));
			if(!standingTiles.contains(currentTileBottomLeft)) {
				standingTiles.add(currentTileBottomLeft);
			}
			if(!standingTiles.contains(currentTileBottomRight)) {
				standingTiles.add(currentTileBottomRight);
			}
			if(!standingTiles.contains(currentTileTopLeft)) {
				standingTiles.add(currentTileTopLeft);
			}
			if(!standingTiles.contains(currentTileTopRight)) {
				standingTiles.add(currentTileTopRight);
			}
		}
		
	}
	
	public Tile getMovableTile() {
		ArrayList<Tile> nonSolidTiles = new ArrayList<Tile>();
		for (Tile tile : standingTiles) {
			if(tile != null) {
				if(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)) != null) {
					if(!gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)));
					}
				}
				if(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x - 1, tile.getTilePosition().y)) != null) {
					if(!gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)));
					}
				}
				if(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y + 1)) != null) {
					if(!gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)));
					}
				}
				if(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x, tile.getTilePosition().y - 1)) != null) {
					if(!gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)).solid) {
						nonSolidTiles.add(gameLoop.tileGrid.tileMap.get(new Point(tile.getTilePosition().x + 1, tile.getTilePosition().y)));
					}
				}
			}
		}
		if(nonSolidTiles.size() != 0) {
			int chosenTileIndex = (int) Math.ceil(defaultMoveDirection*nonSolidTiles.size());
			return nonSolidTiles.get(chosenTileIndex);
		}
		
		return null;
		
	}
	
	public void moveTowardsPoint(Point positionToMoveTowards) {
		Point movementVector = new Point(positionToMoveTowards.x - position.x, positionToMoveTowards.y - position.y);
		if(movementVector.x <= 0 && movementVector.y <= 0) {
			tileInMovementDirection = currentTileBottomLeft;
		} else if (movementVector.x >= 0 && movementVector.y <= 0) {
			tileInMovementDirection = currentTileBottomRight;
		} else if (movementVector.x <= 0 && movementVector.y >= 0) {
			tileInMovementDirection = currentTileTopLeft;
		} else if (movementVector.x >= 0 && movementVector.y >= 0) {
			tileInMovementDirection = currentTileTopRight;
		}
		movementVector.x /= 0.1*Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));
		movementVector.y /= 0.1*Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));
		
		position.x += movementVector.x * moveSpeed/10;
		position.y += movementVector.y * moveSpeed/10;
	}


	protected abstract void attack(int attackDamage);


	protected abstract void move(int moveSpeed);

	
}
