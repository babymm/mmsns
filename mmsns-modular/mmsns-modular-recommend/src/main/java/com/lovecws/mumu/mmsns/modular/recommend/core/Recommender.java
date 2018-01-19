package com.lovecws.mumu.mmsns.modular.recommend.core;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本推荐接口
 * @date 2018-01-18 12:35
 */
public interface Recommender {

    public long[] recommend(String taskName, int userID);

    public long[] recommend(String taskName, Float threshold, int userID);

    public long[] recommend(String taskName, String similarity, String neighborhood, Float threshold, int userID);

}
