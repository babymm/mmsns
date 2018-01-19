package com.lovecws.mumu.mmsns.modular.recommend.core;

import com.lovecws.mumu.mmsns.modular.recommend.config.RecommendConfig;
import com.lovecws.mumu.mmsns.modular.recommend.util.HadoopUtil;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mahout推荐
 * @date 2018-01-18 12:42
 */
public class MahoutRecommender implements Recommender {

    private static final Logger log = Logger.getLogger(MahoutRecommender.class);

    @Override
    public long[] recommend(String taskName, int userID) {
        return recommend(taskName, null, null, null, userID);
    }

    @Override
    public long[] recommend(final String taskName, final Float threshold, final int userID) {
        return recommend(taskName, null, null, threshold, userID);
    }

    @Override
    public long[] recommend(String taskName, String similarity, String neighborhood, Float threshold, int userID) {
        String itemmodelsPath = RecommendConfig.class.getResource("/").getPath() + "itemmodels.csv";
        HadoopUtil.download(taskName, itemmodelsPath, false);

        try {
            DataModel dataModel = new FileDataModel(new File(itemmodelsPath));
            UserSimilarity userSimilarity = null;
            if (SpearmanCorrelationSimilarity.class.getSimpleName().equals(similarity)) {
                userSimilarity = new SpearmanCorrelationSimilarity(dataModel);
            } else if (PearsonCorrelationSimilarity.class.getSimpleName().equals(similarity)) {
                userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            } else if (EuclideanDistanceSimilarity.class.getName().equals(similarity)) {
                userSimilarity = new EuclideanDistanceSimilarity(dataModel);
            } else {
                userSimilarity = new CachingUserSimilarity(new SpearmanCorrelationSimilarity(dataModel), dataModel);
            }

            UserNeighborhood userNeighborhood = null;
            if (NearestNUserNeighborhood.class.getName().equals(neighborhood)) {
                if (threshold == null) threshold = 5f;
                userNeighborhood = new NearestNUserNeighborhood(threshold.intValue(), userSimilarity, dataModel);
            } else if (ThresholdUserNeighborhood.class.getName().equals(neighborhood)) {
                if (threshold == null) threshold = 0.2f;
                userNeighborhood = new ThresholdUserNeighborhood(threshold, userSimilarity, dataModel);
            } else {
                if (threshold == null) threshold = 5f;
                userNeighborhood = new CachingUserNeighborhood(new NearestNUserNeighborhood(threshold.intValue(), userSimilarity, dataModel), dataModel);
            }
            return userNeighborhood.getUserNeighborhood(userID);
        } catch (TasteException | IOException e) {
            log.error(e);
        }
        return null;
    }

    public void buildRecommend(String taskName) {
        String itemmodelsPath = RecommendConfig.class.getResource("/").getPath() + "itemmodels.csv";
        HadoopUtil.download(taskName, itemmodelsPath, true);
        try {
            DataModel dataModel = new FileDataModel(new File(itemmodelsPath));
            UserSimilarity similarity = new SpearmanCorrelationSimilarity(dataModel);
            UserNeighborhood userNeighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
            LongPrimitiveIterator userIDs = dataModel.getUserIDs();
            while (userIDs.hasNext()) {
                Long userID = userIDs.nextLong();
                long[] neighborhoods = userNeighborhood.getUserNeighborhood(userID);
                for (long neighborhood : neighborhoods) {
                    double userSimilarity = similarity.userSimilarity(userID, neighborhood);
                    System.out.printf("(%s,%s,%f)", userID, neighborhood, userSimilarity);
                    System.out.println();
                }
            }
        } catch (TasteException | IOException e) {
            log.error(e);
        }
    }

    public void evaluate(String taskName) {
        String itemmodelsPath = RecommendConfig.class.getResource("/").getPath() + "itemmodels.csv";
        HadoopUtil.download(taskName, itemmodelsPath, false);

        RandomUtils.useTestSeed();
        try {
            DataModel fileDataModel = new FileDataModel(new File(itemmodelsPath));
            /*RecommenderEvaluator recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();*/
            RecommenderEvaluator recommenderEvaluator = new RMSRecommenderEvaluator();
            double evaluate = recommenderEvaluator.evaluate(new RecommenderBuilder() {
                @Override
                public org.apache.mahout.cf.taste.recommender.Recommender buildRecommender(final DataModel dataModel) throws TasteException {
                    UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
                    UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
                    return new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
                }
            }, new DataModelBuilder() {
                @Override
                public DataModel buildDataModel(final FastByIDMap<PreferenceArray> fastByIDMap) {
                    for (Map.Entry<Long, PreferenceArray> entry : fastByIDMap.entrySet()) {
                        System.out.println(entry.getKey() + " " + entry.getValue());
                    }
                    return new GenericDataModel(fastByIDMap);
                }
            }, fileDataModel, 0.6, 1.0);
            System.out.println("评估结果:" + evaluate);
        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }
    }

    public void IRState(String taskName) {
        String itemmodelsPath = RecommendConfig.class.getResource("/").getPath() + "itemmodels.csv";
        HadoopUtil.download(taskName, itemmodelsPath, false);
        try {
            DataModel fileDataModel = new FileDataModel(new File(itemmodelsPath));
            RecommenderIRStatsEvaluator irStatsEvaluator = new GenericRecommenderIRStatsEvaluator();
            IRStatistics irStatistics = irStatsEvaluator.evaluate(new RecommenderBuilder() {
                @Override
                public org.apache.mahout.cf.taste.recommender.Recommender buildRecommender(final DataModel dataModel) throws TasteException {
                    UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
                    UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(5, userSimilarity, dataModel);
                    return new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
                }
            }, new DataModelBuilder() {
                @Override
                public DataModel buildDataModel(final FastByIDMap<PreferenceArray> fastByIDMap) {
                    return new GenericDataModel(fastByIDMap);
                }
            }, fileDataModel, null, 5, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
            System.out.println("查准率:" + irStatistics.getPrecision());
            System.out.println("查全率:" + irStatistics.getRecall());
        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }
    }
}
