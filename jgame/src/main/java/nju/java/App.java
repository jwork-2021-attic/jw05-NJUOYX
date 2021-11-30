package nju.java;

import nju.java.logic.system.GSystem;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.engine.AsciiEngine;

import java.io.IOException;


public class App {
    public static void main(String args[]){
        final String configFilePath = "config.xml";
        Engine engine = new AsciiEngine();
        GSystem gapi = null;
        try {
            gapi = new GSystem(engine, configFilePath);
        }catch (IOException e){
            System.err.println("config.xml is broken!");
            e.printStackTrace();
        }
        gapi.run();
    }
}
