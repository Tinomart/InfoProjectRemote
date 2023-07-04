package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;

public class GrassFlower_2Tile extends Tile {

	public GrassFlower_2Tile(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.GrassFlower_2TileSprite;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}