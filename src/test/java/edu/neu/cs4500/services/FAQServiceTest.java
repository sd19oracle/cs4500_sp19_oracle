package edu.neu.cs4500.services;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.repositories.FAQAnswerRepository;
import edu.neu.cs4500.repositories.FAQRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FAQService.class)
@ExtendWith(SpringExtension.class)
class FAQServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private FAQRepository repository;

    @MockBean
    private FAQAnswerRepository answerRepository;

    private FrequentlyAskedQuestion q1;
    private FrequentlyAskedQuestion q2;
    private FrequentlyAskedQuestion q3;
    private FrequentlyAskedQuestion q4;
    private FrequentlyAskedQuestion q5;
    private List<FrequentlyAskedQuestion> allQuestions;


    @BeforeEach
    void setUp() {
        this.q1 = new FrequentlyAskedQuestion(1, "q1 title", "q1 question");
        this.q2 = new FrequentlyAskedQuestion(2, "q2 title", "q2 question");
        this.q3 = new FrequentlyAskedQuestion(3, "q3 title", "q3 question");
        this.q4 = new FrequentlyAskedQuestion(4, "q4 title", "q4 question");
        this.q5 = new FrequentlyAskedQuestion(5, "q5 title", "q5 question");
        this.allQuestions = Arrays.asList(this.q1, this.q2, this.q3, this.q4, this.q5);
    }

    @Test
    void findAllFrequentlyAskedQuestions() {

    }

    @Test
    void findFrequentlyAskedQuestionById() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(this.q1));
        when(repository.findById(7)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/faqs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("q1 title")))
                .andExpect(jsonPath("$.question", is("q1 question")));
        this.mockMvc.perform(get("/api/faqs/7"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).findById(7);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAllFAQsPaged() throws Exception {

        Pageable p1 = PageRequest.of(0, 10);
        Pageable p2 = PageRequest.of(1, 3);
        Pageable p3 = PageRequest.of(0, 3);
        Page<FrequentlyAskedQuestion> pg1 = new PageImpl<>(this.allQuestions, p1, this.allQuestions.size());
        Page<FrequentlyAskedQuestion> pg2 = new PageImpl<>(this.allQuestions.subList(3, 5), p2, this.allQuestions.size());
        Page<FrequentlyAskedQuestion> pg3 = new PageImpl<>(this.allQuestions.subList(0, 3), p3, this.allQuestions.size());


        when(repository.findAll(p1)).thenReturn(pg1);
        when(repository.findAll(p2)).thenReturn(pg2);
        when(repository.findAll(p3)).thenReturn(pg3);

        this.mockMvc.perform(get("/api/faqs/paged")
                .param("page", "0")
                .param("count", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3, 4, 5)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.numberOfElements", is(5)));
        this.mockMvc.perform(get("/api/faqs/paged"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3, 4, 5)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.numberOfElements", is(5)));
        this.mockMvc.perform(get("/api/faqs/paged")
                .param("page", "1")
                .param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(4, 5)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.number", is(1)))
                .andExpect(jsonPath("$.numberOfElements", is(2)));
        this.mockMvc.perform(get("/api/faqs/paged")
                .param("page", "0")
                .param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.numberOfElements", is(3)));

    }

    @Test
    void filterFAQs() {
    }

    @Test
    void addAnswertoQuestion() {
    }
}
