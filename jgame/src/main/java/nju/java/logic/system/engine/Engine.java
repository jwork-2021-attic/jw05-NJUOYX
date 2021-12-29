package nju.java.logic.system.engine;

import java.awt.*;

public interface Engine {
    void resizeScreen(int rangeX, int rangeY);
    void display(int x, int y, char character, Color color, Boolean visible);
    int getRangeX();
    int getRangeY();
    String getInput();
    String getInput(String player);
    void logOut(int logIndex, String log);
}
