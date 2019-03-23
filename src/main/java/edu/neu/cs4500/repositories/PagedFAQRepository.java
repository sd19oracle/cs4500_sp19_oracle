package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Michael Goodnow on 2019-03-21.
 */

public interface PagedFAQRepository extends JpaRepository<FrequentlyAskedQuestion, Integer> {
}
