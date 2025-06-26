package com.example.demo.service.impl;

import com.example.demo.persistence.entity.ArticleEntity;
import com.example.demo.persistence.entity.InformationEntity;
import com.example.demo.persistence.mapper.ArticleEntityMapper;
import com.example.demo.persistence.mapper.InformationEntityMapper;
import com.example.demo.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    ArticleEntityMapper articleEntityMapper;

    @Autowired
    InformationEntityMapper informationEntityMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void processTransaction() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthor("zhangsan");
        articleEntity.setTitle(new Random(System.currentTimeMillis()).nextInt()+"");
        articleEntity.setDateCreated(LocalDateTime.now());
        articleEntity.setVersion(0);
        articleEntity.setRecordStatus(0);
        articleEntityMapper.insert(articleEntity);

        InformationEntity informationEntity = new InformationEntity();
        informationEntity.setTitle("aaa");
        informationEntity.setSummary("aaa");
        informationEntity.setUrl("aaa");
        informationEntity.setUrlHashValue(new Random(System.currentTimeMillis()).nextInt());
        informationEntity.setDateCreated(LocalDateTime.now());
        informationEntity.setVersion(0);
        informationEntity.setRecordStatus(0);
        informationEntityMapper.insert(informationEntity);


    }
}
