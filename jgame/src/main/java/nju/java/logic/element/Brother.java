package nju.java.logic.element;

import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;
import nju.java.logic.element.opration.ToBrotherAttack;
import nju.java.logic.element.utils.Bullet;
import nju.java.logic.element.utils.UtilsMaker;

public class Brother extends ActiveElement {

    private String[] weapons;
    private int currentWeapon = 0;
    protected int hp;

    public void setWeapons(String[] weapons) {
        this.weapons = weapons;
    }
    public void setHp(int hp){this.hp = hp;}

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
        GAPI.logOut(0, "Your Weapon: "+ weapons[currentWeapon]);
        GAPI.logOut(1, "Your HP: " + hp);
        String str = GAPI.getInput();
        resolveInput(str);
    }


    @Override
    public void frameSleep() {
        eSleep(25);
    }

    @Override
    public void process(Operation operation){
        if(operation instanceof Attack){
            hp--;
            if(hp>0){
                attackedDisplay();
            }else{
                interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return "brother_" + getId();
    }
}
