package nju.java.logic.element;

import java.util.concurrent.TimeUnit;

import nju.java.logic.system.UnitSystem;

public class UnitBrother extends Unit {
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

    @Override
    public void prepare() {
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        assert (us.tryOccupy(this, p) == this);
        us.setVisibleOfMe(p, character, color, true);
    }

    @Override
    public void underAttack(int damage) {
    }

    @Override
    public Boolean notRunning() {
        return death;
    }

    @Override
    public void run() {
        UnitSystem us = UnitSystem.getInstance();
        while (!death) {
            synchronized (death) {
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
                    Unit res = us.tryOccupy(this, np);
                    if (res == this) {
                        us.release(this, p);
                        us.setVisibleOfMe(p, character, color, false);
                        x = nx;
                        y = ny;
                        us.setVisibleOfMe(np, character, color, true);
                    } else {
                        res.underAttack(1);
                    }
                }
            }
            us.await(this);
        } // running

        // now sleepping
        while (alive) {
            us.await(this);
        }
    }

    @Override
    public String toString() {
        return String.format("UnitBrother:%d", getId());
    }
}
