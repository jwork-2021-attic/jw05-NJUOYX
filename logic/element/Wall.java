package logic.element;

import logic.system.SysHandler;

public class Wall extends Element{
    
    public Wall(SysHandler handler, int identity){
        super(handler, identity);
    }

    @Override
    protected Boolean _overlapAble_(){
        return false;
    }

}
