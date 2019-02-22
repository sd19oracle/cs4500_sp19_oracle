package edu.neu.cs4500.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class ServiceSearch {
  public static List<User> searchForProviders(Service service, SearchCriteria criteria) {
    List<User> results = new ArrayList<>();
    List<User> listOfProviders = service.getProviders();
    TreeMap<User, Integer> scoreBoard = new TreeMap<>();
    for (User user: listOfProviders) {
      scoreBoard.put(user, 0);
    }

    List<SearchPredicate> listOfPredicates = criteria.getListPredicate();

    for (SearchPredicate predicate : listOfPredicates) {
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

    TreeMap<User, Integer> sortedHash = sortByValue(scoreBoard);
    for (User user : sortedHash.keySet()) {
      results.add(0, user);
    }
    return results;
  }

  static TreeMap<User, Integer> sortByValue(TreeMap<User, Integer> hm) {
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

    // put data from sorted list to hashmap
    TreeMap<User, Integer> temp = new TreeMap<>();
    for (Map.Entry<User, Integer> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }
}


