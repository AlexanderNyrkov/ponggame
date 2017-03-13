package org.shurik.ponggame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Шурик on 23.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("PongGame");
        Game game = new Game(); // create an object of the game
        game.setPreferredSize(new Dimension(game.getWidth(), game.getHeight())); // set the size of the game
        f.getContentPane().add(game); // control bar to which we add the game
        f.pack(); // sets the size of the container, which is needed to display all of the components
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the action which will take place on the closure frame (exit application)
        f.setVisible(true); // make frame visible
    }
}
