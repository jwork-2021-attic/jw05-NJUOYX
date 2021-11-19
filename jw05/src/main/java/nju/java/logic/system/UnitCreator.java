package nju.java.logic.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
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

    private static UnitCreator INSTANCE = new UnitCreator();

    public static UnitCreator getInstance(){return INSTANCE;}

    private UnitCreator() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
    }

    private Unit readWall() throws IOException {
        InputStream is = UnitCreator.class.getClassLoader().getResource("element/UnitWall.json").openStream();
        Unit unitWall = mapper.readValue(is, UnitWall.class);
        is.close();
        return unitWall;
    }

    private Unit readBrother() throws IOException {
        InputStream is = UnitCreator.class.getClassLoader().getResource("element/UnitBrother.json").openStream();
        Unit unitBroter = mapper.readValue(is, UnitBrother.class);
        is.close();
        return unitBroter;
    }

    private List<Unit> readMonsters() throws IOException {
        int i = 0;
        InputStream is = null;
        List<Unit> unitMonsters = new LinkedList<Unit>();
        while (true) {
            String name = String.format("element/UnitMonster%d.json", i);
            try {
                is = UnitCreator.class.getClassLoader().getResource(name).openStream();
            } catch (Exception e) {
                if (i == 0) {
                    throw e;
                }else{
                    break;
                }
            }
            UnitMonster unitMonster = mapper.readValue(is, UnitMonster.class);
            unitMonsters.add(unitMonster);
            i++;
        }
        return unitMonsters;
    }

    public Unit getWall() {
        try {
            return readWall();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Unit getBrother() {
        try {
            return readBrother();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Unit> getMonsters() {
        try {
            return readMonsters();
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    public void next() {
    }

    public Boolean hasNext() {
        return false;
    }
}
