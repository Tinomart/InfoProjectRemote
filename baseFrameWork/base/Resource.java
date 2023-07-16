package base;

import base.graphics.GUI;

public abstract class Resource {
	private int amount;

	public int getAmount() {
		return amount;
	}

	public boolean changeAmount(int amount) {
		if(this.amount + amount >= 0) {
			this.amount += amount;
			GUI.updateResourceAmount(this);
			return true;
		} 
		
		return false;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
		GUI.updateResourceAmount(this);
	}

	public Resource(int startAmount) {
		this.amount = startAmount;
	}
}
