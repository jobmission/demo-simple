package com.example.demo;

import org.csource.common.MyException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

public class DfsTest {

    @Ignore
    @Test
    public void uploadTest() {
        try {
            FastDFSClient fastDFSClient = new FastDFSClient();
            File file = new File("E:\\tmp\\data-2019-08-12.log");
            byte[] bytes = toByteArray(file);
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, "txt", null));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, "txt", null));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, "txt", null));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group1").upload_file1(bytes, "txt", null));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group2").upload_file1(bytes, "txt", null));
            System.out.println("newName: " + fastDFSClient.getStorageClient1("group3").upload_file1(bytes, "txt", null));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
