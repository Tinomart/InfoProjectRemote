package base;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import base.graphics.GamePanel;
import java.util.ArrayDeque;
import java.util.HashMap;

import base.gameObjects.GameObject;
import base.gameObjects.PureGrassTile;
import base.gameObjects.Tile;
import base.graphics.GamePanel.PanelType;
import base.graphics.GameWindow;
import base.graphics.Menu;
import base.graphics.Sprite;
import base.graphics.SpriteLoader;
import base.graphics.TileBased;
import base.graphics.TileGrid;
import game.Main;

//What this class does on a basic level is pretty obvious
//But I want this to be the basic way to edit our game as well
//I want all our basic methods to edit the game world be stored here
//so that we can access them later in the Main Class of the game package
public class GameLoop implements Runnable {

	private Thread gameThread;
	public GameWindow window;

	public TileGrid tileGrid;

	public HashMap<PanelType, GamePanel> panels;
	private GamePanel mainPanel;
	
	public ArrayDeque<GameObject> gameObjects = new ArrayDeque<GameObject>();
	private SpriteLoader spriteLoader = new SpriteLoader();

	// if we need to ever add anything based on a specific frame
	public int fpsCount;
	// the game will save every time this many seconds have passed
	// file reading and writing operations are quite resource intensive, so
	// I would suggest keeping this at the very lowest at 60 seconds;
	private int autoSaveIntervallInSeconds = 4;

	public int cameraSpeed = 4;

	public Point spawnPoint = new Point(0, 0);
	public Point cameraPosition = new Point(0, 0);

	public enum Direction {
		up, left, down, right
	}

	public GameLoop(GameWindow window) {
		this.window = window;
		panels = window.getPanels();
		mainPanel = panels.get(PanelType.MainPanel);

//		Sprite testSprite = new Sprite(new Point(50, 50));
//		testSprite.imagePath = "res/Kreis.png";
//		testSprite.loadImage(testSprite.imagePath);

//		gameObjects.add(new GameObject(new Point(12, 17), testSprite));
//		gameObjects.add(createGameObject(GameObject.class, (new Object[]{new Point(12, 17), new Sprite(new Point(50, 50))})));

		setClosingFunctionality(window);
		setMenuResizability(window);
		
		// Move our Camera to the Spawnpoint
		for (int i = 0; i < spawnPoint.x / cameraSpeed; i++) {
			moveCamera(Direction.right);
		}
		for (int j = 0; j < spawnPoint.y / cameraSpeed; j++) {
			moveCamera(Direction.down);
		}
	}
	// This had to be its own thing, because for some reason the basic resize
	// implementation was failing me, so I had to write my own.

