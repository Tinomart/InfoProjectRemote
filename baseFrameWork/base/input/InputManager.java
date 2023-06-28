package base.input;

import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import base.GameLoop.Direction;
import base.gameObjects.*;

import base.graphics.Sprite;
import base.graphics.GamePanel;
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
	
	public double zoomSpeed = 0.025;
	private double zoomFactor;
	public int maxTileSize = 33;
	
	public InputManager(JPanel panel) {
		this.panel = panel;
		input = new Input(panel);
		input.inputManager = this;
	}
	
	public void readInputs() {
		
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
				executeLeftPress();
			} else if(input.currentInput.contains(InputType.rightpress)) {
				executeRightPress();
			} else if(input.currentInput.contains(InputType.keypress) && input.pressedKeys != null) {
				for (int key : input.pressedKeys) {
					executeKeyPress(key);
				}
			}
			
		}
		
		//This is for Mouse and Key clicks.
		if(input.releasedInput != 0) {
			executeClicks(input.releasedInput);
			input.releasedInput = 0;
		}
		
		addHoveredTiles();
		if(input.scrollAmount != 0) {
			zoom(zoomSpeed);
		}
		
		
		
		

	}
	

	

	private void zoom(double zoomSpeed2) {
		boolean zoomAmountIsLargeEnough = true;
		boolean zoomMaxReached = false;
		int positionDeltaX = 0;
		int positionDeltaY = 0;
		int sizeDeltaX = 0;
		int sizeDeltaY = 0;
		int tileSizeDelta = 0;
		
		for (GameObject gameObject : Main.gameLoop.gameObjects) {
			positionDeltaX = (int)(zoomFactor*gameObject.GetPosition().x);
			positionDeltaY = (int)(zoomFactor*gameObject.GetPosition().y);
			sizeDeltaX = (int)(zoomFactor*gameObject.sprite.size.x);
			sizeDeltaY = (int)(zoomFactor*gameObject.sprite.size.y);
			tileSizeDelta = (int)(zoomFactor*Main.tileGrid.tileSize);
			if(!(sizeDeltaX > 0 && sizeDeltaY > 0 && tileSizeDelta > 0)) {
				zoomAmountIsLargeEnough = false;
				zoomFactor += zoomSpeed2;
			} else {
				
			}
		}
		if(Main.tileGrid.tileSize + tileSizeDelta >= maxTileSize) {
			zoomMaxReached = true;
		} else {
			zoomMaxReached = false;
		}
		
		if(zoomAmountIsLargeEnough) {
			System.out.println("working");
			for (GameObject gameObject : Main.gameLoop.gameObjects) {
				positionDeltaX = (int)(zoomFactor*gameObject.GetPosition().x);
				positionDeltaY = (int)(zoomFactor*gameObject.GetPosition().y);
				sizeDeltaX = (int)(zoomFactor*gameObject.sprite.size.x);
				sizeDeltaY = (int)(zoomFactor*gameObject.sprite.size.y);
				tileSizeDelta = (int)(zoomFactor*Main.tileGrid.tileSize);
				if(input.scrollAmount < 0) {
					if(!zoomMaxReached) {
					 Main.tileGrid.tileSize += tileSizeDelta;
					}
				} else {
					Main.tileGrid.tileSize-= tileSizeDelta;
				}
				if(input.scrollAmount < 0) {
					if(!zoomMaxReached) {
						gameObject.GetPosition().x += positionDeltaX;
						gameObject.GetPosition().y += positionDeltaY;
						gameObject.sprite.size.x += sizeDeltaX;
						gameObject.sprite.size.y += sizeDeltaY;
					}
					
				} else {
					gameObject.GetPosition().x -= positionDeltaX;
					gameObject.GetPosition().y -= positionDeltaY;
					gameObject.sprite.size.x -= sizeDeltaX;
					gameObject.sprite.size.y-= sizeDeltaY;
				}
				if(gameObject instanceof Tile) {
					((Tile) gameObject).redsquare.setBounds(gameObject.GetPosition().x, gameObject.GetPosition().y, Main.tileGrid.tileSize, Main.tileGrid.tileSize);
				}
				zoomFactor = zoomSpeed2;
			}
			panel.revalidate();
			Main.gameWindow.revalidate();
			Main.gameWindow.repaint();
		}
		
		zoomAmountIsLargeEnough = true;
		input.scrollAmount = 0;
//		System.out.println(zoomFactor);
		
	}

	private void executeKeyPress(int key) {
		//TODO: Tell the game what to do once a certain key is pressed
		
		switch (key) {
			// What comes after KeyEvent.VK_ is the actual key
			case KeyEvent.VK_W: 
				Main.gameLoop.moveCamera(Direction.up);
				break;
			case KeyEvent.VK_A: 
				Main.gameLoop.moveCamera(Direction.left);
				break;
			case KeyEvent.VK_S:
				Main.gameLoop.moveCamera(Direction.down);
				break;
			case KeyEvent.VK_D: 
				Main.gameLoop.moveCamera(Direction.right);
				break;
			
			default:
				break;
			}
		
	}
	
	private void executeClicks(int releasedInput) {
		//TODO: Tell the game what to do once a certain key is clicked
		
		switch (releasedInput) {
		
			//if left click
			case MouseEvent.BUTTON1:
				executeLeftClick();
				break;
			//if right click
			case MouseEvent.BUTTON3:
				executeRightClick();
				break;
		
			case KeyEvent.VK_ESCAPE:
				//Open Pause Menu if the User is not currently in the Main Menu
				//Close Pause Menu if Pause Menu is open
				
				if(!Main.gameWindow.activePanels.contains(PanelType.MainMenu)){
					if(!Main.gameWindow.activePanels.contains(PanelType.PauseMenu)) {
						Main.gameWindow.setPanel(PanelType.PauseMenu);
					} else {
						Main.gameWindow.setPanel(PanelType.PauseMenu, false);
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

	private void executeRightClick() {
		// TODO: Tell the game what to do if the right mouse button is cicked		
		
	}

	private void executeLeftClick() {
		// TODO Auto-generated method stub
		if(selectedTile != null) {
			Main.gameLoop.gameObjects.add(Main.gameLoop.createGameObject(selectedTile, new Object[] {currentHoveredTiles.getFirst().getTilePosition(), Main.tileGrid}));
		}
		
	}

	private void executeRightPress() {
		// TODO: Tell the game what to do if the left mouse button is pressed
		
	}
	private void executeLeftPress() {
		// TODO: Tell the game what to do if the left mouse button is pressed
		
	}

	private void addHoveredTiles() {
		// TODO This is mostly if we have cursor that is meant to hover more than one tile
		//say we want to add a big building more tiles should be hovered. I would dynamically
		//change the cursor size depending on what we have equipped and then take that to read
		//what tiles are being hovered
		
		//calculate MousePosition on our Map, as we only track the mouse position in the window right now
		int xPositionOnPanel = input.mousePositionInWindow.x + Main.gameLoop.cameraPosition.x;
		System.out.println(xPositionOnPanel);
		int yPositionOnPanel = input.mousePositionInWindow.y + Main.gameLoop.cameraPosition.y;
		System.out.println(Main.tileGrid.tileSize);
		Point mouseTilePosition = new Point(xPositionOnPanel/Main.tileGrid.tileSize, yPositionOnPanel/Main.tileGrid.tileSize);
		
		
		
		if(!currentHoveredTiles.contains(Main.tileGrid.tileMap.get(mouseTilePosition))) {
			//remove highlighting redsquare
			for (Tile tile : currentHoveredTiles) {
				tile.redsquare.removeComponent();
			}
			
			//remove all previously hovered tiles
			currentHoveredTiles.clear();
			//add the tiles that the mouse is over
			if(Main.tileGrid.tileMap.get(mouseTilePosition)!= null) {
				for (Tile tile : Main.tileGrid.tileMap.get(mouseTilePosition).getTiles()) {
					currentHoveredTiles.add(tile);
				}
			}
			hoverTiles();
		}
	}
	
	private void hoverTiles() {
		//Place holder To show that the TileSystem is semi functional
		
		for (Tile tile : currentHoveredTiles) {
			tile.onHover();
		}
	}

	public void addInputForPanel() {
		panel.addMouseListener(input);
		panel.addMouseMotionListener(input);
		panel.addKeyListener(input);
		panel.addMouseWheelListener(input);
	}

	
	
	
	
}
