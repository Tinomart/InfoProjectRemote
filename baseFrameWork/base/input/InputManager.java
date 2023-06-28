package base.input;

import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import base.GameLoop.Direction;
import base.gameObjects.Tile;
import base.gameObjects.*;

import base.graphics.Sprite;
import base.graphics.GamePanel.PanelType;
import base.input.Input.InputType;
import game.Main;

//This class actually works with the read inputs and executes in game actions when it recognized that a key is pressed.
//There will be a lot of change for this class, as I assume every feature will need their own input code.

public class InputManager{
		
	public JPanel panel;
	public Input input;
	
	//To track all tiles that are currently being hovered
	public ArrayDeque<Tile> currentHoveredTiles = new ArrayDeque<Tile>();
	public Class<? extends Tile> selectedTile;
	
	public InputManager(JPanel panel) {
		this.panel = panel;
		input = new Input(panel);
	}
	
	public void ReadInputs() {
		
		//This is the code for continuous Key Holds. E.g. Camera movement if user holds wasd
		//or when user tries to drag an object or something
		if(!input.currentInput.isEmpty()) {
			
			//I added a debug statement here once and the Sysout itself broke the code.
			//the code is bug free if you dont try to read currentInput in any way, so if
			//for some reason you should add a debug statement here that tries to access
			//currentInput and you get an error, your code is still working fine, there is
			//just some weird interaction going on
			
			//check for all different InputTypes and execute the corresponding action
			//depending on what the current input is
			if(input.currentInput.contains(InputType.leftpress)) {
				ExecuteLeftPress();
			} else if(input.currentInput.contains(InputType.rightpress)) {
				ExecuteRightPress();
			} else if(input.currentInput.contains(InputType.keypress) && input.pressedKeys != null) {
				for (int key : input.pressedKeys) {
					ExecuteKeyPress(key);
				}
			}
			
		}
		
		//This is for Mouse and Key clicks.
		if(input.releasedInput != 0) {
			ExecuteClicks(input.releasedInput);
			input.releasedInput = 0;
		}
		
		
		AddHoveredTiles();
		

	}
	

	

	private void ExecuteKeyPress(int key) {
		//TODO: Tell the game what to do once a certain key is pressed
		
		switch (key) {
			// What comes after KeyEvent.VK_ is the actual key
			case KeyEvent.VK_W: 
				Main.gameLoop.MoveCamera(Direction.up);
				break;
			case KeyEvent.VK_A: 
				Main.gameLoop.MoveCamera(Direction.left);
				break;
			case KeyEvent.VK_S:
				Main.gameLoop.MoveCamera(Direction.down);
				break;
			case KeyEvent.VK_D: 
				Main.gameLoop.MoveCamera(Direction.right);
				break;
			
			default:
				break;
			}
		
	}
	
