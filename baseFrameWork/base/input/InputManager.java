package base.input;

import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;

import base.Resource;
import base.GameLoop.Direction;
import base.gameObjects.*;

import base.graphics.GamePanel.PanelType;
import base.input.Input.InputType;
import game.Main;

//This class actually works with the read inputs and executes in game actions when it recognized that a key is pressed.
//There will be a lot of change for this class, as I assume every feature will need their own input code.

public class InputManager {

	public JPanel panel;
	public Input input;

	// To track all tiles that are currently being hovered
	public ArrayDeque<Tile> currentHoveredTiles = new ArrayDeque<Tile>();
	public Class<? extends Tile> selectedTile;
	public Class<? extends Structure> selectedStructure;

//	public double zoomSpeed = 0.025;
//	private double zoomFactor = .0;
	public int maxTileSize = 40;
	public int minTileSize = 8;
	private boolean debugMode = false;

	public InputManager(JPanel panel) {
		this.panel = panel;
		input = new Input(panel);
		input.inputManager = this;
	}

	public void readInputs() {

		// This is the code for continuous Key Holds. E.g. Camera movement if user holds
		// wasd
		// or when user tries to drag an object or something
		if (!input.currentInput.isEmpty()) {

			// I added a debug statement here once and the Sysout itself broke the code.
			// the code is bug free if you dont try to read currentInput in any way, so if
			// for some reason you should add a debug statement here that tries to access
			// currentInput and you get an error, your code is still working fine, there is
			// just some weird interaction going on

			// check for all different InputTypes and execute the corresponding action
			// depending on what the current input is
			if (input.currentInput.contains(InputType.leftpress)) {
				executeLeftPress();
			} else if (input.currentInput.contains(InputType.rightpress)) {
				executeRightPress();
			} else if (input.currentInput.contains(InputType.keypress) && input.pressedKeys != null) {
				Iterator<Integer> iterator = input.pressedKeys.iterator();
				while(iterator.hasNext()) {
					int key = iterator.next();
					executeKeyPress(key);
				}
				
			}

		}

		// This is for Mouse and Key clicks.
		if (input.releasedInput != 0) {
			executeClicks(input.releasedInput);
			input.releasedInput = 0;
		}

		addHoveredTiles();
//		if (input.scrollAmount != 0) {
//			zoom(zoomSpeed);
//		}
		Main.gameWindow.repaint();
	}

//	private void zoom(double zoomSpeed2) {
//		boolean zoomAmountIsLargeEnough = true;
//		boolean zoomMaxReached = false;
//		boolean zoomMinReached = false;
//		int positionDeltaX = 0;
//		int positionDeltaY = 0;
//		int sizeDeltaX = 0;
//		int sizeDeltaY = 0;
//		int tileSizeDelta = 0;
//		
//		for (GameObject gameObject : Main.gameLoop.gameObjects) {
//			positionDeltaX = (int)(zoomFactor*gameObject.getPosition().x);
//			positionDeltaY = (int)(zoomFactor*gameObject.getPosition().y);
//			sizeDeltaX = (int)(zoomFactor*gameObject.getSprite().size.x);
//			sizeDeltaY = (int)(zoomFactor*gameObject.getSprite().size.y);
//			tileSizeDelta = (int)(zoomFactor*Main.tileGrid.tileSize);
//			if(!(sizeDeltaX > 0 && sizeDeltaY > 0 && tileSizeDelta > 0)) {
//				zoomAmountIsLargeEnough = false;
//				zoomFactor += zoomSpeed2;
//			} 
//		}
//		if(Main.tileGrid.tileSize + tileSizeDelta >= maxTileSize) {
//			zoomMaxReached = true;
//		} else {
//			zoomMaxReached = false;
//		}
//		
//		if(Main.tileGrid.tileSize - tileSizeDelta <= minTileSize) {
//			zoomMinReached = true;
//		} else {
//			zoomMinReached = false;
//		}
//		
//		if(zoomAmountIsLargeEnough) {
//			for (GameObject gameObject : Main.gameLoop.gameObjects) {
//				positionDeltaX = (int)(zoomFactor*gameObject.getPosition().x);
//				positionDeltaY = (int)(zoomFactor*gameObject.getPosition().y);
//				sizeDeltaX = (int)(zoomFactor*gameObject.getSprite().size.x);
//				sizeDeltaY = (int)(zoomFactor*gameObject.getSprite().size.y);
//				tileSizeDelta = (int)(zoomFactor*Main.tileGrid.tileSize);
//				
//				if(input.scrollAmount < 0) {
//					if(!zoomMaxReached) {
//			 			Main.tileGrid.tileSize += tileSizeDelta;
//					}
//				} 	else {
//					if(!zoomMinReached) {
//						Main.tileGrid.tileSize -= tileSizeDelta;
//					}	
//				}
//				
//				if(input.scrollAmount < 0) {
//					if(!zoomMaxReached) {
//						gameObject.getPosition().x += positionDeltaX;
//						gameObject.getPosition().y += positionDeltaY;
//						gameObject.getSprite().size.x += sizeDeltaX;
//						gameObject.getSprite().size.y += sizeDeltaY;
//					}
//					
//				} else {
//					if(!zoomMinReached) {
//						gameObject.getPosition().x -= positionDeltaX;
//						gameObject.getPosition().y -= positionDeltaY;
//						gameObject.getSprite().size.x -= sizeDeltaX;
//						gameObject.getSprite().size.y-= sizeDeltaY;
//					}
//					
//				}
//				if(gameObject instanceof Tile) {
//					((Tile) gameObject).highlightSquare.setBounds(gameObject.getPosition().x, gameObject.getPosition().y, Main.tileGrid.tileSize, Main.tileGrid.tileSize);
//				}
//				zoomFactor = zoomSpeed2;
//			}
//			panel.revalidate();
//			Main.gameWindow.revalidate();
//			Main.gameWindow.repaint();
//		} else {
//	    	zoomFactor += zoomSpeed2;
//		}
//		
//		zoomAmountIsLargeEnough = true;
//		input.scrollAmount = 0;
//		
//		
//	}

