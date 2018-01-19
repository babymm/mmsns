package com.lovecws.mumu.mmsns.modular.recommend.task;

import com.lovecws.mumu.mmsns.modular.recommend.core.MahoutRecommender;
import org.apache.log4j.Logger;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐任务
 * @date 2018-01-18 09:29:
 */
public class RecommendTask {

    private static final Logger log = Logger.getLogger(RecommendTask.class);

    public void recommend(String taskName) {
        new MahoutRecommender().buildRecommend(taskName);
    }

}
