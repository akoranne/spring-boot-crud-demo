package com.sakx.developer.demo.controller;

import com.sakx.developer.demo.Application;
import com.sakx.developer.demo.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Schema.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Data.sql")
@AutoConfigureMockMvc
public class ArticleRestControllerTests {

	public static final Logger logger = LoggerFactory.getLogger(ArticleRestControllerTests.class);

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

	@Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        assertThat(restTemplate.getForObject(createURLWithPort("/"), String.class))
                .contains("Article service");
    }

    @Test
    public void shouldReturnArticleForId() throws Exception {
        String expected = "{\"articleId\":1,\"title\":\"Spring REST Security using Hibernate\",\"category\":\"Spring\"}";

        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Article> response =
                restTemplate.exchange(createURLWithPort("/articles/show/1"),
                        HttpMethod.GET,
                        (new HttpEntity<String>(null, headers)), Article.class);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().toJson()).isEqualToIgnoringCase(expected);
    }


    @Test
    public void shouldReturnNotFoundForId() throws Exception {

        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Article> response =
                restTemplate.exchange(createURLWithPort("/articles/show/100"),
                        HttpMethod.GET,
                        (new HttpEntity<String>(null, headers)), Article.class);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void shouldReturnAllArticles() throws Exception{

        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Collection<Article>> response =
                restTemplate.exchange(createURLWithPort("/articles/list"),
                        HttpMethod.GET,
                        (new HttpEntity<String>(null, headers)), new ParameterizedTypeReference<Collection<Article>>() {});
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(4);

        response.getBody().forEach(a -> System.out.println(a.toJson()));
    }


    @Test
    public void shouldAddArticleAndReturnId() {

        Article article = new Article(-1, "The Phoenix Project", "Management");

        HttpEntity<Article> entity = new HttpEntity<Article>(article, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(createURLWithPort("/articles/add"),
                        HttpMethod.POST, entity, String.class);

        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        // assertThat(response.getBody()).is
    }

    @Test
    public void shouldDeleteArticle() {
        // delete
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response1 =
                restTemplate.exchange(createURLWithPort("/articles/delete/3"),
                        HttpMethod.DELETE,
                        (new HttpEntity<String>(null, headers)), String.class);
        System.out.println(response1);
        assertThat(response1).isNotNull();
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);

        // verify delete worked by searching for it
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Article> response2 =
                restTemplate.exchange(createURLWithPort("/articles/show/3"),
                        HttpMethod.GET,
                        (new HttpEntity<String>(null, headers)), Article.class);
        System.out.println(response2);
        assertThat(response2).isNotNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response2.getBody()).isNull();
    }


    @Test
    public void shouldUpdateArticle() {
        Article article = new Article(2, "Cloud Native Java", "Cloud Native");

        HttpEntity<Article> entity = new HttpEntity<Article>(article, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(createURLWithPort("/articles/update/2"),
                        HttpMethod.PUT, entity, String.class);

        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        // verify delete worked by searching for it
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Article> response2 =
                restTemplate.exchange(createURLWithPort("/articles/show/2"),
                        HttpMethod.GET,
                        (new HttpEntity<String>(null, headers)), Article.class);
        System.out.println(response2);
        assertThat(response2).isNotNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isNotNull();
        assertThat(response2.getBody().getCategory()).isEqualToIgnoringCase("Cloud Native");
    }




    // helper method to build uri
    private String createURLWithPort(String uri) {
        String url = "http://localhost:" + port + uri;
        System.out.println(" url - " + url);
        return url;
    }

}
