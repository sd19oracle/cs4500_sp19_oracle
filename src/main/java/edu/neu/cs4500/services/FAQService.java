package edu.neu.cs4500.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import edu.neu.cs4500.repositories.FAQAnswerRepository;
import edu.neu.cs4500.repositories.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.models.FrequentlyAskedAnswer;

@RestController
@CrossOrigin(origins="*")
public class FAQService {

	@Autowired
	FAQRepository repository;

	@Autowired
	FAQAnswerRepository answerRepository;

	@GetMapping("/api/faqs")
	public Page<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions(
			@RequestParam(name="page", defaultValue = "0", required=false) Integer page,
			@RequestParam(name="count", required=false) Integer count,
			@RequestParam(name="title", defaultValue = "", required=false) String title,
			@RequestParam(name="question", defaultValue = "", required=false) String question
	) {

		question = "%" + question + "%";
		title = "%" + title + "%";
		Pageable p = Optional.ofNullable(count).map(c -> (Pageable) PageRequest.of(page, c)).orElse(Pageable.unpaged());
		return repository.filterFAQsPaged(title, question, p);

	}

	@GetMapping("/api/faqs/{id}")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findById(id).orElse(null);
	}

	@GetMapping("/api/faqs/paged")
	public Page<FrequentlyAskedQuestion> findAllFAQsPaged(
			@RequestParam(name="page", defaultValue = "0", required=false) Integer page,
			@RequestParam(name="count", required=false) Integer count
	) {

		if(count == null) {
			count = 10;
		}

		Pageable p = PageRequest.of(page, count);
		Page<FrequentlyAskedQuestion> questionPage = repository.findAll(p);
		return questionPage;
	}

	@GetMapping("/api/faqs/filtered")
	public List<FrequentlyAskedQuestion> filterFAQs(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="question", required=false) String question
	) {
		if (title == null) title = "";
		if (question == null) question = "";

		question = "%" + question + "%";
		title = "%" + title + "%";
		return repository.filterFAQs(title, question);
	}


	// Adds an answer to a specific question
        //   NOTE: Do not add ability to add answer without question
        //         Answer can only be added when their is a question
        @PostMapping("api/faqs/{id}/addAnswer")
        public FrequentlyAskedAnswer addAnswertoQuestion(
		@RequestBody FrequentlyAskedAnswer anAnswer,
		@PathVariable("id") Integer id
	) {
		FrequentlyAskedQuestion findQuestion = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No such question"));
		findQuestion.addFrequentlyAskedAnswer(anAnswer);
		anAnswer.setFrequentlyAskedQuestion(findQuestion);
		return this.answerRepository.save(anAnswer);
	}
}
