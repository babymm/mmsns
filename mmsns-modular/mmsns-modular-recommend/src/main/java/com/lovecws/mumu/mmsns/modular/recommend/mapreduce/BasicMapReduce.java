package com.lovecws.mumu.mmsns.modular.recommend.mapreduce;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import com.lovecws.mumu.mmsns.modular.recommend.util.HadoopUtil;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.GroupReadSupport;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本mapreduce实现
 * @date 2018-01-18 09:05:
 */
public class BasicMapReduce extends AbstractMapReduce {

    private static final Logger log = Logger.getLogger(BasicMapReduce.class);
    private static final PropertyConfig propertyConfig = new PropertyConfig();

    @Override
    public void startMapReduce(String taskName) {
        String outputDirectory = propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.outputDirectory");
        String hadoopAddress = propertyConfig.getProperty("sqoop.task." + taskName + ".tolink.linkConfig.uri");
        DistributedFileSystem distributedFileSystem = HadoopUtil.distributedFileSystem(hadoopAddress);
        try {
            FileStatus[] fileStatuses = distributedFileSystem.listStatus(new Path(outputDirectory));
            for (FileStatus fileStatus : fileStatuses) {
                log.info(fileStatus);
                if (fileStatus.isFile()) {
                    GroupReadSupport readSupport = new GroupReadSupport();
                    ParquetReader<Group> reader = new ParquetReader<Group>(fileStatus.getPath(), readSupport);
                    Group group = null;
                    while ((group = reader.read()) != null) {
                        int article_id = group.getInteger("article_id", 0);
                        int sys_category_id = group.getInteger("sys_category_id", 0);
                        int user_category_id = group.getInteger("user_category_id", 0);
                        String article_labels = group.getString("article_labels", 0);

                        storeRecord(article_id, "sc" + sys_category_id, 3);
                        storeRecord(article_id, "uc" + user_category_id, 4);
                        if (article_labels != null) {
                            for (String articleLabel : article_labels.split("\\s+")) {
                                storeRecord(article_id, articleLabel, 5);
                            }
                        }
                    }
                }
            }
            storeData(taskName);
        } catch (IOException e) {
            log.error(e);
        } finally {
            try {
                distributedFileSystem.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
