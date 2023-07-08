package base.physics;

import base.graphics.RectangleComponent;

//This interface is basically here to generate common code among otherwise unrelated gameObjects like structures and characters, just to avoid duplicate code and make it more readable
public interface Damageable {
	// every damageable object needs a healthbar
	public RectangleComponent getHealthBar();

	// every damageable object needs health
	public int getHealth();

	// every damageable object needs to have its health changed
	public void setHealth(int health);

	// every damageable object needs to have its health be able to get reduced aka
	// take damage
	public void reduceHealth(int health);
}
