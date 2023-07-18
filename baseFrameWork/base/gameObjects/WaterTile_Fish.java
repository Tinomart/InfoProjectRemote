package base.gameObjects;

import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileGrid;


//Class for a background water Tile with fish... not as impressive as Ducks imo 
public class WaterTile_Fish extends UnevenTile {

	public WaterTile_Fish(Point tilePosition, TileGrid tileGrid) {

		super(tilePosition, tileGrid);
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