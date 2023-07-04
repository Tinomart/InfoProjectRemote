package base.graphics;

import java.awt.*;

import javax.swing.*;

//this entire class is just for debugging the Tile System and will be removed once
//we have the entire sprite system in place
public class SquareComponent extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//x and y for position to set the bounds later
	private int x;
	private int y;
	public Color color;
	
	public SquareComponent(int x, int y, int tileSize, Color color) {
		this.x = x*tileSize;
		this.y = y*tileSize;
		this.color = color;
		
		//set position with x and y and make its size tileSize*tileSize
		this.setBounds(this.x, this.y, tileSize, tileSize);
	}

	@Override
	//when painted fill the entire component with a rectangle of its size
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        
    }
	
	//just using standard remove sometimes caused errors when panel switching
	//so I just included a conditional to only remove if its parent exists
	public void removeComponent() {
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }
}
