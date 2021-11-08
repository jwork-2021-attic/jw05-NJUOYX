package logic.system;

import logic.element.Element;

public interface SysHandler {
    int getRangeX();
    int getRangeY();

    /*
    *"up" "down" "right" "left"
    */
    String getMoveInput();
    Boolean moveTo(Element ele, int x ,int y);
    void setVisibleOfMe(Element ele, Boolean on);
}
