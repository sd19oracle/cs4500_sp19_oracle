package edu.neu.cs4500.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.PageInfo;
import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.repositories.FAQAnswerRepository;
import edu.neu.cs4500.repositories.FAQRepository;

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
		FrequentlyAskedAnswer findAnswer = FAQAnswerRepository.findFrequentlyAskedAnswerById(id);
		findAnswer.setAnswer(updatedAnswer.getAnswer());
		return FAQAnswerRepository.save(findAnswer);
	}

	// Delete an FAQAnswer
	@DeleteMapping("api/faq-answers/{id}")
	public void deleteAnswer(
			@PathVariable("id") Integer id) {
		FAQAnswerRepository.deleteById(id);
	}

	// Remove an FAQAnswer (does not delete answer)
	@PutMapping("api/faq-answers/{id}/removeAnswer")
	public FrequentlyAskedAnswer removeAnswer(
			@PathVariable("id") Integer id) {
		FrequentlyAskedAnswer findAnswer = FAQAnswerRepository.findFrequentlyAskedAnswerById(id);
		findAnswer.deleteAnswer();
		return FAQAnswerRepository.save(findAnswer);
	}
}
