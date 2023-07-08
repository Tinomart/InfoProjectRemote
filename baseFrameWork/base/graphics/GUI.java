package base.graphics;

import java.awt.Graphics2D;
import java.awt.BorderLayout;

//import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
		titlePanel.setBackground(new Color(170, 71, 186));
		
		
		//Create TitleLabel for Title
		JLabel titleLabel = new JLabel("Titel");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titlePanel.add(titleLabel, BorderLayout.NORTH);
		
		//Button-Panel
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(new Color(147, 15, 165));
		
		//Image for startButton
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(new File("res/Start Button.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//for resizing the image because it was really huge
		int imageWidth = 50;
		int imageHeight = 50;
		Image scaledImage = originalImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		//Start-Button
		JButton startButton = new JButton();
		startButton.setIcon(new ImageIcon(scaledImage));
		startButton.setOpaque(false);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.addActionListener(e -> startButtonPress(e));	
		
//		startButton.setFont(new Font("Arial", Font.BOLD, 15));
//		startButton.setForeground(Color.WHITE);
//		startButton.setBackground(Color.BLUE);

		//this should change when you hover the startButton
//		startButton.setUI(new BasicButtonUI() {
//			@Override
//			public void paint(Graphics g, JComponent c) {
//				Graphics g2 = (Graphics2D) g.create();
//				g2.setColor(c.getBackground());
//				g2.fillRect(0, 0, c.getWidth(), c.getHeight());
//				g2.dispose();
//				super.paint(g, c);
//			}
//		});
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
		quitButton.addActionListener(e -> quitButtonPress(e));	
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

//-----------------------------------------------------------------------------	
	//in game icons n shit -"Matteo Holzer"
	//idk why, but it will place it on every borderside except SOUTH where we want it to be
	public static void addInGameGUI(JPanel panel) {
		panel.setLayout(new BorderLayout());
	    panel.setOpaque(false);
	    
	    //Panel for the different Elements ingame
	    JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    userPanel.setOpaque(false);	
	    
	    //different buttons
	    JButton button1 = new JButton("Haus");
	    JButton button2 = new JButton("Tür");
	    JButton button3 = new JButton("Kaserne");
	    
	    //loading the image
	    ImageIcon originalIcon = new ImageIcon("res/internetHaus.png");
	    Image originalImage = originalIcon.getImage();	
	    //TODO find out how the image is automatically resized to the button size without needing to change the coordinates everywhere
	    //TODO probably define so local dimensions so that we only need to change it in one place
	    //resizing the image
	    Image resizedImage = originalImage.getScaledInstance(100, 60, Image.SCALE_SMOOTH);
	    //create imageIcon with resized image
	    ImageIcon resizedIcon = new ImageIcon(resizedImage);
	    //creating button with resized imageIcon
	    JButton button4 = new JButton(resizedIcon);
	    
	    //customize the Buttons; later on we can add images to them
	    button1.setFont(new Font("Arial", Font.ITALIC, 10));
	    button1.setPreferredSize(new Dimension(100, 60));
	    button2.setFont(new Font("Arial", Font.ITALIC, 10));
	    button2.setPreferredSize(new Dimension(100, 60));
	    button3.setFont(new Font("Arial", Font.ITALIC, 10));
	    button3.setPreferredSize(new Dimension(100, 60));
	    
	    userPanel.add(button1);
	    userPanel.add(button2);
	    userPanel.add(button3);
	    userPanel.add(button4);
	    
	    //this two lines are for increasing the Size of the UserPanel if we need it later on
	    Dimension userPanelSize = new Dimension(panel.getWidth(), 60);
	    userPanel.setPreferredSize(userPanelSize);
	    
	    panel.add(userPanel, BorderLayout.NORTH);		
	}
	
	private static void startButtonPress(ActionEvent e) {
		Main.gameWindow.setPanel(PanelType.MainPanel);
		Main.gameWindow.setPanel(PanelType.InGameGUI);
		Main.gameWindow.setPanel(PanelType.MainMenu, false);
	}
	
	//cant close whole window
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
	
	//this method is for the GridBagLayout and includes the variables for different UI elements
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
