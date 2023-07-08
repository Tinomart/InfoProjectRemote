package base.graphics;

import java.awt.BorderLayout;

//import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;


import javax.swing.*;

import base.graphics.GamePanel.PanelType;
import game.Main;


//TODO Here we will create all the different UI Elements and add them in a static way
//to our GamePanels later if needed

//TODO 
public class GUI {
	
	//title screen, Quit, Play buttons etc.
	public static void addMainMenuGUI(JPanel panel) {
		
		//GridBagLayout for our panel
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLUE);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//TitlePanel
		JPanel titlePanel = new JPanel (new BorderLayout());
		titlePanel.setBackground(Color.RED);
		
		
		//Create TitleLabel for Title
		JLabel titleLabel = new JLabel("Titel");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titlePanel.add(titleLabel, BorderLayout.NORTH);
		
		//Button-Panel
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.GREEN);
		
		//Start-Button
		JButton startButton = new JButton("Start");
		startButton.addActionListener(e -> startButtonPress(e));	
		startButton.setFocusable(false);
		startButton.setFont(new Font("Arial", Font.BOLD, 15));	
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //this will end the column and jump to the next
		buttonPanel.add(startButton, gbc);
		
		
		//settings button
		JButton settingButton = new JButton("Settings");
		settingButton.setFont(new Font("Arial", Font.BOLD, 15));
		settingButton.setFocusable(false);
		settingButton.setForeground(Color.BLACK); //changes the font color
		settingButton.setBackground(Color.LIGHT_GRAY); //changes background of the button
		settingButton.setBorder(BorderFactory.createLoweredBevelBorder());//creates a Border for the button
		gbc.insets = new Insets(11, 11, 11, 11);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		buttonPanel.add(settingButton, gbc);
		
		//quit button
		JButton quitButton = new JButton ("Quit");
		quitButton.setFont(new Font("Arial", Font.BOLD, 15));
		gbc.insets = new Insets(12, 12, 12, 12);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		buttonPanel.add(quitButton, gbc);
	
		//add the titlePanel to Mainpanel
		panel.add(titlePanel, BorderLayout.NORTH);
		
		//add the buttonPanel to Mainpanel
		panel.add(buttonPanel, BorderLayout.CENTER);
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
	}
	
	private static void mainMenuButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.MainMenu);
		for (PanelType panelType : PanelType.values()) {
			if(panelType != PanelType.MainMenu) {
				Main.gameWindow.setPanel(panelType, false);
			} 
		}
	}	
	
	//this method is for the GridBagLayout and includes the variables for different UI elements
		private GridBagConstraints makegbc(int x, int y, int width, int height) {
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = x;
			gbc.gridy = y;
			gbc.gridwidth = width;
			gbc.gridheight = height;
			gbc.insets = new Insets(1, 1, 1, 1);
			return gbc;
		}
}
