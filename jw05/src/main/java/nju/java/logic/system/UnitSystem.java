package nju.java.logic.system;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import nju.java.logic.element.Position;
import nju.java.logic.element.Unit;
import nju.java.logic.element.UnitPosition;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UnitSystem extends JFrame implements KeyListener {

    private int rangeX;
    private int rangeY;

    private AsciiPanel terminal;

    private Queue<KeyEvent> press_input_queue;
    private Queue<KeyEvent> type_input_queue;

    private List<Unit> units;

    private List<UnitPosition> positions;

    private static UnitSystem INSTANCE = new UnitSystem(10, 10);

    private UnitSystem(int rangeX, int rangeY) {
        super();

        this.rangeX = rangeX;
        this.rangeY = rangeY;

        positions = new LinkedList<UnitPosition>();
        for (int x = 0; x < rangeX; x++) {
            for (int y = 0; y < rangeY; y++) {
                positions.add(new UnitPosition(x, y));
            }
        }

        press_input_queue = new LinkedList<>();
        type_input_queue = new LinkedList<>();

        try {
            units = new UnitCreator().getUnits();
        } catch (Exception e) {
            e.printStackTrace();
            units = new LinkedList<>();
        }

        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    public void exec() {
        units.forEach(unit -> unit.start());
        Boolean running = true;
        while(running) {
            running = false;
            Iterator<Unit> iterator = units.iterator();
            while(iterator.hasNext()) {
                if(iterator.next().isAlive()){
                    running = true;
                }
            }
        }
    }

    public static UnitSystem getInstance() {
        return INSTANCE;
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

    public Boolean tryOccupy(Position target) {
        assert (positions.contains(target));
        UnitPosition up = positions.get(positions.indexOf(target));
        return up.tryOccupy();
    }

    public void release(Position target) {
        assert (positions.contains(target));
        UnitPosition up = positions.get(positions.indexOf(target));
        up.release();
    }

    public void setVisibleOfMe(Position position, char character, Color color, Boolean on) {
        assert (positions.contains(position));
        terminal.write(character, position.x, position.y, on ? Color.white : Color.black);
        repaint();
    }
}
