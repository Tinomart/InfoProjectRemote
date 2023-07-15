package base.graphics;

import java.awt.Point;
import java.util.*;

import game.Main;

//logically very simple method that loads all sprites and assignes them in a Hashmap with a key of an enum 
//called SpriteType and the corresponding sprite. This is where all file operations for the sprites are being
//done, to keep load times very short, because we are store the information about the sprite, which can be 
//then reused as often as we want
public class SpriteLoader {
	// the names of all different sprites we have
	public enum SpriteType {
		TestSprite, CircleSprite, GrassTileSprite, GrassFlowerTile_1Sprite, GrassTreeTrunkTileSprite,
		PureGrassTileSprite, GrassFlower_2TileSprite, WaterTile_FishSprite, BeachTile_LeftBottomSprite,
		BeachTile_BottomSprite, BeachTile_RightBottomSprite, BeachTile_TopSprite, BeachTile_LeftTopSprite,
		BeachTile_RightTopSprite, BeachTile_LeftSprite, BeachTile_RightSprite, WaterTileSprite, WaterTile_DucksSprite,
		House_0_0_TileSprite,House_0_1_TileSprite, House_1_0_TileSprite, House_1_1_TileSprite, Enemy_1TileSprite,
		WatchtowerTopSprite, WatchtowerBottomSprite,CityHallTopRightSprite,CityHallTopMidSprite,CityHallTopLeftSprite,
		CityHallMidRightSprite,CityHallMidMidSprite,CityHallMidLeftSprite,CityHallBottomLeftSprite,CityHallBottomMidSprite,
		CityHallBottomRightSprite,

	}

	public HashMap<SpriteType, Sprite> sprites = new HashMap<SpriteType, Sprite>();

	public SpriteLoader() {
		loadSprites();
	}

