package base.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;
import base.gameObjects.GameObject;
import base.gameObjects.Structure;
import base.gameObjects.Character;
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
		MainPanel, MainMenu, PauseMenu, InGameGUI,

	}

	public ArrayDeque<GameObject> addedObjects = new ArrayDeque<GameObject>();
	public ArrayDeque<GameObject> removedObjects = new ArrayDeque<GameObject>();
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
		initializePanel();
		inputManager.addInputForPanel();
	}

	private void initializePanel() {
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));

		// "revalidate" is just something generic that checks all components and updates
		// them
		// Doesn't hurt after setting the size or ever. It is never wrong and sometimes
		// it
		// needs to be there to work, so never remove these
		this.revalidate();
		// this lets the Panel register key inputs
		// Important to make sure that the panel can react
		// when you press any key
		this.setFocusable(true);
	}

	// Method that will be used in GameRunningManager to assign to the GameWindow
	// all panels it can possibly display, this is so that the SetPanel method can
	// work with only one parameter
	public static HashMap<PanelType, GamePanel> assignAllPanels(GamePanel... panelsToAssign) {

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
			GamePanel panel = panelsToAssign[i];

			// AddCorrectGUI For Panels that require GUI.
			switch (panelTypes[i]) {
			case MainMenu: {
				GUI.addMainMenuGUI(panel);
				break;
			}
			case PauseMenu: {
				GUI.addPauseMenuGUI(panel);
				break;
			}
			case InGameGUI: {
				GUI.addInGameGUI(panel);
				break;
			}
			default:
				break;
			}
			panelsToAssign[i].revalidate();

			panels.put(panelTypes[i], panelsToAssign[i]);
		}

		return panels;
	}

	private void drawGameObjects(ArrayDeque<GameObject> gameObjects, Graphics graphics) {

		// try catch to supress concurrent modification exceptions, that dont have any
		// impact on our game
		try {
			// first draw lowest layer, call draw method of each gameObject that is not a
			// character or structure and has not been deleted
			Iterator<GameObject> iterator = gameObjects.iterator();
			while (iterator.hasNext()) {
				GameObject gameObject = iterator.next();
				if (!(gameObject instanceof Structure
						|| gameObject instanceof Character && !removedObjects.contains(gameObject))) {
					gameObject.draw(graphics);
				}
			}

			// draw the next layer, do the same as above for all characters and has not been
			// deleted
			iterator = gameObjects.iterator();
			while (iterator.hasNext()) {
				GameObject gameObject = iterator.next();
				if (gameObject instanceof Character && !removedObjects.contains(gameObject)) {
					gameObject.draw(graphics);
				}
			}

			// draw the last layer, do the same as above for all structures and has not been
			// deleted
			iterator = gameObjects.iterator();
			while (iterator.hasNext()) {
				GameObject gameObject = iterator.next();
				if (gameObject instanceof Structure && !removedObjects.contains(gameObject)) {
					gameObject.draw(graphics);
				}
			}
		} catch (Exception e) {

		}

	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D graphics2D = (Graphics2D) graphics.create();

		// call our drawGameObjects
		drawGameObjects(addedObjects, graphics2D);
		// to avoid paint trails of object that is being painted every frame

		graphics2D.dispose();
	}

}
