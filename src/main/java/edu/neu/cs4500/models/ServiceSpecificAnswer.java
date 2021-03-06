package edu.neu.cs4500.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_specific_answer")
public class ServiceSpecificAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String answer;
  @ManyToOne
  @JsonIgnore
  private ServiceSpecificQuestion question;
  @ManyToOne
  @JsonIgnore
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
