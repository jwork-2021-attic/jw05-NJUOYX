package nju.java.logic.element;

import java.util.concurrent.TimeUnit;

import nju.java.logic.system.UnitSystem;

public class UnitMonster extends Unit {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void prepare() {
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        assert (us.tryOccupy(p));
        us.setVisibleOfMe(p, character, color, true);
    }

    @Override
    public void run() {
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        while (true) {
            for (int i = 0; i < 4; ++i) {
                UnitSystem us = UnitSystem.getInstance();
                Position p = new Position(x, y);
                Position np = new Position(x + dir[i][0], y + dir[i][1]);
                if (us.tryOccupy(np)) {
                    us.release(p);
                    us.setVisibleOfMe(p, character, color, false);
                    us.setVisibleOfMe(np, character, color, true);
                    x = np.x;
                    y = np.y;
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException ir) {
                        System.out.println(ir);
                    }
                }
            }
        }
    }
}
