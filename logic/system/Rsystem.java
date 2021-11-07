package logic.system;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import logic.element.Element;

public class Rsystem extends JFrame implements SysHandler, KeyListener{
    
    private class Position{
        public int x;
        public int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private int rangeX;
    private int rangeY;

    private Queue<KeyEvent>press_input_queue;
    private Queue<KeyEvent>type_input_queue;

    private Map<Position, Element> spaces;

    private AsciiPanel terminal;

    public Rsystem(int rangeX, int rangeY){
        super();
        this.rangeX = rangeX;
        this.rangeY = rangeY;

        press_input_queue = new LinkedList<>();
        type_input_queue = new LinkedList<>();

        spaces = new HashMap<>();

        terminal = new AsciiPanel(rangeX,rangeY,AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint(){

        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){
        type_input_queue.add(e);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        press_input_queue.add(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public int getRangeX(){
        return this.rangeX;
    }

    @Override
    public int getRangeY(){
        return this.rangeY;
    }

    @Override
    public String getMoveInput(){
        KeyEvent e = press_input_queue.poll();
        if(e != null){
            String res;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:res = "up";
                    break;
                case KeyEvent.VK_DOWN:res = "down";
                    break;
                case KeyEvent.VK_LEFT:res = "left";
                    break;
                case KeyEvent.VK_RIGHT:res = "right";
                default:res = null;
                    break;
            }
            return res;
        }else{
            return null;
        }
    }


    /**
     * this assumes that other elements do not try to take space
     */
    @Override
    public synchronized Boolean moveTo(Element ele, int x, int y){
        Position pos = new Position(ele.getX(),ele.getY());
        assert(spaces.get(pos)!=null);
        Position npos = new Position(x, y);
        if(spaces.get(npos) == null){
            spaces.remove(pos);
            spaces.put(npos, ele);
            return true;
        }else{
            return false;
        }
    }

}
