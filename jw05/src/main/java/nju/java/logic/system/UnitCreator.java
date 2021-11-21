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

import nju.java.logic.element.Unit;
import nju.java.logic.element.UnitBrother;
import nju.java.logic.element.UnitMonster;
import nju.java.logic.element.UnitWall;

/**
 * UnitCreator must be create after UnitSystem
 */
public class UnitCreator {
    private ObjectMapper mapper;

    private int rounds = 1;

    private static UnitCreator INSTANCE = new UnitCreator();

    public static UnitCreator getInstance() {
        return INSTANCE;
    }

    private UnitCreator() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
    }

    private Unit readWall() throws IOException {
        InputStream is = UnitCreator.class.getClassLoader()
                .getResource(String.format("element/round%d/UnitWall.json", rounds)).openStream();
        Unit unitWall = mapper.readValue(is, UnitWall.class);
        is.close();
        return unitWall;
    }

    private Unit readBrother() throws IOException {
        InputStream is = UnitCreator.class.getClassLoader()
                .getResource(String.format("element/round%d/UnitBrother.json", rounds)).openStream();
        Unit unitBroter = mapper.readValue(is, UnitBrother.class);
        is.close();
        return unitBroter;
    }

    private List<Unit> readMonsters() throws IOException {
        InputStream is = UnitCreator.class.getClassLoader().getResource(String.format("element/round%d/UnitMonsters.json", rounds)).openStream();
        List<UnitMonster> unitMonsters = mapper.readValue(is, new TypeReference<List<UnitMonster>>(){});
        is.close();
        List<Unit>res = new ArrayList<Unit>();
        unitMonsters.forEach(u->res.add(u));
        return res;
    }

    public List<Unit> getUnits(){
        List<Unit>res = new LinkedList<>();
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
        URL url = UnitCreator.class.getClassLoader().getResource(String.format("element/round%d",++rounds));
        return url != null;
    }
}
