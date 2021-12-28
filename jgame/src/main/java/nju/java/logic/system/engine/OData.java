package nju.java.logic.system.engine;

import java.io.Serializable;

public class OData implements Serializable {
    long triggerTime;
    int keyCode;
    OData(long triggerTime, int keyCode){
        this.triggerTime = triggerTime;
        this.keyCode = keyCode;
    }
}
