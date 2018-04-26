package com.sakx.developer.demo.controller;

import com.sakx.developer.demo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Schema.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:Before-Test-Data.sql")
@AutoConfigureMockMvc
public class ArticleRestControllerTests {

	public static final Logger logger = LoggerFactory.getLogger(ArticleRestControllerTests.class);
	
	@Autowired
    private MockMvc mockMvc;
 
    // @MockBean
    // private ArticleService service;
 
    // write test cases here

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
    	// Mockito.when(service.getInfo()).thenReturn("Article service");
    	
        this.mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Article service")));
    }


    
/*    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {

        List<Article> allArticles = IntStream.rangeClosed(1, 4)
        	    .boxed()
        	    .flatMap(value -> 
        	        IntStream.rangeClosed(1, 13)
        	            .mapToObj(suit -> new Article(-1, "The Phoenix Project", "Management"))
        	    )
        	    .collect(Collectors.toList());
        
//        List<User> allArticles = users.stream().filter(u -> u.age > 30).collect(Collectors.toList());

        
     
//		List<Article> results = repository.getAllArticles();
//        List<Article> allArticles = Arrays.asList(alex);
     
//        Mockito.when(
//				studentService.retrieveCourse(Mockito.anyString(),
//						Mockito.anyString())).thenReturn(mockCourse);
        
        mockMvc.perform(get("/articles/list")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].name", is(alex.getName())));

//		logger.debug(results.size() + ">>>" + results);
//		logger.debug(tname.getMethodName() + " " + results);
//
//		logger.info(" returned results - \n {}", results);
//
//		Assert.assertTrue("returned no of rows is incorrect", (results.size() == 4));
    	
    	

		
    	
    	

    
    }
*/}
