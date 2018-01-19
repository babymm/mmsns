package com.lovecws.mumu.mmsns.modular.recommend.sqoop.util;

import org.apache.log4j.Logger;
import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.model.MLink;
import org.apache.sqoop.model.MSubmission;
import org.apache.sqoop.submission.counter.Counter;
import org.apache.sqoop.submission.counter.CounterGroup;
import org.apache.sqoop.submission.counter.Counters;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: link工具包
 * @date 2018-01-09 13:38:
 */
public class SqoopUtil {

    private static final Logger log = Logger.getLogger(SqoopUtil.class);

    /**
     * 检测link是否存在
     *
     * @param sqoopClient
     * @param linkName
     * @return
     */
    public static boolean checkExists(SqoopClient sqoopClient, String linkName) {
        List<MLink> links = sqoopClient.getLinks();
        for (MLink Link : links) {
            if (Link.getName().equals(linkName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测工作是否存在
     *
     * @param jobName
     * @return
     */
    public static boolean checkExistsJob(SqoopClient sqoopClient, String jobName) {
        List<MJob> jobs = sqoopClient.getJobs();
        for (MJob job : jobs) {
            if (job.getName().equals(jobName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 开始执行任务
     *
     * @param jobName
     */
    public static void startJob(SqoopClient sqoopClient, String jobName) {
        MSubmission submission = sqoopClient.startJob(jobName);
        log.info("任务执行结果: " + submission.getStatus());
        while (submission.getStatus().isRunning() && submission.getProgress() != -1) {
            log.info("进度 : " + String.format("%.2f %%", submission.getProgress() * 100));
        }

        log.info("hadoop任务ID :" + submission.getExternalJobId());
        log.info("任务连接 : " + submission.getExternalLink());
        Counters counters = submission.getCounters();
        if (counters != null) {
            log.info("任务计数器:");
            for (CounterGroup group : counters) {
                log.info(group.getName());
                for (Counter counter : group) {
                    log.info(counter.getName() + " : " + counter.getValue());
                }
            }
        }
    }

    public static void deleteAll(SqoopClient sqoopClient) {
        sqoopClient.deleteAllLinksAndJobs();
    }
}
