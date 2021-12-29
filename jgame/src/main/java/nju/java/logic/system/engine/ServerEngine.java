package nju.java.logic.system.engine;

import nju.java.logic.system.engine.utils.Log;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerEngine implements Engine {

    private ServerSocket serverSocket;
    private Map<String, Socket>players;
    private int rangeX;
    private int rangeY;


    public ServerEngine(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(60000);
        this.players = new ConcurrentHashMap<>();
        run();
    }

    private void run() {
        new Thread(() -> {
            while(true) {
                try {
                    Socket socket = this.serverSocket.accept();
                    Log.logOut("accepted a new connection from"+socket);
                    Properties properties = new Properties();
                    properties.load(new DataInputStream(socket.getInputStream()));
                    String name = properties.getProperty("player");
                    players.put(name, socket);
                    Log.logOut(String.format("new connection: player:%d",name));
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    interface Task {
        void run(Socket socket);
    }

    private void handle(Task task){
        players.forEach((name, socket)->{
            new Thread(()->{
                task.run(socket);
            }).start();
        });
    }

    @Override
    public void resizeScreen(int rangeX, int rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        handle(player->{
            try {
                DataOutputStream out = new DataOutputStream(player.getOutputStream());
                Properties properties = new Properties();
                properties.put("function","resizeScreen");
                properties.put("arg0",String.valueOf(rangeX));
                properties.put("arg1",String.valueOf(rangeY));
                properties.store(out,null);
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visible) {
        handle(player->{
            try{
                DataOutputStream out = new DataOutputStream(player.getOutputStream());
                Properties properties = new Properties();
                properties.put("function","display");
                properties.put("arg0",String.valueOf(x));
                properties.put("arg1",String.valueOf(y));
                properties.put("arg2",String.valueOf(character));
                properties.put("arg3",String .valueOf(color.getRGB()));
                properties.put("arg4",Boolean.toString(visible));
                properties.store(out,null);
                out.flush();
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
        List<String>gets = new LinkedList<>();
        handle(p->{
            try{
                DataOutputStream out = new DataOutputStream(p.getOutputStream());
                Properties properties = new Properties();
                properties.put("function","getInput");
                properties.put("arg0",player);
                properties.store(out,null);
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        handle(p->{
            try{
                DataInputStream in = new DataInputStream(p.getInputStream());
                Properties properties= new Properties();
                properties.load(in);
                String res = properties.getProperty("return");
                if(res!=null){
                    gets.add(res);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        return gets.isEmpty()?null:gets.get(0);
    }

    @Override
    public void logOut(int logIndex, String log) {
        handle(player->{
            try{
                DataOutputStream out = new DataOutputStream(player.getOutputStream());
                Properties properties = new Properties();
                properties.put("function","logOut");
                properties.put("arg0",String.valueOf(logIndex));
                properties.put("arg1",log);
                properties.store(out,null);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
