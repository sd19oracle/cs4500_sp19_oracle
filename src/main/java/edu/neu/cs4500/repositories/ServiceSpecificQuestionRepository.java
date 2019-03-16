package edu.neu.cs4500.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.neu.cs4500.models.ServiceSpecificQuestion;

import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ServiceSpecificQuestionRepository extends CrudRepository<ServiceSpecificQuestion,
        Integer> {
  // for view all questions
  @Query(value = "SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion serviceSpecificQuestion")
  public List<ServiceSpecificQuestion> findAllServiceSpecificQuestion();

  // for one specific question by given question id
  @Query(value = "SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion " +
          "serviceSpecificQuestion WHERE serviceSpecificQuestion.id=:id")
  public ServiceSpecificQuestion findAllServiceSpecificQuestionById(@Param("id") Integer id);

  @Query(value = "SELECT s FROM ServiceSpecificQuestion s "
          + "WHERE s.title LIKE CONCAT('%',:filterTitle,'%')"
          + "AND s.type LIKE CONCAT('%',:filterType,'%') "
          + "AND (s.choice LIKE CONCAT('%',:filterChoice,'%') OR s.choice is NULL)")
  public List<ServiceSpecificQuestion> findServiceQuestionsByFilter(
          @Param("filterTitle") String title,
          @Param("filterType") String type,
          @Param("filterChoice") String choice);
}
