package base.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.HashMap;
import javax.swing.JPanel;

import base.gameObjects.GameObject;
import base.input.InputManager;
import game.Main;

//Panels are like the images you draw on the game window. 
//If the Window is the sheet for a printer then the Panels 
//would be the ink that is printed on it

public class GamePanel extends JPanel {

	// hard coding all the different Panel types we have, if not all of these are
	// assigned when trying to use CreatePanels() an exception will be thrown
	// these will be used as keys for the panels HashMap in the GameWindow Class
	public static enum PanelType {
		MainPanel,
		MainMenu,
		PauseMenu,
		InGameGUI
	}
	
	public ArrayDeque<GameObject> addedObjects;
	public InputManager inputManager = new InputManager(this);

	private int panelWidth;
	private int panelHeight;
	
	public int drawLayer;

	// suggested by eclipse to do to avoid warning
	private static final long serialVersionUID = 1L;

	public GamePanel(int width, int height, int drawLayer) {
		panelWidth = width * Main.TILE_SIZE;
		panelHeight = height * Main.TILE_SIZE;
		this.drawLayer = drawLayer;
		InitializePanel();
		inputManager.AddInputForPanel();
	}

	private void InitializePanel() {
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		//this.setLayout();
		//this lets the Panel register key inputs
		//Important to make sure that the panel can react
		//when you press any key
		this.setFocusable(true);
	}

	// Method that will be used in GameRunningManager to assign to the GameWindow
	// all panels it can possibly display, this is so that the SetPanel method can
	// work with only one parameter
	public static HashMap<PanelType, GamePanel> AssignAllPanels(GamePanel... panelsToAssign) {

		HashMap<PanelType, GamePanel> panels = new HashMap<PanelType, GamePanel>();
		PanelType[] panelTypes = PanelType.values();

		// try/catch to make sure the inputs the method got were valid (sadly could only
		// get it to work at runtime)
		try {
			if (panelTypes.length != panelsToAssign.length) {
				throw new base.exceptions.ArrayLengthMismatchException(
						"There must be a Panel for each PanelType. Current PanelType length = " + panelTypes.length);
			}
		} catch (base.exceptions.ArrayLengthMismatchException e) {
			System.out.println(e.getMessage());
		}

		// assignment of panelTypes to panels
		for (int i = 0; i < panelTypes.length; i++) {
			
			//AddCorrectGUI For Panels that require GUI.
			switch (panelTypes[i]) {
				case MainMenu: {
					GUI.AddMainMenuGUI(panelsToAssign[i]);
					break;
				}
				case PauseMenu: {
					GUI.AddPauseMenuGUI(panelsToAssign[i]);
					break;
				}
				case InGameGUI: {
					GUI.AddInGameGUI(panelsToAssign[i]);
					break;
				}
				case MainPanel: {
					panelsToAssign[i].setLayout(null);
					break;
				}
				default:
					break;
			}
			
			panels.put(panelTypes[i], panelsToAssign[i]);
			
			
		}
		
		
		return panels;
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
	    
	    Graphics2D graphics2D = (Graphics2D) graphics.create();
	    
	    // to avoid paint trails of object that is being painted every frame
	    
	    graphics2D.dispose();
	}

}
