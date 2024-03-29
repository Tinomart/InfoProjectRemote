package base.gameObjects;

import java.awt.Color;
import java.awt.Point;

import base.graphics.Sprite;
import base.graphics.TileGrid;

public class StructureTile extends Tile {
//	 This class is special, necessary and cool in 2 ways
//	 1. if you want to get this tile you always get the mainTile of the structure
//	 instead and if you want
//	 to get the tiles you always just get all structure tiles, meaning stuff like
//	 hovering becomes very intuitive.
//	 2. also a very cool thing is that if you add a non StructureTile to a
//	 structure, it is treated like its own thing, meaning
//	 you can dynamically make Structure parts that can interact in different ways.
//	 Maybe like a special button on a structure
//	 that does something special that is not supposed to happen when clicking on
//	 the normal parts of the structure. So we can make somewhat modular structures.
//	 But non structure tiles will still be saved in the structure meaning if you
//	 add or remove a structure, it will remove or add
//	 the normal tiles as well, so it is only determining the interaction 

	public StructureTile(Point tilePosition, TileGrid tileGrid, Sprite sprite) {
		super(tilePosition, tileGrid, sprite);
		//structure Tiles should have a red color when hovered to show that they cant just be built on like other tiles
		highlightSquare.color = new Color(255, 0, 0, 125);
		
		this.solid = true;
	}

	//instead of getting this tile we get the structures mainTile
	@Override
	public Tile getMainTile() {
		return structure.mainTile;
	}

	//instead of getting this tile we get all tiles of the structure
	@Override
	public Tile[] getTiles() {
		return structure.tiles;
	}
	
	

}
