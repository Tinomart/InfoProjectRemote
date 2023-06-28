package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;

public class BeachTile_Bottom extends Tile {

	public BeachTile_Bottom(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.BeachTile_BottomSprite;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(GetPosition().x / tileGrid.tileSize).append(";").append(GetPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}