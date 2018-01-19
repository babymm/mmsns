package com.lovecws.mumu.mmsns.modular.recommend.service.impl;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import com.lovecws.mumu.mmsns.modular.recommend.core.MahoutRecommender;
import com.lovecws.mumu.mmsns.modular.recommend.core.Recommender;
import com.lovecws.mumu.mmsns.modular.recommend.entity.RecommendTypeEnum;
import com.lovecws.mumu.mmsns.modular.recommend.service.ModularRecommendService;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐服务实现
 * @date 2018-01-18 12:27
 */
public class ModularRecommendServiceImpl implements ModularRecommendService {

    private static final PropertyConfig propertyConfig = new PropertyConfig();
    private Recommender recommender = new MahoutRecommender();

    @Override
    public long[] recommend(final RecommendTypeEnum recommendType, final int userId, final Float count) {
        String taskName = propertyConfig.getProperty("sqoop.task.classify." + recommendType.name().toLowerCase());
        long[] recommend = recommender.recommend(taskName, count, userId);
        return recommend;
    }
}
