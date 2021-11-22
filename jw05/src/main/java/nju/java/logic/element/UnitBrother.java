package nju.java.logic.element;

import java.util.concurrent.TimeUnit;

import nju.java.logic.system.UnitSystem;

public class UnitBrother extends Unit {
    private int x;
    private int y;
    private int hp;
    private Boolean death = false;

    private String direction = "up";

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
                direction = move;
                ny--;
            } else if (move == "down") {
                direction = move;
                ny++;
            } else if (move == "left") {
                direction = move;
                nx--;
            } else if (move == "right") {
                direction = move;
                nx++;
            }else if(move == "attack"){
                doAttack(us, direction);
            }
             else {
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

    private void doAttack(UnitSystem us, String direction){
        ElementFactory.getBullet(direction, x, y, 1).start();
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
            move(us);

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
