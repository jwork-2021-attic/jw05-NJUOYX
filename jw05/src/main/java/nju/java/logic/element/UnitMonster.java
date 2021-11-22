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

    private Position traceTarget(int tx, int ty) {
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        for (int i = 0; i < dir.length; i++) {
            int nx = x + dir[i][0];
            int ny = y + dir[i][1];
            if (distance(tx, ty, nx, ny) < distance(tx, ty, x, y)) {
                return new Position(nx, ny);
            }
        }
        return null;
    }

    /**
     * return null if move success,otherwise return the owner of the position
     */
    private Unit tryMove(UnitSystem unitSystem, Position np) {
        Position p = new Position(x, y);
        Unit res = unitSystem.tryOccupy(this, np);
        if (res == this) {
            unitSystem.release(this, p);
            unitSystem.setVisibleOfMe(p, character, color, false);
            x = np.x;
            y = np.y;
            unitSystem.setVisibleOfMe(np, character, color, true);
            return null;
        } else {
            return res;
        }
    }

    private void terminate() {
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        us.setVisibleOfMe(p, character, color, false);
        us.release(this, p);
        us.remove(id, this);
    }

    private void mainLogic(){
        
    }


    @Override
    public void prepare() {
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        us.tryOccupy(this, p);
        us.setVisibleOfMe(p, character, color, true);
        id = 10;
        while (!us.register(id, this))
            id++;
    }

    @Override
    public void underAttack(int damage) {
        if (!death) {
            hp -= damage;
            if (hp <= 0) {
                death = true;
            }
        }
    }

    @Override
    public void run() {

        while (!death) {
            operationProcess();

            UnitSystem us = UnitSystem.getInstance();
            Unit target = us.getUnit(0);
            if (target == null) {
                terminate();
                return;
            }

            Position np = traceTarget(target.getX(), target.getY());
            if (np == null) {
                frameSleep(1000);
                continue;
            }

            Unit res = tryMove(us, np);
            if (res != null) {
                if (res instanceof UnitBrother) {
                    res.addAttackOperation(1);
                }
            }

            frameSleep(1000);

        }
        terminate();
    }
}
