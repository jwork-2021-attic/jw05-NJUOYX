package nju.java.logic.element.utils;

import nju.java.logic.element.GAPI;
import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;

public class Wall extends Util{

    public Wall(int x, int y, String sdir, GAPI GAPI){
        super(x, y, sdir, GAPI);
        this.x = x + dir[0];
        this.y = y + dir[1];
        character = 177;
        setColor(new int[]{255, 0, 255});
        running = init(GAPI);
    }

    @Override
    protected void activeProcessor() {
    }

    @Override
    protected void frameSleep() {
        eSleep(1000);
    }

    @Override
    public String toString(){
        return "Wall"+getId();
    }

    @Override
    public void process(Operation operation){
        if(operation instanceof Attack){
            interrupt();
        }
    }
}
