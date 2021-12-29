package nju.java;

import nju.java.logic.system.engine.ClientEngine;
import nju.java.logic.system.engine.ServerEngine;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebTest {
    final int port = 33006;
    final String hostName = "localhost";

    @Test
    public void registTest(){
        String playerName = "player0";
        try {
            ServerEngine serverEngine = new ServerEngine(this.port);
            ClientEngine clientEngine = new ClientEngine(this.hostName, this.port, playerName);
            clientEngine.run();
            serverEngine.resizeScreen(10,10);
            TimeUnit.MILLISECONDS.sleep(1000);
        }catch (IOException e){
            assertTrue(false);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void displayTest(){
        try{
            ServerEngine serverEngine = new ServerEngine(this.port);
            serverEngine.resizeScreen(10,10);
            serverEngine.display(2,2,(char)1, Color.WHITE,true);
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
