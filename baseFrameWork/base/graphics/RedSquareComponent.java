package base.graphics;

import java.awt.*;

import javax.swing.*;

//this entire class is just for debugging the Tile System and will be removed once
//we have the entire sprite system in place
public class RedSquareComponent extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	public RedSquareComponent(int x, int y, int tileSize) {
		this.x = x*tileSize;
		this.y = y*tileSize;
		this.setBounds(this.x, this.y, tileSize, tileSize);
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 0, 0, 125));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
	
	public void removeComponent() {
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }
}
