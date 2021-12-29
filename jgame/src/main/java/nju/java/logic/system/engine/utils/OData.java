package nju.java.logic.system.engine.utils;

import java.io.Serializable;

public class OData implements Serializable {
    public long triggerTime;
    public int keyCode;
    public OData(long triggerTime, int keyCode){
        this.triggerTime = triggerTime;
        this.keyCode = keyCode;
    }
}