	private void setMenuResizability(GameWindow window) {
		GameLoop gameLoop = this;

		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				for (GamePanel menu : window.getPanels().values()) {
					if (menu instanceof Menu) {
						menu.setSize(new Dimension(window.getSize()));
						menu.repaint();
						menu.revalidate();

						// this fixes a bug where the player was able to have a small window, move to
						// the edge of the screen and resize the window to the
						int outOfBoundsWidth = gameLoop.cameraPosition.x + window.getBounds().width
								- Main.MAP_WIDTH * tileGrid.tileSize;
						int outOfBoundsHeight = gameLoop.cameraPosition.y + window.getBounds().height
								- Main.MAP_HEIGHT * tileGrid.tileSize;
						if (outOfBoundsWidth > 0 || outOfBoundsHeight > 0) {
							for (int i = 0; i < (outOfBoundsWidth / Main.gameLoop.cameraSpeed) + 1; i++) {
								gameLoop.moveCamera(Direction.left);
							}
							for (int i = 0; i < (outOfBoundsHeight / Main.gameLoop.cameraSpeed) + 1; i++) {
								gameLoop.moveCamera(Direction.up);
							}
						}
					}
				}
			}
		});
	}

	// stops the gameloop and closes the program if the user exits the window

	private void setClosingFunctionality(Window window) {
		GameLoop gameLoop = this;
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gameLoop.stop();
				System.exit(0);
			}
		});

	}

	public void start() {
		// start the thread, then start the main loop
		gameThread = new Thread(this);
		run();
	}

	public void stop() {
		// stop the thread, thus stopping the main loop
		gameThread = null;
	}

	@Override
	public void run() {
		load();
		// main thread(happens very frame)
		while (gameThread != null) {
			executeEveryFrame(gameThread);

			// this is part of the fps count, that I am still unsure we need
			fpsCount += 1;
//			if(fpsCount >= 60) {
//				fpsCount = 1;
//			}

			// TODO this needs to be readded once the Draw() function in GameObject is
			// properly
			// implemented
			// DrawGameObjects();

			// The Main Menu has its inputs read;
			// requestFocus is so that the JFrame and JPanel doesnt all of the
			// sudden shift focus and stops listening to our MainPanel
			// is is possible that this is inefficient and has to be reallocated
			// later

			panels.get(PanelType.InGameGUI).requestFocus();
			panels.get(PanelType.InGameGUI).inputManager.readInputs();

			// debug code that displays the layer of every Panel that is currently a
			// component of the Window
//			Container container = window.getContentPane();
//			
//			for (Component component : container.getComponents()) {
//				if(component instanceof JPanel) {
//					GamePanel panel = (GamePanel)component;
//					System.out.println(panel.drawLayer);
//				}
//			}

			if (fpsCount >= autoSaveIntervallInSeconds * 60) {
				save();
				fpsCount = 0;
			}

			GamePanel panel = panels.get(PanelType.MainPanel);
			if (!(panel instanceof Menu)) {

				for (GameObject gameObject : gameObjects) {

					if (gameObject.getPanelToDrawOn() == PanelType.MainPanel && !(panel.addedObjects.contains(gameObject))) {

						panel.addedObjects.add(gameObject);
					}
				}

			}

			

			updateGameObjects();
//			window.revalidate();
//			window.repaint();

		}

	}

	private void updateGameObjects() {
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}

	}

	public static void executeEveryFrame(Thread thread) {

		// The time frame of one frame in nanoseconds
		// that has to pass until the loop can run again
		double loopSleepInterval = 1000000000 / Main.FPS;
		double nextLoopTime = /* This gives you the current system time in nanoseconds */System.nanoTime()
				+ loopSleepInterval;

		// Sees how long there still is till the time of the next time the run method
		// loops and turns the thread inactive until that time, to make the run loop
		// happen 60 times a second
		// code taken from this Youtube video:
		// https://www.youtube.com/watch?v=VpH33Uw-_0E&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=2&ab_channel=RyiSnow
		// At 26:36
		try {
			double timeTillNextLoop = (nextLoopTime - System.nanoTime());
			timeTillNextLoop = timeTillNextLoop / 1000000;
			Thread.sleep((long) timeTillNextLoop);

			// just for safety to make sure the thread doesnt sleep indefinitely if our pc
			// wasn't able to render the game at 60 FPS
			if (timeTillNextLoop < 0) {
				timeTillNextLoop = 0;
			}

			timeTillNextLoop = timeTillNextLoop + loopSleepInterval;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveCamera(Direction direction) {
		mainPanel = panels.get(PanelType.MainPanel);

		// if the camera is inside the Maps bounds move it in a direction with the
		// cameraSpeed
		switch (direction) {
		case up:
			if (cameraPosition.y - cameraSpeed > 0) {
				cameraPosition.y -= cameraSpeed;
			}
			break;
		case left:
			if (cameraPosition.x - cameraSpeed > 0) {
				cameraPosition.x -= cameraSpeed;
			}
			break;
		case down:
			if (cameraPosition.y + cameraSpeed + Main.gameWindow.getBounds().height < Main.MAP_HEIGHT
					* Main.TILE_SIZE) {
				cameraPosition.y += cameraSpeed;
			}
			break;
		case right:
			if (cameraPosition.x + cameraSpeed + Main.gameWindow.getBounds().width < Main.MAP_WIDTH * Main.TILE_SIZE) {
				cameraPosition.x += cameraSpeed;
			}

			break;

		}
		// set the negative location because we are moving the panel beneath the camera,
		// not the actual camera itself, which
		// means we need to reverse the cameraPosition for the panels position

		mainPanel.setLocation(-cameraPosition.x, -cameraPosition.y);
		mainPanel.revalidate();
		window.revalidate();
		window.repaint();
	}

	public void save() {
		// TODO if we update a GameObjects position and such, we need to have something
		// that saves the previous position that needs to be removed from the file, to
		// not have duplicates

		// try catch, because it is possible the file does not exist
		try {
			// readers and writers to change our file and retrieve information for it, we
			// need to know what is already in the file so that we dont accidentily
			// duplicate suff
			BufferedWriter writer = new BufferedWriter(new FileWriter("SaveData"));

			// if the file does not yet contain the gameObject we are trying to save, write
			// the gameObjects toString into the file
			for (GameObject gameObject : gameObjects) {
				String objectString = gameObject.toString();
				writer.write(objectString);
			}
			// this needs to be here because java is angry if it isn't. No clue why we need
			// it
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
//		gameObjects.add(new CityHall(new Point(4,4), Main.tileGrid));
//		System.out.println(gameObjects);
		// try catch, because it is possible the file does not exist
		try {
			// reader, because we need the information from the file
			BufferedReader reader = new BufferedReader(new FileReader("SaveData"));
			String fileString = "";
			String readLine = reader.readLine();

			// so that we can read an emtpy file and read nothing if there is nothing in the
			// file
			if (readLine != null) {
				fileString = readLine;
			} else {
				reader.close();
				return;
			}
			// all objects are seperated with spaces so we split the string of the entire
			// line to get an array of all object strings
			String[] gameObjectsStrings = fileString.split(" ");
			// create the GameObject and add it to our gameObjects, if the gameObject could
			// successfully be created = is not null
			for (String gameObjectString : gameObjectsStrings) {
				GameObject createdObject = createGameObject(gameObjectString);

			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public GameObject createGameObject(String gameObjectString) {
		try {
			// the type of the object and all arguments of each gameobjectstring are
			// seperated with "," so we split the string to get an array of all arguments
			String[] argumentsString = gameObjectString.split(",");
			// the first string in the argumentsString is always the type and not
			// technically a passed argument, so we get the Type with the forName method
			// from the string that contains the type, since we do not know what type it
			// will be we use reflection with Class<?> to determine its type at runtime
			Class<?> gameObjectClass = Class.forName(argumentsString[0]);
			// we get an array of all constructors that are associated with the Type the
			// object is supposed to be
			Constructor<?>[] gameObjectConstructors = gameObjectClass.getConstructors();
			// arrays that we will use in the future. paramterTypes will use reflections
			// again, as the contained items in the array will be of different types and
			// will vary depending on the type of object that the constructor belongs to, so
			// we cannot know what they are yet
			Class<?>[] parameterTypes;
			Object[] arguments;

			// we check all constructors and see, which one has the correct amount of
			// parameters
			for (Constructor<?> constructor : gameObjectConstructors) {
				int parameterCount = constructor.getParameterCount();
				// +1 because the first item in argumentsString is not an argument but the type
				if (parameterCount + 1 == argumentsString.length) {
					// we assign parameter types of the correct constructor and now know how many
					// arguments we have, so we assign the lenght of it
					parameterTypes = constructor.getParameterTypes();
					arguments = new Object[parameterTypes.length];

					// assign all arguments based on what their String representation is in the
					// argumentsString
					for (int i = 1; i < argumentsString.length; i++) {
						arguments[i - 1] = convertStringToArgument(argumentsString[i], parameterTypes[i - 1]);
					}
					// return a GameObject with the correct constructor and arguments
					GameObject gameObject = (GameObject) constructor.newInstance(arguments);
					initializeSprite(gameObject);
					boolean gameObjectExists = false;
					for (GameObject object : gameObjects) {
						if(object.equals(gameObject)) {
							gameObjectExists = true;
						}
					}
					if (!gameObjectExists) {
						gameObjects.add(gameObject);
					}
				}
			}
		} catch (InvocationTargetException | IllegalArgumentException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
		// if reading the file was unsuccessful the method return null
		return null;

	}

	public GameObject createGameObject(Class<? extends GameObject> type, Object[] arguments) {
		Constructor<?> constructor;
		Class<?>[] argumentTypes = new Class<?>[arguments.length];
		for (int i = 0; i < argumentTypes.length; i++) {
			argumentTypes[i] = arguments[i].getClass();
		}
		try {
			constructor = type.getConstructor(argumentTypes);
			GameObject gameObject = (GameObject) constructor.newInstance(arguments);
			initializeSprite(gameObject);
			
			boolean gameObjectExists = false;
			for (GameObject object : gameObjects) {
				if(object.equals(gameObject)) {
					gameObjectExists = true;
				}
			}
			if (!gameObjectExists) {
				gameObjects.add(gameObject);
			}
			return gameObject;
		} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void destroyGameObject(GameObject gameObjectToDestroy) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject.equals(gameObjectToDestroy)) {
				if(gameObject instanceof TileBased) {
					for (Tile tile : ((TileBased)gameObject).getTiles()) {
							tile.getSprite().setImage(null);
							tile.tileGrid.tileMap.remove(tile.getTilePosition());
							createGameObject(PureGrassTile.class, new Object[] {tile.getTilePosition(), tile.tileGrid});
							panels.get(PanelType.MainPanel).revalidate();
							window.revalidate();
					}
					
				} else {
					Sprite nullSprite = new Sprite(new Point(0, 0));
					gameObject.setSprite(nullSprite);
				}
//				gameObjects.remove(gameObject);
			} 
			window.repaint();
		}
	}
	
	public void initializeSprite(GameObject gameObject) {
		if (gameObject.getSprite().getImage() == null) {
			Sprite sprite = spriteLoader.sprites.get(gameObject.spriteType);
			if(gameObject instanceof TileBased) {
				if(((TileBased)gameObject).getMainTile().structure != null) {
					Sprite assignmentSprite = new Sprite(sprite.size);
					int width = sprite.getImage().getWidth();
			        int height = sprite.getImage().getHeight();

			        BufferedImage assignmentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			        for (int y = 0; y < height; y++) {
			            for (int x = 0; x < width; x++) {
			                int pixel = sprite.getImage().getRGB(x, y);
			                assignmentImage.setRGB(x, y, pixel);
			            }
			        }

					assignmentSprite.setImage(assignmentImage);
					gameObject.setSprite(assignmentSprite);
				} else {
					gameObject.setSprite(sprite);
				}
			} else {
				gameObject.setSprite(sprite);
			}
			
			
			
		}
	}

	// here are all the conversions for the String representations of the types into
	// the full correct Arguments based on the string information
	public Object convertStringToArgument(String string, Class<?> type) {
		if (type == int.class) {
			return Integer.parseInt(string);
		} else if (type == double.class) {
			return Double.parseDouble(string);
		} else if (type == String.class) {
			return string;
		} else if (type == Point.class) {
			// points are represented by 2 values seperated with a ";" So we split them to
			// get a String array of the two seperate values
			String[] coords = string.split(";");
			// we return a point with the values of the first and second coordinate as the
			// first and second argument of our returned point
			return new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
		} else if (type == TileGrid.class) {
			// this is pretty awkward but I couldnt find a better solution with how the
			// tiles should communicate with the rest of the code and how much access they
			// have to have
			if (tileGrid != null) {
				// if we defined a Main.tileGrid, the tilegrid will be the one in the main class
				return tileGrid;
			} else {
				// if not then we create a new one with the basic finals
				tileGrid = new TileGrid(Main.MAP_WIDTH, Main.MAP_HEIGHT, Main.TILE_SIZE, this);
				return tileGrid;
			}
		} else if (type == GameWindow.class) {
			// GameLoop has access to Main.gamewindow with the window field, because that is
			// the parameter is was given, so we just return the Main window with this
			return window;
		} else if (type == Sprite.class) {

			// points are represented by 2 values seperated with a ";" So we split them to
			// get a String array of the two seperate values
			String[] coords = string.split(";");
			// we return a point with the values of the first and second coordinate as the
			// first and second argument of our returned point
			return new Sprite(new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
		} else {
			GameObject createdGameObject = createGameObject(string);
			gameObjects.remove(createdGameObject);
			for (GameObject gameObject : gameObjects) {
				if (gameObject.equals(createdGameObject)) {
					return gameObject;
				}
			}
			return createdGameObject;
		}
	}

	// TODO: Add methods that change the game world on a basic level
	// e.g Create Map, ChangeTile, AddEnemy, AddBuilding, etc.
}
