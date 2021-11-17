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
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UnitSystem extends JFrame implements KeyListener {

    private int rangeX;
    private int rangeY;

    private AsciiPanel terminal;

    private Queue<KeyEvent> press_input_queue;
    private Queue<KeyEvent> type_input_queue;

    private Unit unitWall;
    private Unit unitBrother;
    private List<Unit> unitMonsters;

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

        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    public void exec() {
        UnitCreator unitCreator = UnitCreator.getInstance();

        unitWall = unitCreator.getWall();
        unitBrother = unitCreator.getBrother();

        unitWall.prepare();
        unitBrother.prepare();

        unitWall.start();
        unitBrother.start();

        while (unitBrother.isAlive()) {
            unitMonsters = unitCreator.getMonsters();
            unitMonsters.forEach(unit->{unit.prepare();unit.start();});
            for (Unit unit : unitMonsters) {
                try {
                    unit.join();
                } catch (Exception e) {
                    e.printStackTrace();
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

    public Unit tryOccupy(Unit unit, Position target) {
        assert (positions.contains(target));
        UnitPosition up = positions.get(positions.indexOf(target));
        return up.tryOccupy(unit);
    }

    public void release(Unit unit, Position target) {
        assert (positions.contains(target));
        UnitPosition up = positions.get(positions.indexOf(target));
        up.release(unit);
    }

    public void setVisibleOfMe(Position position, char character, Color color, Boolean on) {
        assert (positions.contains(position));
        terminal.write(character, position.x, position.y, on ? color : Color.BLACK);
        repaint();
    }
}
