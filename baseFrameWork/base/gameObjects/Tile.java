package base.gameObjects;

import base.graphics.GamePanel.PanelType;
import base.graphics.RedSquareComponent;
import base.graphics.TileBased;
import base.graphics.TileGrid;
import game.Main;

import java.awt.Point;
import java.util.HashMap;

public class Tile extends GameObject implements TileBased {
	private Point tilePosition;
	
	public TileGrid tileGrid;
	
	public Point GetTilePosition() {
		return tilePosition;
	}

	public HashMap<Point, Tile> tileMap;
	
	//There still needs to be a sprite field and have the sprite field be initialized in the constructor with an argument
	//The redSquare is the very, very basic implementation of the hovering feature and could be replaced later
	//if we find it unfitting
	
	public RedSquareComponent redsquare;
	
	public Tile(Point tilePosition, TileGrid tileGrid) {
		super(new Point(tilePosition.x * Main.TILE_SIZE, tilePosition.y * Main.TILE_SIZE));
		this.tilePosition = tilePosition;
		this.tileGrid = tileGrid;
		tileGrid.ReplaceTile(tilePosition, this);
		redsquare = new RedSquareComponent(tilePosition.x, tilePosition.y, Main.TILE_SIZE);
	}
	
	
	//when hovered display the redsquare on the main panel
	public void OnHover() {
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
