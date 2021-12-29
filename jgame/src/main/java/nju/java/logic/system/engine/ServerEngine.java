package nju.java.logic.system.engine;

import nju.java.logic.system.engine.utils.Log;
import nju.java.logic.system.engine.utils.Player;
import nju.java.logic.system.engine.utils.WebIO;

import java.awt.Color;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class ServerEngine implements Engine{

    private ServerSocket serverSocket;
    private Set<Player>players;
    private int rangeX;
    private int rangeY;


    public ServerEngine(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.players = new CopyOnWriteArraySet<>();
    }


    public void run(int playerNum) {
        for(int i = 0;i<playerNum;++i) {
            try {
                Socket socket = this.serverSocket.accept();
                Log.logOut("accepted a new connection from"+socket);
                Properties properties = WebIO.read(socket);
                String name = properties.getProperty("player");
                Player player = new Player(socket,name);
                players.add(player);
                new Thread(player).start();
                Log.logOut("new connection: player:"+name);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void resizeScreen(int rangeX, int rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        players.forEach(player->{
            try {
                Properties properties = new Properties();
                properties.put("function","resizeScreen");
                properties.put("arg0",String.valueOf(rangeX));
                properties.put("arg1",String.valueOf(rangeY));
                player.send(properties);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visible) {
        players.forEach(player->{
            try{
                Properties properties = new Properties();
                properties.put("function","display");
                properties.put("arg0",String.valueOf(x));
                properties.put("arg1",String.valueOf(y));
                properties.put("arg2",String.valueOf(character));
                properties.put("arg3",String .valueOf(color.getRGB()));
                properties.put("arg4",Boolean.toString(visible));
                player.send(properties);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getRangeX() {
        return this.rangeX;
    }

    @Override
    public int getRangeY() {
        return this.rangeY;
    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public String getInput(String player){
        for (Player p : players) {
            if(p.identify(player)){
                return p.getInput();
            }
        }
        return null;
    }

    private String inputReceiver(Socket p)throws IOException{
        Log.logOut(p.toString()+"--------reading");
        Properties properties = WebIO.read(p);
        String input = properties.getProperty("input");
        Log.logOut("get input:" + input);
        return input;
    }

    @Override
    public void logOut(int logIndex, String log) {
        players.forEach(player->{
            try{
                Properties properties = new Properties();
                properties.put("function","logOut");
                properties.put("arg0",String.valueOf(logIndex));
                properties.put("arg1",log);
                player.send(properties);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
