package com.sakx.developer.demo.controller;

import com.sakx.developer.demo.model.Article;
import com.sakx.developer.demo.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	@RequestMapping(value = "/list", method= RequestMethod.GET)
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> list = articleService.getAllArticles();
		return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
	public ResponseEntity<Article> getArticleById(@PathVariable("id") Integer id) {
		Article article = articleService.getArticleById(id);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity addArticle(@RequestBody Article article, UriComponentsBuilder builder) {

		ResponseEntity response = null;

		boolean isAdded = articleService.addArticle(article);
		if (isAdded == false) {
			response = new ResponseEntity("Article already existed!", HttpStatus.CONFLICT);
		} else {
			response = new ResponseEntity("Article added successfully", HttpStatus.OK);
//			if (forward) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.setLocation(builder.path("/show/{id}").buildAndExpand(article.getArticleId()).toUri());
//				response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//			}
		}
		return response;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity updateArticle(@PathVariable Integer id, @RequestBody Article article) {
		article.setArticleId(id);
		articleService.updateArticle(article);
		return new ResponseEntity("Article updated successfully", HttpStatus.OK);
	}


	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteArticle(@PathVariable("id") Integer id) {
		articleService.deleteArticle(id);
		return new ResponseEntity("Article deleted successfully", HttpStatus.OK);
	}

} 