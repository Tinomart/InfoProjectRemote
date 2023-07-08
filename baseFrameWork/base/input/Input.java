package base.input;

import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayDeque;
import javax.swing.JPanel;

import game.Main;

//this class is the class that gets all the inputs and transforms them into more digestible Formats. 
//It gives all InputTypes to currentInput, so that we know what types of inputs are currently happening
//and stores additional information about what keys are pressed in case of key press in the pressedKeys Variable. 

public class Input implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {

	// this was not only very useful for debugging
	// but makes the code much more readable in the
	// Input Manager for when we actually want to add
	// behaviour when certain actions are performed
	public enum InputType {
		leftpress, rightpress, keypress
	}

	public JPanel panel;
	public InputManager inputManager;

	// for taking track of what types of Inputs are happening right now
	public ArrayDeque<InputType> currentInput = new ArrayDeque<InputType>();

	// to add additional behaviour based on the position
	// of the mouse in Inputmanager
	public Point mousePositionInWindow = new Point(0, 0);
	public int scrollAmount = 0;

	// this saves current clicked Input in int form because I doubt anyone
	// would execute more than 60 clicks a second, which is the
	// way that this would cause issues
	public int releasedInput;

	// stores all pressedKeys to later be used in
	// input manager
	public ArrayDeque<Integer> pressedKeys = new ArrayDeque<Integer>();

	public Input(JPanel panel) {
		this.panel = panel;
	}

	// if a keyboard key is pressed, it is added to pressed keys and
	// the input
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (!pressedKeys.contains(key)) {
			pressedKeys.add(key);
		}

		if (!currentInput.contains(InputType.keypress)) {
			currentInput.add(InputType.keypress);
		}
	}

	// if a keyboard key is released, it is removed from pressed keys and
	// the input
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		currentInput.remove(InputType.keypress);
		pressedKeys.remove(key);
		releasedInput = key;

	}

	// when the mouse moves we save its new and changed position
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePositionInWindow = e.getPoint();

		// this is here so that if we make hovering over something
		// change the color of a tile, the change happens, without
		// messing with the draw order and maybe not being visible
		panel.revalidate();
		Main.gameWindow.revalidate();
		Main.gameWindow.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// if we have somebody presses a mouse button that event is saved in our current
		// input
		int button = e.getButton();
		if (button == MouseEvent.BUTTON1) {
			currentInput.add(InputType.leftpress);
		} else if (button == MouseEvent.BUTTON3) {
			currentInput.add(InputType.rightpress);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// if we have somebody releases a mouse button that event is saved in our
		// current input
		int button = e.getButton();
		if (button == MouseEvent.BUTTON1) {
			currentInput.remove(InputType.leftpress);
		} else if (button == MouseEvent.BUTTON3) {
			currentInput.remove(InputType.rightpress);
		}
	}

	// if somebody clicks a mouse button we save it in released input
	@Override
	public void mouseClicked(MouseEvent e) {
		int button = e.getButton();
		releasedInput = button;
	}

	// unused input methods I need to implement to use the interfaces
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	//if we scroll with our mouse the value of the scroll gets saved. it is -1 if the wheel is scrolled up and +1 if it is scrolled down
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scrollWheelPos = e.getWheelRotation();
		scrollAmount = scrollWheelPos;
	}
}
