package base.gameObjects;

import java.awt.Point;

import base.GameLoop;
import base.ResourceGenerating;
import base.graphics.Sprite;
import base.graphics.TileGrid;
import game.Main;
import base.graphics.SpriteLoader.SpriteType;

public class Enemy_1 extends Character {
	
	public Enemy_1(Point position) {

		super(position, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.Enemy_1TileSprite;
		moveSpeed = 5;
		attackDamage = 1;
	}






	@Override
	protected void attack(int attackDamage) {
		if(currentTileTopLeft != null && currentTileBottomLeft != null && currentTileTopRight != null && currentTileBottomRight != null) {
			Boolean damageDealt = false;
			
			if(currentTileTopLeft.structure != null) {
				currentTileTopLeft.structure.reduceHealth(attackDamage);
				damageDealt = true;
			}
			if(currentTileBottomLeft.structure != null && !damageDealt) {
				currentTileBottomLeft.structure.reduceHealth(attackDamage);
				damageDealt = true;
			}
			if(currentTileTopRight.structure != null && !damageDealt) {
				currentTileTopRight.structure.reduceHealth(attackDamage);
				damageDealt = true;
			}
			if(currentTileBottomRight.structure != null) {
				currentTileBottomRight.structure.reduceHealth(attackDamage);
			}
		}
	}

	@Override
	protected void move(int moveSpeed) {
		if(gameLoop != null) {
			if(gameLoop.cityHall != null && (currentTileTopLeft != null && currentTileBottomLeft != null && currentTileTopRight != null && currentTileBottomRight != null)) {
				currentTarget = gameLoop.cityHall;
				for (GameObject gameObject : gameLoop.gameObjects) {
					if(gameObject instanceof ResourceGenerating || gameObject instanceof CityHall) {
						if(position.distance(gameObject.position) < position.distance(currentTarget.position)) {
							currentTarget = (Structure) gameObject;
						}
					}
				}
				if(!(currentTileTopLeft.solid || currentTileBottomLeft.solid || currentTileTopRight.solid || currentTileBottomRight.solid)) {
					moveTowardsPoint(currentTarget.position);
				} else {
					for (Tile tile : standingTiles) {
						if(tile.solid && tile.structure == null) {
							avoidingTile = true;
						}
					}
					
					if(avoidingTile) {
						if(currentTileTopLeft.structure == null && currentTileBottomLeft.structure == null && currentTileTopRight.structure == null && currentTileBottomRight.structure == null) {
							previousPosition = new Point(position.x, position.y);
							if(defaultMoveDirection <= 0.5) {
								Point targetAvoidPoint = new Point(-currentTarget.position.y, currentTarget.position.x);
								boolean directionIsValid = true;
								if(gameLoop.tileGrid.tileMap.get(new Point(tileInMovementDirection.getTilePosition().x + targetAvoidPoint.x/Math.abs(targetAvoidPoint.x), tileInMovementDirection.getTilePosition().y + targetAvoidPoint.y/Math.abs(targetAvoidPoint.y))).solid) {
									directionIsValid = false;
								}
								if(directionIsValid) {
									if(moveDistance < gameLoop.tileGrid.tileSize) {
										previousPosition = new Point(position.x, position.y);
										moveTowardsPoint(targetAvoidPoint);
										moveDistance += position.distance(previousPosition);
										if(previousPosition.x == position.x && previousPosition.y == position.y) {
											gameLoop.destroyGameObject(this);
										}
									} else {
										defaultMoveDirection = Math.random();
										avoidingTile = false;
										moveDistance = 0;
									}
									
									
								}
								
							} else {
								Point targetAvoidPoint = new Point(currentTarget.position.y, -currentTarget.position.x);
								boolean directionIsValid = true;
								
								if(Math.abs(targetAvoidPoint.x) >= Math.abs(targetAvoidPoint.y)) {
									if(gameLoop.tileGrid.tileMap.get(new Point(tileInMovementDirection.getTilePosition().x + targetAvoidPoint.x/Math.abs(targetAvoidPoint.x), tileInMovementDirection.getTilePosition().y)).solid) {
										directionIsValid = false;
									}
								} else {
									if(gameLoop.tileGrid.tileMap.get(new Point(tileInMovementDirection.getTilePosition().x , tileInMovementDirection.getTilePosition().y + targetAvoidPoint.y/Math.abs(targetAvoidPoint.y))).solid) {
										directionIsValid = false;
									}
								}
								if(directionIsValid) {
									if(moveDistance < gameLoop.tileGrid.tileSize) {
										previousPosition = new Point(position.x, position.y);
										moveTowardsPoint(targetAvoidPoint);
										moveDistance += position.distance(previousPosition);
										if(previousPosition.x == position.x && previousPosition.y == position.y) {
											gameLoop.destroyGameObject(this);
										}
									} else {
//										defaultMoveDirection = Math.random();
										avoidingTile = false;
										moveDistance = 0;
									}
								}
							}
							if(previousPosition.x == position.x && previousPosition.y == position.y) {
								if(moveDistance < gameLoop.tileGrid.tileSize) {
									previousPosition = new Point(position.x, position.y);
									moveTowardsPoint(new Point(-currentTarget.position.y, currentTarget.position.x));
									moveDistance += position.distance(previousPosition);
									if(previousPosition.x == position.x && previousPosition.y == position.y) {
										gameLoop.destroyGameObject(this);
									}
								} else {
									defaultMoveDirection = Math.random();
									avoidingTile = false;
									moveDistance = 0;
								}
							}
						}
					}

					
				}
				
			} 
	
		}
		
	}
 
}
