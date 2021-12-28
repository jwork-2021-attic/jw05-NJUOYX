package nju.java;

import nju.java.logic.system.GSystem;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.engine.AsciiEngine;
import org.apache.commons.cli.*;

import java.io.IOException;


public class App {

    public static void main(String args[])throws ParseException {
        System.out.println("hello!");
        final String configFilePath = "config.xml";
        Engine engine = null;
        GSystem gapi = null;

        CommandLineParser parser = new BasicParser();

        Options options = new Options();
        options.addOption("l","load",true,"start game with load mode,which will play a review");
        options.addOption("s","sava",true,"the game will save the whole play");

        CommandLine commandLine = parser.parse(options,args);
        if(commandLine.hasOption("l")) {
            engine = AsciiEngine.createAsciiEngine(commandLine.getOptionValue("l"), true);
        }else if(commandLine.hasOption("s")){
            if(commandLine.getOptionValue("s") != null) {
                engine = AsciiEngine.createAsciiEngine(commandLine.getOptionValue("s"), true);
            }
        }else{
            engine = AsciiEngine.createAsciiEngine();
        }

        try {
            gapi = new GSystem(engine, configFilePath);
        }catch (IOException e){
            System.err.println("config.xml is broken!");
            e.printStackTrace();
        }
        gapi.run();
    }
}
