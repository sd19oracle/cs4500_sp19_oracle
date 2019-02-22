import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.neu.cs4500.models.SearchCriteria;
import edu.neu.cs4500.models.SearchPredicate;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceSearch;
import edu.neu.cs4500.models.ServiceSpecificAnswer;
import edu.neu.cs4500.models.ServiceSpecificQuestion;
import edu.neu.cs4500.models.User;

public class ServiceSearchTest {
  private Service service1 = new Service();
  private List<ServiceSpecificQuestion> questionsToService1 = new ArrayList<>();
  private ServiceSpecificQuestion q1 = new ServiceSpecificQuestion();
  private ServiceSpecificAnswer a1q1 = new ServiceSpecificAnswer();
  private ServiceSpecificAnswer a2q1 = new ServiceSpecificAnswer();
  private ServiceSpecificAnswer a3q1 = new ServiceSpecificAnswer();
  private User provider1 = new User();
  private User provider2 = new User();
  private User provider3 = new User();

  private User customer1 = new User();
  private ServiceSpecificAnswer cust1Q1 = new ServiceSpecificAnswer();


  private List<ServiceSpecificAnswer> ansQ1 = new ArrayList<>();
  private SearchPredicate predicate1 = new SearchPredicate();
  private SearchCriteria criteria1 = new SearchCriteria();



  @BeforeEach
  public void setUp() {
    provider1.setId(1);
    provider1.setUsername("John");
    provider2.setId(2);
    provider2.setUsername("Joe");
    provider3.setId(3);
    provider3.setUsername("Jose");

    a1q1.setAnswer("FALSE");
    a1q1.setUser(provider1);
    a2q1.setAnswer("TRUE");
    a2q1.setUser(provider2);
    a3q1.setAnswer("FALSE");
    a3q1.setUser(provider3);

    q1.setId(1);
    q1.setTitle("Do you have pets?");
    q1.setType("TRUEFALSE");
    q1.setService(service1);
    ansQ1 = new ArrayList<>(Arrays.asList(a1q1, a2q1, a3q1));
    q1.setAnswers(ansQ1);

    service1.setQuestions(questionsToService1);
    service1.setProviders(new ArrayList<>(Arrays.asList(provider1, provider2, provider3)));

    cust1Q1.setAnswer("TRUE");
    predicate1.setQuestion(q1);
    predicate1.setAnswer(cust1Q1);
    criteria1.setListPredicate(new ArrayList<>(Arrays.asList(predicate1)));
  }

  @Test
  public void testCorrectScore() {
    ServiceSpecificAnswer cust1Q2 = new ServiceSpecificAnswer();
    SearchPredicate predicate2 = new SearchPredicate();
    SearchCriteria criteria2 = new SearchCriteria();
    List<ServiceSpecificAnswer> ansQ2 = new ArrayList<>();

    ServiceSpecificQuestion q2 = new ServiceSpecificQuestion();
    ServiceSpecificAnswer a1q2 = new ServiceSpecificAnswer();
    ServiceSpecificAnswer a2q2 = new ServiceSpecificAnswer();
    ServiceSpecificAnswer a3q2 = new ServiceSpecificAnswer();

    a1q2.setAnswer("1");
    a1q2.setUser(provider1);
    a2q2.setAnswer("1");
    a2q2.setUser(provider2);
    a3q2.setAnswer("2");
    a3q2.setUser(provider3);
    ansQ2 = new ArrayList<>(Arrays.asList(a1q2, a2q2, a3q2));

    q2.setId(1);
    q2.setTitle("How many rooms do you have?");
    q2.setType("MUTIPLECHOICE");
    q2.setService(service1);
    q2.setAnswers(ansQ2);
    cust1Q2.setAnswer("1");

    predicate2.setQuestion(q2);
    predicate2.setAnswer(cust1Q2);
    criteria2.setListPredicate(new ArrayList<>(Arrays.asList(predicate1, predicate2)));

    TreeMap<User, Integer> scoreResult = ServiceSearch.algorithm(service1.getProviders(), criteria2.getListPredicate());

    int p1Score = scoreResult.get(provider1);
    int p2Score = scoreResult.get(provider2);
    int p3Score = scoreResult.get(provider3);
    assertEquals(1, p1Score);
    assertEquals(2, p2Score);
    assertEquals(0, p3Score);

  }

  @Test
  public void testCorrectList() {
    List<User> originalProvider = new ArrayList<>(Arrays.asList(provider1, provider2, provider3));
    List<User> returnProvider = ServiceSearch.searchForProviders(service1, criteria1);
    for (User provider : returnProvider) {
      assertEquals(true, originalProvider.contains(provider));
    }
  }

  @Test
  public void testEmptyPredicateScore() {
    SearchCriteria criteria2 = new SearchCriteria();
    TreeMap<User, Integer> scoreResult = ServiceSearch.algorithm(service1.getProviders(), criteria2.getListPredicate());
    int p1Score = scoreResult.get(provider1);
    int p2Score = scoreResult.get(provider2);
    int p3Score = scoreResult.get(provider3);
    assertEquals(0, p1Score);
    assertEquals(0, p2Score);
    assertEquals(0, p3Score);

  }


  @Test
  public void testRandom() {
    List<User> actualRanking = ServiceSearch.searchForProviders(service1, criteria1);
    List<User> expectedRanking = new ArrayList<>(Arrays.asList(provider2, provider1, provider3));

    for (int i = 0; i < actualRanking.size(); i++) {
      assertEquals(expectedRanking.get(i).getId(), actualRanking.get(i).getId());
    }
  }


}
