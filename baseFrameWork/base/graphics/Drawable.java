package base.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

public interface Drawable {
	//this is just so that we can draw everything that is drawable in our GameLoop
	//all this interface needs is a draw method and a panel the thing should be drawn on
	
	public GamePanel.PanelType getPanelToDrawOn();
	
//	public void Draw(GamePanel gamePanel);
	public void draw(Graphics graphics);
}
