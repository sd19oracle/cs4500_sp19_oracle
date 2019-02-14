package edu.neu.cs4500.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;

public interface FrequentlyAskedQuestionRepository extends CrudRepository<FrequentlyAskedQuestion, Integer> {
	  // Lists all the FAQs in the table
	  @Query(value = "SELECT frequentlyAskedQuestion FROM FrequentlyAskedQuestion frequentlyAskedQuestion")
	  public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions();

	  // Finds a specific FAQ by ID
	  @Query(value = "SELECT frequentlyAskedQuestion FROM FrequentlyAskedQuestion frequentlyAskedQuestion "
	          + "WHERE frequentlyAskedQuestion.id=:questionId")
	  public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(@Param("questionId") Integer id);
}