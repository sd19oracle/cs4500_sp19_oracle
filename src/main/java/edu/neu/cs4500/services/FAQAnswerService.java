package edu.neu.cs4500.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.repositories.FAQAnswerRepository;

@RestController
@CrossOrigin(origins="*")
public class FAQAnswerService {
	@Autowired
	FAQAnswerRepository repository;
	@GetMapping("/api/faq-answers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedQuestions() {
		return repository.findAllFrequentlyAskedAnswers();
	}
	@GetMapping("/api/faq-answers/{id}")
	public FrequentlyAskedAnswer findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findFrequentlyAskedAnswerById(id);
	}
}
