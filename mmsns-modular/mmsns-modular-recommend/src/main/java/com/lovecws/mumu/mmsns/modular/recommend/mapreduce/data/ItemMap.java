package com.lovecws.mumu.mmsns.modular.recommend.mapreduce.data;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: item map
 * @date 2018-01-17 17:04:
 */
public class ItemMap {

    private static final Map<String, Integer> ITEMMAP = new LinkedTreeMap<String, Integer>();
    private static final AtomicInteger ATOMICINTEGER = new AtomicInteger(1);

    public Integer get(String key) {
        return ITEMMAP.get(key);
    }

    public synchronized Integer put(String key) {
        Integer integer = get(key);
        if (integer != null) {
            return integer;
        }
        int increment = ATOMICINTEGER.getAndIncrement();
        ITEMMAP.put(key, increment);
        return increment;
    }

    public String value() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : ITEMMAP.entrySet()) {
            stringBuilder.append(entry.getValue() + "," + entry.getKey() + "\n");
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
