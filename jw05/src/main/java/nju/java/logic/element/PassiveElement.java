package nju.java.logic.element;

import nju.java.logic.element.opration.Operation;

public abstract class PassiveElement extends Element{
    @Override
    public void run(){
        while(isRunning()){
            passiveProcessor();
        }
    }

    protected void await(){
        eSleep(5);
    }

    protected void passiveProcessor() {
        Operation op = getOperation();
        if(op == null){
            await();
        }else{
            process(op);
        }
    }
}
