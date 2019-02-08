package edu.neu.cs4500.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.ServiceSpecificAnswer;
import edu.neu.cs4500.repositories.ServiceSpecificAnswerRepository;

@RestController
public class ServiceSpecificAnswerService {
  @Autowired
  ServiceSpecificAnswerRepository serviceSpecificAnswerRepository;
  // for Admin view all services' answers
  @GetMapping("api/servicesSpecificAnswers/allAnswers")
  public List<ServiceSpecificAnswer> findAllServiceSpecificAnswer() {
    return serviceSpecificAnswerRepository.findAllServiceSpecificAnswers();
  }

//  @GetMapping("api/servicesSpecificAnswers/{userID}")
//  public ServiceSpecificAnswer find

}