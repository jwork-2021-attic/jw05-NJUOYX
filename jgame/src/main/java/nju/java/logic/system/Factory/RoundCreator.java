package nju.java.logic.system.Factory;

import nju.java.logic.element.Brother;
import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.element.Monster;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class RoundCreator {
    private Properties properties;
    private GAPI gapi;

    public RoundCreator(Properties properties, GAPI gapi){
        this.properties = properties;
        this.gapi = gapi;
    }
    public void run(){
        barrierInit().start();
        brotherInit().start();
        Queue<Monster> monsters = monsterCreate();
        while(!monsters.isEmpty()){
            Monster monster = monsters.poll();
            Boolean res = monster.init(gapi);
            if(!res){
                monsters.add(monster);
            }else{
                monster.start();
            }
            eSleep(1000);
        }
    }

    private void eSleep(int milliseconds){
        try{
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private Element barrierInit(){
        String barrierFile = properties.getProperty("barrier");
        Element barrier = ElementFactory.getElement(barrierFile);
        barrier.init(gapi);
        return barrier;
    }

    private Element brotherInit(){
        String brotherFile = properties.getProperty("brother");
        Brother brother = (Brother)ElementFactory.getElement(brotherFile);
        String strX = properties.getProperty("brother_x");
        String strY = properties.getProperty("brother_y");
        brother.setX(Integer.parseInt(strX));
        brother.setY(Integer.parseInt(strY));
        brother.init(gapi);
        return brother;
    }

    private Queue<Monster> monsterCreate(){
        String monsterFile = properties.getProperty("monster");
        String monsterNum = properties.getProperty("monster_num");
        String strX = properties.getProperty("monster_x");
        String strY = properties.getProperty("monster_y");
        int x = Integer.parseInt(strX);
        int y = Integer.parseInt(strY);
        int mnum = Integer.parseInt(monsterNum);
        Queue<Monster>queue = new LinkedList<>();
        for(int i = 0;i<mnum;++i){
           Monster monster = (Monster)ElementFactory.getElement(monsterFile);
           monster.setX(x);
           monster.setY(y);
           queue.add(monster);
        }
        return queue;
    }

}
