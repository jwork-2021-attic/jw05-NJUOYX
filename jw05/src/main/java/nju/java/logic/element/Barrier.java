package nju.java.logic.element;
import java.awt.Color;

public class Barrier extends StablePBElement {
    private int[][] barriers;
    
    private char character;

    private Color color;

    public void setBarriers(int[][] barriers) {
        this.barriers = barriers;
    }

    public void setColor(int[] color) {
        this.color = new Color(color[0], color[1], color[2]);
    }

    public void setCharacter(char character) {
        this.character = character;
    }
}
