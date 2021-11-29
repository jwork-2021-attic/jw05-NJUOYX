package nju.java.logic.element;

import java.awt.Color;

public abstract class ActiveElement extends PassiveElement {

    protected int x;
    protected int y;
    protected char character;
    protected Color color;

    protected String direction = "up";

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public void setColor(int[] color) {
        assert (color.length == 3);
        this.color = new Color(color[0], color[1], color[2]);
    }

    @Override
    public void init(GameSystem gameSystem) {
        super.init(gameSystem);
        Element e = gameSystem.tryOccupy(x, y, this);
        assert (e == this);
        gameSystem.display(x, y, character, color, true);
    }

    @Override
    public void interrupt() {
        super.interrupt();
        gameSystem.release(x, y, this);
        gameSystem.display(x, y, character, color, false);
    }

    class ActiveProcessor implements Runnable {
        ActiveElement activeElement;
        public ActiveProcessor(ActiveElement activeElement) {
            this.activeElement = activeElement;
        }
        @Override
        public void run(){
            while(activeElement.isRunning()) {
                activeElement.activeProcessor();
                frameSleep();
            }
        }
    }

    @Override
    public void run() {
        new Thread(new ActiveProcessor(this)).start();
        super.run();
    }

    protected Element moveTo(int nx, int ny) {
        Element e = gameSystem.tryOccupy(nx, ny, this);
        if (e == this) {
            gameSystem.release(x, y, this);
            gameSystem.display(x, y, character, color, false);
            x = nx;
            y = ny;
            gameSystem.display(x, y, character, color, true);
        }
        return e;
    }

    protected abstract void activeProcessor();

    protected abstract void frameSleep();

}
