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

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    private Unit move(UnitSystem us) {
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
                return null;
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
                return null;
            } else {
                return res;
            }
        }
        return null;
    }

    private void terminate(){
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        us.setVisibleOfMe(p, character, color, false);
        us.release(this, p);
        us.remove(id,this);
    }

    @Override
    public void prepare() {
        UnitSystem us = UnitSystem.getInstance();
        Position p = new Position(x, y);
        us.tryOccupy(this, p);
        us.setVisibleOfMe(p, character, color, true);
        us.register(id = 0, this);// set id and register it
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
        UnitSystem us = UnitSystem.getInstance();
        while (!death) {
            Unit res = move(us);
            if (res != null) {
                res.addAttackOperation(1);
            }

            Boolean roundPass = true;
            for (int i = 10; i < 20; ++i) {
                if (UnitSystem.getInstance().getUnit(i) != null) {
                    roundPass = false;
                    break;
                }
            }
            if (roundPass) {
                return;//terminate me for next round
            }

            operationProcess();
            frameSleep(25);
        }
        terminate();//terminate me
        us.setGameOver();//I'm dead,game over.
    }
}
