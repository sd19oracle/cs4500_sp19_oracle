package edu.neu.cs4500.models;

public class ServiceSpecificAnswer {
  private Integer id;
  private String answer;
  private ServiceSpecificQuestion question;
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public ServiceSpecificQuestion getQuestion() {
    return question;
  }

  public void setQuestion(ServiceSpecificQuestion question) {
    this.question = question;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
