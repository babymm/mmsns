package com.lovecws.mumu.mmsns.modular.recommend.core;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mahout推荐测试
 * @date 2018-01-18 16:07
 */
public class MahoutRecommenderTest {

    MahoutRecommender recommender = new MahoutRecommender();

    @Test
    public void recommend() {
        long[] mmsnsarticles = recommender.recommend("mmsnsarticle", 10);
        System.out.println(StringUtils.join(mmsnsarticles, ','));
    }

    @Test
    public void evaluate() {
        recommender.evaluate("mmsnsarticle");
    }

    @Test
    public void IRState() {
        recommender.IRState("mmsnsarticle");
    }
}
