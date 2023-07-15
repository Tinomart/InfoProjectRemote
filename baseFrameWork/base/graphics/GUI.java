package base.graphics;

import java.awt.event.ActionEvent;


import javax.swing.*;

import base.graphics.GamePanel.PanelType;
import game.Main;


//TODO Here we will create all the different UI Elements and add them in a static way
//to our GamePanels later if needed

public class GUI {
	
	//title screen, Quit, Play buttons etc.
	public static void addMainMenuGUI(JPanel panel) {
		JButton startButton = new JButton("Start");
		startButton.addActionListener(e -> startButtonPress(e));
		panel.add(startButton);
	}

	//in game Menu with Quit, etc.
	public static void addPauseMenuGUI(JPanel panel) {
		panel.setOpaque(false);
		JButton mainMenuButton = new JButton("MainMenu");
		mainMenuButton.addActionListener(e -> mainMenuButtonPress(e));
		panel.add(mainMenuButton);
	}

	//in game icons n shit -"Matteo Holzer"
	public static void addInGameGUI(JPanel panel) {
		panel.setOpaque(false);
	}
	
	private static void startButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.MainPanel);
		Main.gameWindow.setPanel(PanelType.InGameGUI);
		Main.gameWindow.setPanel(PanelType.MainMenu, false);
		Main.gameLoop.setPaused(false);
	}
	
	private static void mainMenuButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.MainMenu);
		for (PanelType panelType : PanelType.values()) {
			if(panelType != PanelType.MainMenu) {
				Main.gameWindow.setPanel(panelType, false);
			} 
		}
	}	
}
