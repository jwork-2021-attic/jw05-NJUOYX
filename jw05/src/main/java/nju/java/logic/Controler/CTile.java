package nju.java.logic.Controler;

import nju.java.logic.element.Tile;
import nju.java.logic.system.SysHandler;

public class CTile extends Tile implements Controler {
    public CTile(SysHandler handler, int identity) {
        super(handler, identity);
    }

    @Override
    public void run() {
        handler.setVisibleOfMe(this, true);
    }
}
