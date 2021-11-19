package nju.java.logic.element;

import java.util.concurrent.TimeUnit;

import nju.java.logic.system.UnitSystem;

public class UnitMonster extends Unit {
    private int x;
    private int y;
    private int hp;

    private Boolean death = false;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void prepare() {
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        assert (us.tryOccupy(this, p) == this);
        us.setVisibleOfMe(p, character, color, true);
    }

    @Override
    public void underAttack(int damage) {
        synchronized (death) {
            if (!death) {
                hp -= damage;
                if (hp <= 0) {
                    UnitSystem us = UnitSystem.getInstance();
                    Position p = new Position(x, y);
                    us.setVisibleOfMe(p, character, color, false);
                    us.release(this, p);
                    death = true;
                }
            }
        }
    }


    @Override
    public void run() {
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        while (!death) {
            synchronized (death) {
                for (int i = 0; i < 4; ++i) {
                    UnitSystem us = UnitSystem.getInstance();
                    Position p = new Position(x, y);
                    Position np = new Position(x + dir[i][0], y + dir[i][1]);
                    if (us.tryOccupy(this, np) == this) {
                        us.release(this, p);
                        us.setVisibleOfMe(p, character, color, false);
                        us.setVisibleOfMe(np, character, color, true);
                        x = np.x;
                        y = np.y;
                        break;
                    }
                }
            }
            UnitSystem.getInstance().await();
        }
    }
}
