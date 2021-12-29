package nju.java;

import nju.java.logic.system.GSystem;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.engine.AsciiEngine;
import nju.java.logic.system.engine.ServerEngine;
import org.apache.commons.cli.*;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;


public class Server {

    public static void main(String args[]) {
        try {
            ServerEngine engine = new ServerEngine(33006);
            engine.run(1);
            engine.resizeScreen(10,10);
            engine.display(2,2,(char)2, Color.yellow,true);
            while(true){
                String res = engine.getInput("player");
                if(res != null){
                    System.out.println(res);
                }
            }
            //new GSystem(engine, "config.xml").run();
        }catch (IOException e){
            System.err.println("config.xml is broken!");
            e.printStackTrace();
        }
    }
}
