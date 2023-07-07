package base;

public abstract class Resource {
	private int amount;

	public int getAmount() {
		return amount;
	}

	public boolean changeAmount(int amount) {
		if(this.amount + amount >= 0) {
			this.amount += amount;
			return true;
		} 
		return false;
	}
	
	public Resource(int startAmount) {
		this.amount = startAmount;
	}
}
