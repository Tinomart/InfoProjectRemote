package base.graphics;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//common code for sprites
public class Sprite {

    public Point size;
    
    protected boolean visible;
    protected BufferedImage image;
    
    public void setImage(BufferedImage image) {
		this.image = image;
	}

    //constructor initiates x and y coordinates and visible variable
    public Sprite(Point size) {

        this.size = size;
        visible = true;
    }

//    protected void loadImage(String imageName) {
//
//        ImageIcon ii = new ImageIcon(imageName);
//        image = ii.getImage();
//    }
    
    public void  loadImage(String imagePath) {
        try {
            File file = new File(imagePath);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
     
    }
    
    protected void getImageDimensions() {

        size.x = image.getWidth();
        size.y = image.getHeight();
    }    

    public BufferedImage getImage() {
        return image;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}