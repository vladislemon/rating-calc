package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * slimon
 * 07.07.2014
 */
public class InputListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
