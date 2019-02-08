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

import edu.neu.cs4500.models.ServiceSpecificQuestion;
import edu.neu.cs4500.repositories.ServiceSpecificQuestionRepository;

@RestController
public class ServiceSpecificQuestionService {
  @Autowired
  ServiceSpecificQuestionRepository serviceSpecificQuestionRepository;

  // for Admin to view all service questions
  @GetMapping("api/servicesSpecificQuestions")
  public List<ServiceSpecificQuestion> findAllServiceSpecificAnswer() {
    return serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();
  }

  // for Admin find one question by question id
  @GetMapping("api/servicesSpecificQuestions/{questionID}")
  public ServiceSpecificQuestion findOneQuestion(@PathVariable("questionID") Integer id) {
    return serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id);
  }

  // for Admin find one service's all questions
  @GetMapping("api/servicesSpecificQuestions/{serviceID}/allQuestions")
  public List<ServiceSpecificQuestion> findOneProviderAllQuestions(
          @PathVariable("serviceID") Integer id) {
    List<ServiceSpecificQuestion> list =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();
    List<ServiceSpecificQuestion> temp = new ArrayList<>();
    for (ServiceSpecificQuestion question: list) {
      if (question.getService().getId().equals(id)) {
        temp.add(question);
      }
    }
    return temp;
  }

  // Admin add a question
  @PostMapping("api/servicesSpecificQuestions/}")
  public ServiceSpecificQuestion createAQuestion(
          @RequestBody ServiceSpecificQuestion oneQuestion) {
    return serviceSpecificQuestionRepository.save(oneQuestion);
  }

  // to update a question
  @PutMapping("api/servicesSpecificQuestions/{QuestionId}")
  public ServiceSpecificQuestion updateQuestion(
          @PathVariable("QuestionId") Integer id,
          @RequestBody ServiceSpecificQuestion updateQuestion) {
    ServiceSpecificQuestion findQuestion =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id);
    findQuestion.setTitle(updateQuestion.getTitle());
    findQuestion.setType(updateQuestion.getType());

    // optional
    if (updateQuestion.getChoice() != null) {
      findQuestion.setChoice(updateQuestion.getChoice());
    }

    if (updateQuestion.getAnswers().size() > 0) {
      findQuestion.setAnswers(updateQuestion.getAnswers());
    }

    if (updateQuestion.getService() != null) {
      findQuestion.setService(updateQuestion.getService());
    }
    if (updateQuestion.getId() != null) {
      findQuestion.setId(updateQuestion.getId());
    }
    return serviceSpecificQuestionRepository.save(findQuestion);
  }

  // to delete one question by given question id
  @DeleteMapping("api/servicesSpecificQuestions/{questionId}")
  public void deleteOneAnswer( @PathVariable("questionId") Integer id) {
    serviceSpecificQuestionRepository.deleteById(id);
  }
}
