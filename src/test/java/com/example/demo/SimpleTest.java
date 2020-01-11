package com.example.demo;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import net.minidev.json.JSONArray;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

public class SimpleTest {

    @Ignore
    @Test
    public void quickSortTest() {
        int[] arr = { 10, 7, 2, 4, 7, 62, 3, 4, 2, 1, 8, 9, 19 };
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

    @Ignore
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

        b[0] = (new Integer(176 + highPos)).byteValue(); // 高字节

        b[1] = (new Integer(161 + lowPos)).byteValue(); // 低字节

        str = new String(b, "GBK");

        return str;
    }

    @Ignore
    @Test
    public void jsonpath() {
        String json = "{\n" + "    \"store\": {\n" + "        \"book\": [\n" + "            {\n"
                + "                \"category\": \"reference\",\n" + "                \"author\": \"Nigel Rees\",\n"
                + "                \"title\": \"Sayings of the Century\",\n" + "                \"price\": 8.95\n"
                + "            },\n" + "            {\n" + "                \"category\": \"fiction\",\n"
                + "                \"author\": \"Evelyn Waugh\",\n"
                + "                \"title\": \"Sword of Honour\",\n" + "                \"price\": 12.99\n"
                + "            },\n" + "            {\n" + "                \"category\": \"fiction\",\n"
                + "                \"author\": \"Herman Melville\",\n" + "                \"title\": \"Moby Dick\",\n"
                + "                \"isbn\": \"0-553-21311-3\",\n" + "                \"price\": 8.99\n"
                + "            },\n" + "            {\n" + "                \"category\": \"fiction\",\n"
                + "                \"author\": \"J. R. R. Tolkien\",\n"
                + "                \"title\": \"The Lord of the Rings\",\n"
                + "                \"isbn\": \"0-395-19395-8\",\n" + "                \"price\": 22.99\n"
                + "            }\n" + "        ],\n" + "        \"bicycle\": {\n" + "            \"color\": \"red\",\n"
                + "            \"price\": 19.95\n" + "        },\n" + "        \"realm_access\":{\n"
                + "            \"roles\":[\"a\",\"b\",\"c\"]\n" + "        },\n" + "        \"resource_access\":{\n"
                + "            \"roles\":[\"c\",\"d\",\"e\",\"f\"]\n" + "        },\n"
                + "        \"roles\": [\"h\",\"i\"]\n" + "    },\n" + "    \"expensive\": 10,\n"
                + "\"name\": \"zhangsan\",\n" + "\"todo\": [\"zhangsan\",\"lisi\",\"wangwu\"],\n"
                + "\"roles\": [\"zhangsan\",\"lisi\",\"wangwu\"]\n" + "}";

        Configuration conf = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        List<String> result1 = JsonPath.read(document, "$.store.book[*].author");
        Assert.assertNotNull(result1);
        List<Object> result2 = JsonPath.using(conf).parse(document).read("$..roles");
        Assert.assertNotNull(result2);
        List<String> result3 = JsonPath.read(document, "$..resource_access.roles");
        Assert.assertNotNull(result3);
        List<String> result4 = JsonPath.read(document, "$.store.resource_access.roles");
        Assert.assertNotNull(result4);
        List<String> result5 = JsonPath.read(document, "$.*.resource_access.roles");
        Assert.assertNotNull(result5);
        List<String> result6 = JsonPath.read(document, "$.store..price");
        Assert.assertNotNull(result6);
        Object result7 = JsonPath.using(conf).parse(document).read("$.name");
        Assert.assertNotNull(result7);
        Object result8 = JsonPath.using(conf).parse(document).read("$.todo");
        Assert.assertNotNull(result8);
        Object result9 = JsonPath.using(conf).parse(document).read("$.todo33.aa");
        Assert.assertEquals(result9, null);

        result2.forEach(o -> {
            if (o instanceof JSONArray) {
                System.out.println(o);
            } else {
                System.out.println(o);
            }
        });

    }
}
