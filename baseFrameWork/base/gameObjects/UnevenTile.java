package base.gameObjects;

import java.awt.Color;
import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.TileGrid;

public class UnevenTile extends Tile {

	public UnevenTile(Point tilePosition, TileGrid tileGrid) {
		super(tilePosition, tileGrid, new Sprite(new Point(0,0)));
		highlightSquare.color = new Color(255, 0, 0, 125);
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",")
				.append(getPosition().x / tileGrid.tileSize).append(";").append(getPosition().y / tileGrid.tileSize)
				.append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}

}
