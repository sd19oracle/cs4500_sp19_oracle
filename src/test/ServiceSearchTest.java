import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

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
  private SearchPredicate predPet = new SearchPredicate();
  private SearchPredicate predDuration = new SearchPredicate();
  private SearchPredicate predContact = new SearchPredicate();
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
    predPet.setQuestion(q1);
    predPet.setAnswer(cust1Q1);
    criteria1.setListPredicate(new ArrayList<>(Arrays.asList(predPet)));
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
    criteria2.setListPredicate(new ArrayList<>(Arrays.asList(predPet, predicate2)));

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

  // Test if the order of the ranking is as expected when two providers have the same
  // score. The user who has a lower ID should appear earlier in the list.
  @Test
  public void testOrderRanking1() {
    setUpPredicateDuration();
    // set up criteria consisting of pet and duration questions
    SearchCriteria criteriaPetAndDuration = new SearchCriteria();
    criteriaPetAndDuration.setListPredicate(new ArrayList<>(Arrays.asList(predPet, predDuration)));
    List<User> actualRanking = ServiceSearch.searchForProviders(service1, criteriaPetAndDuration);
    // provider2 wins predPet (predicate - pet)
    // provider3 wins predDuration (predicate - duration)
    List<User> expectedRanking = new ArrayList<>(Arrays.asList(provider2, provider3, provider1));

    for (int i = 0; i < actualRanking.size(); i++) {
      assertEquals(expectedRanking.get(i).getId(), actualRanking.get(i).getId());
    }
  }

  // Test when all providers have the same score, the ranking of the provider should be
  // as if they are listed by user ID (ascending).
  @Test
  public void testOrderRanking2() {
    // set up the predicates for all providers to have one and only one point
    setUpPredicateDuration();
    setUpPredicateContact();
    // set up criteria consisting of pet, duration, and way of contact
    SearchCriteria criteriaPetAndDurationAndContact = new SearchCriteria();
    criteriaPetAndDurationAndContact.setListPredicate(
            new ArrayList<>(Arrays.asList(predPet, predDuration, predContact)));
    List<User> actualRanking = ServiceSearch.searchForProviders(service1, criteriaPetAndDurationAndContact);
    List<User> expectedRanking = new ArrayList<>(Arrays.asList(provider1, provider2, provider3));

    for (int i = 0; i < actualRanking.size(); i++) {
      assertEquals(expectedRanking.get(i).getId(), actualRanking.get(i).getId());
    }
  }

  // Initialize a predicate asking the amount of time needed for cleaning.
  private void setUpPredicateDuration() {
    // initialize a question
    ServiceSpecificQuestion qDuration = new ServiceSpecificQuestion();
    qDuration.setId(2);
    qDuration.setType("MINMAX");
    qDuration.setTitle("How much time (minutes) do you need to do the cleaning?");
    qDuration.setService(service1);
    // initialize all providers' answers for the question
    ServiceSpecificAnswer a1Ava = new ServiceSpecificAnswer();
    ServiceSpecificAnswer a2Ava = new ServiceSpecificAnswer();
    ServiceSpecificAnswer a3Ava = new ServiceSpecificAnswer();
    a1Ava.setAnswer("30,45");
    a1Ava.setUser(provider1);
    a2Ava.setAnswer("10,20");
    a2Ava.setUser(provider2);
    a3Ava.setAnswer("50,65");
    a3Ava.setUser(provider3);
    // set all answers to the question
    qDuration.setAnswers(new ArrayList<>(Arrays.asList(a1Ava, a2Ava, a3Ava)));
    // initialize a predicate for criterion - duration
    predDuration.setQuestion(qDuration);
    ServiceSpecificAnswer custAnsAva = new ServiceSpecificAnswer();
    custAnsAva.setAnswer("50,55");
    predDuration.setAnswer(custAnsAva);
  }

  // Initialize a predicate asking the best way of contact.
  private void setUpPredicateContact() {
    // initialize a question
    ServiceSpecificQuestion qContact = new ServiceSpecificQuestion();
    qContact.setId(3);
    qContact.setType("MUTIPLECHOICE");
    qContact.setTitle("How would you like us to contact you?");
    qContact.setService(service1);
    // initialize all providers' answers for the question
    ServiceSpecificAnswer a1Contact = new ServiceSpecificAnswer();
    ServiceSpecificAnswer a2Contact = new ServiceSpecificAnswer();
    ServiceSpecificAnswer a3Contact = new ServiceSpecificAnswer();
    a1Contact.setAnswer("email");
    a1Contact.setUser(provider1);
    a2Contact.setAnswer("app");
    a2Contact.setUser(provider2);
    a3Contact.setAnswer("phone");
    a3Contact.setUser(provider3);
    // set all answers to the question
    qContact.setAnswers(new ArrayList<>(Arrays.asList(a1Contact, a2Contact, a3Contact)));
    // initialize a predicate for criterion - duration
    predContact.setQuestion(qContact);
    ServiceSpecificAnswer custAnsContact = new ServiceSpecificAnswer();
    custAnsContact.setAnswer("email");
    predContact.setAnswer(custAnsContact);
  }
}
