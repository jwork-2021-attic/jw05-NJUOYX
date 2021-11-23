package nju.java.logic.element;

import java.awt.Color;

public interface GameSystem {
    void display(int x ,int y, char character, Color color, Boolean visi);
    
    Element tryOccupy(int x , int y, Element requester);
    
    Element exsit(int x, int y);

    void release(int x, int y, Element element);

    String getInput();

    Element getBrother();


}
