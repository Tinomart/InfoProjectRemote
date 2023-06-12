package base.gameObjects;

import base.graphics.GamePanel;
import base.graphics.GamePanel.PanelType;

import base.graphics.RedSquareComponent;
import base.graphics.Sprite;
import base.graphics.TileBased;
import base.graphics.TileGrid;
import game.Main;

import java.awt.Point;
import java.util.HashMap;

public class Tile extends GameObject implements TileBased {
	private Point tilePosition;
	
	public TileGrid tileGrid;
	
	public GamePanel mainPanel;
	
	public HashMap<Point, Tile> tileMap;
	
	//There still needs to be a sprite field and have the sprite field be initialized in the constructor with an argument
	//The redSquare is the very, very basic implementation of the hovering feature and could be replaced later
	//if we find it unfitting
	
	public RedSquareComponent redsquare;
	
	public Tile(Point tilePosition, TileGrid tileGrid, Sprite sprite) {
		super(new Point(tilePosition.x * Main.TILE_SIZE, tilePosition.y * Main.TILE_SIZE), sprite);
		this.tileGrid = tileGrid;
		this.tilePosition = tilePosition;
		tileGrid.ReplaceTile(tilePosition, this);
		mainPanel = tileGrid.gameLoop.window.getPanels().get(PanelType.MainPanel);
		redsquare = new RedSquareComponent(tilePosition.x, tilePosition.y, Main.TILE_SIZE);
	}
	
	
	//when hovered display the redsquare on the main panel
	public void OnHover() {
		mainPanel.add(redsquare);
	}
	
	public Point GetTilePosition() {
		return tilePosition;
	}
	
	@Override
	public Tile GetMainTile() {
		return this;
	}



	@Override
	public Tile[] GetTiles() {
		return new Tile[]{this};
	}
	
	//Tile has one more argument than GameEntity, so it needs to have a new toString that also returns the value of its bonus argument, which is tileGrid
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(GetPosition().x/tileGrid.tileSize).append( ";").append(GetPosition().y/tileGrid.tileSize).append(",").append("TileGrid").append(" ");
		return stringBuilder.toString();
	}
}
