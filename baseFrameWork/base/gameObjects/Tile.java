package base.gameObjects;

import base.graphics.GamePanel;
import base.graphics.GamePanel.PanelType;

import base.graphics.SquareComponent;
import base.graphics.Sprite;
import base.graphics.SpriteLoader.SpriteType;
import base.graphics.TileBased;
import base.graphics.TileGrid;
import game.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Tile extends GameObject implements TileBased {
	private Point tilePosition;
	
	public TileGrid tileGrid;
	
	public GamePanel mainPanel;

	//There still needs to be a sprite field and have the sprite field be initialized in the constructor with an argument
	//The redSquare is the very, very basic implementation of the hovering feature and could be replaced later
	//if we find it unfitting
	
	public SquareComponent highlightSquare;
	
	public Structure structure;

	
	
	public Tile(Point tilePosition, TileGrid tileGrid, Sprite sprite) {
		super(new Point(tilePosition.x * tileGrid.tileSize, tilePosition.y * tileGrid.tileSize), sprite);
		this.tileGrid = tileGrid;
		this.tilePosition = tilePosition;
		tileGrid.replaceTile(tilePosition, this);
		mainPanel = tileGrid.gameLoop.window.getPanels().get(PanelType.MainPanel);
		highlightSquare = new SquareComponent(tilePosition.x, tilePosition.y, tileGrid.tileSize, new Color(255, 255, 255, 125));
		spriteType = SpriteType.TestSprite;
		setSprite(sprite);
	}
	
		
	//when hovered display the highlight square on the main panel and repaint to show changes
	public void onHover() {
		mainPanel.add(highlightSquare);
		mainPanel.revalidate();
		tileGrid.gameLoop.window.revalidate();
		tileGrid.gameLoop.window.repaint();
		
	}
	
	public Point getTilePosition() {
		return tilePosition;
	}
	
	@Override
	public Tile getMainTile() {
		return this;
	}



	@Override
	public Tile[] getTiles() {
		return new Tile[]{this};
	}
	
	//individual toString method that stores all necessary information to be able to create an identical tile when loading in the game
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("base.gameObjects.").append(getClass().getSimpleName()).append(",").append(getPosition().x/tileGrid.tileSize).append( ";").append(getPosition().y/tileGrid.tileSize).append(",").append("TileGrid").append(",").append(getSprite().size.x).append(";").append(getSprite().size.y).append(" ");
		return stringBuilder.toString();
	}
	
}
