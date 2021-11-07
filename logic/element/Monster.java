package logic.element;

import logic.system.SysHandler;

public abstract class Monster extends Moveable{
    
    public Monster(SysHandler handler, int identity){
        super(handler, identity);
    }
}
