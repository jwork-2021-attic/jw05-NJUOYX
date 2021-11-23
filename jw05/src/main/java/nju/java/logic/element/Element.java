package nju.java.logic.element;

interface Operation{

}

class AttackOperation implements Operation{

}

class MoveOperation implements Operation{}


public abstract class Element extends Thread{
    @Override
    public void run(){}

    public void process(AttackOperation operation){}

    public void process(MoveOperation operation){}

}
