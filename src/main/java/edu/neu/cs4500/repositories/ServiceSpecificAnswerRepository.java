package edu.neu.cs4500.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import edu.neu.cs4500.models.ServiceSpecificAnswer;

public interface ServiceSpecificAnswerRepository extends CrudRepository<ServiceSpecificAnswer,
        Integer> {
  // for all answers from all providers
  @Query(value = "SELECT serviceSpecificAnswer FROM ServiceSpecificAnswer serviceSpecificAnswer")
  public List<ServiceSpecificAnswer> findAllServiceSpecificAnswers();

  // for one answer by given answer id
  @Query(value = "SELECT serviceSpecificAnswer FROM ServiceSpecificAnswer serviceSpecificAnswer "
          + "WHERE serviceSpecificAnswer.id=:answerId")
  public ServiceSpecificAnswer findServiceSpecificAnswerById(@Param("answerId") Integer id);

  // for one provider's all answers
  @Query(value = "SELECT serviceSpecificAnswer FROM ServiceSpecificAnswer serviceSpecificAnswer "
          + "WHERE serviceSpecificAnswer.user=:ProviderId")
  public List<ServiceSpecificAnswer> findALLServiceSpecificAnswerByProviderId(
          @Param("ProviderId") Integer id);
}
