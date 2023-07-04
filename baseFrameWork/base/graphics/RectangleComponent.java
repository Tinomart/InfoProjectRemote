package base.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JComponent;

public class RectangleComponent extends JComponent {
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		//x and y for position to set the bounds later
		private int x;
		private int y;
		public int width;
		private int height;
		private Color color;
		
		public RectangleComponent(int x, int y, int width, int height, Color color) {
			this.x = x;
			this.y = y;
		    this.width = width;
		    this.height = height;
		    this.color = color;
			
			//set position with x and y and make its size given width and height
			this.setBounds(this.x, this.y, this.width, this.height);
		}

		@Override
		//when painted fill the entire component with a rectangle of its size
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        //set its color to given color
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
