package com.sakx.developer.demo.repository;

import com.sakx.developer.demo.Application;
import com.sakx.developer.demo.model.Article;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Schema.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Data.sql")
public class ArticleRepositoryTests {

	private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryTests.class);

	@LocalServerPort
    private int port;
	
	@Rule
	public TestName tname = new TestName();

	@Autowired
	private ArticleRepository repository;

	@Test
	public void testAutowires() throws Exception {
		Assert.assertNotNull("... repository instance is null/empty", repository);
	}

	@Test
	public void testGetArticleById() throws Exception {
		int id = 1;
		Article article = repository.getArticleById(id);
		Assert.assertNotNull("... returned object is null/empty", article);
		System.out.println(" returned article for id " + id + ", " + article);
	}

	@Test
	public void testGetAllArticles() {
		List<Article> results = repository.getAllArticles();
		logger.debug(results.size() + ">>>" + results);
		logger.debug(tname.getMethodName() + " " + results);

		logger.info(" returned results - \n {}", results);

		Assert.assertTrue("returned no of rows is incorrect", (results.size() == 4));
	}

	@Test
	public void testAddArticle() {
		int id1 = repository.addArticle(new Article(-1, "The Phoenix Project", "Management"));
		int id2 = repository.addArticle(new Article(-1, "The JHipster Mini Book", "Frontend"));

		assertThat(id1, is(notNullValue()));
		assertThat(id2, is(notNullValue()));

		List<Article> results = repository.getAllArticles();
		logger.debug(results.size() + ">>>" + results);
		logger.debug(tname.getMethodName() + " " + results);
		Assert.assertTrue("returned no of rows is incorrect", (results.size() == 6));
	}

	@Test
	public void testAddUpdateArticle() {
		
		// add a new article
		Article phoenixProject = new Article(-1, "The Phoenix Project", "Management");
		Article jhipster = new Article(-1, "The JHipster Mini Book", "Frontend");
		
		int id1 = repository.addArticle(phoenixProject);
		
		// verify the article id 
		assertThat(id1, is(notNullValue()));

		//retrieve the article by id
		Article article = repository.getArticleById(id1);
		System.out.println("returned article for id - " + id1 + " ==> " + article);
		assertThat(article.getArticleId(), is(article.getArticleId()));
        assertThat(article,is(phoenixProject));

		jhipster.setArticleId(id1);
		int updCnt = repository.updateArticle(jhipster);
		assertThat(updCnt, is(notNullValue()));

		Article article2 = repository.getArticleById(id1);
        assertThat(article2,is(jhipster));
		
	}

    @Test
    public void testDeleteArticle() {

        List<Article> results = repository.getAllArticles();
        Assert.assertTrue("returned no of rows is incorrect", (results.size() == 4));
        logger.debug(results.size() + ">>>" + results);
        logger.info(" returned results - \n {}", results);

	    int id1 = 3;

        //retrieve the article by id
        Article article = repository.getArticleById(id1);
        System.out.println("returned article for id - " + id1 + " ==> " + article);
        assertThat(article, is(notNullValue()));

        int delCnt = repository.deleteArticle(id1);
        assertThat(delCnt, is(1));

        Article article2 = repository.getArticleById(id1);
        assertThat(article2, is(nullValue()));

        results = repository.getAllArticles();
        Assert.assertTrue("returned no of rows is incorrect", (results.size() == 3));
        logger.debug(results.size() + ">>>" + results);
        logger.info(" returned results - \n {}", results);
    }
}
