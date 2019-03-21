package edu.neu.cs4500.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import edu.neu.cs4500.models.PageInfo;
import edu.neu.cs4500.models.ServiceSpecificQuestion;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceSpecificQuestionService.class)
public class ServiceSpecificQuestionServicePaginationTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ServiceSpecificQuestionService service;
  ServiceSpecificQuestion q1 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q2 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q3 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q4 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q5 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q6 = new ServiceSpecificQuestion();

  @Before
  public void setUpInitialTestCases() {
    q1.setId(1);
    q1.setTitle("How many rooms do you need to clean?");
    q1.setType("MULTIPLECHOICE");
    q1.setChoice("1,2,3,more");
    q2.setId(2);
    q2.setTitle("How long do you need to clean a small room?");
    q2.setType("MINMAX");
    q3.setId(3);
    q3.setTitle("How many people do you have?");
    q3.setType("MINMAX");
    q4.setId(4);
    q4.setTitle("Can you work on the weekend?");
    q4.setType("TRUEFALSE");
    q5.setId(5);
    q5.setTitle("Do you have a car?");
    q5.setType("TRUEFALSE");
    q6.setId(6);
    q6.setTitle("How do accept payment?");
    q6.setType("1,2,3,more");
  }

  @Test
  public void testFirstPageLoad() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1, q2, q3, q4, q5);
    PageInfo page = new PageInfo();
    page.setPage_num(5);
    page.setTotal_questions(questions.size());
    page.setList_questions(questions);
    when(service.findServicesQuestionsByItemNum(1)).thenReturn(page);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions/page/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.list_questions", hasSize(5)))
            .andExpect(jsonPath("$.list_questions[0].id", is(1)))
            .andExpect(jsonPath("$.list_questions[4].id", is(5)))
            .andExpect(jsonPath("$.list_questions.[*].id",
                    containsInAnyOrder(1, 2, 3, 4, 5)))
            .andExpect(jsonPath("$.total_questions", is(5)))
            .andExpect(jsonPath("$.page_num", is(5)));
    verify(service, times(1)).findServicesQuestionsByItemNum(1);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void testFirstPage() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1, q2, q3, q4, q5);
    when(service.findServicesQuestionsByPageNum(10,1)).thenReturn(questions);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions/page/10/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$.[0].id", is(1)))
            .andExpect(jsonPath("$.[4].id", is(5)))
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2, 3, 4, 5)));
    verify(service, times(1)).
            findServicesQuestionsByPageNum(10,1);
    verifyNoMoreInteractions(service);
  }


  @Test
  public void page_size_bigger_than_total_questions() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1, q2, q3, q4, q5);
    when(service.findServicesQuestionsByPageNum(6,1)).thenReturn(questions);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions/page/6/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$.[0].id", is(1)))
            .andExpect(jsonPath("$.[4].id", is(5)))
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2, 3, 4, 5)));
    verify(service, times(1)).
            findServicesQuestionsByPageNum(6,1);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void change_page() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q3, q4);
    when(service.findServicesQuestionsByPageNum(2,2)).thenReturn(questions);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions/page/2/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id", is(3)))
            .andExpect(jsonPath("$.[1].id", is(4)))
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(3,4)));
    verify(service, times(1)).
            findServicesQuestionsByPageNum(2,2);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void enough_page() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q3, q4);
    PageInfo page = new PageInfo();
    page.setPage_num(1);
    page.setTotal_questions(questions.size());
    page.setList_questions(questions);
    when(service.findServicesQuestionsByItemNum(100)).thenReturn(page);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions/page/100"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.list_questions", hasSize(2)))
            .andExpect(jsonPath("$.total_questions", is(2)))
            .andExpect(jsonPath("$.page_num", is(1)))
            .andExpect(jsonPath("$.list_questions[0].id", is(3)))
            .andExpect(jsonPath("$.list_questions[1].id", is(4)))
            .andExpect(jsonPath("$.list_questions[*].id",
                    containsInAnyOrder(3,4)));
    verify(service, times(1)).
            findServicesQuestionsByItemNum(100);
    verifyNoMoreInteractions(service);
  }

}