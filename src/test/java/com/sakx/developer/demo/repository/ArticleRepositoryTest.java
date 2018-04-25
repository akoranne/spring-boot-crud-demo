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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
//@Sql({"classpath:Before-Test-Schema.sql", "classpath:Before-Test-Data.sql"})
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Schema.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Data.sql")
public class ArticleRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryTest.class);
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
		Article article = repository.getArticleById(1);
		Assert.assertNotNull("... returned object is null/empty", article);
		logger.debug(" returned article - ", article);
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

}
