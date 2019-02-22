package edu.neu.cs4500.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * This is the class for searching the service providers by a Service type and a search criteria,
 * and there is an algorithm that returns the score board for all providers. There is a function
 * that checks the score board and will return a list of providers in a ascending order of their
 * scores. When two providers have the same score, the one with lower id comes first in the list.
 */
public class ServiceSearch {

  /**
   * Returns a list of providers in a ascending order of their scores according to the
   * search criteria for the given service.
   * @param service The service type.
   * @param criteria The criteria given by users. (Users' preference)
   * @return a list of providers in a ascending order of their scores
   */
  public static List<User> searchForProviders(Service service, SearchCriteria criteria) {
    List<User> listOfProviders = service.getProviders();
    TreeMap<User, Integer> scoreBoard = algorithm(listOfProviders, criteria.getListPredicate());
    List<User> sorted = sortByValue(scoreBoard);

    return sorted;
  }

  /**
   * The algorithm to calculate the score board for given providers and a list of search predicates.
   * There are three types of question for now, which are MINMAX, MULTIPLECHOICE, TRUEFALSE.
   * (This function should be private, but we make it public for testing!!!)
   * @param providers the providers
   * @param expectations a list of expectations (predicates)
   * @return The score board for the given provider by comparing with provider's answers with answers
   * from the predicates.
   */
  public static TreeMap<User, Integer> algorithm(List<User> providers,
                                                 List<SearchPredicate> expectations) {
    TreeMap<User, Integer> scoreBoard = new TreeMap<>(new byUserId());
    for (User user: providers) {
      scoreBoard.put(user, 0);
    }

    for (SearchPredicate predicate : expectations) {
      ServiceSpecificQuestion question = predicate.getQuestion();
      List<ServiceSpecificAnswer> listOfProvAnswers = question.getAnswers();
      for (ServiceSpecificAnswer providerAnswer : listOfProvAnswers) {
        String QuestionType = question.getType();
        switch (QuestionType) {
          case "MINMAX":
            String[] minMax = providerAnswer.getAnswer().split(",");
            String[] criteraMinMax = predicate.getAnswer().getAnswer().split(",");
            if (Integer.parseInt(minMax[0]) <= Integer.parseInt(criteraMinMax[0]) &&
                    Integer.parseInt(minMax[1]) >= Integer.parseInt(criteraMinMax[1])) {
              User findProvider = providerAnswer.getUser();
              scoreBoard.put(findProvider, scoreBoard.get(findProvider) + 1);
            }
            break;
          case "TRUEFALSE":
            if (providerAnswer.getAnswer().equals(predicate.getAnswer().getAnswer())) {
              User findProvider = providerAnswer.getUser();
              scoreBoard.put(findProvider, scoreBoard.get(findProvider) + 1);
            }
            break;
          case "MUTIPLECHOICE":
            if (providerAnswer.getAnswer().equals(predicate.getAnswer().getAnswer())) {
              User findProvider = providerAnswer.getUser();
              scoreBoard.put(findProvider, scoreBoard.get(findProvider) + 1);
            }
            break;
          default: break;
        }
      }
    }
    return scoreBoard;
  }


  static class byUserId implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
      if (u1.getId().compareTo(u2.getId()) > 0) {
        return -1;
      } else if (u1.getId().compareTo(u2.getId()) == 0){
        return 0;
      } else {
        return 1;
      }
    }
  }


  static List<User> sortByValue(TreeMap<User, Integer> hm) {
    // Create a list from elements of HashMap
    List<Map.Entry<User, Integer> > list =
            new LinkedList<Map.Entry<User, Integer> >(hm.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<User, Integer> >() {
      public int compare(Map.Entry<User, Integer> o1,
                         Map.Entry<User, Integer> o2)
      {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // put data from sorted list to a new list in reversed order
    // cause we want to higher grade providers
    List<User> sortedUsers = new ArrayList<>();
    for (Map.Entry<User, Integer> aa : list) {
      sortedUsers.add(0, aa.getKey());
    }
    return sortedUsers;
  }
}


