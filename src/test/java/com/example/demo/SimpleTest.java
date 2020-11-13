package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class SimpleTest {

    @Disabled
    @Test
    public void quickSortTest() {
        int[] arr = {10, 7, 2, 4, 7, 62, 3, 4, 2, 1, 8, 9, 19};
        quickSort(arr, 0, arr.length - 1);

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

    }

    public void quickSort(int[] arr, int begin, int end) {
        int i, j, base, swap;
        if (begin > end) {
            return;
        }
        i = begin;
        j = end;
        // base?????,???????
        base = arr[begin];

        while (i < j) {
            // ???????????
            while (base <= arr[j] && i < j) {
                j--;
            }
            // ???????????
            while (base >= arr[i] && i < j) {
                i++;
            }
            // ?????????
            if (i < j) {
                swap = arr[j];
                arr[j] = arr[i];
                arr[i] = swap;
            }

        }
        // ???????i?j?????????
        arr[begin] = arr[i];
        arr[i] = base;
        // ????????
        quickSort(arr, begin, j - 1);
        // ????????
        quickSort(arr, j + 1, end);

    }

    @Disabled
    @Test
    public void printChinese() throws UnsupportedEncodingException {

        long seed = System.currentTimeMillis();
        Random random = new Random(seed); // 随机数生成器
        for (int i = 0; i < 100; i++) {
            System.out.println(getChineseCharacter(random.nextInt(93), random.nextInt(39)));
        }

    }

    public String getChineseCharacter(int lowPos, int highPos) throws UnsupportedEncodingException {
        String str = null; // 保存结果

        byte[] b = new byte[2]; // 转化为B类型

        b[0] = Integer.valueOf(176 + highPos).byteValue(); // 高字节

        b[1] = Integer.valueOf(161 + lowPos).byteValue(); // 低字节

        str = new String(b, "GBK");

        return str;
    }


}
