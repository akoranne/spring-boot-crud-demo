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
		boolean isExists = articleRepository.isExists(article.getTitle(), article.getCategory());
		if (! isExists) {
			int id = articleRepository.addArticle(article);
		}
		return isExists;
	}

	@Override
	public int updateArticle(Article article) {
		return articleRepository.updateArticle(article);
	}

	@Override
	public int deleteArticle(int articleId) {
		return articleRepository.deleteArticle(articleId);
	}
}
