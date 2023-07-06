package base.gameObjects;

import java.awt.Color;
import java.awt.Point;

import base.graphics.RectangleComponent;
import base.graphics.Sprite;
import base.graphics.GamePanel.PanelType;
import base.physics.Damageable;
import game.Main;

public abstract class Character extends GameObject implements Damageable{
	//TODO This class is meant for living, moving beings so soldiers and the like
	//it really depends on what we want to do with them and they still need 
	//sprites working and such before we can resonably implement them
	
	//just like structures, characters should also have health and healthbars that dynamically change
	private int maxHealth = 100;
	public int health;
	
	private Color healthBarColor = new Color(190,25, 25, 255);
	private int healthBarWidth;
	private RectangleComponent healthBar;
	
	@Override
	public RectangleComponent getHealthBar() {
		return healthBar;
	}

	@Override
	public int getHealth() {
		return health;
	}

	// setHealth should aslo immediately refelct the changes of the health on the
	// healthbar
	@Override
	public void setHealth(int health) {
		//save how much percent of the health is already missing
		double healthPercent = (double) health / (double) maxHealth;
		this.health = health;
		//set the healthbars width to be the original width times the remaing healthPercent
		healthBar.setBounds(healthBar.getX(), healthBar.getY(), (int) (healthBarWidth * healthPercent), healthBar.getHeight());
	}

	@Override
	public void reduceHealth(int health) {
		//use setHealth, so that the healthbarchanges are immediately visible
		setHealth(this.health - health);
	}
	
	private int attackDamage;
	private int moveSpeed;
	
	public Character(Point position, Sprite sprite) {
		super(position, sprite);
		health = maxHealth;
		
		healthBarWidth = position.y - 7;
		//assign the healthbar a Rectangle Component in the middle
		this.healthBar = new RectangleComponent(position.x - sprite.size.x/2,
				healthBarWidth, 32, 5,
				healthBarColor);
		health = maxHealth;
		
	}
	
	
	public void update() {
		move(moveSpeed);
		attack(attackDamage);
		
		// as soon as a strucutre dies aka their health goes to 0 or below that the
		// strucutre will be destroyed automatically
		if (health <= 0) {
			Main.gameLoop.destroyGameObject(this);
		}
		//display healthbar as soon as the structure has taken damage
		if(health != maxHealth) {
			Main. gameLoop.panels.get(PanelType.MainPanel).add(healthBar);
		}
	}


	protected abstract void attack(int attackDamage);


	protected abstract void move(int moveSpeed);
}
