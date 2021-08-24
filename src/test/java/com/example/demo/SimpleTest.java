package com.example.demo;

import com.example.demo.persistence.entity.ArticleEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Disabled
    @Test
    public void name() {
        String sql = "w select *  from a where b=1 order by id".toLowerCase();

        Pattern p1 = Pattern.compile(".*select.*", Pattern.DOTALL);
        Pattern p2 = Pattern.compile("(\\s*explain)?\\s*select[^;]*;?\\s*", Pattern.DOTALL);

        System.out.println(p1.matcher(sql).matches());
        System.out.println(p2.matcher(sql).matches());
    }


    @Disabled
    @Test
    public void lambdaTest() {

        ArticleEntity entity1 = new ArticleEntity();
        entity1.setAuthor("zhangsan");
        entity1.setTitle("下大雨了");
        entity1.setTags("天气");
        ArticleEntity entity2 = new ArticleEntity();
        entity2.setAuthor("zhangsan");
        entity2.setTitle("下大雨了");
        entity2.setTags("天气预报");

        ArticleEntity entity3 = new ArticleEntity();
        entity3.setAuthor("lisi");
        entity3.setTitle("下大雨了");
        entity3.setTags("天气报告");

        List<ArticleEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);
        entityList.add(entity3);

        TreeMap<String, List<ArticleEntity>> treeMap = entityList.stream().collect(Collectors.groupingBy(this::functionA, TreeMap::new, Collectors.toList()));

        System.out.println(treeMap);
    }

    String functionA(ArticleEntity articleEntity) {
        return StringUtils.substring(articleEntity.getTags(), 0, 3);
    }

}
