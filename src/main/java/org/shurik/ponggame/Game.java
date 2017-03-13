package org.shurik.ponggame;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * class describing the game
 * extends JPanel class
 * interface implements ActionListener
 * interface implements org.shurik.ponggame.Paintable
 */
public class Game extends JPanel implements ActionListener, Paintable {
    private final Timer mainTimer; // actions to be performed with a certain interval
    private final Image img; // background image
    private final int width; // game width
    private final int height;  // game height
    private final int x0; // coordinate of the left side of the game on the X axis
    private final int y0; // coordinate of the top of the game on the Y axis
    private List<Moveable> moveableElements = new ArrayList<>(); // list of objects that will move
    private List<Paintable> paintableElements = new ArrayList<>(); // list of objects that will be painted
    private Player player1; // player 1
    private Player player2; // player 2
    private Ball ball; // ball

    public Game() {
        this.img = new ImageIcon(getClass().getResource("/game.jpg")).getImage(); // object with the image of the game
        this.width = img.getWidth(null); // width of the game becomes equal to the width of the image
        this.height = img.getHeight(null); // height of the game becomes equal to the height of the image
        this.x0 = 0; // left coordinate point of the X axis is the left side of the game - 0
        this.y0 = 0; // coordinate point on the upper Y axis is the top of the game - 0
        this.mainTimer = new Timer(20, this); // set the interval perform actions

        this.ball = new Ball(0, 0, "/ball.png"); // object ball with the image
        this.ball.setX0(width / 2); // position of the ball on the X axis becomes the middle of the game
        this.ball.setY0(height / 2 - ball.getDiameter() / 2); // position of the ball on the axis Y becomes the middle of the game

        this.player1 = new Player(0, 0, "/player1.png"); // player1 with the image of the object
        this.player1.setX0(getX0()); // player1 position in the X axis becomes equal to the left side of the game
        this.player1.setY0(height / 2 - player1.getHeight() / 2); // player1 position to Y axis mid-game becomes

        this.player2 = new Player(0, 0, "/player2.png"); // player2 with the image of the object
        this.player2.setX0(width - player2.getWidth()); // player2 position on the x-axis is equal to the right part of the game
        this.player2.setY0(height / 2 - player2.getHeight() / 2); // player2 position on the x-axis is equal to the right part of the game

        paintableElements.add(player1); // add player1 in the list, then to draw it
        paintableElements.add(player2); // add player2 in the list, then to draw it
        paintableElements.add(ball); // adds the ball on the list, then to draw it
        moveableElements.add(player1); // add player1 in the list, then to set it in motion
        moveableElements.add(player2); // add player2 in the list, then to set it in motion
        moveableElements.add(ball); // adds the ball on the list, then to set it in motion
        mainTimer.start (); // starts the timer
        addKeyListener (new MyKeyAdapter ()); // handle keyboard events
        setFocusable (true); // set the focused state of the component
    }

    public int getX0() {
        return x0;
    }

    public int getY0() {
        return y0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * method implements the interface main.java.org.shurik.ponggame.Paintable
     * @param g class object that allows you to work on graphics
     */
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null); // background
        for(Paintable p : paintableElements) {
            p.paint(g); // draws elements paintableElements list
        }
    }

    /**
     * method for determining the committed action
     * @param e event handler
     */
    public void actionPerformed(ActionEvent e) {
        move(); // launches method move
        repaint(); // redraw
    }

    /**
     * method is responsible for the movement
     */
    public void move() {
        for(Moveable m : moveableElements) {
            m.move();
        }

        if (ball.getY1() >= this.height) {
            ball.setDy(-ball.getV()); // if the ball touches the top of the game will bounce and fly up
        }
        if (ball.getY0() <= this.getY0()) {
            ball.setDy(ball.getV()); // if the ball touches the bottom of the game bounce off and fly down
        }

        // if the player touches the bottom border of the game, then stops
        if (player1.getY1()>= this.height) {
            player1.setY1(this.height);
        }
        // if the player 1 touches the top border of the game, then stops
        if (player1.getY0() <= this.getY0()) {
            player1.setY0(this.getY0());
        }

        if (player2.getY1()>= this.height) {
            player2.setY1(this.height);
        }
        // if the player 2 touches the top border of the game, then stops
        if (player2.getY0() <= this.getY0()) {
            player2.setY0(this.getY0());
        }

        testCollisionWithPlayers(); // check and ball touch the player

         /*
       if the ball touches the edge of the game to the left or to the right, then the game is over
         */
        if (ball.getX0() <= this.getX0() || ball.getX1() >= this.width) {
            System.exit(0);
        }
    }

    /**
     * method is responsible for the calculations in the collision of the ball with the players
     */
    public void testCollisionWithPlayers() {
        if (player1.rect().getBounds2D().intersects(ball.ellipse().getBounds2D())) {
            ball.setDx(ball.getV()); // if the ball collided with the player 1, it flies right
            if (player1.upRect().getBounds2D().intersects(ball.ellipse().getBounds2D())){
                ball.upDy(); // the collision of the ball with the top of the player 1, the ball will fly up
            } else if (player1.centerRect().getBounds2D().intersects(ball.ellipse().getBounds2D())) {
                ball.centerDy(); // the collision of the ball with the center of the player 1, the ball will fly in the middle
            } else if(player1.bottomRect().getBounds2D().intersects(ball.ellipse().getBounds2D())) {
                ball.bottomDy(); // the collision of the ball with the bottom of the player 1, ball flight down
            }
        }
        if (player2.rect().getBounds2D().intersects(ball.ellipse().getBounds2D())) {
            ball.setDx(-ball.getV()); // if the ball collided with the player 1, it flies right
            if (player2.upRect().getBounds2D().intersects(ball.ellipse().getBounds2D())){
                ball.upDy(); // the collision of the ball with the top of the player 1, the ball will fly up
            } else if (player2.centerRect().getBounds2D().intersects(ball.ellipse().getBounds2D())) {
                ball.centerDy(); // the collision of the ball with the center of the player 1, the ball will fly in the middle
            } else if(player2.bottomRect().getBounds2D().intersects(ball.ellipse().getBounds2D())) {
                ball.bottomDy(); // the collision of the ball with the bottom of the player 1, ball flight down
            }
        }
    }

    /**
     * a class that extends from the abstract class adapter in order to get the keyboard events
     */
    private class MyKeyAdapter extends KeyAdapter {
        /**
         * method is responsible for the actions when keys are pressed
         * @param e contains all the necessary information about the occurred event
         */
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode(); // recognizes the key code that was pressed
            /*
             if the user clicks on the "S" button, the player 1 starts to move down
             else if the user clicks on the "DOWN" button, the player 2 starts to move down
              */
            if (key == KeyEvent.VK_S) {
                player1.setV(player1.getVStart());
            } else if (key == KeyEvent.VK_DOWN) {
                player2.setV(player2.getVStart());
            }
            /*
            if the user clicks on the "W" button, the player 1 starts to move up
            else if the user clicks on the "UP" button, the player 2 starts to move up
             */
            if (key == KeyEvent.VK_W) {
                player1.setV(-player1.getVStart());
            } else if (key == KeyEvent.VK_UP) {
                player2.setV(-player2.getVStart());
            }
        }

        /**
         * method is responsible for the action, until the button is released
         * @param e contains all the necessary information about the occurred event
         */
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            // if the key "W" and / or "S" is released, the player does not move
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
                player1.setV(player1.getVStop());
            } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                player2.setV(player2.getVStop());
            }
        }
    }
}