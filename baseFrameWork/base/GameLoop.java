package base;

import java.awt.*;

import java.util.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import base.graphics.GUI;
import base.graphics.GamePanel;
import base.gameObjects.GameObject;
import base.gameObjects.PureGrassTile;
import base.gameObjects.Spell;
import base.gameObjects.Temple;
import base.gameObjects.Tile;
import base.gameObjects.Watchtower;
import base.gameObjects.Enemy_1;
import base.gameObjects.Character;
import base.gameObjects.CityHall;
import base.graphics.GamePanel.PanelType;
import base.graphics.GameWindow;
import base.graphics.Menu;
import base.graphics.Sprite;
import base.graphics.SpriteLoader;
import base.graphics.TileBased;
import base.graphics.TileGrid;
import base.physics.Damageable;
import game.Main;

//What this class does on a basic level is pretty obvious
//But I want this to be the basic way to edit our game as well
//I want all our basic methods to edit the game world be stored here
//so that we can access them later in the Main Class of the game package
public class GameLoop implements Runnable {

	public Thread gameThread;
	public GameWindow window;

	public TileGrid tileGrid;

	public HashMap<PanelType, GamePanel> panels;
	private GamePanel mainPanel;
	private JPanel gameOverPanel = new JPanel(new BorderLayout());

	public ArrayDeque<GameObject> gameObjects = new ArrayDeque<GameObject>();
	public ArrayDeque<GameObject> gameObjectsToRemove = new ArrayDeque<GameObject>();
	public int currentWaveCount;
	public ArrayList<Level> waves = new ArrayList<Level>();

	// this is public even though it has getters and setters, because we wanna
	// access it without using set once, without having the specific code that
	// otherwise should happen everytime we set it
	public boolean combatPhase = false;
	private boolean paused = true;
	private boolean gameOver = false;

	public boolean getGameOver() {
		return gameOver;
	}

	// method for displaying Game Over
	public void setGameOver(boolean gameOver) {
		GamePanel panel = panels.get(PanelType.InGameGUI);
		if (gameOver = true) {

			gameOverPanel.setOpaque(false);

			// creating the Game-Over-Label with custom font
			JLabel gameOverLabel = new JLabel("Game Over");
			gameOverLabel.setFont(GUI.pythia);
			gameOverLabel.setForeground(Color.RED);

			// creating the Panel where the Label will be placed
			JPanel centerGameOverPanel = new JPanel();
			centerGameOverPanel.setOpaque(false);
			centerGameOverPanel.add(gameOverLabel, BorderLayout.CENTER);

			// adding the centerGameOverPanel to the gameOverPanel and putting it to the
			// center
			gameOverPanel.add(centerGameOverPanel, BorderLayout.CENTER);

			// adding the gameOverPanel to the normal panel
			panel.add(gameOverPanel, BorderLayout.CENTER);

			// revalidating and repainting to reflect changes
			panel.revalidate();
			window.revalidate();
			window.repaint();
		} else {
			// remove the panel again, so that if we plan to continue the game, we dont
			// accidentally have the game over text still visible
			panel.remove(gameOverPanel);
		}
		this.gameOver = gameOver;
	}

	private boolean gameHasLoaded = false;

	public boolean hasGameLoaded() {
		return gameHasLoaded;
	}

