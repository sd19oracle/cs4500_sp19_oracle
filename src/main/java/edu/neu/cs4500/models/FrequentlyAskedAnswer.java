package edu.neu.cs4500.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "frequently_asked_answers")
public class FrequentlyAskedAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    @ManyToOne
    @JsonIgnore
    private FrequentlyAskedQuestion frequentlyAskedQuestion;
    @ManyToOne
    @JsonIgnore
    private User user;
    @Transient
    private String question;
    @Transient
    private Integer questionId;

    public FrequentlyAskedAnswer() {

    }

    public FrequentlyAskedAnswer(FrequentlyAskedQuestion faq, User u, String a) {
        this.frequentlyAskedQuestion = faq;
        this.user = u;
        this.answer = a;
    }

    public Integer getQuestionId() {
        return frequentlyAskedQuestion.getId();
    }

    public String getQuestion() {
        return frequentlyAskedQuestion.getQuestion();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FrequentlyAskedQuestion getFrequentlyAskedQuestion() {
        return frequentlyAskedQuestion;
    }

    public void setFrequentlyAskedQuestion(FrequentlyAskedQuestion frequentlyAskedQuestion) {
        this.frequentlyAskedQuestion = frequentlyAskedQuestion;
    }

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
}
