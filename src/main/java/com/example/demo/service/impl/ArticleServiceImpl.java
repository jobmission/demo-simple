package com.example.demo.service.impl;

import com.example.demo.persistence.entity.ArticleEntity;
import com.example.demo.persistence.entity.ArticleEntityExample;
import com.example.demo.persistence.mapper.ArticleEntityMapper;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleEntityMapper articleEntityMapper;

    @Cacheable(value = "articleList", key = "#author", unless = "#result == null or #result.size() == 0")
    @Override
    public List<ArticleEntity> listArticle(String author) {
        return articleEntityMapper.selectByExample(new ArticleEntityExample());
    }
}
