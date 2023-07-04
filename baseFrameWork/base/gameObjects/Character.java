package base.gameObjects;

import java.awt.Color;
import java.awt.Point;

import base.graphics.RectangleComponent;
import base.graphics.Sprite;

public abstract class Character extends GameObject{
	//TODO This class is meant for living, moving beings so soldiers and the like
	//it really depends on what we want to do with them and they still need 
	//sprites working and such before we can resonably implement them
	private int maxHealth = 100;
	public int health;
	
	private Color healthBarColor = new Color(255, 0, 0, 255);
	private RectangleComponent healthBar;
	
	public int getHealth() {
		return health;
	}
	
	//setHealth should aslo immediately refelct the changes of the health on the healthbar
	public void setHealth(int health) {
		double healthChange = this.health/health;
		this.health = health;
		healthBar.width = (int) (healthBar.width * healthChange);
	}
	
	private int attackDamage;
	private int moveSpeed;
	
	public Character(Point position, Sprite sprite) {
		super(position, sprite);
		health = maxHealth;
	}
	
	
	public void update() {
		move(moveSpeed);
		attack(attackDamage);
	}


	protected abstract void attack(int attackDamage);


	protected abstract void move(int moveSpeed);
}
