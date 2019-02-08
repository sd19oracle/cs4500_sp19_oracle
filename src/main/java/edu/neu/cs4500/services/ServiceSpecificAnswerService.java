package edu.neu.cs4500.services;
import java.util.ArrayList;
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
  @GetMapping("api/servicesSpecificAnswers")
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
      if (a.getUser().getId().equals(id)) {
        temp.add(a);
      }
    }
    return temp;
  }

  // Admin help add an answer for a provider (for some case this provider cannot add answer
  // and asks help from admin users)
  @PostMapping("api/servicesSpecificAnswers/}")
  public ServiceSpecificAnswer createAnAnswer(
          @RequestBody ServiceSpecificAnswer oneAnswer) {
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
    return serviceSpecificAnswerRepository.save(findAnswer);
  }

  // to delete one answer
  @DeleteMapping("api/servicesSpecificAnswers/{answerId}")
  public void deleteOneAnswer( @PathVariable("answerId") Integer id) {
    serviceSpecificAnswerRepository.deleteById(id);
  }
}
