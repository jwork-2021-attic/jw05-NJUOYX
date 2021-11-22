package nju.java;

import javax.swing.JFrame;

import nju.java.logic.system.UnitSystem;


public class App {
    public static void main(String args[]){
        UnitSystem unitsystem = UnitSystem.getInstance();
        unitsystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        unitsystem.setVisible(true);
        unitsystem.exec();
        System.out.println("Game Over!");
    }
}
