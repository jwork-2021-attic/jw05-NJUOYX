package nju.java.logic.system;

import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.system.Factory.RoundCreator;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.position.EPosition;
import nju.java.logic.system.position.Position;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Recorder{
    private Map<String, Map<String,Element>> recorder;

    public Recorder(){
        recorder = new HashMap<>();
    }

    public void put(String name, Element element){
        String rootName = getRootName(name);
        Map<String, Element> subrecorder = recorder.get(rootName);
        if(subrecorder == null){
            subrecorder = new HashMap<>();
            subrecorder.put(name, element);
            recorder.put(rootName, subrecorder);
        }else{
            subrecorder.put(name, element);
        }
    }

    public Element get(String name){
        String rootName = getRootName(name);
        Map<String, Element>subrecord = recorder.get(rootName);
        if(subrecord == null){
            return null;
        }else{
            Element res = subrecord.get(name);
            if(res == null){
                for(String s: subrecord.keySet()){
                    return subrecord.get(s);
                }
                assert false;
            }
            return res;
        }
    }

    public void remove(String name){
        String rootName = getRootName(name);
        if(recorder.get(rootName) != null){
            recorder.get(rootName).remove(name);
        }
    }

    public List<Element>toList(){
        List<Element>res = new LinkedList<>();
        recorder.forEach((k,v)->{
            v.forEach((s,e)->{
                res.add(e);
            });
        });
        return res;
    }

    private String getRootName(String name){
        return name.split("_")[0];
    }
}


public class GSystem implements GAPI {

    private Engine engine;
    private String configFilePath;
    private Properties properties;
    private List<EPosition>positionList;
    private Recorder recorder = new Recorder();

    /**
     * GAPI controls the whole game logic
     * @param engine engine as UI
     * @param configFilePath app config file path, in this class it is assumed to be a correct path
    */
    public GSystem(Engine engine, String configFilePath) throws IOException{
        this.engine = engine;
        this.configFilePath = configFilePath;
        initGAPI();
        initEngine();
        initEPosition();
    }

    private void initGAPI() throws IOException {
        FileInputStream fs = new FileInputStream(configFilePath);
        properties = new Properties();
        properties.loadFromXML(fs);
    }

    private void initEngine(){
        String rangeX = properties.getProperty("range_x");
        String rangeY = properties.getProperty("range_x");
        engine.resizeScreen(Integer.parseInt(rangeX),Integer.parseInt(rangeY));
    }

    private void initEPosition(){
        positionList = new ArrayList<>();
        int rangeX = engine.getRangeX();
        int rangeY = engine.getRangeY();
        for(int x = 0;x<rangeX;++x){
            for(int y = 0;y<rangeY;++y){
                positionList.add(new EPosition(x, y));
            }
        }
    }

    public void run(){
        new RoundCreator(properties,this).run();
        while(runningCheck()){
            eSleep(400);
        }
        recorder.toList().forEach(e->e.interrupt());
    }

    private Boolean runningCheck(){
        Element e = getElement("brother");
        if(e == null){
            return false;
        }
        e = getElement("monster");
        if(e == null){
            return false;
        }
        return true;
    }

    private void eSleep(int milliseconds){
        try{
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visible) {
        engine.display(x, y, character, color, visible);
    }

    @Override
    public Element tryOccupy(int x, int y, Element requester) {
        int index = positionList.indexOf(new Position(x,y));
        assert(index>=0);
        return positionList.get(index).tryOccupy(requester);
    }

    @Override
    public Element exsit(int x, int y) {
        int index = positionList.indexOf(new Position(x,y));
        assert(index>=0);
        return positionList.get(index).getOwner();
    }

    @Override
    public void release(int x, int y, Element element) {
        int index = positionList.indexOf(new Position(x,y));
        assert(index>=0);
        positionList.get(index).release(element);
    }

    @Override
    public String getInput() {
        return engine.getInput();
    }

    @Override
    public Element getElement(String name){
        return recorder.get(name);
    }

    @Override
    public void register(String name, Element element) {
        recorder.put(name,element);
    }

    @Override
    public void unregister(String name, Element element) {
        Element e = recorder.get(name);
        if(e == element){
            recorder.remove(name);
        }
    }
}
