package base.graphics;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import base.graphics.GamePanel.PanelType;
import game.Main;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private HashMap<GamePanel.PanelType, GamePanel> panels;
	public ArrayDeque<GamePanel.PanelType> activePanels;

	public GameWindow(String title, HashMap<GamePanel.PanelType, GamePanel> panels) {
		this.title = title;

		// all panels that can possibly added to the game window are assigned
		this.panels = panels;
		// this is to get the Panels it has to print as some would be active and others
		// would not
		activePanels = new ArrayDeque<>();
		SetMenuResizability();
		SetClosingFunctionality();
	}

	// stops the gameloop and closes the program if the user exist the window
	private void SetClosingFunctionality() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.gameLoop.Stop();
				System.exit(0);
			}
		});

	}

	// This had to be its own thing, because for some reason the basic resize
	// implementation was failing me, so I had to write my own.
	private void SetMenuResizability() {
		GameWindow window = this;

		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				for (GamePanel menu : window.getPanels().values()) {
					if (menu instanceof Menu) {
						menu.setSize(new Dimension(window.getSize()));
						menu.repaint();
						menu.revalidate();
					}
				}
			}
		});
	}

	public void InitializeScreen() {

		// so that the window can get Closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// pretty self explanatory
		// this.setResizable(true);
		this.setTitle(title);
	}

	// Sets panel, so that it will be displayed in the Window
	public void SetPanel(GamePanel.PanelType panelToSet) {
		if (!activePanels.contains(panelToSet)) {
			activePanels.add(panelToSet);
			GamePanel panel = getPanels().get(panelToSet);
			// menus are resizable and just as big as the screen so they are just added
			if (getPanels().get(panelToSet) instanceof Menu) {
				this.add(panel);
			} else {
				// all maps are much larger and arent allowed to just get added, so we instead
				// add their scroll panel that displays the part of them that would currently be
				// visible (I dont support this unintuitive solution as it sure was a hassle to
				// understand and make work)
				this.add(((Map) panel).scrollPanel);
			}

			SetCorrectDrawingOrder();
		}

	}

	public void SetPanel(GamePanel.PanelType panelType, boolean isBeingSetToActive) {
		if (isBeingSetToActive) {
			SetPanel(panelType);
		} else {
			activePanels.remove(panelType);
			GamePanel panel = getPanels().get(panelType);

			// remove the panel if it is a menu
			if (panel instanceof Menu) {
				this.remove(panel);
			} else {
//				remove its scroll panel instead if it is a Map, since that is what we are added before
				this.remove(((Map) panel).scrollPanel);
			}
			SetCorrectDrawingOrder();
		}
	}

	private void SetCorrectDrawingOrder() {

		// Get all of our gamePanels and scroll Panels with their corresponding layer
		// ordered in two separate HashMaps
		// and remove all Panels or scroll Panels from the screen, so that they can be
		// ordered;

		HashMap<Integer, GamePanel> gameMenusWithLayers = new HashMap<Integer, GamePanel>();
		HashMap<Integer, JScrollPane> gameMapsWithLayers = new HashMap<Integer, JScrollPane>();
		for (PanelType panelType : activePanels) {
			if (panelType == PanelType.MainPanel) {

			}
			GamePanel panel = panels.get(panelType);
			if (panel instanceof Menu) {
				gameMenusWithLayers.put(panel.drawLayer, panel);
				this.remove(panel);
			} else {
				gameMapsWithLayers.put(panel.drawLayer, ((Map) panel).scrollPanel);
				this.remove(((Map) panel).scrollPanel);
			}
		}

		// first add all Menus as they will always be drawn above the maps

		// find out what the highest layer is

		Set<Integer> layers = gameMenusWithLayers.keySet();
		int highestLayer = 0;

		for (int layer : layers) {
			if (layer > highestLayer) {
				highestLayer = layer;
			}
		}

//		 going through all layers from highest to lowest and drawing them in order to
//		 display them in the correct order, if there are multiple panels with the same
//		 layer, they are just added in order until there are no more panels on the
//		 layer
		for (int currentLayer = highestLayer; currentLayer >= 0; currentLayer--) {
			if (layers.contains(currentLayer)) {
				while (gameMenusWithLayers.containsKey(currentLayer)) {
					this.add(gameMenusWithLayers.get(currentLayer));
					gameMenusWithLayers.remove(currentLayer);

					// this is the most important part of the entire method, without
					// this statement literally none of the changes are reflected.
					// you cannot reposition it either, keep it here or none of our
					// entire panel drawing will work
					this.revalidate();
				}
			}
		}

		// now do the exact same thing again but with the Maps, as they should be below
		// Menus and what is added later is drawn beneath

		// find out what the highest layer is

		layers = gameMapsWithLayers.keySet();
		highestLayer = 0;

		for (int layer : layers) {
			if (layer > highestLayer) {
				highestLayer = layer;
			}
		}

//		 going through all layers from highest to lowest and drawing them in order to
//		 display them in the correct order, if there are multiple panels with the same
//		 layer, they are just added in order until there are no more panels on the
//		 layer
		for (int currentLayer = highestLayer; currentLayer >= 0; currentLayer--) {
			if (layers.contains(currentLayer)) {
				while (gameMapsWithLayers.containsKey(currentLayer)) {
					this.add(gameMapsWithLayers.get(currentLayer));
					gameMapsWithLayers.remove(currentLayer);

					// this is the most important part of the entire method, without
					// this statement literally none of the changes are reflected.
					// you cannot reposition it either, keep it here or none of our
					// entire panel drawing will work
					this.revalidate();
				}
			}
		}

		// repaint to reflect changes
		this.repaint();

	}

	public void OpenWindow() {
		// so that the things we did in InitialzeScreen do something
		this.pack();

		// so that the window displays in the middle of the screen not in the corner
		this.setLocationRelativeTo(null);

		// So that we can see the window
		this.setVisible(true);
	}

	public HashMap<GamePanel.PanelType, GamePanel> getPanels() {
		return panels;
	}

}
