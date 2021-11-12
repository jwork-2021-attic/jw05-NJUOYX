package nju.java.logic.element;

import nju.java.logic.system.SysHandler;

public abstract class Moveable extends Element{
    
    public Moveable(SysHandler handler, int identity){
        super(handler, identity);
    }

    public abstract void run();

    @Override
    protected Boolean _overlapAble_(){
        return false;
    }

}
