package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.neu.cs4500.models.ServiceSpecificQuestion;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceSpecificQuestionRepository extends CrudRepository<ServiceSpecificQuestion, Integer> {
    @Query(value="SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion serviceSpecificQuestion")
    public List<User> findAllServiceSpecificQuestion();
    @Query(value="SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion serviceSpecificQuestion WHERE serviceSpecificQuestion.id=:id")
    public User findAllServiceSpecificQuestionById(@Param("id") Integer id);
    @Query(value="SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion serviceSpecificQuestion WHERE serviceSpecificQuestion.service=:ServiceId")
    public User findAllServiceSpecificQuestionByServiceId(@Param("ServiceId") Integer id);
}
