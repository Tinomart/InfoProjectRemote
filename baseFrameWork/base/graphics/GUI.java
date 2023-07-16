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
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import base.Faith;
import base.Gold;
import base.Resource;
import base.graphics.GamePanel.PanelType;
import game.Main;
import base.gameObjects.*;


//TODO Here we will create all the different UI Elements and add them in a static way
//to our GamePanels later if needed

public class GUI {
	
	static Font pythia;
	
	private static JLabel resourceGold = new JLabel();
	private static JLabel resourceFaith = new JLabel();
	
	private static HashMap<Class<? extends Resource>, JLabel> resources = new HashMap<Class<? extends Resource>, JLabel>();
	static {
		resources.put(Gold.class, resourceGold);
		resources.put(Faith.class, resourceFaith);
	}
	
	
	//GUI for the MainMenu
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
		BufferedImage continueImage = null;
		try {
			continueImage = ImageIO.read(new File("res/fonts/continue.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		Image scaledContinueImage = continueImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		//settings button
		JButton continueButton = new JButton();
		continueButton.setIcon(new ImageIcon(scaledContinueImage));
		continueButton.setOpaque(false);
		continueButton.setBorderPainted(false);
		continueButton.setContentAreaFilled(false);
		continueButton.addActionListener(e -> mainMenuContinueButtonPress(e));
		continueButton.setBackground(Color.BLACK); //changes background of the button
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		buttonPanel.add(continueButton, gbc);
		
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
	
	
	


	public static void addPauseMenuGUI(JPanel panel) {
		panel.setLayout(new GridBagLayout()); // Set GridBagLayout for the panel
		panel.setOpaque(false);
		
		BufferedImage continueImage = null;
		try {
			continueImage = ImageIO.read(new File("res/fonts/continue.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		//Image scaledcontinueImage = continueImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		
		//for resizing the image
		int imageWidth = 200;
		int imageHeight = 70;
		Image scaledContinueImage = continueImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		//in game Menu with Quit, etc.
		
		JButton continueButton = new JButton();
		continueButton.setOpaque(false);
		continueButton.setBorderPainted(false);
		continueButton.setContentAreaFilled(false);
		continueButton.setIcon(new ImageIcon(scaledContinueImage));
		continueButton.addActionListener(e -> continueButtonPress(e));
		panel.add(continueButton);

		BufferedImage mainMenuImage = null;
		try {
			mainMenuImage = ImageIO.read(new File("res/fonts/mainMenu.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		Image scaledMainMenuImage = mainMenuImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		JButton mainMenuButton = new JButton();
		mainMenuButton.setOpaque(false);
		mainMenuButton.setBorderPainted(false);
		mainMenuButton.setContentAreaFilled(false);
		mainMenuButton.setIcon(new ImageIcon(scaledMainMenuImage));
		mainMenuButton.addActionListener(e -> mainMenuButtonPress(e));
		
		JPanel buttonPanel = new JPanel(); // Create a new panel to hold the buttons
	    buttonPanel.setOpaque(false);
	    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Set vertical BoxLayout for the button panel
	    buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center the buttons
	    buttonPanel.add(continueButton);
	    buttonPanel.add(mainMenuButton);
	    buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center the buttons
	    
	    // Create GridBagConstraints to center the button panel
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    gbc.fill = GridBagConstraints.CENTER;
	    
	 // Add the button panel using GridBagConstraints
	    panel.add(buttonPanel, gbc); 
		
		
		
	}

	
//-----------------------------------------------------------------------------	
	//this method is for the inGameGui-Panel
	public static void addInGameGUI(GamePanel panel) {
		panel.setLayout(new BorderLayout());
	    panel.setOpaque(false);
	    
	    //Panel for "Continue-wave" button
	    JPanel wavePanel = new JPanel();
	    wavePanel.setOpaque(false);
	    
	    ImageIcon nextWaveIcon = new ImageIcon("res/fonts/next wave.jpg");
	    Image nextWaveImage = nextWaveIcon.getImage();	
	    
	    Image resizedNextWaveImage = nextWaveImage.getScaledInstance(130, 60, Image.SCALE_SMOOTH);
	    //create imageIcon with resized image
	    ImageIcon resizedNextWaveIcon = new ImageIcon(resizedNextWaveImage);
	    
	    //Creating the continue-Wave-Button
	    JButton continueWaveButton = new JButton(resizedNextWaveIcon);
	    continueWaveButton.setPreferredSize(new Dimension(200, 60));
	    continueWaveButton.setOpaque(false);
	    continueWaveButton.setBorderPainted(false);
	    continueWaveButton.setContentAreaFilled(false);
	    
	    //adding the continue-Wave Button to the wavePanel
	    wavePanel.add(continueWaveButton);
	    	    
	    //adding the wavePanel to the North Side of the Main-Panel 
	    panel.add(wavePanel, BorderLayout.NORTH);
	    
	    
	    //Panel for the different objects ingame
	    JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    userPanel.setOpaque(false);	
	    
	    Dimension userPanelSize = new Dimension(120, 60);
	    userPanel.setPreferredSize(userPanelSize);
	    
	    //different buttons for the userPanel on the left side of the Game
	    JButton cityHallButton = new JButton("Cityhall");	    
	    JButton houseButton = new JButton("House");
	    JButton watchTowerButton = new JButton("Watchtower");
	    JButton templeButton = new JButton("Temple");
	    JButton statueButton = new JButton("Statue");
	    
	        
	    //customize the Buttons; later on we can add images to them
	    houseButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    houseButton.setPreferredSize(new Dimension(100, 60));
	    watchTowerButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    watchTowerButton.setPreferredSize(new Dimension(100, 60));
	    templeButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    templeButton.setPreferredSize(new Dimension(100, 60));
	    cityHallButton.setFont(new Font ("Arial", Font.ITALIC, 10));
	    cityHallButton.setPreferredSize(new Dimension(100,60));
	    statueButton.setFont(new Font("Arial", Font.ITALIC, 10));
	    statueButton.setPreferredSize(new Dimension(100, 60));
	    
	    
	    //ActionListener for each Object
	    continueWaveButton.addActionListener(e -> continueWaveButtonPress(e));
	    watchTowerButton.addActionListener(e -> defenseTowerButtonPress(e, panel));
	    houseButton.addActionListener(e -> houseButtonPress(e, panel));
	    templeButton.addActionListener(e -> templeButtonPress(e, panel));
	    cityHallButton.addActionListener(e -> cityHallButtonPress(e, panel));
	    statueButton.addActionListener(e-> statueButtonPress(e, panel));
	    
	    //adding the object-buttons to the userPanel
	    userPanel.add(houseButton);
	    userPanel.add(watchTowerButton);
	    userPanel.add(templeButton);
	    userPanel.add(cityHallButton);
	    userPanel.add(statueButton);
	    
	    panel.add(userPanel, BorderLayout.WEST);		

	    
	    //draw ressources on the right side
	    JPanel resourcePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    resourcePanel.setOpaque(false);	
	    
	    Dimension ressourcesSize = new Dimension(125, 60);
	    resourcePanel.setPreferredSize(ressourcesSize);
	    
	    ImageIcon goldIcon = new ImageIcon("res/fonts/Gold.png");
	    Image goldImage = goldIcon.getImage();	
	    
	    Image resizedGoldImage = goldImage.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
	    ImageIcon resizedGoldIcon = new ImageIcon(resizedGoldImage);
	    
	    resourceGold.setFont(new Font("Arial", Font.BOLD, 15));
	    resourceGold.setIcon(resizedGoldIcon);
	    resourceGold.setForeground(Color.RED);
	    resourceGold.setPreferredSize(new Dimension(100, 60));
	    
	    //loading the faith-Image
	    ImageIcon faithIcon = new ImageIcon("res/fonts/Faith.jpg");
	    Image faithImage = faithIcon.getImage();	
	    
	    Image resizedFaithImage = faithImage.getScaledInstance(60, 50, Image.SCALE_SMOOTH);
	    ImageIcon resizedFaithIcon = new ImageIcon(resizedFaithImage);
	    
	    resourceFaith.setFont(new Font("Arial", Font.BOLD, 15));
	    resourceFaith.setForeground(Color.RED);
	    resourceFaith.setIcon(resizedFaithIcon);
	    resourceFaith.setPreferredSize(new Dimension(100, 60));
	    
	    resourcePanel.add(resourceGold);
	    resourcePanel.add(resourceFaith);
	
	    //ressource-Panel added to the east side of the main-Panel
	    panel.add(resourcePanel, BorderLayout.EAST);		
	}


	private static void startButtonPress(ActionEvent e) {
		if(!Main.gameLoop.hasGameLoaded()) {
			Main.gameLoop.load("res/BaseSave");
		}
		
		Main.gameWindow.setPanel(PanelType.MainPanel);
		Main.gameWindow.setPanel(PanelType.InGameGUI);
		Main.gameWindow.setPanel(PanelType.MainMenu, false);
		Main.gameLoop.setPaused(false);
	}
	
	private static void mainMenuContinueButtonPress(ActionEvent e) {
		if(!Main.gameLoop.hasGameLoaded()) {
			Main.gameLoop.load("SaveData");
		}
		
		Main.gameWindow.setPanel(PanelType.MainPanel);
		Main.gameWindow.setPanel(PanelType.InGameGUI);
		Main.gameWindow.setPanel(PanelType.MainMenu, false);
		Main.gameLoop.setPaused(false);
		
		
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
	
	private static void continueButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.PauseMenu, false);
		Main.gameWindow.setPanel(PanelType.PauseMenu, false);
		Main.gameLoop.setPaused(false);
	}	
	
	private static void continueWaveButtonPress(ActionEvent e) {
		if(Main.gameLoop.currentWaveCount == 0) {
			Main.gameLoop.combatPhase =  true;
			Main.gameLoop.waves.get(Main.gameLoop.currentWaveCount).begin();
		}
		
		if (!Main.gameLoop.isCombatPhase()) {
			Main.gameLoop.setCombatPhase(true);;
		}
	}
	
	private static void cityHallButtonPress(ActionEvent e, GamePanel panel) {
		if (panel.inputManager.selectedStructure != CityHall.class) {
			panel.inputManager.selectedStructure = CityHall.class;
		} else {
			panel.inputManager.selectedStructure = null;
		}
	}

	private static void templeButtonPress(ActionEvent e, GamePanel panel) {

	}

	private static void houseButtonPress(ActionEvent e, GamePanel panel) {
		if (panel.inputManager.selectedStructure != PolisHouse.class) {
			panel.inputManager.selectedStructure = PolisHouse.class;
		} else {
			panel.inputManager.selectedStructure = null;
		}
	}

	private static void defenseTowerButtonPress(ActionEvent e, GamePanel panel) {
		if (panel.inputManager.selectedStructure != Watchtower.class) {
			panel.inputManager.selectedStructure = Watchtower.class;
		} else {
			panel.inputManager.selectedStructure = null;
		}
	}	
	
	private static void statueButtonPress(ActionEvent e, GamePanel panel) {
//		if(panel.inputManager.selectedStructure != Statue.class) {
//			panel.inputManager.selectedStructure = Statue.class;
//		} else {
//			panel.inputManager.selectedStructure = null;
//		}	
	}





	public static void updateResourceAmount(Resource resource) {
		for (Entry<Class<? extends Resource>, JLabel> resourcePair : resources.entrySet()) {
			if(resourcePair.getKey() == resource.getClass()) {
				resourcePair.getValue().setText("" + resource.getAmount());
			}
		}
	}	
}

