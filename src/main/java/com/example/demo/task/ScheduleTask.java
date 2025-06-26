package com.example.demo.task;

import com.example.demo.persistence.entity.ArticleEntityExample;
import com.example.demo.persistence.entity.PersonEntityExample;
import com.example.demo.persistence.mapper.ArticleEntityMapper;
import com.example.demo.persistence.mapper.PersonEntityMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ArticleEntityMapper articleEntityMapper;

    @Autowired
    PersonEntityMapper personEntityMapper;

    /**
     * 1分钟调一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void task1() {
        long count1 = articleEntityMapper.countByExample(new ArticleEntityExample());
        log.info("ArticleEntity:" + count1);
    }

    /**
     * 15分钟调一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void task2() {
        long count2 = personEntityMapper.countByExample(new PersonEntityExample());
        log.info("PersonEntity:" + count2);
    }
}
