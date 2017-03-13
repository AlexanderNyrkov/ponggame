package org.shurik.ponggame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * class describing a ball
 * interface implements org.shurik.ponggame.Moveable
 * interface implements org.shurik.ponggame.Paintable
 */
public class Ball implements Moveable, Paintable {
    private final Image img; // ball image
    private final int diameter; // ball diameter
    private int v; // ball speed
    private int dx; // the direction of movement of the ball along the X axis
    private int dy; // movement direction of the ball on the Y axis
    private int x0; // variable declaration, are the coordinates of the left side of the ball on the X axis
    private int y0; // variable declaration, are the coordinates of the top of the ball on the Y axis

    /**
     * @param imgPath path to the image
     * @param x0 left point of the ball on the X axis
     * @param y0 highest point of the ball on the Y axis
     */
    public Ball(int x0, int y0, String imgPath) {
        this.img = new ImageIcon (getClass().getResource(imgPath)).getImage(); // create the image, pointing his way
        this.x0 = x0; // left ball point on the X axis
        this.y0 = y0; // upper ball point on the Y axis
        this.diameter = img.getWidth(null); // diameter equal to the width of the image
        this.v = 9; // set the speed of the ball
        this.dx = v; // direction of the ball on the Y axis, the positive value of the ball flies to the right, with a negative - left
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getX1() {
        return x0 + diameter;
    }

    public int getY1() {
        return y0 + diameter;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getV() {
        return v;
    }

    /**
     * method implements the interface main.java.org.shurik.ponggame.Paintable
     * @param g class object that allows you to work on graphics
     */
    public void paint(Graphics g) {
        g.drawImage(img, x0, y0, null);
    }

    /**
     * @return ball ellipse
     */
    public Ellipse2D ellipse() {
        return new Ellipse2D.Double(x0, y0, diameter, diameter);
    }

    /**
     * method is responsible for the behavior of the ball when hit in the upper part of the player
     */
    public void upDy() {
        this.dy = -v;
    }

    /**
     * method is responsible for the behavior of the ball when hit in the central part of the player
     */
    public void centerDy() {
        this.dy = 0;
    }

    /**
     * method is responsible for the behavior of the ball when hit in the bottom of the player
     */
    public void bottomDy() {
        this.dy = v;
    }

    /**
     * method implements the interface main.java.org.shurik.ponggame.Moveable and is responsible for the behavior of the ball
     */
    public void move() {
        if (dx == v) {
            x0 += dx; // the ball flies right
            y0 += dy; // the ball flies up or down (or center), depending on the last ball of collision
        } else {
            x0 += dx; // the ball flies left
            y0 += dy; // the ball flies up or down (or center), depending on the last ball of collision
        }
    }
}