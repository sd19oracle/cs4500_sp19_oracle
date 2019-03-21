package edu.neu.cs4500.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PreUpdate;

import edu.neu.cs4500.models.ServiceSpecificAnswer;
import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.ServiceSpecificAnswerRepository;
import edu.neu.cs4500.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*")
public class ServiceSpecificAnswerService {
  @Autowired
  ServiceSpecificAnswerRepository serviceSpecificAnswerRepository;
  @Autowired
  UserRepository userRepository;
  // for Admin view all services' answers
  @GetMapping("/api/servicesSpecificAnswers")
  public List<ServiceSpecificAnswer> findAllServiceSpecificAnswer() {
    return serviceSpecificAnswerRepository.findAllServiceSpecificAnswers();
  }

  // for Admin find one answer by answer id
  @GetMapping("api/servicesSpecificAnswers/{answerID}")
  public ServiceSpecificAnswer findOneAnswer(@PathVariable("answerID") Integer id) {
    return serviceSpecificAnswerRepository.findServiceSpecificAnswerById(id);
  }

  // for Admin find one provider's all answers
  @GetMapping("api/servicesSpecificAnswers/{ProviderID}/allAnswers")
  public List<ServiceSpecificAnswer> findOneProviderAllAnswers(
          @PathVariable("ProviderID") Integer id) {
    List<ServiceSpecificAnswer> list = serviceSpecificAnswerRepository.findAllServiceSpecificAnswers();
    List<ServiceSpecificAnswer> temp = new ArrayList<>();
    for (ServiceSpecificAnswer a: list) {
      if (a.getUser() != null) {
        if (a.getUser().getId().equals(id)) {
          temp.add(a);
        }
      }
    }
    return temp;
  }


  // for Admin find one provider's one answer for one question
  @GetMapping("api/servicesSpecificAnswers/{ProviderID}/byQuestion/{questionID}")
  public ServiceSpecificAnswer findOneProviderOneQuestion(
          @PathVariable("ProviderID") Integer id,
          @PathVariable("questionID") Integer qId) {
    List<ServiceSpecificAnswer> list =
            serviceSpecificAnswerRepository.findAllServiceSpecificAnswers();
    ServiceSpecificAnswer answer = null;
    for (ServiceSpecificAnswer a: list) {
      if (a.getUser()!= null && a.getQuestion() != null) {
        if (a.getUser().getId().equals(id) && a.getQuestion().getId().equals(qId)) {
          answer = a;
        }
      }
    }
    return answer;
  }

  // for Admin find one provider's all answers for one service
  @GetMapping("api/servicesSpecificAnswers/{ProviderID}/byService/{serviceID}")
  public List<ServiceSpecificAnswer> findOneProviderAllAnswersForAService(
          @PathVariable("ProviderID") Integer id,
          @PathVariable("serviceID") Integer sId) {
    List<ServiceSpecificAnswer> list =
            serviceSpecificAnswerRepository.findAllServiceSpecificAnswers();
    List<ServiceSpecificAnswer> temp = new ArrayList<>();
    for (ServiceSpecificAnswer a: list) {
      if (a.getUser()!= null && a.getQuestion() != null) {
        if (a.getUser().getId().equals(id) && a.getQuestion().getService().getId().equals(sId)) {
          temp.add(a);
        }
      }
    }
    return temp;
  }


  // Admin add an answer
  @PostMapping("api/servicesSpecificAnswers/{providerId}")
  public ServiceSpecificAnswer createAnAnswer(
          @PathVariable("providerId") Integer id,
          @RequestBody ServiceSpecificAnswer oneAnswer) {
    User findUser = userRepository.findUserById(id);
    if (findUser != null) {
      oneAnswer.setUser(findUser);
      findUser.addAnswer(oneAnswer);
    }
    return serviceSpecificAnswerRepository.save(oneAnswer);
  }

  // to update an answer
  @PutMapping("api/servicesSpecificAnswers/{answerId}")
  public ServiceSpecificAnswer updateAnswer(
          @PathVariable("answerId") Integer id,
          @RequestBody ServiceSpecificAnswer updateAnswer) {
    ServiceSpecificAnswer findAnswer =
            serviceSpecificAnswerRepository.findServiceSpecificAnswerById(id);
    findAnswer.setAnswer(updateAnswer.getAnswer());
    if (updateAnswer.getUser() != null) {
      findAnswer.setUser(updateAnswer.getUser());
    }
    if (updateAnswer.getQuestion() != null) {
      findAnswer.setQuestion(updateAnswer.getQuestion());
    }
    if (updateAnswer.getId() != null) {
      findAnswer.setId(updateAnswer.getId());
    }
    return serviceSpecificAnswerRepository.save(findAnswer);
  }

  // to delete one answer
  @DeleteMapping("api/servicesSpecificAnswers/{answerId}")
  public void deleteOneAnswer( @PathVariable("answerId") Integer id) {
    serviceSpecificAnswerRepository.deleteById(id);
  }
}
