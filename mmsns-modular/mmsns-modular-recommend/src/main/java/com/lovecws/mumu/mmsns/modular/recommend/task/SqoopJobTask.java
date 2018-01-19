package com.lovecws.mumu.mmsns.modular.recommend.task;

import com.lovecws.mumu.mmsns.modular.recommend.config.PropertyConfig;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.SqoopJobConfig;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.SqoopJobFactory;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.SqoopLinkConfig;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.SqoopLinkFactory;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.jobconfig.*;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.linkconfig.*;
import com.lovecws.mumu.mmsns.modular.recommend.sqoop.util.SqoopUtil;
import org.apache.log4j.Logger;
import org.apache.sqoop.client.SqoopClient;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: sqoop任务
 * @date 2018-01-17 11:05:
 */
public class SqoopJobTask {

    private static final Logger log = Logger.getLogger(SqoopJobTask.class);

    private static final PropertyConfig propertyConfig = new PropertyConfig();

    public void startSqoopJob(String taskName, boolean force) {
        String sqoopTasks = propertyConfig.getProperty("sqoop.tasks");
        if (sqoopTasks == null || !sqoopTasks.contains(taskName)) {
            throw new RuntimeException("配置文件错误[任务不存在]");
        }
        String sqoopUrl = propertyConfig.getProperty("sqoop.url");
        SqoopClient sqoopClient = new SqoopClient(sqoopUrl);
        if (force) {
            SqoopUtil.deleteAll(sqoopClient);
        }

        String jobName = propertyConfig.getProperty("sqoop.task." + taskName + ".jobname");
        boolean checkExistsJob = SqoopUtil.checkExistsJob(sqoopClient, jobName);
        if (!checkExistsJob) {
            createJob(sqoopClient, taskName, jobName);
        }
        SqoopUtil.startJob(sqoopClient, jobName);
    }

    /**
     * 创建sqoop工作
     *
     * @param sqoopClient
     * @param taskName
     * @param jobName
     */
    private void createJob(SqoopClient sqoopClient, String taskName, String jobName) {
        String creationUser = propertyConfig.getProperty("sqoop.task." + taskName + ".creationUser");

        //fromlink
        String fromlink = propertyConfig.getProperty("sqoop.task." + taskName + ".fromlink");
        String fromlinkConnector = propertyConfig.getProperty("sqoop.task." + taskName + ".fromlink.connector");
        if (!SqoopUtil.checkExists(sqoopClient, fromlink)) {
            createLink(sqoopClient, taskName, creationUser, fromlink, fromlinkConnector, "fromlink");
        }

        //tolink
        String tolink = propertyConfig.getProperty("sqoop.task." + taskName + ".tolink");
        String tolinkConnector = propertyConfig.getProperty("sqoop.task." + taskName + ".tolink.connector");
        if (!SqoopUtil.checkExists(sqoopClient, tolink)) {
            createLink(sqoopClient, taskName, creationUser, tolink, tolinkConnector, "tolink");
        }

        BaseJobConfig fromJobConfig = createJobConfig(taskName, fromlinkConnector, "fromJobConfig");
        BaseJobConfig toJobConfig = createJobConfig(taskName, tolinkConnector, "toJobConfig");

        SqoopJobConfig sqoopJobConfig = new SqoopJobConfig(jobName,
                creationUser,
                fromJobConfig,
                toJobConfig);

        SqoopJobFactory sqoopJobFactory = new SqoopJobFactory(sqoopClient,
                sqoopJobConfig,
                fromlink,
                tolink);
        sqoopJobFactory.instance();
    }

    /**
     * 创建sqoop连接
     *
     * @param sqoopClient
     * @param taskName
     * @param creationUser
     * @param linkname
     * @param linkConnector
     * @param linkwhere
     */
    private void createLink(SqoopClient sqoopClient, String taskName, String creationUser, String linkname, String linkConnector, String linkwhere) {
        if (linkConnector == null) {
            return;
        }
        BaseLinkConfig linkConfig = null;
        switch (linkConnector) {
            case HdfsLinkConfig.connectorName:
                linkConfig = new HdfsLinkConfig(propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.uri"),
                        propertyConfig.getProperty("sqoop.task." + taskName + ".fromlink.linkConfig.confDir"));
                break;
            case JdbcLinkConfig.connectorName:
                linkConfig = new JdbcLinkConfig(propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.connectionString"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.jdbcDriver"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.username"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.password"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.identifierEnclose"));
                break;
            case FtpLinkConfig.connectorName:
                linkConfig = new FtpLinkConfig(propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.server"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.port"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.username"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.password"));
                break;
            case KafkaLinkConfig.connectorName:
                linkConfig = new KafkaLinkConfig(propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.brokerList"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.zookeeperConnect"));
                break;
            case KiteLinkConfig.connectorName:
                linkConfig = new KiteLinkConfig(propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.authority"),
                        propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".linkConfig.confDir"));
                break;
        }
        SqoopLinkFactory sqoopLinkFactory = new SqoopLinkFactory(sqoopClient,
                new SqoopLinkConfig(linkname, creationUser, linkConfig));
        sqoopLinkFactory.instance();
    }

    /**
     * 创建sqoop工作配置信息
     *
     * @param taskName
     * @param linkConnector
     * @param linkwhere
     * @return
     */
    private BaseJobConfig createJobConfig(String taskName, String linkConnector, String linkwhere) {
        if (linkConnector == null) {
            return null;
        }
        BaseJobConfig baseJobConfig = null;
        switch (linkConnector) {
            case HdfsLinkConfig.connectorName:
                if ("fromJobConfig".equals(linkwhere)) {
                    baseJobConfig = new HdfsJobConfig(propertyConfig.getProperty("sqoop.task." + taskName + ".fromJobConfig.inputDirectory"),
                            Boolean.valueOf(propertyConfig.getProperty("sqoop.task." + taskName + ".fromJobConfig.nullValue")),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".fromJobConfig.overrideNullValue"));
                } else if ("toJobConfig".equals(linkwhere)) {
                    baseJobConfig = new HdfsJobConfig(propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.outputDirectory"),
                            Boolean.valueOf(propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.overrideNullValue")),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.nullValue"),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.outputFormat"),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.compression"),
                            Boolean.valueOf(propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.appendMode")));
                }
                break;
            case JdbcLinkConfig.connectorName:
                if ("fromJobConfig".equals(linkwhere)) {
                    baseJobConfig = new JdbcJobConfig(propertyConfig.getProperty("sqoop.task." + taskName + ".fromJobConfig.schemaName"),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".fromJobConfig.tableName"),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".fromJobConfig.columnList"));
                } else if ("toJobConfig".equals(linkwhere)) {
                    baseJobConfig = new JdbcJobConfig(propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.schemaName"),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.tableName"),
                            propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.columnList"));
                }
                break;
            case FtpLinkConfig.connectorName:
                break;
            case KafkaLinkConfig.connectorName:
                baseJobConfig = new KakfaJobConfig(propertyConfig.getProperty("sqoop.task." + taskName + ".toJobConfig.topic"));
                break;
            case KiteLinkConfig.connectorName:
                baseJobConfig = new KiteJobConfig(propertyConfig.getProperty("sqoop.task." + taskName + "." + linkwhere + ".uri"));
                break;
        }
        return baseJobConfig;
    }
}
