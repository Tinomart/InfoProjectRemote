package base.graphics;

public interface Drawable {
	//this is just so that we can draw everything that is drawable in our GameLoop
	//all this interface needs is a draw method and a panel the thing should be drawn on
	
	public GamePanel.PanelType getPanelToDrawOn();
	
	public void Draw();
}
