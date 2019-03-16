package edu.neu.cs4500.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.neu.cs4500.models.ServiceSpecificQuestion;
import edu.neu.cs4500.repositories.ServiceSpecificQuestionRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceSpecificQuestionService.class)
public class ServiceSpecificQuestionServiceTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ServiceSpecificQuestionRepository serviceSpecificQuestionService;

  @Test
  public void testFindAllServiceQuestion() throws Exception {
    ServiceSpecificQuestion q1 = new ServiceSpecificQuestion();
    ServiceSpecificQuestion q2 = new ServiceSpecificQuestion();
    q1.setId(1);
    q1.setTitle("How many rooms do you need to clean?");
    q1.setType("MULTIPLECHOICE");
    q2.setId(2);
    q2.setTitle("How long do you need to clean a small room?");
    q2.setType("MINMAX");
    List<ServiceSpecificQuestion> questions = Arrays.asList(q1, q2);
    when(serviceSpecificQuestionService.findAllServiceSpecificQuestion()).thenReturn(questions);
    this.mockMvc
            .perform(get("/api/servicesSpecificQuestions"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2)));
  }

}