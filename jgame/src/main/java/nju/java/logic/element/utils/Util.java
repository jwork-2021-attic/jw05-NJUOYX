package nju.java.logic.element.utils;

import nju.java.logic.element.GAPI;
import nju.java.logic.element.ActiveElement;

public abstract class Util extends ActiveElement {

    protected int[] dir;
    GAPI GAPI;

    public Util(int x, int y, String sdir, GAPI GAPI) {
        this.x = x;
        this.y = y;
        this.GAPI = GAPI;
        setDir(sdir);
    }

    private void setDir(String sdir) {
        switch (sdir) {
            case "up":
                dir = new int[]{0, -1};
                break;
            case "down":
                dir = new int[]{0, 1};
                break;
            case "left":
                dir = new int[]{-1, 0};
                break;
            case "right":
                dir = new int[]{1, 0};
                break;
            case "right-up":
                dir = new int[]{1,-1};
                break;
            case"right-down":
                dir = new int[]{1,1};
                break;
            case"left-up":
                dir = new int[]{-1,-1};
                break;
            case"left-down":
                dir = new int[]{-1, 1};
                break;
            default:
                break;
        }
    }
}
