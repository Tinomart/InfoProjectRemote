package base.graphics;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

//common code for sprites
public class Sprite {

    public Point size;
    
    protected boolean visible;
    protected Image image;

    //constructor initiates x and y coordinates and visible variable
    public Sprite(Point size) {

        this.size = size;
        visible = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    protected void getImageDimensions() {

        size.x = image.getWidth(null);
        size.y = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}