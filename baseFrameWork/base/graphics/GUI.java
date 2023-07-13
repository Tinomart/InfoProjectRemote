package base.graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.BorderLayout;

//import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import base.Gold;
import base.Resource;
import base.graphics.GamePanel.PanelType;
import game.Main;


//TODO Here we will create all the different UI Elements and add them in a static way
//to our GamePanels later if needed

public class GUI {
	
	static Font pythia;
	
	public static Resource[] resources = new Resource[] {new Gold(0)};
		
	
	
	//title screen, Quit, Play buttons etc.
	public static void addMainMenuGUI(JPanel panel) {
		
		//this is a standard code for implementing custom fonts so that it looks more ancient
		//source: https://www.youtube.com/watch?v=43duJsYmhxQ&t=300s
		try {
			pythia = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/pythia.ttf")).deriveFont(70f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pythia.ttf")));
			
		}catch(IOException | FontFormatException e) {
			
		}
		
		panel.setLayout(new BorderLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//TitlePanel
		JPanel titlePanel = new JPanel (new BorderLayout());
		titlePanel.setBackground(new Color(0, 0, 0));
		
		
		//Create TitleLabel for Title
		JLabel titleLabel = new JLabel("POLIS");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setFont(pythia);
		titleLabel.setForeground(Color.WHITE);
		titlePanel.add(titleLabel, BorderLayout.NORTH);
		
		//Button-Panel
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(new Color(0, 0, 0));
		
		//Image for startButton
		BufferedImage startImage = null;
		try {
			startImage = ImageIO.read(new File("res/fonts/start.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//for resizing the image
		int imageWidth = 200;
		int imageHeight = 100;
		Image scaledStartImage = startImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		//Start-Button
		JButton startButton = new JButton();
		startButton.setIcon(new ImageIcon(scaledStartImage));
		startButton.setOpaque(false);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.addActionListener(e -> startButtonPress(e));	
		
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //this will end the column and jump to the next
		buttonPanel.add(startButton, gbc);
		
		//this should change when you hover the startButton
		
		//this is for loading the custom setting-Button
		BufferedImage settingImage = null;
		try {
			settingImage = ImageIO.read(new File("res/fonts/settings.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		Image scaledSettingImage = settingImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		//settings button
		JButton settingButton = new JButton();
		settingButton.setIcon(new ImageIcon(scaledSettingImage));
		settingButton.setOpaque(false);
		settingButton.setBorderPainted(false);
		settingButton.setContentAreaFilled(false);
		settingButton.setBackground(Color.BLACK); //changes background of the button
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		buttonPanel.add(settingButton, gbc);
		
		//loading the custom quit-Button Image
		BufferedImage quitImage = null;
		try {

			quitImage = ImageIO.read(new File("res/fonts/quit.jpg"));

		} catch(IOException e) {
			e.printStackTrace();
		}
		Image scaledQuitImage = quitImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		//quit button
		JButton quitButton = new JButton ();
		quitButton.setIcon(new ImageIcon(scaledQuitImage));
		quitButton.setOpaque(false);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setBackground(Color.BLACK);
		quitButton.addActionListener(e -> quitButtonPress(e));	
		gbc.insets = new Insets(10, 10, 10, 10);
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

//-----------------------------------------------------------------------------	
	//in game icons n shit -"Matteo Holzer"
	public static void addInGameGUI(JPanel panel) {
		panel.setLayout(new BorderLayout());
	    panel.setOpaque(false);
	    
	    //Panel for the different objects ingame
	    JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    userPanel.setOpaque(false);	
	    userPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4, true));
	    
	    Dimension userPanelSize = new Dimension(120, 60);
	    userPanel.setPreferredSize(userPanelSize);
	    
	  //loading the image-example for the cityHall-Button
	    ImageIcon originalIcon = new ImageIcon("res/internetHaus.png");
	    Image originalImage = originalIcon.getImage();	
	    
	    Image resizedImage = originalImage.getScaledInstance(100, 60, Image.SCALE_SMOOTH);
	    //create imageIcon with resized image
	    ImageIcon resizedIcon = new ImageIcon(resizedImage);
	    
	    //different buttons
	    JButton cityHallButton = new JButton(resizedIcon);
	    JButton houseButton = new JButton("Haus");

	    JButton defenseTowerButton = new JButton("defTower");


	    JButton templeButton = new JButton("Kaserne");
	    
	        
	    //customize the Buttons; later on we can add images to them
	    houseButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    houseButton.setPreferredSize(new Dimension(100, 60));
	    defenseTowerButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    defenseTowerButton.setPreferredSize(new Dimension(100, 60));
	    templeButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    templeButton.setPreferredSize(new Dimension(100, 60));
	    cityHallButton.setFont(new Font ("Arial", Font.ITALIC, 10));
	    cityHallButton.setPreferredSize(new Dimension(100,60));
	    
	    //ActionListener for each Object
	    defenseTowerButton.addActionListener(e -> defenseTowerButtonPress(e));
	    houseButton.addActionListener(e -> houseButtonPress(e));
	    templeButton.addActionListener(e -> templeButtonPress(e));
	    cityHallButton.addActionListener(e -> cityHallButtonPress(e));
	    
	    //adding the object-buttons to the userPanel
	    userPanel.add(houseButton);
	    userPanel.add(defenseTowerButton);
	    userPanel.add(templeButton);
	    userPanel.add(cityHallButton);
	    
	    panel.add(userPanel, BorderLayout.WEST);		

	    
	    //draw ressources on the right side
	
	    JPanel ressources = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    ressources.setOpaque(false);	
	    ressources.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4, true));
	    
	    Dimension ressourcesSize = new Dimension(125, 60);
	    ressources.setPreferredSize(ressourcesSize);
	    
	    //TODO replace the name of "ressources1,2,3" to the ressources we want to implement
	    JLabel ressourceGold = new JLabel("Gold: " + resources[0].getAmount());
	    JLabel ressourceFaith = new JLabel("Faith");
	    JLabel ressources3 = new JLabel("ressources3");
	    
	    
	    
	    ressourceGold.setFont(new Font("Arial", Font.BOLD, 15));
	    ressourceGold.setForeground(Color.RED);
	    ressourceGold.setPreferredSize(new Dimension(100, 60));
	    ressourceFaith.setFont(new Font("Arial", Font.BOLD, 15));
	    ressourceFaith.setForeground(Color.RED);
	    ressourceFaith.setPreferredSize(new Dimension(100, 60));
	    ressources3.setFont(new Font("Arial", Font.BOLD, 15));
	    ressources3.setForeground(Color.RED);
	    ressources3.setPreferredSize(new Dimension(100, 60));
	    
	    ressources.add(ressourceGold);
	    ressources.add(ressourceFaith);
	    ressources.add(ressources3);
	
	    //ressource-Panel added to the east side of the main-Panel
	    panel.add(ressources, BorderLayout.EAST);		
	}
	

	


	private static void startButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.MainPanel);
		Main.gameWindow.setPanel(PanelType.InGameGUI);
		Main.gameWindow.setPanel(PanelType.MainMenu, false);
		resources = Main.gameLoop.resources;
	}
	
	private static void quitButtonPress(ActionEvent e) {
		Main.gameLoop.stop();
        System.exit(0);
	}
	
	private static void mainMenuButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.MainMenu);
		for (PanelType panelType : PanelType.values()) {
			if(panelType != PanelType.MainMenu) {
				Main.gameWindow.setPanel(panelType, false);
			} 
		}
	}
	
	//TODO obviously need to add code for getting the object from the buttons to the gamefield
	//so that it can be placed
	private static Object cityHallButtonPress(ActionEvent e) {
		
		return null;
		}


		private static Object templeButtonPress(ActionEvent e) {
		
		return null;
		}


		private static Object houseButtonPress(ActionEvent e) {
		
		return null;
		}


		private static Object defenseTowerButtonPress(ActionEvent e) {
		
		return null;
		}
	
	//this method is for the GridBagLayout and includes the variables for different UI elements
		//can be deleted later on because we dont really need it
//		private GridBagConstraints makegbc(int x, int y, int width, int height) {
//			
//			GridBagConstraints gbc = new GridBagConstraints();
//			gbc.gridx = x;
//			gbc.gridy = y;
//			gbc.gridwidth = width;
//			gbc.gridheight = height;
//			gbc.insets = new Insets(1, 1, 1, 1);
//			return gbc;
//		}
		
}

