package base.gameObjects;

import java.awt.Point;

import base.GameLoop;
import base.ResourceGenerating;
import base.graphics.Sprite;
import base.graphics.TileGrid;
import game.Main;
import base.graphics.SpriteLoader.SpriteType;

//this is our basic enemy that does nothing special, it just moves towards and deals damage if it is right next to a structure
public class Enemy_1 extends Character {

	// give Enemy_1 all properties specific to its type
	public Enemy_1(Point position) {

		super(position, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.Enemy_1TileSprite;
		moveSpeed = 5;
		attackDamage = 1;
	}

	// override to give Enemy_1 its own attack
	@Override
	protected void attack(int attackDamage) {
		// check if none of the tiles it is touching are null
		if (currentTileTopLeft != null && currentTileBottomLeft != null && currentTileTopRight != null
				&& currentTileBottomRight != null) {
			Boolean damageDealt = false;

			// if its top left corner is touching a structure, deal its attackdamage to it
			if (currentTileTopLeft.structure != null) {
				currentTileTopLeft.structure.reduceHealth(attackDamage);
				damageDealt = true;
			}
			// if its bottom left corner is touching a structure, deal its attackdamage to
			// it
			if (currentTileBottomLeft.structure != null && !damageDealt) {
				currentTileBottomLeft.structure.reduceHealth(attackDamage);
				damageDealt = true;
			}
			// if its top right corner is touching a structure, deal its attackdamage to it
			if (currentTileTopRight.structure != null && !damageDealt) {
				currentTileTopRight.structure.reduceHealth(attackDamage);
				damageDealt = true;
			}
			// if its bottom right corner is touching a structure, deal its attackdamage to it
			if (currentTileBottomRight.structure != null) {
				currentTileBottomRight.structure.reduceHealth(attackDamage);
			}
		}
	}

	@Override
	protected void move(int moveSpeed) {
		// check if gameLoop is null
		if (gameLoop != null) {
			// check if cityhall is null or any of the tiles the enemy is touching is null
			if (gameLoop.cityHall != null && (currentTileTopLeft != null && currentTileBottomLeft != null
					&& currentTileTopRight != null && currentTileBottomRight != null)) {
				// set the target to the cityhall
				currentTarget = gameLoop.cityHall;
				// if any resource generating objects are closer, set the target to that object
				// instead
				for (GameObject gameObject : gameLoop.gameObjects) {
					if (gameObject instanceof ResourceGenerating || gameObject instanceof CityHall) {
						if (position.distance(gameObject.position) < position.distance(currentTarget.position)) {
							currentTarget = (Structure) gameObject;
						}
					}
				}

				// if the enemy is not touching a solid tile, make it move towards the target
				if (!(currentTileTopLeft.solid || currentTileBottomLeft.solid || currentTileTopRight.solid
						|| currentTileBottomRight.solid)) {
					moveTowardsPoint(currentTarget.position);
				} else {
					// if any tile we are touching is does not have a structure, which means we
					// cannot attack it, go into avoiding obstacle mode
					for (Tile tile : standingTiles) {
						if (tile.solid && tile.structure == null) {
							avoidingTile = true;
						}
					}

					// this code is giga scuffed and only semi functional, our enemy will not simply
					// stop, if it is being stopped by an obstacle, but the avoiding behaviour is
					// very strange and I cannot explaing myself why some of it is happening
					if (avoidingTile) {
						// if we can find a tile we can move to, change our movement Vector to the
						// normal vector random wether left or right
						if (getMovableTile() != null) {
							// set the Previous position to the curren position as we plan to update it
							previousPosition = new Point(position.x, position.y);
							// 50/50 to have one or the other normal vector
							if (defaultMoveDirection <= 0.5) {
								// make the vector to the target and change it to one ofthe normal
								Point targetAvoidPoint = new Point(currentTarget.position.y, currentTarget.position.x);
								Point targetVector = new Point(-(position.x - targetAvoidPoint.x),
										position.y - targetAvoidPoint.x);
								targetVector = new Point(position.x + targetVector.x, position.y + targetVector.y);
								// check if the tile we are currently going to is solild and if it is the
								// direction is not valid
								boolean directionIsValid = true;
								if (gameLoop.tileGrid.tileMap.get(new Point(
										tileInMovementDirection.getTilePosition().x
												+ targetVector.x / Math.abs(targetVector.x),
										tileInMovementDirection.getTilePosition().y
												+ targetVector.y / Math.abs(targetVector.y))).solid) {
									directionIsValid = false;
								}
								// if the direction is valid, move in the direction until the enemy has moved as
								// much as a tile

								if (directionIsValid) {
									if (moveDistance < gameLoop.tileGrid.tileSize) {
										previousPosition = new Point(position.x, position.y);
										moveTowardsPoint(targetVector);
										moveDistance += position.distance(previousPosition);
										if (previousPosition.x == position.x && previousPosition.y == position.y) {
											gameLoop.destroyGameObject(this);
										}
									} else {
										// once we have moved the distance of a tile reroll the random directionality
										// and reset the distance we have moved
										defaultMoveDirection = Math.random();
										moveDistance = 0;
									}

								}
							} else {
								// if we cannot find a tile to move to just attmpt to go backwards and see what
								// happends
								Point targetAvoidPoint = new Point(currentTarget.position.y, -currentTarget.position.x);
								Point targetVector = new Point(-(position.x - targetAvoidPoint.x),
										-(position.y - targetAvoidPoint.x));
								targetVector = new Point(position.x + targetVector.x, position.y + targetVector.y);
								boolean directionIsValid = true;

								// check if our direction is valid again, just like above
								if (Math.abs(targetAvoidPoint.x) >= Math.abs(targetVector.y)) {
									if (gameLoop.tileGrid.tileMap.get(new Point(
											tileInMovementDirection.getTilePosition().x
													+ targetVector.x / Math.abs(targetVector.x),
											tileInMovementDirection.getTilePosition().y)).solid) {
										directionIsValid = false;
									}
								} else {
									if (gameLoop.tileGrid.tileMap
											.get(new Point(tileInMovementDirection.getTilePosition().x,
													tileInMovementDirection.getTilePosition().y
															+ targetVector.y / Math.abs(targetVector.y))).solid) {
										directionIsValid = false;
									}
								}

								// if it is valid move in that direction until we have moved for a tile
								if (directionIsValid) {
									if (moveDistance < gameLoop.tileGrid.tileSize) {
										previousPosition = new Point(position.x, position.y);
										moveTowardsPoint(targetAvoidPoint);
										moveDistance += position.distance(previousPosition);
										if (previousPosition.x == position.x && previousPosition.y == position.y) {
											gameLoop.destroyGameObject(this);
										}
									} else {
										// if we have finished, reset the movement count
										moveDistance = 0;
									}
								}
							}
							// if we havent moved at all, try to move again in the normal vector
							if (previousPosition.x == position.x && previousPosition.y == position.y) {
								// move until we have moved a tile
								if (moveDistance < gameLoop.tileGrid.tileSize) {
									previousPosition = new Point(position.x, position.y);
									moveTowardsPoint(new Point(-currentTarget.position.y, currentTarget.position.x));
									moveDistance += position.distance(previousPosition);
									// check if we still havent actually movedand if we are still in the same
									// position kill the enemy, to avoid soft locksf
									if (previousPosition.x == position.x && previousPosition.y == position.y) {
										gameLoop.destroyGameObject(this);
									}
								} else {
									// once we have moved for a tile reroll our move directoin again and reset our
									// moveDistance, so that we may move for a tile again in the future
									defaultMoveDirection = Math.random();
									moveDistance = 0;
								}
							}
						}
					}

				}
				avoidingTile = false;
			}

		}

	}

}
