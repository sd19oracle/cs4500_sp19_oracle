package edu.neu.cs4500.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import edu.neu.cs4500.models.ServiceSpecificAnswer;
import edu.neu.cs4500.models.User;

public interface ServiceSpecificAnswerRepository extends CrudRepository<User, Integer> {
  @Query(value = "SELECT serviceSpecificAnswer FROM ServiceSpecificAnswer serviceSpecificAnswer")
  public List<ServiceSpecificAnswer> findAllServiceSpecificAnswers();

  @Query(value = "SELECT serviceSpecificAnswer FROM ServiceSpecificAnswer serviceSpecificAnswer "
          + "WHERE serviceSpecificAnswer.id=:id")
  public ServiceSpecificAnswer findServiceSpecificAnswerById(@Param("id") Integer id);

  @Query(value = "SELECT serviceSpecificAnswer FROM ServiceSpecificAnswer serviceSpecificAnswer "
          + "WHERE serviceSpecificAnswer.user=:ProviderId")
  public List<ServiceSpecificAnswer> findALLServiceSpecificAnswerByProviderId(@Param("ProviderId") Integer id);
}