	// method that loads every single image assignes it to a sprite and then assigns
	// it into the sprites Hashmap. this has to be done manually for all sprites
	private void loadSprites() {
		Sprite testSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		testSprite.loadImage("res/Test.png");
		sprites.put(SpriteType.TestSprite, testSprite);

		Sprite circleSprite = new Sprite(new Point(50, 50));
		circleSprite.loadImage("res/Kreis.png");
		sprites.put(SpriteType.CircleSprite, circleSprite);

		Sprite grassTileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		grassTileSprite.loadImage("res/GrassTile_1.png");
		sprites.put(SpriteType.GrassTileSprite, grassTileSprite);

		Sprite grassFlowerTile_1Sprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		grassFlowerTile_1Sprite.loadImage("res/GrassTile_2.png");
		sprites.put(SpriteType.GrassFlowerTile_1Sprite, grassFlowerTile_1Sprite);

		Sprite grassTreeTrunkTileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		grassTreeTrunkTileSprite.loadImage("res/GrassTile_3.png");
		sprites.put(SpriteType.GrassTreeTrunkTileSprite, grassTreeTrunkTileSprite);

		Sprite pureGrassTileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		pureGrassTileSprite.loadImage("res/GrassTile_4.png");
		sprites.put(SpriteType.PureGrassTileSprite, pureGrassTileSprite);

		Sprite grassFlower_2TileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		grassFlower_2TileSprite.loadImage("res/GrassTile_5.png");
		sprites.put(SpriteType.GrassFlower_2TileSprite, grassFlower_2TileSprite);

		Sprite waterTile_FishSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		waterTile_FishSprite.loadImage("res/WaterTile_Fish.png");
		sprites.put(SpriteType.WaterTile_FishSprite, waterTile_FishSprite);

		Sprite BeachTile_LeftBottomSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_LeftBottomSprite.loadImage("res/BeachTile_LeftBottom.png");
		sprites.put(SpriteType.BeachTile_LeftBottomSprite, BeachTile_LeftBottomSprite);

		Sprite BeachTile_BottomSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_BottomSprite.loadImage("res/BeachTile_Bottom.png");
		sprites.put(SpriteType.BeachTile_BottomSprite, BeachTile_BottomSprite);

		Sprite BeachTile_RightBottomSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_RightBottomSprite.loadImage("res/BeachTile_RightBottom.png");
		sprites.put(SpriteType.BeachTile_RightBottomSprite, BeachTile_RightBottomSprite);

		Sprite BeachTile_TopSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_TopSprite.loadImage("res/BeachTile_Top.png");
		sprites.put(SpriteType.BeachTile_TopSprite, BeachTile_TopSprite);

		Sprite BeachTile_LeftTopSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_LeftTopSprite.loadImage("res/BeachTile_LeftTop.png");
		sprites.put(SpriteType.BeachTile_LeftTopSprite, BeachTile_LeftTopSprite);

		Sprite BeachTile_RightTopSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_RightTopSprite.loadImage("res/BeachTile_RightTop.png");
		sprites.put(SpriteType.BeachTile_RightTopSprite, BeachTile_RightTopSprite);

		Sprite BeachTile_LeftSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_LeftSprite.loadImage("res/BeachTile_Left.png");
		sprites.put(SpriteType.BeachTile_LeftSprite, BeachTile_LeftSprite);

		Sprite BeachTile_RightSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		BeachTile_RightSprite.loadImage("res/BeachTile_Right.png");
		sprites.put(SpriteType.BeachTile_RightSprite, BeachTile_RightSprite);

		Sprite WaterTileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		WaterTileSprite.loadImage("res/WaterTile.png");
		sprites.put(SpriteType.WaterTileSprite, WaterTileSprite);

		Sprite WaterTile_DucksSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		WaterTile_DucksSprite.loadImage("res/WaterTile_Ducks.png");
		sprites.put(SpriteType.WaterTile_DucksSprite, WaterTile_DucksSprite);
		
		Sprite House_0_0_TileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		House_0_0_TileSprite.loadImage("res/House_0_0_Tile.png");
		sprites.put(SpriteType.House_0_0_TileSprite, House_0_0_TileSprite);
		
		Sprite House_0_1_TileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		House_0_1_TileSprite.loadImage("res/House_0_1_Tile.png");
		sprites.put(SpriteType.House_0_1_TileSprite, House_0_1_TileSprite);
		
		Sprite House_1_0_TileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		House_1_0_TileSprite.loadImage("res/House_1_0_Tile.png");
		sprites.put(SpriteType.House_1_0_TileSprite, House_1_0_TileSprite);
		
		Sprite House_1_1_TileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		House_1_1_TileSprite.loadImage("res/House_1_1_Tile.png");
		sprites.put(SpriteType.House_1_1_TileSprite, House_1_1_TileSprite);
		
		Sprite Enemy_1TileSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		Enemy_1TileSprite.loadImage("res/Enemy_1Tile.png");
		sprites.put(SpriteType.Enemy_1TileSprite, Enemy_1TileSprite);
		
		Sprite WatchtowerTopSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		WatchtowerTopSprite.loadImage("res/WatchtowerTop.png");
		sprites.put(SpriteType.WatchtowerTopSprite, WatchtowerTopSprite);
		
		Sprite WatchtowerBottomSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		WatchtowerBottomSprite.loadImage("res/WatchtowerBottom.png");
		sprites.put(SpriteType.WatchtowerBottomSprite, WatchtowerBottomSprite);
		
		Sprite CityHallTopLeftSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallTopLeftSprite.loadImage("res/CityHallTopLeft.png");
		sprites.put(SpriteType.CityHallTopLeftSprite, CityHallTopLeftSprite);
		
		Sprite CityHallTopMidSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallTopMidSprite.loadImage("res/CityHallTopMid.png");
		sprites.put(SpriteType.CityHallTopMidSprite, CityHallTopMidSprite);

		Sprite CityHallMidLeftSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallMidLeftSprite.loadImage("res/CityHallMidLeft.png");
		sprites.put(SpriteType.CityHallMidLeftSprite, CityHallMidLeftSprite);
		
		Sprite CityHallTopRightSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallTopRightSprite.loadImage("res/CityHallTopRight.png");
		sprites.put(SpriteType.CityHallTopRightSprite, CityHallTopRightSprite);
		
		Sprite CityHallMidMidSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallMidMidSprite.loadImage("res/CityHallMidMid.png");
		sprites.put(SpriteType.CityHallMidMidSprite, CityHallMidMidSprite);
		
		Sprite CityHallMidRightSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallMidRightSprite.loadImage("res/CityHallMidRight.png");
		sprites.put(SpriteType.CityHallMidRightSprite, CityHallMidRightSprite);
		
		Sprite CityHallBottomLeftSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallBottomLeftSprite.loadImage("res/CityHallBottomLeft.png");
		sprites.put(SpriteType.CityHallBottomLeftSprite, CityHallBottomLeftSprite);
		
		Sprite CityHallBottomMidSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallBottomMidSprite.loadImage("res/CityHallBottomMid.png");
		sprites.put(SpriteType.CityHallBottomMidSprite, CityHallBottomMidSprite);
		
		Sprite CityHallBottomRightSprite = new Sprite(new Point(Main.TILE_SIZE, Main.TILE_SIZE));
		CityHallBottomRightSprite.loadImage("res/CityHallBottomRight.png");
		sprites.put(SpriteType.CityHallBottomRightSprite, CityHallBottomRightSprite);
		
		
		
	}
}
