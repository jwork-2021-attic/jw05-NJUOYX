package nju.java.logic.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import nju.java.logic.element.Unit;
import nju.java.logic.element.UnitBrother;
import nju.java.logic.element.UnitWall;

public class UnitCreator {
    public List<Unit> getUnits() throws IOException{
        List<Unit> units = new LinkedList<Unit>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        InputStream is = UnitCreator.class.getClassLoader().getResource("element/Thing/UnitWall.json").openStream();
        UnitWall unitWall = mapper.readValue(is, UnitWall.class);
        units.add(unitWall);

        is = UnitCreator.class.getClassLoader().getResource("element/Thing/UnitBrother.json").openStream();
        UnitBrother unitBroter = mapper.readValue(is, UnitBrother.class);
        units.add(unitBroter);

        return units;
    }
}
