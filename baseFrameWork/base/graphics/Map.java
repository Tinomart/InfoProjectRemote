package base.graphics;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import game.Main;

public class Map extends GamePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JScrollPane scrollPanel;

	public Map(int width, int height, int drawLayer) {
		super(width, height, drawLayer);

		// NullLayout since we want to change the position of our Map
		setLayout(null);

		// We want our map to have one strict size that is not influenced by the
		// GameWindows size so we hard set it
		setSize(getPreferredSize());

		// These are the adjustments we need to make for our ScrollPane for every Map
		// scrollPanes are basically
		// windows that show some content that is too big to display. All maps should be
		// exactly that so i had to use them here
		// They have some annoying basic visuals that interfere with our game, so I have
		// to change a lot to make it seemless
		scrollPanel = new JScrollPane(this);
		scrollPanel.setBounds(0, 0, Main.MAP_HEIGHT * Main.TILE_SIZE, Main.MAP_HEIGHT * Main.TILE_SIZE);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());

		// TODO this is just a placeholder to show how all of tile system works, even
		// though we don't have any sprites or anything working, remove once the sprites
		// are working
//		add(new RedSquareComponent(15, 15, Main.TILE_SIZE * 3));
		
		
	}

}
