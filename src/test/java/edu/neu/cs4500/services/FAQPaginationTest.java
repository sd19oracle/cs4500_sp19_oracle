package edu.neu.cs4500.services;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.repositories.FAQRepository;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(FAQService.class)
public class FAQPaginationTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FAQRepository faqRepo;
	@MockBean
	private FAQService service;

	
	
	@Before
	public void setup() {
		for (int i = 0; i < 100; i++) {
			
		}
	}
	
//	@Test
//	public void testTest() throws Exception {
//		List<FrequentlyAskedQuestion> arr = new ArrayList<>(0);
//	    Pageable pageable = PageRequest.of(0, 10);
//	    Page<FrequentlyAskedQuestion> page = new PageImpl<>(arr, pageable, arr.size());
//	    when(faqRepo.findAll(pageable)).thenReturn(page);
//	    
//	    this.mockMvc.perform(get("/api/faqs/paged/0/10"))
//	    	.andExpect(status().isOk())
//	    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//            .andExpect(jsonPath("$.content", hasSize(0)))
//            .andExpect(jsonPath("$.totalElements", is(0)))
//            .andExpect(jsonPath("$.totalPages", is(0)));
//	}
	
	
	
	
	
}
