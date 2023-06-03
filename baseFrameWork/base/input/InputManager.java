package base.input;

import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import base.GameLoop.Direction;
import base.gameObjects.Tile;
import base.graphics.GamePanel.PanelType;
import base.graphics.TileGrid;
import base.input.Input.InputType;
import game.Main;

//This class actually works with the read inputs and executes in game actions when it recognized that a key is pressed.
//There will be a lot of change for this class, as I assume every feature will need their own input code.

public class InputManager{
		
	public JPanel panel;
	public Input input;
	
	//To track all tiles that are currently being hovered
	public ArrayDeque<Tile> currentHoveredTiles = new ArrayDeque<Tile>();
	
	public InputManager(JPanel panel) {
		this.panel = panel;
		input = new Input(panel);
	}
	
	public void ReadInputs() {
		
		//This is the code for continuous Key Holds. E.g Camera movement if user holds wasd
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
//				System.out.println("W");
				Main.gameLoop.MoveCamera(Direction.up);
				break;
			case KeyEvent.VK_A: 
//				System.out.println("A");
				Main.gameLoop.MoveCamera(Direction.left);
				break;
			case KeyEvent.VK_S:
//				System.out.println("S");
				Main.gameLoop.MoveCamera(Direction.down);
				break;
			case KeyEvent.VK_D: 
				//System.out.println("D");
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
			default:
				break;
				
		}
		
	}

	private void ExecuteRightClick() {
		// TODO: Tell the game what to do if the right mouse button is cicked		
		
	}

	private void ExecuteLeftClick() {
		// TODO Auto-generated method stub
		
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
		
		
		Point mouseTilePosition = new Point(input.mousePositionOnPanel.x/Main.TILE_SIZE, input.mousePositionOnPanel.y/Main.TILE_SIZE);
		if(!currentHoveredTiles.contains(Main.tileGrid.tileMap.get(mouseTilePosition))) {
			for (Tile tile : currentHoveredTiles) {
				tile.redsquare.removeComponent();
			}
			
			currentHoveredTiles.clear();
			currentHoveredTiles.add(Main.tileGrid.tileMap.get(mouseTilePosition));
			HoverTiles();
		}
	}
	
	private void HoverTiles() {
		//Place holder To show that the TileSystem is semi functional
		
		for (Tile tile : currentHoveredTiles) {
			tile.OnHover();
		}
	}

	public void AddInputForPanel() {
		panel.addMouseListener(input);
		panel.addMouseMotionListener(input);
		panel.addKeyListener(input);
	}

	
	
	
	
}
