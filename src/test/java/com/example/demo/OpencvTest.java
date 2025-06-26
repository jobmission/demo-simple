//package com.example.demo;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//
//public class OpencvTest {
//
//    static {
//        // 方式1：自动加载（需配置环境变量）
//         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        // 方式2：指定路径加载（推荐）
////        System.load("C:\\programs\\opencv\\build\\java\\x64\\opencv_java4110.dll");
//    }
//
//    @Disabled
//    @Test
//    void test1() {
//
//        // 读取图像
//        Mat image = Imgcodecs.imread("C:\\tmp\\1.jpeg"); // 使用绝对路径
//        if (image.empty()) {
//            System.out.println("图像加载失败！");
//            return;
//        }
//
//        Mat grayImage = new Mat();
//        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY); // 转灰度
//        Imgcodecs.imwrite("C:\\tmp\\2.jpeg", grayImage); // 保存
//    }
//}
