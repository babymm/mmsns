package com.lovecws.mumu.mmsns.modular.recommend.mapreduce;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import com.lovecws.mumu.mmsns.modular.recommend.mapreduce.data.ItemMap;
import com.lovecws.mumu.mmsns.modular.recommend.mapreduce.data.ItemModel;
import com.lovecws.mumu.mmsns.modular.recommend.task.MapreduceTask;
import com.lovecws.mumu.mmsns.modular.recommend.util.HadoopUtil;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description:
 * @date 2018-01-18 08:55:
 */
public abstract class AbstractMapReduce {

    private static final Logger log = Logger.getLogger(MapreduceTask.class);
    private static final PropertyConfig propertyConfig = new PropertyConfig();

    public ItemMap itemMap = new ItemMap();
    public ItemModel itemModel = new ItemModel();

    public abstract void startMapReduce(String taskName);


    public void storeRecord(Integer uid, String item, float preference) {
        Integer itemId = itemMap.put(item);
        itemModel.put(new ItemModel(uid, itemId, preference));
    }

    public void storeData(String taskName) {
        String hadoopAddress = propertyConfig.getProperty("sqoop.task." + taskName + ".tolink.linkConfig.uri");
        DistributedFileSystem distributedFileSystem = HadoopUtil.distributedFileSystem(hadoopAddress);
        //存储itemmap
        String itemmapdir = propertyConfig.getProperty("sqoop.task." + taskName + ".recommend.itemmap");
        FSDataOutputStream fsDataOutputStream = null;
        try {
            fsDataOutputStream = distributedFileSystem.create(new Path(itemmapdir), true);

            fsDataOutputStream.writeBytes(itemMap.value());
            fsDataOutputStream.close();

            //存储itemmodellist
            String itemmodellistdir = propertyConfig.getProperty("sqoop.task." + taskName + ".recommend.itemmodels");
            FSDataOutputStream fsDataOutputStream1 = distributedFileSystem.create(new Path(itemmodellistdir), true);
            fsDataOutputStream1.writeBytes(itemModel.value());
            fsDataOutputStream1.close();
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
