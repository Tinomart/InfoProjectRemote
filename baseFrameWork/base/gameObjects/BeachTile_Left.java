package base.gameObjects;

import java.awt.Graphics;
import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;

public class BeachTile_Left extends UnevenTile {

	public BeachTile_Left(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid);
		spriteType = SpriteType.BeachTile_LeftSprite;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}