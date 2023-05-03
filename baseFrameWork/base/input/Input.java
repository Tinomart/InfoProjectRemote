package base.input;

import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayDeque;
import javax.swing.JPanel;

//this class is the class that gets all the inputs and transforms them into more digestible Formats. 
//It gives all InputTypes to currentInput, so that we know what types of inputs are currently happening
//and stores additional information about what keys are pressed in case of key press in the pressedKeys Variable. 

public class Input implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {
	
	//this was not only very useful for debugging
	//but makes the code much more readable in the 
	//Input Manager for when we actually want to add
	//behaviour when certain actions are performed
	public enum InputType {
		leftpress, rightpress, keypress,
	}

	public JPanel panel;

	//for taking track of what types of Inputs are happening right now
	public ArrayDeque<InputType> currentInput = new ArrayDeque<InputType>();
	

	//to add additional behaviour based on the position
	//of the mouse in Inputmanager
	public Point mousePositionOnPanel;

	//stores all pressedKeys to later be used in 
	//input manager
	public ArrayDeque<Integer> pressedKeys = new ArrayDeque<Integer>();

	public Input(JPanel panel) {
		this.panel = panel;
	}

	//if a keyboard key is pressed, it is added to pressed keys and
	//the input 
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		pressedKeys.add(key);
		if(!currentInput.contains(InputType.keypress)) {
		 currentInput.add(InputType.keypress);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		currentInput.remove(InputType.keypress);
		pressedKeys.remove(key);
		
	}

	

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePositionOnPanel = e.getPoint();
		
		//this is here so that if we make hovering over something
		//change the color of a tile, the change happens, without
		//messing with the draw order and maybe not being visible
		panel.repaint();
	}

	
	

	@Override
	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		if (button == MouseEvent.BUTTON1) {
			currentInput.add(InputType.leftpress);
		} else if (button == MouseEvent.BUTTON3) {
			currentInput.add(InputType.rightpress);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int button = e.getButton();
		if (button == MouseEvent.BUTTON1) {
			currentInput.remove(InputType.leftpress);
		} else if (button == MouseEvent.BUTTON3) {
			currentInput.remove(InputType.rightpress);
		}
	}

	//unused input methods I need to implement to use the interfaces
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}

	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO potential if we want to add a zoom with the mouse wheel or something
		
	}
}
