package nju.java.logic.system.engine;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import nju.java.logic.system.engine.utils.Log;
import nju.java.logic.system.engine.utils.WebIO;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ClientEngine extends JFrame implements KeyListener {
    private Socket client;
    private String playerName;

    private AsciiPanel terminal;

    private final int logXLen = 25;
    private final int logYLen = 3;
    private final int logXGap = 2;
    private final int logYGap = 1;

    public ClientEngine(String serverName, int port ,String playerName)throws IOException{
        this.playerName = playerName;
        this.terminal = null;
        this.client = new Socket(serverName, port);
        Log.logOut("connected to server"+this.client);
        setVisible(true);
    }

    public void run()throws IOException{
        register(playerName);
        receiver();
    }

    private void register(String playerName)throws IOException{
        Properties properties = new Properties();
        properties.setProperty("player",playerName);
        WebIO.write(properties,this.client);
        Log.logOut("registered!");
    }

    private void resizeScreen(int rangeX, int rangeY) {
        this.terminal = new AsciiPanel(rangeX+ logXLen, rangeY, AsciiFont.TALRYTH_15_15);
        add(this.terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    private void display(int x, int y, char character, Color color, Boolean visible) {
        this.terminal.write(character,x,y,visible?color:Color.BLACK);
        repaint();
    }


    private void solveCommand(Properties properties)throws IOException{
        String function = properties.getProperty("function");
        Log.logOut("solving command"+function);
        switch (function){
            case "resizeScreen":{
                int rangeX = Integer.valueOf(properties.getProperty("arg0"));
                int rangeY = Integer.valueOf(properties.getProperty("arg1"));
                resizeScreen(rangeX, rangeY);
            }break;
            case "display":{
                int x = Integer.valueOf(properties.getProperty("arg0"));
                int y = Integer.valueOf(properties.getProperty("arg1"));
                char character = Character.valueOf(properties.getProperty("arg2").charAt(0));
                Color color = new Color(Integer.valueOf(properties.getProperty("arg3")));
                Boolean visible = Boolean.valueOf(properties.getProperty("arg4"));
                display(x, y, character, color, visible);
            }break;
            default:break;
        }
    }

    private void receiver()throws IOException{
        while(true){
            Properties properties = WebIO.read(this.client);
            solveCommand(properties);
        }
    }


    private String resolveKeyCode(int keyCode){
        String res = null;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                res = "up";
                break;
            case KeyEvent.VK_DOWN:
                res = "down";
                break;
            case KeyEvent.VK_LEFT:
                res = "left";
                break;
            case KeyEvent.VK_RIGHT:
                res = "right";
                break;
            case KeyEvent.VK_SPACE:
                res = "attack";
                break;
            case KeyEvent.VK_C:
                res = "change_weapon";
                break;
            default:
                break;
        }
        return res;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        String res = resolveKeyCode(e.getKeyCode());
        if(res!=null) {
            Properties properties = new Properties();
            properties.setProperty("input",res);
            try {
                WebIO.write(properties, this.client);
                Log.logOut("send string: "+res);
            }catch (IOException err){
                err.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
