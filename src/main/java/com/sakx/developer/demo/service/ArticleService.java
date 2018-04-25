package com.sakx.developer.demo.service;

import com.sakx.developer.demo.repository.ArticleRepository;
import com.sakx.developer.demo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService implements IArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public Article getArticleById(int articleId) {
		Article obj = articleRepository.getArticleById(articleId);
		return obj;
	}

	@Override
	public List<Article> getAllArticles() {
		return articleRepository.getAllArticles();
	}

	@Override
	public synchronized boolean addArticle(Article article) {
		if (articleRepository.isExists(article.getTitle(), article.getCategory())) {
			return false;
		} else {
			articleRepository.addArticle(article);
			return true;
		}
	}

	@Override
	public void updateArticle(Article article) {
		articleRepository.updateArticle(article);
	}

	@Override
	public void deleteArticle(int articleId) {
		articleRepository.deleteArticle(articleId);
	}
}
