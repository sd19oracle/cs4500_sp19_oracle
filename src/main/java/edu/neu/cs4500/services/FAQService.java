package edu.neu.cs4500.services;

import java.util.List;

import edu.neu.cs4500.repositories.PagedFAQRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.repositories.FAQRepository;
import edu.neu.cs4500.repositories.FAQAnswerRepository;

@RestController
@CrossOrigin(origins="*")
public class FAQService {

	@Autowired
	PagedFAQRepository pagedRepository;

	@Autowired
	FAQRepository repository;

	@GetMapping("/api/faqs")
	public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions() {
		return (List<FrequentlyAskedQuestion>) repository.findAll();
	}

	@GetMapping("/api/faqs/{id}")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findById(id).orElse(null);
	}

	@GetMapping("/api/faqs/paged")
	public Page<FrequentlyAskedQuestion> findAllFAQsPaged(
			@RequestParam(name="page", required=false) Integer page,
			@RequestParam(name="count", required=false) Integer count
	) {

		if(page == null) {
			page = 0;
		}
		if(count == null) {
			count = 10;
		}

		Pageable p = PageRequest.of(page, count);
		return pagedRepository.findAll(p);
	}

	// Adds an answer to a specific question
        //   NOTE: Do not add ability to add answer without question
        //         Answer can only be added when their is a question
        @PostMapping("api/faqs/{id}/addAnswer")
        public FrequentlyAskedQuestion addAnswertoQuestion(
		@RequestBody FrequentlyAskedAnswer anAnswer,
		@PathVariable("id") Integer id
	) {
		FrequentlyAskedQuestion findQuestion =
			repository.findFrequentlyAskedQuestionById(id);
		findQuestion.addFrequentlyAskedAnswer(anAnswer);
		anAnswer.setFrequentlyAskedQuestion(findQuestion);
		return findQuestion;
	}
}
