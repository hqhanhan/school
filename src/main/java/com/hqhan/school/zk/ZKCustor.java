package com.hqhan.school.zk;


import com.alibaba.fastjson.JSONObject;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ZKCustor implements ZKHandler {

    final static Logger log = LoggerFactory.getLogger(ZKCustor.class);

    @Autowired
    private Environment env;

    private CuratorFramework client = null;


    @PostConstruct
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        client = CuratorFrameworkFactory.builder().connectString(env.getProperty("zookeeper.servers"))
                .sessionTimeoutMs(30000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        //FIXME 组装临时节点路径
        String path = getRegisterPath();
        createEphemeralPath(path, buildServerInfo("running"));
    }


    public String createPersistentPath(String path, JSONObject data) {
        try {
            if (client.checkExists().forPath(path) == null) {
                String forPath = client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(path, data.toJSONString().getBytes("UTF-8"));
                log.info("Create persistent successfully path : {}", path);
                return forPath;
            }
        } catch (Exception e) {
            log.error("Create persistent path failed {}", path);
        }
        return "";

    }


    public CuratorFramework getClient(){
        return client;
    }


    public String createEphemeralPath(String path, JSONObject data) {
        try {
            if (client.checkExists().forPath(path) == null) {
                String forPath = client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(path, data.toJSONString().getBytes("UTF-8"));
                log.info("Create ephemera successfully path : {}", path);
                return forPath;
            }
        } catch (Exception e) {
            log.error("Create ephemera path failed {}", path);
        }
        return "";
    }


    public void setDataForPath(String path, String status) {
        try {
            //TODO 更新serverInfo状态
            JSONObject data = new JSONObject();
            data.put("status", status);
            client.setData().forPath(path, data.toJSONString().getBytes("UTF-8"));
            log.info("Set data successfully path : {}", path);
        } catch (Exception e) {
            log.error("Set data for path error ,path {}", path);
        }
    }


    public void deleteForPath(String path) {
        try {
            client.delete().forPath(path);
            log.info("Delete path successfully path : {}", path);
        } catch (Exception e) {
            log.info("Delete path error path : {}", path);
        }
    }


    private JSONObject buildServerInfo(String status) {
        //FIXME 自动获取ip，port
        JSONObject data = new JSONObject();
        data.put("ip", "127.0.0.1");
        data.put("port", "8080");
        data.put("status", status);
        return data;
    }

    private String getRegisterPath() {
        String ip = "127.0.0.1";
        return new StringBuffer(REGISTER_PREFIX)
                .append("/")
                .append(env.getProperty("spring.application.name"))
                .append("/")
                .append(ip)
                .append(":")
                .append(env.getProperty("server.port"))
                .toString();
    }


}
