package com.lovecws.mumu.mmsns.modular.recommend.task;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mapreduce任务测试
 * @date 2018-01-17 14:59:
 */
public class MapreduceTaskTest {

    @Test
    public void startMapreduce(){
        new MapreduceTask().startMapreduce("mmsnsarticle");
    }
}
