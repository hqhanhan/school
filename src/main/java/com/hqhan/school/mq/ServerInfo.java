package com.hqhan.school.mq;

/**
 * 〈description〉<br>
 * 〈各服务工作情况〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-27 20:31
 */
public class ServerInfo {

    private String ip;
    private String port;
    private String status;
    private String disk;
    private String memory;
    //TODO other

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", status='" + status + '\'' +
                ", disk='" + disk + '\'' +
                ", memory='" + memory + '\'' +
                '}';
    }
}