	private void ExecuteClicks(int releasedInput) {
		//TODO: Tell the game what to do once a certain key is clicked
		
		switch (releasedInput) {
		
			//if left click
			case MouseEvent.BUTTON1:
				ExecuteLeftClick();
				break;
			//if right click
			case MouseEvent.BUTTON3:
				ExecuteRightClick();
				break;
		
			case KeyEvent.VK_ESCAPE:
				//Open Pause Menu if the User is not currently in the Main Menu
				//Close Pause Menu if Pause Menu is open
				
				if(!Main.gameWindow.activePanels.contains(PanelType.MainMenu)){
					if(!Main.gameWindow.activePanels.contains(PanelType.PauseMenu)) {
						Main.gameWindow.SetPanel(PanelType.PauseMenu);
					} else {
						Main.gameWindow.SetPanel(PanelType.PauseMenu, false);
					}
				}
				break;
			case KeyEvent.VK_T: 
				if(selectedTile != CircleTile.class) {
					selectedTile = CircleTile.class;
				} else {
					selectedTile = null;
				}
				break;
			
			case KeyEvent.VK_1: 
				if(selectedTile != GrassTile.class) {
					selectedTile = GrassTile.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_2: 
				if(selectedTile != GrassFlower_1Tile.class) {
					selectedTile = GrassFlower_1Tile.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_3: 
				if(selectedTile != GrassFlower_2Tile.class) {
					selectedTile = GrassFlower_2Tile.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_4: 
				if(selectedTile != GrassTreeTrunkTile.class) {
					selectedTile = GrassTreeTrunkTile.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_5: 
				if(selectedTile != PureGrassTile.class) {
					selectedTile = PureGrassTile.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_6: 
				if(selectedTile != WaterTile_Fish.class) {
					selectedTile = WaterTile_Fish.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_7: 
				if(selectedTile != BeachTile_LeftBottom.class) {
					selectedTile = BeachTile_LeftBottom.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_8: 
				if(selectedTile != BeachTile_Bottom.class) {
					selectedTile = BeachTile_Bottom.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_9: 
				if(selectedTile != BeachTile_RightBottom.class) {
					selectedTile = BeachTile_RightBottom.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_Q: 
				if(selectedTile != BeachTile_Top.class) {
					selectedTile = BeachTile_Top.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_W: 
				if(selectedTile != BeachTile_LeftTop.class) {
					selectedTile = BeachTile_LeftTop.class;
				} else {
					selectedTile = null;
				}
				break;	
			case KeyEvent.VK_E: 
				if(selectedTile != BeachTile_RightTop.class) {
					selectedTile = BeachTile_RightTop.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_R: 
				if(selectedTile != BeachTile_Right.class) {
					selectedTile = BeachTile_Right.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_I: 
				if(selectedTile != BeachTile_Left.class) {
					selectedTile = BeachTile_Left.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_Z: 
				if(selectedTile != WaterTile.class) {
					selectedTile = WaterTile.class;
				} else {
					selectedTile = null;
				}
				break;
			case KeyEvent.VK_U: 
				if(selectedTile != WaterTile_Ducks.class) {
					selectedTile = WaterTile_Ducks.class;
				} else {
					selectedTile = null;
				}
				break;
			default:
				break;
				
				
		}
	
	}

	private void ExecuteRightClick() {
		// TODO: Tell the game what to do if the right mouse button is cicked		
		
	}

	private void ExecuteLeftClick() {
		// TODO Auto-generated method stub
		if(selectedTile != null) {
			Main.gameLoop.gameObjects.add(Main.gameLoop.createGameObject(selectedTile, new Object[] {currentHoveredTiles.getFirst().GetTilePosition(), Main.tileGrid}));
		}
		
	}

	private void ExecuteRightPress() {
		// TODO: Tell the game what to do if the left mouse button is pressed
		
	}
	private void ExecuteLeftPress() {
		// TODO: Tell the game what to do if the left mouse button is pressed
		
	}

	private void AddHoveredTiles() {
		// TODO This is mostly if we have cursor that is meant to hover more than one tile
		//say we want to add a big building more tiles should be hovered. I would dynamically
		//change the cursor size depending on what we have equipped and then take that to read
		//what tiles are being hovered
		
		//calculate MousePosition on our Map, as we only track the mouse position in the window right now
		int xPositionOnPanel = input.mousePositionInWindow.x + Main.gameLoop.cameraPosition.x;
		int yPositionOnPanel = input.mousePositionInWindow.y + Main.gameLoop.cameraPosition.y;
		Point mouseTilePosition = new Point(xPositionOnPanel/Main.TILE_SIZE, yPositionOnPanel/Main.TILE_SIZE);
		
		
		if(!currentHoveredTiles.contains(Main.tileGrid.tileMap.get(mouseTilePosition))) {
			//remove highlighting redsquare
			for (Tile tile : currentHoveredTiles) {
				tile.redsquare.removeComponent();
			}
			
			//remove all previously hovered tiles
			currentHoveredTiles.clear();
			//add the tiles that the mouse is over
			if(Main.tileGrid.tileMap.get(mouseTilePosition)!= null) {
				for (Tile tile : Main.tileGrid.tileMap.get(mouseTilePosition).GetTiles()) {
					currentHoveredTiles.add(tile);
				}
			}
			HoverTiles();
		}
	}
	
	private void HoverTiles() {
		//Place holder To show that the TileSystem is semi functional
		
		for (Tile tile : currentHoveredTiles) {
			tile.onHover();
		}
	}

	public void AddInputForPanel() {
		panel.addMouseListener(input);
		panel.addMouseMotionListener(input);
		panel.addKeyListener(input);
	}

	
	
	
	
}
