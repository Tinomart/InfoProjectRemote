package base.gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.IOException;

import base.graphics.Drawable;
import base.graphics.GamePanel;
import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.physics.Positional.Corner;
import base.graphics.TileBased;
import base.graphics.TileGrid;

import java.io.File;
import java.io.FileInputStream;

import game.Main;

//Basic class to define all Objects we want to display in a certain position in our game 

public class GameObject extends GameEntity implements Drawable {

	private GamePanel.PanelType panelToDrawOn;

	private Sprite sprite;

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public SpriteType spriteType;
	public int drawLayer = 0;

	public boolean solid = false;

	public GameObject(Point position, Sprite sprite) {
		// We want GameObjects to be drawn on the main panel only since they are not
		// part of a menu or some special pop up
		super(position);
		this.sprite = sprite;
		panelToDrawOn = GamePanel.PanelType.MainPanel;
		spriteType = SpriteType.PureGrassTileSprite;

	}

	@Override
	public void update() {

	}

	@Override
	public void start() {

	}

	@Override
	public GamePanel.PanelType getPanelToDrawOn() {
		return panelToDrawOn;
	}

	// override to be able to display each corner with the get position method this
	// is done by simply adding the object size to the coordinates
	@Override
	public Point getPosition(Corner corner) {

		switch (corner) {
		case topleft: {
			return position;
		}
		case topright: {
			return new Point(position.x + sprite.size.x, position.y);
		}
		case bottomleft: {
			return new Point(position.x, position.y + sprite.size.y);
		}
		case bottomright: {
			return new Point(position.x + sprite.size.x, position.y + sprite.size.y);
		}
		default:
			return position;
		}
	}

	// a simple method to check whether two object are colliding
	public boolean collisionWith(GameObject gameObject) {
		boolean isColliding = false;
		// check each corner, if any of the corners are within the bounds of the other
		// object, then collision is occurring so set isColliding to true
		for (Corner corner : Corner.values()) {
			if (gameObject.getPosition().x < getPosition(corner).x
					&& gameObject.getPosition(Corner.bottomright).x > getPosition(corner).x
					&& gameObject.getPosition().y < getPosition(corner).y
					&& gameObject.getPosition(Corner.bottomright).y > getPosition(corner).y) {
				isColliding = true;
			}
		}

		return isColliding;
	}

	// basic draw method, it simply draws the sprite at the position with the size
	// of the sprite
	public void draw(Graphics graphics) {
		if (isActive()) {
			graphics.drawImage(sprite.getImage(), getPosition().x, getPosition().y, sprite.size.x, sprite.size.y, null);
		}

	}

	// equality check for gameObjects, we want our gameObjects to be recognized as
	// identical if they either have the same object reference or if their position,
	// size and type are exactly the same
	public boolean equals(GameObject gameObject) {
		if (this.getPosition().x == gameObject.getPosition().x && this.getPosition().y == gameObject.getPosition().y
				&& this.sprite.size == gameObject.sprite.size && this.getClass() == gameObject.getClass()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(getPosition().x)
				.append(";").append(getPosition().y).append(",").append(sprite.size.x).append(";").append(sprite.size.y)
				.append(" ");
		return stringBuilder.toString();
	}

}
