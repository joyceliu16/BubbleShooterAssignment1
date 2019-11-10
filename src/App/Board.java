/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Sprites.Bubble;
import Sprites.Cannon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 *
 * @author S346795925
 */
public class Board extends JPanel implements Runnable, MouseMotionListener, MouseListener {
    private final int BUBBLE_WIDTH = 15;
    private final int BUBBLE_HEIGHT = 15;
    private final int COLUMN_LENGTH = 81;
    private final int ROW_LENGTH = 10;
    private final int CANNON_X = 580;
    private final int CANNON_Y = 500;
    private int mouseX, mouseY;
    private Thread animator;
    private List<List<Bubble>> grid = new ArrayList<>();
    private Cannon can;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addMouseMotionListener(this);
        // Add mouse click listener
        this.addMouseListener(this);
        setBackground(Color.BLACK);
        initBubble();
        try{
        initCannon();
        }catch(Exception e){
            System.out.println("No Spin");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateBubble(g);
        updateCannon(g);
    }
    
    private void initCannon() throws Exception{
        can = new Cannon(CANNON_X, CANNON_Y);
    }
    
    private void initBubble() {
        int offset;
        for (int j = 0; j < ROW_LENGTH; j++) {
            grid.add(new ArrayList<Bubble>());
            if (j % 2 == 0) {
                offset = 5;
            } else {
                offset = 0;
            }
            for (int i = 0; i < COLUMN_LENGTH; i++) {
                int colour = (int) (Math.random() * 5 + 1);
                int x = i * BUBBLE_WIDTH + offset;
                int y = j * BUBBLE_HEIGHT;
                grid.get(j).add(new Bubble(x, y, colour));
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void updateCannon(Graphics g) {
        int angle;
        try{
            //angle = (int)Math.toDegrees(Math.atan((mouseY-CANNON_Y)/(mouseX-CANNON_X)));
            angle = (int)Math.toDegrees(Math.atan2((mouseY-CANNON_Y), (mouseX-CANNON_X)));
        }catch (Exception e){
            angle = 0;
        }
        can.update(g, angle);
    }

    private void updateBubble(Graphics g) {
        for (List<Bubble> j : grid) {
            for (Bubble i : j) {
                i.update(g);
            }
        }
    }

    private void cycle() {

    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 25 - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // calculate the bubble that is shot
        System.out.println("Mouse click position: " + e.getX() + ", " + e.getY());
        shotBubble(e.getX(), e.getY());
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    /**
     * Calculate the bubble that is shot by the position of the mouse. Set visible to false
     * @param xm
     * @param xy 
     */
    private void shotBubble(int xm, int ym) {
        // cannon position is (580,580), pic is 64X64, so the center is (612, 612)
        int xc = 608; //612;
        int yc = 524; //612;
        //int shootAngle = Utils.calculateBulletAngle(xc, yc, xm, ym);

        // calculate from the bottom row
        for(int row = ROW_LENGTH -1; row >= 0; row --) {
            List<Bubble> rowBubbleList = grid.get(row);
            int yb = rowBubbleList.get(0).getY() + 15/2;
            double ratio = (double)(xm-xc)/(double)(ym-yc);
            int xb = xc + (int)(ratio*(yb-yc));
            System.out.println("X calculated is (x,y): " + xb + ", " + yb);
            for(Bubble bub: rowBubbleList) {
                // Bubble dimension is 32X32
                if(bub.isVisible())  {
                    if(xb> bub.getX() && xb < (bub.getX() + this.BUBBLE_WIDTH)) {
                        bub.setVisible(false);
                        System.out.println("hit bubble: " + bub.getX() + ", " + bub.getY());
                        // only hit the most bottom row
                        return;
                    }
                }
                    
            
            }
            
        }
        
    }
}
