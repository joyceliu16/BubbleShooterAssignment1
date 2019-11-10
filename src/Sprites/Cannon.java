/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprites;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Ash
 */
public class Cannon extends Sprite {
    private final String imageName = "src/images/cannon.png"; 
    private final BufferedImage bi;
    private AffineTransform transform;
    private AffineTransformOp op;
    
    public Cannon(int x, int y) throws Exception{      
        super(x,y);    
        super.loadImage(imageName);
        transform = new AffineTransform();
        bi = ImageIO.read(new File(imageName));
    }
    
    public void update(Graphics g, int angle){
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform rotation = transform.getRotateInstance(Math.toRadians(angle+90), this.width/2, this.height/2);
        op = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
        g2.drawImage(op.filter(bi, null), this.x, this.y, null);
    }
}
