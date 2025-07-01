package com.example.demo.controller;

import com.example.demo.persistence.entity.ArticleEntity;
import com.example.demo.persistence.entity.ArticleEntityExample;
import com.example.demo.persistence.mapper.ArticleEntityMapper;
import com.example.demo.service.ArticleService;
import com.example.demo.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.time.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ArticleEntityMapper articleMapper;

    @Autowired
    ArticleService articleService;

    @Autowired
    DataSource dataSource;

    @ResponseBody
    @GetMapping("/t1")
    public String t1() {

        log.info("系统info，info");
        log.warn("系统warn，warn");
        log.error("系统error，error");

        return "" + articleMapper.countByExample(new ArticleEntityExample());
    }

    @ResponseBody
    @GetMapping("/t3")
    public LocalDateTime t3() {
        return LocalDateTime.now();
    }

    @ResponseBody
    @GetMapping("/t4")
    public LocalDate t4() {
        return LocalDate.now();
    }

    @ResponseBody
    @GetMapping("/t5")
    public Instant t5() {
        log.info("=================" + LocalDateTime.parse("2019-10-24T11:10:57").toInstant(ZoneOffset.UTC));
        log.info("=================" + LocalDateTime.parse("2019-10-24T11:10:57"));
        return LocalDateTime.parse("2019-10-24T11:10:57").toInstant(ZoneOffset.UTC);
    }

    @ResponseBody
    @GetMapping("/t6")
    public ZonedDateTime t6() {
        return ZonedDateTime.now();
    }

    @ResponseBody
    @GetMapping("/t7")
    public Instant t7() {
        return ZonedDateTime.now().toInstant();
    }

    @ResponseBody
    @GetMapping("/t8")
    public LocalDateTime t8() {
        return ZonedDateTime.now().toLocalDateTime();
    }

    @ResponseBody
    @GetMapping("/t9")
    public ZonedDateTime t9() {
        ZoneId america = ZoneId.of("America/New_York");
        // ZonedDateTime dateAndTimeInNewYork
        // =ZonedDateTime.of(localDateAndTime,america);
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.now(america);
        log.info("Current date and time in a particular timezone : " + dateAndTimeInNewYork.toInstant());
        return dateAndTimeInNewYork;
    }

    @ResponseBody
    @GetMapping("/t2")
    public List<ArticleEntity> t2(@RequestParam(value = "author", required = false, defaultValue = "abc") String author) {

        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            log.info("getConnectionTimeout:" + hikariDataSource.getHikariConfigMXBean().getConnectionTimeout());
            log.info("getMaxLifetime:" + hikariDataSource.getHikariConfigMXBean().getMaxLifetime());
            log.info("getIdleTimeout:" + hikariDataSource.getHikariConfigMXBean().getIdleTimeout());
            log.info("getMaximumPoolSize:" + hikariDataSource.getHikariConfigMXBean().getMaximumPoolSize());
            log.info("getMinimumIdle:" + hikariDataSource.getHikariConfigMXBean().getMinimumIdle());
            log.info("getValidationTimeout:" + hikariDataSource.getHikariConfigMXBean().getValidationTimeout());

        }

        return articleService.listArticle(author);
    }

    @ResponseBody
    @GetMapping("/oauth/check_token")
    public Map<String, Object> checkToken(HttpServletRequest request) throws JsonProcessingException {

        Map<String, Object> result = new HashMap<>();
        result.put("threadId", Thread.currentThread().getId());

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null) {
            log.info("parameterMap:" + JsonUtil.multiValueMapToJsonString(parameterMap));
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("cookie=" + cookie.getName() + ":" + cookie.getValue());
            }
        }

        Enumeration<String> headerNames = request.getHeaderNames();

        // 输出所有的头信息的名字
        while (headerNames.hasMoreElements()) {
            // 获取request 的请求头名字name
            String name = headerNames.nextElement();
            // 根据头名字获取对应的值
            String value = request.getHeader(name);
            // 输出：
            log.info("header:" + name + ":    " + value);
        }

        log.info("getRequestURI:" + request.getRequestURI());
        return result;
    }

    @CrossOrigin("*")
    @ResponseBody
    @GetMapping(value = "/sse/random", produces = "text/event-stream;charset=UTF-8")
    public String ssePushTest() {
        log.info(LocalTime.now().toString());
        return "data:模拟动态数据" + Math.random() + "\n\n";
    }

    @Override
    public void afterPropertiesSet() {
        log.info("afterPropertiesSet");
    }

}
