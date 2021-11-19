package nju.java.logic.element;

import nju.java.logic.system.UnitSystem;

public class UnitWall extends Unit {

    private int[][] positions;// [x][y]

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    @Override
    public void prepare() {
        UnitSystem us = UnitSystem.getInstance();
        for (int i = 0; i < positions.length; i++) {
            Position p = new Position(positions[i][0], positions[i][1]);
            us.tryOccupy(this, p);
            us.setVisibleOfMe(p, character, color, true);
        }
    }

    @Override
    public void run() {
        while(true){
            UnitSystem.getInstance().await();
        }
    }

    @Override
    public void underAttack(int damage) {

    }
}
