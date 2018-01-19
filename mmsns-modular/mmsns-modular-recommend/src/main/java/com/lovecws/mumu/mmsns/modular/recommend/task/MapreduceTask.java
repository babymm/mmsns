package com.lovecws.mumu.mmsns.modular.recommend.task;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import com.lovecws.mumu.mmsns.modular.recommend.config.RecommendConfig;
import com.lovecws.mumu.mmsns.modular.recommend.mapreduce.AbstractMapReduce;
import com.lovecws.mumu.mmsns.modular.recommend.mapreduce.BasicMapReduce;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mapreduce任务
 * @date 2018-01-17 14:50:
 */
public class MapreduceTask {

    private static final Logger log = Logger.getLogger(MapreduceTask.class);

    public void startMapreduce(String taskName) {
        log.info("开始mapreduce程序");
        AbstractMapReduce mapReduce = new BasicMapReduce();
        mapReduce.startMapReduce(taskName);
    }
}
