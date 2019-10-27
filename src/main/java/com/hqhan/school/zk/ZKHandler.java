package com.hqhan.school.zk;

import java.util.Arrays;
import java.util.List;

public interface ZKHandler {

    /**
     * 各服务在zookeeper中注册node的前缀
     */
    String REGISTER_PREFIX = "/Server/Register";

    /**
     * 各服务节点状态
     */
    String IDLE = "idle";
    String RUNNING = "running";
    String ERROR = "error";
    List<String> SERVER_STATUS_LIST = Arrays.asList(IDLE, RUNNING, ERROR);

}
