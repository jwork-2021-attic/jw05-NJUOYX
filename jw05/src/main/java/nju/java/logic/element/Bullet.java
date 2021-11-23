package nju.java.logic.element;

import nju.java.logic.element.opration.Attack;

public class Bullet extends ActiveElement {

    private int[] dir;
    

    public Bullet(int x, int y, String sdir, GameSystem gameSystem) {
        this.character = 7;
        setColor(new int[]{255,255,255});

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
        this.x = x + dir[0];
        this.y = y + dir[1];
        Element res = null;
        res = gameSystem.exsit(this.x, this.y);
        if(res == null){
            init(gameSystem);
        }else{
            res.submit(new Attack());
            running = false;
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
        await();
    }

    @Override
    public void frameSleep() {
        eSleep(20);
    }
}
