package base.graphics;

import java.util.*;

import javax.swing.JFrame;

import base.graphics.GamePanel.PanelType;

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
		InitializeScreen();
	}

	public void InitializeScreen() {

		// so that the window can get Closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// pretty self explanatory
		this.setResizable(false);
		this.setTitle(title);
	}

	// Sets panel, so that it will be displayed in the Window
	public void SetPanel(GamePanel.PanelType panelToSet) {
		if (!activePanels.contains(panelToSet)) {
			activePanels.add(panelToSet);
			GamePanel panel = getPanels().get(panelToSet);
			this.add(panel);
			SetCorrectDrawingOrder();
		}
		
	}

	public void SetPanel(GamePanel.PanelType panelType, boolean isBeingSetToActive) {
		if (isBeingSetToActive) {
			SetPanel(panelType);
		} else {
			activePanels.remove(panelType);
			this.remove(getPanels().get(panelType));
			SetCorrectDrawingOrder();
		}
	}
	
	private void SetCorrectDrawingOrder() {

		// Get all of our gamePanels with their corresponding layer ordered in a HashMap
		// and remove all Panels from the screen, so that they can be ordered;

		HashMap<Integer, GamePanel> gamePanelsWithLayers = new HashMap<Integer, GamePanel>();
		for (PanelType panelType : activePanels) {
			GamePanel panel = panels.get(panelType);
			gamePanelsWithLayers.put(panel.drawLayer, panel);
			this.remove(panel);
		}

		// find out what the highest layer is

		Set<Integer> layers = gamePanelsWithLayers.keySet();
		int highestLayer = 0;

		for (int layer : layers) {
			if (layer > highestLayer) {
				highestLayer = layer;
			}
		}

//		 going through all layers from highest to lowest and drawing them in order to
//		 display them in the correct order, if there are multiple panels with the same
//		 layer, they are just added in order until there are no more panels on the
//		layer
		for (int currentLayer = highestLayer; currentLayer >= 0; currentLayer--) {
			if (layers.contains(currentLayer)) {
				while (gamePanelsWithLayers.containsKey(currentLayer)) {
					this.add(gamePanelsWithLayers.get(currentLayer));
					gamePanelsWithLayers.remove(currentLayer);
					
					//this is the most important part of the entire method, without
					//this statement literally none of the changes are reflected.
					//you cannot reposition it either, keep it here or none of our
					//entire panel drawing will work
					this.revalidate();
				}
			}
		}
		
		//repaint to reflect changes
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
