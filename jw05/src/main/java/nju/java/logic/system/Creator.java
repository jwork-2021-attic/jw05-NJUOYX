package nju.java.logic.system;

import nju.java.logic.Controler.*;
import nju.java.logic.element.Element;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


public class Creator {
    private static class EleThing {
        public int positionX;
        public int positionY;
        public int identity;
    }

    private static class EleUser{
        public int positionX;
        public int positionY;
        public int identity;
    }

    public Map<Position, Element> getSpace(SysHandler sys) throws IOException {
        Map<Position, Element> spaces = new HashMap<Position, Element>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        InputStream is = Creator.class.getClassLoader().getResource("element/Thing/Tile.json").openStream();
        List<EleThing> list = mapper.readValue(is, new TypeReference<List<EleThing>>() {
        });
        for(EleThing thing : list){
            Position position = new Position(thing.positionX, thing.positionY);
            CTile ctile = new CTile(sys, thing.identity);
            ctile.setX(thing.positionX);
            ctile.setY(thing.positionY);
            spaces.put(position,ctile);
        }

        is = Creator.class.getClassLoader().getResource("element/Thing/Wall.json").openStream();
        list = mapper.readValue(is, new TypeReference<List<EleThing>>(){});
        for(EleThing thing : list){
            Position position = new Position(thing.positionX, thing.positionY);
            CWall ctile = new CWall(sys, thing.identity);
            ctile.setX(thing.positionX);
            ctile.setY(thing.positionY);
            spaces.put(position,ctile);
        }

        is = Creator.class.getClassLoader().getResource("element/User/demo.json").openStream();
        EleUser thing = mapper.readValue(is, EleUser.class);
        Position position = new Position(thing.positionX, thing.positionY);
        User user = new User(sys, thing.identity);
        user.setX(thing.positionX);
        user.setY(thing.positionY);
        spaces.put(position, user);
        return spaces;
    }
}
