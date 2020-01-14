package com.example.demo;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.util.Properties;

public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;

    public FastDFSClient(Properties properties) throws Exception {

        ClientGlobal.initByProperties(properties);
        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getTrackerServer();

    }

    public StorageClient1 getStorageClient1(String groupName) throws Exception {
        return new StorageClient1(trackerServer, trackerClient.getStoreStorage(trackerServer, groupName));
    }

    /**
     * 得到Storage服务
     *
     * @param groupName
     * @param storePathIndex
     * @return
     */
    public StorageClient1 getStorageClient1(String groupName, int storePathIndex) {
        try {
            StorageServer defaultStoreStorage = trackerClient.getStoreStorage(trackerServer, groupName);
            if (defaultStoreStorage != null) {
                return new StorageClient1(trackerServer, new StorageServer(defaultStoreStorage.getInetSocketAddress().getAddress().getHostAddress(), defaultStoreStorage.getInetSocketAddress().getPort(), storePathIndex));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
