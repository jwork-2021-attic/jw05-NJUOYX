package logic.element;

import logic.system.SysHandler;


public class Tile extends Element{
    
    public Tile(SysHandler handler, int identity){
        super(handler, identity);
    }


    @Override
    protected Boolean _overlapAble_(){
        return true;
    }

}
