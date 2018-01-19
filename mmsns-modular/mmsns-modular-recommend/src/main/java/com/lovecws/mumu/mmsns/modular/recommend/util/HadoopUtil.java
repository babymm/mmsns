package com.lovecws.mumu.mmsns.modular.recommend.util;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hadoop工具类
 * @date 2018-01-18 12:43
 */
public class HadoopUtil {

    private static final Logger log = Logger.getLogger(HadoopUtil.class);

    private static PropertyConfig propertyConfig = new PropertyConfig();

    /**
     * 从hadoop中下载文件
     *
     * @param taskName
     * @param filePath
     */
    public static void download(String taskName, String filePath, boolean existDelete) {
        File file = new File(filePath);
        if (file.exists()) {
            if (existDelete) {
                file.deleteOnExit();
            } else {
                return;
            }
        }
        String hadoopAddress = propertyConfig.getProperty("sqoop.task." + taskName + ".tolink.linkConfig.uri");
        String itemmodels = propertyConfig.getProperty("sqoop.task." + taskName + ".recommend.itemmodels");
        try {
            DistributedFileSystem distributedFileSystem = distributedFileSystem(hadoopAddress);
            FSDataInputStream fsDataInputStream = distributedFileSystem.open(new Path(itemmodels));
            byte[] bs = new byte[fsDataInputStream.available()];
            fsDataInputStream.read(bs);
            log.info(new String(bs));

            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            IOUtils.write(bs, fileOutputStream);
            IOUtils.closeQuietly(fileOutputStream);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static DistributedFileSystem distributedFileSystem(String hadoopAddress) {
        DistributedFileSystem distributedFileSystem = new DistributedFileSystem();
        try {
            distributedFileSystem.initialize(new URI(hadoopAddress), new Configuration());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return distributedFileSystem;
    }
}
