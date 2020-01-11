package com.example.demo.service;

import com.example.demo.persistence.entity.ArticleEntity;

import java.util.List;

public interface ArticleService {

    List<ArticleEntity> listArticle(String author);
}
