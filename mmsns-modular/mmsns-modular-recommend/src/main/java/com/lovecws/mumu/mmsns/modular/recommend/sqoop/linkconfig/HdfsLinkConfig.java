package com.lovecws.mumu.mmsns.modular.recommend.sqoop.linkconfig;

import org.apache.sqoop.model.MLinkConfig;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hdfs link
 * @date 2018-01-17 11:24:
 */
public class HdfsLinkConfig extends BaseLinkConfig {

    public static final String connectorName = "hdfs-connector";

    private String uri;
    private String confDir;

    public HdfsLinkConfig(String uri, String confDir) {
        this.uri = uri;
        this.confDir = confDir;
    }

    @Override
    public MLinkConfig linkConfig(MLinkConfig linkConfig) {
        linkConfig.getStringInput("linkConfig.uri").setValue(uri);
        linkConfig.getStringInput("linkConfig.confDir").setValue(confDir);
        return linkConfig;
    }

    @Override
    public String getConnector() {
        return connectorName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getConfDir() {
        return confDir;
    }

    public void setConfDir(String confDir) {
        this.confDir = confDir;
    }
}
