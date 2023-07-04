package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;

public class WaterTile_Fish extends Tile {

	public WaterTile_Fish(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid, new Sprite(new Point(0, 0)));
		spriteType = SpriteType.WaterTile_FishSprite;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}