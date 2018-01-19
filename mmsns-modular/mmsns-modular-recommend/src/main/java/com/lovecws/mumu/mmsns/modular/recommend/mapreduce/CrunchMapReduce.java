package com.lovecws.mumu.mmsns.modular.recommend.mapreduce;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import org.apache.crunch.Pipeline;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.parquet.AvroParquetFileSourceTarget;
import org.apache.hadoop.conf.Configuration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 使用crunch来实现mapreduce
 * @date 2018-01-18 09:30:
 */
public class CrunchMapReduce extends AbstractMapReduce {

    private static final PropertyConfig propertyConfig = new PropertyConfig();

    @Override
    public void startMapReduce(String taskName) {
        String outputDirectory = propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.outputDirectory");
        String hadoopAddress = propertyConfig.getProperty("sqoop.task." + taskName + ".tolink.linkConfig.uri");
        Pipeline pipeline = new MRPipeline(CrunchMapReduce.class, new Configuration());
        Class<AvroParquetFileSourceTarget> avroParquetFileSourceTargetClass = AvroParquetFileSourceTarget.class;
    }
}
