package nju.java.logic.system;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import nju.java.logic.element.Barrier;
import nju.java.logic.element.Brother;
import nju.java.logic.element.Element;
import nju.java.logic.element.NormalMonster;


/**
 * UnitCreator must be create after UnitSystem
 */
public class ElementCreator {
    private ObjectMapper mapper;

    private int rounds = 1;

    private static ElementCreator INSTANCE = new ElementCreator();

    public static ElementCreator getInstance() {
        return INSTANCE;
    }

    private ElementCreator() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
    }

    private Element readWall() throws IOException {
        InputStream is = ElementCreator.class.getClassLoader()
                .getResource(String.format("element/round%d/Barrier.json", rounds)).openStream();
        Element barrier = (Element)mapper.readValue(is, Barrier.class);
        is.close();
        return barrier;
    }

    private Element readBrother() throws IOException {
        InputStream is = ElementCreator.class.getClassLoader()
                .getResource(String.format("element/round%d/Brother.json", rounds)).openStream();
        Element Brother = (Element)mapper.readValue(is, Brother.class);
        is.close();
        return Brother;
    }

    private List<Element> readMonsters() throws IOException {
        InputStream is = ElementCreator.class.getClassLoader().getResource(String.format("element/round%d/NormalMonsters.json", rounds)).openStream();
        List<NormalMonster> NormalMonsters = mapper.readValue(is, new TypeReference<List<NormalMonster>>(){});
        is.close();
        List<Element>res = new ArrayList<Element>();
        NormalMonsters.forEach(u->res.add((Element)u));
        return res;
    }

    public List<Element> getElements(){
        List<Element>res = new LinkedList<>();
        try {
            res.add(readWall());
            res.add(readBrother());
            res.addAll(readMonsters());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean next(){
        URL url = ElementCreator.class.getClassLoader().getResource(String.format("element/round%d",++rounds));
        return url != null;
    }
}
