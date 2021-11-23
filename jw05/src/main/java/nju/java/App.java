package nju.java;

import javax.swing.JFrame;

import nju.java.logic.system.GSystem;


public class App {
    public static void main(String args[]){
        GSystem unitsystem = new GSystem(10,10);
        unitsystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        unitsystem.setVisible(true);
        unitsystem.exec();
    }
}
