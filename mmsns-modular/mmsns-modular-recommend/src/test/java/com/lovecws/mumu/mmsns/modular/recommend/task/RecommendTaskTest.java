package com.lovecws.mumu.mmsns.modular.recommend.task;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐程序测试
 * @date 2018-01-18 09:53:
 */
public class RecommendTaskTest {

    @Test
    public void recommend(){
        new RecommendTask().recommend("mmsnsarticle");
    }
}
