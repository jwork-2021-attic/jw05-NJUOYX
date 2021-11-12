package nju.java.logic.Controler;

import nju.java.logic.element.Wall;
import nju.java.logic.system.SysHandler;

public class CWall extends Wall implements Controler {
    public CWall(SysHandler handler, int identity) {
        super(handler, identity);
    }

    @Override
    public void run() {
        handler.setVisibleOfMe(this, true);
    }
}
