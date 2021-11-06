package logic.element;

import logic.system.SysHandler;

public class Moveable extends Element{
    
    public Moveable(SysHandler handler, int identity){
        super(handler, identity);
    }


    @Override
    protected Boolean _overlapAble_(){
        return false;
    }

}
