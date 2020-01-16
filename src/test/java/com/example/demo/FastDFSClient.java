package com.example.demo;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class FastDFSClient {
    private static final Logger log = LoggerFactory.getLogger(FastDFSClient.class);
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;

    public FastDFSClient(Properties properties) throws Exception {

        ClientGlobal.initByProperties(properties);
        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getTrackerServer();
    }

    /**
     * 获取 Storage服务,根据tracker.conf中的策略:groupName及store_path
     *
     * @param groupName
     * @return
     * @throws Exception
     */
    public StorageClient1 getStorageClient1(String groupName) throws Exception {
        return new StorageClient1(trackerServer, trackerClient.getStoreStorage(trackerServer, groupName));
    }

    /**
     * 获取 Storage服务,storePathIndex从0开始
     *
     * @param groupName
     * @param storePathIndex
     * @return
     * @throws IOException
     * @throws MyException
     */
    public StorageClient1 getStorageClient1(String groupName, int storePathIndex) throws IOException, MyException {
        StorageServer defaultStoreStorage = trackerClient.getStoreStorage(trackerServer, groupName);
        log.info("getStorePathIndex:" + defaultStoreStorage.getStorePathIndex());
        if (storePathIndex < 0 || storePathIndex == defaultStoreStorage.getStorePathIndex()) {
            return new StorageClient1(trackerServer, defaultStoreStorage);
        } else {
            return new StorageClient1(trackerServer, new StorageServer(defaultStoreStorage.getInetSocketAddress().getAddress().getHostAddress(), defaultStoreStorage.getInetSocketAddress().getPort(), storePathIndex));
        }
    }
}
