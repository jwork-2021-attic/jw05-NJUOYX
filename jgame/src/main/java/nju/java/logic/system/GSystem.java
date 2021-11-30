package nju.java.logic.system;

import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.position.EPosition;
import nju.java.logic.system.position.Position;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GSystem implements GAPI {

    private Engine engine;
    private String configFilePath;
    private Properties properties;
    private List<EPosition>positionList;
    private Map<String, Element> recorder = new HashMap<>();

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
    }



    private void initGAPI() throws IOException {
        FileInputStream fs = new FileInputStream(configFilePath);
        properties = new Properties();
        properties.loadFromXML(fs);
    }

    private void initEngine(){
        String rangeX = properties.getProperty("rangeX");
        String rangeY = properties.getProperty("rangeY");
        engine.resizeScreen(Integer.getInteger(rangeX),Integer.getInteger(rangeY));
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
}
