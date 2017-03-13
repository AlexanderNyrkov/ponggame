package org.shurik.ponggame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * class describing Player
 * interface implements org.shurik.arkanoid.Moveable
 * interface implements org.shurik.arkanoid.Paintable
 */
public class Player implements Moveable, Paintable {
    private final Image img; // player image
    private final int width; // the width of the player
    private final int height; // height of the player
    private final int upStart; // the beginning of the top of the player
    private final int centerHeight; // center height
    private final int partHeight; // height occupied one part
    private final int centerStart; // the beginning of the central part of the player
    private final int bottomStart; // the beginning of the bottom of the player
    private final int vStop; // speed, when a player does not move
    private final int vStart; // player's speed in motion
    private int v; // player's speed
    private int x0; // left point player X axis
    private int y0; // upper point player Y-axis

    /**
     * @param x0 coordinate of the left point player
     * @param y0 coordinate of the top of the player
     * @param imgPath path to image
     */
    public Player(int x0, int y0, String imgPath) {
        this.x0 = x0; // Left point player X axis
        this.y0 = y0; // upper point player Y axis
        this.img = new ImageIcon (getClass().getResource(imgPath)).getImage(); // the image that you want to specify the path to the player's image
        this.width = img.getWidth (null); // the width of the player is equal to the width of the image
        this.height = img.getHeight (null); // height of the player is equal to the height of the image
        this.vStart = 12; // set the speed of the player in motion
        this.vStop = 0; // set speed when the player does not move
        this.centerHeight = 3; // center height when hit by the ball that must change its direction
        this.partHeight = height/2 - centerHeight + 1; // often takes the player from the top and from the bottom to the central part, in contact with which the ball must change its direction
        this.upStart = 0; // the upper part starts with the top of the player, which when released into the ball between the beginning and the end should fly off up
        this.centerStart = upStart + partHeight + 1; // the central part of the player from the end of the top, if it enters into that between the beginning and the end of the ball should fly off the center
        this.bottomStart = centerStart + centerHeight + 1; // the lower part of the player from the end of the central part, in contact with which between the beginning and the end of the ball should fly off down
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

    public int getY1() {
        return y0 + height;
    }

    public void setY1(int y1) {
        this.y0 = y1 - height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVStop() {
        return vStop;
    }

    public int getVStart() {
        return vStart;
    }

    public void setV(int v) {
        this.v = v;
    }

    /**
     * @return rectangle top of the player when hit by an object which changes its direction
     */
    public Rectangle2D upRect() {
        return new Rectangle2D.Double(x0, y0 + upStart, width, partHeight);
    }

    /**
     * @return rectangle central part player when hit by an object which changes its direction
     */
    public Rectangle2D centerRect() {
        return new Rectangle2D.Double(x0, y0 + centerStart, width, centerHeight);
    }

    /**
     * @return rectangle bottom of the player, in contact with which the object changes its direction
     */
    public Rectangle2D bottomRect() {
        return new Rectangle2D.Double(x0, y0 + bottomStart, width, partHeight);
    }

    /**
     * @return rectangle from the beginning to the end, when hit by an object that changes its direction
     */
    public Rectangle2D rect() {
        return new Rectangle2D.Double(getX0(), getY0(), width, height);
    }

    /**
     * method implements the interface main.java.org.shurik.ponggame.Paintable
     * @param g class object that allows you to work on graphics
     */
    public void paint (Graphics g) {
        g.drawImage(img, getX0(), getY0(), null); // player
    }

    /**
     * method implements the interface main.java.org.shurik.ponggame.Moveable and is responsible for the behavior of the player
     */
    public void move() {
        y0 += v; //
    }
}