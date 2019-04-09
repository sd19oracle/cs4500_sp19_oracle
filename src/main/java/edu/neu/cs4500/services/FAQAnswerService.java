package edu.neu.cs4500.services;

import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.repositories.FAQAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class FAQAnswerService {
	@Autowired
	FAQAnswerRepository repository;

	// Find all FAQ Answer
	@GetMapping("api/faq-answers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedQuestions() {
		return repository.findAllFrequentlyAskedAnswers();
	}

	// Find single FAQ Answer by the ID
	@GetMapping("api/faq-answers/{id}")
	public FrequentlyAskedAnswer findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findFrequentlyAskedAnswerById(id);
	}

	// Update an FAQAnswer
	@PutMapping("api/faq-answers/{id}")
	public FrequentlyAskedAnswer updateAnswer(
			@PathVariable("id") Integer id,
			@RequestBody FrequentlyAskedAnswer updatedAnswer) {
		FrequentlyAskedAnswer findAnswer = repository.findFrequentlyAskedAnswerById(id);
		findAnswer.setAnswer(updatedAnswer.getAnswer());
		return repository.save(findAnswer);
	}

	// Delete an FAQAnswer
	@DeleteMapping("api/faq-answers/{id}")
	public void deleteAnswer(
			@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
}
