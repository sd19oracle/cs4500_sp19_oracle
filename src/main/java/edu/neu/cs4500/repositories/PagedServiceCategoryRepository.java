package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagedServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {
}
