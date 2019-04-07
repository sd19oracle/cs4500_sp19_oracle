package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.repositories.FAQRepository;
import edu.neu.cs4500.repositories.FAQAnswerRepository;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FAQAnswerService.class)
public class FAQAnswerServiceCRUDTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private FAQRepository faqRepository;

    @MockBean
    private FAQAnswerRepository faqAnswerRepository;

    FrequentlyAskedQuestion q1 = new FrequentlyAskedQuestion();
    FrequentlyAskedQuestion q2 = new FrequentlyAskedQuestion();
    FrequentlyAskedQuestion q3 = new FrequentlyAskedQuestion();
    FrequentlyAskedQuestion q4 = new FrequentlyAskedQuestion();

    FrequentlyAskedAnswer a1 = new FrequentlyAskedAnswer();
    FrequentlyAskedAnswer a2 = new FrequentlyAskedAnswer();
    FrequentlyAskedAnswer a3 = new FrequentlyAskedAnswer();
    FrequentlyAskedAnswer a4 = new FrequentlyAskedAnswer();

    public static String asJson(final Object obj) {
        try {
            final ObjectMapper m = new ObjectMapper();
            final String json = m.writeValueAsString(obj);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setupTestCases() {
	q1.setId(11);
	q2.setId(12);
	q3.setId(13);
	q4.setId(14);
	q1.setQuestion("How many employees do you have?");
	q1.setTitle("Number of Employees");
	q2.setQuestion("How long have you been in business?");
	q2.setrrTitle("Length of Business");
	q3.setQuestion("How many clients do you currently have?");
	q3.setTitle("Number of Clients");
	q4.setQuestion("Do you handle X situations?");
	q4.setTitle("X Situations");

	a1.setId(21);
	a2.setId(22);
	a3.setId(23);
	a4.setId(24);
	a1.setAnswer("13 Employees.");
	a2.setAnswer("9 Years.");
	a3.setAnswer("28 Clients.");
	a4.setAnswer("Yes, we handle X situations.");

        q1.addFrequentlyAskedAnswer(a1);
	q2.addFrequentlyAskedAnswer(a2);
	q3.addFrequentlyAskedAnswer(a3);
	q4.addFrequentlyAskedAnswer(a4);

	a1.setFrequentlyAskedQuestion(q1);
	a2.setFrequentlyAskedQuestion(q2);
	a3.setFrequentlyAskedQuestion(q3);
	a4.setFrequentlyAskedQuestion(q4);
    }

    @Test
    public void testFindAllFAQAnswers() throws Exception {
        List<FrequentlyAskedAnswer> answers = Arrays.asList(a1, a2, a3, a4);
        when(faqAnswerRepository.findAllFrequentlyAskedAnswer())
                .thenReturn(answers);
        this.mockmvc.perform(get("/api/faq-answers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id",
                        containsInAnyOrder(21,22,23, 24)))
                .andExpect(jsonPath("$.[*].answer",
                        containsInAnyOrder("13 Employees.", "9 Years.", "28 Clients.", "Yes, we handle X situations.")));
    }

    @Test
    public void testFindFAQAnswerById() throws Exception {
        List<FrequentlyAskedAnswer> answers = Arrays.asList(a1, a2, a3, a4);
        when(faqAnswerRepository.findFrequentlyAskedQuestionById(21))
                .thenReturn(a1);

        this.mockmvc.perform(get("/api/faq-answers/21"))
                .andDo(print())
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$.id", is(21)))
                .andExpect(jsonPath("$.answer", is("13 Employees.")));
    }

    @Test
    public void testUpdateFrequentlyAskedAnswer() throws Exception {
        FrequentlyAskedAnswer a1_update = new FrequentlyAskedAnswer();
        a1_update.setId(21);
        a1_update.setAnswer("15 Employees.");
        when(faqAnswerRepository.save(a1_update)).thenReturn(a1_update);
        when(faqAnswerRepository.findFrequentlyAskedQuestionById(21)).thenReturn(a1);

        this.mockmvc.perform(
                put("/api/faq-answers/21")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"serviceCategoryName\": \"Fancy Plumbing\", \"popularity\": 200, \"icon\": \"1a\"}"))
                .andExpect(status().isOk());
    }


    @Test
    public void testRemoveFrequentlyAskedAnswer() throws Exception {
	a1.deleteAnswer();
	when(faqAnswerRepository.save(a1)).thenReturn(a1);
	when(faqAnswerRepository.findFrequentlyAskedQuestionById(21)).thenReturn(a1);

        this.mockmvc.perform(put("/api/categories/21/removeAnswer")
		.contentType(MediaType.APPLICATION_JSON)
		.content())
                .andExpect(status().isOk());
    }

}
