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

import edu.neu.cs4500.models.PageInfo;
import edu.neu.cs4500.models.ServiceSpecificAnswer;
import edu.neu.cs4500.models.ServiceSpecificQuestion;
import edu.neu.cs4500.repositories.ServiceSpecificAnswerRepository;
import edu.neu.cs4500.repositories.ServiceSpecificQuestionRepository;

@RestController
@CrossOrigin(origins = "*")
public class ServiceSpecificQuestionService {
  @Autowired
  ServiceSpecificQuestionRepository serviceSpecificQuestionRepository;

  // for Admin to view all service questions
  @GetMapping("api/servicesSpecificQuestions")
  public List<ServiceSpecificQuestion> findAllServiceSpecificQuestion() {
    return serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();
  }

  // for Admin find one question by question id
  @GetMapping("api/servicesSpecificQuestions/{questionID}")
  public ServiceSpecificQuestion findOneQuestion(@PathVariable("questionID") Integer id) {
//    return new ServiceSpecificQuestion("TEST", "TEST", "123");
    return serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id);
  }

  @PostMapping("api/servicesSpecificQuestions/{questionID}")
  public ServiceSpecificQuestion findOneQuestion(
          @RequestBody ServiceSpecificQuestion returnOne,
          @PathVariable("questionID") Integer id) {
//    return new ServiceSpecificQuestion("TEST", "TEST", "123");
    return serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id);
  }

  // for Admin find one service's all questions
  @GetMapping("api/servicesSpecificQuestions/byService/{serviceID}")
  public List<ServiceSpecificQuestion> findOneServiceAllQuestions(
          @PathVariable("serviceID") Integer id) {
    List<ServiceSpecificQuestion> list =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();
    List<ServiceSpecificQuestion> temp = new ArrayList<>();
    for (ServiceSpecificQuestion question : list) {
      if (question.getService().getId().equals(id)) {
        temp.add(question);
      }
    }
    return temp;
  }

  // for Admin find all questions by question type
  @GetMapping("api/servicesSpecificQuestions/byType/{type}")
  public List<ServiceSpecificQuestion> findAllQuestionsByType(
          @PathVariable("type") String type
  ) {
    List<ServiceSpecificQuestion> list =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();
    List<ServiceSpecificQuestion> temp = new ArrayList<>();
    for (ServiceSpecificQuestion question : list) {
      if (question.getType().equals(type)) {
        temp.add(question);
      }
    }
    return temp;
  }

  @GetMapping("api/servicesSpecificQuestions/page/{num_item}")
  public PageInfo findServicesQuestionsByItemNum(@PathVariable("num_item") int num_item) {
    PageInfo pInfo = new PageInfo();
    List<ServiceSpecificQuestion> allQuestion =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();

    double page_info = Math.ceil((double) allQuestion.size() / num_item);

    if (num_item > allQuestion.size()) {
      num_item = allQuestion.size();
    }
    List<ServiceSpecificQuestion> listQuestion = allQuestion.subList(0, num_item);

    pInfo.setPage_num((int) page_info);
    pInfo.setList_questions(listQuestion);
    pInfo.setTotal_questions(allQuestion.size());

    return pInfo;
  }

  @GetMapping("api/servicesSpecificQuestions/page/{num_item}/{page_num}")
  public List<ServiceSpecificQuestion> findServicesQuestionsByPageNum(
          @PathVariable("num_item") int num_item,
          @PathVariable("page_num") int page_num) {
    List<ServiceSpecificQuestion> allQuestion =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestion();
    int start = num_item * (page_num - 1);
    int end = num_item * page_num;

    if (end > allQuestion.size()) {
      end = allQuestion.size();
    }

    return allQuestion.subList(start, end);
  }


  @PostMapping("api/servicesSpecificQuestions/filter")
  public List<ServiceSpecificQuestion> findServiceQuestionsByFilter(
          @RequestBody ServiceSpecificQuestion filterQuestion) {
    String title = filterQuestion.getTitle();
    String type = filterQuestion.getType();
    String choice = filterQuestion.getChoice();
    List<ServiceSpecificQuestion> questions =
            serviceSpecificQuestionRepository.findServiceQuestionsByFilter(title, type, choice);
    if (!choice.equals("")) {
      List<ServiceSpecificQuestion> accurate = new ArrayList<>();
      for (ServiceSpecificQuestion q : questions) {
        if (q.getChoice() != null) {
          accurate.add(q);
        }
      }
      return accurate;
    }
    return questions;
  }

  // Admin add a question
  // TODO: NEED TO DISTINGUISH THE SERVICE LATER
  @PostMapping("api/servicesSpecificQuestions/")
  public ServiceSpecificQuestion createAQuestion(
          @RequestBody ServiceSpecificQuestion oneQuestion) {
    System.out.println(oneQuestion);
    return serviceSpecificQuestionRepository.save(oneQuestion);

  }

  // Admin add an answer for A question
  @PostMapping("api/serviceSpecificQuestions/question/{questionId}/addAnAnswer")
  public ServiceSpecificQuestion createAnswerForAQuestion(
          @RequestBody ServiceSpecificAnswer oneAnswer,
          @PathVariable("questionId") Integer id
  ) {
    ServiceSpecificQuestion findQ =
            serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id);
    findQ.addServiceSpecificAnswer(oneAnswer);
    oneAnswer.setQuestion(findQ);
    return findQ;
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

    if (updateQuestion.getAnswers() != null) {
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

  // To update the choice of a Question
  @PutMapping("api/servicesSpecificQuestions/question/{questionId}/addChoice/{choice}")
  public ServiceSpecificQuestion updateChoiceForAQuestion(
          @PathVariable("questionId") Integer id,
          @PathVariable("choice") String choice
  ) {
    serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id).setChoice(choice);
    return serviceSpecificQuestionRepository.findAllServiceSpecificQuestionById(id);
  }

  // to delete one question by given question id
  @DeleteMapping("api/servicesSpecificQuestions/{questionId}")
  public void deleteOneAnswer(@PathVariable("questionId") Integer id) {
    serviceSpecificQuestionRepository.deleteById(id);
  }
}
