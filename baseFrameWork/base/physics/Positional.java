package base.physics;

import java.awt.Point;

//Class to make all Objects that actually have a position be able to access that position
//I often run into issues of not getting proper position data in engines
//I want there to be one position solution and one only used for everything
public interface Positional {
	public enum Corner {
		topleft, topright, bottomleft, bottomright

	}

	// get the position of one of the characters corners depending on the size of
	// the positional object
	public Point getPosition(Corner corner);

	// by default you get the position of the top left corner
	public default Point GetPosition() {
		return getPosition(Corner.topleft);
	}

}
