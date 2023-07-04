package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;

public class GrassTile extends Tile {

	public GrassTile(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.GrassTileSprite;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}