package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.TileGrid;
import game.Main;
import base.graphics.SpriteLoader.SpriteType;

public class Enemy_1 extends Character {
	
	private double defaultMoveDirection = Math.random();
	private Point previousPosition;

	
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
		if(getGameLoop() != null) {
			if(gameLoop.cityHall != null) {
				if(currentTileTopLeft != null && currentTileBottomLeft != null && currentTileTopRight != null && currentTileBottomRight != null) {
					if(!(currentTileTopLeft.solid || currentTileBottomLeft.solid || currentTileTopRight.solid || currentTileBottomRight.solid)) {
						Point movementVector = new Point(gameLoop.cityHall.position.x - position.x, gameLoop.cityHall.position.y - position.y);
						movementVector.x /= 0.1*Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));
						movementVector.y /= 0.1*Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));
						
						previousPosition = position;
						
						position.x += movementVector.x * moveSpeed/10;
						position.y += movementVector.y * moveSpeed/10;
					} else {
						position = previousPosition;
					}
				}
				
			}
		} 
		
	}
 
}
