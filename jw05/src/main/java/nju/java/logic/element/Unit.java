package nju.java.logic.element;

import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/*
UnitBrother : 0;
UnitMonsters: 10+;
*/


public abstract class Unit extends Thread {
    protected char character;
    protected Color color;

    protected int id;

    interface Operation{
        void execute();
    }

    protected Queue<Operation> operations = new ConcurrentLinkedQueue<Operation>();

    class AttackOperation implements Operation{
        private int damage;

        private AttackOperation(int damage){
            this.damage = damage;
        }

        @Override
        public void execute(){
            underAttack(damage);
        }
    }
    public void addAttackOperation(int damage){
        operations.add(new AttackOperation(damage));
    }


    public void setCharacter(char character) {
        this.character = character;
    }

    public void setColor(int[] color) {
        this.color = new Color(color[0], color[1], color[2]);
    }

    public char getCharacter() {
        return character;
    }

    public Color getColor() {
        return color;
    }

    public void interact(Operation operation){
        operations.add(operation);
    }


    /**
     * system need this function to tell unit to occupid positions
     */
    public abstract void prepare();

    public abstract void underAttack(int damage);

    public int getX(){return 0;}
    public int getY(){return 0;}

    protected void frameSleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ir) {
            System.out.println(ir);
        }
    }

    protected void operationProcess() {
        while (!operations.isEmpty()) {
            operations.poll().execute();
        }
    }

    protected int distance(int x0, int y0, int x1, int y1) {
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }

    
}
