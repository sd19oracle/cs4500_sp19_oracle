package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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


import edu.neu.cs4500.models.ServiceSpecificQuestion;
import edu.neu.cs4500.repositories.PagedServiceQuestionRepo;
import edu.neu.cs4500.repositories.ServiceSpecificQuestionRepository;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceSpecificQuestionService.class)
public class ServiceSpecificQuestionServiceTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ServiceSpecificQuestionRepository serviceSpecificQuestionService;
  @MockBean
  private PagedServiceQuestionRepo pagedServiceQuestionRepo;
  ServiceSpecificQuestion q1 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q2 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q3 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q4 = new ServiceSpecificQuestion();
  ServiceSpecificQuestion q5 = new ServiceSpecificQuestion();

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

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
  }

  @Test
  public void testCreateANewQuestion() throws Exception {

    ServiceSpecificQuestion q3 = new ServiceSpecificQuestion();
    q3.setId(3);
    when(serviceSpecificQuestionService.existsById(3)).thenReturn(false);
    mockMvc.perform(
            post("/api/servicesSpecificQuestions/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(q3)))
                    .andExpect(status().isOk());
  }

  @Test
  public void testRemoveAServiceQuestion() throws Exception {
    doNothing().when(serviceSpecificQuestionService).deleteById(1);
    mockMvc.perform(delete("/api/servicesSpecificQuestions/1"))
            .andExpect(status().isOk());
  }


  @Test
  public void testUpdateAServiceQuestion() throws Exception {
    ServiceSpecificQuestion q1new = new ServiceSpecificQuestion();
    q1new.setId(1);
    q1new.setType("MULTIPLECHOICE");
    q1new.setTitle("Chooes you pet(s)!");
    when(serviceSpecificQuestionService.save(q1new)).thenReturn(q1new);
    when(serviceSpecificQuestionService.findAllServiceSpecificQuestionById(1)).thenReturn(q1);
    mockMvc.perform(
            put("/api/servicesSpecificQuestions/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"Chooes you pet(s)!\", \"type\":\"MULTIPLECHOICE\", \"choice\":\"\" }"))
            .andExpect(status().isOk());
  }


  @Test
  public void testFindAllServiceQuestion() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1, q2);
    when(serviceSpecificQuestionService.findAllServiceSpecificQuestion()).thenReturn(questions);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE", "MINMAX")));
  }

  @Test
  public void testFindServiceQuestionsByFilterBasic() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1, q2);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("", "", ""))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"\", \"type\":\"\", \"choice\":\"\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE", "MINMAX")));
  }

  @Test
  public void testFindServiceQuestionsByFilterTitleOnly() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("rooms", "", ""))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"rooms\", \"type\":\"\", \"choice\":\"\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE")));

  }

  @Test
  public void testFindServiceQuestionsByFilterTypeOnly() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q2);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("", "MINMAX", ""))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"\", \"type\":\"MINMAX\", \"choice\":\"\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(2)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MINMAX")));
  }

  @Test
  public void testFindServiceQuestionsByFilterChoiceOnly() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("", "", "more"))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"\", \"type\":\"\", \"choice\":\"more\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE")));
  }

  @Test
  public void testFindServiceQuestionsByFilterTitleAndType() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q2);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("How", "MINMAX", ""))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"How\", \"type\":\"MINMAX\", \"choice\":\"\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(2)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MINMAX")));
  }

  @Test
  public void testFindServiceQuestionsByFilterTypeAndChoice() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("", "MULTIPLECHOICE", "2"))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"\", \"type\":\"MULTIPLECHOICE\", \"choice\":\"2\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE")));
  }

  @Test
  public void testFindServiceQuestionsByFilterTitleAndChoice() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("clean?", "", "2"))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"clean?\", \"type\":\"\", \"choice\":\"2\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE")));
  }

  @Test
  public void testFindServiceQuestionsByFilterTitleAndTypeAndChoice() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1);
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("clean?", "MULTIPLECHOICE", "2"))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"clean?\", \"type\":\"MULTIPLECHOICE\", \"choice\":\"2\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1)))
            .andExpect(jsonPath("$.[*].type",
                    containsInAnyOrder("MULTIPLECHOICE")));
  }

  @Test
  public void testFindServiceQuestionsByFilterNoMatchTitle() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList();
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("#@asd?", "", ""))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"#@asd?\", \"type\":\"\", \"choice\":\"\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",
                    hasSize(0)));
  }

  @Test
  public void testFindServiceQuestionsByFilterNoMatchType() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList();
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("", "ANY", ""))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"\", \"type\":\"ANY\", \"choice\":\"\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",
                    hasSize(0)));
  }

  @Test
  public void testFindServiceQuestionsByFilterNoMatchChoice() throws Exception {
    List<ServiceSpecificQuestion> questions = Arrays.asList();
    when(serviceSpecificQuestionService
            .findServiceQuestionsByFilter("", "", "qwerty"))
            .thenReturn(questions);
    this.mockMvc
            .perform(post("/api/servicesSpecificQuestions/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"title\":\"\", \"type\":\"\", \"choice\":\"qwerty\" }")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",
                    hasSize(0)));
  }

}