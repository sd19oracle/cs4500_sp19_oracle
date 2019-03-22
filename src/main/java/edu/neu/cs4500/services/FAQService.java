package edu.neu.cs4500.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.repositories.FAQRepository;
import edu.neu.cs4500.repositories.FAQAnswerRepository;

@RestController
@CrossOrigin(origins="*")
public class FAQService {
	@Autowired
	FAQRepository repository;

	@GetMapping("/api/faqs")
	public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions() {
		return repository.findAllFrequentlyAskedQuestions();
	}

	@GetMapping("/api/faqs/{id}")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findFrequentlyAskedQuestionById(id);
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
