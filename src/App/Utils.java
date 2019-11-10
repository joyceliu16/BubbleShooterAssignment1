/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

/**
 *
 * @author Joyce
 */
public class Utils {
        // this angle should be from 0 to 180 degree, the 0 is the right position of the shooter 
    public static int calculateBulletAngle(int shooterX, int shooterY, int mX, int mY) {
        // TODO, this needs to be double checked
        int angle = (int)(Math.toDegrees(Math.atan2((shooterY - mY), (mX - shooterX))));
        System.out.println(" bullet angle is: " + angle);
        return angle;
    }

    
}
