package nju.java.logic.system;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import nju.java.logic.Controler.User;
import nju.java.logic.element.Element;

public class Rsystem extends JFrame implements SysHandler, KeyListener {

    private int rangeX;
    private int rangeY;

    private Queue<KeyEvent> press_input_queue;
    private Queue<KeyEvent> type_input_queue;

    private List<Thread> threads;

    private Map<Position, Element> spaces;

    private AsciiPanel terminal;

    public Rsystem(int rangeX, int rangeY) {
        super();
        this.rangeX = rangeX;
        this.rangeY = rangeY;

        press_input_queue = new LinkedList<>();
        type_input_queue = new LinkedList<>();

        Creator creator = new Creator();
        try {
            spaces = creator.getSpace(this);
        } catch (Exception e) {
            e.printStackTrace();
            spaces = new HashMap<>();
        }

        threads = new LinkedList<>();

        spaces.forEach((k, v) -> threads.add(new Thread(v)));

        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    public void exec() {
        threads.forEach(t -> {
            t.start();
        });
        Boolean running = true;
        while (running) {
            running = false;
            while (threads.iterator().hasNext()) {
                if (threads.iterator().next().isAlive()) {
                    running = true;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        type_input_queue.add(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        press_input_queue.add(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public int getRangeX() {
        return this.rangeX;
    }

    @Override
    public int getRangeY() {
        return this.rangeY;
    }

    @Override
    public String getMoveInput() {
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
            default:
                res = null;
                break;
            }
            return res;
        } else {
            return null;
        }
    }

    /**
     * this assumes that other elements do not try to take space
     */
    @Override
    public synchronized Boolean moveTo(Element ele, int x, int y) {
        Position pos = new Position(ele.getX(), ele.getY());
        assert (spaces.get(pos) != null);
        Position npos = new Position(x, y);
        if (spaces.get(npos) == null) {
            spaces.remove(pos);
            spaces.put(npos, ele);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setVisibleOfMe(Element ele, Boolean on) {
        Position pos = new Position(ele.getX(), ele.getY());
        assert (spaces.get(pos) != null);
        terminal.write((char) ele.identity(), pos.x, pos.y, on ? Color.white : Color.black);
        repaint();
    }

}