package nju.java.logic.element;

import nju.java.logic.element.utils.Bullet;
import nju.java.logic.element.utils.UtilsMaker;

public class Brother extends ActiveElement {

    private String[] weapons;
    private int currentWeapon = 0;

    public void setWeapons(String[] weapons) {
        this.weapons = weapons;
    }

    private void resolveMove(String str) {
        int nx = x;
        int ny = y;

        switch (str) {
            case "up":
                ny--;
                break;
            case "down":
                ny++;
                break;
            case "left":
                nx--;
                break;
            case "right":
                nx++;
                break;
            default:
                assert (false);
                break;
        }
        direction = str;
        moveTo(nx, ny);
    }

    protected void attack() {
        UtilsMaker.utilStart(weapons[currentWeapon], x, y, direction, GAPI);
    }

    private void resolveInput(String str) {
        if (str != null) {
            switch (str) {
                case "up":
                case "down":
                case "left":
                case "right":
                    resolveMove(str);
                    break;
                case "change_weapon":
                    changeWeapon();
                    break;
                case "attack":
                    attack();
                    break;
                default:
                    break;
            }
        }
    }

    private void changeWeapon() {
        currentWeapon = (currentWeapon+1)%weapons.length;
    }

    @Override
    public void activeProcessor() {
        String str = GAPI.getInput();
        resolveInput(str);
    }


    @Override
    public void frameSleep() {
        eSleep(25);
    }

    @Override
    public String toString() {
        return "brother_" + getId();
    }
}