	private void executeKeyPress(int key) {
		// TODO: Tell the game what to do once a certain key is pressed
		// what is after KeyEvent.VK_ is the key being pressed

		switch (key) {
		// Set basic WASD keybinds for moving the camera around
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
		// TODO: Tell the game what to do once a certain key is clicked
		// what is after KeyEvent.VK_ is the key being clicked
		switch (releasedInput) {

		// if left click
		case MouseEvent.BUTTON1:
			executeLeftClick();
			break;
		// if right click
		case MouseEvent.BUTTON3:
			executeRightClick();
			break;

		case KeyEvent.VK_ESCAPE:
			// Open Pause Menu if the User is not currently in the Main Menu
			// Close Pause Menu if Pause Menu is open

			if (!Main.gameWindow.activePanels.contains(PanelType.MainMenu)) {
				if (!Main.gameWindow.activePanels.contains(PanelType.PauseMenu)) {
					Main.gameWindow.setPanel(PanelType.PauseMenu);
					Main.gameLoop.setPaused(true);
				} else {
					Main.gameWindow.setPanel(PanelType.PauseMenu, false);
					Main.gameLoop.setPaused(false);
				}
			}
			break;

		// all of these set keybinds is selecting different tiles fo map building, if we
		// dont have it selected already, if we do simply unselect it

		case KeyEvent.VK_1:
			if (selectedTile != GrassTile.class) {
				selectedTile = GrassTile.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_2:
			if (selectedTile != GrassFlower_1Tile.class) {
				selectedTile = GrassFlower_1Tile.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_3:
			if (selectedTile != GrassFlower_2Tile.class) {
				selectedTile = GrassFlower_2Tile.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_4:
			if (selectedTile != GrassTreeTrunkTile.class) {
				selectedTile = GrassTreeTrunkTile.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_5:
			if (selectedTile != PureGrassTile.class) {
				selectedTile = PureGrassTile.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_6:
			if (selectedTile != WaterTile_Fish.class) {
				selectedTile = WaterTile_Fish.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_7:
			if (selectedTile != BeachTile_LeftBottom.class) {
				selectedTile = BeachTile_LeftBottom.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_8:
			if (selectedTile != BeachTile_Bottom.class) {
				selectedTile = BeachTile_Bottom.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_9:
			if (selectedTile != BeachTile_RightBottom.class) {
				selectedTile = BeachTile_RightBottom.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_Q:
			if (selectedTile != BeachTile_Top.class) {
				selectedTile = BeachTile_Top.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_T:
			if (selectedTile != BeachTile_LeftTop.class) {
				selectedTile = BeachTile_LeftTop.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_E:
			if (selectedTile != BeachTile_RightTop.class) {
				selectedTile = BeachTile_RightTop.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_R:
			if (selectedTile != BeachTile_Right.class) {
				selectedTile = BeachTile_Right.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_I:
			if (selectedTile != BeachTile_Left.class) {
				selectedTile = BeachTile_Left.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_Z:
			if (selectedTile != WaterTile.class) {
				selectedTile = WaterTile.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_U:
			if (selectedTile != WaterTile_Ducks.class) {
				selectedTile = WaterTile_Ducks.class;
			} else {
				selectedTile = null;
			}
			break;
		case KeyEvent.VK_B:
			if (selectedStructure != CityHall.class) {
				selectedStructure = CityHall.class;
			} else {
				selectedStructure = null;
			}
			break;
		case KeyEvent.VK_Y:
			if (selectedStructure != PolisHouse.class) {
				selectedStructure = PolisHouse.class;
			} else {
				selectedStructure = null;
			}
			break;
		case KeyEvent.VK_X:
			if (selectedStructure != Watchtower.class) {
				selectedStructure = Watchtower.class;
			} else {
				selectedStructure = null;
			}
			break;
		case KeyEvent.VK_C:
			if (selectedStructure != Watchtower.class) {
				selectedStructure = Watchtower.class;
			} else {
				selectedStructure = null;
			}
			break;
		case KeyEvent.VK_V:
			if (selectedStructure != Statue.class) {
				selectedStructure = Statue.class;
			} else {
				selectedStructure = null;
			}
			break;
		case KeyEvent.VK_N:
			if (selectedStructure != Temple.class) {
				selectedStructure = Temple.class;
			} else {
				selectedStructure = null;
			}
			break;
		case KeyEvent.VK_ENTER:
			if(Main.gameLoop.currentWaveCount == 0) {
				Main.gameLoop.combatPhase =  true;
				Main.gameLoop.waves.get(Main.gameLoop.currentWaveCount).begin();
			}
			
			if (!Main.gameLoop.isCombatPhase()) {
				Main.gameLoop.setCombatPhase(true);;
			}
			break;
		case KeyEvent.VK_F2:
			if (!debugMode) {
				debugMode = true;
			} else {
				debugMode = false;
			}
			break;
		default:
			break;

		}

	}

	@SuppressWarnings("unchecked")
	private void executeRightClick() {
		// if we are holding shift and are hovering a structure, destroy it
		if(!Main.gameLoop.isCombatPhase()){
			if (input.pressedKeys.contains(KeyEvent.VK_SHIFT)) {
				for (Tile tile : currentHoveredTiles) {
					if (tile.structure != null) {
						Main.gameLoop.destroyGameObject(tile.structure);
					}
				}
			} else if (selectedStructure != null) {
				// if we have a structure selected and only pressing right click, place the
				// structure.
				boolean placeable = true;
				// if we are hovering a structure, then we cannot place it, so set he placable
				// boolean to false
				for (Tile tile : currentHoveredTiles) {
					if (tile.structure != null) {
						placeable = false;
					}
				}
				
				HashMap<Class<? extends Resource>, Integer> cost = null;
				try {
					
					// if we have a structure selected, set the cursor shape to its shape
					cost = (HashMap<Class<? extends Resource>, Integer>) selectedStructure.getDeclaredField("cost").get(null);
					
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(cost != null && placeable) {
					if(Structure.applyCost(Main.gameLoop, cost)) {
						// if our structure is placable, create it, where our current mouse cursor is
						Main.gameLoop.createGameObject(selectedStructure,
						new Object[] { currentHoveredTiles.getFirst().getTilePosition(), Main.tileGrid });
					}
				}
				
				
				
			}
		}

	}

	private void executeLeftClick() {
		// if we have a tile selected, create that tile and place it where our cursor
		// currently is
		if(debugMode) {
			if (selectedTile != null) {
				Main.gameLoop.createGameObject(selectedTile,
						new Object[] { currentHoveredTiles.getFirst().getTilePosition(), Main.tileGrid });
			}
		}
	}

	private void executeRightPress() {
		// TODO: Tell the game what to do if the right mouse button is pressed

	}

	private void executeLeftPress() {
		// TODO: Tell the game what to do if the left mouse button is pressed

	}

	private void addHoveredTiles() {
		// This is mostly if we have cursor that is meant to hover more than one
		// tile
		// say we want to add a big building more tiles should be hovered. I would
		// dynamically
		// change the cursor size depending on what we have equipped and then take that
		// to read
		// what tiles are being hovered

		// calculate MousePosition on our Map, as we only track the mouse position in
		// the window right now
		int xPositionOnPanel = input.mousePositionInWindow.x + Main.gameLoop.cameraPosition.x;
		int yPositionOnPanel = input.mousePositionInWindow.y + Main.gameLoop.cameraPosition.y;
		// calculate its tile Position
		Point mouseTilePosition = new Point(xPositionOnPanel / Main.tileGrid.tileSize,
				yPositionOnPanel / Main.tileGrid.tileSize);

		// stores the shape of the currently hovering cursor
		Point[] shape = new Point[0];
		try {
			// if we have a structure selected, set the cursor shape to its shape
			if (selectedStructure != null) {
				shape = (Point[]) selectedStructure.getDeclaredField("shape").get(null);
			} else {
				// else reset it to its basic empty form
				shape = new Point[0];
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// variable to check if the hovered tiles have changed, so we can reflect
		// changes if they happen
		boolean hoveredTilesHaveChanged = false;

		// if the tile currently hovered by our mouse is not contained in hovered tiles
		// or the length of our shape + 1 (which represent the tile that gets hovered
		// anyway directly by your cursor) is not the same as the currentHoveredtiles,
		// the currenthoveredtiles have changed
		if (!currentHoveredTiles.contains(Main.tileGrid.tileMap.get(mouseTilePosition))
				|| shape.length + 1 != currentHoveredTiles.size()) {
			hoveredTilesHaveChanged = true;
		}

		// if currentHoveredTiles does not contain all tiles that are being hovered by
		// our shape, the currenthoveredTiles have changed
		for (Point point : shape) {
			if (Main.tileGrid.tileMap
					.get(new Point(mouseTilePosition.x + point.x, mouseTilePosition.y + point.y)) != null) {
				if (!currentHoveredTiles.contains(Main.tileGrid.tileMap
						.get(new Point(mouseTilePosition.x + point.x, mouseTilePosition.y + point.y)))) {
					hoveredTilesHaveChanged = true;
				}
			}
		}
		// if the hovered tiles have changed, we need to reset all old tiles tiles,
		// remove them and add all new tiles that are being hovered
		if (hoveredTilesHaveChanged) {
			// remove highlighting square
			for (Tile tile : currentHoveredTiles) {
				tile.highlightSquare.removeComponent();
			}

			// remove all previously hovered tiles
			currentHoveredTiles.clear();
			// add the tiles that the mouse is over
			if (Main.tileGrid.tileMap.get(mouseTilePosition) != null) {
				// add the tile that the mouse is hovering directly
				for (Tile tile : Main.tileGrid.tileMap.get(mouseTilePosition).getTiles()) {
					currentHoveredTiles.add(tile);
				}
				// if the cursor has a special shape, add all tiles that are hovered by the
				// shape
				if (shape.length != 0) {
					for (Point point : shape) {
						if (Main.tileGrid.tileMap
								.get(new Point(mouseTilePosition.x + point.x, mouseTilePosition.y + point.y)) != null) {
							for (Tile tile : Main.tileGrid.tileMap
									.get(new Point(mouseTilePosition.x + point.x, mouseTilePosition.y + point.y))
									.getTiles()) {
								currentHoveredTiles.add(tile);
							}
						}
					}
				}
			}
			hoverTiles();
		}
	}

	// method to activate all tiles onHover method
	private void hoverTiles() {
		for (Tile tile : currentHoveredTiles) {
			tile.onHover();
		}
	}

	// inputs is all our listeners at once, so we need to add all of them to the
	// panel that Inputmanager is attached to, for them to recognize our inputs
	public void addInputForPanel() {
		panel.addMouseListener(input);
		panel.addMouseMotionListener(input);
		panel.addKeyListener(input);
		panel.addMouseWheelListener(input);
	}

}
