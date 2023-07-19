package base;

import java.util.HashMap;

import base.gameObjects.Character;
import base.graphics.GamePanel.PanelType;

import java.util.*;

//each level is a class with an array of characters the player needs to fight, 
//a condition to win a reward and the game (here gameLoop) of which it is a part of

public class Level {

	// to save every enemy the level has
	private ArrayList<Character> characters;

	public ArrayList<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<Character> characters) {
		this.characters = characters;
	}

	private GameLoop gameLoop;

	// not every level necessarily gives equal amounts of resources, so each
	// resource that will be given has an according integer as a reward
	private HashMap<Class<? extends Resource>, Integer> reward;

	public Level(ArrayList<Character> characters, HashMap<Class<? extends Resource>, Integer> reward,
			GameLoop gameLoop) {
		this.characters = characters;
		this.gameLoop = gameLoop;
		this.reward = reward;
		initializeCharacters();
	}

	private void initializeCharacters() {
		for (Character character : characters) {
			gameLoop.initializeSprite(character);
			character.setGameLoop(gameLoop);
		}
		
	}

	// when the level begins all gameObjects
	public void begin() {
		for (Character character : characters) {
			gameLoop.gameObjects.add(character);
		}
	}

	public void end() {
		// once the game ends destroy all characters for safety if the goal is not to
		// destroy all enemies, then it destroys all still existing enemies
		for (Character character : characters) {
			gameLoop.destroyGameObject(character);
			gameLoop.panels.get(PanelType.MainPanel).remove(character.getHealthBar());
		}

		// for each resource check if the rewards contains that resource and if it does
		// add the amount to the resource
		for (Resource resource : gameLoop.resources) {
			if (reward.get(resource.getClass()) != null) {
				resource.changeAmount(reward.get(resource.getClass()));
			}
		}
	}

	//if the endcondition is fullfilled, automatically begin the next wave
	public void update() {
		if (gameLoop.allEnemiesDefeated(characters)) {
			gameLoop.setCombatPhase(false);
			gameLoop.beginNextWave();
		}
	}

}
