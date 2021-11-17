package nju.java.logic.element;

import java.awt.Color;

public abstract class Unit extends Thread {
    protected char character;
    protected Color color;


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

    /**
     * system need this function to tell unit to occupid positions
     */
    public abstract void prepare();

    public abstract void underAttack(int damage);

}
