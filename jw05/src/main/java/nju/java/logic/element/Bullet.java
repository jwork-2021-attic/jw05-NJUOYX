package nju.java.logic.element;

import nju.java.logic.element.opration.Attack;

public class Bullet extends ActiveElement {

    private int x;
    private int y;
    private int[] dir;

    public Bullet(int x, int y, String sdir) {
        this.x = x;
        this.y = y;
        switch (sdir) {
        case "up":
            dir = new int[] { 0, -1 };
            break;
        case "down":
            dir = new int[] { 0, 1 };
            break;
        case "left":
            dir = new int[] { -1, 0 };
            break;
        case "right":
            dir = new int[] { 1, 0 };
            break;
        default:
            break;
        }
    }

    @Override
    public void activeProcessor() {
        int nx = x + dir[0];
        int ny = y + dir[1];
        Element res = gameSystem.exsit(nx, ny);
        if(res == null){
            moveTo(nx, ny);
        }else{
            res.submit(new Attack());
            interrupt();
        }
    }

    @Override
    public void passiveProcessor() {
        // keep null
    }

    @Override
    public void frameSleep() {
        eSleep(10);
    }
}
