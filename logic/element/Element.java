package logic.element;

import java.util.ResourceBundle.Control;

import logic.Controler.Controler;
import logic.system.SysHandler;

public abstract class Element {
    /*
     * ---------------->x
     * x axis points to right side
     * y axis points to down side
     * x, y start from zero
     */
    protected int x;
    protected int y;

    protected SysHandler handler;

    protected int identity;

    public Element(SysHandler handler, int identity){
        assert(handler != null);
        this.handler = handler;
        this.identity = identity;
    }

    public Boolean setX(int x) {
        if (checkX(x)) {
            this.x = x;
            return true;
        } else {
            return false;
        }
    }

    public Boolean setY(int y) {
        if (checkY(y)) {
            this.y = y;
            return true;
        } else {
            return false;
        }
    }

    public Boolean overlapAble() {
        return _overlapAble_();
    }

    public int identity(){
        return identity;
    }

    public void run(Controler controler){
        
    }

    protected Boolean checkX(int x) {
        return handler.getRangeX() > x && x >= 0;
    }

    protected Boolean checkY(int y) {
        return handler.getRangeY() > y && y >= 0;
    }

    protected abstract Boolean _overlapAble_();
}
