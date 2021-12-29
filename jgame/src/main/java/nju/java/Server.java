package nju.java;

import nju.java.logic.system.GSystem;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.engine.AsciiEngine;
import nju.java.logic.system.engine.ServerEngine;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.ServerSocket;


public class Server {

    public static void main(String args[]) {

        try {
            Engine engine = new ServerEngine(33006);
            //new GSystem(engine, "config.xml").run();
        }catch (IOException e){
            System.err.println("config.xml is broken!");
            e.printStackTrace();
        }
    }
}
