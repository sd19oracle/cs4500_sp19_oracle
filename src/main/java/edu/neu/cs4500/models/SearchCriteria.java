package edu.neu.cs4500.models;

import java.util.ArrayList;
import java.util.List;


public class SearchCriteria {
  private List<SearchPredicate> listPredicate;
  public SearchCriteria() {
    this.listPredicate = new ArrayList<>();
  }
  public SearchCriteria(List<SearchPredicate> listOfPredicate) {
    this.listPredicate = listOfPredicate;
  }

  public List<SearchPredicate> getListPredicate() {
    return listPredicate;
  }

  public void setListPredicate(List<SearchPredicate> listPredicate) {
    this.listPredicate = listPredicate;
  }

  public void addListPredicate(SearchPredicate predicate) {
    this.listPredicate.add(predicate);
  }

}
