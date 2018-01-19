package com.lovecws.mumu.mmsns.modular.recommend.sqoop.linkconfig;

import org.apache.sqoop.model.MLinkConfig;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本jdbc连接
 * @date 2018-01-09 13:19:
 */
public class FtpLinkConfig extends BaseLinkConfig {

    public static final String connectorName = "ftp-connector";

    private String server;

    private String port;

    private String username;

    private String password;

    public FtpLinkConfig(String server, String port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public MLinkConfig linkConfig(MLinkConfig linkConfig) {
        linkConfig.getStringInput("linkConfig.server").setValue(server);
        linkConfig.getIntegerInput("linkConfig.port").setValue(Integer.parseInt(port));
        linkConfig.getStringInput("linkConfig.username").setValue(username);
        linkConfig.getStringInput("linkConfig.password").setValue(password);
        return linkConfig;
    }

    @Override
    public String getConnector() {
        return connectorName;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
