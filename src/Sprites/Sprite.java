/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprites;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author S346795925
 */
public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

    public void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    public void update(Graphics g){
        // if bubble is not shot, show it
        if(visible) {
            g.drawImage(this.image, this.x, this.y, null);
        }
    }
   
    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
