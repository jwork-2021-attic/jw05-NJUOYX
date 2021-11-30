package nju.java.logic.system.engine;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class AsciiEngine extends JFrame implements Engine, KeyListener {
    private AsciiPanel terminal;
    private int rangeX;
    private int rangeY;
    private Queue<KeyEvent>press_input_queue;

    public AsciiEngine(){
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        rangeX = 10;
        rangeY = 10;
        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        press_input_queue = new LinkedList<>();
    }

    @Override
    public void resizeScreen(int rangeX, int rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visible) {
        terminal.write(character,x,y,visible?color:Color.BLACK);
        repaint();
    }

    @Override
    public int getRangeX() {
        return rangeX;
    }

    @Override
    public int getRangeY() {
        return rangeY;
    }

    @Override
    public String getInput() {
        KeyEvent e = press_input_queue.poll();
        if (e != null) {
            String res;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    res = "up";
                    break;
                case KeyEvent.VK_DOWN:
                    res = "down";
                    break;
                case KeyEvent.VK_LEFT:
                    res = "left";
                    break;
                case KeyEvent.VK_RIGHT:
                    res = "right";
                    break;
                case KeyEvent.VK_SPACE:
                    res = "attack";
                    break;
                default:
                    res = null;
                    break;
            }
            return res;
        } else {
            return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        press_input_queue.add(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
