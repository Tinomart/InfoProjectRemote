package base.graphics;

import java.awt.event.ActionEvent;


import javax.swing.*;

import base.graphics.GamePanel.PanelType;
import game.Main;


//TODO Here we will create all the different UI Elements and add them in a static way
//to our GamePanels later if needed

public class GUI {
	
	//title screen, Quit, Play buttons etc.
	public static void AddMainMenuGUI(JPanel panel) {
		JButton startButton = new JButton("Start");
		startButton.addActionListener(e -> startButtonPress(e));
		panel.add(startButton);
	}

	//in game Menu with Quit, etc.
	public static void AddPauseMenuGUI(JPanel panel) {
		panel.setOpaque(false);
		JButton mainMenuButton = new JButton("MainMenu");
		mainMenuButton.addActionListener(e -> MainMenuButtonPress(e));
		panel.add(mainMenuButton);
	}

	//in game icons n shit -"Matteo Holzer"
	public static void AddInGameGUI(JPanel panel) {
		panel.setOpaque(false);
	}
	
	private static void startButtonPress(ActionEvent e) {
		Main.gameWindow.SetPanel(PanelType.MainPanel);
		Main.gameWindow.SetPanel(PanelType.MainMenu, false);
	}
	
	private static void MainMenuButtonPress(ActionEvent e) {
		for (PanelType panelType : PanelType.values()) {
			Main.gameWindow.SetPanel(panelType, false);
		}
		Main.gameWindow.SetPanel(PanelType.MainMenu);
	}	
}
