package nju.java.logic.element;

import nju.java.logic.system.UnitSystem;

public class UnitWall extends Unit{

    private int[][]positions;//[x][y]

    public void setPositions(int[][]positions) {
        this.positions = positions;
    }

    @Override
    public void run(){
        UnitSystem us = UnitSystem.getInstance();
        for(int x = 0;x<positions.length;x++){
            for(int y = 0;y<positions[x].length;y++){
                us.setVisibleOfMe(new Position(x,y), character, color, true);
            }
        }
    }
}
