package base.graphics;

import java.util.HashMap;
import java.util.ArrayDeque;

import javax.swing.JFrame;

public class GameWindow {

	private String title;
	private JFrame screen = new JFrame();
	private HashMap<GamePanel.PanelType, GamePanel> panels;
	public ArrayDeque<GamePanel.PanelType> activePanels;

	public GameWindow(String title, HashMap<GamePanel.PanelType, GamePanel> panels) {
		this.title = title;
		
		//all panels that can possibly added to the game window
		this.setPanels(panels);
		
		// this is to get the Panels it has to print as some would be active and others
		// would not
		activePanels = new ArrayDeque<>();
		InitializeScreen();
	}

	public void InitializeScreen() {
		// so that the window can get Closed
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// pretty self explanatory
		screen.setResizable(false);
		screen.setTitle(title);
	}

	// Sets panel, so that it will be displayed in the Window
	public void SetPanel(GamePanel.PanelType panelType) {
		activePanels.add(panelType);
		screen.add(getPanels().get(panelType));
	}

	public void OpenWindow() {
		//so that the things we did in InitialzeScreen do something
		screen.pack();
		
		//so that the window displays in the middle of the screen not in the corner
		screen.setLocationRelativeTo(null);
		
		//So that we can see the window
		screen.setVisible(true);
	}

	public HashMap<GamePanel.PanelType, GamePanel> getPanels() {
		return panels;
	}

	public void setPanels(HashMap<GamePanel.PanelType, GamePanel> panels) {
		this.panels = panels;
	}
}
