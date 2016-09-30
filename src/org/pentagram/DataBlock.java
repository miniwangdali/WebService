package org.pentagram;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang_ on 2016/9/27.
 */
public class DataBlock {
    Map<String, String> dataMap = null;

    public DataBlock() {
        dataMap = new HashMap<>();
    }

    public DataBlock(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }
    public void setHeartRate(String info){
        dataMap.put("HeartRate", info);
    }
}
