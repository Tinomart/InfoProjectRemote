package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;

//Class for a beach background Tile with sand on the top of the frame
public class BeachTile_Top extends UnevenTile {


	public BeachTile_Top(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid);
		spriteType = SpriteType.BeachTile_TopSprite;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}