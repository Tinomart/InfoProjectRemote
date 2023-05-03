package base.input;

import java.awt.event.KeyEvent;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import base.gameObjects.Tile;
import base.input.Input.InputType;

//This class actually works with the read inputs and executes in game actions when it recognized that a key is pressed.
//There will be a lot of change for this class, as I assume every feature will need their own input code.

public class InputManager{
		
	public JPanel panel;
	public Input input;
	
	//To track all tiles that are currently being hovered
	public ArrayDeque<Tile> currentHoveredTiles = new ArrayDeque<Tile>();
	
	
	public InputManager(JPanel panel) {
		this.panel = panel;
		input = new Input(panel);
	}
	
	public void ReadInputs() {
		
		//this  if is just to avoid reading input every frame, even when there is not input
		if(!input.currentInput.isEmpty()) {
			
			//I added a debug statement here once and the Sysout itself broke the code.
			//the code is bug free if you dont try to read currentInput in any way, so if
			//for some reason you should add a debug statement here that tries to access
			//currentInput and you get an error, your code is still working fine, there is
			//just some weird interaction going on
			
			//check for all different InputTypes and execute the correspoding action
			//depending on what the current input is
			if(input.currentInput.contains(InputType.leftpress)) {
				ExecuteLeftPress();
			} else if(input.currentInput.contains(InputType.rightpress)) {
				ExecuteRightPress();
			} else if(input.currentInput.contains(InputType.keypress)) {
				for (int key : input.pressedKeys) {
					ExecuteKeyPress(key);
				}
			}
			
			AddHoveredTiles();
			
		}

	}
	
	

	private void ExecuteKeyPress(int key) {
		//TODO: Tell the game what to do once a certain key is pressed
		
		switch (key) {
		// What comes after KeyEvent.VK_ is the actual key
		case KeyEvent.VK_0: 
			break;
		
		default:
			break;
		}
		
	}

	private void ExecuteRightPress() {
		// TODO: Tell the game what to do if the right mouse button is pressed
		
	}

	private void ExecuteLeftPress() {
		// TODO: Tell the game what to do if the left mouse button is pressed
		
	}

	private void AddHoveredTiles() {
		// TODO This is mostly if we have cursor that is meant to hover more than one tile
		//say we want to add a big building more tiles should be hovered. I would dynamically
		//change the cursor size depending on what we have equipped and then take that to read
		//what tiles are being hovered
		
	}

	public void AddInputForPanel() {
		panel.addMouseListener(input);
		panel.addMouseMotionListener(input);
		panel.addKeyListener(input);
	}

	
	
	
	
}
