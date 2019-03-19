package edu.neu.cs4500.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.neu.cs4500.models.ServiceSpecificQuestion;

public interface PagedServiceQuestionRepo extends JpaRepository<ServiceSpecificQuestion, Integer>{
}