	public void setGameLoaded(boolean gameHasLoaded) {
		this.gameHasLoaded = gameHasLoaded;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isCombatPhase() {
		return combatPhase;
	}

	// each time the combat phase is set certain things should apply, so this is
	// longer than usual
	public void setCombatPhase(boolean combatPhase) {
		save();
		if (combatPhase) {
			// if we can switch to the next wave, because we havent passed the final wave,
			// simply start the next wave
			if (currentWaveCount > waves.size()) {
				waves.get(currentWaveCount).begin();
			} else {
				// if we are past the final wave create a new bonus wave and begin it
				addBonusWave();
				waves.get(currentWaveCount).begin();
			}

		} else {
			// if we are exiting the combat phase we need to gather all resource from our
			// resource generating gameObjects
			for (GameObject gameObject : gameObjects) {
				if (gameObject instanceof ResourceGenerating) {
					((ResourceGenerating) gameObject).generateResources(resources);
				}
			}
			// end the current wave
			waves.get(currentWaveCount).end();
		}
		// execute normal setter code
		this.combatPhase = combatPhase;
	}

	public CityHall cityHall;

	private SpriteLoader spriteLoader = new SpriteLoader();

	public SpriteLoader getSpriteLoader() {
		return spriteLoader;
	}

	private int cameraSpeed = 7;

	private Point spawnPoint = new Point(0, 0);
	public Point cameraPosition = new Point(0, 0);

	public Resource[] resources = new Resource[] { new Gold(20), new Faith(0) };

	// relevant for spellcasting we can only cast spells, if this is true, we may
	// cast spells
	private boolean templeExist = false;

	public boolean doesTempleExist() {
		return templeExist;
	}

	public void setTempleExist(boolean templeExist) {
		this.templeExist = templeExist;
	}

	private boolean spellSelected = false;

	public boolean isSpellSelected() {
		return spellSelected;
	}

	public void setSpellSelected(boolean spellSelected) {
		this.spellSelected = spellSelected;
	}

	public enum Direction {
		up, left, down, right
	}

	public GameLoop(GameWindow window) {
		this.window = window;
		panels = window.getPanels();
		mainPanel = panels.get(PanelType.MainPanel);

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

	// this is a check used to determine whether the current active wave is over,
	// which happens when all of the enemies are defeated
	public boolean allEnemiesDefeated(ArrayList<Character> level1Enemies) {
		boolean anyEnemyContained = false;
		if (combatPhase) {
			anyEnemyContained = true;
			// if any enemy is still in gameObjects, then set any enemy contained
			for (Character character : level1Enemies) {
				if (gameObjects.contains(character)) {
					anyEnemyContained = false;
				}
			}
		}
		// enemy contained determines whether all enemies are defeated so return it
		return anyEnemyContained;
	}

	// this creates a new level with more enemies than the last wave and adds it to
	// waves, all enemies are completely random, so low quality
	private void addBonusWave() {
		ArrayList<Character> bonusEnemies = new ArrayList<Character>();
		// add an enemy for each previous enemy in our previous wave
		for (int i = 0; i < waves.get(currentWaveCount - 1).getCharacters().size(); i++) {
			bonusEnemies.add(new Enemy_1(new Point((int) (Math.random() * window.getWidth()),
					(int) (Math.random() * panels.get(PanelType.MainPanel).getHeight()))));
		}
		// add three random bonus enemies on top of that
		for (int i = 0; i < 3; i++) {
			bonusEnemies.add(new Enemy_1(
					new Point((int) (Math.random() * window.getWidth()), (int) (Math.random() * window.getHeight()))));
		}
		Level bonusLevel = new Level(bonusEnemies, new HashMap<Class<? extends Resource>, Integer>(), this);
		waves.add(bonusLevel);
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
						// the edge of the screen and resize the window to a larger size, thus get out
						// of bounds
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
		save();
		gameThread = null;
	}

	@Override
	public void run() {
		// main thread(happens very frame)
		while (gameThread != null) {
			executeEveryFrame(gameThread);

			gameObjectsToRemove.clear();

			// The Main Menu has its inputs read;
			// requestFocus is so that the JFrame and JPanel doesnt all of the
			// sudden shift focus and stops listening to our MainPanel
			// is is possible that this is inefficient and has to be reallocated
			// later

			panels.get(PanelType.InGameGUI).requestFocus();
			panels.get(PanelType.InGameGUI).inputManager.readInputs();

			if (gameHasLoaded) {

				GamePanel panel = panels.get(PanelType.MainPanel);
				

				// communicate with the mainpanel and make it know all different gameObjects
				// that should be drawn on it by adding them to addedObjects
				Iterator<GameObject> iterator = gameObjects.iterator();
				while (iterator.hasNext()) {
					GameObject gameObject = iterator.next();
					if (gameObject.getPanelToDrawOn() == PanelType.MainPanel
							&& !(panel.addedObjects.contains(gameObject))) {

						panel.addedObjects.add(gameObject);
					}
				}
				
				// add all gameObjects that are in added objects, but not in gameObjects
				// anymore, and add them to the objects that need to be removed later in
				// gamePanel.drawComponent, again to avoid the ungodly amount of concurrent
				// modification exceptions
				iterator = panel.addedObjects.iterator();
				while (iterator.hasNext()) {
					GameObject gameObject = iterator.next();
					if (!Main.gameLoop.gameObjects.contains(gameObject)) {
						panel.removedObjects.add(gameObject);
					}
				}
				
				// remove all object that have been removed, so that we dont cause any
				// ConcurrentModification exception
				iterator = panel.removedObjects.iterator();
				while (iterator.hasNext()) {
					GameObject gameObject = iterator.next();
					panel.addedObjects.remove(gameObject);
				}

				// only update the game if the game is not paused
				if (!paused) {
					updateGame();
				}
			}
			// all iterations are done, so now we actually remove all gameObjects, all to
			// fix the ungodly amount of concurrent modification exceptions I was dealing
			// with constantly
			for (GameObject gameObject : gameObjectsToRemove) {
				gameObjects.remove(gameObject);
			}

		}

	}

	// update the wave to check if it has been completed and update all gameObjects
	private void updateGame() {
		waves.get(currentWaveCount).update();
		updateGameObjects();
	}

	// call the update method of each gameObject to make pretty much every part of
	// the individual logic possible, like movement, damage checks, etc.
	private void updateGameObjects() {
		Iterator<GameObject> iterator = gameObjects.iterator();
		while (iterator.hasNext()) {
			GameObject gameObject = iterator.next();
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

	// basic method to enable the camera control for the player, to see the
	// different parts of the map
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

	public void beginNextWave() {
		currentWaveCount += 1;
	}

	public void save() {
		// try catch, because it is possible the file does not exist
		try {
			// readers and writers to change our file and retrieve information for it, we
			// need to know what is already in the file so that we dont accidentily
			// duplicate suff
			BufferedWriter writer = new BufferedWriter(new FileWriter("SaveData"));

			// if the file does not yet contain the gameObject we are trying to save, write
			// the gameObjects toString into the file
			for (GameObject gameObject : gameObjects) {
				if (!(waves.get(currentWaveCount).getCharacters().contains(gameObject) || gameObject instanceof Spell)) {
					String objectString = gameObject.toString();
					writer.write(objectString);
				}
			}

			// Write currentWaveCount to the save call in the next line
			writer.newLine();
			writer.write("" + currentWaveCount);

			// Save the amount of resources
			writer.newLine();
			for (Resource resource : resources) {
				writer.write("" + resource.getAmount() + " ");
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(String save) {
		// try catch, because it is possible the file does not exist
		try {
			// reader, because we need the information from the file
			BufferedReader reader = new BufferedReader(new FileReader(save));
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
			// create the GameObject, which adds it to our gameObjects
			for (String gameObjectString : gameObjectsStrings) {
				createGameObject(gameObjectString);

			}

			readLine = reader.readLine();
			currentWaveCount = Integer.valueOf(readLine);

			// same setup as before this time for
			readLine = reader.readLine();
			if (readLine != null) {
				fileString = readLine;
			} else {
				reader.close();
				return;
			}
			// all objects are seperated with spaces so we split the string of the entire
			// line to get an array of all object strings
			String[] resourceString = fileString.split(" ");
			for (int i = 0; i < resourceString.length; i++) {
				int resourceAmount = Integer.parseInt(resourceString[i]);
				resources[i].setAmount(resourceAmount);
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		gameHasLoaded = true;

	}

	// the method that returns a gameObject based on a string, very important to
	// make reading of the save file possible. it also add the gameObject to our
	// gameObjects, which for the context of our game means physically existing in
	// our game, if it does not already exist, to avoid duplicates
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
					// initialing the sprite to finish up all the setup for a complete drawable
					// gameObject
					initializeSprite(gameObject);
					boolean gameObjectExists = false;
					// check if the gameObject already exist by iterating through gameObjects and
					// using the equals method, which is defined for all gameObjects and checks
					// their position, size and type
					for (GameObject object : gameObjects) {
						if (object.equals(gameObject)) {
							gameObjectExists = true;
						}
					}
					// if the gameObject doesnt already exist add it to gameObjects
					if (!gameObjectExists) {
						gameObjects.add(gameObject);
					}
				}
			}
		} catch (InvocationTargetException | IllegalArgumentException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
		// if reading the string was unsuccessful the method return null
		return null;

	}

	// overloaded createGameObject method, to allow creation of a gameObject based
	// on a given type and an array of the different arguments
	public GameObject createGameObject(Class<? extends GameObject> type, Object[] arguments) {
		// code to deterime all the different types of arguments to see their type for
		// creating an object with the constructor of the given types
		Constructor<?> constructor;
		Class<?>[] argumentTypes = new Class<?>[arguments.length];
		for (int i = 0; i < argumentTypes.length; i++) {
			argumentTypes[i] = arguments[i].getClass();
		}
		try {
			// similar to earlier method, searching for the constructor and finishing up our
			// object with initialize sprite
			constructor = type.getConstructor(argumentTypes);
			GameObject gameObject = (GameObject) constructor.newInstance(arguments);
			initializeSprite(gameObject);

			// same code as first method for checking if the gameObejct already exists to
			// avoid duplicates
			boolean gameObjectExists = false;
			for (GameObject object : gameObjects) {
				if (object.equals(gameObject)) {
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
		// if trying to find the constructor and class was unsuccessful the method
		// returns null
		return null;
	}

	// opposite operation to our createGameObject method.
	public void destroyGameObject(GameObject gameObjectToDestroy) {
		// iterators to avoid concurrent modification exceptions, as we are actively
		// altering our data structure
		// we iterate through gameObjects to search for our gameObject to see if we can
		// even destroy the gameObject
		Iterator<GameObject> iterator = gameObjects.iterator();
		while (iterator.hasNext()) {
			GameObject gameObject = iterator.next();
			if (gameObject.equals(gameObjectToDestroy)) {
				// we have found a gameObject we want to destroy, if it is tilebased set the
				// images of all tiles to null and replace them with a pure grass tile,
				// essentially removing all visual traces from our panel, that would remain if
				// we didnt do that
				if (gameObject instanceof TileBased) {
					for (Tile tile : ((TileBased) gameObject).getTiles()) {
						createGameObject(PureGrassTile.class, new Object[] { tile.getTilePosition(), tile.tileGrid });
					}
				}
				if (gameObject instanceof Damageable) {
					panels.get(PanelType.MainPanel).remove(((Damageable) gameObject).getHealthBar());
				}
				if (gameObject instanceof Temple) {
					templeExist = false;
				}
				// remove the gameObject out of gameObjects, essentially making it non existant
				// for the scope of our game
				gameObjectsToRemove.add(gameObject);
			}

		}

		// set the cityHall to null, this is an important step for checks concerning if
		// the game is over or not
		if (gameObjectToDestroy == cityHall) {
			cityHall = null;
		}

	}

	// method to make our entire sprite system somewhat efficient. since we have so
	// many tiles making each of them have their own image loaded would take ages,
	// so we reuse if we dont intend to interactig with it and duplicate it if we
	// do, since file loading operations are so expensive
	public void initializeSprite(GameObject gameObject) {
		// only initialize if no image exists yet
		if (gameObject.getSprite().getImage() == null) {
			// load sprite from gameObject.spriteType, this is the reuse of same sprites to
			// make sure only one loading for each sprite happens
			Sprite sprite = spriteLoader.sprites.get(gameObject.spriteType);
			if (gameObject instanceof TileBased) {
				if (((TileBased) gameObject).getMainTile().structure != null) {
					// regular tiles that are not part of structure dont need to be destroyable, so
					// they should just be able to share sprites, which is much more efficient and
					// needed considering most of our gameObjects are just plain tiles
					gameObject.setSprite(duplicateSprite(sprite));
				} else {
					// if it is part of a structure aka the structure variable is not null, create a
//					// duplicate sprite and assign, it. This because these tiles should be able to
//					// get destroyed and altered, which requires them to have their own sprite, as to not affect
//					// other gameObject that would have the same object reference assigned to their
//					// sprite variable
					gameObject.setSprite(sprite);
				}
			} else {
				// all other gameObjects should probably be destroyable, so they have to have
				// their own duplicate for the same reason as given above
				gameObject.setSprite(duplicateSprite(sprite));
			}

		}
	}

	// method that returns a new sprite with the same information as the given
	// sprite
	private Sprite duplicateSprite(Sprite sprite) {
		// create new sprite of the same size as the old one
		Sprite duplicateSprite = new Sprite(sprite.size);

		// save image width and height
		int width = sprite.getImage().getWidth();
		int height = sprite.getImage().getHeight();

		// create new Image that should newly be assigned of the same width and height
		// as the originial
		BufferedImage assignmentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// iterate through each pixel of the original and set the new Images RGB value
		// to the same as the originals pix
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = sprite.getImage().getRGB(x, y);
				assignmentImage.setRGB(x, y, pixel);
			}
		}

		// assign the new image to our new sprite
		duplicateSprite.setImage(assignmentImage);
		// return the now complete duplicate
		return duplicateSprite;
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
			// if a gameObject has another gameObject as a parameter, just create that
			// object and check if it already exists, and if it does return the existing
			// object instead, else add the created object to the gameObject and return it
			// instead
			GameObject createdGameObject = createGameObject(string);
			gameObjects.remove(createdGameObject);
			for (GameObject gameObject : gameObjects) {
				if (gameObject.equals(createdGameObject)) {
					return gameObject;
				}
			}

			// removing the objects directly causes massive current modification we mark the
			// object as removable to have it removed later after all iterations are
			// finished
			gameObjects.add(createdGameObject);
			return createdGameObject;
		}
	}

}
