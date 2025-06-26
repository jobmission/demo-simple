package com.example.demo;

import com.example.demo.config.CachesEnum;
import com.example.demo.persistence.entity.ArticleEntity;
import com.example.demo.persistence.entity.ArticleEntityExample;
import com.example.demo.persistence.entity.PersonEntity;
import com.example.demo.persistence.mapper.ArticleEntityMapper;
import com.example.demo.persistence.mapper.CustomMapper;
import com.example.demo.persistence.mapper.GenericMapper;
import com.example.demo.persistence.mapper.PersonEntityMapper;
import com.example.demo.service.ProcessService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    ArticleEntityMapper articleMapper;

    @Autowired
    PersonEntityMapper personEntityMapper;

    @Autowired
    GenericMapper genericMapper;

    @Autowired
    ProcessService processService;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    CustomMapper customMapper;


    @Test
    @Disabled
    public void contextLoads() {
        System.out.println("count:" + articleMapper.countByExample(new ArticleEntityExample()));

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthor("zhangsan");
        articleEntity.setTitle("下大雨了");
        articleEntity.setTags("天气,实时,民生");
        TempDomain a = new TempDomain();
        a.setA(1);
        a.setB(1);
        articleEntity.setPj(a.toString());
        articleEntity.setRecordStatus(1);
        articleEntity.setSortPriority(1);
        try {
            articleMapper.insertSelective(articleEntity);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ArticleEntityExample articleEntityExample = new ArticleEntityExample();

        List<ArticleEntity> list = articleMapper.selectByExample(articleEntityExample);
        list.forEach(System.out::println);

        System.out.println();

        List<Map<String, Object>> mapList = customMapper.selectAll();
        System.out.println(mapList);

    }

    @Test
    @Disabled
    public void genericMapperTest() {

        Map<String, Object> paramsMapWithSql = new HashMap<>();
        String sql = "select *from person_entity " +
            "<where>" +
            "<if test=\"name != null\">" +
            "     AND   name = #{name,jdbcType=VARCHAR}" +
            "</if>" +
            "<if test=\"gender != null\">" +
            "     AND   gender = #{gender,jdbcType=INTEGER}" +
            "</if>" +
            "</where>";

//        paramsMapWithSql.put("name", "e");
        paramsMapWithSql.put("gender", 2);
        paramsMapWithSql.put("sql", sql);

        List<LinkedHashMap<String, Object>> list = genericMapper.queryForList(paramsMapWithSql);
        list.forEach(System.out::println);

    }


    @Disabled
    @Test
    public void insertArticle() {

        Random random = new Random();

        List<String> tags = new ArrayList<>();
        tags.add("实时");
        tags.add("科技");
        tags.add("互联网");
        tags.add("财经");
        tags.add("股票");
        tags.add("娱乐");
        tags.add("原创");

        List<ArticleEntity> articleEntityList = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setAuthor("wangwu");
            articleEntity.setTitle(RandomStringUtils.secure().nextAlphanumeric(8));
            articleEntity.setTags(tags.get(random.nextInt(6)) + "," + tags.get(random.nextInt(6)) + "," + tags.get(random.nextInt(6)) + "," + tags.get(random.nextInt(6)));
            articleEntity.setContent(RandomStringUtils.secure().nextAlphanumeric(30));
            articleEntity.setRecordStatus(1);
            TempDomain a = new TempDomain();
            a.setA(random.nextInt(20));
            a.setB(random.nextInt(20));
            articleEntity.setPj(a.toString());
            articleEntity.setRecordStatus(1);
            articleEntityList.add(articleEntity);

            if (articleEntityList.size() % 5000 == 0) {
                articleMapper.batchInsert(articleEntityList);
                articleEntityList.clear();
            }
        }

        if (!articleEntityList.isEmpty()) {
            articleMapper.batchInsert(articleEntityList);
        }

    }

    @Disabled
    @Test
    public void selectArticle() {
        ArticleEntityExample articleEntityExample = new ArticleEntityExample();
        articleEntityExample
            .createCriteria()
            .andConditionValue("pj->'$.a' =", 1)
            .andFunctionRightKey("find_in_set", "tags", "实时");

        articleEntityExample.setCommaSeparatedColumns("id");
        PageHelper.startPage(10000, 10, false);
        List<ArticleEntity> list = articleMapper.selectColumnsByExample(articleEntityExample);

        list.forEach(e -> {
            System.out.println("======================\n" + e);
        });

        System.out.println();
    }

    @Disabled
    @Test
    public void insertPerson() {
        PersonEntity personEntity = new PersonEntity();

        LocalDate date = LocalDate.of(1988, 6, 6);
        personEntity.setBirthday(date);
        personEntity.setDateCreated(LocalDateTime.now().minusDays(5));
        personEntity.setLastModified(LocalDateTime.now().plusDays(5));
        personEntityMapper.insertSelective(personEntity);
    }

    @Disabled
    @Test
    public void logicalDeleteTest() {
        processService.processTransaction();
//        int b = 1 / 0;
    }

    @Disabled
    @Test
    public void cacheTest() {
        cacheManager.getCache(CachesEnum.KeywordCache.name()).put("a", null);
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(CachesEnum.KeywordCache.name()).get("a");
        System.out.println("valueWrapper:" + valueWrapper.get());
        cacheManager.getCache(CachesEnum.KeywordCache.name()).put("a", "123");
        valueWrapper = cacheManager.getCache(CachesEnum.KeywordCache.name()).get("a");
        System.out.println("valueWrapper:" + valueWrapper.get());
        valueWrapper = cacheManager.getCache(CachesEnum.KeywordCache.name()).get("b");
        System.out.println("valueWrapper:" + valueWrapper);
        valueWrapper = cacheManager.getCache("CachesEnum.KeywordCache.name()").get("b");
        System.out.println("valueWrapper:" + valueWrapper);
    }
}
