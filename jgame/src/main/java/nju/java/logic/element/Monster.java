package nju.java.logic.element;

import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;

public abstract class Monster extends ActiveElement {

    protected void attack(Element target){
        target.submit(new Attack());
    }

    protected int[] moveStrategy() {
        ActiveElement brother = (ActiveElement) gameSystem.getBrother();
        int bx = brother.getX();
        int by = brother.getY();

        int nx = x;
        int ny = y;

        if (bx != nx) {
            nx += (bx > nx) ? (1) : (-1);
        } else if (by != ny) {
            ny += (by > ny) ? (1) : (-1);
        }else{
            
        }
        return new int[]{nx, ny};
    }

    protected void strategy() {
        int[] np = moveStrategy();
        Element res = moveTo(np[0], np[1]);
        if(res!=this){
            attack(res);
        }
    }

    @Override
    public void process(Operation operation){
        if(operation instanceof Attack){
            interrupt();
        }
    }

    @Override
    public void activeProcessor() {
        strategy();
    }

    @Override
    public void frameSleep(){
        eSleep(1000);
    }
}
