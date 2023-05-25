package base.gameObjects;

import base.graphics.GamePanel.PanelType;
import base.graphics.RedSquareComponent;
import base.graphics.TileBased;
import game.Main;

import java.awt.Point;
import java.util.HashMap;

public class Tile extends GameObject implements TileBased {
	public Point tilePosition;
	public HashMap<Point, Tile> tileMap;
	
	//There still needs to be a sprite field and have the sprite field be initialized in the constructor with an argument
	//right now redsquare is placeholder for a sprite that we would implement later
	
	public RedSquareComponent redsquare;
	
	public Tile(Point tilePosition) {
		this.tilePosition = tilePosition;
		Main.tileGrid.ReplaceTile(tilePosition, this);
		redsquare = new RedSquareComponent(tilePosition.x, tilePosition.y, Main.TILE_SIZE);
	}
	
	

	public void OnHover() {
		System.out.println("Hovering Tile: " + tilePosition);
		Main.gameWindow.getPanels().get(PanelType.MainPanel).add(redsquare);
		
	}
	
	@Override
	public Tile GetMainTile() {
		return this;
	}



	@Override
	public Tile[] GetTiles() {
		return new Tile[]{this};
	}
}
