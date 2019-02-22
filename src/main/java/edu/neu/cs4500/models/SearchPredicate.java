package edu.neu.cs4500.models;

public class SearchPredicate {
  private ServiceSpecificQuestion question;
  private ServiceSpecificAnswer answer;
  public SearchPredicate() {}
  
  public SearchPredicate(ServiceSpecificQuestion question, ServiceSpecificAnswer answer) {
    this.question = question;
    this.answer = answer;
  }

  public ServiceSpecificQuestion getQuestion() {
    return question;
  }

  public void setQuestion(ServiceSpecificQuestion question) {
    this.question = question;
  }

  public ServiceSpecificAnswer getAnswer() {
    return answer;
  }

  public void setAnswer(ServiceSpecificAnswer answer) {
    this.answer = answer;
  }




}
