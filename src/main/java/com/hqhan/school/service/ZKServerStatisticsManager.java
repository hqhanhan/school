package com.hqhan.school.service;

import com.alibaba.fastjson.JSONObject;
import com.hqhan.school.mq.ServerInfo;
import com.hqhan.school.zk.ZKCustor;
import com.hqhan.school.zk.ZKHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈description〉<br>
 * 〈统计服务工作情况〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-27 20:30
 */

@Service
public class ZKServerStatisticsManager {

    final static Logger log = LoggerFactory.getLogger(ZKServerStatisticsManager.class);

    @Autowired
    private ZKCustor zkCustor;

    /**
     * description: <br>
     * 〈根据服务名称，服务状态，获取各服务节点信息〉
     *
     * @param serverName 服务名称
     * @param status     服务状态    idle节点空闲，running节点忙碌，error节点错误（但未宕机）
     * @return java.util.Map<java.lang.String, java.util.List < com.hqhan.school.mq.ServerInfo>>
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/27 22:40
     */
    public Map<String, List<ServerInfo>> getAliveServerList(String serverName, String status) {
        Map<String, List<ServerInfo>> result = new HashMap();
        List<String> serverNameList = getServerNameList();
        if (CollectionUtils.isEmpty(serverNameList)) {
            return result;
        }
        //获取部分指定服务部署信息
        if (!StringUtils.isEmpty(serverName) && serverNameList.contains(serverName)) {
            serverNameList = Arrays.asList(serverName);
        }
        serverNameList.forEach(sn -> {
            List<ServerInfo> serverInfoList = new ArrayList();
            String serverPath = new StringBuffer(ZKCustor.REGISTER_PREFIX).append("/").append(sn).toString();
            try {
                List<String> serverList = zkCustor.getClient().getChildren().forPath(serverPath);
                if (!CollectionUtils.isEmpty(serverList)) {
                    serverList.forEach(ipPort -> {
                        String node = new StringBuffer(serverPath).append("/").append(ipPort).toString();
                        try {
                            byte[] bytes = zkCustor.getClient().getData().forPath(node);
                            ServerInfo serverInfo = JSONObject.parseObject(new String(bytes, "UTF-8"), ServerInfo.class);
                            serverInfoList.add(serverInfo);
                        } catch (Exception e) {
                            log.error("Get data for path error path={}", node);
                        }
                    });
                }
            } catch (Exception e) {
                log.error("Get Server node error, path={}", serverPath, e);
            }
            List<ServerInfo> finalList = serverInfoList;
            finalList = StringUtils.isEmpty(status) || !ZKHandler.SERVER_STATUS_LIST.contains(status) ? finalList : finalList.stream().filter(serverInfo -> status.equals(serverInfo.getStatus())).collect(Collectors.toList());
            result.put(sn, finalList);
        });
        return result;
    }

    /**
     * description: <br>
     * 〈 获取注册的服务名称〉
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/27 22:42
     */
    public List<String> getServerNameList() {
        List<String> serverNameList = new ArrayList<>();
        try {
            serverNameList = zkCustor.getClient().getChildren().forPath(ZKCustor.REGISTER_PREFIX);
        } catch (Exception e) {
            log.error("Get Server parent node error, path={}", ZKCustor.REGISTER_PREFIX);
        }
        return serverNameList;
    }

}
