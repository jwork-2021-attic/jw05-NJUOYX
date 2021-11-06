package logic.element;

import logic.system.SysHandler;

public class Moveable extends Element{
    
    public Moveable(SysHandler handler){
        super(handler);
    }

    @Override
    protected Boolean _overlapAble_(){
        return false;
    }

}
