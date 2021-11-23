package nju.java.logic.element;

import java.awt.Color;

public class Barrier extends PassiveElement {
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

    @Override
    public void init(GameSystem gameSystem) {
        super.init(gameSystem);
        for (int i = 0; i < barriers.length; i++) {
            Element e = gameSystem.tryOccupy(barriers[i][0], barriers[i][1], this);
            assert (e == this);
            gameSystem.display(barriers[i][0], barriers[i][1], character, color, true);
        }
    }

    @Override
    public void interrupt(){
        super.interrupt();
        for(int i = 0; i < barriers.length; i++) {
            gameSystem.release(barriers[i][0], barriers[i][1], this);
            gameSystem.display(barriers[i][0], barriers[i][1],character, color, false);            
        }
    }

}
