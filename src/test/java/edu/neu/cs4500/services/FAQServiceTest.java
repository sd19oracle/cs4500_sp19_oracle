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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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


    @BeforeEach
    void setUp() {
        this.q1 = new FrequentlyAskedQuestion("q1 title", "q1 question");
        this.q2 = new FrequentlyAskedQuestion("q2 title", "q2 question");
        this.q3 = new FrequentlyAskedQuestion("q3 title", "q3 question");
        this.q4 = new FrequentlyAskedQuestion("q4 title", "q4 question");
        this.q5 = new FrequentlyAskedQuestion("q5 title", "q5 question");
    }

    @Test
    void findAllFrequentlyAskedQuestions() {

    }

    @Test
    void findFrequentlyAskedQuestionById() {
    }

    @Test
    void findAllFAQsPaged() {
    }

    @Test
    void filterFAQs() {
    }

    @Test
    void addAnswertoQuestion() {
    }
}
