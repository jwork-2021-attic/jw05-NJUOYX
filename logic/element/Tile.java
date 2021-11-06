package logic.element;

import logic.system.SysHandler;


public class Tile extends Element{
    
    public Tile(SysHandler handler){
        super(handler);
    }

    @Override
    protected Boolean _overlapAble_(){
        return true;
    }


}
