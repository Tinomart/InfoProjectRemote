package base.gameObjects;

import java.awt.Color;
import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.TileGrid;

//this is a class to hinder characters from moving and make placing structures on it impossible
public class UnevenTile extends Tile {

	public int tileMovePenalty = 2;

	public UnevenTile(Point tilePosition, TileGrid tileGrid) {
		super(tilePosition, tileGrid, new Sprite(new Point(0, 0)));
		//this tile highlights in red, to show you cannot place structures on it
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
