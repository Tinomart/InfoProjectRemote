package base;

import java.util.HashMap;

import base.gameObjects.Character;

public class Level {
	private Character[] characters;

	public Character[] getCharacters() {
		return characters;
	}

	public void setCharacters(Character[] characters) {
		this.characters = characters;
	}
	
	private GameLoop gameLoop;
	
	private boolean endCondition;
	
	private HashMap<Class<? extends Resource>, Integer> reward;
	
	public Level(Character[] characters, boolean endCondition, HashMap<Class<? extends Resource>, Integer> reward, GameLoop gameLoop) {
		this.characters = characters;
		this.gameLoop = gameLoop;
		this.endCondition = endCondition;
		this.reward = reward;
	}
	
	public void begin() {
		for (Character character : characters) {
			gameLoop.gameObjects.add(character);
		}
	}
	
	public void end() {
		for (Character character : characters) {
			gameLoop.gameObjects.remove(character);
		}
		for (Resource resource : gameLoop.resources) {
			if(reward.get(resource.getClass()) != null) {
				resource.changeAmount(reward.get(resource.getClass()));
			}
		}
	}
	
	public void update() {
		if(endCondition) {
			end();
		}
	}
	
}
