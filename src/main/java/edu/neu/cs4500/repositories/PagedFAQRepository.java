package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Michael Goodnow on 2019-03-21.
 */

public interface PagedFAQRepository extends JpaRepository<FrequentlyAskedQuestion, Integer> {
	@Query("SELECT faq FROM FrequentlyAskedQuestion faq WHERE faq.title LIKE :title AND faq.question LIKE :question")
    public List<FrequentlyAskedQuestion> filterFAQs(
            @Param("title") String title,
            @Param("question") String question);
}
