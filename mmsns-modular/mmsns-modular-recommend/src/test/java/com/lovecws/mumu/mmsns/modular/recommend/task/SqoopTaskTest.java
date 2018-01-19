package com.lovecws.mumu.mmsns.modular.recommend.task;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: sqoop任务测试
 * @date 2018-01-17 13:59:
 */
public class SqoopTaskTest {

    @Test
    public void executeTask() {
        new SqoopJobTask().startSqoopJob("mmsnsarticle", true);
    }
}
