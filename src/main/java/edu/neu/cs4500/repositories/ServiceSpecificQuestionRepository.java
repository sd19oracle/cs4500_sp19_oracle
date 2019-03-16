package edu.neu.cs4500.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.neu.cs4500.models.ServiceSpecificQuestion;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface ServiceSpecificQuestionRepository extends CrudRepository<ServiceSpecificQuestion,
        Integer> {
    // for view all questions
    @Query(value="SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion serviceSpecificQuestion")
    public List<ServiceSpecificQuestion> findAllServiceSpecificQuestion();

    // for one specific question by given question id
    @Query(value="SELECT serviceSpecificQuestion FROM ServiceSpecificQuestion serviceSpecificQuestion WHERE serviceSpecificQuestion.id=:id")
    public ServiceSpecificQuestion findAllServiceSpecificQuestionById(@Param("id") Integer id);

    // Selecting delete on a record shall remove the record from the database
//    @Transactional
//    @Modifying
//    @Query(value="INSERT INTO service_specific_question (title, type, choice, service_id) VALUES (?1,?2,?3,'123')",
//            nativeQuery = true)
//    public ServiceSpecificQuestion save(String title, String type, String choice);
    // Selecting edit shall update the edit form with the record fields from the database

    @Transactional
    @Modifying
    @Query(value="DELETE FROM ServiceSpecificQuestion serviceSpecificQuestion WHERE serviceSpecificQuestion.id=:id" )
    public void deleteById(@Param("id") Integer id);
//    Selecting update shall update the record in the database with the current values in the edit form
//    Selecting create shall create a new record in the database with the current values in the edit form
//    Selecting any field in a record navigates to the detail view for that record

}
