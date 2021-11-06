package logic.element;

import logic.system.SysHandler;

public class Wall extends Element{
    
    public Wall(SysHandler handler){
        super(handler);
    }

    @Override
    protected Boolean _overlapAble_(){
        return false;
    }
}
