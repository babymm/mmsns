package com.lovecws.mumu.mmsns.modular.recommend.service;

import com.lovecws.mumu.mmsns.modular.recommend.entity.RecommendTypeEnum;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐服务
 * @date 2018-01-18 12:23
 */
public interface ModularRecommendService {

    public long[] recommend(RecommendTypeEnum recommendType, int uid, Float count);
}
