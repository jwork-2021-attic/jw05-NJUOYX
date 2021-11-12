package nju.java;

import javax.swing.JFrame;
import nju.java.logic.system.Rsystem;

public class App {
    public static void main(String args[]){
        Rsystem sys = new Rsystem(10, 10);
        sys.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sys.setVisible(true);
        sys.exec();
    }
}
