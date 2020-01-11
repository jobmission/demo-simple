package com.example.demo;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;

    public FastDFSClient() throws Exception {
        String configPath = this.getClass().getClassLoader().getResource("fastdfs-client.properties").getFile();
        System.out.println(configPath);
        ClientGlobal.initByProperties(configPath);

        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getTrackerServer();

    }

    public StorageClient1 getStorageClient1(String groupName) throws Exception {
        return new StorageClient1(trackerServer, trackerClient.getStoreStorage(trackerServer, groupName));
    }

    public StorageClient1 getStorageClient1() throws Exception {
        return new StorageClient1(trackerServer, trackerClient.getStoreStorage(trackerServer));
    }

}
