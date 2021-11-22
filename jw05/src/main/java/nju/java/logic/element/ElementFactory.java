package nju.java.logic.element;

import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

import nju.java.logic.system.UnitSystem;

class Bullet extends Unit {

    private int x;
    private int y;

    private int[] dir;

    private int damage;

    public Bullet(int x, int y, int[] dir, int damage) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.damage = damage;
    }

    private Position nextPosition() {
        x+=dir[0];
        y+=dir[1];
        return new Position(x, y);
    }

    @Override
    public void prepare() {
    }

    @Override
    public void underAttack(int damage) {
    }

    @Override
    public void run() {
        UnitSystem us = UnitSystem.getInstance();
        Unit res = null;
        while ((res = us.tryOccupy(this, nextPosition())) == this) {
            Position np = new Position(x,y);
            us.setVisibleOfMe(np, (char)7, Color.WHITE, true);
            frameSleep(10);
            us.setVisibleOfMe(np, (char)7,Color.WHITE, false);
            us.release(this, np);
        }
        res.addAttackOperation(damage);
    }
}

public class ElementFactory {

    public static Unit getBullet(String direction, int x, int y, int damage) {
        if (direction == "up") {
            return new Bullet(x, y, new int[] { 0, -1 }, damage);
        }else if(direction == "down") {
            return new Bullet(x, y, new int[] {0,1},damage);
        }else if(direction == "left") {
            return new Bullet(x, y, new int[] {-1,0},damage);
        }else if(direction == "right") {
            return new Bullet(x, y, new int[] {1,0},damage);
        }else{
            return null;
        }
    }
}
