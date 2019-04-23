package edu.neu.cs4500.services;

import edu.neu.cs4500.models.ServiceSpecificQuestion;
import edu.neu.cs4500.repositories.PagedServiceQuestionRepo;
import edu.neu.cs4500.repositories.ServiceSpecificQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServiceSpecificQuestionService.class)
public class ServiceSpecificQuestionPaginationTest {
    ServiceSpecificQuestion q1 = new ServiceSpecificQuestion();
    ServiceSpecificQuestion q2 = new ServiceSpecificQuestion();
    ServiceSpecificQuestion q3 = new ServiceSpecificQuestion();
    ServiceSpecificQuestion q4 = new ServiceSpecificQuestion();
    ServiceSpecificQuestion q5 = new ServiceSpecificQuestion();
    ServiceSpecificQuestion q6 = new ServiceSpecificQuestion();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceSpecificQuestionRepository serviceSpecificQuestionRepository;
    @MockBean
    private PagedServiceQuestionRepo service;

    @BeforeEach
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
        q6.setTitle("How do accept payment methods?");
        q6.setType("1,2,3,more");
    }

    @Test
    public void testFirstPageLoad() throws Exception {
        List<ServiceSpecificQuestion> arr = Arrays.asList(q1, q2, q3, q4, q5, q6);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ServiceSpecificQuestion> temp = new PageImpl<>(arr, pageable, arr.size());
        when(service.findAll(pageable)).thenReturn(temp);
        this.mockMvc
                .perform(get("/api/servicesSpecificQuestions/paged/10/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(6)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[5].id", is(6)))
                .andExpect(jsonPath("$.content.[*].id",
                        containsInAnyOrder(1, 2, 3, 4, 5, 6)))
                .andExpect(jsonPath("$.totalElements", is(6)))
                .andExpect(jsonPath("$.totalPages", is(1)));
        verify(service, times(1)).findAll(pageable);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void blankPage() throws Exception {
        List<ServiceSpecificQuestion> arr = new ArrayList<>(0);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ServiceSpecificQuestion> temp = new PageImpl<>(arr, pageable, arr.size());
        when(service.findAll(pageable)).thenReturn(temp);
        this.mockMvc
                .perform(get("/api/servicesSpecificQuestions/paged/10/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(0)));
        verify(service, times(1)).findAll(pageable);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void page_size_bigger_than_total_questions() throws Exception {
        List<ServiceSpecificQuestion> arr = Arrays.asList(q1, q2, q3, q4, q5);
        Pageable pageable = PageRequest.of(0, 6);
        Page<ServiceSpecificQuestion> temp = new PageImpl<>(arr, pageable, arr.size());
        when(service.findAll(pageable)).thenReturn(temp);
        this.mockMvc
                .perform(get("/api/servicesSpecificQuestions/paged/6/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[4].id", is(5)))
                .andExpect(jsonPath("$.content[*].id",
                        containsInAnyOrder(1, 2, 3, 4, 5)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(1)));
        verify(service, times(1)).
                findAll(pageable);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void change_page() throws Exception {
        List<ServiceSpecificQuestion> arr = Arrays.asList(q3, q4);
        Pageable pageable = PageRequest.of(1, 2);
        Page<ServiceSpecificQuestion> temp = new PageImpl<>(arr, pageable, arr.size());
        when(service.findAll(pageable)).thenReturn(temp);
        this.mockMvc
                .perform(get("/api/servicesSpecificQuestions/paged/2/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(3)))
                .andExpect(jsonPath("$.content[1].id", is(4)))
                .andExpect(jsonPath("$.content[*].id",
                        containsInAnyOrder(3, 4)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.totalPages", is(2)));
        verify(service, times(1)).
                findAll(pageable);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void enough_page() throws Exception {
        List<ServiceSpecificQuestion> arr = Arrays.asList(q3, q4);
        Pageable pageable = PageRequest.of(0, 100);
        Page<ServiceSpecificQuestion> temp = new PageImpl<>(arr, pageable, arr.size());
        when(service.findAll(pageable)).thenReturn(temp);
        this.mockMvc
                .perform(get("/api/servicesSpecificQuestions/paged/100/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(3)))
                .andExpect(jsonPath("$.content[1].id", is(4)))
                .andExpect(jsonPath("$.content[*].id",
                        containsInAnyOrder(3, 4)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)));
        verify(service, times(1)).
                findAll(pageable);
        verifyNoMoreInteractions(service);
    }
}
