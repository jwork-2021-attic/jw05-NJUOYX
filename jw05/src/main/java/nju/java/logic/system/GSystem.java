package nju.java.logic.system;

import nju.java.logic.element.Brother;
import nju.java.logic.element.Element;
import nju.java.logic.element.GameSystem;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class GSystem extends JFrame implements GameSystem, KeyListener {

    private int rangeX;
    private int rangeY;

    private AsciiPanel terminal;

    private Queue<KeyEvent> press_input_queue = new LinkedList<>();

    private List<EPosition> positions = new LinkedList<>();

    private List<Element> elements;

    private void UIInit(int rangeX, int rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    private void GameSystemInit() {
        for (int x = 0; x < rangeX; x++) {
            for (int y = 0; y < rangeY; y++) {
                positions.add(new EPosition(x, y));
            }
        }
    }

    public GSystem(int rangeX, int rangeY) {
        UIInit(rangeX, rangeY);
        GameSystemInit();
    }

    public void exec(){
        ElementCreator elementCreator = ElementCreator.getInstance();
        elements = elementCreator.getElements();
        elements.forEach(e->e.init(this));
        elements.forEach(e->e.start());

    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visiable) {
        terminal.write(character, x, y, visiable ? color : Color.BLACK);
        repaint();
    }

    @Override
    public Element tryOccupy(int x, int y, Element requester) {
        int index = positions.indexOf(new Position(x, y));
        assert (index >= 0);
        return positions.get(index).tryOccupy(requester);
    }

    @Override
    public Element exsit(int x, int y) {
        int index = positions.indexOf(new Position(x, y));
        assert (index >= 0);
        return positions.get(index).getOwner();
    }

    @Override
    public void release(int x, int y, Element element) {
        int index = positions.indexOf(new Position(x, y));
        assert (index >= 0);
        positions.get(index).release(element);
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
    public Element getBrother() {
        for(Element element: elements) {
            if(element instanceof Brother){
                return element;
            }
        }
        assert(false);
        return null;
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
