package nju.java.logic.element;

import java.util.concurrent.TimeUnit;

import nju.java.logic.system.UnitSystem;

public class UnitBrother extends Unit {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void prepare(){
        UnitSystem us = UnitSystem.getInstance();
        assert(us.tryOccupy(new Position(x,y)));
    }

    @Override
    public void run() {
        UnitSystem us = UnitSystem.getInstance();
        us.setVisibleOfMe(new Position(x, y), character, color, true);
        while (true) {
            String move = us.getMoveInput();
            int nx = x, ny = y;
            if (move != null) {
                if (move == "up") {
                    ny--;
                } else if (move == "down") {
                    ny++;
                } else if (move == "left") {
                    nx--;
                } else if (move == "right") {
                    nx++;
                } else {
                    continue;
                }
                Position p = new Position(x, y);
                Position np = new Position(nx, ny);
                if (us.tryOccupy(np)) {
                    us.release(p);
                    us.setVisibleOfMe(p, character, color, false);
                    x = nx;
                    y = ny;
                    us.setVisibleOfMe(np, character, color, true);
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(25);
            } catch (InterruptedException ir) {
                System.out.println(ir);
            }
        }

    }
}
