package com.example.demo;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class DfsTest {

    String trackerServers = "192.168.1.6:22122";

    @Ignore
    @Test
    public void uploadTest() {
        Properties properties = new Properties();
        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServers);
        try {
            FastDFSClient fastDFSClient = new FastDFSClient(properties);
            File file = new File("E:\\test.txt");
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            byte[] bytes = toByteArray(file);
            NameValuePair[] metaList = new NameValuePair[2];
            //这是个数组，可以继续添加
            metaList[0] = new NameValuePair("fileName", fileName);
            metaList[1] = new NameValuePair("category", "advertisement");
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, suffix, metaList));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, suffix, metaList));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, suffix, metaList));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, suffix, metaList));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group2").upload_file1(bytes, suffix, metaList));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group3").upload_file1(bytes, suffix, metaList));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Ignore
    @Test
    public void testQuery() {
        Properties properties = new Properties();
        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServers);
        try {
            FastDFSClient fastDFSClient = new FastDFSClient(properties);
            //定义storage的客户端
            StorageClient1 client = fastDFSClient.getStorageClient1("group2");
            FileInfo fileInfo1 = client.query_file_info("group2", "M00/00/00/CgkQTl4dd3qANg1dAAAy_nv9wkc515.txt");
            FileInfo fileInfo2 = client.query_file_info1("group2/M00/00/00/CgkQTl4dd3qANg1dAAAy_nv9wkc515.txt");
            System.out.println(fileInfo1);
            System.out.println(fileInfo2);
            //查询文件元信息
            NameValuePair[] metadata1 = client.get_metadata1("group2/M00/00/00/CgkQTl4dd3qANg1dAAAy_nv9wkc515.txt");
            for (NameValuePair temp : metadata1) {
                System.out.println(temp.getName() + "\t" + temp.getValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void testDownLoad() {
        Properties properties = new Properties();
        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServers);
        try {
            FastDFSClient fastDFSClient = new FastDFSClient(properties);
            //定义storage的客户端
            StorageClient1 client = fastDFSClient.getStorageClient1("group2");
            //下载
            byte[] bytes = client.download_file1("group2/M00/00/00/CgkQTl4dd3qANg1dAAAy_nv9wkc515.txt");
            File file = new File("e:/456.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*读取文件的字节数组*/
    public static byte[] toByteArray(File file) throws IOException {
        File f = file;
        if (!f.exists()) {
            throw new FileNotFoundException("file not exists");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
}
